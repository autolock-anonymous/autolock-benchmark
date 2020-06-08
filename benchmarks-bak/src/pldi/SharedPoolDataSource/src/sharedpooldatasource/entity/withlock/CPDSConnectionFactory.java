package sharedpooldatasource.entity.withlock;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
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
class CPDSConnectionFactory implements PoolableObjectFactory, ConnectionEventListener {
	public ReentrantReadWriteLock _cpds__password__pool__username__validationQuery_pcMap_validatingMapLock = new ReentrantReadWriteLock();
	private static final String NO_KEY_MESSAGE = "close() was called on a Connection, but "
			+ "I have no record of the underlying PooledConnection.";
	protected ConnectionPoolDataSource _cpds = null;
	protected String _validationQuery = null;
	protected ObjectPool _pool = null;
	protected String _username = null;
	protected String _password = null;
	private Map validatingMap = new HashMap();
	private WeakHashMap pcMap = new WeakHashMap();
	@Perm(requires = "unique(_cpds) * unique(_pool) * unique(_validationQuery) * unique(_username) * unique(_password) in alive", ensures = "unique(_cpds) * unique(_pool) * unique(_validationQuery) * unique(_username) * unique(_password) in alive")
	public CPDSConnectionFactory(ConnectionPoolDataSource cpds, ObjectPool pool, String validationQuery,
			String username, String password) {
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		_cpds = cpds;
		_pool = pool;
		_pool.setFactory(this);
		_validationQuery = validationQuery;
		_username = username;
		_password = password;
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
	}
	@Perm(requires = "full(_cpds) in alive", ensures = "full(_cpds) in alive")
	public synchronized void setCPDS(ConnectionPoolDataSource cpds) {
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		_cpds = cpds;
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
	}
	@Perm(requires = "full(_validationQuery) in alive", ensures = "full(_validationQuery) in alive")
	public synchronized void setValidationQuery(String validationQuery) {
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
		_validationQuery = validationQuery;
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
	}
	@Perm(requires = "share(_pool) in alive", ensures = "share(_pool) in alive")
	public synchronized void setPool(ObjectPool pool) throws SQLException {
		try {
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
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
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(_pool) in alive", ensures = "pure(_pool) in alive")
	public ObjectPool getPool() {
		try {
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.readLock().lock();
			return _pool;
		} finally {
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.readLock().unlock();
		}
	}
	@Perm(requires = "none(_username) * pure(_cpds) * none(_password) * full(pcMap) in alive", ensures = "none(_username) * pure(_cpds) * none(_password) * full(pcMap) in alive")
	public synchronized Object makeObject() {
		Object obj;
		try {
			PooledConnection pc = null;
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			if (_username == null) {
				pc = _cpds.getPooledConnection();
			} else {
				pc = _cpds.getPooledConnection(_username, _password);
			}
			pc.addConnectionEventListener(this);
			obj = new PooledConnectionAndInfo(pc, _username, _password);
			pcMap.put(pc, obj);
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return obj;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void destroyObject(Object obj) throws Exception {
		if (obj instanceof PooledConnectionAndInfo) {
			((PooledConnectionAndInfo) obj).getPooledConnection().close();
		}
	}
	@Perm(requires = "pure(_validationQuery) * share(validatingMap) in alive", ensures = "pure(_validationQuery) * share(validatingMap) in alive")
	public boolean validateObject(Object obj) {
		boolean valid = false;
		if (obj instanceof PooledConnectionAndInfo) {
			PooledConnection pconn = ((PooledConnectionAndInfo) obj).getPooledConnection();
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
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
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		} else {
			valid = false;
		}
		return valid;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void passivateObject(Object obj) {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void activateObject(Object obj) {
	}
	@Perm(requires = "share(validatingMap) * pure(pcMap) * share(_pool) in alive", ensures = "share(validatingMap) * pure(pcMap) * share(_pool) in alive")
	public void connectionClosed(ConnectionEvent event) {
		PooledConnection pc = (PooledConnection) event.getSource();
		try {
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().lock();
			if (!validatingMap.containsKey(pc)) {
				Object info = pcMap.get(pc);
				if (info == null) {
					throw new IllegalStateException(NO_KEY_MESSAGE);
				}
				try {
					_pool.returnObject(info);
				} catch (Exception e) {
					System.err.println("CLOSING DOWN CONNECTION AS IT COULD " + "NOT BE RETURNED TO THE POOL");
					try {
						destroyObject(info);
					} catch (Exception e2) {
						System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
						e2.printStackTrace();
					}
				}
			}
		} finally {
			_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(pcMap) in alive", ensures = "pure(pcMap) in alive")
	public void connectionErrorOccurred(ConnectionEvent event) {
		PooledConnection pc = (PooledConnection) event.getSource();
		try {
			if (null != event.getSQLException()) {
				System.err.println("CLOSING DOWN CONNECTION DUE TO INTERNAL ERROR (" + event.getSQLException() + ")");
			}
			pc.removeConnectionEventListener(this);
		} catch (Exception ignore) {
		}
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.readLock().lock();
		Object info = pcMap.get(pc);
		_cpds__password__pool__username__validationQuery_pcMap_validatingMapLock.readLock().unlock();
		if (info == null) {
			throw new IllegalStateException(NO_KEY_MESSAGE);
		}
		try {
			destroyObject(info);
		} catch (Exception e) {
			System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
			e.printStackTrace();
		}
	}
}
