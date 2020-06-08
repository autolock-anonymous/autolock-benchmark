package aeminium.quicksort.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqQuickSort {
  public static ReentrantReadWriteLock original_arrayLock=new ReentrantReadWriteLock();
  static long[] original_array;
  @Perm(requires="pure(original_array) in alive",ensures="pure(original_array) in alive") public static void main(  String[] args){
    long start=System.nanoTime();
    int size=QuickSort.DEFAULT_SIZE;
    original_arrayLock.readLock().lock();
    ArrayHelper.generateRandomArray(original_array,size);
    SeqQuickSort.sort(original_array);
    System.out.println("Sorted: " + ArrayHelper.checkArray(original_array));
    original_arrayLock.readLock().unlock();
    long elapsedTime=System.nanoTime() - start;
    double ms=(double)elapsedTime / 1000000.0;
    System.out.println(" Milli Seconds Time = " + ms);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void sort(  long[] original_array){
    qsort_seq(original_array,0,original_array.length - 1);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void qsort_seq(  long[] data,  int left,  int right){
    int index=QuickSort.partition(data,left,right);
    if (left < index - 1)     qsort_seq(data,left,index - 1);
    if (index < right)     qsort_seq(data,index,right);
  }
  public static ReentrantReadWriteLock getOriginal_arrayLock(){
    Cloner cloner=new Cloner();
    original_arrayLock=cloner.deepClone(original_arrayLock);
    Cloner cloner=new Cloner();
    original_arrayLock=cloner.deepClone(original_arrayLock);
    return original_arrayLock;
  }
}
