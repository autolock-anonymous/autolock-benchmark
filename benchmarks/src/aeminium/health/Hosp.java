package aeminium.health.withlock;
import java.util.ArrayList;
import java.util.List;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Hosp {
  public int personnel;
  public int free_personnel;
  List<Patient> waiting=new ArrayList<Patient>();
  List<Patient> assess=new ArrayList<Patient>();
  List<Patient> inside=new ArrayList<Patient>();
  List<Patient> realloc=new ArrayList<Patient>();
  public List<Patient> getInside(){
    Cloner cloner=new Cloner();
    inside=cloner.deepClone(inside);
    Cloner cloner=new Cloner();
    inside=cloner.deepClone(inside);
    return inside;
  }
  public List<Patient> getRealloc(){
    Cloner cloner=new Cloner();
    realloc=cloner.deepClone(realloc);
    Cloner cloner=new Cloner();
    realloc=cloner.deepClone(realloc);
    return realloc;
  }
  public List<Patient> getWaiting(){
    Cloner cloner=new Cloner();
    waiting=cloner.deepClone(waiting);
    Cloner cloner=new Cloner();
    waiting=cloner.deepClone(waiting);
    return waiting;
  }
  public List<Patient> getAssess(){
    Cloner cloner=new Cloner();
    assess=cloner.deepClone(assess);
    Cloner cloner=new Cloner();
    assess=cloner.deepClone(assess);
    return assess;
  }
}
