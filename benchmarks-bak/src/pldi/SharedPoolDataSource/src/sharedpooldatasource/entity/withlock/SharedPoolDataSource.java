package sharedpooldatasource.entity.withlock;
import org.apache.commons.collections.LRUMap;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.dbcp.datasources.*;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import javax.naming.NamingException;
import javax.sql.ConnectionPoolDataSource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SharedPoolDataSource extends InstanceKeyDataSource {
	public ReentrantReadWriteLock maxIdleLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock instanceKey_maxActive_maxWait_poolLock = new ReentrantReadWriteLock();
	private static final Map userKeys = new LRUMap(10);
	private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
	private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
	private int maxWait = (int) Math.min((long) Integer.MAX_VALUE, GenericObjectPool.DEFAULT_MAX_WAIT);
	private KeyedObjectPool pool = null;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public SharedPoolDataSource() {
	}
	@Perm(requires = "share(pool) * pure(instanceKey) in alive", ensures = "share(pool) * pure(instanceKey) in alive")
	public void close() throws Exception {
		instanceKey_maxActive_maxWait_poolLock.writeLock().lock();
		pool.close();
		InstanceKeyObjectFactory.removeInstance(instanceKey);
		instanceKey_maxActive_maxWait_poolLock.writeLock().unlock();
	}
	@Perm(requires = "pure(maxActive) in alive", ensures = "pure(maxActive) in alive")
	public int getMaxActive() {
		try {
			instanceKey_maxActive_maxWait_poolLock.readLock().lock();
			return (this.maxActive);
		} finally {
			instanceKey_maxActive_maxWait_poolLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(maxActive) in alive", ensures = "full(maxActive) in alive")
	public void setMaxActive(int maxActive) {
		assertInitializationAllowed();
		instanceKey_maxActive_maxWait_poolLock.writeLock().lock();
		this.maxActive = maxActive;
		instanceKey_maxActive_maxWait_poolLock.writeLock().unlock();
	}
	@Perm(requires = "pure(maxIdle) in alive", ensures = "pure(maxIdle) in alive")
	public int getMaxIdle() {
		try {
			maxIdleLock.readLock().lock();
			return (this.maxIdle);
		} finally {
			maxIdleLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(maxIdle) in alive", ensures = "full(maxIdle) in alive")
	public void setMaxIdle(int maxIdle) {
		assertInitializationAllowed();
		maxIdleLock.writeLock().lock();
		this.maxIdle = maxIdle;
		maxIdleLock.writeLock().unlock();
	}
	@Perm(requires = "pure(maxWait) in alive", ensures = "pure(maxWait) in alive")
	public int getMaxWait() {
		try {
			instanceKey_maxActive_maxWait_poolLock.readLock().lock();
			return (this.maxWait);
		} finally {
			instanceKey_maxActive_maxWait_poolLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(maxWait) in alive", ensures = "full(maxWait) in alive")
	public void setMaxWait(int maxWait) {
		assertInitializationAllowed();
		instanceKey_maxActive_maxWait_poolLock.writeLock().lock();
		this.maxWait = maxWait;
		instanceKey_maxActive_maxWait_poolLock.writeLock().unlock();
	}
	@Perm(requires = "pure(pool) in alive", ensures = "pure(pool) in alive")
	public int getNumActive() {
		try {
			instanceKey_maxActive_maxWait_poolLock.readLock().lock();
			return (pool == null) ? 0 : pool.getNumActive();
		} finally {
			instanceKey_maxActive_maxWait_poolLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(pool) in alive", ensures = "pure(pool) in alive")
	public int getNumIdle() {
		try {
			instanceKey_maxActive_maxWait_poolLock.readLock().lock();
			return (pool == null) ? 0 : pool.getNumIdle();
		} finally {
			instanceKey_maxActive_maxWait_poolLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(pool) in alive", ensures = "share(pool) in alive")
	protected synchronized PooledConnectionAndInfo getPooledConnectionAndInfo(String username, String password)
			throws SQLException {
		try {
			instanceKey_maxActive_maxWait_poolLock.writeLock().lock();
			if (pool == null) {
				try {
					registerPool(username, password);
				} catch (NamingException e) {
					throw new SQLNestedException("RegisterPool failed", e);
				}
			}
			PooledConnectionAndInfo info = null;
			try {
				info = (PooledConnectionAndInfo) pool.borrowObject(getUserPassKey(username, password));
			} catch (Exception e) {
				throw new SQLNestedException("Could not retrieve connection info from pool", e);
			}
		} finally {
			instanceKey_maxActive_maxWait_poolLock.writeLock().unlock();
		}
		return info;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private UserPassKey getUserPassKey(String username, String password) {
		UserPassKey key = (UserPassKey) userKeys.get(username);
		if (key == null) {
			key = new UserPassKey(username, password);
			userKeys.put(username, key);
		}
		return key;
	}
	@Perm(requires = "pure(maxActive) * pure(maxWait) * share(pool) in alive", ensures = "pure(maxActive) * pure(maxWait) * share(pool) in alive")
	private void registerPool(String username, String password) throws NamingException, SQLException {
		ConnectionPoolDataSource cpds = testCPDS(username, password);
		GenericKeyedObjectPool tmpPool = new GenericKeyedObjectPool(null);
		instanceKey_maxActive_maxWait_poolLock.writeLock().lock();
		tmpPool.setMaxActive(getMaxActive());
		maxIdleLock.readLock().lock();
		tmpPool.setMaxIdle(getMaxIdle());
		maxIdleLock.readLock().unlock();
		tmpPool.setMaxWait(getMaxWait());
		tmpPool.setWhenExhaustedAction(whenExhaustedAction(maxActive, maxWait));
		tmpPool.setTestOnBorrow(getTestOnBorrow());
		tmpPool.setTestOnReturn(getTestOnReturn());
		tmpPool.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
		tmpPool.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun());
		tmpPool.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
		tmpPool.setTestWhileIdle(getTestWhileIdle());
		pool = tmpPool;
		new KeyedCPDSConnectionFactory(cpds, pool, getValidationQuery());
		instanceKey_maxActive_maxWait_poolLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected void setupDefaults(Connection con, String username) throws SQLException {
		con.setAutoCommit(isDefaultAutoCommit());
		con.setReadOnly(isDefaultReadOnly());
		int defaultTransactionIsolation = getDefaultTransactionIsolation();
		if (defaultTransactionIsolation != UNKNOWN_TRANSACTIONISOLATION) {
			con.setTransactionIsolation(defaultTransactionIsolation);
		}
	}
	@Perm(requires = "share(pool) in alive", ensures = "share(pool) in alive")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		try {
			in.defaultReadObject();
			SharedPoolDataSource oldDS = (SharedPoolDataSource) new SharedPoolDataSourceFactory()
					.getObjectInstance(getReference(), null, null, null);
			instanceKey_maxActive_maxWait_poolLock.writeLock().lock();
			this.pool = oldDS.pool;
			instanceKey_maxActive_maxWait_poolLock.writeLock().unlock();
		} catch (NamingException e) {
			throw new IOException("NamingException: " + e);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}
