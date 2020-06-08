package vectorjava142.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Vector extends AbstractList implements List, RandomAccess, Cloneable, java.io.Serializable {
	public ReentrantReadWriteLock capacityIncrement_elementCount_elementData_modCountLock = new ReentrantReadWriteLock();
	protected Object elementData[];
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
	@Perm(requires = "unique(elementCount) * unique(elementData) in alive", ensures = "unique(elementCount) * unique(elementData) in alive")
	public Vector(Collection c) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		elementCount = c.size();
		elementData = new Object[(int) Math.min((elementCount * 110L) / 100, Integer.MAX_VALUE)];
		c.toArray(elementData);
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "pure(elementData) * pure(elementCount) in alive", ensures = "pure(elementData) * pure(elementCount) in alive")
	public void copyInto(Object anArray[]) {
		capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
		System.arraycopy(elementData, 0, anArray, 0, elementCount);
		capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
	}
	@Perm(requires = "share(modCount) * unique(elementData) * pure(elementCount) in alive", ensures = "share(modCount) * unique(elementData) * pure(elementCount) in alive")
	public void trimToSize() {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		int oldCapacity = elementData.length;
		if (elementCount < oldCapacity) {
			Object oldData[] = elementData;
			elementData = new Object[elementCount];
			System.arraycopy(oldData, 0, elementData, 0, elementCount);
		}
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) in alive", ensures = "share(modCount) in alive")
	public void ensureCapacity(int minCapacity) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		ensureCapacityHelper(minCapacity);
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "unique(elementData) * immutable(capacityIncrement) * pure(elementCount) in alive", ensures = "unique(elementData) * immutable(capacityIncrement) * pure(elementCount) in alive")
	private void ensureCapacityHelper(int minCapacity) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			Object oldData[] = elementData;
			int newCapacity = (capacityIncrement > 0) ? (oldCapacity + capacityIncrement) : (oldCapacity * 2);
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			elementData = new Object[newCapacity];
			System.arraycopy(oldData, 0, elementData, 0, elementCount);
		}
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public void setSize(int newSize) {
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
	public int capacity() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return elementData.length;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public int size() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return elementCount;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public boolean isEmpty() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return elementCount == 0;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Enumeration elements() {
		return new Enumeration() {
			int count = 0;
			public boolean hasMoreElements() {
				return count < elementCount;
			}
			public Object nextElement() {
				if (count < elementCount) {
					return elementData[count++];
				}
				throw new NoSuchElementException("Vector Enumeration");
			}
		};
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean contains(Object elem) {
		return indexOf(elem, 0) >= 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int indexOf(Object elem) {
		return indexOf(elem, 0);
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public int indexOf(Object elem, int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (elem == null) {
				for (int i = index; i < elementCount; i++)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = index; i < elementCount; i++)
					if (elem.equals(elementData[i]))
						return i;
			}
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public int lastIndexOf(Object elem) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			return lastIndexOf(elem, elementCount - 1);
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public int lastIndexOf(Object elem, int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (index >= elementCount)
				throw new IndexOutOfBoundsException(index + " >= " + elementCount);
			if (elem == null) {
				for (int i = index; i >= 0; i--)
					if (elementData[i] == null)
						return i;
			} else {
				for (int i = index; i >= 0; i--)
					if (elem.equals(elementData[i]))
						return i;
			}
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public Object elementAt(int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			}
			return elementData[index];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public Object firstElement() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (elementCount == 0) {
				throw new NoSuchElementException();
			}
			return elementData[0];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public Object lastElement() {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (elementCount == 0) {
				throw new NoSuchElementException();
			}
			return elementData[elementCount - 1];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * share(elementData) in alive", ensures = "pure(elementCount) * share(elementData) in alive")
	public void setElementAt(Object obj, int index) {
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
	public void removeElementAt(int index) {
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
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public void insertElementAt(Object obj, int index) {
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
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public void addElement(Object obj) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = obj;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) in alive", ensures = "share(modCount) in alive")
	public boolean removeElement(Object obj) {
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
	public void removeAllElements() {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		for (int i = 0; i < elementCount; i++)
			elementData[i] = null;
		elementCount = 0;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "unique(elementData) * pure(elementCount) * pure(modCount) in alive", ensures = "unique(elementData) * pure(elementCount) * pure(modCount) in alive")
	public Object clone() {
		try {
			Vector v = (Vector) super.clone();
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			v.elementData = new Object[elementCount];
			System.arraycopy(elementData, 0, v.elementData, 0, elementCount);
			v.modCount = 0;
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
			return v;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public Object[] toArray() {
		capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
		Object[] result = new Object[elementCount];
		System.arraycopy(elementData, 0, result, 0, elementCount);
		capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		return result;
	}
	@Perm(requires = "unique(elementCount) * pure(elementData) in alive", ensures = "unique(elementCount) * pure(elementData) in alive")
	public Object[] toArray(Object a[]) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		if (a.length < elementCount)
			a = (Object[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), elementCount);
		System.arraycopy(elementData, 0, a, 0, elementCount);
		if (a.length > elementCount)
			a[elementCount] = null;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		return a;
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public Object get(int index) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().lock();
			if (index >= elementCount)
				throw new ArrayIndexOutOfBoundsException(index);
			return elementData[index];
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * share(elementData) in alive", ensures = "pure(elementCount) * share(elementData) in alive")
	public Object set(int index, Object element) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			if (index >= elementCount)
				throw new ArrayIndexOutOfBoundsException(index);
			Object oldValue = elementData[index];
			elementData[index] = element;
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
		return oldValue;
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public boolean add(Object o) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = o;
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean remove(Object o) {
		try {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
			return removeElement(o);
		} finally {
			capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(int index, Object element) {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		insertElementAt(element, index);
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public Object remove(int index) {
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
		return oldValue;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void clear() {
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().lock();
		removeAllElements();
		capacityIncrement_elementCount_elementData_modCountLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean containsAll(Collection c) {
		return super.containsAll(c);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public boolean addAll(Collection c) {
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
	public boolean removeAll(Collection c) {
		return super.removeAll(c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean retainAll(Collection c) {
		return super.retainAll(c);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	public boolean addAll(int index, Collection c) {
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
	public boolean equals(Object o) {
		return super.equals(o);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public int hashCode() {
		return super.hashCode();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public String toString() {
		return super.toString();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public List subList(int fromIndex, int toIndex) {
		return Collections.synchronizedList(super.subList(fromIndex, toIndex), this);
	}
	@Perm(requires = "share(modCount) * share(elementCount) * unique(elementData) in alive", ensures = "share(modCount) * share(elementCount) * unique(elementData) in alive")
	protected void removeRange(int fromIndex, int toIndex) {
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
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		s.defaultWriteObject();
	}
}
