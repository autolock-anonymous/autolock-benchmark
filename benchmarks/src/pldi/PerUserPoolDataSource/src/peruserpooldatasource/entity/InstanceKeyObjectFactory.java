package peruserpooldatasource.entity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
/** 
 * A JNDI ObjectFactory which creates <code>SharedPoolDataSource</code>s or <code>PerUserPoolDataSource</code>s
 * @version $Revision: 1.9 $ $Date: 2004/02/28 21:51:59 $
 */
abstract class InstanceKeyObjectFactory implements ObjectFactory {
  private static Map instanceMap=new HashMap();
  synchronized static String registerNewInstance(  InstanceKeyDataSource ds){
    int max=0;
    Iterator i=instanceMap.keySet().iterator();
    while (i.hasNext()) {
      Object obj=i.next();
      if (obj instanceof String) {
        try {
          max=Math.max(max,Integer.valueOf((String)obj).intValue());
        }
 catch (        NumberFormatException e) {
        }
      }
    }
    String instanceKey=String.valueOf(max + 1);
    instanceMap.put(instanceKey,ds);
    return instanceKey;
  }
  static void removeInstance(  String key){
    instanceMap.remove(key);
  }
  /** 
 * Close all pools associated with this class.
 */
  public static void closeAll() throws Exception {
    Iterator instanceIterator=instanceMap.entrySet().iterator();
    while (instanceIterator.hasNext()) {
      ((InstanceKeyDataSource)((Map.Entry)instanceIterator.next()).getValue()).close();
    }
    instanceMap.clear();
  }
  /** 
 * implements ObjectFactory to create an instance of SharedPoolDataSource or PerUserPoolDataSource
 */
  public Object getObjectInstance(  Object refObj,  Name name,  Context context,  Hashtable env) throws IOException, ClassNotFoundException {
    Object obj=null;
    if (refObj instanceof Reference) {
      Reference ref=(Reference)refObj;
      if (isCorrectClass(ref.getClassName())) {
        RefAddr ra=ref.get("instanceKey");
        if (ra != null && ra.getContent() != null) {
          obj=instanceMap.get(ra.getContent());
        }
 else {
          String key=null;
          if (name != null) {
            key=name.toString();
            obj=instanceMap.get(key);
          }
          if (obj == null) {
            InstanceKeyDataSource ds=getNewInstance(ref);
            setCommonProperties(ref,ds);
            obj=ds;
            if (key != null) {
              instanceMap.put(key,ds);
            }
          }
        }
      }
    }
    return obj;
  }
  private void setCommonProperties(  Reference ref,  InstanceKeyDataSource ikds) throws IOException, ClassNotFoundException {
    RefAddr ra=ref.get("dataSourceName");
    if (ra != null && ra.getContent() != null) {
      ikds.setDataSourceName(ra.getContent().toString());
    }
    ra=ref.get("defaultAutoCommit");
    if (ra != null && ra.getContent() != null) {
      ikds.setDefaultAutoCommit(Boolean.valueOf(ra.getContent().toString()).booleanValue());
    }
    ra=ref.get("defaultReadOnly");
    if (ra != null && ra.getContent() != null) {
      ikds.setDefaultReadOnly(Boolean.valueOf(ra.getContent().toString()).booleanValue());
    }
    ra=ref.get("description");
    if (ra != null && ra.getContent() != null) {
      ikds.setDescription(ra.getContent().toString());
    }
    ra=ref.get("jndiEnvironment");
    if (ra != null && ra.getContent() != null) {
      byte[] serialized=(byte[])ra.getContent();
      ikds.jndiEnvironment=(Properties)deserialize(serialized);
    }
    ra=ref.get("loginTimeout");
    if (ra != null && ra.getContent() != null) {
      ikds.setLoginTimeout(Integer.parseInt(ra.getContent().toString()));
    }
    ra=ref.get("testOnBorrow");
    if (ra != null && ra.getContent() != null) {
      ikds.setTestOnBorrow(Boolean.getBoolean(ra.getContent().toString()));
    }
    ra=ref.get("testOnReturn");
    if (ra != null && ra.getContent() != null) {
      ikds.setTestOnReturn(Boolean.valueOf(ra.getContent().toString()).booleanValue());
    }
    ra=ref.get("timeBetweenEvictionRunsMillis");
    if (ra != null && ra.getContent() != null) {
      ikds.setTimeBetweenEvictionRunsMillis(Integer.parseInt(ra.getContent().toString()));
    }
    ra=ref.get("numTestsPerEvictionRun");
    if (ra != null && ra.getContent() != null) {
      ikds.setNumTestsPerEvictionRun(Integer.parseInt(ra.getContent().toString()));
    }
    ra=ref.get("minEvictableIdleTimeMillis");
    if (ra != null && ra.getContent() != null) {
      ikds.setMinEvictableIdleTimeMillis(Integer.parseInt(ra.getContent().toString()));
    }
    ra=ref.get("testWhileIdle");
    if (ra != null && ra.getContent() != null) {
      ikds.setTestWhileIdle(Boolean.valueOf(ra.getContent().toString()).booleanValue());
    }
    ra=ref.get("validationQuery");
    if (ra != null && ra.getContent() != null) {
      ikds.setValidationQuery(ra.getContent().toString());
    }
  }
  /** 
 * @return true if and only if className is the value returnedfrom getClass().getName().toString()
 */
  protected abstract boolean isCorrectClass(  String className);
  /** 
 * Creates an instance of the subclass and sets any properties contained in the Reference.
 */
  protected abstract InstanceKeyDataSource getNewInstance(  Reference ref) throws IOException, ClassNotFoundException ;
  /** 
 * used to set some properties saved within a Reference
 */
  protected static final Object deserialize(  byte[] data) throws IOException, ClassNotFoundException {
    ObjectInputStream in=null;
    try {
      in=new ObjectInputStream(new ByteArrayInputStream(data));
      return in.readObject();
    }
  finally {
      try {
        in.close();
      }
 catch (      IOException ex) {
      }
    }
  }
  public static Map getInstanceMap(){
    Cloner cloner=new Cloner();
    instanceMap=cloner.deepClone(instanceMap);
    return instanceMap;
  }
}
