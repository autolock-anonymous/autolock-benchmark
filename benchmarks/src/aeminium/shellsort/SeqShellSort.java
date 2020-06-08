package aeminium.shellsort.withlock;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqShellSort {
  public static ReentrantReadWriteLock dataLock=new ReentrantReadWriteLock();
  public static ReentrantReadWriteLock DEFAULT_SIZELock=new ReentrantReadWriteLock();
  public static Integer[] data;
  public static int DEFAULT_SIZE=10000;
  @Perm(requires="unique(data) * none(DEFAULT_SIZE) in alive",ensures="unique(data) * none(DEFAULT_SIZE) in alive") SeqShellSort(){
    dataLock.writeLock().lock();
    data=new Integer[DEFAULT_SIZE];
    dataLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static Integer[] InitializeColl(  Integer[] data){
    Random randGen=new Random();
    for (Integer i=0; i < data.length; i++) {
      data[i]=(randGen.nextInt());
    }
    return data;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void displayArray(  Integer[] data){
    for (int i=0; i < data.length; i++) {
      System.out.println("" + data[i]);
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void Sort(  Integer[] data,  Integer[] gaps){
    for (    Integer gap : gaps) {
      for (Integer i=gap; i < data.length; i++) {
        Integer temp=data[i];
        Integer j=i;
        for (; j >= gap && data[j - gap] > temp; j-=gap) {
          data[j]=data[j - gap];
        }
        data[j]=temp;
      }
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static boolean isSorted(  Integer[] data){
    for (Integer i=1; i < data.length; i++) {
      if (data[i - 1] > data[i])       return false;
    }
    return true;
  }
  public static ReentrantReadWriteLock getDEFAULT_SIZELock(){
    Cloner cloner=new Cloner();
    DEFAULT_SIZELock=cloner.deepClone(DEFAULT_SIZELock);
    Cloner cloner=new Cloner();
    DEFAULT_SIZELock=cloner.deepClone(DEFAULT_SIZELock);
    return DEFAULT_SIZELock;
  }
  public static ReentrantReadWriteLock getDataLock(){
    Cloner cloner=new Cloner();
    dataLock=cloner.deepClone(dataLock);
    Cloner cloner=new Cloner();
    dataLock=cloner.deepClone(dataLock);
    return dataLock;
  }
}
class Client {
  public static ReentrantReadWriteLock data_gapsLock=new ReentrantReadWriteLock();
  public static Integer[] gaps=new Integer[]{701,301,132,57,23,10,4,1};
  @Perm(requires="share(data) * immutable(gaps) in alive",ensures="share(data) * immutable(gaps) in alive") public static void main(  String[] args) throws Exception {
    long start=System.nanoTime();
    data_gapsLock.writeLock().lock();
    SeqShellSort.InitializeColl(SeqShellSort.data);
    SeqShellSort.displayArray(SeqShellSort.data);
    if (!SeqShellSort.isSorted(SeqShellSort.data)) {
      SeqShellSort.Sort(SeqShellSort.data,Client.gaps);
    }
    data_gapsLock.writeLock().unlock();
    System.out.println("Sorted Array");
    long end=System.nanoTime();
    long elapsedTime=end - start;
    double ms=(double)elapsedTime / 1000000.0;
    System.out.println(" Milli Seconds Time = " + ms);
  }
  public static ReentrantReadWriteLock getData_gapsLock(){
    Cloner cloner=new Cloner();
    data_gapsLock=cloner.deepClone(data_gapsLock);
    Cloner cloner=new Cloner();
    data_gapsLock=cloner.deepClone(data_gapsLock);
    return data_gapsLock;
  }
}
