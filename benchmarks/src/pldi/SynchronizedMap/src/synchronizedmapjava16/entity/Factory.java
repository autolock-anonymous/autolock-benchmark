package synchronizedmapjava16.entity;
public class Factory {
  public static Collections.SynchronizedMap createSyncMap(){
    return (Collections.SynchronizedMap)Collections.synchronizedMap(new Hashtable());
  }
}
