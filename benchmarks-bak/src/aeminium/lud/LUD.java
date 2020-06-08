package aeminium.lud.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class LUD {
	public ReentrantReadWriteLock BLOCK_SIZE_LU_col_row_sizeLock = new ReentrantReadWriteLock();
	public static final int DEFAULT_SIZE = 512;
	public static final int DEFAULT_BLOCK_SIZE = 8;
	public static final double MAX_DIFF_THRESHOLD = 0.01;
	protected double[][] LU;
	protected int size;
	protected int BLOCK_SIZE;
	@Perm(requires = "unique(LU) * unique(size) * unique(BLOCK_SIZE) in alive", ensures = "unique(LU) * unique(size) * unique(BLOCK_SIZE) in alive")
	public LUD(Matrix A, int blocksize) {
		try {
			BLOCK_SIZE_LU_col_row_sizeLock.writeLock().lock();
			LU = A.getArrayCopy();
			size = A.getRowDimension();
			BLOCK_SIZE = blocksize;
			if (size != A.getColumnDimension()) {
				throw new RuntimeException("The matrix must be a square matrix");
			}
		} finally {
			BLOCK_SIZE_LU_col_row_sizeLock.writeLock().unlock();
		}
	}
	@Perm(requires = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive", ensures = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive")
	protected void blockLowerSolve(MatrixPosition posB, MatrixPosition posL) {
		double a;
		int i, k, n;
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().lock();
		for (i = 1; i < BLOCK_SIZE; i++) {
			for (k = 0; k < i; k++) {
				a = LU[posL.row + i][posL.col + k];
				for (n = BLOCK_SIZE - 1; n >= 0; n--) {
					LU[posB.row + i][posB.col + n] -= a * LU[posB.row + k][posB.col + n];
				}
			}
		}
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive", ensures = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive")
	protected void blockUpperSolve(MatrixPosition posB, MatrixPosition posU) {
		double a;
		int i, k, n;
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().lock();
		for (i = 0; i < BLOCK_SIZE; i++) {
			for (k = 0; k < BLOCK_SIZE; k++) {
				LU[posB.row + i][posB.col + k] /= LU[posU.row + k][posU.col + k];
				a = LU[posB.row + i][posB.col + k];
				for (n = BLOCK_SIZE - 1; n >= (k + 1); n--) {
					LU[posB.row + i][posB.col + n] -= a * LU[posU.row + k][posU.col + n];
				}
			}
		}
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive", ensures = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive")
	protected void blockLU(MatrixPosition posB) {
		double a;
		int i, k, n;
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().lock();
		for (k = 0; k < BLOCK_SIZE; k++) {
			for (i = k + 1; i < BLOCK_SIZE; i++) {
				LU[posB.row + i][posB.col + k] /= LU[posB.row + k][posB.col + k];
				a = LU[posB.row + i][posB.col + k];
				for (n = BLOCK_SIZE - 1; n >= (k + 1); n--) {
					LU[posB.row + i][posB.col + n] -= a * LU[posB.row + k][posB.col + n];
				}
			}
		}
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive", ensures = "immutable(BLOCK_SIZE) * share(LU) * immutable(row) * immutable(col) in alive")
	protected void blockSchur(MatrixPosition posB, MatrixPosition posA, MatrixPosition posC) {
		int i, k, n;
		double a;
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().lock();
		for (i = 0; i < BLOCK_SIZE; i++) {
			for (k = 0; k < BLOCK_SIZE; k++) {
				a = LU[posA.row + i][posA.col + k];
				for (n = BLOCK_SIZE - 1; n >= 0; n--) {
					LU[posB.row + i][posB.col + n] -= a * LU[posC.row + k][posC.col + n];
				}
			}
		}
		BLOCK_SIZE_LU_col_row_sizeLock.writeLock().unlock();
	}
}
