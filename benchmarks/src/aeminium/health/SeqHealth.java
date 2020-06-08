package aeminium.health.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqHealth {
  public static ReentrantReadWriteLock sim_timeLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock childrenLock=new ReentrantReadWriteLock();
  public static ReentrantReadWriteLock sim_levelLock=new ReentrantReadWriteLock();
  @Perm(requires="none(sim_time) * immutable(sim_level) in alive",ensures="none(sim_time) * immutable(sim_level) in alive") public static void main(  String[] args){
    int size=Health.sim_time;
    if (args.length > 0) {
      size=Integer.parseInt(args[0]);
    }
    Village village=null;
    village=Health.allocateVillage(Health.sim_level,0,null);
    Village.displayVillageData(village);
    for (int i=0; i < size; i++) {
      simVillage(village);
    }
  }
  @Perm(requires="immutable(children) in alive",ensures="immutable(children) in alive") public static void simVillage(  Village village){
    for (    Village child : village.children) {
      simVillage(child);
    }
    village.tick();
  }
  public static ReentrantReadWriteLock getSim_levelLock(){
    Cloner cloner=new Cloner();
    sim_levelLock=cloner.deepClone(sim_levelLock);
    Cloner cloner=new Cloner();
    sim_levelLock=cloner.deepClone(sim_levelLock);
    return sim_levelLock;
  }
  public static ReentrantReadWriteLock getSim_timeLock(){
    Cloner cloner=new Cloner();
    sim_timeLock=cloner.deepClone(sim_timeLock);
    Cloner cloner=new Cloner();
    sim_timeLock=cloner.deepClone(sim_timeLock);
    return sim_timeLock;
  }
  public ReentrantReadWriteLock getChildrenLock(){
    Cloner cloner=new Cloner();
    childrenLock=cloner.deepClone(childrenLock);
    Cloner cloner=new Cloner();
    childrenLock=cloner.deepClone(childrenLock);
    return childrenLock;
  }
}
