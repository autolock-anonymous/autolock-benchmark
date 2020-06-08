package jomp.crypt.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFCryptBench extends IDEATest implements JGFSection2 {
	public ReentrantReadWriteLock plain1Lock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock plain2Lock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock crypt1Lock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock array_rows_datasizes_sizeLock = new ReentrantReadWriteLock();
	private int size;
	private int datasizes[] = {3000000, 20000000, 50000000};
	@Perm(requires = "share(size) in alive", ensures = "share(size) in alive")
	public void JGFsetsize(int size) {
		array_rows_datasizes_sizeLock.writeLock().lock();
		this.size = size;
		array_rows_datasizes_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "share(array_rows) * immutable(datasizes) * pure(size) in alive", ensures = "share(array_rows) * immutable(datasizes) * pure(size) in alive")
	public void JGFinitialise() {
		array_rows_datasizes_sizeLock.writeLock().lock();
		array_rows = datasizes[size];
		array_rows_datasizes_sizeLock.writeLock().unlock();
		buildTestData();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFkernel() {
		Do();
	}
	@Perm(requires = "pure(array_rows) * pure(plain1) * pure(plain2) * pure(crypt1) in alive", ensures = "pure(array_rows) * pure(plain1) * pure(plain2) * pure(crypt1) in alive")
	public void JGFvalidate() {
		boolean error;
		error = false;
		array_rows_datasizes_sizeLock.readLock().lock();
		for (int i = 0; i < array_rows; i++) {
			plain2Lock.readLock().lock();
			plain1Lock.readLock().lock();
			error = (plain1[i] != plain2[i]);
			if (error) {
				System.out.println("Validation failed");
				System.out.println("Original Byte " + i + " = " + plain1[i]);
				crypt1Lock.readLock().lock();
				System.out.println("Encrypted Byte " + i + " = " + crypt1[i]);
				crypt1Lock.readLock().unlock();
				System.out.println("Decrypted Byte " + i + " = " + plain2[i]);
			}
			plain1Lock.readLock().unlock();
			plain2Lock.readLock().unlock();
		}
		array_rows_datasizes_sizeLock.readLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFtidyup() {
		freeTestData();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void JGFrun(int size) {
		array_rows_datasizes_sizeLock.writeLock().lock();
		JGFsetsize(size);
		JGFinitialise();
		JGFkernel();
		JGFvalidate();
		array_rows_datasizes_sizeLock.writeLock().unlock();
		JGFtidyup();
	}
}
