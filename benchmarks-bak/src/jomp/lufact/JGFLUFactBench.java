package jomp.lufact.withlock;
import jomp.jgfutil.JGFInstrumentor;
import jomp.jgfutil.JGFSection2;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFLUFactBench extends Linpack implements JGFSection2 {
	public ReentrantReadWriteLock a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock = new ReentrantReadWriteLock();
	private int size;
	private int datasizes[] = {500, 1000, 2000};
	@Perm(requires = "share(size) in alive", ensures = "share(size) in alive")
	public void JGFsetsize(int size) {
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().lock();
		this.size = size;
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().unlock();
	}
	@Perm(requires = "unique(a) * unique(b) * unique(x) * unique(ipvt) in alive", ensures = "unique(a) * unique(b) * unique(x) * unique(ipvt) in alive")
	public void JGFtidyup() {
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().lock();
		a = null;
		b = null;
		x = null;
		ipvt = null;
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().unlock();
		System.gc();
	}
	@Perm(requires = "share(n) * immutable(datasizes) * pure(size) * share(ldaa) * share(lda) * unique(a) * unique(b) * unique(x) * unique(ipvt) * share(ops) * share(norma) in alive", ensures = "share(n) * immutable(datasizes) * pure(size) * share(ldaa) * share(lda) * unique(a) * unique(b) * unique(x) * unique(ipvt) * share(ops) * share(norma) in alive")
	public void JGFinitialise() {
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().lock();
		n = datasizes[size];
		ldaa = n;
		lda = ldaa + 1;
		a = new double[ldaa][lda];
		b = new double[ldaa];
		x = new double[ldaa];
		ipvt = new int[ldaa];
		long nl = (long) n;
		ops = (2.0 * (nl * nl * nl)) / 3.0 + 2.0 * (nl * nl);
		norma = matgen(a, lda, n, b);
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().unlock();
	}
	@Perm(requires = "share(info) * pure(a) * pure(lda) * pure(n) * pure(ipvt) * pure(b) in alive", ensures = "share(info) * pure(a) * pure(lda) * pure(n) * pure(ipvt) * pure(b) in alive")
	public void JGFkernel() {
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.readLock().lock();
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().lock();
		info = dgefa(a, lda, n, ipvt);
		dgesl(a, lda, n, ipvt, b, 0);
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().unlock();
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.readLock().unlock();
	}
	@Perm(requires = "pure(n) * share(x) * share(b) * share(norma) * pure(a) * pure(lda) * share(resid) * share(normx) * pure(size) in alive", ensures = "pure(n) * share(x) * share(b) * share(norma) * pure(a) * pure(lda) * share(resid) * share(normx) * pure(size) in alive")
	public void JGFvalidate() {
		int i;
		double eps, residn;
		final double ref[] = {6.0, 12.0, 20.0};
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().lock();
		for (i = 0; i < n; i++) {
			x[i] = b[i];
		}
		norma = matgen(a, lda, n, b);
		for (i = 0; i < n; i++) {
			b[i] = -b[i];
		}
		dmxpy(n, b, n, x, a);
		resid = 0.0;
		normx = 0.0;
		for (i = 0; i < n; i++) {
			resid = (resid > abs(b[i])) ? resid : abs(b[i]);
			normx = (normx > abs(x[i])) ? normx : abs(x[i]);
		}
		eps = epslon((double) 1.0);
		residn = resid / (n * norma * normx * eps);
		if (residn > ref[size]) {
			System.out.println("Validation failed");
			System.out.println("Computed Norm Res = " + residn);
			System.out.println("Reference Norm Res = " + ref[size]);
		}
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFrun(int size) {
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().lock();
		JGFsetsize(size);
		JGFinitialise();
		JGFkernel();
		JGFvalidate();
		JGFtidyup();
		a_b_datasizes_info_ipvt_lda_ldaa_n_norma_normx_ops_resid_size_xLock.writeLock().unlock();
	}
}
