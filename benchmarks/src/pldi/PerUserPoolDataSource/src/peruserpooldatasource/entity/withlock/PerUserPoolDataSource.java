package peruserpooldatasource.entity.withlock;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.dbcp.datasources.*;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import javax.naming.NamingException;
import javax.sql.ConnectionPoolDataSource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class PerUserPoolDataSource extends InstanceKeyDataSource {
	public ReentrantReadWriteLock perUserMaxWaitLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock perUserMaxIdleLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock perUserMaxActiveLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock defaultMaxWaitLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock defaultMaxIdleLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock defaultMaxActiveLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock perUserDefaultTransactionIsolationLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock perUserDefaultReadOnlyLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock perUserDefaultAutoCommitLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock instanceKey_poolsLock = new ReentrantReadWriteLock();
	private static final Map poolKeys = new HashMap();
	private int defaultMaxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
	private int defaultMaxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
	private int defaultMaxWait = (int) Math.min((long) Integer.MAX_VALUE, GenericObjectPool.DEFAULT_MAX_WAIT);
	Map perUserDefaultAutoCommit = null;
	Map perUserDefaultTransactionIsolation = null;
	Map perUserMaxActive = null;
	Map perUserMaxIdle = null;
	Map perUserMaxWait = null;
	Map perUserDefaultReadOnly = null;
	private transient Map pools = new HashMap();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public PerUserPoolDataSource() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void close(Map poolMap) {
	}
	@Perm(requires = "share(pools) * pure(instanceKey) in alive", ensures = "share(pools) * pure(instanceKey) in alive")
	public void close() {
		instanceKey_poolsLock.writeLock().lock();
		for (Iterator poolIter = pools.values().iterator(); poolIter.hasNext();) {
			try {
				((ObjectPool) poolIter.next()).close();
			} catch (Exception closePoolException) {
			}
		}
		InstanceKeyObjectFactory.removeInstance(instanceKey);
		instanceKey_poolsLock.writeLock().unlock();
	}
	@Perm(requires = "pure(defaultMaxActive) in alive", ensures = "pure(defaultMaxActive) in alive")
	public int getDefaultMaxActive() {
		try {
			defaultMaxActiveLock.readLock().lock();
			return (this.defaultMaxActive);
		} finally {
			defaultMaxActiveLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(defaultMaxActive) in alive", ensures = "full(defaultMaxActive) in alive")
	public void setDefaultMaxActive(int maxActive) {
		assertInitializationAllowed();
		defaultMaxActiveLock.writeLock().lock();
		this.defaultMaxActive = maxActive;
		defaultMaxActiveLock.writeLock().unlock();
	}
	@Perm(requires = "pure(defaultMaxIdle) in alive", ensures = "pure(defaultMaxIdle) in alive")
	public int getDefaultMaxIdle() {
		try {
			defaultMaxIdleLock.readLock().lock();
			return (this.defaultMaxIdle);
		} finally {
			defaultMaxIdleLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(defaultMaxIdle) in alive", ensures = "full(defaultMaxIdle) in alive")
	public void setDefaultMaxIdle(int defaultMaxIdle) {
		assertInitializationAllowed();
		defaultMaxIdleLock.writeLock().lock();
		this.defaultMaxIdle = defaultMaxIdle;
		defaultMaxIdleLock.writeLock().unlock();
	}
	@Perm(requires = "pure(defaultMaxWait) in alive", ensures = "pure(defaultMaxWait) in alive")
	public int getDefaultMaxWait() {
		try {
			defaultMaxWaitLock.readLock().lock();
			return (this.defaultMaxWait);
		} finally {
			defaultMaxWaitLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(defaultMaxWait) in alive", ensures = "full(defaultMaxWait) in alive")
	public void setDefaultMaxWait(int defaultMaxWait) {
		assertInitializationAllowed();
		defaultMaxWaitLock.writeLock().lock();
		this.defaultMaxWait = defaultMaxWait;
		defaultMaxWaitLock.writeLock().unlock();
	}
	@Perm(requires = "pure(perUserDefaultAutoCommit) in alive", ensures = "pure(perUserDefaultAutoCommit) in alive")
	public Boolean getPerUserDefaultAutoCommit(String key) {
		Boolean value = null;
		perUserDefaultAutoCommitLock.readLock().lock();
		if (perUserDefaultAutoCommit != null) {
			value = (Boolean) perUserDefaultAutoCommit.get(key);
		}
		perUserDefaultAutoCommitLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(perUserDefaultAutoCommit) in alive", ensures = "unique(perUserDefaultAutoCommit) in alive")
	public void setPerUserDefaultAutoCommit(String username, Boolean value) {
		assertInitializationAllowed();
		perUserDefaultAutoCommitLock.writeLock().lock();
		if (perUserDefaultAutoCommit == null) {
			perUserDefaultAutoCommit = new HashMap();
		}
		perUserDefaultAutoCommit.put(username, value);
		perUserDefaultAutoCommitLock.writeLock().unlock();
	}
	@Perm(requires = "pure(perUserDefaultTransactionIsolation) in alive", ensures = "pure(perUserDefaultTransactionIsolation) in alive")
	public Integer getPerUserDefaultTransactionIsolation(String username) {
		Integer value = null;
		perUserDefaultTransactionIsolationLock.readLock().lock();
		if (perUserDefaultTransactionIsolation != null) {
			value = (Integer) perUserDefaultTransactionIsolation.get(username);
		}
		perUserDefaultTransactionIsolationLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(perUserDefaultTransactionIsolation) in alive", ensures = "unique(perUserDefaultTransactionIsolation) in alive")
	public void setPerUserDefaultTransactionIsolation(String username, Integer value) {
		assertInitializationAllowed();
		perUserDefaultTransactionIsolationLock.writeLock().lock();
		if (perUserDefaultTransactionIsolation == null) {
			perUserDefaultTransactionIsolation = new HashMap();
		}
		perUserDefaultTransactionIsolation.put(username, value);
		perUserDefaultTransactionIsolationLock.writeLock().unlock();
	}
	@Perm(requires = "pure(perUserMaxActive) in alive", ensures = "pure(perUserMaxActive) in alive")
	public Integer getPerUserMaxActive(String username) {
		Integer value = null;
		perUserMaxActiveLock.readLock().lock();
		if (perUserMaxActive != null) {
			value = (Integer) perUserMaxActive.get(username);
		}
		perUserMaxActiveLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(perUserMaxActive) in alive", ensures = "unique(perUserMaxActive) in alive")
	public void setPerUserMaxActive(String username, Integer value) {
		assertInitializationAllowed();
		perUserMaxActiveLock.writeLock().lock();
		if (perUserMaxActive == null) {
			perUserMaxActive = new HashMap();
		}
		perUserMaxActive.put(username, value);
		perUserMaxActiveLock.writeLock().unlock();
	}
	@Perm(requires = "pure(perUserMaxIdle) in alive", ensures = "pure(perUserMaxIdle) in alive")
	public Integer getPerUserMaxIdle(String username) {
		Integer value = null;
		perUserMaxIdleLock.readLock().lock();
		if (perUserMaxIdle != null) {
			value = (Integer) perUserMaxIdle.get(username);
		}
		perUserMaxIdleLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(perUserMaxIdle) in alive", ensures = "unique(perUserMaxIdle) in alive")
	public void setPerUserMaxIdle(String username, Integer value) {
		assertInitializationAllowed();
		perUserMaxIdleLock.writeLock().lock();
		if (perUserMaxIdle == null) {
			perUserMaxIdle = new HashMap();
		}
		perUserMaxIdle.put(username, value);
		perUserMaxIdleLock.writeLock().unlock();
	}
	@Perm(requires = "pure(perUserMaxWait) in alive", ensures = "pure(perUserMaxWait) in alive")
	public Integer getPerUserMaxWait(String username) {
		Integer value = null;
		perUserMaxWaitLock.readLock().lock();
		if (perUserMaxWait != null) {
			value = (Integer) perUserMaxWait.get(username);
		}
		perUserMaxWaitLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(perUserMaxWait) in alive", ensures = "unique(perUserMaxWait) in alive")
	public void setPerUserMaxWait(String username, Integer value) {
		assertInitializationAllowed();
		perUserMaxWaitLock.writeLock().lock();
		if (perUserMaxWait == null) {
			perUserMaxWait = new HashMap();
		}
		perUserMaxWait.put(username, value);
		perUserMaxWaitLock.writeLock().unlock();
	}
	@Perm(requires = "pure(perUserDefaultReadOnly) in alive", ensures = "pure(perUserDefaultReadOnly) in alive")
	public Boolean getPerUserDefaultReadOnly(String username) {
		Boolean value = null;
		perUserDefaultReadOnlyLock.readLock().lock();
		if (perUserDefaultReadOnly != null) {
			value = (Boolean) perUserDefaultReadOnly.get(username);
		}
		perUserDefaultReadOnlyLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(perUserDefaultReadOnly) in alive", ensures = "unique(perUserDefaultReadOnly) in alive")
	public void setPerUserDefaultReadOnly(String username, Boolean value) {
		assertInitializationAllowed();
		perUserDefaultReadOnlyLock.writeLock().lock();
		if (perUserDefaultReadOnly == null) {
			perUserDefaultReadOnly = new HashMap();
		}
		perUserDefaultReadOnly.put(username, value);
		perUserDefaultReadOnlyLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int getNumActive() {
		return getNumActive(null, null);
	}
	@Perm(requires = "pure(pools) in alive", ensures = "pure(pools) in alive")
	public int getNumActive(String username, String password) {
		instanceKey_poolsLock.readLock().lock();
		ObjectPool pool = (ObjectPool) pools.get(getPoolKey(username));
		instanceKey_poolsLock.readLock().unlock();
		return (pool == null) ? 0 : pool.getNumActive();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int getNumIdle() {
		return getNumIdle(null, null);
	}
	@Perm(requires = "pure(pools) in alive", ensures = "pure(pools) in alive")
	public int getNumIdle(String username, String password) {
		instanceKey_poolsLock.readLock().lock();
		ObjectPool pool = (ObjectPool) pools.get(getPoolKey(username));
		instanceKey_poolsLock.readLock().unlock();
		return (pool == null) ? 0 : pool.getNumIdle();
	}
	@Perm(requires = "share(pools) in alive", ensures = "share(pools) in alive")
	protected synchronized PooledConnectionAndInfo getPooledConnectionAndInfo(String username, String password)
			throws SQLException {
		PoolKey key = getPoolKey(username);
		try {
			instanceKey_poolsLock.writeLock().lock();
			Object pool = pools.get(key);
			if (pool == null) {
				try {
					registerPool(username, password);
					pool = pools.get(key);
				} catch (NamingException e) {
					throw new SQLNestedException("RegisterPool failed", e);
				}
			}
		} finally {
			instanceKey_poolsLock.writeLock().unlock();
		}
		PooledConnectionAndInfo info = null;
		try {
			info = (PooledConnectionAndInfo) ((ObjectPool) pool).borrowObject();
		} catch (Exception e) {
			throw new SQLNestedException("Could not retrieve connection info from pool", e);
		}
		return info;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected void setupDefaults(Connection con, String username) throws SQLException {
		boolean defaultAutoCommit = isDefaultAutoCommit();
		if (username != null) {
			perUserDefaultAutoCommitLock.readLock().lock();
			Boolean userMax = getPerUserDefaultAutoCommit(username);
			perUserDefaultAutoCommitLock.readLock().unlock();
			if (userMax != null) {
				defaultAutoCommit = userMax.booleanValue();
			}
		}
		boolean defaultReadOnly = isDefaultReadOnly();
		if (username != null) {
			perUserDefaultReadOnlyLock.readLock().lock();
			Boolean userMax = getPerUserDefaultReadOnly(username);
			perUserDefaultReadOnlyLock.readLock().unlock();
			if (userMax != null) {
				defaultReadOnly = userMax.booleanValue();
			}
		}
		int defaultTransactionIsolation = getDefaultTransactionIsolation();
		if (username != null) {
			perUserDefaultTransactionIsolationLock.readLock().lock();
			Integer userMax = getPerUserDefaultTransactionIsolation(username);
			perUserDefaultTransactionIsolationLock.readLock().unlock();
			if (userMax != null) {
				defaultTransactionIsolation = userMax.intValue();
			}
		}
		con.setAutoCommit(defaultAutoCommit);
		con.setReadOnly(defaultReadOnly);
		if (defaultTransactionIsolation != UNKNOWN_TRANSACTIONISOLATION) {
			con.setTransactionIsolation(defaultTransactionIsolation);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private PoolKey getPoolKey(String username) {
		PoolKey key = null;
		String dsName = getDataSourceName();
		Map dsMap = (Map) poolKeys.get(dsName);
		if (dsMap != null) {
			key = (PoolKey) dsMap.get(username);
		}
		if (key == null) {
			key = new PoolKey(dsName, username);
			if (dsMap == null) {
				dsMap = new HashMap();
				poolKeys.put(dsName, dsMap);
			}
			dsMap.put(username, key);
		}
		return key;
	}
	@Perm(requires = "share(pools) in alive", ensures = "share(pools) in alive")
	private synchronized void registerPool(String username, String password) throws NamingException, SQLException {
		ConnectionPoolDataSource cpds = testCPDS(username, password);
		perUserMaxActiveLock.readLock().lock();
		Integer userMax = getPerUserMaxActive(username);
		perUserMaxActiveLock.readLock().unlock();
		defaultMaxActiveLock.readLock().lock();
		int maxActive = (userMax == null) ? getDefaultMaxActive() : userMax.intValue();
		defaultMaxActiveLock.readLock().unlock();
		perUserMaxIdleLock.readLock().lock();
		userMax = getPerUserMaxIdle(username);
		perUserMaxIdleLock.readLock().unlock();
		defaultMaxIdleLock.readLock().lock();
		int maxIdle = (userMax == null) ? getDefaultMaxIdle() : userMax.intValue();
		defaultMaxIdleLock.readLock().unlock();
		perUserMaxWaitLock.readLock().lock();
		userMax = getPerUserMaxWait(username);
		perUserMaxWaitLock.readLock().unlock();
		defaultMaxWaitLock.readLock().lock();
		int maxWait = (userMax == null) ? getDefaultMaxWait() : userMax.intValue();
		defaultMaxWaitLock.readLock().unlock();
		GenericObjectPool pool = new GenericObjectPool(null);
		pool.setMaxActive(maxActive);
		pool.setMaxIdle(maxIdle);
		pool.setMaxWait(maxWait);
		pool.setWhenExhaustedAction(whenExhaustedAction(maxActive, maxWait));
		pool.setTestOnBorrow(getTestOnBorrow());
		pool.setTestOnReturn(getTestOnReturn());
		pool.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
		pool.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun());
		pool.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
		pool.setTestWhileIdle(getTestWhileIdle());
		new CPDSConnectionFactory(cpds, pool, getValidationQuery(), username, password);
		instanceKey_poolsLock.writeLock().lock();
		pools.put(getPoolKey(username), pool);
		instanceKey_poolsLock.writeLock().unlock();
	}
	@Perm(requires = "share(pools) in alive", ensures = "share(pools) in alive")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		try {
			in.defaultReadObject();
			PerUserPoolDataSource oldDS = (PerUserPoolDataSource) new PerUserPoolDataSourceFactory()
					.getObjectInstance(getReference(), null, null, null);
			instanceKey_poolsLock.writeLock().lock();
			this.pools = oldDS.pools;
			instanceKey_poolsLock.writeLock().unlock();
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
