package aeminium.lud.withlock;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class SeqLUD extends LUD {
  public ReentrantReadWriteLock BLOCK_SIZE_col_isFirstCall_rowLock=new ReentrantReadWriteLock();
  @Perm(requires="no permission in alive",ensures="no permission in alive") public SeqLUD(  Matrix a,  int bs){
    super(a,bs);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void main(  String[] args){
    int size=LUD.DEFAULT_SIZE;
    int blocksize=LUD.DEFAULT_BLOCK_SIZE;
    Random r=new Random(1L);
    Matrix A=Matrix.random(size,size,r);
    final int numOfBlocks=size / blocksize;
    final SeqLUD lu=new SeqLUD(A,blocksize);
    BLOCK_SIZE_col_isFirstCall_rowLock.writeLock().lock();
    lu.calcLU(new MatrixPosition(0,0),numOfBlocks);
    BLOCK_SIZE_col_isFirstCall_rowLock.writeLock().unlock();
    System.out.println("Done");
  }
  @Perm(requires="immutable(row) * immutable(col) * immutable(BLOCK_SIZE) in alive",ensures="immutable(row) * immutable(col) * immutable(BLOCK_SIZE) in alive") public void lowerSolve(  MatrixPosition posM,  final MatrixPosition posL,  final int numOfBlocks){
    if (numOfBlocks == 1) {
      blockLowerSolve(posM,posL);
      return;
    }
    final int halfNb=numOfBlocks / 2;
    final MatrixPosition posM00=posM;
    final MatrixPosition posM01=new MatrixPosition(posM.row,posM.col + (halfNb * BLOCK_SIZE));
    final MatrixPosition posM10=new MatrixPosition(posM.row + (halfNb * BLOCK_SIZE),posM.col);
    final MatrixPosition posM11=new MatrixPosition(posM.row + (halfNb * BLOCK_SIZE),posM.col + (halfNb * BLOCK_SIZE));
    auxLowerSolve(posM00,posM10,posL,halfNb);
    auxLowerSolve(posM01,posM11,posL,halfNb);
    return;
  }
  @Perm(requires="immutable(row) * immutable(col) * immutable(BLOCK_SIZE) in alive",ensures="immutable(row) * immutable(col) * immutable(BLOCK_SIZE) in alive") @SuppressWarnings("unused") public void auxLowerSolve(  final MatrixPosition posMa,  final MatrixPosition posMb,  MatrixPosition posL,  final int numOfBlocks){
    final MatrixPosition posL00=posL;
    final MatrixPosition posL01=new MatrixPosition(posL.row,posL.col + (numOfBlocks * BLOCK_SIZE));
    final MatrixPosition posL10=new MatrixPosition(posL.row + (numOfBlocks * BLOCK_SIZE),posL.col);
    final MatrixPosition posL11=new MatrixPosition(posL.row + (numOfBlocks * BLOCK_SIZE),posL.col + (numOfBlocks * BLOCK_SIZE));
    lowerSolve(posMa,posL00,numOfBlocks);
    lowerSolve(posMb,posL11,numOfBlocks);
    return;
  }
  boolean isFirstCall=true;
  @Perm(requires="share(isFirstCall) * immutable(row) * immutable(col) * immutable(BLOCK_SIZE) in alive",ensures="share(isFirstCall) * immutable(row) * immutable(col) * immutable(BLOCK_SIZE) in alive") public void calcLU(  MatrixPosition pos,  int numOfBlocks){
    try {
      BLOCK_SIZE_col_isFirstCall_rowLock.writeLock().lock();
      if (isFirstCall) {
        pos=new MatrixPosition(0,0);
        isFirstCall=false;
      }
      if (numOfBlocks == 1) {
        blockLU(pos);
        return;
      }
      final int halfNb=numOfBlocks / 2;
      final MatrixPosition pos00=pos;
      final MatrixPosition pos01=new MatrixPosition(pos.row,pos.col + (halfNb * BLOCK_SIZE));
      final MatrixPosition pos10=new MatrixPosition(pos.row + (halfNb * BLOCK_SIZE),pos.col);
      final MatrixPosition pos11=new MatrixPosition(pos.row + (halfNb * BLOCK_SIZE),pos.col + (halfNb * BLOCK_SIZE));
      calcLU(pos00,halfNb);
      lowerSolve(pos01,pos00,halfNb);
      calcLU(pos11,halfNb);
    }
  finally {
      BLOCK_SIZE_col_isFirstCall_rowLock.writeLock().unlock();
    }
  }
  public ReentrantReadWriteLock getBLOCK_SIZE_col_isFirstCall_rowLock(){
    Cloner cloner=new Cloner();
    BLOCK_SIZE_col_isFirstCall_rowLock=cloner.deepClone(BLOCK_SIZE_col_isFirstCall_rowLock);
    Cloner cloner=new Cloner();
    BLOCK_SIZE_col_isFirstCall_rowLock=cloner.deepClone(BLOCK_SIZE_col_isFirstCall_rowLock);
    return BLOCK_SIZE_col_isFirstCall_rowLock;
  }
}
