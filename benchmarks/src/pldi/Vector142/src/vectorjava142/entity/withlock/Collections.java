package vectorjava142.entity.withlock;
import java.io.Serializable;
import java.util.*;
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
	public static void sort(List list) {
		Object a[] = list.toArray();
		Arrays.sort(a);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void sort(List list, Comparator c) {
		Object a[] = list.toArray();
		Arrays.sort(a, c);
		ListIterator i = list.listIterator();
		for (int j = 0; j < a.length; j++) {
			i.next();
			i.set(a[j]);
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int binarySearch(List list, Object key) {
		if (list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD)
			return indexedBinarySearch(list, key);
		else
			return iteratorBinarySearch(list, key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int indexedBinarySearch(List list, Object key) {
		int low = 0;
		int high = list.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			Object midVal = list.get(mid);
			int cmp = ((Comparable) midVal).compareTo(key);
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
	private static int iteratorBinarySearch(List list, Object key) {
		int low = 0;
		int high = list.size() - 1;
		ListIterator i = list.listIterator();
		while (low <= high) {
			int mid = (low + high) >> 1;
			Object midVal = get(i, mid);
			int cmp = ((Comparable) midVal).compareTo(key);
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
	private static Object get(ListIterator i, int index) {
		Object obj = null;
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
	public static int binarySearch(List list, Object key, Comparator c) {
		if (c == null)
			return binarySearch(list, key);
		if (list instanceof RandomAccess || list.size() < BINARYSEARCH_THRESHOLD)
			return indexedBinarySearch(list, key, c);
		else
			return iteratorBinarySearch(list, key, c);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int indexedBinarySearch(List l, Object key, Comparator c) {
		int low = 0;
		int high = l.size() - 1;
		while (low <= high) {
			int mid = (low + high) >> 1;
			Object midVal = l.get(mid);
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
	private static int iteratorBinarySearch(List l, Object key, Comparator c) {
		int low = 0;
		int high = l.size() - 1;
		ListIterator i = l.listIterator();
		while (low <= high) {
			int mid = (low + high) >> 1;
			Object midVal = get(i, mid);
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
	public static void reverse(List list) {
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
	@Perm(requires = "none(r) in alive", ensures = "none(r) in alive")
	public static void shuffle(List list) {
		shuffle(list, r);
	}
	private static Random r = new Random();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void shuffle(List list, Random rnd) {
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
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void swap(List list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void swap(Object[] arr, int i, int j) {
		Object tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void fill(List list, Object obj) {
		int size = list.size();
		if (size < FILL_THRESHOLD || list instanceof RandomAccess) {
			for (int i = 0; i < size; i++)
				list.set(i, obj);
		} else {
			ListIterator itr = list.listIterator();
			for (int i = 0; i < size; i++) {
				itr.next();
				itr.set(obj);
			}
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void copy(List dest, List src) {
		int srcSize = src.size();
		if (srcSize > dest.size())
			throw new IndexOutOfBoundsException("Source does not fit in dest");
		if (srcSize < COPY_THRESHOLD || (src instanceof RandomAccess && dest instanceof RandomAccess)) {
			for (int i = 0; i < srcSize; i++)
				dest.set(i, src.get(i));
		} else {
			ListIterator di = dest.listIterator(), si = src.listIterator();
			for (int i = 0; i < srcSize; i++) {
				di.next();
				di.set(si.next());
			}
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Object min(Collection coll) {
		Iterator i = coll.iterator();
		Comparable candidate = (Comparable) (i.next());
		while (i.hasNext()) {
			Comparable next = (Comparable) (i.next());
			if (next.compareTo(candidate) < 0)
				candidate = next;
		}
		return candidate;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Object min(Collection coll, Comparator comp) {
		if (comp == null)
			return min(coll);
		Iterator i = coll.iterator();
		Object candidate = i.next();
		while (i.hasNext()) {
			Object next = i.next();
			if (comp.compare(next, candidate) < 0)
				candidate = next;
		}
		return candidate;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Object max(Collection coll) {
		Iterator i = coll.iterator();
		Comparable candidate = (Comparable) (i.next());
		while (i.hasNext()) {
			Comparable next = (Comparable) (i.next());
			if (next.compareTo(candidate) > 0)
				candidate = next;
		}
		return candidate;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Object max(Collection coll, Comparator comp) {
		if (comp == null)
			return max(coll);
		Iterator i = coll.iterator();
		Object candidate = i.next();
		while (i.hasNext()) {
			Object next = i.next();
			if (comp.compare(next, candidate) > 0)
				candidate = next;
		}
		return candidate;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void rotate(List list, int distance) {
		if (list instanceof RandomAccess || list.size() < ROTATE_THRESHOLD)
			rotate1(list, distance);
		else
			rotate2(list, distance);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void rotate1(List list, int distance) {
		int size = list.size();
		if (size == 0)
			return;
		distance = distance % size;
		if (distance < 0)
			distance += size;
		if (distance == 0)
			return;
		for (int cycleStart = 0, nMoved = 0; nMoved != size; cycleStart++) {
			Object displaced = list.get(cycleStart);
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
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static void rotate2(List list, int distance) {
		int size = list.size();
		if (size == 0)
			return;
		int mid = -distance % size;
		if (mid < 0)
			mid += size;
		if (mid == 0)
			return;
		Collections.reverse(list.subList(0, mid));
		Collections.reverse(list.subList(mid, size));
		Collections.reverse(list);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static boolean replaceAll(List list, Object oldVal, Object newVal) {
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
			ListIterator itr = list.listIterator();
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
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int indexOfSubList(List source, List target) {
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
			ListIterator si = source.listIterator();
			nextCand : for (int candidate = 0; candidate <= maxCandidate; candidate++) {
				ListIterator ti = target.listIterator();
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
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static int lastIndexOfSubList(List source, List target) {
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
			ListIterator si = source.listIterator(maxCandidate);
			nextCand : for (int candidate = maxCandidate; candidate >= 0; candidate--) {
				ListIterator ti = target.listIterator();
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
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Collection unmodifiableCollection(Collection c) {
		return new UnmodifiableCollection(c);
	}
	static class UnmodifiableCollection implements Collection, Serializable {
		public ReentrantReadWriteLock cLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 1820017752578914078L;
		Collection c;
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		UnmodifiableCollection(Collection c) {
			if (c == null)
				throw new NullPointerException();
			cLock.writeLock().lock();
			this.c = c;
			cLock.writeLock().unlock();
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public int size() {
			try {
				cLock.writeLock().lock();
				return c.size();
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public boolean isEmpty() {
			try {
				cLock.writeLock().lock();
				return c.isEmpty();
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public boolean contains(Object o) {
			try {
				cLock.writeLock().lock();
				return c.contains(o);
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public Object[] toArray() {
			try {
				cLock.writeLock().lock();
				return c.toArray();
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public Object[] toArray(Object[] a) {
			try {
				cLock.writeLock().lock();
				return c.toArray(a);
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public String toString() {
			try {
				cLock.writeLock().lock();
				return c.toString();
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(c) in alive", ensures = "share(c) in alive")
		public Iterator iterator() {
			try {
				cLock.writeLock().lock();
				return new Iterator() {
					Iterator i = c.iterator();
					public boolean hasNext() {
						return i.hasNext();
					}
					public Object next() {
						return i.next();
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean add(Object o) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public boolean containsAll(Collection coll) {
			try {
				cLock.writeLock().lock();
				return c.containsAll(coll);
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(Collection coll) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean removeAll(Collection coll) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean retainAll(Collection coll) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			throw new UnsupportedOperationException();
		}
	}
	public static Set unmodifiableSet(Set s) {
		return new UnmodifiableSet(s);
	}
	static class UnmodifiableSet extends UnmodifiableCollection implements Set, Serializable {
		public ReentrantReadWriteLock cLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableSet(Set s) {
			super(s);
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public boolean equals(Object o) {
			try {
				cLock.writeLock().lock();
				return c.equals(o);
			} finally {
				cLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public int hashCode() {
			try {
				cLock.writeLock().lock();
				return c.hashCode();
			} finally {
				cLock.writeLock().unlock();
			}
		}
	}
	public static SortedSet unmodifiableSortedSet(SortedSet s) {
		return new UnmodifiableSortedSet(s);
	}
	static class UnmodifiableSortedSet extends UnmodifiableSet implements SortedSet, Serializable {
		public ReentrantReadWriteLock ssLock = new ReentrantReadWriteLock();
		private SortedSet ss;
		@Perm(requires = "unique(ss) in alive", ensures = "unique(ss) in alive")
		UnmodifiableSortedSet(SortedSet s) {
			super(s);
			ssLock.writeLock().lock();
			ss = s;
			ssLock.writeLock().unlock();
		}
		@Perm(requires = "unique(ss) in alive", ensures = "unique(ss) in alive")
		public Comparator comparator() {
			try {
				ssLock.writeLock().lock();
				return ss.comparator();
			} finally {
				ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(ss) in alive", ensures = "share(ss) in alive")
		public SortedSet subSet(Object fromElement, Object toElement) {
			try {
				ssLock.writeLock().lock();
				return new UnmodifiableSortedSet(ss.subSet(fromElement, toElement));
			} finally {
				ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(ss) in alive", ensures = "share(ss) in alive")
		public SortedSet headSet(Object toElement) {
			try {
				ssLock.writeLock().lock();
				return new UnmodifiableSortedSet(ss.headSet(toElement));
			} finally {
				ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(ss) in alive", ensures = "share(ss) in alive")
		public SortedSet tailSet(Object fromElement) {
			try {
				ssLock.writeLock().lock();
				return new UnmodifiableSortedSet(ss.tailSet(fromElement));
			} finally {
				ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(ss) in alive", ensures = "unique(ss) in alive")
		public Object first() {
			try {
				ssLock.writeLock().lock();
				return ss.first();
			} finally {
				ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(ss) in alive", ensures = "unique(ss) in alive")
		public Object last() {
			try {
				ssLock.writeLock().lock();
				return ss.last();
			} finally {
				ssLock.writeLock().unlock();
			}
		}
	}
	public static List unmodifiableList(List list) {
		return (list instanceof RandomAccess ? new UnmodifiableRandomAccessList(list) : new UnmodifiableList(list));
	}
	static class UnmodifiableList extends UnmodifiableCollection implements List {
		public ReentrantReadWriteLock listLock = new ReentrantReadWriteLock();
		static final long serialVersionUID = -283967356065247728L;
		List list;
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		UnmodifiableList(List list) {
			super(list);
			listLock.writeLock().lock();
			this.list = list;
			listLock.writeLock().unlock();
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public boolean equals(Object o) {
			try {
				listLock.writeLock().lock();
				return list.equals(o);
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public int hashCode() {
			try {
				listLock.writeLock().lock();
				return list.hashCode();
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public Object get(int index) {
			try {
				listLock.writeLock().lock();
				return list.get(index);
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object set(int index, Object element) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void add(int index, Object element) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object remove(int index) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public int indexOf(Object o) {
			try {
				listLock.writeLock().lock();
				return list.indexOf(o);
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public int lastIndexOf(Object o) {
			try {
				listLock.writeLock().lock();
				return list.lastIndexOf(o);
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean addAll(int index, Collection c) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public ListIterator listIterator() {
			return listIterator(0);
		}
		@Perm(requires = "share(list) in alive", ensures = "share(list) in alive")
		public ListIterator listIterator(final int index) {
			try {
				listLock.writeLock().lock();
				return new ListIterator() {
					ListIterator i = list.listIterator(index);
					public boolean hasNext() {
						return i.hasNext();
					}
					public Object next() {
						return i.next();
					}
					public boolean hasPrevious() {
						return i.hasPrevious();
					}
					public Object previous() {
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
					public void set(Object o) {
						throw new UnsupportedOperationException();
					}
					public void add(Object o) {
						throw new UnsupportedOperationException();
					}
				};
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(list) in alive", ensures = "share(list) in alive")
		public List subList(int fromIndex, int toIndex) {
			try {
				listLock.writeLock().lock();
				return new UnmodifiableList(list.subList(fromIndex, toIndex));
			} finally {
				listLock.writeLock().unlock();
			}
		}
		@Perm(requires = "pure(list) in alive", ensures = "pure(list) in alive")
		private Object readResolve() {
			try {
				listLock.readLock().lock();
				return (list instanceof RandomAccess ? new UnmodifiableRandomAccessList(list) : this);
			} finally {
				listLock.readLock().unlock();
			}
		}
	}
	static class UnmodifiableRandomAccessList extends UnmodifiableList implements RandomAccess {
		public ReentrantReadWriteLock listLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableRandomAccessList(List list) {
			super(list);
		}
		@Perm(requires = "share(list) in alive", ensures = "share(list) in alive")
		public List subList(int fromIndex, int toIndex) {
			try {
				listLock.writeLock().lock();
				return new UnmodifiableRandomAccessList(list.subList(fromIndex, toIndex));
			} finally {
				listLock.writeLock().unlock();
			}
		}
		private static final long serialVersionUID = -2542308836966382001L;
		@Perm(requires = "pure(list) in alive", ensures = "pure(list) in alive")
		private Object writeReplace() {
			try {
				listLock.readLock().lock();
				return new UnmodifiableList(list);
			} finally {
				listLock.readLock().unlock();
			}
		}
	}
	public static Map unmodifiableMap(Map m) {
		return new UnmodifiableMap(m);
	}
	private static class UnmodifiableMap implements Map, Serializable {
		public ReentrantReadWriteLock keySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock entrySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock valuesLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = -1034234728574286014L;
		private final Map m;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		UnmodifiableMap(Map m) {
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
		public Object get(Object key) {
			return m.get(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object put(Object key, Object value) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object remove(Object key) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void putAll(Map t) {
			throw new UnsupportedOperationException();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			throw new UnsupportedOperationException();
		}
		private transient Set keySet = null;
		private transient Set entrySet = null;
		private transient Collection values = null;
		@Perm(requires = "unique(keySet) in alive", ensures = "unique(keySet) in alive")
		public Set keySet() {
			try {
				keySetLock.writeLock().lock();
				if (keySet == null)
					keySet = unmodifiableSet(m.keySet());
				return keySet;
			} finally {
				keySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(entrySet) in alive", ensures = "unique(entrySet) in alive")
		public Set entrySet() {
			try {
				entrySetLock.writeLock().lock();
				if (entrySet == null)
					entrySet = new UnmodifiableEntrySet(m.entrySet());
				return entrySet;
			} finally {
				entrySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(values) in alive", ensures = "unique(values) in alive")
		public Collection values() {
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
			return m.equals(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			return m.hashCode();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public String toString() {
			return m.toString();
		}
		static class UnmodifiableEntrySet extends UnmodifiableSet {
			public ReentrantReadWriteLock cLock = new ReentrantReadWriteLock();
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			UnmodifiableEntrySet(Set s) {
				super(s);
			}
			@Perm(requires = "share(c) in alive", ensures = "share(c) in alive")
			public Iterator iterator() {
				try {
					cLock.writeLock().lock();
					return new Iterator() {
						Iterator i = c.iterator();
						public boolean hasNext() {
							return i.hasNext();
						}
						public Object next() {
							return new UnmodifiableEntry((Entry) i.next());
						}
						public void remove() {
							throw new UnsupportedOperationException();
						}
					};
				} finally {
					cLock.writeLock().unlock();
				}
			}
			@Perm(requires = "share(c) in alive", ensures = "share(c) in alive")
			public Object[] toArray() {
				cLock.writeLock().lock();
				Object[] a = c.toArray();
				cLock.writeLock().unlock();
				for (int i = 0; i < a.length; i++)
					a[i] = new UnmodifiableEntry((Entry) a[i]);
				return a;
			}
			@Perm(requires = "share(c) in alive", ensures = "share(c) in alive")
			public Object[] toArray(Object a[]) {
				cLock.writeLock().lock();
				Object[] arr = c.toArray(a.length == 0
						? a
						: (Object[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 0));
				cLock.writeLock().unlock();
				for (int i = 0; i < arr.length; i++)
					arr[i] = new UnmodifiableEntry((Entry) arr[i]);
				if (arr.length > a.length)
					return arr;
				System.arraycopy(arr, 0, a, 0, arr.length);
				if (a.length > arr.length)
					a[arr.length] = null;
				return a;
			}
			@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
			public boolean contains(Object o) {
				if (!(o instanceof Map.Entry))
					return false;
				try {
					cLock.writeLock().lock();
					return c.contains(new UnmodifiableEntry((Entry) o));
				} finally {
					cLock.writeLock().unlock();
				}
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean containsAll(Collection coll) {
				Iterator e = coll.iterator();
				try {
					cLock.writeLock().lock();
					while (e.hasNext())
						if (!contains(e.next()))
							return false;
				} finally {
					cLock.writeLock().unlock();
				}
				return true;
			}
			@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
			public boolean equals(Object o) {
				if (o == this)
					return true;
				if (!(o instanceof Set))
					return false;
				Set s = (Set) o;
				try {
					cLock.writeLock().lock();
					if (s.size() != c.size())
						return false;
				} finally {
					cLock.writeLock().unlock();
				}
				return containsAll(s);
			}
			private static class UnmodifiableEntry implements Entry {
				private Entry e;
				UnmodifiableEntry(Entry e) {
					this.e = e;
				}
				public Object getKey() {
					return e.getKey();
				}
				public Object getValue() {
					return e.getValue();
				}
				public Object setValue(Object value) {
					throw new UnsupportedOperationException();
				}
				public int hashCode() {
					return e.hashCode();
				}
				public boolean equals(Object o) {
					if (!(o instanceof Map.Entry))
						return false;
					Entry t = (Entry) o;
					return eq(e.getKey(), t.getKey()) && eq(e.getValue(), t.getValue());
				}
				public String toString() {
					return e.toString();
				}
			}
		}
	}
	public static SortedMap unmodifiableSortedMap(SortedMap m) {
		return new UnmodifiableSortedMap(m);
	}
	static class UnmodifiableSortedMap extends UnmodifiableMap implements SortedMap, Serializable {
		public ReentrantReadWriteLock smLock = new ReentrantReadWriteLock();
		private SortedMap sm;
		@Perm(requires = "unique(sm) in alive", ensures = "unique(sm) in alive")
		UnmodifiableSortedMap(SortedMap m) {
			super(m);
			smLock.writeLock().lock();
			sm = m;
			smLock.writeLock().unlock();
		}
		@Perm(requires = "unique(sm) in alive", ensures = "unique(sm) in alive")
		public Comparator comparator() {
			try {
				smLock.writeLock().lock();
				return sm.comparator();
			} finally {
				smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(sm) in alive", ensures = "share(sm) in alive")
		public SortedMap subMap(Object fromKey, Object toKey) {
			try {
				smLock.writeLock().lock();
				return new UnmodifiableSortedMap(sm.subMap(fromKey, toKey));
			} finally {
				smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(sm) in alive", ensures = "share(sm) in alive")
		public SortedMap headMap(Object toKey) {
			try {
				smLock.writeLock().lock();
				return new UnmodifiableSortedMap(sm.headMap(toKey));
			} finally {
				smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(sm) in alive", ensures = "share(sm) in alive")
		public SortedMap tailMap(Object fromKey) {
			try {
				smLock.writeLock().lock();
				return new UnmodifiableSortedMap(sm.tailMap(fromKey));
			} finally {
				smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(sm) in alive", ensures = "unique(sm) in alive")
		public Object firstKey() {
			try {
				smLock.writeLock().lock();
				return sm.firstKey();
			} finally {
				smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(sm) in alive", ensures = "unique(sm) in alive")
		public Object lastKey() {
			try {
				smLock.writeLock().lock();
				return sm.lastKey();
			} finally {
				smLock.writeLock().unlock();
			}
		}
	}
	public static Collection synchronizedCollection(Collection c) {
		return new SynchronizedCollection(c);
	}
	static Collection synchronizedCollection(Collection c, Object mutex) {
		return new SynchronizedCollection(c, mutex);
	}
	static class SynchronizedCollection implements Collection, Serializable {
		public ReentrantReadWriteLock c_mutexLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 3053995032091335093L;
		Collection c;
		Object mutex;
		@Perm(requires = "unique(c) * none(mutex) in alive", ensures = "unique(c) * none(mutex) in alive")
		SynchronizedCollection(Collection c) {
			if (c == null)
				throw new NullPointerException();
			c_mutexLock.writeLock().lock();
			this.c = c;
			mutex = this;
			c_mutexLock.writeLock().unlock();
		}
		@Perm(requires = "none(mutex) * unique(c) * unique(mutex) in alive", ensures = "none(mutex) * unique(c) * unique(mutex) in alive")
		SynchronizedCollection(Collection c, Object mutex) {
			c_mutexLock.writeLock().lock();
			this.c = c;
			this.mutex = mutex;
			c_mutexLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public int size() {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.size();
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean isEmpty() {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.isEmpty();
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean contains(Object o) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.contains(o);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public Object[] toArray() {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.toArray();
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public Object[] toArray(Object[] a) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.toArray(a);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(c) in alive", ensures = "unique(c) in alive")
		public Iterator iterator() {
			try {
				c_mutexLock.writeLock().lock();
				return c.iterator();
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean add(Object o) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.add(o);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean remove(Object o) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.remove(o);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean containsAll(Collection coll) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.containsAll(coll);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean addAll(Collection coll) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.addAll(coll);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean removeAll(Collection coll) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.removeAll(coll);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean retainAll(Collection coll) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.retainAll(coll);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(c) in alive", ensures = "immutable(mutex) * share(c) in alive")
		public void clear() {
			c_mutexLock.writeLock().lock();
			synchronized (mutex) {
				c.clear();
			}
			c_mutexLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public String toString() {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.toString();
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
	}
	public static Set synchronizedSet(Set s) {
		return new SynchronizedSet(s);
	}
	static Set synchronizedSet(Set s, Object mutex) {
		return new SynchronizedSet(s, mutex);
	}
	static class SynchronizedSet extends SynchronizedCollection implements Set {
		public ReentrantReadWriteLock c_mutexLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSet(Set s) {
			super(s);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedSet(Set s, Object mutex) {
			super(s, mutex);
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public boolean equals(Object o) {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.equals(o);
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(c) in alive", ensures = "immutable(mutex) * unique(c) in alive")
		public int hashCode() {
			try {
				c_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return c.hashCode();
				}
			} finally {
				c_mutexLock.writeLock().unlock();
			}
		}
	}
	public static SortedSet synchronizedSortedSet(SortedSet s) {
		return new SynchronizedSortedSet(s);
	}
	static class SynchronizedSortedSet extends SynchronizedSet implements SortedSet {
		public ReentrantReadWriteLock mutex_ssLock = new ReentrantReadWriteLock();
		private SortedSet ss;
		@Perm(requires = "unique(ss) in alive", ensures = "unique(ss) in alive")
		SynchronizedSortedSet(SortedSet s) {
			super(s);
			mutex_ssLock.writeLock().lock();
			ss = s;
			mutex_ssLock.writeLock().unlock();
		}
		@Perm(requires = "unique(ss) in alive", ensures = "unique(ss) in alive")
		SynchronizedSortedSet(SortedSet s, Object mutex) {
			super(s, mutex);
			mutex_ssLock.writeLock().lock();
			ss = s;
			mutex_ssLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(ss) in alive", ensures = "immutable(mutex) * unique(ss) in alive")
		public Comparator comparator() {
			try {
				mutex_ssLock.writeLock().lock();
				synchronized (mutex) {
					return ss.comparator();
				}
			} finally {
				mutex_ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(ss) in alive", ensures = "immutable(mutex) * share(ss) in alive")
		public SortedSet subSet(Object fromElement, Object toElement) {
			try {
				mutex_ssLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedSortedSet(ss.subSet(fromElement, toElement), mutex);
				}
			} finally {
				mutex_ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(ss) in alive", ensures = "immutable(mutex) * share(ss) in alive")
		public SortedSet headSet(Object toElement) {
			try {
				mutex_ssLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedSortedSet(ss.headSet(toElement), mutex);
				}
			} finally {
				mutex_ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(ss) in alive", ensures = "immutable(mutex) * share(ss) in alive")
		public SortedSet tailSet(Object fromElement) {
			try {
				mutex_ssLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedSortedSet(ss.tailSet(fromElement), mutex);
				}
			} finally {
				mutex_ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(ss) in alive", ensures = "immutable(mutex) * unique(ss) in alive")
		public Object first() {
			try {
				mutex_ssLock.writeLock().lock();
				synchronized (mutex) {
					return ss.first();
				}
			} finally {
				mutex_ssLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(ss) in alive", ensures = "immutable(mutex) * unique(ss) in alive")
		public Object last() {
			try {
				mutex_ssLock.writeLock().lock();
				synchronized (mutex) {
					return ss.last();
				}
			} finally {
				mutex_ssLock.writeLock().unlock();
			}
		}
	}
	public static List synchronizedList(List list) {
		return (list instanceof RandomAccess ? new SynchronizedRandomAccessList(list) : new SynchronizedList(list));
	}
	static List synchronizedList(List list, Object mutex) {
		return (list instanceof RandomAccess
				? new SynchronizedRandomAccessList(list, mutex)
				: new SynchronizedList(list, mutex));
	}
	static class SynchronizedList extends SynchronizedCollection implements List {
		public ReentrantReadWriteLock list_mutexLock = new ReentrantReadWriteLock();
		static final long serialVersionUID = -7754090372962971524L;
		List list;
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		SynchronizedList(List list) {
			super(list);
			list_mutexLock.writeLock().lock();
			this.list = list;
			list_mutexLock.writeLock().unlock();
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		SynchronizedList(List list, Object mutex) {
			super(list, mutex);
			list_mutexLock.writeLock().lock();
			this.list = list;
			list_mutexLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public boolean equals(Object o) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.equals(o);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public int hashCode() {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.hashCode();
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public Object get(int index) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.get(index);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public Object set(int index, Object element) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.set(index, element);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(list) in alive", ensures = "immutable(mutex) * share(list) in alive")
		public void add(int index, Object element) {
			list_mutexLock.writeLock().lock();
			synchronized (mutex) {
				list.add(index, element);
			}
			list_mutexLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public Object remove(int index) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.remove(index);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public int indexOf(Object o) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.indexOf(o);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public int lastIndexOf(Object o) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.lastIndexOf(o);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(list) in alive", ensures = "immutable(mutex) * unique(list) in alive")
		public boolean addAll(int index, Collection c) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return list.addAll(index, c);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public ListIterator listIterator() {
			try {
				list_mutexLock.writeLock().lock();
				return list.listIterator();
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(list) in alive", ensures = "unique(list) in alive")
		public ListIterator listIterator(int index) {
			try {
				list_mutexLock.writeLock().lock();
				return list.listIterator(index);
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(list) in alive", ensures = "immutable(mutex) * share(list) in alive")
		public List subList(int fromIndex, int toIndex) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedList(list.subList(fromIndex, toIndex), mutex);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		@Perm(requires = "pure(list) in alive", ensures = "pure(list) in alive")
		private Object readResolve() {
			try {
				list_mutexLock.readLock().lock();
				return (list instanceof RandomAccess ? new SynchronizedRandomAccessList(list) : this);
			} finally {
				list_mutexLock.readLock().unlock();
			}
		}
	}
	static class SynchronizedRandomAccessList extends SynchronizedList implements RandomAccess {
		public ReentrantReadWriteLock list_mutexLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedRandomAccessList(List list) {
			super(list);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SynchronizedRandomAccessList(List list, Object mutex) {
			super(list, mutex);
		}
		@Perm(requires = "immutable(mutex) * share(list) in alive", ensures = "immutable(mutex) * share(list) in alive")
		public List subList(int fromIndex, int toIndex) {
			try {
				list_mutexLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedRandomAccessList(list.subList(fromIndex, toIndex), mutex);
				}
			} finally {
				list_mutexLock.writeLock().unlock();
			}
		}
		static final long serialVersionUID = 1530674583602358482L;
		@Perm(requires = "pure(list) in alive", ensures = "pure(list) in alive")
		private Object writeReplace() {
			try {
				list_mutexLock.readLock().lock();
				return new SynchronizedList(list);
			} finally {
				list_mutexLock.readLock().unlock();
			}
		}
	}
	public static Map synchronizedMap(Map m) {
		return new SynchronizedMap(m);
	}
	private static class SynchronizedMap implements Map, Serializable {
		public ReentrantReadWriteLock entrySet_keySet_m_mutex_valuesLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 1978198479659022715L;
		private Map m;
		Object mutex;
		@Perm(requires = "unique(m) * none(mutex) in alive", ensures = "unique(m) * none(mutex) in alive")
		SynchronizedMap(Map m) {
			if (m == null)
				throw new NullPointerException();
			entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
			this.m = m;
			mutex = this;
			entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
		}
		@Perm(requires = "unique(m) * unique(mutex) in alive", ensures = "unique(m) * unique(mutex) in alive")
		SynchronizedMap(Map m, Object mutex) {
			entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
			this.m = m;
			this.mutex = mutex;
			entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public int size() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.size();
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public boolean isEmpty() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.isEmpty();
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public boolean containsKey(Object key) {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.containsKey(key);
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public boolean containsValue(Object value) {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.containsValue(value);
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public Object get(Object key) {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.get(key);
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public Object put(Object key, Object value) {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.put(key, value);
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public Object remove(Object key) {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.remove(key);
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(m) in alive", ensures = "immutable(mutex) * share(m) in alive")
		public void putAll(Map map) {
			entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
			synchronized (mutex) {
				m.putAll(map);
			}
			entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * share(m) in alive", ensures = "immutable(mutex) * share(m) in alive")
		public void clear() {
			entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
			synchronized (mutex) {
				m.clear();
			}
			entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
		}
		private transient Set keySet = null;
		private transient Set entrySet = null;
		private transient Collection values = null;
		@Perm(requires = "immutable(mutex) * unique(keySet) * share(m) in alive", ensures = "immutable(mutex) * unique(keySet) * share(m) in alive")
		public Set keySet() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					if (keySet == null)
						keySet = new SynchronizedSet(m.keySet(), mutex);
					return keySet;
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(entrySet) * share(m) in alive", ensures = "immutable(mutex) * unique(entrySet) * share(m) in alive")
		public Set entrySet() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					if (entrySet == null)
						entrySet = new SynchronizedSet(m.entrySet(), mutex);
					return entrySet;
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(values) * share(m) in alive", ensures = "immutable(mutex) * unique(values) * share(m) in alive")
		public Collection values() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					if (values == null)
						values = new SynchronizedCollection(m.values(), mutex);
					return values;
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public boolean equals(Object o) {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.equals(o);
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public int hashCode() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.hashCode();
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(m) in alive", ensures = "immutable(mutex) * unique(m) in alive")
		public String toString() {
			try {
				entrySet_keySet_m_mutex_valuesLock.writeLock().lock();
				synchronized (mutex) {
					return m.toString();
				}
			} finally {
				entrySet_keySet_m_mutex_valuesLock.writeLock().unlock();
			}
		}
	}
	public static SortedMap synchronizedSortedMap(SortedMap m) {
		return new SynchronizedSortedMap(m);
	}
	static class SynchronizedSortedMap extends SynchronizedMap implements SortedMap {
		public ReentrantReadWriteLock mutex_smLock = new ReentrantReadWriteLock();
		private SortedMap sm;
		@Perm(requires = "unique(sm) in alive", ensures = "unique(sm) in alive")
		SynchronizedSortedMap(SortedMap m) {
			super(m);
			mutex_smLock.writeLock().lock();
			sm = m;
			mutex_smLock.writeLock().unlock();
		}
		@Perm(requires = "unique(sm) in alive", ensures = "unique(sm) in alive")
		SynchronizedSortedMap(SortedMap m, Object mutex) {
			super(m, mutex);
			mutex_smLock.writeLock().lock();
			sm = m;
			mutex_smLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(mutex) * unique(sm) in alive", ensures = "immutable(mutex) * unique(sm) in alive")
		public Comparator comparator() {
			try {
				mutex_smLock.writeLock().lock();
				synchronized (mutex) {
					return sm.comparator();
				}
			} finally {
				mutex_smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(sm) in alive", ensures = "immutable(mutex) * share(sm) in alive")
		public SortedMap subMap(Object fromKey, Object toKey) {
			try {
				mutex_smLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedSortedMap(sm.subMap(fromKey, toKey), mutex);
				}
			} finally {
				mutex_smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(sm) in alive", ensures = "immutable(mutex) * share(sm) in alive")
		public SortedMap headMap(Object toKey) {
			try {
				mutex_smLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedSortedMap(sm.headMap(toKey), mutex);
				}
			} finally {
				mutex_smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * share(sm) in alive", ensures = "immutable(mutex) * share(sm) in alive")
		public SortedMap tailMap(Object fromKey) {
			try {
				mutex_smLock.writeLock().lock();
				synchronized (mutex) {
					return new SynchronizedSortedMap(sm.tailMap(fromKey), mutex);
				}
			} finally {
				mutex_smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(sm) in alive", ensures = "immutable(mutex) * unique(sm) in alive")
		public Object firstKey() {
			try {
				mutex_smLock.writeLock().lock();
				synchronized (mutex) {
					return sm.firstKey();
				}
			} finally {
				mutex_smLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(mutex) * unique(sm) in alive", ensures = "immutable(mutex) * unique(sm) in alive")
		public Object lastKey() {
			try {
				mutex_smLock.writeLock().lock();
				synchronized (mutex) {
					return sm.lastKey();
				}
			} finally {
				mutex_smLock.writeLock().unlock();
			}
		}
	}
	public static final Set EMPTY_SET = new EmptySet();
	private static class EmptySet extends AbstractSet implements Serializable {
		private static final long serialVersionUID = 1582296315990362920L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator iterator() {
			return new Iterator() {
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
	private static class EmptyList extends AbstractList implements RandomAccess, Serializable {
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
	private static class EmptyMap extends AbstractMap implements Serializable {
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
		public Set keySet() {
			return EMPTY_SET;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Collection values() {
			return EMPTY_SET;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Set entrySet() {
			return EMPTY_SET;
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
	public static Set singleton(Object o) {
		return new SingletonSet(o);
	}
	private static class SingletonSet extends AbstractSet implements Serializable {
		public ReentrantReadWriteLock elementLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 3193687207550431679L;
		private Object element;
		@Perm(requires = "unique(element) in alive", ensures = "unique(element) in alive")
		SingletonSet(Object o) {
			elementLock.writeLock().lock();
			element = o;
			elementLock.writeLock().unlock();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator iterator() {
			return new Iterator() {
				private boolean hasNext = true;
				public boolean hasNext() {
					return hasNext;
				}
				public Object next() {
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
		@Perm(requires = "immutable(element) in alive", ensures = "immutable(element) in alive")
		public boolean contains(Object o) {
			return eq(o, element);
		}
	}
	public static List singletonList(Object o) {
		return new SingletonList(o);
	}
	private static class SingletonList extends AbstractList implements RandomAccess, Serializable {
		static final long serialVersionUID = 3093736618740652951L;
		private final Object element;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SingletonList(Object obj) {
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
		public Object get(int index) {
			if (index != 0)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
			return element;
		}
	}
	public static Map singletonMap(Object key, Object value) {
		return new SingletonMap(key, value);
	}
	private static class SingletonMap extends AbstractMap implements Serializable {
		public ReentrantReadWriteLock keySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock entrySetLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock valuesLock = new ReentrantReadWriteLock();
		private final Object k, v;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		SingletonMap(Object key, Object value) {
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
		public Object get(Object key) {
			return (eq(key, k) ? v : null);
		}
		private transient Set keySet = null;
		private transient Set entrySet = null;
		private transient Collection values = null;
		@Perm(requires = "unique(keySet) in alive", ensures = "unique(keySet) in alive")
		public Set keySet() {
			try {
				keySetLock.writeLock().lock();
				if (keySet == null)
					keySet = singleton(k);
				return keySet;
			} finally {
				keySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(entrySet) in alive", ensures = "unique(entrySet) in alive")
		public Set entrySet() {
			try {
				entrySetLock.writeLock().lock();
				if (entrySet == null)
					entrySet = singleton(new ImmutableEntry(k, v));
				return entrySet;
			} finally {
				entrySetLock.writeLock().unlock();
			}
		}
		@Perm(requires = "unique(values) in alive", ensures = "unique(values) in alive")
		public Collection values() {
			try {
				valuesLock.writeLock().lock();
				if (values == null)
					values = singleton(v);
				return values;
			} finally {
				valuesLock.writeLock().unlock();
			}
		}
		private static class ImmutableEntry implements Entry {
			final Object k;
			final Object v;
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			ImmutableEntry(Object key, Object value) {
				k = key;
				v = value;
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Object getKey() {
				return k;
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Object getValue() {
				return v;
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Object setValue(Object value) {
				throw new UnsupportedOperationException();
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean equals(Object o) {
				if (!(o instanceof Map.Entry))
					return false;
				Entry e = (Entry) o;
				return eq(e.getKey(), k) && eq(e.getValue(), v);
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public int hashCode() {
				return ((k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode()));
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public String toString() {
				return k + "=" + v;
			}
		}
	}
	public static List nCopies(int n, Object o) {
		return new CopiesList(n, o);
	}
	private static class CopiesList extends AbstractList implements RandomAccess, Serializable {
		public ReentrantReadWriteLock element_nLock = new ReentrantReadWriteLock();
		static final long serialVersionUID = 2739099268398711800L;
		int n;
		Object element;
		@Perm(requires = "unique(n) * unique(element) in alive", ensures = "unique(n) * unique(element) in alive")
		CopiesList(int n, Object o) {
			if (n < 0)
				throw new IllegalArgumentException("List length = " + n);
			element_nLock.writeLock().lock();
			this.n = n;
			element = o;
			element_nLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(n) in alive", ensures = "immutable(n) in alive")
		public int size() {
			return n;
		}
		@Perm(requires = "immutable(n) * immutable(element) in alive", ensures = "immutable(n) * immutable(element) in alive")
		public boolean contains(Object obj) {
			return n != 0 && eq(obj, element);
		}
		@Perm(requires = "immutable(n) * immutable(element) in alive", ensures = "immutable(n) * immutable(element) in alive")
		public Object get(int index) {
			if (index < 0 || index >= n)
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
			return element;
		}
	}
	public static Comparator reverseOrder() {
		return REVERSE_ORDER;
	}
	private static final Comparator REVERSE_ORDER = new ReverseComparator();
	private static class ReverseComparator implements Comparator, Serializable {
		private static final long serialVersionUID = 7207038068494060240L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int compare(Object o1, Object o2) {
			Comparable c1 = (Comparable) o1;
			Comparable c2 = (Comparable) o2;
			int cmp = c1.compareTo(c2);
			return -(cmp | (cmp >>> 1));
		}
	}
	public static Enumeration enumeration(final Collection c) {
		return new Enumeration() {
			Iterator i = c.iterator();
			public boolean hasMoreElements() {
				return i.hasNext();
			}
			public Object nextElement() {
				return i.next();
			}
		};
	}
	public static ArrayList list(Enumeration e) {
		ArrayList l = new ArrayList();
		while (e.hasMoreElements())
			l.add(e.nextElement());
		return l;
	}
	private static boolean eq(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}
}
