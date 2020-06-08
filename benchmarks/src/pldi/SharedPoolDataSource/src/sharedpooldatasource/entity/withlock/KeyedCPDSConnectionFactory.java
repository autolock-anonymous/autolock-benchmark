package sharedpooldatasource.entity.withlock;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class KeyedCPDSConnectionFactory implements KeyedPoolableObjectFactory, ConnectionEventListener {
	public ReentrantReadWriteLock _cpds__pool__validationQuery_pcMap_validatingMapLock = new ReentrantReadWriteLock();
	private static final String NO_KEY_MESSAGE = "close() was called on a Connection, but "
			+ "I have no record of the underlying PooledConnection.";
	protected ConnectionPoolDataSource _cpds = null;
	protected String _validationQuery = null;
	protected KeyedObjectPool _pool = null;
	private Map validatingMap = new HashMap();
	private WeakHashMap pcMap = new WeakHashMap();
	@Perm(requires = "unique(_cpds) * unique(_pool) * unique(_validationQuery) in alive", ensures = "unique(_cpds) * unique(_pool) * unique(_validationQuery) in alive")
	public KeyedCPDSConnectionFactory(ConnectionPoolDataSource cpds, KeyedObjectPool pool, String validationQuery) {
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		_cpds = cpds;
		_pool = pool;
		_pool.setFactory(this);
		_validationQuery = validationQuery;
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
	}
	@Perm(requires = "full(_cpds) in alive", ensures = "full(_cpds) in alive")
	synchronized public void setCPDS(ConnectionPoolDataSource cpds) {
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		_cpds = cpds;
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
	}
	@Perm(requires = "full(_validationQuery) in alive", ensures = "full(_validationQuery) in alive")
	synchronized public void setValidationQuery(String validationQuery) {
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		_validationQuery = validationQuery;
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
	}
	@Perm(requires = "share(_pool) in alive", ensures = "share(_pool) in alive")
	synchronized public void setPool(KeyedObjectPool pool) throws SQLException {
		try {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			if (null != _pool && pool != _pool) {
				try {
					_pool.close();
				} catch (RuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw new SQLNestedException("Cannot set the pool on this factory", e);
				}
			}
			_pool = pool;
		} finally {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(_pool) in alive", ensures = "pure(_pool) in alive")
	public KeyedObjectPool getPool() {
		try {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.readLock().lock();
			return _pool;
		} finally {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(_cpds) * share(pcMap) in alive", ensures = "pure(_cpds) * share(pcMap) in alive")
	public synchronized Object makeObject(Object key) throws Exception {
		Object obj = null;
		UserPassKey upkey = (UserPassKey) key;
		PooledConnection pc = null;
		String username = upkey.getUsername();
		String password = upkey.getPassword();
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		if (username == null) {
			pc = _cpds.getPooledConnection();
		} else {
			pc = _cpds.getPooledConnection(username, password);
		}
		pc.addConnectionEventListener(this);
		obj = new PooledConnectionAndInfo(pc, username, password);
		pcMap.put(pc, obj);
		_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		return obj;
	}
	@Perm(requires = "share(pcMap) in alive", ensures = "share(pcMap) in alive")
	public void destroyObject(Object key, Object obj) throws Exception {
		if (obj instanceof PooledConnectionAndInfo) {
			PooledConnection pc = ((PooledConnectionAndInfo) obj).getPooledConnection();
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			pcMap.remove(pc);
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
			pc.close();
		}
	}
	@Perm(requires = "pure(_validationQuery) * share(validatingMap) in alive", ensures = "pure(_validationQuery) * share(validatingMap) in alive")
	public boolean validateObject(Object key, Object obj) {
		boolean valid = false;
		if (obj instanceof PooledConnectionAndInfo) {
			PooledConnection pconn = ((PooledConnectionAndInfo) obj).getPooledConnection();
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			String query = _validationQuery;
			if (null != query) {
				Connection conn = null;
				Statement stmt = null;
				ResultSet rset = null;
				validatingMap.put(pconn, null);
				try {
					conn = pconn.getConnection();
					stmt = conn.createStatement();
					rset = stmt.executeQuery(query);
					if (rset.next()) {
						valid = true;
					} else {
						valid = false;
					}
				} catch (Exception e) {
					valid = false;
				} finally {
					try {
						rset.close();
					} catch (Throwable t) {
					}
					try {
						stmt.close();
					} catch (Throwable t) {
					}
					try {
						conn.close();
					} catch (Throwable t) {
					}
					validatingMap.remove(pconn);
				}
			} else {
				valid = true;
			}
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		} else {
			valid = false;
		}
		return valid;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void passivateObject(Object key, Object obj) {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void activateObject(Object key, Object obj) {
	}
	@Perm(requires = "share(validatingMap) * share(pcMap) * share(_pool) in alive", ensures = "share(validatingMap) * share(pcMap) * share(_pool) in alive")
	public void connectionClosed(ConnectionEvent event) {
		PooledConnection pc = (PooledConnection) event.getSource();
		try {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			if (!validatingMap.containsKey(pc)) {
				PooledConnectionAndInfo info = (PooledConnectionAndInfo) pcMap.get(pc);
				if (info == null) {
					throw new IllegalStateException(NO_KEY_MESSAGE);
				}
				try {
					_pool.returnObject(info.getUserPassKey(), info);
				} catch (Exception e) {
					System.err.println("CLOSING DOWN CONNECTION AS IT COULD " + "NOT BE RETURNED TO THE POOL");
					try {
						destroyObject(info.getUserPassKey(), info);
					} catch (Exception e2) {
						System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
						e2.printStackTrace();
					}
				}
			}
		} finally {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(pcMap) in alive", ensures = "share(pcMap) in alive")
	public void connectionErrorOccurred(ConnectionEvent event) {
		PooledConnection pc = (PooledConnection) event.getSource();
		try {
			if (null != event.getSQLException()) {
				System.err.println("CLOSING DOWN CONNECTION DUE TO INTERNAL ERROR (" + event.getSQLException() + ")");
			}
			pc.removeConnectionEventListener(this);
		} catch (Exception ignore) {
		}
		try {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			PooledConnectionAndInfo info = (PooledConnectionAndInfo) pcMap.get(pc);
			if (info == null) {
				throw new IllegalStateException(NO_KEY_MESSAGE);
			}
			try {
				destroyObject(info.getUserPassKey(), info);
			} catch (Exception e) {
				System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
				e.printStackTrace();
			}
		} finally {
			_cpds__pool__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		}
	}
}
