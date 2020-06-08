package jomp.search.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSearchBench extends SearchGame implements JGFSection3 {
	public ReentrantReadWriteLock nodesLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock he_ht_sizeLock = new ReentrantReadWriteLock();
	private int size;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFapplication() {
		int result = solve();
	}
	@Perm(requires = "share(size) in alive", ensures = "share(size) in alive")
	public void JGFsetsize(int size) {
		he_ht_sizeLock.writeLock().lock();
		this.size = size;
		he_ht_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public void JGFinitialise() {
		reset();
		he_ht_sizeLock.readLock().lock();
		for (int i = 0; i < startingMoves[size].length(); i++) {
			makemove(startingMoves[size].charAt(i) - '0');
			emptyTT();
		}
		he_ht_sizeLock.readLock().unlock();
	}
	@Perm(requires = "share(he) * pure(size) in alive", ensures = "share(he) * pure(size) in alive")
	public void JGFvalidate() {
		int i, works[];
		int ref[][] = {
				{422, 97347, 184228, 270877, 218810, 132097, 72059, 37601, 18645, 9200, 4460, 2230, 1034, 502, 271, 121,
						55, 28, 11, 6, 4, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 9, 2885, 105101, 339874, 282934, 156627, 81700, 40940, 20244, 10278, 4797, 2424, 1159, 535, 246,
						139, 62, 28, 11, 11, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0}};
		works = new int[32];
		for (i = 0; i < 32; i++)
			works[i] = 0;
		he_ht_sizeLock.writeLock().lock();
		for (i = 0; i < TRANSIZE; i++)
			works[he[i] & 31]++;
		for (i = 0; i < 32; i++) {
			int error = works[i] - ref[size][i];
			if (error != 0) {
				System.out.print("Validation failed for work count " + i);
				System.out.print("Computed value = " + works[i]);
				System.out.print("Reference value = " + ref[size][i]);
			}
		}
		he_ht_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "unique(ht) * unique(he) in alive", ensures = "unique(ht) * unique(he) in alive")
	public void JGFtidyup() {
		he_ht_sizeLock.writeLock().lock();
		ht = null;
		he = null;
		he_ht_sizeLock.writeLock().unlock();
		System.gc();
	}
	@Perm(requires = "unique(nodes) in alive", ensures = "unique(nodes) in alive")
	public void JGFrun(int size) {
		JGFInstrumentor.addTimer("Section3:AlphaBetaSearch:Run", "positions", size);
		he_ht_sizeLock.writeLock().lock();
		JGFsetsize(size);
		JGFinitialise();
		JGFapplication();
		JGFvalidate();
		JGFtidyup();
		he_ht_sizeLock.writeLock().unlock();
		nodesLock.writeLock().lock();
		JGFInstrumentor.addOpsToTimer("Section3:AlphaBetaSearch:Run", (double) nodes);
		nodesLock.writeLock().unlock();
		JGFInstrumentor.printTimer("Section3:AlphaBetaSearch:Run");
	}
}
