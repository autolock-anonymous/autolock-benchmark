package aeminium.integral.withlock;
import java.text.DecimalFormat;
import java.lang.Double;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqIntegral {
  public static ReentrantReadWriteLock area_x1_x2Lock=new ReentrantReadWriteLock();
  public static Double x1=0.0;
  public static Double x2=1.0;
  public static Double area;
  @Perm(requires="share(area) in alive",ensures="share(area) in alive") public static Double compute(  Double x1,  Double x2){
    Double EPSILON=0.000001;
    double diff;
    diff=x2 - x1;
    try {
      area_x1_x2Lock.writeLock().lock();
      if (diff < EPSILON) {
        double f1, f2, combinedf, avgf;
        f1=f(x1);
        f2=f(x2);
        combinedf=f1 + f2;
        avgf=combinedf / 2;
        area=avgf * (x2 - x1);
        return area;
      }
 else {
        final double middle=(x2 + x1) / 2;
        return compute(x1,middle) + compute(middle,x2);
      }
    }
  finally {
      area_x1_x2Lock.writeLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static Double f(  final Double x1){
    return x1 * x1;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void display(  Double area){
    System.out.println((new DecimalFormat("#.####")).format(area));
  }
  @Perm(requires="immutable(x1) * immutable(x2) * share(area) in alive",ensures="immutable(x1) * immutable(x2) * share(area) in alive") public static void main(  String[] args){
    SeqIntegral obj=new SeqIntegral();
    long startTime=System.nanoTime();
    area_x1_x2Lock.writeLock().lock();
    compute(x1,x2);
    display(area);
    area_x1_x2Lock.writeLock().unlock();
    long elapsedTime=System.nanoTime() - startTime;
    double ms=(double)elapsedTime / 1000000.0;
    System.out.println(" Milli Seconds Time = " + ms);
  }
  public static Double getArea(){
    Cloner cloner=new Cloner();
    area=cloner.deepClone(area);
    Cloner cloner=new Cloner();
    area=cloner.deepClone(area);
    return area;
  }
  public static ReentrantReadWriteLock getArea_x1_x2Lock(){
    Cloner cloner=new Cloner();
    area_x1_x2Lock=cloner.deepClone(area_x1_x2Lock);
    Cloner cloner=new Cloner();
    area_x1_x2Lock=cloner.deepClone(area_x1_x2Lock);
    return area_x1_x2Lock;
  }
  public static Double getX1(){
    Cloner cloner=new Cloner();
    x1=cloner.deepClone(x1);
    Cloner cloner=new Cloner();
    x1=cloner.deepClone(x1);
    return x1;
  }
  public static Double getX2(){
    Cloner cloner=new Cloner();
    x2=cloner.deepClone(x2);
    Cloner cloner=new Cloner();
    x2=cloner.deepClone(x2);
    return x2;
  }
}
