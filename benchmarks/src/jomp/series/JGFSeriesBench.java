package jomp.series.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSeriesBench extends SeriesTest implements JGFSection2 {
  public ReentrantReadWriteLock TestArrayLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock array_rows_datasizes_sizeLock=new ReentrantReadWriteLock();
  private int size;
  private int datasizes[]={10000,100000,1000000};
  @Perm(requires="share(size) in alive",ensures="share(size) in alive") public void JGFsetsize(  int size){
    array_rows_datasizes_sizeLock.writeLock().lock();
    this.size=size;
    array_rows_datasizes_sizeLock.writeLock().unlock();
  }
  @Perm(requires="share(array_rows) * immutable(datasizes) * pure(size) in alive",ensures="share(array_rows) * immutable(datasizes) * pure(size) in alive") public void JGFinitialise(){
    array_rows_datasizes_sizeLock.readLock().lock();
    array_rows_datasizes_sizeLock.writeLock().lock();
    array_rows=datasizes[size];
    array_rows_datasizes_sizeLock.writeLock().unlock();
    array_rows_datasizes_sizeLock.readLock().unlock();
    buildTestData();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFkernel(){
    Do();
  }
  @Perm(requires="pure(TestArray) in alive",ensures="pure(TestArray) in alive") public void JGFvalidate(){
    double ref[][]={{2.8729524964837996,0.0},{1.1161046676147888,-1.8819691893398025},{0.34429060398168704,-1.1645642623320958},{0.15238898702519288,-0.8143461113044298}};
    for (int i=0; i < 4; i++) {
      for (int j=0; j < 2; j++) {
        TestArrayLock.readLock().lock();
        double error=Math.abs(TestArray[j][i] - ref[i][j]);
        if (error > 1.0e-12) {
          System.out.println("Validation failed for coefficient " + j + ","+ i);
          System.out.println("Computed value = " + TestArray[j][i]);
          System.out.println("Reference value = " + ref[i][j]);
        }
        TestArrayLock.readLock().unlock();
      }
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFtidyup(){
    freeTestData();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFrun(  int size){
    array_rows_datasizes_sizeLock.writeLock().lock();
    JGFsetsize(size);
    JGFinitialise();
    array_rows_datasizes_sizeLock.writeLock().unlock();
    JGFkernel();
    JGFvalidate();
    JGFtidyup();
  }
  public ReentrantReadWriteLock getTestArrayLock(){
    Cloner cloner=new Cloner();
    TestArrayLock=cloner.deepClone(TestArrayLock);
    return TestArrayLock;
  }
  public ReentrantReadWriteLock getArray_rows_datasizes_sizeLock(){
    Cloner cloner=new Cloner();
    array_rows_datasizes_sizeLock=cloner.deepClone(array_rows_datasizes_sizeLock);
    return array_rows_datasizes_sizeLock;
  }
}
