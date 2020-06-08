package aeminium.lud.withlock;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class Matrix {
  public ReentrantReadWriteLock A_m_nLock=new ReentrantReadWriteLock();
  private double[][] A;
  private int m;
  private int n;
  @Perm(requires="unique(m) * unique(n) * unique(A) in alive",ensures="unique(m) * unique(n) * unique(A) in alive") public Matrix(  int m,  int n){
    A_m_nLock.writeLock().lock();
    this.m=m;
    this.n=n;
    A=new double[m][n];
    A_m_nLock.writeLock().unlock();
  }
  @Perm(requires="immutable(A) in alive",ensures="immutable(A) in alive") public double[][] getArray(){
    return A;
  }
  @Perm(requires="immutable(m) * immutable(n) * immutable(A) in alive",ensures="immutable(m) * immutable(n) * immutable(A) in alive") public double[][] getArrayCopy(){
    double[][] C=new double[m][n];
    for (int i=0; i < m; i++) {
      for (int j=0; j < n; j++) {
        C[i][j]=A[i][j];
      }
    }
    return C;
  }
  @Perm(requires="immutable(m) in alive",ensures="immutable(m) in alive") public int getRowDimension(){
    return m;
  }
  @Perm(requires="immutable(n) in alive",ensures="immutable(n) in alive") public int getColumnDimension(){
    return n;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static Matrix random(  int m,  int n,  Random r){
    Matrix A=new Matrix(m,n);
    double[][] X=A.getArray();
    for (int i=0; i < m; i++) {
      for (int j=0; j < n; j++) {
        X[i][j]=r.nextDouble();
      }
    }
    return A;
  }
  public ReentrantReadWriteLock getA_m_nLock(){
    Cloner cloner=new Cloner();
    A_m_nLock=cloner.deepClone(A_m_nLock);
    Cloner cloner=new Cloner();
    A_m_nLock=cloner.deepClone(A_m_nLock);
    return A_m_nLock;
  }
}
