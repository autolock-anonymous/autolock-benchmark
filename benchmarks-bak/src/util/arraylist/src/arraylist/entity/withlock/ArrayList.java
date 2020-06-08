package arraylist.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
	public ReentrantReadWriteLock elementData_modCount_sizeLock = new ReentrantReadWriteLock();
	private static final long serialVersionUID = 8683452581122892189L;
	private transient Object[] elementData;
	private int size;
	@Perm(requires = "unique(elementData) in alive", ensures = "unique(elementData) in alive")
	public ArrayList(int initialCapacity) {
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		elementData_modCount_sizeLock.writeLock().lock();
		this.elementData = new Object[initialCapacity];
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public ArrayList() {
		this(10);
	}
	@Perm(requires = "unique(elementData) * unique(size) in alive", ensures = "unique(elementData) * unique(size) in alive")
	public ArrayList(Collection<? extends E> c) {
		elementData_modCount_sizeLock.writeLock().lock();
		elementData = c.toArray();
		size = elementData.length;
		if (elementData.getClass() != Object[].class)
			elementData = Arrays.copyOf(elementData, size, Object[].class);
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(elementData) * pure(size) in alive", ensures = "share(modCount) * share(elementData) * pure(size) in alive")
	public void trimToSize() {
		elementData_modCount_sizeLock.writeLock().lock();
		modCount++;
		int oldCapacity = elementData.length;
		if (size < oldCapacity) {
			elementData = Arrays.copyOf(elementData, size);
		}
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(elementData) in alive", ensures = "share(modCount) * share(elementData) in alive")
	public void ensureCapacity(int minCapacity) {
		elementData_modCount_sizeLock.writeLock().lock();
		modCount++;
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			Object oldData[] = elementData;
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			elementData = Arrays.copyOf(elementData, newCapacity);
		}
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public int size() {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			return size;
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public boolean isEmpty() {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			return size == 0;
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}
	@Perm(requires = "pure(size) * pure(elementData) in alive", ensures = "pure(size) * pure(elementData) in alive")
	public int indexOf(Object o) {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			if (o == null) {
				for (int i = 0; i < size; i++)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = 0; i < size; i++)
					if (o.equals(elementData[i]))
						return i;
			}
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(size) * pure(elementData) in alive", ensures = "pure(size) * pure(elementData) in alive")
	public int lastIndexOf(Object o) {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			if (o == null) {
				for (int i = size - 1; i >= 0; i--)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = size - 1; i >= 0; i--)
					if (o.equals(elementData[i]))
						return i;
			}
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "share(elementData) * pure(size) * pure(modCount) in alive", ensures = "share(elementData) * pure(size) * pure(modCount) in alive")
	public Object clone() {
		try {
			ArrayList<E> v = (ArrayList<E>) super.clone();
			elementData_modCount_sizeLock.writeLock().lock();
			v.elementData = Arrays.copyOf(elementData, size);
			v.modCount = 0;
			elementData_modCount_sizeLock.writeLock().unlock();
			return v;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	@Perm(requires = "pure(elementData) * pure(size) in alive", ensures = "pure(elementData) * pure(size) in alive")
	public Object[] toArray() {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			return Arrays.copyOf(elementData, size);
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "unique(size) * pure(elementData) in alive", ensures = "unique(size) * pure(elementData) in alive")
	public <T> T[] toArray(T[] a) {
		try {
			elementData_modCount_sizeLock.writeLock().lock();
			if (a.length < size)
				return (T[]) Arrays.copyOf(elementData, size, a.getClass());
			System.arraycopy(elementData, 0, a, 0, size);
			if (a.length > size)
				a[size] = null;
		} finally {
			elementData_modCount_sizeLock.writeLock().unlock();
		}
		return a;
	}
	@Perm(requires = "pure(elementData) in alive", ensures = "pure(elementData) in alive")
	public E get(int index) {
		RangeCheck(index);
		try {
			elementData_modCount_sizeLock.readLock().lock();
			return (E) elementData[index];
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(elementData) in alive", ensures = "share(elementData) in alive")
	public E set(int index, E element) {
		elementData_modCount_sizeLock.writeLock().lock();
		RangeCheck(index);
		E oldValue = (E) elementData[index];
		elementData[index] = element;
		elementData_modCount_sizeLock.writeLock().unlock();
		return oldValue;
	}
	@Perm(requires = "share(size) * share(elementData) in alive", ensures = "share(size) * share(elementData) in alive")
	public boolean add(E e) {
		elementData_modCount_sizeLock.writeLock().lock();
		ensureCapacity(size + 1);
		elementData[size++] = e;
		elementData_modCount_sizeLock.writeLock().unlock();
		return true;
	}
	@Perm(requires = "share(size) * share(elementData) in alive", ensures = "share(size) * share(elementData) in alive")
	public void add(int index, E element) {
		try {
			elementData_modCount_sizeLock.writeLock().lock();
			if (index > size || index < 0)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
			ensureCapacity(size + 1);
			System.arraycopy(elementData, index, elementData, index + 1, size - index);
			elementData[index] = element;
			size++;
		} finally {
			elementData_modCount_sizeLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(modCount) * unique(elementData) * share(size) in alive", ensures = "share(modCount) * unique(elementData) * share(size) in alive")
	public E remove(int index) {
		elementData_modCount_sizeLock.writeLock().lock();
		RangeCheck(index);
		modCount++;
		E oldValue = (E) elementData[index];
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;
		elementData_modCount_sizeLock.writeLock().unlock();
		return oldValue;
	}
	@Perm(requires = "pure(size) * pure(elementData) in alive", ensures = "pure(size) * pure(elementData) in alive")
	public boolean remove(Object o) {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			if (o == null) {
				for (int index = 0; index < size; index++)
					if (elementData[index] == null) {
						fastRemove(index);
						return true;
					}
			} else {
				for (int index = 0; index < size; index++)
					if (o.equals(elementData[index])) {
						fastRemove(index);
						return true;
					}
			}
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
		return false;
	}
	@Perm(requires = "share(modCount) * share(size) * unique(elementData) in alive", ensures = "share(modCount) * share(size) * unique(elementData) in alive")
	private void fastRemove(int index) {
		elementData_modCount_sizeLock.writeLock().lock();
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(size) * unique(elementData) in alive", ensures = "share(modCount) * share(size) * unique(elementData) in alive")
	public void clear() {
		elementData_modCount_sizeLock.writeLock().lock();
		modCount++;
		for (int i = 0; i < size; i++)
			elementData[i] = null;
		size = 0;
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "share(size) * pure(elementData) in alive", ensures = "share(size) * pure(elementData) in alive")
	public boolean addAll(Collection<? extends E> c) {
		Object[] a = c.toArray();
		int numNew = a.length;
		elementData_modCount_sizeLock.writeLock().lock();
		ensureCapacity(size + numNew);
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		elementData_modCount_sizeLock.writeLock().unlock();
		return numNew != 0;
	}
	@Perm(requires = "share(size) * pure(elementData) in alive", ensures = "share(size) * pure(elementData) in alive")
	public boolean addAll(int index, Collection<? extends E> c) {
		try {
			elementData_modCount_sizeLock.writeLock().lock();
			if (index > size || index < 0)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
			Object[] a = c.toArray();
			int numNew = a.length;
			ensureCapacity(size + numNew);
			int numMoved = size - index;
			if (numMoved > 0)
				System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
			System.arraycopy(a, 0, elementData, index, numNew);
			size += numNew;
		} finally {
			elementData_modCount_sizeLock.writeLock().unlock();
		}
		return numNew != 0;
	}
	@Perm(requires = "share(modCount) * share(size) * unique(elementData) in alive", ensures = "share(modCount) * share(size) * unique(elementData) in alive")
	protected void removeRange(int fromIndex, int toIndex) {
		elementData_modCount_sizeLock.writeLock().lock();
		modCount++;
		int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
		int newSize = size - (toIndex - fromIndex);
		while (size != newSize)
			elementData[--size] = null;
		elementData_modCount_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	private void RangeCheck(int index) {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			if (index >= size)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(modCount) * pure(elementData) * pure(size) in alive", ensures = "pure(modCount) * pure(elementData) * pure(size) in alive")
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		try {
			elementData_modCount_sizeLock.readLock().lock();
			int expectedModCount = modCount;
			s.defaultWriteObject();
			s.writeInt(elementData.length);
			for (int i = 0; i < size; i++)
				s.writeObject(elementData[i]);
			if (modCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
		} finally {
			elementData_modCount_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "unique(elementData) * pure(size) in alive", ensures = "unique(elementData) * pure(size) in alive")
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		int arrayLength = s.readInt();
		elementData_modCount_sizeLock.writeLock().lock();
		Object[] a = elementData = new Object[arrayLength];
		for (int i = 0; i < size; i++)
			a[i] = s.readObject();
		elementData_modCount_sizeLock.writeLock().unlock();
	}
}
