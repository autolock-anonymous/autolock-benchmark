package jomp.sparsematmult.withlock;
import jomp.jgfutil.*;
import java.util.Random;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSparseMatmultBench extends SparseMatmult implements JGFSection2 {
	public static ReentrantReadWriteLock ytotalLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock R_col_lastElement_row_size_val_x_yLock = new ReentrantReadWriteLock();
	private static int size = 0;
	private static final long RANDOM_SEED = 10101010;
	private static final int datasizes_M[] = {50000, 100000, 500000};
	private static final int datasizes_N[] = {50000, 100000, 500000};
	private static final int datasizes_nz[] = {250000, 500000, 2500000};
	private static final int SPARSE_NUM_ITER = 200;
	int lastElement;
	Random R = new Random(RANDOM_SEED);
	double[] x;
	double[] y;
	double[] val;
	int[] col;
	int[] row;
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public static void main(String argv[]) {
		JGFInstrumentor.printHeader(2, 0);
		JGFSparseMatmultBench cb = new JGFSparseMatmultBench();
		R_col_lastElement_row_size_val_x_yLock.readLock().lock();
		cb.JGFrun(size);
		R_col_lastElement_row_size_val_x_yLock.readLock().unlock();
	}
	@Perm(requires = "full(size) in alive", ensures = "full(size) in alive")
	public void JGFsetsize(int size) {
		R_col_lastElement_row_size_val_x_yLock.writeLock().lock();
		this.size = size;
		R_col_lastElement_row_size_val_x_yLock.writeLock().unlock();
	}
	@Perm(requires = "share(lastElement) * pure(size) * share(x) * share(R) * unique(y) * unique(val) * unique(col) * unique(row) in alive", ensures = "share(lastElement) * pure(size) * share(x) * share(R) * unique(y) * unique(val) * unique(col) * unique(row) in alive")
	public void JGFinitialise() {
		R_col_lastElement_row_size_val_x_yLock.writeLock().lock();
		lastElement = datasizes_N[size];
		x = RandomVector(lastElement, R);
		y = new double[datasizes_M[size]];
		val = new double[datasizes_nz[size]];
		col = new int[datasizes_nz[size]];
		row = new int[datasizes_nz[size]];
		for (int i = 0; i < datasizes_nz[size]; i++) {
			row[i] = Math.abs(R.nextInt()) % datasizes_M[size];
			col[i] = Math.abs(R.nextInt()) % datasizes_N[size];
			val[i] = R.nextDouble();
		}
		R_col_lastElement_row_size_val_x_yLock.writeLock().unlock();
	}
	@Perm(requires = "pure(y) * pure(val) * pure(row) * pure(col) * pure(x) in alive", ensures = "pure(y) * pure(val) * pure(row) * pure(col) * pure(x) in alive")
	public void JGFkernel() {
		R_col_lastElement_row_size_val_x_yLock.readLock().lock();
		SparseMatmult.test(y, val, row, col, x, SPARSE_NUM_ITER);
		R_col_lastElement_row_size_val_x_yLock.readLock().unlock();
	}
	@Perm(requires = "pure(ytotal) * pure(size) in alive", ensures = "pure(ytotal) * pure(size) in alive")
	public void JGFvalidate() {
		double refval[] = {75.02484945753453, 150.0130719633895, 749.5245870753752};
		ytotalLock.readLock().lock();
		R_col_lastElement_row_size_val_x_yLock.readLock().lock();
		double dev = Math.abs(ytotal - refval[size]);
		if (dev > 1.0e-12) {
			System.out.println("Validation failed");
			System.out.println("ytotal = " + ytotal + "  " + dev + "  " + size);
		}
		R_col_lastElement_row_size_val_x_yLock.readLock().unlock();
		ytotalLock.readLock().unlock();
	}
	@Perm(requires = "unique(x) in alive", ensures = "unique(x) in alive")
	public void JGFtidyup() {
		R_col_lastElement_row_size_val_x_yLock.writeLock().lock();
		x = null;
		R_col_lastElement_row_size_val_x_yLock.writeLock().unlock();
		System.gc();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFrun(int size) {
		JGFInstrumentor.addTimer("Section2:SparseMatmult:Kernel", "Iterations", size);
		R_col_lastElement_row_size_val_x_yLock.writeLock().lock();
		JGFsetsize(size);
		JGFinitialise();
		JGFkernel();
		JGFvalidate();
		JGFtidyup();
		R_col_lastElement_row_size_val_x_yLock.writeLock().unlock();
		JGFInstrumentor.addOpsToTimer("Section2:SparseMatmult:Kernel", (double) (SPARSE_NUM_ITER));
		JGFInstrumentor.printTimer("Section2:SparseMatmult:Kernel");
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static double[] RandomVector(int N, java.util.Random R) {
		double A[] = new double[N];
		for (int i = 0; i < N; i++) {
			A[i] = R.nextDouble() * 1e-6;
		}
		return A;
	}
}
