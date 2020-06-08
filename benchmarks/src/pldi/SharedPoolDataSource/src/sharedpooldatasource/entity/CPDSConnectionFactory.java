package sharedpooldatasource.entity;
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
class CPDSConnectionFactory implements PoolableObjectFactory, ConnectionEventListener {
  private static final String NO_KEY_MESSAGE="close() was called on a Connection, but " + "I have no record of the underlying PooledConnection.";
  protected ConnectionPoolDataSource _cpds=null;
  protected String _validationQuery=null;
  protected ObjectPool _pool=null;
  protected String _username=null;
  protected String _password=null;
  private Map validatingMap=new HashMap();
  private WeakHashMap pcMap=new WeakHashMap();
  /** 
 * Create a new <tt>PoolableConnectionFactory</tt>.
 * @param cpds the ConnectionPoolDataSource from which to obtain PooledConnection's
 * @param pool the {*link ObjectPool} in which to pool those {*link Connection}s
 * @param validationQuery a query to use to {*link #validateObject validate}{*link Connection}s. Should return at least one row.  May be <tt>null</tt>
 * @param username 
 * @param password
 */
  public CPDSConnectionFactory(  ConnectionPoolDataSource cpds,  ObjectPool pool,  String validationQuery,  String username,  String password){
    _cpds=cpds;
    _pool=pool;
    _pool.setFactory(this);
    _validationQuery=validationQuery;
    _username=username;
    _password=password;
  }
  public synchronized void setCPDS(  ConnectionPoolDataSource cpds){
    _cpds=cpds;
  }
  /** 
 * Sets the query I use to {*link #validateObject validate}  {*link Connection}s. Should return at least one row. May be <tt>null</tt>
 * @param validationQuery a query to use to {*link #validateObject validate} {*link Connection}s.
 */
  public synchronized void setValidationQuery(  String validationQuery){
    _validationQuery=validationQuery;
  }
  /** 
 * Sets the {*link ObjectPool} in which to pool {*link Connection}s.
 * @param pool the {*link ObjectPool} in which to pool those {*link Connection}s
 */
  public synchronized void setPool(  ObjectPool pool) throws SQLException {
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
  public ObjectPool getPool(){
    return _pool;
  }
  public synchronized Object makeObject(){
    Object obj;
    try {
      PooledConnection pc=null;
      if (_username == null) {
        pc=_cpds.getPooledConnection();
      }
 else {
        pc=_cpds.getPooledConnection(_username,_password);
      }
      pc.addConnectionEventListener(this);
      obj=new PooledConnectionAndInfo(pc,_username,_password);
      pcMap.put(pc,obj);
    }
 catch (    SQLException e) {
      throw new RuntimeException(e.getMessage());
    }
    return obj;
  }
  public void destroyObject(  Object obj) throws Exception {
    if (obj instanceof PooledConnectionAndInfo) {
      ((PooledConnectionAndInfo)obj).getPooledConnection().close();
    }
  }
  public boolean validateObject(  Object obj){
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
  public void passivateObject(  Object obj){
  }
  public void activateObject(  Object obj){
  }
  /** 
 * This will be called if the Connection returned by the getConnection method came from a PooledConnection, and the user calls the close() method of this connection object. What we need to do here is to release this PooledConnection from our pool...
 */
  public void connectionClosed(  ConnectionEvent event){
    PooledConnection pc=(PooledConnection)event.getSource();
    if (!validatingMap.containsKey(pc)) {
      Object info=pcMap.get(pc);
      if (info == null) {
        throw new IllegalStateException(NO_KEY_MESSAGE);
      }
      try {
        _pool.returnObject(info);
      }
 catch (      Exception e) {
        System.err.println("CLOSING DOWN CONNECTION AS IT COULD " + "NOT BE RETURNED TO THE POOL");
        try {
          destroyObject(info);
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
    Object info=pcMap.get(pc);
    if (info == null) {
      throw new IllegalStateException(NO_KEY_MESSAGE);
    }
    try {
      destroyObject(info);
    }
 catch (    Exception e) {
      System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
      e.printStackTrace();
    }
  }
  public ConnectionPoolDataSource get_cpds(){
    Cloner cloner=new Cloner();
    _cpds=cloner.deepClone(_cpds);
    return _cpds;
  }
  public WeakHashMap getPcMap(){
    Cloner cloner=new Cloner();
    pcMap=cloner.deepClone(pcMap);
    return pcMap;
  }
  public Map getValidatingMap(){
    Cloner cloner=new Cloner();
    validatingMap=cloner.deepClone(validatingMap);
    return validatingMap;
  }
  public static String getNO_KEY_MESSAGE(){
    Cloner cloner=new Cloner();
    NO_KEY_MESSAGE=cloner.deepClone(NO_KEY_MESSAGE);
    return NO_KEY_MESSAGE;
  }
  public ObjectPool get_pool(){
    Cloner cloner=new Cloner();
    _pool=cloner.deepClone(_pool);
    return _pool;
  }
  public String get_password(){
    Cloner cloner=new Cloner();
    _password=cloner.deepClone(_password);
    return _password;
  }
  public String get_validationQuery(){
    Cloner cloner=new Cloner();
    _validationQuery=cloner.deepClone(_validationQuery);
    return _validationQuery;
  }
  public String get_username(){
    Cloner cloner=new Cloner();
    _username=cloner.deepClone(_username);
    return _username;
  }
}
