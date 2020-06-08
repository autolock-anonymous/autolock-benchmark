package peruserpooldatasource.entity.withlock;
import java.io.Serializable;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import org.apache.commons.dbcp.SQLNestedException;
import org.apache.commons.pool.impl.GenericObjectPool;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public abstract class InstanceKeyDataSource implements DataSource, Referenceable, Serializable {
	public ReentrantReadWriteLock descriptionLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock _timeBetweenEvictionRunsMillisLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock _numTestsPerEvictionRunLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock _minEvictableIdleTimeMillisLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock loginTimeoutLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock defaultTransactionIsolationLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock defaultReadOnlyLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock defaultAutoCommitLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock _testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock cpds_dataSourceName_getConnectionCalled_instanceKeyLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock jndiEnvironmentLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock logWriterLock = new ReentrantReadWriteLock();
	private static final String GET_CONNECTION_CALLED = "A Connection was already requested from this source, "
			+ "further initialization is not allowed.";
	private static final String BAD_TRANSACTION_ISOLATION = "The requested TransactionIsolation level is invalid.";
	protected static final int UNKNOWN_TRANSACTIONISOLATION = -1;
	private boolean getConnectionCalled = false;
	private ConnectionPoolDataSource cpds = null;
	private String dataSourceName = null;
	private boolean defaultAutoCommit = false;
	private int defaultTransactionIsolation = UNKNOWN_TRANSACTIONISOLATION;
	private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
	private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
	private int maxWait = (int) Math.min((long) Integer.MAX_VALUE, GenericObjectPool.DEFAULT_MAX_WAIT);
	private boolean defaultReadOnly = false;
	private String description = null;
	Properties jndiEnvironment = null;
	private int loginTimeout = 0;
	private PrintWriter logWriter = null;
	private boolean _testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
	private boolean _testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
	private int _timeBetweenEvictionRunsMillis = (int) Math.min((long) Integer.MAX_VALUE,
			GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
	private int _numTestsPerEvictionRun = GenericObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
	private int _minEvictableIdleTimeMillis = (int) Math.min((long) Integer.MAX_VALUE,
			GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
	private boolean _testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
	private String validationQuery = null;
	private boolean testPositionSet = false;
	protected String instanceKey = null;
	@Perm(requires = "unique(defaultAutoCommit) in alive", ensures = "unique(defaultAutoCommit) in alive")
	public InstanceKeyDataSource() {
		defaultAutoCommitLock.writeLock().lock();
		defaultAutoCommit = true;
		defaultAutoCommitLock.writeLock().unlock();
	}
	@Perm(requires = "pure(getConnectionCalled) in alive", ensures = "pure(getConnectionCalled) in alive")
	protected void assertInitializationAllowed() throws IllegalStateException {
		try {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
			if (getConnectionCalled) {
				throw new IllegalStateException(GET_CONNECTION_CALLED);
			}
		} finally {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		}
	}
	public abstract void close() throws Exception;
	@Perm(requires = "pure(cpds) in alive", ensures = "pure(cpds) in alive")
	public ConnectionPoolDataSource getConnectionPoolDataSource() {
		try {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
			return cpds;
		} finally {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(dataSourceName) * full(cpds) * share(instanceKey) in alive", ensures = "pure(dataSourceName) * full(cpds) * share(instanceKey) in alive")
	public void setConnectionPoolDataSource(ConnectionPoolDataSource v) {
		try {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.writeLock().lock();
			assertInitializationAllowed();
			if (dataSourceName != null) {
				throw new IllegalStateException("Cannot set the DataSource, if JNDI is used.");
			}
			if (cpds != null) {
				throw new IllegalStateException("The CPDS has already been set. It cannot be altered.");
			}
			cpds = v;
			instanceKey = InstanceKeyObjectFactory.registerNewInstance(this);
		} finally {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(dataSourceName) in alive", ensures = "pure(dataSourceName) in alive")
	public String getDataSourceName() {
		try {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
			return dataSourceName;
		} finally {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(cpds) * full(dataSourceName) * share(instanceKey) in alive", ensures = "pure(cpds) * full(dataSourceName) * share(instanceKey) in alive")
	public void setDataSourceName(String v) {
		try {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.writeLock().lock();
			assertInitializationAllowed();
			if (cpds != null) {
				throw new IllegalStateException("Cannot set the JNDI name for the DataSource, if already "
						+ "set using setConnectionPoolDataSource.");
			}
			if (dataSourceName != null) {
				throw new IllegalStateException("The DataSourceName has already been set. " + "It cannot be altered.");
			}
			this.dataSourceName = v;
			instanceKey = InstanceKeyObjectFactory.registerNewInstance(this);
		} finally {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(defaultAutoCommit) in alive", ensures = "pure(defaultAutoCommit) in alive")
	public boolean isDefaultAutoCommit() {
		try {
			defaultAutoCommitLock.readLock().lock();
			return defaultAutoCommit;
		} finally {
			defaultAutoCommitLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(defaultAutoCommit) in alive", ensures = "full(defaultAutoCommit) in alive")
	public void setDefaultAutoCommit(boolean v) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		defaultAutoCommitLock.writeLock().lock();
		this.defaultAutoCommit = v;
		defaultAutoCommitLock.writeLock().unlock();
	}
	@Perm(requires = "pure(defaultReadOnly) in alive", ensures = "pure(defaultReadOnly) in alive")
	public boolean isDefaultReadOnly() {
		try {
			defaultReadOnlyLock.readLock().lock();
			return defaultReadOnly;
		} finally {
			defaultReadOnlyLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(defaultReadOnly) in alive", ensures = "full(defaultReadOnly) in alive")
	public void setDefaultReadOnly(boolean v) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		defaultReadOnlyLock.writeLock().lock();
		this.defaultReadOnly = v;
		defaultReadOnlyLock.writeLock().unlock();
	}
	@Perm(requires = "pure(defaultTransactionIsolation) in alive", ensures = "pure(defaultTransactionIsolation) in alive")
	public int getDefaultTransactionIsolation() {
		try {
			defaultTransactionIsolationLock.readLock().lock();
			return defaultTransactionIsolation;
		} finally {
			defaultTransactionIsolationLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(defaultTransactionIsolation) in alive", ensures = "full(defaultTransactionIsolation) in alive")
	public void setDefaultTransactionIsolation(int v) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		switch (v) {
			case Connection.TRANSACTION_NONE :
			case Connection.TRANSACTION_READ_COMMITTED :
			case Connection.TRANSACTION_READ_UNCOMMITTED :
			case Connection.TRANSACTION_REPEATABLE_READ :
			case Connection.TRANSACTION_SERIALIZABLE :
				break;
			default :
				throw new IllegalArgumentException(BAD_TRANSACTION_ISOLATION);
		}
		defaultTransactionIsolationLock.writeLock().lock();
		this.defaultTransactionIsolation = v;
		defaultTransactionIsolationLock.writeLock().unlock();
	}
	@Perm(requires = "pure(description) in alive", ensures = "pure(description) in alive")
	public String getDescription() {
		try {
			descriptionLock.readLock().lock();
			return description;
		} finally {
			descriptionLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(description) in alive", ensures = "full(description) in alive")
	public void setDescription(String v) {
		descriptionLock.writeLock().lock();
		this.description = v;
		descriptionLock.writeLock().unlock();
	}
	@Perm(requires = "pure(jndiEnvironment) in alive", ensures = "pure(jndiEnvironment) in alive")
	public String getJndiEnvironment(String key) {
		String value = null;
		jndiEnvironmentLock.readLock().lock();
		if (jndiEnvironment != null) {
			value = jndiEnvironment.getProperty(key);
		}
		jndiEnvironmentLock.readLock().unlock();
		return value;
	}
	@Perm(requires = "unique(jndiEnvironment) in alive", ensures = "unique(jndiEnvironment) in alive")
	public void setJndiEnvironment(String key, String value) {
		jndiEnvironmentLock.writeLock().lock();
		if (jndiEnvironment == null) {
			jndiEnvironment = new Properties();
		}
		jndiEnvironment.setProperty(key, value);
		jndiEnvironmentLock.writeLock().unlock();
	}
	@Perm(requires = "pure(loginTimeout) in alive", ensures = "pure(loginTimeout) in alive")
	public int getLoginTimeout() {
		try {
			loginTimeoutLock.readLock().lock();
			return loginTimeout;
		} finally {
			loginTimeoutLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(loginTimeout) in alive", ensures = "full(loginTimeout) in alive")
	public void setLoginTimeout(int v) {
		loginTimeoutLock.writeLock().lock();
		this.loginTimeout = v;
		loginTimeoutLock.writeLock().unlock();
	}
	@Perm(requires = "unique(logWriter) in alive", ensures = "unique(logWriter) in alive")
	public PrintWriter getLogWriter() {
		try {
			logWriterLock.writeLock().lock();
			if (logWriter == null) {
				logWriter = new PrintWriter(System.out);
			}
			return logWriter;
		} finally {
			logWriterLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(logWriter) in alive", ensures = "share(logWriter) in alive")
	public void setLogWriter(PrintWriter v) {
		logWriterLock.writeLock().lock();
		this.logWriter = v;
		logWriterLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public final boolean isTestOnBorrow() {
		return getTestOnBorrow();
	}
	@Perm(requires = "pure(_testOnBorrow) in alive", ensures = "pure(_testOnBorrow) in alive")
	public boolean getTestOnBorrow() {
		try {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().lock();
			return _testOnBorrow;
		} finally {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(_testOnBorrow) * share(testPositionSet) in alive", ensures = "share(_testOnBorrow) * share(testPositionSet) in alive")
	public void setTestOnBorrow(boolean testOnBorrow) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().lock();
		_testOnBorrow = testOnBorrow;
		testPositionSet = true;
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public final boolean isTestOnReturn() {
		return getTestOnReturn();
	}
	@Perm(requires = "pure(_testOnReturn) in alive", ensures = "pure(_testOnReturn) in alive")
	public boolean getTestOnReturn() {
		try {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().lock();
			return _testOnReturn;
		} finally {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(_testOnReturn) * share(testPositionSet) in alive", ensures = "full(_testOnReturn) * share(testPositionSet) in alive")
	public void setTestOnReturn(boolean testOnReturn) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().lock();
		_testOnReturn = testOnReturn;
		testPositionSet = true;
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().unlock();
	}
	@Perm(requires = "pure(_timeBetweenEvictionRunsMillis) in alive", ensures = "pure(_timeBetweenEvictionRunsMillis) in alive")
	public int getTimeBetweenEvictionRunsMillis() {
		try {
			_timeBetweenEvictionRunsMillisLock.readLock().lock();
			return _timeBetweenEvictionRunsMillis;
		} finally {
			_timeBetweenEvictionRunsMillisLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(_timeBetweenEvictionRunsMillis) in alive", ensures = "full(_timeBetweenEvictionRunsMillis) in alive")
	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_timeBetweenEvictionRunsMillisLock.writeLock().lock();
		_timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		_timeBetweenEvictionRunsMillisLock.writeLock().unlock();
	}
	@Perm(requires = "pure(_numTestsPerEvictionRun) in alive", ensures = "pure(_numTestsPerEvictionRun) in alive")
	public int getNumTestsPerEvictionRun() {
		try {
			_numTestsPerEvictionRunLock.readLock().lock();
			return _numTestsPerEvictionRun;
		} finally {
			_numTestsPerEvictionRunLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(_numTestsPerEvictionRun) in alive", ensures = "full(_numTestsPerEvictionRun) in alive")
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_numTestsPerEvictionRunLock.writeLock().lock();
		_numTestsPerEvictionRun = numTestsPerEvictionRun;
		_numTestsPerEvictionRunLock.writeLock().unlock();
	}
	@Perm(requires = "pure(_minEvictableIdleTimeMillis) in alive", ensures = "pure(_minEvictableIdleTimeMillis) in alive")
	public int getMinEvictableIdleTimeMillis() {
		try {
			_minEvictableIdleTimeMillisLock.readLock().lock();
			return _minEvictableIdleTimeMillis;
		} finally {
			_minEvictableIdleTimeMillisLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(_minEvictableIdleTimeMillis) in alive", ensures = "full(_minEvictableIdleTimeMillis) in alive")
	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_minEvictableIdleTimeMillisLock.writeLock().lock();
		_minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		_minEvictableIdleTimeMillisLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public final boolean isTestWhileIdle() {
		return getTestWhileIdle();
	}
	@Perm(requires = "pure(_testWhileIdle) in alive", ensures = "pure(_testWhileIdle) in alive")
	public boolean getTestWhileIdle() {
		try {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().lock();
			return _testWhileIdle;
		} finally {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(_testWhileIdle) * share(testPositionSet) in alive", ensures = "full(_testWhileIdle) * share(testPositionSet) in alive")
	public void setTestWhileIdle(boolean testWhileIdle) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().lock();
		_testWhileIdle = testWhileIdle;
		testPositionSet = true;
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().unlock();
	}
	@Perm(requires = "pure(validationQuery) in alive", ensures = "pure(validationQuery) in alive")
	public String getValidationQuery() {
		try {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().lock();
			return (this.validationQuery);
		} finally {
			_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(validationQuery) * share(testPositionSet) in alive", ensures = "full(validationQuery) * share(testPositionSet) in alive")
	public void setValidationQuery(String validationQuery) {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		assertInitializationAllowed();
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().lock();
		this.validationQuery = validationQuery;
		if (!testPositionSet) {
			setTestOnBorrow(true);
		}
		_testOnBorrow__testOnReturn__testWhileIdle_testPositionSet_validationQueryLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Connection getConnection() throws SQLException {
		return getConnection(null, null);
	}
	@Perm(requires = "pure(instanceKey) * full(getConnectionCalled) in alive", ensures = "pure(instanceKey) * full(getConnectionCalled) in alive")
	public Connection getConnection(String username, String password) throws SQLException {
		try {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.writeLock().lock();
			if (instanceKey == null) {
				throw new SQLException("Must set the ConnectionPoolDataSource "
						+ "through setDataSourceName or setConnectionPoolDataSource"
						+ " before calling getConnection.");
			}
			getConnectionCalled = true;
		} finally {
			cpds_dataSourceName_getConnectionCalled_instanceKeyLock.writeLock().unlock();
		}
		PooledConnectionAndInfo info = null;
		try {
			info = getPooledConnectionAndInfo(username, password);
		} catch (NoSuchElementException e) {
			closeDueToException(info);
			throw new SQLNestedException("Cannot borrow connection from pool", e);
		} catch (RuntimeException e) {
			closeDueToException(info);
			throw e;
		} catch (SQLException e) {
			closeDueToException(info);
			throw e;
		} catch (Exception e) {
			closeDueToException(info);
			throw new SQLNestedException("Cannot borrow connection from pool", e);
		}
		if (!(null == password ? null == info.getPassword() : password.equals(info.getPassword()))) {
			closeDueToException(info);
			throw new SQLException("Given password did not match password used" + " to create the PooledConnection.");
		}
		Connection con = info.getPooledConnection().getConnection();
		setupDefaults(con, username);
		con.clearWarnings();
		return con;
	}
	protected abstract PooledConnectionAndInfo getPooledConnectionAndInfo(String username, String password)
			throws SQLException;
	protected abstract void setupDefaults(Connection con, String username) throws SQLException;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private void closeDueToException(PooledConnectionAndInfo info) {
		if (info != null) {
			try {
				info.getPooledConnection().getConnection().close();
			} catch (Exception e) {
				logWriterLock.writeLock().lock();
				getLogWriter().println("[ERROR] Could not return connection to " + "pool during exception handling. "
						+ e.getMessage());
				logWriterLock.writeLock().unlock();
			}
		}
	}
	@Perm(requires = "pure(cpds) * pure(jndiEnvironment) * pure(dataSourceName) in alive", ensures = "pure(cpds) * pure(jndiEnvironment) * pure(dataSourceName) in alive")
	protected ConnectionPoolDataSource testCPDS(String username, String password) throws NamingException, SQLException {
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		ConnectionPoolDataSource cpds = this.cpds;
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		if (cpds == null) {
			Context ctx = null;
			jndiEnvironmentLock.readLock().lock();
			if (jndiEnvironment == null) {
				ctx = new InitialContext();
			} else {
				ctx = new InitialContext(jndiEnvironment);
			}
			jndiEnvironmentLock.readLock().unlock();
			try {
				cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
				Object ds = ctx.lookup(dataSourceName);
				if (ds instanceof ConnectionPoolDataSource) {
					cpds = (ConnectionPoolDataSource) ds;
				} else {
					throw new SQLException("Illegal configuration: " + "DataSource " + dataSourceName + " ("
							+ ds.getClass().getName() + ")" + " doesn't implement javax.sql.ConnectionPoolDataSource");
				}
			} finally {
				cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
			}
		}
		PooledConnection conn = null;
		try {
			if (username != null) {
				conn = cpds.getPooledConnection(username, password);
			} else {
				conn = cpds.getPooledConnection();
			}
			if (conn == null) {
				throw new SQLException("Cannot connect using the supplied username/password");
			}
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return cpds;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected byte whenExhaustedAction(int maxActive, int maxWait) {
		byte whenExhausted = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
		if (maxActive <= 0) {
			whenExhausted = GenericObjectPool.WHEN_EXHAUSTED_GROW;
		} else if (maxWait == 0) {
			whenExhausted = GenericObjectPool.WHEN_EXHAUSTED_FAIL;
		}
		return whenExhausted;
	}
	@Perm(requires = "pure(instanceKey) in alive", ensures = "pure(instanceKey) in alive")
	public Reference getReference() throws NamingException {
		Reference ref = new Reference(getClass().getName(), InstanceKeyObjectFactory.class.getName(), null);
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().lock();
		ref.add(new StringRefAddr("instanceKey", instanceKey));
		cpds_dataSourceName_getConnectionCalled_instanceKeyLock.readLock().unlock();
		return ref;
	}
}
