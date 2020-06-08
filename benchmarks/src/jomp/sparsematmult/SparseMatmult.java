package jomp.sparsematmult.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SparseMatmult {
  public static ReentrantReadWriteLock ytotalLock=new ReentrantReadWriteLock();
  public static double ytotal=0.0;
  @Perm(requires="full(ytotal) in alive",ensures="full(ytotal) in alive") public static void test(  double y[],  double val[],  int row[],  int col[],  double x[],  int NUM_ITERATIONS){
    int nz=val.length;
    JGFInstrumentor.startTimer("Section2:SparseMatmult:Kernel");
    for (int reps=0; reps < NUM_ITERATIONS; reps++) {
      for (int i=0; i < nz; i++) {
        y[row[i]]+=x[col[i]] * val[i];
      }
    }
    JGFInstrumentor.stopTimer("Section2:SparseMatmult:Kernel");
    for (int i=0; i < nz; i++) {
      ytotalLock.writeLock().lock();
      ytotal+=y[row[i]];
      ytotalLock.writeLock().unlock();
    }
  }
  public static ReentrantReadWriteLock getYtotalLock(){
    Cloner cloner=new Cloner();
    ytotalLock=cloner.deepClone(ytotalLock);
    return ytotalLock;
  }
}
