package vector.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Vector<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
	public ReentrantReadWriteLock capacityIncrement_elementCount_elementData_modCountLock = new ReentrantReadWriteLock();
	protected Object[] elementData;
	protected int elementCount;
	protected int capacityIncrement;
	private static final long serialVersionUID = -2767605614048989439L;
	@Perm(requires = "unique(elementData) * unique(capacityIncrement) in alive", ensures = "unique(elementData) * unique(capacityIncrement) in alive")
	public Vector(int initialCapacity, int capacityIncrement) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		this.elementData = new Object[initialCapacity];
		this.capacityIncrement = capacityIncrement;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Vector(int initialCapacity) {
		this(initialCapacity, 0);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Vector() {
		this(10);
	}
	@Perm(requires = "unique(elementData) * unique(elementCount) in alive", ensures = "unique(elementData) * unique(elementCount) in alive")
	public Vector(Collection<? extends E> c) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		elementData = c.toArray();
		elementCount = elementData.length;
		if (elementData.getClass() != Object[].class)
			elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "pure(elementData) * pure(elementCount) in alive", ensures = "pure(elementData) * pure(elementCount) in alive")
	public synchronized void copyInto(Object[] anArray) {
		capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
		System.arraycopy(elementData, 0, anArray, 0, elementCount);
		capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(elementData) * pure(elementCount) in alive", ensures = "share(modCount) * share(elementData) * pure(elementCount) in alive")
	public synchronized void trimToSize() {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		int oldCapacity = elementData.length;
		if (elementCount < oldCapacity) {
			elementData = Arrays.copyOf(elementData, elementCount);
		}
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) in alive", ensures = "share(modCount) in alive")
	public synchronized void ensureCapacity(int minCapacity) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		ensureCapacityHelper(minCapacity);
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(elementData) * none(capacityIncrement) in alive", ensures = "share(elementData) * none(capacityIncrement) in alive")
	private void ensureCapacityHelper(int minCapacity) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			Object[] oldData = elementData;
			int newCapacity = (capacityIncrement > 0) ? (oldCapacity + capacityIncrement) : (oldCapacity * 2);
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public synchronized void setSize(int newSize) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		if (newSize > elementCount) {
			ensureCapacityHelper(newSize);
		} else {
			for (int i = newSize; i < elementCount; i++) {
				elementData[i] = null;
			}
		}
		elementCount = newSize;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "pure(elementData) in alive", ensures = "pure(elementData) in alive")
	public synchronized int capacity() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return elementData.length;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public synchronized int size() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return elementCount;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public synchronized boolean isEmpty() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return elementCount == 0;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Enumeration<E> elements() {
		return new Enumeration<E>() {
			int count = 0;
			public boolean hasMoreElements() {
				return count < elementCount;
			}
			public E nextElement() {
				synchronized (Vector.this) {
					if (count < elementCount) {
						return (E) elementData[count++];
					}
				}
				throw new NoSuchElementException("Vector Enumeration");
			}
		};
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean contains(Object o) {
		return indexOf(o, 0) >= 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int indexOf(Object o) {
		return indexOf(o, 0);
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public synchronized int indexOf(Object o, int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (o == null) {
				for (int i = index; i < elementCount; i++)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = index; i < elementCount; i++)
					if (o.equals(elementData[i]))
						return i;
			}
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public synchronized int lastIndexOf(Object o) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return lastIndexOf(o, elementCount - 1);
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public synchronized int lastIndexOf(Object o, int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (index >= elementCount)
				throw new IndexOutOfBoundsException(index + " >= " + elementCount);
			if (o == null) {
				for (int i = index; i >= 0; i--)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = index; i >= 0; i--)
					if (o.equals(elementData[i]))
						return i;
			}
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public synchronized E elementAt(int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			}
			return (E) elementData[index];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public synchronized E firstElement() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (elementCount == 0) {
				throw new NoSuchElementException();
			}
			return (E) elementData[0];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public synchronized E lastElement() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (elementCount == 0) {
				throw new NoSuchElementException();
			}
			return (E) elementData[elementCount - 1];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * share(elementData) in alive", ensures = "pure(elementCount) * share(elementData) in alive")
	public synchronized void setElementAt(E obj, int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			}
			elementData[index] = obj;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(modCount) * unique(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * unique(elementCount) * unique(elementData) in alive")
	public synchronized void removeElementAt(int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			modCount++;
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			} else if (index < 0) {
				throw new ArrayIndexOutOfBoundsException(index);
			}
			int j = elementCount - index - 1;
			if (j > 0) {
				System.arraycopy(elementData, index + 1, elementData, index, j);
			}
			elementCount--;
			elementData[elementCount] = null;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(modCount) * share(elementCount) * share(elementData) in alive", ensures = "share(modCount) * share(elementCount) * share(elementData) in alive")
	public synchronized void insertElementAt(E obj, int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			modCount++;
			if (index > elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " > " + elementCount);
			}
			ensureCapacityHelper(elementCount + 1);
			System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
			elementData[index] = obj;
			elementCount++;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(modCount) * share(elementCount) * share(elementData) in alive", ensures = "share(modCount) * share(elementCount) * share(elementData) in alive")
	public synchronized void addElement(E obj) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = obj;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) in alive", ensures = "share(modCount) in alive")
	public synchronized boolean removeElement(Object obj) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			modCount++;
			int i = indexOf(obj);
			if (i >= 0) {
				removeElementAt(i);
				return true;
			}
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
		return false;
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public synchronized void removeAllElements() {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		for (int i = 0; i < elementCount; i++)
			elementData[i] = null;
		elementCount = 0;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(elementData) * pure(elementCount) * pure(modCount) in alive", ensures = "share(elementData) * pure(elementCount) * pure(modCount) in alive")
	public synchronized Object clone() {
		try {
			Vector<E> v = (Vector<E>) super.clone();
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			v.elementData = Arrays.copyOf(elementData, elementCount);
			v.modCount = 0;
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
			return v;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	@Perm(requires = "pure(elementData) * pure(elementCount) in alive", ensures = "pure(elementData) * pure(elementCount) in alive")
	public synchronized Object[] toArray() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return Arrays.copyOf(elementData, elementCount);
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "unique(elementCount) * pure(elementData) in alive", ensures = "unique(elementCount) * pure(elementData) in alive")
	public synchronized <T> T[] toArray(T[] a) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			if (a.length < elementCount)
				return (T[]) Arrays.copyOf(elementData, elementCount, a.getClass());
			System.arraycopy(elementData, 0, a, 0, elementCount);
			if (a.length > elementCount)
				a[elementCount] = null;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
		return a;
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public synchronized E get(int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (index >= elementCount)
				throw new ArrayIndexOutOfBoundsException(index);
			return (E) elementData[index];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * share(elementData) in alive", ensures = "pure(elementCount) * share(elementData) in alive")
	public synchronized E set(int index, E element) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			if (index >= elementCount)
				throw new ArrayIndexOutOfBoundsException(index);
			Object oldValue = elementData[index];
			elementData[index] = element;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
		return (E) oldValue;
	}
	@Perm(requires = "share(modCount) * share(elementCount) * share(elementData) in alive", ensures = "share(modCount) * share(elementCount) * share(elementData) in alive")
	public synchronized boolean add(E e) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = e;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean remove(Object o) {
		return removeElement(o);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(int index, E element) {
		insertElementAt(element, index);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public synchronized E remove(int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			modCount++;
			if (index >= elementCount)
				throw new ArrayIndexOutOfBoundsException(index);
			Object oldValue = elementData[index];
			int numMoved = elementCount - index - 1;
			if (numMoved > 0)
				System.arraycopy(elementData, index + 1, elementData, index, numMoved);
			elementData[--elementCount] = null;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
		return (E) oldValue;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void clear() {
		removeAllElements();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized boolean containsAll(Collection<?> c) {
		return super.containsAll(c);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * pure(elementData) in alive", ensures = "share(modCount) * share(elementCount) * pure(elementData) in alive")
	public synchronized boolean addAll(Collection<? extends E> c) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityHelper(elementCount + numNew);
		System.arraycopy(a, 0, elementData, elementCount, numNew);
		elementCount += numNew;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		return numNew != 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized boolean removeAll(Collection<?> c) {
		return super.removeAll(c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized boolean retainAll(Collection<?> c) {
		return super.retainAll(c);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * pure(elementData) in alive", ensures = "share(modCount) * share(elementCount) * pure(elementData) in alive")
	public synchronized boolean addAll(int index, Collection<? extends E> c) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			modCount++;
			if (index < 0 || index > elementCount)
				throw new ArrayIndexOutOfBoundsException(index);
			Object[] a = c.toArray();
			int numNew = a.length;
			ensureCapacityHelper(elementCount + numNew);
			int numMoved = elementCount - index;
			if (numMoved > 0)
				System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
			System.arraycopy(a, 0, elementData, index, numNew);
			elementCount += numNew;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
		return numNew != 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized boolean equals(Object o) {
		return super.equals(o);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized int hashCode() {
		return super.hashCode();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized String toString() {
		return super.toString();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized List<E> subList(int fromIndex, int toIndex) {
		return Collections.synchronizedList(super.subList(fromIndex, toIndex), this);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	protected synchronized void removeRange(int fromIndex, int toIndex) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		int numMoved = elementCount - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
		int newElementCount = elementCount - (toIndex - fromIndex);
		while (elementCount != newElementCount)
			elementData[--elementCount] = null;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private synchronized void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		s.defaultWriteObject();
	}
}
