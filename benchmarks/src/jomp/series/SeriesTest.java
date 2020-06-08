package jomp.series.withlock;
import jomp.jgfutil.JGFInstrumentor;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class SeriesTest {
  public ReentrantReadWriteLock TestArray_array_rowsLock=new ReentrantReadWriteLock();
  int array_rows;
  double[][] TestArray;
  @Perm(requires="unique(TestArray) * pure(array_rows) in alive",ensures="unique(TestArray) * pure(array_rows) in alive") void buildTestData(){
    TestArray_array_rowsLock.writeLock().lock();
    TestArray=new double[2][array_rows];
    TestArray_array_rowsLock.writeLock().unlock();
  }
  @Perm(requires="share(TestArray) * pure(array_rows) in alive",ensures="share(TestArray) * pure(array_rows) in alive") void Do(){
    double omega;
    TestArray_array_rowsLock.writeLock().lock();
    TestArray[0][0]=TrapezoidIntegrate((double)0.0,(double)2.0,1000,(double)0.0,0) / (double)2.0;
    omega=(double)3.1415926535897932;
    for (int i=1; i < array_rows; i++) {
      TestArray[0][i]=TrapezoidIntegrate((double)0.0,(double)2.0,1000,omega * (double)i,1);
      TestArray[1][i]=TrapezoidIntegrate((double)0.0,(double)2.0,1000,omega * (double)i,2);
    }
    TestArray_array_rowsLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private double TrapezoidIntegrate(  double x0,  double x1,  int nsteps,  double omegan,  int select){
    double x;
    double dx;
    double rvalue;
    x=x0;
    dx=(x1 - x0) / (double)nsteps;
    rvalue=thefunction(x0,omegan,select) / (double)2.0;
    if (nsteps != 1) {
      --nsteps;
      while (--nsteps > 0) {
        x+=dx;
        rvalue+=thefunction(x,omegan,select);
      }
    }
    rvalue=(rvalue + thefunction(x1,omegan,select) / (double)2.0) * dx;
    return (rvalue);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private double thefunction(  double x,  double omegan,  int select){
switch (select) {
case 0:
      return (Math.pow(x + (double)1.0,x));
case 1:
    return (Math.pow(x + (double)1.0,x) * Math.cos(omegan * x));
case 2:
  return (Math.pow(x + (double)1.0,x) * Math.sin(omegan * x));
}
return (0.0);
}
@Perm(requires="unique(TestArray) in alive",ensures="unique(TestArray) in alive") void freeTestData(){
TestArray_array_rowsLock.writeLock().lock();
TestArray=null;
TestArray_array_rowsLock.writeLock().unlock();
System.gc();
}
public ReentrantReadWriteLock getTestArray_array_rowsLock(){
Cloner cloner=new Cloner();
TestArray_array_rowsLock=cloner.deepClone(TestArray_array_rowsLock);
return TestArray_array_rowsLock;
}
}
