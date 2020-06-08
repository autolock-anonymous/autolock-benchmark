package jomp.moldyn.withlock;
import java.io.*;
import jomp.jgfutil.JGFInstrumentor;
import jomp.jgfutil.JGFSection3;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFMolDynBench extends md implements JGFSection3 {
	public ReentrantReadWriteLock ekLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock oneLock = new ReentrantReadWriteLock();
	@Perm(requires = "unique(one) in alive", ensures = "unique(one) in alive")
	public void JGFtidyup() {
		oneLock.writeLock().lock();
		one = null;
		oneLock.writeLock().unlock();
		System.gc();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFrun(int size) {
		JGFsetsize(size);
		JGFinitialise();
		JGFapplication();
		JGFvalidate();
		oneLock.writeLock().lock();
		JGFtidyup();
		oneLock.writeLock().unlock();
	}
	@Perm(requires = "full(size) in alive", ensures = "full(size) in alive")
	public void JGFsetsize(int size) {
		sizeLock.writeLock().lock();
		this.size = size;
		sizeLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFinitialise() {
		initialise();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFapplication() {
		runiters();
	}
	@Perm(requires = "pure(ek) * pure(size) in alive", ensures = "pure(ek) * pure(size) in alive")
	public void JGFvalidate() {
		double refval[] = {1731.4306625334357, 7397.392307839352};
		ekLock.readLock().lock();
		sizeLock.readLock().lock();
		double dev = Math.abs(ek - refval[size]);
		if (dev > 1.0e-12) {
			System.out.println("Validation failed");
			System.out.println("Kinetic Energy = " + ek + "  " + dev + "  " + size);
		}
		sizeLock.readLock().unlock();
		ekLock.readLock().unlock();
	}
}
