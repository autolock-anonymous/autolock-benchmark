package jomp.search.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFInstrumentor {
  public ReentrantReadWriteLock dataLock=new ReentrantReadWriteLock();
  public static ReentrantReadWriteLock time_timersLock=new ReentrantReadWriteLock();
  private static Hashtable timers;
  private static Hashtable data;
static {
    timers=new Hashtable();
    data=new Hashtable();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void addTimer(  String name){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      System.out.println("JGFInstrumentor.addTimer: warning -  timer " + name + " already exists");
    }
 else {
      timers.put(name,new JGFTimer(name));
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void addTimer(  String name,  String opname){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      System.out.println("JGFInstrumentor.addTimer: warning -  timer " + name + " already exists");
    }
 else {
      timers.put(name,new JGFTimer(name,opname));
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void addTimer(  String name,  String opname,  int size){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      System.out.println("JGFInstrumentor.addTimer: warning -  timer " + name + " already exists");
    }
 else {
      timers.put(name,new JGFTimer(name,opname,size));
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void startTimer(  String name){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      ((JGFTimer)timers.get(name)).start();
    }
 else {
      System.out.println("JGFInstrumentor.startTimer: failed -  timer " + name + " does not exist");
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void stopTimer(  String name){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      ((JGFTimer)timers.get(name)).stop();
    }
 else {
      System.out.println("JGFInstrumentor.stopTimer: failed -  timer " + name + " does not exist");
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void addOpsToTimer(  String name,  double count){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      ((JGFTimer)timers.get(name)).addops(count);
    }
 else {
      System.out.println("JGFInstrumentor.addOpsToTimer: failed -  timer " + name + " does not exist");
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) * pure(time) in alive",ensures="share(timers) * pure(time) in alive") public static synchronized double readTimer(  String name){
    double time;
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      time=((JGFTimer)timers.get(name)).time;
    }
 else {
      System.out.println("JGFInstrumentor.readTimer: failed -  timer " + name + " does not exist");
      time=0.0;
    }
    time_timersLock.writeLock().unlock();
    return time;
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void resetTimer(  String name){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      ((JGFTimer)timers.get(name)).reset();
    }
 else {
      System.out.println("JGFInstrumentor.resetTimer: failed -  timer " + name + " does not exist");
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void printTimer(  String name){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      ((JGFTimer)timers.get(name)).print();
    }
 else {
      System.out.println("JGFInstrumentor.printTimer: failed -  timer " + name + " does not exist");
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(timers) in alive",ensures="share(timers) in alive") public static synchronized void printperfTimer(  String name){
    time_timersLock.writeLock().lock();
    if (timers.containsKey(name)) {
      ((JGFTimer)timers.get(name)).printperf();
    }
 else {
      System.out.println("JGFInstrumentor.printTimer: failed -  timer " + name + " does not exist");
    }
    time_timersLock.writeLock().unlock();
  }
  @Perm(requires="share(data) in alive",ensures="share(data) in alive") public static synchronized void storeData(  String name,  Object obj){
    dataLock.writeLock().lock();
    data.put(name,obj);
    dataLock.writeLock().unlock();
  }
  @Perm(requires="pure(data) in alive",ensures="pure(data) in alive") public static synchronized void retrieveData(  String name,  Object obj){
    dataLock.readLock().lock();
    obj=data.get(name);
    dataLock.readLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static synchronized void printHeader(  int section,  int size){
    String header, base;
    header="";
    base="Java Grande Forum Benchmark Suite - Version 2.0 - Section ";
switch (section) {
case 1:
      header=base + "1";
    break;
case 2:
switch (size) {
case 0:
    header=base + "2 - Size A";
  break;
case 1:
header=base + "2 - Size B";
break;
case 2:
header=base + "2 - Size C";
break;
}
break;
case 3:
switch (size) {
case 0:
header=base + "3 - Size A";
break;
case 1:
header=base + "3 - Size B";
break;
}
break;
}
System.out.println(header);
System.out.println("");
}
@Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(String argv[]){
Object obj=new Object();
dataLock.writeLock().lock();
JGFInstrumentor.storeData("hi",obj);
dataLock.writeLock().unlock();
time_timersLock.writeLock().lock();
JGFInstrumentor.startTimer("start");
time_timersLock.writeLock().unlock();
}
public ReentrantReadWriteLock getDataLock(){
Cloner cloner=new Cloner();
dataLock=cloner.deepClone(dataLock);
return dataLock;
}
public static Hashtable getData(){
Cloner cloner=new Cloner();
data=cloner.deepClone(data);
return data;
}
public static ReentrantReadWriteLock getTime_timersLock(){
Cloner cloner=new Cloner();
time_timersLock=cloner.deepClone(time_timersLock);
return time_timersLock;
}
public static Hashtable getTimers(){
Cloner cloner=new Cloner();
timers=cloner.deepClone(timers);
return timers;
}
}
