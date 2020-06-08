package sharedpooldatasource.entity;
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
/** 
 * A {*link PoolableObjectFactory} that creates {*link PoolableConnection}s.
 * @author John D. McNally
 * @version $Revision: 1.6 $ $Date: 2004/03/07 11:19:25 $
 */
class KeyedCPDSConnectionFactory implements KeyedPoolableObjectFactory, ConnectionEventListener {
  private static final String NO_KEY_MESSAGE="close() was called on a Connection, but " + "I have no record of the underlying PooledConnection.";
  protected ConnectionPoolDataSource _cpds=null;
  protected String _validationQuery=null;
  protected KeyedObjectPool _pool=null;
  private Map validatingMap=new HashMap();
  private WeakHashMap pcMap=new WeakHashMap();
  /** 
 * Create a new <tt>KeyedPoolableConnectionFactory</tt>.
 * @param cpds the ConnectionPoolDataSource from which to obtain PooledConnection's
 * @param pool the {*link ObjectPool} in which to pool those {*link Connection}s
 * @param validationQuery a query to use to {*link #validateObject validate} {*link Connection}s.  Should return at least one row. May be <tt>null</tt>
 */
  public KeyedCPDSConnectionFactory(  ConnectionPoolDataSource cpds,  KeyedObjectPool pool,  String validationQuery){
    _cpds=cpds;
    _pool=pool;
    _pool.setFactory(this);
    _validationQuery=validationQuery;
  }
  synchronized public void setCPDS(  ConnectionPoolDataSource cpds){
    _cpds=cpds;
  }
  /** 
 * Sets the query I use to {*link #validateObject validate} {*link Connection}s. Should return at least one row. May be <tt>null</tt>
 * @param validationQuery a query to use to {*link #validateObject validate} {*link Connection}s.
 */
  synchronized public void setValidationQuery(  String validationQuery){
    _validationQuery=validationQuery;
  }
  /** 
 * Sets the {*link ObjectPool} in which to pool {*link Connection}s.
 * @param pool the {*link ObjectPool} in which to pool those {*link Connection}s
 */
  synchronized public void setPool(  KeyedObjectPool pool) throws SQLException {
    if (null != _pool && pool != _pool) {
      try {
        _pool.close();
      }
 catch (      RuntimeException e) {
        throw e;
      }
catch (      Exception e) {
        throw new SQLNestedException("Cannot set the pool on this factory",e);
      }
    }
    _pool=pool;
  }
  public KeyedObjectPool getPool(){
    return _pool;
  }
  /** 
 * @param key
 * @throws SQLException if the connection could not be created.
 * @see KeyedPoolableObjectFactory#makeObject(Object)
 */
  public synchronized Object makeObject(  Object key) throws Exception {
    Object obj=null;
    UserPassKey upkey=(UserPassKey)key;
    PooledConnection pc=null;
    String username=upkey.getUsername();
    String password=upkey.getPassword();
    if (username == null) {
      pc=_cpds.getPooledConnection();
    }
 else {
      pc=_cpds.getPooledConnection(username,password);
    }
    pc.addConnectionEventListener(this);
    obj=new PooledConnectionAndInfo(pc,username,password);
    pcMap.put(pc,obj);
    return obj;
  }
  public void destroyObject(  Object key,  Object obj) throws Exception {
    if (obj instanceof PooledConnectionAndInfo) {
      PooledConnection pc=((PooledConnectionAndInfo)obj).getPooledConnection();
      pcMap.remove(pc);
      pc.close();
    }
  }
  public boolean validateObject(  Object key,  Object obj){
    boolean valid=false;
    if (obj instanceof PooledConnectionAndInfo) {
      PooledConnection pconn=((PooledConnectionAndInfo)obj).getPooledConnection();
      String query=_validationQuery;
      if (null != query) {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rset=null;
        validatingMap.put(pconn,null);
        try {
          conn=pconn.getConnection();
          stmt=conn.createStatement();
          rset=stmt.executeQuery(query);
          if (rset.next()) {
            valid=true;
          }
 else {
            valid=false;
          }
        }
 catch (        Exception e) {
          valid=false;
        }
 finally {
          try {
            rset.close();
          }
 catch (          Throwable t) {
          }
          try {
            stmt.close();
          }
 catch (          Throwable t) {
          }
          try {
            conn.close();
          }
 catch (          Throwable t) {
          }
          validatingMap.remove(pconn);
        }
      }
 else {
        valid=true;
      }
    }
 else {
      valid=false;
    }
    return valid;
  }
  public void passivateObject(  Object key,  Object obj){
  }
  public void activateObject(  Object key,  Object obj){
  }
  /** 
 * This will be called if the Connection returned by the getConnection method came from a PooledConnection, and the user calls the close() method of this connection object. What we need to do here is to release this PooledConnection from our pool...
 */
  public void connectionClosed(  ConnectionEvent event){
    PooledConnection pc=(PooledConnection)event.getSource();
    if (!validatingMap.containsKey(pc)) {
      PooledConnectionAndInfo info=(PooledConnectionAndInfo)pcMap.get(pc);
      if (info == null) {
        throw new IllegalStateException(NO_KEY_MESSAGE);
      }
      try {
        _pool.returnObject(info.getUserPassKey(),info);
      }
 catch (      Exception e) {
        System.err.println("CLOSING DOWN CONNECTION AS IT COULD " + "NOT BE RETURNED TO THE POOL");
        try {
          destroyObject(info.getUserPassKey(),info);
        }
 catch (        Exception e2) {
          System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
          e2.printStackTrace();
        }
      }
    }
  }
  /** 
 * If a fatal error occurs, close the underlying physical connection so as not to be returned in the future
 */
  public void connectionErrorOccurred(  ConnectionEvent event){
    PooledConnection pc=(PooledConnection)event.getSource();
    try {
      if (null != event.getSQLException()) {
        System.err.println("CLOSING DOWN CONNECTION DUE TO INTERNAL ERROR (" + event.getSQLException() + ")");
      }
      pc.removeConnectionEventListener(this);
    }
 catch (    Exception ignore) {
    }
    PooledConnectionAndInfo info=(PooledConnectionAndInfo)pcMap.get(pc);
    if (info == null) {
      throw new IllegalStateException(NO_KEY_MESSAGE);
    }
    try {
      destroyObject(info.getUserPassKey(),info);
    }
 catch (    Exception e) {
      System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
      e.printStackTrace();
    }
  }
  public KeyedObjectPool get_pool(){
    Cloner cloner=new Cloner();
    _pool=cloner.deepClone(_pool);
    return _pool;
  }
  public Map getValidatingMap(){
    Cloner cloner=new Cloner();
    validatingMap=cloner.deepClone(validatingMap);
    return validatingMap;
  }
  public WeakHashMap getPcMap(){
    Cloner cloner=new Cloner();
    pcMap=cloner.deepClone(pcMap);
    return pcMap;
  }
  public String get_validationQuery(){
    Cloner cloner=new Cloner();
    _validationQuery=cloner.deepClone(_validationQuery);
    return _validationQuery;
  }
  public ConnectionPoolDataSource get_cpds(){
    Cloner cloner=new Cloner();
    _cpds=cloner.deepClone(_cpds);
    return _cpds;
  }
  public static String getNO_KEY_MESSAGE(){
    Cloner cloner=new Cloner();
    NO_KEY_MESSAGE=cloner.deepClone(NO_KEY_MESSAGE);
    return NO_KEY_MESSAGE;
  }
}
