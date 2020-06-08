package linkedlist.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public abstract class AbstractSequentialList<E> extends AbstractList<E> {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected AbstractSequentialList() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E get(int index) {
		try {
			return listIterator(index).next();
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E set(int index, E element) {
		try {
			ListIterator<E> e = listIterator(index);
			E oldVal = e.next();
			e.set(element);
			return oldVal;
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(int index, E element) {
		try {
			listIterator(index).add(element);
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E remove(int index) {
		try {
			ListIterator<E> e = listIterator(index);
			E outCast = e.next();
			e.remove();
			return outCast;
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean addAll(int index, Collection<? extends E> c) {
		try {
			boolean modified = false;
			ListIterator<E> e1 = listIterator(index);
			Iterator<? extends E> e2 = c.iterator();
			while (e2.hasNext()) {
				e1.add(e2.next());
				modified = true;
			}
			return modified;
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Iterator<E> iterator() {
		return listIterator();
	}
	public abstract ListIterator<E> listIterator(int index);
}
