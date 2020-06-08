package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class test {
	public ReentrantReadWriteLock a_arrayLock = new ReentrantReadWriteLock();
	Integer a = 10;
	Integer[] array = new Integer[]{1, 3, 5, 7, 9};
	@Perm(requires = "unique(array) in alive", ensures = "unique(array) in alive")
	public void createObject() {
		a_arrayLock.writeLock().lock();
		array = new Integer[5];
		a_arrayLock.writeLock().unlock();
	}
	@Perm(requires = "pure(array) * immutable(a) in alive", ensures = "pure(array) * immutable(a) in alive")
	public void readA() {
		a_arrayLock.readLock().lock();
		System.out.println("array[2] = " + array[2]);
		a_arrayLock.readLock().unlock();
		System.out.println("t1.a = " + a);
	}
	@Perm(requires = "unique(array) * immutable(a) in alive", ensures = "unique(array) * immutable(a) in alive")
	public static void main(String[] arg) {
		test obj1 = new test();
		a_arrayLock.writeLock().lock();
		obj1.createObject();
		obj1.readA();
		a_arrayLock.writeLock().unlock();
	}
}
