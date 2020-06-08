package synchronizedmapjava16.entity.withlock;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Set;
import java.util.TreeMap;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Collections {
	public static ReentrantReadWriteLock rLock = new ReentrantReadWriteLock();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private Collections() {
	}
	private static final int BINARYSEARCH_THRESHOLD = 5000;
	private static final int REVERSE_THRESHOLD = 18;
	private static final int SHUFFLE_THRESHOLD = 5;
	private static final int FILL_THRESHOLD = 25;
	private static final int ROTATE_THRESHOLD = 100;
	private static final int COPY_THRESHOLD = 10;
	private static final int REPLACEALL_THRESHOLD = 11;
	private static final int INDEXOFSUBLIST_THRESHOLD = 35;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T extends Comparable<? super T>> void sort(List<T> list) {
		Object[] a = list.toArray();
		Arrays.sort(a);
		ListIterator<T> i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set((T) a[j]);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		Object[] a = list.toArray();
		Arrays.sort(a, (Comparator) c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
		if (list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD)
			return Collections.indexedBinarySearch(list, key);
		else
			return Collections.iteratorBinarySearch(list, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static <T> int indexedBinarySearch(List<? extends Comparable<? super T>> list, T key) {
		int low = 0;
		int high = list.size() - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			Comparable<? super T> midVal = list.get(mid);
			int cmp = midVal.compareTo(key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static <T> int iteratorBinarySearch(List<? extends Comparable<? super T>> list, T key) {
		int low = 0;
		int high = list.size() - 1;
		ListIterator<? extends Comparable<? super T>> i = list.listIterator();
		while (low <= high) {
			int mid = (low + high) >>> 1;
			Comparable<? super T> midVal = get(i, mid);
			int cmp = midVal.compareTo(key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static <T> T get(ListIterator<? extends T> i, int index) {
		T obj = null;
		int pos = i.nextIndex();
		if (pos <= index) {
			do {
				obj = i.next();
			} while (pos++ < index);
		} else {
			do {
				obj = i.previous();
			} while (--pos > index);
		}
		return obj;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
		if (c == null)
			return binarySearch((List) list, key);
		if (list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD)
			return Collections.indexedBinarySearch(list, key, c);
		else
			return Collections.iteratorBinarySearch(list, key, c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static <T> int indexedBinarySearch(List<? extends T> l, T key, Comparator<? super T> c) {
		int low = 0;
		int high = l.size() - 1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			T midVal = l.get(mid);
			int cmp = c.compare(midVal, key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static <T> int iteratorBinarySearch(List<? extends T> l, T key, Comparator<? super T> c) {
		int low = 0;
		int high = l.size() - 1;
		ListIterator<? extends T> i = l.listIterator();
		while (low <= high) {
			int mid = (low + high) >>> 1;
			T midVal = get(i, mid);
			int cmp = c.compare(midVal, key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
	private interface SelfComparable extends Comparable<SelfComparable> {
	}
	public static void reverse(List<?> list) {
		int size = list.size();
		if (size < REVERSE_THRESHOLD || list instanceof RandomAccess) {
			for (int i = 0, mid = size >> 1, j = size - 1; i < mid; i++, j--)
				swap(list, i, j);
		} else {
			ListIterator fwd = list.listIterator();
			ListIterator rev = list.listIterator(size);
			for (int i = 0, mid = list.size() >> 1; i < mid; i++) {
				Object tmp = fwd.next();
				fwd.set(rev.previous());
				rev.set(tmp);
			}
		}
	}
	public static void shuffle(List<?> list) {
		rLock.writeLock().lock();
		if (r == null) {
			r = new Random();
		}
		shuffle(list, r);
		rLock.writeLock().unlock();
	}
	private static Random r;
	public static void shuffle(List<?> list, Random rnd) {
		int size = list.size();
		if (size < SHUFFLE_THRESHOLD || list instanceof RandomAccess) {
			for (int i = size; i > 1; i--)
				swap(list, i - 1, rnd.nextInt(i));
		} else {
			Object arr[] = list.toArray();
			for (int i = size; i > 1; i--)
				swap(arr, i - 1, rnd.nextInt(i));
			ListIterator it = list.listIterator();
			for (int i = 0; i < arr.length; i++) {
				it.next();
				it.set(arr[i]);
			}
		}
	}
	public static void swap(List<?> list, int i, int j) {
		final List l = list;
		l.set(i, l.set(j, l.get(i)));
	}
	private static void swap(Object[] arr, int i, int j) {
		Object tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	public static <T> void fill(List<? super T> list, T obj) {
		int size = list.size();
		if (size < FILL_THRESHOLD || list instanceof RandomAccess) {
			for (int i = 0; i < size; i++)
				list.set(i, obj);
		} else {
			ListIterator<? super T> itr = list.listIterator();
			for (int i = 0; i < size; i++) {
				itr.next();
				itr.set(obj);
			}
		}
	}
	public static <T> void copy(List<? super T> dest, List<? extends T> src) {
		int srcSize = src.size();
		if (srcSize > dest.size())
			throw new IndexOutOfBoundsException("Source does not fit in dest");
		if (srcSize < COPY_THRESHOLD || (src instanceof RandomAccess && dest instanceof RandomAccess)) {
			for (int i = 0; i < srcSize; i++)
				dest.set(i, src.get(i));
		} else {
			ListIterator<? super T> di = dest.listIterator();
			ListIterator<? extends T> si = src.listIterator();
			for (int i = 0; i < srcSize; i++) {
				di.next();
				di.set(si.next());
			}
		}
	}
	public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll) {
		Iterator<? extends T> i = coll.iterator();
		T candidate = i.next();
		while (i.hasNext()) {
			T next = i.next();
			if (next.compareTo(candidate) < 0)
				candidate = next;
		}
		return candidate;
	}
	public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
		if (comp == null)
			return (T) min((Collection<SelfComparable>) (Collection) coll);
		Iterator<? extends T> i = coll.iterator();
		T candidate = i.next();
		while (i.hasNext()) {
			T next = i.next();
			if (comp.compare(next, candidate) < 0)
				candidate = next;
		}
		return candidate;
	}
	public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
		Iterator<? extends T> i = coll.iterator();
		T candidate = i.next();
		while (i.hasNext()) {
			T next = i.next();
			if (next.compareTo(candidate) > 0)
				candidate = next;
		}
		return candidate;
	}
	public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
		if (comp == null)
			return (T) max((Collection<SelfComparable>) (Collection) coll);
		Iterator<? extends T> i = coll.iterator();
		T candidate = i.next();
		while (i.hasNext()) {
			T next = i.next();
			if (comp.compare(next, candidate) > 0)
				candidate = next;
		}
		return candidate;
	}
	public static void rotate(List<?> list, int distance) {
		if (list instanceof RandomAccess || list.size() < ROTATE_THRESHOLD)
			rotate1((List) list, distance);
		else
			rotate2((List) list, distance);
	}
	private static <T> void rotate1(List<T> list, int distance) {
		int size = list.size();
		if (size == 0)
			return;
		distance = distance % size;
		if (distance < 0)
			distance += size;
		if (distance == 0)
			return;
		for (int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++) {
			T displaced = list.get(cycleStart);
			int i = cycleStart;
			do {
				i += distance;
				if (i >= size)
					i -= size;
				displaced = list.set(i, displaced);
				nMoved++;
			} while (i != cycleStart);
		}
	}
	private static void rotate2(List<?> list, int distance) {
		int size = list.size();
		if (size == 0)
			return;
		int mid = -distance % size;
		if (mid < 0)
			mid += size;
		if (mid == 0)
			return;
		reverse(list.subList(0, mid));
		reverse(list.subList(mid, size));
		reverse(list);
	}
	public static <T> boolean replaceAll(List<T> list, T oldVal, T newVal) {
		boolean result = false;
		int size = list.size();
		if (size < REPLACEALL_THRESHOLD || list instanceof RandomAccess) {
			if (oldVal == null) {
				for (int i = 0; i < size; i++) {
					if (list.get(i) == null) {
						list.set(i, newVal);
						result = true;
					}
				}
			} else {
				for (int i = 0; i < size; i++) {
					if (oldVal.equals(list.get(i))) {
						list.set(i, newVal);
						result = true;
					}
				}
			}
		} else {
			ListIterator<T> itr = list.listIterator();
			if (oldVal == null) {
				for (int i = 0; i < size; i++) {
					if (itr.next() == null) {
						itr.set(newVal);
						result = true;
					}
				}
			} else {
				for (int i = 0; i < size; i++) {
					if (oldVal.equals(itr.next())) {
						itr.set(newVal);
						result = true;
					}
				}
			}
		}
		return result;
	}
	public static int indexOfSubList(List<?> source, List<?> target) {
		int sourceSize = source.size();
		int targetSize = target.size();
		int maxCandidate = sourceSize - targetSize;
		if (sourceSize < INDEXOFSUBLIST_THRESHOLD
				|| (source instanceof RandomAccess && target instanceof RandomAccess)) {
			nextCand : for (int candidate = 0; candidate <= maxCandidate; candidate++) {
				for (int i = 0, j = candidate; i < targetSize; i++, j++)
					if (!eq(target.get(i), source.get(j)))
						continue nextCand;
				return candidate;
			}
		} else {
			ListIterator<?> si = source.listIterator();
			nextCand : for (int candidate = 0; candidate <= maxCandidate; candidate++) {
				ListIterator<?> ti = target.listIterator();
				for (int i = 0; i < targetSize; i++) {
					if (!eq(ti.next(), si.next())) {
						for (int j = 0; j < i; j++)
							si.previous();
						continue nextCand;
					}
				}
				return candidate;
			}
		}
		return -1;
	}
	public static int lastIndexOfSubList(List<?> source, List<?> target) {
		int sourceSize = source.size();
		int targetSize = target.size();
		int maxCandidate = sourceSize - targetSize;
		if (sourceSize < INDEXOFSUBLIST_THRESHOLD || source instanceof RandomAccess) {
			nextCand : for (int candidate = maxCandidate; candidate >= 0; candidate--) {
				for (int i = 0, j = candidate; i < targetSize; i++, j++)
					if (!eq(target.get(i), source.get(j)))
						continue nextCand;
				return candidate;
			}
		} else {
			if (maxCandidate < 0)
				return -1;
			ListIterator<?> si = source.listIterator(maxCandidate);
			nextCand : for (int candidate = maxCandidate; candidate >= 0; candidate--) {
				ListIterator<?> ti = target.listIterator();
				for (int i = 0; i < targetSize; i++) {
					if (!eq(ti.next(), si.next())) {
						if (candidate != 0) {
							for (int j = 0; j <= i + 1; j++)
								si.previous();
						}
						continue nextCand;
					}
				}
				return candidate;
			}
		}
		return -1;
	}
	public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c) {
		return new UnmodifiableCollection<T>(c);
	}
	static class UnmodifiableCollection<E> implements Collection<E>, Serializable {
		private static final long serialVersionUID = 1820017752578914078L;
		final Collection<? extends E> c;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableCollection(Collection<? extends E> c) {
			if (c == null)
				throw new NullPointerException();
			this.c = c;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return c.size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return c.isEmpty();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return c.contains(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object[] toArray() {
			return c.toArray();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public <T> T[] toArray(T[] a) {
			return c.toArray(a);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			return c.toString();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<E> iterator() {
			return new Iterator<E>() {
				Iterator<? extends E> i = c.iterator();
				public boolean hasNext() {
					return i.hasNext();
				}
				public E next() {
					return i.next();
				}
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean add(E e) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsAll(Collection<?> coll) {
			return c.containsAll(coll);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(Collection<? extends E> coll) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean removeAll(Collection<?> coll) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean retainAll(Collection<?> coll) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			throw new UnsupportedOperationException();
		}
	}
	public static <T> Set<T> unmodifiableSet(Set<? extends T> s) {
		return new UnmodifiableSet<T>(s);
	}
	static class UnmodifiableSet<E> extends UnmodifiableCollection<E> implements Set<E>, Serializable {
		private static final long serialVersionUID = -9215047833775013803L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableSet(Set<? extends E> s) {
			super(s);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return o == this || c.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return c.hashCode();
		}
	}
	public static <T> SortedSet<T> unmodifiableSortedSet(SortedSet<T> s) {
		return new UnmodifiableSortedSet<T>(s);
	}
	static class UnmodifiableSortedSet<E> extends UnmodifiableSet<E> implements SortedSet<E>, Serializable {
		private static final long serialVersionUID = -4929149591599911165L;
		private final SortedSet<E> ss;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableSortedSet(SortedSet<E> s) {
			super(s);
			ss = s;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super E> comparator() {
			return ss.comparator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> subSet(E fromElement, E toElement) {
			return new UnmodifiableSortedSet<E>(ss.subSet(fromElement, toElement));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> headSet(E toElement) {
			return new UnmodifiableSortedSet<E>(ss.headSet(toElement));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> tailSet(E fromElement) {
			return new UnmodifiableSortedSet<E>(ss.tailSet(fromElement));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E first() {
			return ss.first();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E last() {
			return ss.last();
		}
	}
	public static <T> List<T> unmodifiableList(List<? extends T> list) {
		return (list instanceof RandomAccess
				? new UnmodifiableRandomAccessList<T>(list)
				: new UnmodifiableList<T>(list));
	}
	static class UnmodifiableList<E> extends UnmodifiableCollection<E> implements List<E> {
		static final long serialVersionUID = -283967356065247728L;
		final List<? extends E> list;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableList(List<? extends E> list) {
			super(list);
			this.list = list;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return o == this || list.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return list.hashCode();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E get(int index) {
			return list.get(index);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E set(int index, E element) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void add(int index, E element) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E remove(int index) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int indexOf(Object o) {
			return list.indexOf(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int lastIndexOf(Object o) {
			return list.lastIndexOf(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(int index, Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator<E> listIterator() {
			return listIterator(0);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator<E> listIterator(final int index) {
			return new ListIterator<E>() {
				ListIterator<? extends E> i = list.listIterator(index);
				public boolean hasNext() {
					return i.hasNext();
				}
				public E next() {
					return i.next();
				}
				public boolean hasPrevious() {
					return i.hasPrevious();
				}
				public E previous() {
					return i.previous();
				}
				public int nextIndex() {
					return i.nextIndex();
				}
				public int previousIndex() {
					return i.previousIndex();
				}
				public void remove() {
					throw new UnsupportedOperationException();
				}
				public void set(E e) {
					throw new UnsupportedOperationException();
				}
				public void add(E e) {
					throw new UnsupportedOperationException();
				}
			};
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			return new UnmodifiableList<E>(list.subList(fromIndex, toIndex));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object readResolve() {
			return (list instanceof RandomAccess ? new UnmodifiableRandomAccessList<E>(list) : this);
		}
	}
	static class UnmodifiableRandomAccessList<E> extends UnmodifiableList<E> implements RandomAccess {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableRandomAccessList(List<? extends E> list) {
			super(list);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			return new UnmodifiableRandomAccessList<E>(list.subList(fromIndex, toIndex));
		}
		private static final long serialVersionUID = -2542308836966382001L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object writeReplace() {
			return new UnmodifiableList<E>(list);
		}
	}
	public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> m) {
		return new UnmodifiableMap<K, V>(m);
	}
	private static class UnmodifiableMap<K, V> implements Map<K, V>, Serializable {
		public ReentrantReadWriteLock entrySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock keySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock valuesLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = -1034234728574286014L;
		private final Map<? extends K, ? extends V> m;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableMap(Map<? extends K, ? extends V> m) {
			if (m == null)
				throw new NullPointerException();
			this.m = m;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return m.size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return m.isEmpty();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsKey(Object key) {
			return m.containsKey(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsValue(Object val) {
			return m.containsValue(val);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V get(Object key) {
			return m.get(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V put(K key, V value) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V remove(Object key) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void putAll(Map<? extends K, ? extends V> m) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			throw new UnsupportedOperationException();
		}
		private transient Set<K> keySet = null;
		private transient Set<Map.Entry<K, V>> entrySet = null;
		private transient Collection<V> values = null;
		@Perm(requires = "none(keySet) * unique(keySet) in alive", ensures = "none(keySet) * unique(keySet) in alive")
		public Set<K> keySet() {
			try {
				keySetLock.writeLock().lock();
				if (keySet == null)
					keySet = unmodifiableSet(m.keySet());
				return keySet;
			} finally {
				keySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "none(entrySet) * unique(entrySet) in alive", ensures = "none(entrySet) * unique(entrySet) in alive")
		public Set<Map.Entry<K, V>> entrySet() {
			try {
				entrySetLock.writeLock().lock();
				if (entrySet == null)
					entrySet = new UnmodifiableEntrySet<K, V>(m.entrySet());
				return entrySet;
			} finally {
				entrySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "none(values) * unique(values) in alive", ensures = "none(values) * unique(values) in alive")
		public Collection<V> values() {
			try {
				valuesLock.writeLock().lock();
				if (values == null)
					values = unmodifiableCollection(m.values());
				return values;
			} finally {
				valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return o == this || m.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return m.hashCode();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			return m.toString();
		}
		static class UnmodifiableEntrySet<K, V> extends UnmodifiableSet<Map.Entry<K, V>> {
			private static final long serialVersionUID = 7854390611657943733L;
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			UnmodifiableEntrySet(Set<? extends Map.Entry<? extends K, ? extends V>> s) {
				super((Set) s);
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Iterator<Map.Entry<K, V>> iterator() {
				return new Iterator<Map.Entry<K, V>>() {
					Iterator<? extends Map.Entry<? extends K, ? extends V>> i = c.iterator();
					public boolean hasNext() {
						return i.hasNext();
					}
					public Map.Entry<K, V> next() {
						return new UnmodifiableEntry<K, V>(i.next());
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Object[] toArray() {
				Object[] a = c.toArray();
				for (int i = 0; i < a.length; i++)
					a[i] = new UnmodifiableEntry<K, V>((Map.Entry<K, V>) a[i]);
				return a;
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public <T> T[] toArray(T[] a) {
				Object[] arr = c.toArray(a.length == 0 ? a : Arrays.copyOf(a, 0));
				for (int i = 0; i < arr.length; i++)
					arr[i] = new UnmodifiableEntry<K, V>((Map.Entry<K, V>) arr[i]);
				if (arr.length > a.length)
					return (T[]) arr;
				System.arraycopy(arr, 0, a, 0, arr.length);
				if (a.length > arr.length)
					a[arr.length] = null;
				return a;
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean contains(Object o) {
				if (!(o instanceof Map.Entry))
					return false;
				return c.contains(new UnmodifiableEntry<K, V>((Map.Entry<K, V>) o));
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean containsAll(Collection<?> coll) {
				Iterator<?> e = coll.iterator();
				while (e.hasNext())
					if (!contains(e.next()))
						return false;
				return true;
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean equals(Object o) {
				if (o == this)
					return true;
				if (!(o instanceof Set))
					return false;
				Set s = (Set) o;
				if (s.size() != c.size())
					return false;
				return containsAll(s);
			}
			private static class UnmodifiableEntry<K, V> implements Map.Entry<K, V> {
				private Map.Entry<? extends K, ? extends V> e;
				UnmodifiableEntry(Map.Entry<? extends K, ? extends V> e) {
					this.e = e;
				}
				public K getKey() {
					return e.getKey();
				}
				public V getValue() {
					return e.getValue();
				}
				public V setValue(V value) {
					throw new UnsupportedOperationException();
				}
				public int hashCode() {
					return e.hashCode();
				}
				public boolean equals(Object o) {
					if (!(o instanceof Map.Entry))
						return false;
					Map.Entry t = (Map.Entry) o;
					return eq(e.getKey(), t.getKey()) && eq(e.getValue(), t.getValue());
				}
				public String toString() {
					return e.toString();
				}
			}
		}
	}
	public static <K, V> SortedMap<K, V> unmodifiableSortedMap(SortedMap<K, ? extends V> m) {
		return new UnmodifiableSortedMap<K, V>(m);
	}
	static class UnmodifiableSortedMap<K, V> extends UnmodifiableMap<K, V> implements SortedMap<K, V>, Serializable {
		private static final long serialVersionUID = -8806743815996713206L;
		private final SortedMap<K, ? extends V> sm;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableSortedMap(SortedMap<K, ? extends V> m) {
			super(m);
			sm = m;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super K> comparator() {
			return sm.comparator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> subMap(K fromKey, K toKey) {
			return new UnmodifiableSortedMap<K, V>(sm.subMap(fromKey, toKey));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> headMap(K toKey) {
			return new UnmodifiableSortedMap<K, V>(sm.headMap(toKey));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> tailMap(K fromKey) {
			return new UnmodifiableSortedMap<K, V>(sm.tailMap(fromKey));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K firstKey() {
			return sm.firstKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K lastKey() {
			return sm.lastKey();
		}
	}
	public static <T> Collection<T> synchronizedCollection(Collection<T> c) {
		return new SynchronizedCollection<T>(c);
	}
	static <T> Collection<T> synchronizedCollection(Collection<T> c, Object mutex) {
		return new SynchronizedCollection<T>(c, mutex);
	}
	static class SynchronizedCollection<E> implements Collection<E>, Serializable {
		private static final long serialVersionUID = 3053995032091335093L;
		final Collection<E> c;
		final Object mutex;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedCollection(Collection<E> c) {
			if (c == null)
				throw new NullPointerException();
			this.c = c;
			mutex = this;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedCollection(Collection<E> c, Object mutex) {
			this.c = c;
			this.mutex = mutex;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			synchronized (mutex) {
				return c.size();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			synchronized (mutex) {
				return c.isEmpty();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			synchronized (mutex) {
				return c.contains(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object[] toArray() {
			synchronized (mutex) {
				return c.toArray();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public <T> T[] toArray(T[] a) {
			synchronized (mutex) {
				return c.toArray(a);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<E> iterator() {
			return c.iterator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean add(E e) {
			synchronized (mutex) {
				return c.add(e);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			synchronized (mutex) {
				return c.remove(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsAll(Collection<?> coll) {
			synchronized (mutex) {
				return c.containsAll(coll);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(Collection<? extends E> coll) {
			synchronized (mutex) {
				return c.addAll(coll);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean removeAll(Collection<?> coll) {
			synchronized (mutex) {
				return c.removeAll(coll);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean retainAll(Collection<?> coll) {
			synchronized (mutex) {
				return c.retainAll(coll);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			synchronized (mutex) {
				c.clear();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			synchronized (mutex) {
				return c.toString();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (mutex) {
				s.defaultWriteObject();
			}
		}
	}
	public static <T> Set<T> synchronizedSet(Set<T> s) {
		return new SynchronizedSet<T>(s);
	}
	static <T> Set<T> synchronizedSet(Set<T> s, Object mutex) {
		return new SynchronizedSet<T>(s, mutex);
	}
	static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
		private static final long serialVersionUID = 487447009682186044L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSet(Set<E> s) {
			super(s);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSet(Set<E> s, Object mutex) {
			super(s, mutex);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			synchronized (mutex) {
				return c.equals(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			synchronized (mutex) {
				return c.hashCode();
			}
		}
	}
	public static <T> SortedSet<T> synchronizedSortedSet(SortedSet<T> s) {
		return new SynchronizedSortedSet<T>(s);
	}
	static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
		private static final long serialVersionUID = 8695801310862127406L;
		final private SortedSet<E> ss;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSortedSet(SortedSet<E> s) {
			super(s);
			ss = s;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSortedSet(SortedSet<E> s, Object mutex) {
			super(s, mutex);
			ss = s;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super E> comparator() {
			synchronized (mutex) {
				return ss.comparator();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> subSet(E fromElement, E toElement) {
			synchronized (mutex) {
				return new SynchronizedSortedSet<E>(ss.subSet(fromElement, toElement), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> headSet(E toElement) {
			synchronized (mutex) {
				return new SynchronizedSortedSet<E>(ss.headSet(toElement), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> tailSet(E fromElement) {
			synchronized (mutex) {
				return new SynchronizedSortedSet<E>(ss.tailSet(fromElement), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E first() {
			synchronized (mutex) {
				return ss.first();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E last() {
			synchronized (mutex) {
				return ss.last();
			}
		}
	}
	public static <T> List<T> synchronizedList(List<T> list) {
		return (list instanceof RandomAccess
				? new SynchronizedRandomAccessList<T>(list)
				: new SynchronizedList<T>(list));
	}
	static <T> List<T> synchronizedList(List<T> list, Object mutex) {
		return (list instanceof RandomAccess
				? new SynchronizedRandomAccessList<T>(list, mutex)
				: new SynchronizedList<T>(list, mutex));
	}
	static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
		static final long serialVersionUID = -7754090372962971524L;
		final List<E> list;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedList(List<E> list) {
			super(list);
			this.list = list;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedList(List<E> list, Object mutex) {
			super(list, mutex);
			this.list = list;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			synchronized (mutex) {
				return list.equals(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			synchronized (mutex) {
				return list.hashCode();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E get(int index) {
			synchronized (mutex) {
				return list.get(index);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E set(int index, E element) {
			synchronized (mutex) {
				return list.set(index, element);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void add(int index, E element) {
			synchronized (mutex) {
				list.add(index, element);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E remove(int index) {
			synchronized (mutex) {
				return list.remove(index);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int indexOf(Object o) {
			synchronized (mutex) {
				return list.indexOf(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int lastIndexOf(Object o) {
			synchronized (mutex) {
				return list.lastIndexOf(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(int index, Collection<? extends E> c) {
			synchronized (mutex) {
				return list.addAll(index, c);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator<E> listIterator() {
			return list.listIterator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator<E> listIterator(int index) {
			return list.listIterator(index);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			synchronized (mutex) {
				return new SynchronizedList<E>(list.subList(fromIndex, toIndex), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object readResolve() {
			return (list instanceof RandomAccess ? new SynchronizedRandomAccessList<E>(list) : this);
		}
	}
	static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedRandomAccessList(List<E> list) {
			super(list);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedRandomAccessList(List<E> list, Object mutex) {
			super(list, mutex);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			synchronized (mutex) {
				return new SynchronizedRandomAccessList<E>(list.subList(fromIndex, toIndex), mutex);
			}
		}
		static final long serialVersionUID = 1530674583602358482L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object writeReplace() {
			return new SynchronizedList<E>(list);
		}
	}
	public static <K, V> Map<K, V> synchronizedMap(Map<K, V> m) {
		return new SynchronizedMap<K, V>(m);
	}
	public static class SynchronizedMap<K, V> implements Map<K, V>, Serializable {
		public ReentrantReadWriteLock entrySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock keySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock valuesLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 1978198479659022715L;
		private final Map<K, V> m;
		final Object mutex;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedMap(Map<K, V> m) {
			if (m == null)
				throw new NullPointerException();
			this.m = m;
			mutex = this;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedMap(Map<K, V> m, Object mutex) {
			this.m = m;
			this.mutex = mutex;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			synchronized (mutex) {
				return m.size();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			synchronized (mutex) {
				return m.isEmpty();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsKey(Object key) {
			synchronized (mutex) {
				return m.containsKey(key);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsValue(Object value) {
			synchronized (mutex) {
				return m.containsValue(value);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V get(Object key) {
			synchronized (mutex) {
				return m.get(key);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V put(K key, V value) {
			synchronized (mutex) {
				return m.put(key, value);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V remove(Object key) {
			synchronized (mutex) {
				return m.remove(key);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void putAll(Map<? extends K, ? extends V> map) {
			synchronized (mutex) {
				m.putAll(map);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			synchronized (mutex) {
				m.clear();
			}
		}
		private transient Set<K> keySet = null;
		private transient Set<Map.Entry<K, V>> entrySet = null;
		private transient Collection<V> values = null;
		@Perm(requires = "none(keySet) * unique(keySet) in alive", ensures = "none(keySet) * unique(keySet) in alive")
		public Set<K> keySet() {
			synchronized (mutex) {
				try {
					keySetLock.writeLock().lock();
					if (keySet == null)
						keySet = new SynchronizedSet<K>(m.keySet(), mutex);
					return keySet;
				} finally {
					keySetLock.writeLock().unlock();
				}
			}
		}
		@Perm(requires = "none(entrySet) * unique(entrySet) in alive", ensures = "none(entrySet) * unique(entrySet) in alive")
		public Set<Map.Entry<K, V>> entrySet() {
			synchronized (mutex) {
				try {
					entrySetLock.writeLock().lock();
					if (entrySet == null)
						entrySet = new SynchronizedSet<Map.Entry<K, V>>(m.entrySet(), mutex);
					return entrySet;
				} finally {
					entrySetLock.writeLock().unlock();
				}
			}
		}
		@Perm(requires = "none(values) * unique(values) in alive", ensures = "none(values) * unique(values) in alive")
		public Collection<V> values() {
			synchronized (mutex) {
				try {
					valuesLock.writeLock().lock();
					if (values == null)
						values = new SynchronizedCollection<V>(m.values(), mutex);
					return values;
				} finally {
					valuesLock.writeLock().unlock();
				}
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			synchronized (mutex) {
				return m.equals(o);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			synchronized (mutex) {
				return m.hashCode();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			synchronized (mutex) {
				return m.toString();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private void writeObject(ObjectOutputStream s) throws IOException {
			synchronized (mutex) {
				s.defaultWriteObject();
			}
		}
	}
	public static <K, V> SortedMap<K, V> synchronizedSortedMap(SortedMap<K, V> m) {
		return new SynchronizedSortedMap<K, V>(m);
	}
	static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
		private static final long serialVersionUID = -8798146769416483793L;
		private final SortedMap<K, V> sm;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSortedMap(SortedMap<K, V> m) {
			super(m);
			sm = m;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSortedMap(SortedMap<K, V> m, Object mutex) {
			super(m, mutex);
			sm = m;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super K> comparator() {
			synchronized (mutex) {
				return sm.comparator();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> subMap(K fromKey, K toKey) {
			synchronized (mutex) {
				return new SynchronizedSortedMap<K, V>(sm.subMap(fromKey, toKey), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> headMap(K toKey) {
			synchronized (mutex) {
				return new SynchronizedSortedMap<K, V>(sm.headMap(toKey), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> tailMap(K fromKey) {
			synchronized (mutex) {
				return new SynchronizedSortedMap<K, V>(sm.tailMap(fromKey), mutex);
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K firstKey() {
			synchronized (mutex) {
				return sm.firstKey();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K lastKey() {
			synchronized (mutex) {
				return sm.lastKey();
			}
		}
	}
	public static <E> Collection<E> checkedCollection(Collection<E> c, Class<E> type) {
		return new CheckedCollection<E>(c, type);
	}
	static class CheckedCollection<E> implements Collection<E>, Serializable {
		public ReentrantReadWriteLock zeroLengthElementArrayLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 1578914078182001775L;
		final Collection<E> c;
		final Class<E> type;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		void typeCheck(Object o) {
			if (!type.isInstance(o))
				throw new ClassCastException(
						"Attempt to insert " + o.getClass() + " element into collection with element type " + type);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedCollection(Collection<E> c, Class<E> type) {
			if (c == null || type == null)
				throw new NullPointerException();
			this.c = c;
			this.type = type;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return c.size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return c.isEmpty();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return c.contains(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object[] toArray() {
			return c.toArray();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public <T> T[] toArray(T[] a) {
			return c.toArray(a);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			return c.toString();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			return c.remove(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsAll(Collection<?> coll) {
			return c.containsAll(coll);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean removeAll(Collection<?> coll) {
			return c.removeAll(coll);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean retainAll(Collection<?> coll) {
			return c.retainAll(coll);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			c.clear();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<E> iterator() {
			return new Iterator<E>() {
				private final Iterator<E> it = c.iterator();
				public boolean hasNext() {
					return it.hasNext();
				}
				public E next() {
					return it.next();
				}
				public void remove() {
					it.remove();
				}
			};
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean add(E e) {
			typeCheck(e);
			return c.add(e);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(Collection<? extends E> coll) {
			E[] a = null;
			try {
				a = coll.toArray(zeroLengthElementArray());
			} catch (ArrayStoreException e) {
				throw new ClassCastException();
			}
			boolean result = false;
			for (E e : a)
				result |= c.add(e);
			return result;
		}
		private E[] zeroLengthElementArray = null;
		@Perm(requires = "none(zeroLengthElementArray) in alive", ensures = "none(zeroLengthElementArray) in alive")
		E[] zeroLengthElementArray() {
			if (zeroLengthElementArray == null)
				zeroLengthElementArray = (E[]) Array.newInstance(type, 0);
			return zeroLengthElementArray;
		}
	}
	public static <E> Set<E> checkedSet(Set<E> s, Class<E> type) {
		return new CheckedSet<E>(s, type);
	}
	static class CheckedSet<E> extends CheckedCollection<E> implements Set<E>, Serializable {
		private static final long serialVersionUID = 4694047833775013803L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedSet(Set<E> s, Class<E> elementType) {
			super(s, elementType);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return o == this || c.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return c.hashCode();
		}
	}
	public static <E> SortedSet<E> checkedSortedSet(SortedSet<E> s, Class<E> type) {
		return new CheckedSortedSet<E>(s, type);
	}
	static class CheckedSortedSet<E> extends CheckedSet<E> implements SortedSet<E>, Serializable {
		private static final long serialVersionUID = 1599911165492914959L;
		private final SortedSet<E> ss;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedSortedSet(SortedSet<E> s, Class<E> type) {
			super(s, type);
			ss = s;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super E> comparator() {
			return ss.comparator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E first() {
			return ss.first();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E last() {
			return ss.last();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> subSet(E fromElement, E toElement) {
			return new CheckedSortedSet<E>(ss.subSet(fromElement, toElement), type);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> headSet(E toElement) {
			return new CheckedSortedSet<E>(ss.headSet(toElement), type);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> tailSet(E fromElement) {
			return new CheckedSortedSet<E>(ss.tailSet(fromElement), type);
		}
	}
	public static <E> List<E> checkedList(List<E> list, Class<E> type) {
		return (list instanceof RandomAccess
				? new CheckedRandomAccessList<E>(list, type)
				: new CheckedList<E>(list, type));
	}
	static class CheckedList<E> extends CheckedCollection<E> implements List<E> {
		static final long serialVersionUID = 65247728283967356L;
		final List<E> list;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedList(List<E> list, Class<E> type) {
			super(list, type);
			this.list = list;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return o == this || list.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return list.hashCode();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E get(int index) {
			return list.get(index);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E remove(int index) {
			return list.remove(index);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int indexOf(Object o) {
			return list.indexOf(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int lastIndexOf(Object o) {
			return list.lastIndexOf(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E set(int index, E element) {
			typeCheck(element);
			return list.set(index, element);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void add(int index, E element) {
			typeCheck(element);
			list.add(index, element);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(int index, Collection<? extends E> c) {
			E[] a = null;
			try {
				a = c.toArray(zeroLengthElementArray());
			} catch (ArrayStoreException e) {
				throw new ClassCastException();
			}
			return list.addAll(index, Arrays.asList(a));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator<E> listIterator() {
			return listIterator(0);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator<E> listIterator(final int index) {
			return new ListIterator<E>() {
				ListIterator<E> i = list.listIterator(index);
				public boolean hasNext() {
					return i.hasNext();
				}
				public E next() {
					return i.next();
				}
				public boolean hasPrevious() {
					return i.hasPrevious();
				}
				public E previous() {
					return i.previous();
				}
				public int nextIndex() {
					return i.nextIndex();
				}
				public int previousIndex() {
					return i.previousIndex();
				}
				public void remove() {
					i.remove();
				}
				public void set(E e) {
					typeCheck(e);
					i.set(e);
				}
				public void add(E e) {
					typeCheck(e);
					i.add(e);
				}
			};
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			return new CheckedList<E>(list.subList(fromIndex, toIndex), type);
		}
	}
	static class CheckedRandomAccessList<E> extends CheckedList<E> implements RandomAccess {
		private static final long serialVersionUID = 1638200125423088369L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedRandomAccessList(List<E> list, Class<E> type) {
			super(list, type);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			return new CheckedRandomAccessList<E>(list.subList(fromIndex, toIndex), type);
		}
	}
	public static <K, V> Map<K, V> checkedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
		return new CheckedMap<K, V>(m, keyType, valueType);
	}
	private static class CheckedMap<K, V> implements Map<K, V>, Serializable {
		public ReentrantReadWriteLock entrySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock zeroLengthValueArrayLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock zeroLengthKeyArrayLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 5742860141034234728L;
		private final Map<K, V> m;
		final Class<K> keyType;
		final Class<V> valueType;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private void typeCheck(Object key, Object value) {
			if (!keyType.isInstance(key))
				throw new ClassCastException(
						"Attempt to insert " + key.getClass() + " key into collection with key type " + keyType);
			if (!valueType.isInstance(value))
				throw new ClassCastException("Attempt to insert " + value.getClass()
						+ " value into collection with value type " + valueType);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedMap(Map<K, V> m, Class<K> keyType, Class<V> valueType) {
			if (m == null || keyType == null || valueType == null)
				throw new NullPointerException();
			this.m = m;
			this.keyType = keyType;
			this.valueType = valueType;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return m.size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return m.isEmpty();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsKey(Object key) {
			return m.containsKey(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsValue(Object v) {
			return m.containsValue(v);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V get(Object key) {
			return m.get(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V remove(Object key) {
			return m.remove(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			m.clear();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Set<K> keySet() {
			return m.keySet();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Collection<V> values() {
			return m.values();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return o == this || m.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return m.hashCode();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			return m.toString();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V put(K key, V value) {
			typeCheck(key, value);
			return m.put(key, value);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void putAll(Map<? extends K, ? extends V> t) {
			K[] keys = null;
			try {
				keys = t.keySet().toArray(zeroLengthKeyArray());
			} catch (ArrayStoreException e) {
				throw new ClassCastException();
			}
			V[] values = null;
			try {
				values = t.values().toArray(zeroLengthValueArray());
			} catch (ArrayStoreException e) {
				throw new ClassCastException();
			}
			if (keys.length != values.length)
				throw new ConcurrentModificationException();
			for (int i = 0; i < keys.length; i++)
				m.put(keys[i], values[i]);
		}
		private K[] zeroLengthKeyArray = null;
		private V[] zeroLengthValueArray = null;
		@Perm(requires = "none(zeroLengthKeyArray) in alive", ensures = "none(zeroLengthKeyArray) in alive")
		private K[] zeroLengthKeyArray() {
			if (zeroLengthKeyArray == null)
				zeroLengthKeyArray = (K[]) Array.newInstance(keyType, 0);
			return zeroLengthKeyArray;
		}
		@Perm(requires = "none(zeroLengthValueArray) in alive", ensures = "none(zeroLengthValueArray) in alive")
		private V[] zeroLengthValueArray() {
			if (zeroLengthValueArray == null)
				zeroLengthValueArray = (V[]) Array.newInstance(valueType, 0);
			return zeroLengthValueArray;
		}
		private transient Set<Map.Entry<K, V>> entrySet = null;
		@Perm(requires = "none(entrySet) * unique(entrySet) in alive", ensures = "none(entrySet) * unique(entrySet) in alive")
		public Set<Map.Entry<K, V>> entrySet() {
			try {
				entrySetLock.writeLock().lock();
				if (entrySet == null)
					entrySet = new CheckedEntrySet<K, V>(m.entrySet(), valueType);
				return entrySet;
			} finally {
				entrySetLock.writeLock().unlock();
			}
		}
		static class CheckedEntrySet<K, V> implements Set<Map.Entry<K, V>> {
			public ReentrantReadWriteLock s_valueTypeLock = new ReentrantReadWriteLock();
			Set<Map.Entry<K, V>> s;
			Class<V> valueType;
			@Perm(requires = "unique(s) * none(s) * unique(valueType) * none(valueType) in alive", ensures = "unique(s) * none(s) * unique(valueType) * none(valueType) in alive")
			CheckedEntrySet(Set<Map.Entry<K, V>> s, Class<V> valueType) {
				this.s = s;
				this.valueType = valueType;
			}
			@Perm(requires = "unique(s) * share(s) in alive", ensures = "unique(s) * share(s) in alive")
			public int size() {
				try {
					s_valueTypeLock.writeLock().lock();
					return s.size();
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "unique(s) * pure(s) in alive", ensures = "unique(s) * pure(s) in alive")
			public boolean isEmpty() {
				try {
					s_valueTypeLock.readLock().lock();
					return s.isEmpty();
				} finally {
					s_valueTypeLock.readLock().unlock();
				}
			}
			@Perm(requires = "unique(s) * share(s) in alive", ensures = "unique(s) * share(s) in alive")
			public String toString() {
				try {
					s_valueTypeLock.writeLock().lock();
					return s.toString();
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "unique(s) * share(s) in alive", ensures = "unique(s) * share(s) in alive")
			public int hashCode() {
				try {
					s_valueTypeLock.writeLock().lock();
					return s.hashCode();
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "unique(s) * share(s) in alive", ensures = "unique(s) * share(s) in alive")
			public boolean remove(Object o) {
				try {
					s_valueTypeLock.writeLock().lock();
					return s.remove(o);
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "unique(s) * share(s) in alive", ensures = "unique(s) * share(s) in alive")
			public boolean removeAll(Collection<?> coll) {
				try {
					s_valueTypeLock.writeLock().lock();
					return s.removeAll(coll);
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "unique(s) * share(s) in alive", ensures = "unique(s) * share(s) in alive")
			public boolean retainAll(Collection<?> coll) {
				try {
					s_valueTypeLock.writeLock().lock();
					return s.retainAll(coll);
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "share(s) in alive", ensures = "share(s) in alive")
			public void clear() {
				s_valueTypeLock.writeLock().lock();
				s.clear();
				s_valueTypeLock.writeLock().unlock();
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean add(Map.Entry<K, V> e) {
				throw new UnsupportedOperationException();
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean addAll(Collection<? extends Map.Entry<K, V>> coll) {
				throw new UnsupportedOperationException();
			}
			@Perm(requires = "share(s) in alive", ensures = "share(s) in alive")
			public Iterator<Map.Entry<K, V>> iterator() {
				try {
					s_valueTypeLock.writeLock().lock();
					return new Iterator<Map.Entry<K, V>>() {
						Iterator<Map.Entry<K, V>> i = s.iterator();
						public boolean hasNext() {
							return i.hasNext();
						}
						public void remove() {
							i.remove();
						}
						public Map.Entry<K, V> next() {
							return new CheckedEntry<K, V>(i.next(), valueType);
						}
					};
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "share(s) * immutable(valueType) in alive", ensures = "share(s) * immutable(valueType) in alive")
			public Object[] toArray() {
				s_valueTypeLock.writeLock().lock();
				Object[] source = s.toArray();
				Object[] dest = (CheckedEntry.class.isInstance(source.getClass().getComponentType())
						? source
						: new Object[source.length]);
				for (int i = 0; i < source.length; i++)
					dest[i] = new CheckedEntry<K, V>((Map.Entry<K, V>) source[i], valueType);
				s_valueTypeLock.writeLock().unlock();
				return dest;
			}
			@Perm(requires = "share(s) * immutable(valueType) in alive", ensures = "share(s) * immutable(valueType) in alive")
			public <T> T[] toArray(T[] a) {
				s_valueTypeLock.writeLock().lock();
				Object[] arr = s.toArray(a.length == 0 ? a : Arrays.copyOf(a, 0));
				for (int i = 0; i < arr.length; i++)
					arr[i] = new CheckedEntry<K, V>((Map.Entry<K, V>) arr[i], valueType);
				s_valueTypeLock.writeLock().unlock();
				if (arr.length > a.length)
					return (T[]) arr;
				System.arraycopy(arr, 0, a, 0, arr.length);
				if (a.length > arr.length)
					a[arr.length] = null;
				return a;
			}
			@Perm(requires = "unique(s) * share(s) * immutable(valueType) in alive", ensures = "unique(s) * share(s) * immutable(valueType) in alive")
			public boolean contains(Object o) {
				if (!(o instanceof Map.Entry))
					return false;
				try {
					s_valueTypeLock.writeLock().lock();
					return s.contains(new CheckedEntry<K, V>((Map.Entry<K, V>) o, valueType));
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean containsAll(Collection<?> coll) {
				Iterator<?> e = coll.iterator();
				while (e.hasNext())
					if (!contains(e.next()))
						return false;
				return true;
			}
			@Perm(requires = "share(s) in alive", ensures = "share(s) in alive")
			public boolean equals(Object o) {
				if (o == this)
					return true;
				if (!(o instanceof Set))
					return false;
				Set<?> that = (Set<?>) o;
				try {
					s_valueTypeLock.writeLock().lock();
					if (that.size() != s.size())
						return false;
				} finally {
					s_valueTypeLock.writeLock().unlock();
				}
				return containsAll(that);
			}
			private static class CheckedEntry<K, V> implements Map.Entry<K, V> {
				private Map.Entry<K, V> e;
				private Class<V> valueType;
				CheckedEntry(Map.Entry<K, V> e, Class<V> valueType) {
					this.e = e;
					this.valueType = valueType;
				}
				public K getKey() {
					return e.getKey();
				}
				public V getValue() {
					return e.getValue();
				}
				public int hashCode() {
					return e.hashCode();
				}
				public String toString() {
					return e.toString();
				}
				public V setValue(V value) {
					if (!valueType.isInstance(value))
						throw new ClassCastException("Attempt to insert " + value.getClass()
								+ " value into collection with value type " + valueType);
					return e.setValue(value);
				}
				public boolean equals(Object o) {
					if (!(o instanceof Map.Entry))
						return false;
					Map.Entry t = (Map.Entry) o;
					return eq(e.getKey(), t.getKey()) && eq(e.getValue(), t.getValue());
				}
			}
		}
	}
	public static <K, V> SortedMap<K, V> checkedSortedMap(SortedMap<K, V> m, Class<K> keyType, Class<V> valueType) {
		return new CheckedSortedMap<K, V>(m, keyType, valueType);
	}
	static class CheckedSortedMap<K, V> extends CheckedMap<K, V> implements SortedMap<K, V>, Serializable {
		private static final long serialVersionUID = 1599671320688067438L;
		private final SortedMap<K, V> sm;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CheckedSortedMap(SortedMap<K, V> m, Class<K> keyType, Class<V> valueType) {
			super(m, keyType, valueType);
			sm = m;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super K> comparator() {
			return sm.comparator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K firstKey() {
			return sm.firstKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K lastKey() {
			return sm.lastKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> subMap(K fromKey, K toKey) {
			return new CheckedSortedMap<K, V>(sm.subMap(fromKey, toKey), keyType, valueType);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> headMap(K toKey) {
			return new CheckedSortedMap<K, V>(sm.headMap(toKey), keyType, valueType);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> tailMap(K fromKey) {
			return new CheckedSortedMap<K, V>(sm.tailMap(fromKey), keyType, valueType);
		}
	}
	public static final Set EMPTY_SET = new EmptySet();
	public static final <T> Set<T> emptySet() {
		return (Set<T>) EMPTY_SET;
	}
	private static class EmptySet extends AbstractSet<Object> implements Serializable {
		private static final long serialVersionUID = 1582296315990362920L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<Object> iterator() {
			return new Iterator<Object>() {
				public boolean hasNext() {
					return false;
				}
				public Object next() {
					throw new NoSuchElementException();
				}
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return 0;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object obj) {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object readResolve() {
			return EMPTY_SET;
		}
	}
	public static final List EMPTY_LIST = new EmptyList();
	public static final <T> List<T> emptyList() {
		return (List<T>) EMPTY_LIST;
	}
	private static class EmptyList extends AbstractList<Object> implements RandomAccess, Serializable {
		private static final long serialVersionUID = 8842843931221139166L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return 0;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object obj) {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object get(int index) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object readResolve() {
			return EMPTY_LIST;
		}
	}
	public static final Map EMPTY_MAP = new EmptyMap();
	public static final <K, V> Map<K, V> emptyMap() {
		return (Map<K, V>) EMPTY_MAP;
	}
	private static class EmptyMap extends AbstractMap<Object, Object> implements Serializable {
		private static final long serialVersionUID = 6428348081105594320L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return 0;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return true;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsKey(Object key) {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsValue(Object value) {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object get(Object key) {
			return null;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Set<Object> keySet() {
			return Collections.<Object>emptySet();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Collection<Object> values() {
			return Collections.<Object>emptySet();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Set<Map.Entry<Object, Object>> entrySet() {
			return Collections.emptySet();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean equals(Object o) {
			return (o instanceof Map) && ((Map) o).size() == 0;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return 0;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object readResolve() {
			return EMPTY_MAP;
		}
	}
	public static <T> Set<T> singleton(T o) {
		return new SingletonSet<T>(o);
	}
	private static class SingletonSet<E> extends AbstractSet<E> implements Serializable {
		private static final long serialVersionUID = 3193687207550431679L;
		final private E element;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SingletonSet(E e) {
			element = e;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<E> iterator() {
			return new Iterator<E>() {
				private boolean hasNext = true;
				public boolean hasNext() {
					return hasNext;
				}
				public E next() {
					if (hasNext) {
						hasNext = false;
						return element;
					}
					throw new NoSuchElementException();
				}
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return 1;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return eq(o, element);
		}
	}
	public static <T> List<T> singletonList(T o) {
		return new SingletonList<T>(o);
	}
	private static class SingletonList<E> extends AbstractList<E> implements RandomAccess, Serializable {
		static final long serialVersionUID = 3093736618740652951L;
		private final E element;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SingletonList(E obj) {
			element = obj;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return 1;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object obj) {
			return eq(obj, element);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E get(int index) {
			if (index != 0)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
			return element;
		}
	}
	public static <K, V> Map<K, V> singletonMap(K key, V value) {
		return new SingletonMap<K, V>(key, value);
	}
	private static class SingletonMap<K, V> extends AbstractMap<K, V> implements Serializable {
		public ReentrantReadWriteLock entrySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock keySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock valuesLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = -6979724477215052911L;
		private final K k;
		private final V v;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SingletonMap(K key, V value) {
			k = key;
			v = value;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return 1;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsKey(Object key) {
			return eq(key, k);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean containsValue(Object value) {
			return eq(value, v);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public V get(Object key) {
			return (eq(key, k) ? v : null);
		}
		private transient Set<K> keySet = null;
		private transient Set<Map.Entry<K, V>> entrySet = null;
		private transient Collection<V> values = null;
		@Perm(requires = "none(keySet) * unique(keySet) in alive", ensures = "none(keySet) * unique(keySet) in alive")
		public Set<K> keySet() {
			try {
				keySetLock.writeLock().lock();
				if (keySet == null)
					keySet = singleton(k);
				return keySet;
			} finally {
				keySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "none(entrySet) in alive", ensures = "none(entrySet) in alive")
		public Set<Map.Entry<K, V>> entrySet() {
			if (entrySet == null)
				entrySet = Collections.<Map.Entry<K, V>>singleton(new SimpleImmutableEntry<K, V>(k, v));
			return entrySet;
		}
		@Perm(requires = "none(values) * unique(values) in alive", ensures = "none(values) * unique(values) in alive")
		public Collection<V> values() {
			try {
				valuesLock.writeLock().lock();
				if (values == null)
					values = singleton(v);
				return values;
			} finally {
				valuesLock.writeLock().unlock();
			}
		}
	}
	public static <T> List<T> nCopies(int n, T o) {
		if (n < 0)
			throw new IllegalArgumentException("List length = " + n);
		return new CopiesList<T>(n, o);
	}
	private static class CopiesList<E> extends AbstractList<E> implements RandomAccess, Serializable {
		static final long serialVersionUID = 2739099268398711800L;
		final int n;
		final E element;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		CopiesList(int n, E e) {
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return n;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object obj) {
			return n != 0 && eq(obj, element);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int indexOf(Object o) {
			return contains(o) ? 0 : -1;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int lastIndexOf(Object o) {
			return contains(o) ? n - 1 : -1;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E get(int index) {
			if (index < 0 || index >= n)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
			return element;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object[] toArray() {
			final Object[] a = new Object[n];
			if (element != null)
				Arrays.fill(a, 0, n, element);
			return a;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public <T> T[] toArray(T[] a) {
			final int n = this.n;
			if (a.length < n) {
				a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), n);
				if (element != null)
					Arrays.fill(a, 0, n, element);
			} else {
				Arrays.fill(a, 0, n, element);
				if (a.length > n)
					a[n] = null;
			}
			return a;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public List<E> subList(int fromIndex, int toIndex) {
			if (fromIndex < 0)
				throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
			if (toIndex > n)
				throw new IndexOutOfBoundsException("toIndex = " + toIndex);
			if (fromIndex > toIndex)
				throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
			return new CopiesList(toIndex - fromIndex, element);
		}
	}
	public static <T> Comparator<T> reverseOrder() {
		return (Comparator<T>) REVERSE_ORDER;
	}
	private static final Comparator REVERSE_ORDER = new ReverseComparator();
	private static class ReverseComparator<T> implements Comparator<Comparable<Object>>, Serializable {
		private static final long serialVersionUID = 7207038068494060240L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int compare(Comparable<Object> c1, Comparable<Object> c2) {
			return c2.compareTo(c1);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		private Object readResolve() {
			return reverseOrder();
		}
	}
	public static <T> Comparator<T> reverseOrder(Comparator<T> cmp) {
		if (cmp == null)
			return reverseOrder();
		return new ReverseComparator2<T>(cmp);
	}
	private static class ReverseComparator2<T> implements Comparator<T>, Serializable {
		public ReentrantReadWriteLock cmpLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 4374092139857L;
		private Comparator<T> cmp;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		ReverseComparator2(Comparator<T> cmp) {
		}
		@Perm(requires = "none(cmp) in alive", ensures = "none(cmp) in alive")
		public int compare(T t1, T t2) {
			return cmp.compare(t2, t1);
		}
	}
	public static <T> Enumeration<T> enumeration(final Collection<T> c) {
		return new Enumeration<T>() {
			Iterator<T> i = c.iterator();
			public boolean hasMoreElements() {
				return i.hasNext();
			}
			public T nextElement() {
				return i.next();
			}
		};
	}
	public static <T> ArrayList<T> list(Enumeration<T> e) {
		ArrayList<T> l = new ArrayList<T>();
		while (e.hasMoreElements())
			l.add(e.nextElement());
		return l;
	}
	private static boolean eq(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}
	public static int frequency(Collection<?> c, Object o) {
		int result = 0;
		if (o == null) {
			for (Object e : c)
				if (e == null)
					result++;
		} else {
			for (Object e : c)
				if (o.equals(e))
					result++;
		}
		return result;
	}
	public static boolean disjoint(Collection<?> c1, Collection<?> c2) {
		if ((c1 instanceof Set) && !(c2 instanceof Set) || (c1.size() > c2.size())) {
			Collection<?> tmp = c1;
			c1 = c2;
			c2 = tmp;
		}
		for (Object e : c1)
			if (c2.contains(e))
				return false;
		return true;
	}
	public static <T> boolean addAll(Collection<? super T> c, T... elements) {
		boolean result = false;
		for (T element : elements)
			result |= c.add(element);
		return result;
	}
	public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
		return new SetFromMap<E>(map);
	}
	private static class SetFromMap<E> extends AbstractSet<E> implements Set<E>, Serializable {
		private final Map<E, Boolean> m;
		private transient Set<E> s;
		SetFromMap(Map<E, Boolean> map) {
			if (!map.isEmpty())
				throw new IllegalArgumentException("Map is non-empty");
			m = map;
			s = map.keySet();
		}
		public void clear() {
			m.clear();
		}
		public int size() {
			return m.size();
		}
		public boolean isEmpty() {
			return m.isEmpty();
		}
		public boolean contains(Object o) {
			return m.containsKey(o);
		}
		public boolean remove(Object o) {
			return m.remove(o) != null;
		}
		public boolean add(E e) {
			return m.put(e, Boolean.TRUE) == null;
		}
		public Iterator<E> iterator() {
			return s.iterator();
		}
		public Object[] toArray() {
			return s.toArray();
		}
		public <T> T[] toArray(T[] a) {
			return s.toArray(a);
		}
		public String toString() {
			return s.toString();
		}
		public int hashCode() {
			return s.hashCode();
		}
		public boolean equals(Object o) {
			return o == this || s.equals(o);
		}
		public boolean containsAll(Collection<?> c) {
			return s.containsAll(c);
		}
		public boolean removeAll(Collection<?> c) {
			return s.removeAll(c);
		}
		public boolean retainAll(Collection<?> c) {
			return s.retainAll(c);
		}
		private static final long serialVersionUID = 2454657854757543876L;
		private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
			stream.defaultReadObject();
			s = m.keySet();
		}
	}
	public static <T> Queue<T> asLifoQueue(Deque<T> deque) {
		return new AsLIFOQueue<T>(deque);
	}
	static class AsLIFOQueue<E> extends AbstractQueue<E> implements Queue<E>, Serializable {
		private static final long serialVersionUID = 1802017725587941708L;
		private final Deque<E> q;
		AsLIFOQueue(Deque<E> q) {
			this.q = q;
		}
		public boolean add(E e) {
			q.addFirst(e);
			return true;
		}
		public boolean offer(E e) {
			return q.offerFirst(e);
		}
		public E poll() {
			return q.pollFirst();
		}
		public E remove() {
			return q.removeFirst();
		}
		public E peek() {
			return q.peekFirst();
		}
		public E element() {
			return q.getFirst();
		}
		public void clear() {
			q.clear();
		}
		public int size() {
			return q.size();
		}
		public boolean isEmpty() {
			return q.isEmpty();
		}
		public boolean contains(Object o) {
			return q.contains(o);
		}
		public boolean remove(Object o) {
			return q.remove(o);
		}
		public Iterator<E> iterator() {
			return q.iterator();
		}
		public Object[] toArray() {
			return q.toArray();
		}
		public <T> T[] toArray(T[] a) {
			return q.toArray(a);
		}
		public String toString() {
			return q.toString();
		}
		public boolean containsAll(Collection<?> c) {
			return q.containsAll(c);
		}
		public boolean removeAll(Collection<?> c) {
			return q.removeAll(c);
		}
		public boolean retainAll(Collection<?> c) {
			return q.retainAll(c);
		}
	}
}
