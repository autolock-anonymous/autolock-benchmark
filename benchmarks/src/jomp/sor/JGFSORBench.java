package jomp.sor.withlock;
import jomp.jgfutil.*;
import jomp.jgfutil.JGFInstrumentor;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSORBench extends SOR implements JGFSection2 {
  public static ReentrantReadWriteLock GtotalLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock G_R_datasizes_lastElement_lastElementcopy_sizeLock=new ReentrantReadWriteLock();
  private int size;
  private int datasizes[]={1000,1500,2000};
  private static final int JACOBI_NUM_ITER=100;
  private static final long RANDOM_SEED=10101010;
  int lastElement;
  int lastElementcopy;
  Random R;
  double G[][];
  @Perm(requires="share(size) in alive",ensures="share(size) in alive") public void JGFsetsize(  int size){
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().lock();
    this.size=size;
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().unlock();
  }
  @Perm(requires="unique(R) in alive",ensures="unique(R) in alive") public void JGFinitialise(){
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().lock();
    R=new Random(RANDOM_SEED);
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().unlock();
  }
  @Perm(requires="share(lastElement) * pure(datasizes) * pure(size) * share(lastElementcopy) * share(G) * pure(R) in alive",ensures="share(lastElement) * pure(datasizes) * pure(size) * share(lastElementcopy) * share(G) * pure(R) in alive") public void JGFkernel(){
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().lock();
    lastElement=datasizes[size];
    lastElementcopy=datasizes[size];
    G=RandomMatrix(lastElement,lastElementcopy,R);
    SORrun(JACOBI_NUM_ITER,G,1.2);
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private static double[][] RandomMatrix(  int M,  int N,  java.util.Random R){
    double A[][]=new double[M][N];
    for (int i=0; i < N; i++)     for (int j=0; j < N; j++) {
      A[i][j]=R.nextDouble() * 1e-6;
    }
    return A;
  }
  @Perm(requires="pure(Gtotal) * pure(size) in alive",ensures="pure(Gtotal) * pure(size) in alive") public void JGFvalidate(){
    double refval[]={0.498574406322512,1.1234778980135105,1.9954895063582696};
    GtotalLock.readLock().lock();
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.readLock().lock();
    double dev=Math.abs(Gtotal - refval[size]);
    if (dev > 1.0e-12) {
      System.out.println("Validation failed");
      System.out.println("Gtotal = " + Gtotal + "  "+ dev+ "  "+ size);
    }
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.readLock().unlock();
    GtotalLock.readLock().unlock();
  }
  @Perm(requires="unique(G) * unique(datasizes) in alive",ensures="unique(G) * unique(datasizes) in alive") public void JGFtidyup(){
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().lock();
    G=null;
    datasizes=null;
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().unlock();
    System.gc();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFrun(  int size){
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().lock();
    JGFsetsize(size);
    JGFinitialise();
    JGFkernel();
    JGFvalidate();
    JGFtidyup();
    G_R_datasizes_lastElement_lastElementcopy_sizeLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getG_R_datasizes_lastElement_lastElementcopy_sizeLock(){
    Cloner cloner=new Cloner();
    G_R_datasizes_lastElement_lastElementcopy_sizeLock=cloner.deepClone(G_R_datasizes_lastElement_lastElementcopy_sizeLock);
    return G_R_datasizes_lastElement_lastElementcopy_sizeLock;
  }
  public Random getR(){
    Cloner cloner=new Cloner();
    R=cloner.deepClone(R);
    return R;
  }
  public static ReentrantReadWriteLock getGtotalLock(){
    Cloner cloner=new Cloner();
    GtotalLock=cloner.deepClone(GtotalLock);
    return GtotalLock;
  }
}
