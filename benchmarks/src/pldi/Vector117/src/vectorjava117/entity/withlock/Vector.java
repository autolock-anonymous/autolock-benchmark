package vectorjava117.entity.withlock;
import java.util.NoSuchElementException;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Vector {
	public ReentrantReadWriteLock capacityIncrement_elementCount_elementDataLock = new ReentrantReadWriteLock();
	protected Object elementData[];
	protected int elementCount;
	protected int capacityIncrement;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private void arraycopy(Object[] src, int src_pos, Object[] dest, int dest_pos, int length) {
		Object[] tmp = new Object[length];
		int i;
		for (i = 0; i < length; i++)
			tmp[i] = src[src_pos + i];
		for (i = 0; i < length; i++)
			dest[dest_pos + i] = tmp[i];
	}
	@Perm(requires = "unique(elementData) * unique(capacityIncrement) in alive", ensures = "unique(elementData) * unique(capacityIncrement) in alive")
	public Vector(int initialCapacity, int capacityIncrement) {
		super();
		capacityIncrement_elementCount_elementDataLock.writeLock().lock();
		this.elementData = new Object[initialCapacity];
		this.capacityIncrement = capacityIncrement;
		capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Vector(int initialCapacity) {
		this(initialCapacity, 0);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Vector() {
		this(10);
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public final synchronized void copyInto(Object anArray[]) {
		capacityIncrement_elementCount_elementDataLock.readLock().lock();
		int i = elementCount;
		capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		while (i-- > 0) {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			anArray[i] = elementData[i];
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "unique(elementData) * pure(elementCount) in alive", ensures = "unique(elementData) * pure(elementCount) in alive")
	public final synchronized void trimToSize() {
		capacityIncrement_elementCount_elementDataLock.writeLock().lock();
		int oldCapacity = elementData.length;
		if (elementCount < oldCapacity) {
			Object oldData[] = elementData;
			elementData = new Object[elementCount];
			arraycopy(oldData, 0, elementData, 0, elementCount);
		}
		capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
	}
	@Perm(requires = "unique(elementData) * immutable(capacityIncrement) * pure(elementCount) in alive", ensures = "unique(elementData) * immutable(capacityIncrement) * pure(elementCount) in alive")
	public final synchronized void ensureCapacity(int minCapacity) {
		capacityIncrement_elementCount_elementDataLock.writeLock().lock();
		int oldCapacity = elementData.length;
		if (minCapacity > oldCapacity) {
			Object oldData[] = elementData;
			int newCapacity = (capacityIncrement > 0) ? (oldCapacity + capacityIncrement) : (oldCapacity * 2);
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			elementData = new Object[newCapacity];
			arraycopy(oldData, 0, elementData, 0, elementCount);
		}
		capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
	}
	@Perm(requires = "share(elementCount) * unique(elementData) in alive", ensures = "share(elementCount) * unique(elementData) in alive")
	public final synchronized void setSize(int newSize) {
		capacityIncrement_elementCount_elementDataLock.writeLock().lock();
		if (newSize > elementCount) {
			ensureCapacity(newSize);
		} else {
			for (int i = newSize; i < elementCount; i++) {
				elementData[i] = null;
			}
		}
		elementCount = newSize;
		capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
	}
	@Perm(requires = "pure(elementData) in alive", ensures = "pure(elementData) in alive")
	public final int capacity() {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			return elementData.length;
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public final int size() {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			return elementCount;
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public final boolean isEmpty() {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			return elementCount == 0;
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public final boolean contains(Object elem) {
		return indexOf(elem, 0) >= 0;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public final int indexOf1(Object elem) {
		return indexOf(elem, 0);
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public final synchronized int indexOf(Object elem, int index) {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			for (int i = index; i < elementCount; i++) {
				if (elem.equals(elementData[i])) {
					return i;
				}
			}
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(elementCount) in alive", ensures = "pure(elementCount) in alive")
	public final int lastIndexOf(Object elem) {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			return lastIndexOf(elem, elementCount - 1);
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementData) in alive", ensures = "pure(elementData) in alive")
	public final synchronized int lastIndexOf(Object elem, int index) {
		for (int i = index; i >= 0; i--) {
			try {
				capacityIncrement_elementCount_elementDataLock.readLock().lock();
				if (elem.equals(elementData[i])) {
					return i;
				}
			} finally {
				capacityIncrement_elementCount_elementDataLock.readLock().unlock();
			}
		}
		return -1;
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public final synchronized Object elementAt(int index) {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			}
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
		try {
			try {
				capacityIncrement_elementCount_elementDataLock.readLock().lock();
				return elementData[index];
			} finally {
				capacityIncrement_elementCount_elementDataLock.readLock().unlock();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException(index + " < 0");
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public final synchronized Object firstElement() {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			if (elementCount == 0) {
				throw new NoSuchElementException();
			}
			return elementData[0];
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * pure(elementData) in alive", ensures = "pure(elementCount) * pure(elementData) in alive")
	public final synchronized Object lastElement() {
		try {
			capacityIncrement_elementCount_elementDataLock.readLock().lock();
			if (elementCount == 0) {
				throw new NoSuchElementException();
			}
			return elementData[elementCount - 1];
		} finally {
			capacityIncrement_elementCount_elementDataLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(elementCount) * share(elementData) in alive", ensures = "pure(elementCount) * share(elementData) in alive")
	public final synchronized void setElementAt(Object obj, int index) {
		try {
			capacityIncrement_elementCount_elementDataLock.writeLock().lock();
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			}
			elementData[index] = obj;
		} finally {
			capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
		}
	}
	@Perm(requires = "unique(elementCount) * unique(elementData) in alive", ensures = "unique(elementCount) * unique(elementData) in alive")
	public final synchronized void removeElementAt(int index) {
		try {
			capacityIncrement_elementCount_elementDataLock.writeLock().lock();
			if (index >= elementCount) {
				throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
			} else if (index < 0) {
				throw new ArrayIndexOutOfBoundsException(index);
			}
			int j = elementCount - index - 1;
			if (j > 0) {
				arraycopy(elementData, index + 1, elementData, index, j);
			}
			elementCount--;
			elementData[elementCount] = null;
		} finally {
			capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(elementCount) * unique(elementData) in alive", ensures = "share(elementCount) * unique(elementData) in alive")
	public final synchronized void insertElementAt(Object obj, int index) {
		try {
			capacityIncrement_elementCount_elementDataLock.writeLock().lock();
			if (index >= elementCount + 1) {
				throw new ArrayIndexOutOfBoundsException(index + " > " + elementCount);
			}
			ensureCapacity(elementCount + 1);
			arraycopy(elementData, index, elementData, index + 1, elementCount - index);
			elementData[index] = obj;
			elementCount++;
		} finally {
			capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(elementCount) * unique(elementData) in alive", ensures = "share(elementCount) * unique(elementData) in alive")
	public final synchronized void addElement(Object obj) {
		capacityIncrement_elementCount_elementDataLock.writeLock().lock();
		ensureCapacity(elementCount + 1);
		elementData[elementCount++] = obj;
		capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public final synchronized boolean removeElement(Object obj) {
		int i = indexOf1(obj);
		if (i >= 0) {
			capacityIncrement_elementCount_elementDataLock.writeLock().lock();
			removeElementAt(i);
			capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
			return true;
		}
		return false;
	}
	@Perm(requires = "share(elementCount) * unique(elementData) in alive", ensures = "share(elementCount) * unique(elementData) in alive")
	public final synchronized void removeAllElements() {
		capacityIncrement_elementCount_elementDataLock.writeLock().lock();
		for (int i = 0; i < elementCount; i++) {
			elementData[i] = null;
		}
		elementCount = 0;
		capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
	}
	@Perm(requires = "unique(elementData) * pure(elementCount) in alive", ensures = "unique(elementData) * pure(elementCount) in alive")
	public synchronized Object clone() {
		try {
			Vector v = (Vector) super.clone();
			capacityIncrement_elementCount_elementDataLock.writeLock().lock();
			v.elementData = new Object[elementCount];
			arraycopy(elementData, 0, v.elementData, 0, elementCount);
			capacityIncrement_elementCount_elementDataLock.writeLock().unlock();
			return v;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
}
