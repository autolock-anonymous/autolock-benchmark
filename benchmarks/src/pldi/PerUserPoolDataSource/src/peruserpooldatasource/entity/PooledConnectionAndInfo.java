package peruserpooldatasource.entity;
import javax.sql.PooledConnection;
/** 
 * @version $Revision: 1.5 $ $Date: 2004/02/28 12:18:17 $
 */
final class PooledConnectionAndInfo {
  private final PooledConnection pooledConnection;
  private final String password;
  private final String username;
  private final UserPassKey upkey;
  PooledConnectionAndInfo(  PooledConnection pc,  String username,  String password){
    this.pooledConnection=pc;
    this.username=username;
    this.password=password;
    upkey=new UserPassKey(username,password);
  }
  final PooledConnection getPooledConnection(){
    Cloner cloner=new Cloner();
    pooledConnection=cloner.deepClone(pooledConnection);
    return pooledConnection;
  }
  final UserPassKey getUserPassKey(){
    return upkey;
  }
  /** 
 * Get the value of password.
 * @return value of password.
 */
  final String getPassword(){
    Cloner cloner=new Cloner();
    password=cloner.deepClone(password);
    return password;
  }
  /** 
 * Get the value of username.
 * @return value of username.
 */
  final String getUsername(){
    Cloner cloner=new Cloner();
    username=cloner.deepClone(username);
    return username;
  }
  public UserPassKey getUpkey(){
    Cloner cloner=new Cloner();
    upkey=cloner.deepClone(upkey);
    return upkey;
  }
}
