package jomp.montecarlo2.withlock;
import java.io.*;
import jomp.jgfutil.JGFInstrumentor;
import jomp.jgfutil.JGFSection3;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFMonteCarloBench extends CallAppDemo implements JGFSection3 {
  public static ReentrantReadWriteLock JGFavgExpectedReturnRateMCLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock sizeLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock datasizesLock=new ReentrantReadWriteLock();
  @Perm(requires="share(size) in alive",ensures="share(size) in alive") public void JGFsetsize(  int size){
    sizeLock.writeLock().lock();
    this.size=size;
    sizeLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFinitialise(){
    initialise();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFapplication(){
    presults();
    runiters();
  }
  @Perm(requires="pure(JGFavgExpectedReturnRateMC) * pure(size) in alive",ensures="pure(JGFavgExpectedReturnRateMC) * pure(size) in alive") public void JGFvalidate(){
    sizeLock.readLock().lock();
    JGFavgExpectedReturnRateMCLock.readLock().lock();
    System.out.println("" + AppDemo.JGFavgExpectedReturnRateMC + size);
    double refval[]={-0.0333976656762814,-0.03215796752868655};
    double dev=Math.abs(AppDemo.JGFavgExpectedReturnRateMC - refval[size]);
    if (dev > 1.0e-12) {
      System.out.println("Validation failed");
      System.out.println(" expectedReturnRate= " + AppDemo.JGFavgExpectedReturnRateMC + "  "+ dev+ "  "+ size);
    }
    JGFavgExpectedReturnRateMCLock.readLock().unlock();
    sizeLock.readLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFrun(  int size){
    sizeLock.writeLock().lock();
    JGFsetsize(size);
    JGFinitialise();
    JGFapplication();
    JGFvalidate();
    sizeLock.writeLock().unlock();
    datasizesLock.writeLock().lock();
    JGFtidyup();
    datasizesLock.writeLock().unlock();
  }
  @Perm(requires="unique(datasizes) in alive",ensures="unique(datasizes) in alive") public void JGFtidyup(){
    datasizesLock.writeLock().lock();
    datasizes=null;
    datasizesLock.writeLock().unlock();
    System.gc();
  }
  public static ReentrantReadWriteLock getJGFavgExpectedReturnRateMCLock(){
    Cloner cloner=new Cloner();
    JGFavgExpectedReturnRateMCLock=cloner.deepClone(JGFavgExpectedReturnRateMCLock);
    return JGFavgExpectedReturnRateMCLock;
  }
  public ReentrantReadWriteLock getSizeLock(){
    Cloner cloner=new Cloner();
    sizeLock=cloner.deepClone(sizeLock);
    return sizeLock;
  }
  public ReentrantReadWriteLock getDatasizesLock(){
    Cloner cloner=new Cloner();
    datasizesLock=cloner.deepClone(datasizesLock);
    return datasizesLock;
  }
}
