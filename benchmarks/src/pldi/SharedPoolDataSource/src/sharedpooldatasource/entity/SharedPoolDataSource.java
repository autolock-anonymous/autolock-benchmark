package sharedpooldatasource.entity;
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
/** 
 * A pooling <code>DataSource</code> appropriate for deployment within J2EE environment.  There are many configuration options, most of which are defined in the parent class. All users (based on username) share a single  maximum number of Connections in this datasource.
 * @author John D. McNally
 * @version $Revision: 1.9 $ $Date: 2004/02/28 12:18:17 $
 */
public class SharedPoolDataSource extends InstanceKeyDataSource {
  private static final Map userKeys=new LRUMap(10);
  private int maxActive=GenericObjectPool.DEFAULT_MAX_ACTIVE;
  private int maxIdle=GenericObjectPool.DEFAULT_MAX_IDLE;
  private int maxWait=(int)Math.min((long)Integer.MAX_VALUE,GenericObjectPool.DEFAULT_MAX_WAIT);
  private KeyedObjectPool pool=null;
  /** 
 * Default no-arg constructor for Serialization
 */
  public SharedPoolDataSource(){
  }
  /** 
 * Close pool being maintained by this datasource.
 */
  public void close() throws Exception {
    pool.close();
    InstanceKeyObjectFactory.removeInstance(instanceKey);
  }
  /** 
 * The maximum number of active connections that can be allocated from this pool at the same time, or zero for no limit. The default is 0.
 */
  public int getMaxActive(){
    return (this.maxActive);
  }
  /** 
 * The maximum number of active connections that can be allocated from this pool at the same time, or zero for no limit. The default is 0.
 */
  public void setMaxActive(  int maxActive){
    assertInitializationAllowed();
    this.maxActive=maxActive;
  }
  /** 
 * The maximum number of active connections that can remain idle in the pool, without extra ones being released, or zero for no limit. The default is 0.
 */
  public int getMaxIdle(){
    return (this.maxIdle);
  }
  /** 
 * The maximum number of active connections that can remain idle in the pool, without extra ones being released, or zero for no limit. The default is 0.
 */
  public void setMaxIdle(  int maxIdle){
    assertInitializationAllowed();
    this.maxIdle=maxIdle;
  }
  /** 
 * The maximum number of milliseconds that the pool will wait (when there are no available connections) for a connection to be returned before throwing an exception, or -1 to wait indefinitely.  Will fail  immediately if value is 0. The default is -1.
 */
  public int getMaxWait(){
    return (this.maxWait);
  }
  /** 
 * The maximum number of milliseconds that the pool will wait (when there are no available connections) for a connection to be returned before throwing an exception, or -1 to wait indefinitely.  Will fail  immediately if value is 0. The default is -1.
 */
  public void setMaxWait(  int maxWait){
    assertInitializationAllowed();
    this.maxWait=maxWait;
  }
  /** 
 * Get the number of active connections in the pool.
 */
  public int getNumActive(){
    return (pool == null) ? 0 : pool.getNumActive();
  }
  /** 
 * Get the number of idle connections in the pool.
 */
  public int getNumIdle(){
    return (pool == null) ? 0 : pool.getNumIdle();
  }
  protected synchronized PooledConnectionAndInfo getPooledConnectionAndInfo(  String username,  String password) throws SQLException {
    if (pool == null) {
      try {
        registerPool(username,password);
      }
 catch (      NamingException e) {
        throw new SQLNestedException("RegisterPool failed",e);
      }
    }
    PooledConnectionAndInfo info=null;
    try {
      info=(PooledConnectionAndInfo)pool.borrowObject(getUserPassKey(username,password));
    }
 catch (    Exception e) {
      throw new SQLNestedException("Could not retrieve connection info from pool",e);
    }
    return info;
  }
  private UserPassKey getUserPassKey(  String username,  String password){
    UserPassKey key=(UserPassKey)userKeys.get(username);
    if (key == null) {
      key=new UserPassKey(username,password);
      userKeys.put(username,key);
    }
    return key;
  }
  private void registerPool(  String username,  String password) throws NamingException, SQLException {
    ConnectionPoolDataSource cpds=testCPDS(username,password);
    GenericKeyedObjectPool tmpPool=new GenericKeyedObjectPool(null);
    tmpPool.setMaxActive(getMaxActive());
    tmpPool.setMaxIdle(getMaxIdle());
    tmpPool.setMaxWait(getMaxWait());
    tmpPool.setWhenExhaustedAction(whenExhaustedAction(maxActive,maxWait));
    tmpPool.setTestOnBorrow(getTestOnBorrow());
    tmpPool.setTestOnReturn(getTestOnReturn());
    tmpPool.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
    tmpPool.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun());
    tmpPool.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
    tmpPool.setTestWhileIdle(getTestWhileIdle());
    pool=tmpPool;
    new KeyedCPDSConnectionFactory(cpds,pool,getValidationQuery());
  }
  protected void setupDefaults(  Connection con,  String username) throws SQLException {
    con.setAutoCommit(isDefaultAutoCommit());
    con.setReadOnly(isDefaultReadOnly());
    int defaultTransactionIsolation=getDefaultTransactionIsolation();
    if (defaultTransactionIsolation != UNKNOWN_TRANSACTIONISOLATION) {
      con.setTransactionIsolation(defaultTransactionIsolation);
    }
  }
  /** 
 * Supports Serialization interface.
 * @param in a <code>java.io.ObjectInputStream</code> value
 * @exception IOException if an error occurs
 * @exception ClassNotFoundException if an error occurs
 */
  private void readObject(  ObjectInputStream in) throws IOException, ClassNotFoundException {
    try {
      in.defaultReadObject();
      SharedPoolDataSource oldDS=(SharedPoolDataSource)new SharedPoolDataSourceFactory().getObjectInstance(getReference(),null,null,null);
      this.pool=oldDS.pool;
    }
 catch (    NamingException e) {
      throw new IOException("NamingException: " + e);
    }
  }
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }
  @Override public <T>T unwrap(  Class<T> iface) throws SQLException {
    return null;
  }
  @Override public boolean isWrapperFor(  Class<?> iface) throws SQLException {
    return false;
  }
  public static Map getUserKeys(){
    Cloner cloner=new Cloner();
    userKeys=cloner.deepClone(userKeys);
    return userKeys;
  }
  public KeyedObjectPool getPool(){
    Cloner cloner=new Cloner();
    pool=cloner.deepClone(pool);
    return pool;
  }
}
