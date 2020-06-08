package peruserpooldatasource.entity;
import java.io.Serializable;
/** 
 * @version $Revision: 1.5 $ $Date: 2004/02/28 12:18:17 $
 */
class PoolKey implements Serializable {
  private String datasourceName;
  private String username;
  PoolKey(  String datasourceName,  String username){
    this.datasourceName=datasourceName;
    this.username=username;
  }
  public boolean equals(  Object obj){
    if (obj instanceof PoolKey) {
      PoolKey pk=(PoolKey)obj;
      return (null == datasourceName ? null == pk.datasourceName : datasourceName.equals(pk.datasourceName)) && (null == username ? null == pk.username : username.equals(pk.username));
    }
 else {
      return false;
    }
  }
  public int hashCode(){
    int h=0;
    if (datasourceName != null) {
      h+=datasourceName.hashCode();
    }
    if (username != null) {
      h=29 * h + username.hashCode();
    }
    return h;
  }
  public String toString(){
    StringBuffer sb=new StringBuffer(50);
    sb.append("PoolKey(");
    sb.append(username).append(", ").append(datasourceName);
    sb.append(')');
    return sb.toString();
  }
  public String getDatasourceName(){
    Cloner cloner=new Cloner();
    datasourceName=cloner.deepClone(datasourceName);
    return datasourceName;
  }
  public String getUsername(){
    Cloner cloner=new Cloner();
    username=cloner.deepClone(username);
    return username;
  }
}
