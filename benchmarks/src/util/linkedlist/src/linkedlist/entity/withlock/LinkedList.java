package linkedlist.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class LinkedList<E> extends AbstractSequentialList<E>
		implements
			List<E>,
			Deque<E>,
			Cloneable,
			java.io.Serializable {
	public ReentrantReadWriteLock element_header_modCount_next_previous_sizeLock = new ReentrantReadWriteLock();
	private transient Entry<E> header = new Entry<E>(null, null, null);
	private transient int size = 0;
	@Perm(requires = "none(previous) * unique(header) * none(header) * none(next) * none(previous) in alive", ensures = "none(previous) * unique(header) * none(header) * none(next) * none(previous) in alive")
	public LinkedList() {
		header.next = header.previous = header;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public LinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}
	@Perm(requires = "pure(size) * immutable(header) * immutable(next) * pure(element) in alive", ensures = "pure(size) * immutable(header) * immutable(next) * pure(element) in alive")
	public E getFirst() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				throw new NoSuchElementException();
			return header.next.element;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(size) * immutable(header) * immutable(previous) * pure(element) in alive", ensures = "pure(size) * immutable(header) * immutable(previous) * pure(element) in alive")
	public E getLast() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				throw new NoSuchElementException();
			return header.previous.element;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "immutable(header) * immutable(next) in alive", ensures = "immutable(header) * immutable(next) in alive")
	public E removeFirst() {
		return remove(header.next);
	}
	@Perm(requires = "immutable(header) * immutable(previous) in alive", ensures = "immutable(header) * immutable(previous) in alive")
	public E removeLast() {
		return remove(header.previous);
	}
	@Perm(requires = "immutable(header) * immutable(next) in alive", ensures = "immutable(header) * immutable(next) in alive")
	public void addFirst(E e) {
		addBefore(e, header.next);
	}
	@Perm(requires = "immutable(header) in alive", ensures = "immutable(header) in alive")
	public void addLast(E e) {
		addBefore(e, header);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public int size() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			return size;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "immutable(header) in alive", ensures = "immutable(header) in alive")
	public boolean add(E e) {
		addBefore(e, header);
		return true;
	}
	@Perm(requires = "immutable(header) * immutable(next) * share(next) * pure(element) in alive", ensures = "immutable(header) * immutable(next) * share(next) * pure(element) in alive")
	public boolean remove(Object o) {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			element_header_modCount_next_previous_sizeLock.writeLock().lock();
			if (o == null) {
				for (Entry<E> e = header.next; e != header; e = e.next) {
					if (e.element == null) {
						remove(e);
						return true;
					}
				}
			} else {
				for (Entry<E> e = header.next; e != header; e = e.next) {
					if (o.equals(e.element)) {
						remove(e);
						return true;
					}
				}
			}
		} finally {
			element_header_modCount_next_previous_sizeLock.writeLock().unlock();
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
		return false;
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public boolean addAll(Collection<? extends E> c) {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			return addAll(size, c);
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(size) * share(modCount) * immutable(header) * share(previous) * immutable(previous) * share(next) * immutable(next) in alive", ensures = "share(size) * share(modCount) * immutable(header) * share(previous) * immutable(previous) * share(next) * immutable(next) in alive")
	public boolean addAll(int index, Collection<? extends E> c) {
		try {
			element_header_modCount_next_previous_sizeLock.writeLock().lock();
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
			Object[] a = c.toArray();
			int numNew = a.length;
			if (numNew == 0)
				return false;
			modCount++;
			Entry<E> successor = (index == size ? header : entry(index));
			Entry<E> predecessor = successor.previous;
			for (int i = 0; i < numNew; i++) {
				Entry<E> e = new Entry<E>((E) a[i], successor, predecessor);
				predecessor.next = e;
				predecessor = e;
			}
			successor.previous = predecessor;
			size += numNew;
		} finally {
			element_header_modCount_next_previous_sizeLock.writeLock().unlock();
		}
		return true;
	}
	@Perm(requires = "share(next) * immutable(header) * immutable(next) * unique(previous) * immutable(previous) * unique(element) * share(header) * share(size) * share(modCount) in alive", ensures = "share(next) * immutable(header) * immutable(next) * unique(previous) * immutable(previous) * unique(element) * share(header) * share(size) * share(modCount) in alive")
	public void clear() {
		element_header_modCount_next_previous_sizeLock.writeLock().lock();
		Entry<E> e = header.next;
		while (e != header) {
			Entry<E> next = e.next;
			e.next = e.previous = null;
			e.element = null;
			e = next;
		}
		header.next = header.previous = header;
		size = 0;
		modCount++;
		element_header_modCount_next_previous_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "pure(element) in alive", ensures = "pure(element) in alive")
	public E get(int index) {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			return entry(index).element;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(element) in alive", ensures = "pure(element) in alive")
	public E set(int index, E element) {
		Entry<E> e = entry(index);
		element_header_modCount_next_previous_sizeLock.readLock().lock();
		E oldVal = e.element;
		e.element = element;
		element_header_modCount_next_previous_sizeLock.readLock().unlock();
		return oldVal;
	}
	@Perm(requires = "pure(size) * immutable(header) in alive", ensures = "pure(size) * immutable(header) in alive")
	public void add(int index, E element) {
		element_header_modCount_next_previous_sizeLock.readLock().lock();
		addBefore(element, (index == size ? header : entry(index)));
		element_header_modCount_next_previous_sizeLock.readLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E remove(int index) {
		return remove(entry(index));
	}
	@Perm(requires = "pure(size) * share(header) * immutable(header) * share(next) * immutable(next) * pure(previous) * immutable(previous) in alive", ensures = "pure(size) * share(header) * immutable(header) * share(next) * immutable(next) * pure(previous) * immutable(previous) in alive")
	private Entry<E> entry(int index) {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (index < 0 || index >= size)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
			Entry<E> e = header;
			if (index < (size >> 1)) {
				for (int i = 0; i <= index; i++)
					e = e.next;
			} else {
				for (int i = size; i > index; i--)
					e = e.previous;
			}
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
		return e;
	}
	@Perm(requires = "immutable(header) * immutable(next) * full(next) * immutable(element) in alive", ensures = "immutable(header) * immutable(next) * full(next) * immutable(element) in alive")
	public int indexOf(Object o) {
		int index = 0;
		try {
			element_header_modCount_next_previous_sizeLock.writeLock().lock();
			if (o == null) {
				for (Entry e = header.next; e != header; e = e.next) {
					if (e.element == null)
						return index;
					index++;
				}
			} else {
				for (Entry e = header.next; e != header; e = e.next) {
					if (o.equals(e.element))
						return index;
					index++;
				}
			}
		} finally {
			element_header_modCount_next_previous_sizeLock.writeLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(size) * immutable(header) * immutable(previous) * unique(previous) * immutable(element) in alive", ensures = "pure(size) * immutable(header) * immutable(previous) * unique(previous) * immutable(element) in alive")
	public int lastIndexOf(Object o) {
		try {
			element_header_modCount_next_previous_sizeLock.writeLock().lock();
			int index = size;
			if (o == null) {
				for (Entry e = header.previous; e != header; e = e.previous) {
					index--;
					if (e.element == null)
						return index;
				}
			} else {
				for (Entry e = header.previous; e != header; e = e.previous) {
					index--;
					if (o.equals(e.element))
						return index;
				}
			}
		} finally {
			element_header_modCount_next_previous_sizeLock.writeLock().unlock();
		}
		return -1;
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public E peek() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				return null;
			return getFirst();
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E element() {
		return getFirst();
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public E poll() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				return null;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
		return removeFirst();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E remove() {
		return removeFirst();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean offer(E e) {
		return add(e);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public E peekFirst() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				return null;
			return getFirst();
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public E peekLast() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				return null;
			return getLast();
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public E pollFirst() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				return null;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
		return removeFirst();
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public E pollLast() {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			if (size == 0)
				return null;
		} finally {
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
		return removeLast();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void push(E e) {
		addFirst(e);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public E pop() {
		return removeFirst();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}
	@Perm(requires = "immutable(header) * immutable(previous) * share(previous) * pure(element) in alive", ensures = "immutable(header) * immutable(previous) * share(previous) * pure(element) in alive")
	public boolean removeLastOccurrence(Object o) {
		try {
			element_header_modCount_next_previous_sizeLock.readLock().lock();
			element_header_modCount_next_previous_sizeLock.writeLock().lock();
			if (o == null) {
				for (Entry<E> e = header.previous; e != header; e = e.previous) {
					if (e.element == null) {
						remove(e);
						return true;
					}
				}
			} else {
				for (Entry<E> e = header.previous; e != header; e = e.previous) {
					if (o.equals(e.element)) {
						remove(e);
						return true;
					}
				}
			}
		} finally {
			element_header_modCount_next_previous_sizeLock.writeLock().unlock();
			element_header_modCount_next_previous_sizeLock.readLock().unlock();
		}
		return false;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public ListIterator<E> listIterator(int index) {
		return new ListItr(index);
	}
	private class ListItr implements ListIterator<E> {
		public ReentrantReadWriteLock modCountLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock = new ReentrantReadWriteLock();
		private Entry<E> lastReturned = header;
		private Entry<E> next;
		private int nextIndex;
		private int expectedModCount = modCount;
		@Perm(requires = "none(size) * none(next) * unique(next) * none(next) * none(header) * none(next) * unique(nextIndex) * unique(header) * unique(previous) * none(previous) in alive", ensures = "none(size) * none(next) * unique(next) * none(next) * none(header) * none(next) * unique(nextIndex) * unique(header) * unique(previous) * none(previous) in alive")
		ListItr(int index) {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().lock();
				if (index < 0 || index > size)
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
				if (index < (size >> 1)) {
					next = header.next;
					for (nextIndex = 0; nextIndex < index; nextIndex++)
						next = next.next;
				} else {
					next = header;
					for (nextIndex = size; nextIndex > index; nextIndex--)
						next = next.previous;
				}
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().unlock();
			}
		}
		@Perm(requires = "pure(nextIndex) * pure(size) in alive", ensures = "pure(nextIndex) * pure(size) in alive")
		public boolean hasNext() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().lock();
				return nextIndex != size;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "share(nextIndex) * pure(size) * pure(lastReturned) * share(next) * immutable(lastReturned) * immutable(next) * share(next) * immutable(next) * pure(element) in alive", ensures = "share(nextIndex) * pure(size) * pure(lastReturned) * share(next) * immutable(lastReturned) * immutable(next) * share(next) * immutable(next) * pure(element) in alive")
		public E next() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().lock();
				checkForComodification();
				if (nextIndex == size)
					throw new NoSuchElementException();
				lastReturned = next;
				next = next.next;
				nextIndex++;
				return lastReturned.element;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().unlock();
			}
		}
		@Perm(requires = "pure(nextIndex) in alive", ensures = "pure(nextIndex) in alive")
		public boolean hasPrevious() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().lock();
				return nextIndex != 0;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "share(nextIndex) * pure(lastReturned) * share(next) * share(previous) * immutable(lastReturned) * immutable(next) * immutable(previous) * pure(element) in alive", ensures = "share(nextIndex) * pure(lastReturned) * share(next) * share(previous) * immutable(lastReturned) * immutable(next) * immutable(previous) * pure(element) in alive")
		public E previous() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().lock();
				if (nextIndex == 0)
					throw new NoSuchElementException();
				lastReturned = next = next.previous;
				nextIndex--;
				checkForComodification();
				return lastReturned.element;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().unlock();
			}
		}
		@Perm(requires = "pure(nextIndex) in alive", ensures = "pure(nextIndex) in alive")
		public int nextIndex() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().lock();
				return nextIndex;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(nextIndex) in alive", ensures = "pure(nextIndex) in alive")
		public int previousIndex() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().lock();
				return nextIndex - 1;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(next) * immutable(lastReturned) * immutable(next) * immutable(next) * share(next) * share(nextIndex) * share(lastReturned) * share(header) * immutable(header) * share(expectedModCount) in alive", ensures = "pure(next) * immutable(lastReturned) * immutable(next) * immutable(next) * share(next) * share(nextIndex) * share(lastReturned) * share(header) * immutable(header) * share(expectedModCount) in alive")
		public void remove() {
			try {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().lock();
				checkForComodification();
				Entry<E> lastNext = lastReturned.next;
				try {
					LinkedList.this.remove(lastReturned);
				} catch (NoSuchElementException e) {
					throw new IllegalStateException();
				}
				if (next == lastReturned)
					next = lastNext;
				else
					nextIndex--;
				lastReturned = header;
				expectedModCount++;
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(lastReturned) * immutable(header) * pure(element) in alive", ensures = "immutable(lastReturned) * immutable(header) * pure(element) in alive")
		public void set(E e) {
			if (lastReturned == header)
				throw new IllegalStateException();
			checkForComodification();
			element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().lock();
			lastReturned.element = e;
			element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().unlock();
		}
		@Perm(requires = "share(lastReturned) * share(header) * immutable(lastReturned) * immutable(header) * immutable(next) * share(nextIndex) * share(expectedModCount) in alive", ensures = "share(lastReturned) * share(header) * immutable(lastReturned) * immutable(header) * immutable(next) * share(nextIndex) * share(expectedModCount) in alive")
		public void add(E e) {
			element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().lock();
			checkForComodification();
			lastReturned = header;
			addBefore(e, next);
			nextIndex++;
			expectedModCount++;
			element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.writeLock().unlock();
		}
		@Perm(requires = "pure(modCount) * pure(expectedModCount) in alive", ensures = "pure(modCount) * pure(expectedModCount) in alive")
		final void checkForComodification() {
			try {
				modCountLock.readLock().lock();
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
			} finally {
				element_expectedModCount_header_lastReturned_next_nextIndex_previous_sizeLock.readLock().unlock();
				modCountLock.readLock().unlock();
			}
		}
	}
	private static class Entry<E> {
		public ReentrantReadWriteLock element_next_previousLock = new ReentrantReadWriteLock();
		E element;
		Entry<E> next;
		Entry<E> previous;
		@Perm(requires = "unique(element) * unique(next) * none(next) * unique(previous) * none(previous) in alive", ensures = "unique(element) * unique(next) * none(next) * unique(previous) * none(previous) in alive")
		Entry(E element, Entry<E> next, Entry<E> previous) {
			element_next_previousLock.writeLock().lock();
			this.element = element;
			this.next = next;
			this.previous = previous;
			element_next_previousLock.writeLock().unlock();
		}
	}
	private Entry<E> addBefore(E e, Entry<E> entry) {
		Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
		newEntry.previous.next = newEntry;
		newEntry.next.previous = newEntry;
		size++;
		modCount++;
		return newEntry;
	}
	private E remove(Entry<E> e) {
		if (e == header)
			throw new NoSuchElementException();
		E result = e.element;
		e.previous.next = e.next;
		e.next.previous = e.previous;
		e.next = e.previous = null;
		e.element = null;
		size--;
		modCount++;
		return result;
	}
	public Iterator<E> descendingIterator() {
		return new DescendingIterator();
	}
	private class DescendingIterator implements Iterator {
		final ListItr itr = new ListItr(size());
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean hasNext() {
			return itr.hasPrevious();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E next() {
			return itr.previous();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void remove() {
			itr.remove();
		}
	}
	public Object clone() {
		LinkedList<E> clone = null;
		try {
			clone = (LinkedList<E>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		clone.header = new Entry<E>(null, null, null);
		clone.header.next = clone.header.previous = clone.header;
		clone.size = 0;
		clone.modCount = 0;
		for (Entry<E> e = header.next; e != header; e = e.next)
			clone.add(e.element);
		return clone;
	}
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (Entry<E> e = header.next; e != header; e = e.next)
			result[i++] = e.element;
		return result;
	}
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		int i = 0;
		Object[] result = a;
		for (Entry<E> e = header.next; e != header; e = e.next)
			result[i++] = e.element;
		if (a.length > size)
			a[size] = null;
		return a;
	}
	private static final long serialVersionUID = 876323262645176354L;
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		s.defaultWriteObject();
		s.writeInt(size);
		for (Entry e = header.next; e != header; e = e.next)
			s.writeObject(e.element);
	}
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		int size = s.readInt();
		header = new Entry<E>(null, null, null);
		header.next = header.previous = header;
		for (int i = 0; i < size; i++)
			addBefore((E) s.readObject(), header);
	}
}
