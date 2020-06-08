package aeminium.lud.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class MatrixPosition {
  public ReentrantReadWriteLock col_rowLock=new ReentrantReadWriteLock();
  public int row;
  public int col;
  @Perm(requires="unique(row) * unique(col) in alive",ensures="unique(row) * unique(col) in alive") public MatrixPosition(  int r,  int c){
    col_rowLock.writeLock().lock();
    this.row=r;
    this.col=c;
    col_rowLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getCol_rowLock(){
    Cloner cloner=new Cloner();
    col_rowLock=cloner.deepClone(col_rowLock);
    Cloner cloner=new Cloner();
    col_rowLock=cloner.deepClone(col_rowLock);
    return col_rowLock;
  }
}
