package identityhashmap.entity.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class IdentityHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, java.io.Serializable, Cloneable {
	public ReentrantReadWriteLock entrySet_modCount_size_table_thresholdLock = new ReentrantReadWriteLock();
	private static final int DEFAULT_CAPACITY = 32;
	private static final int MINIMUM_CAPACITY = 4;
	private static final int MAXIMUM_CAPACITY = 1 << 29;
	private transient Object[] table;
	private int size;
	private transient volatile int modCount;
	private transient int threshold;
	private static final Object NULL_KEY = new Object();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static Object maskNull(Object key) {
		return (key == null ? NULL_KEY : key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static Object unmaskNull(Object key) {
		return (key == NULL_KEY ? null : key);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public IdentityHashMap() {
		init(DEFAULT_CAPACITY);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public IdentityHashMap(int expectedMaxSize) {
		if (expectedMaxSize < 0)
			throw new IllegalArgumentException("expectedMaxSize is negative: " + expectedMaxSize);
		init(capacity(expectedMaxSize));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private int capacity(int expectedMaxSize) {
		int minCapacity = (3 * expectedMaxSize) / 2;
		int result;
		if (minCapacity > MAXIMUM_CAPACITY || minCapacity < 0) {
			result = MAXIMUM_CAPACITY;
		} else {
			result = MINIMUM_CAPACITY;
			while (result < minCapacity)
				result <<= 1;
		}
		return result;
	}
	@Perm(requires = "share(threshold) * unique(table) in alive", ensures = "share(threshold) * unique(table) in alive")
	private void init(int initCapacity) {
		entrySet_modCount_size_table_thresholdLock.writeLock().lock();
		threshold = (initCapacity * 2) / 3;
		table = new Object[2 * initCapacity];
		entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public IdentityHashMap(Map<? extends K, ? extends V> m) {
		this((int) ((1 + m.size()) * 1.1));
		putAll(m);
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public int size() {
		try {
			entrySet_modCount_size_table_thresholdLock.readLock().lock();
			return size;
		} finally {
			entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public boolean isEmpty() {
		try {
			entrySet_modCount_size_table_thresholdLock.readLock().lock();
			return size == 0;
		} finally {
			entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int hash(Object x, int length) {
		int h = System.identityHashCode(x);
		return ((h << 1) - (h << 8)) & (length - 1);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private static int nextKeyIndex(int i, int len) {
		return (i + 2 < len ? i + 2 : 0);
	}
	@Perm(requires = "pure(table) in alive", ensures = "pure(table) in alive")
	public V get(Object key) {
		Object k = maskNull(key);
		entrySet_modCount_size_table_thresholdLock.readLock().lock();
		Object[] tab = table;
		entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		int len = tab.length;
		int i = hash(k, len);
		while (true) {
			Object item = tab[i];
			if (item == k)
				return (V) tab[i + 1];
			if (item == null)
				return null;
			i = nextKeyIndex(i, len);
		}
	}
	@Perm(requires = "pure(table) in alive", ensures = "pure(table) in alive")
	public boolean containsKey(Object key) {
		Object k = maskNull(key);
		entrySet_modCount_size_table_thresholdLock.readLock().lock();
		Object[] tab = table;
		entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		int len = tab.length;
		int i = hash(k, len);
		while (true) {
			Object item = tab[i];
			if (item == k)
				return true;
			if (item == null)
				return false;
			i = nextKeyIndex(i, len);
		}
	}
	@Perm(requires = "pure(table) in alive", ensures = "pure(table) in alive")
	public boolean containsValue(Object value) {
		entrySet_modCount_size_table_thresholdLock.readLock().lock();
		Object[] tab = table;
		entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		for (int i = 1; i < tab.length; i += 2)
			if (tab[i] == value && tab[i - 1] != null)
				return true;
		return false;
	}
	@Perm(requires = "pure(table) in alive", ensures = "pure(table) in alive")
	private boolean containsMapping(Object key, Object value) {
		Object k = maskNull(key);
		entrySet_modCount_size_table_thresholdLock.readLock().lock();
		Object[] tab = table;
		entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		int len = tab.length;
		int i = hash(k, len);
		while (true) {
			Object item = tab[i];
			if (item == k)
				return tab[i + 1] == value;
			if (item == null)
				return false;
			i = nextKeyIndex(i, len);
		}
	}
	@Perm(requires = "share(table) * share(modCount) * share(size) * pure(threshold) in alive", ensures = "share(table) * share(modCount) * share(size) * pure(threshold) in alive")
	public V put(K key, V value) {
		Object k = maskNull(key);
		try {
			entrySet_modCount_size_table_thresholdLock.writeLock().lock();
			Object[] tab = table;
			int len = tab.length;
			int i = hash(k, len);
			Object item;
			while ((item = tab[i]) != null) {
				if (item == k) {
					V oldValue = (V) tab[i + 1];
					tab[i + 1] = value;
					return oldValue;
				}
				i = nextKeyIndex(i, len);
			}
			modCount++;
			tab[i] = k;
			tab[i + 1] = value;
			if (++size >= threshold)
				resize(len);
		} finally {
			entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(table) * share(threshold) in alive", ensures = "share(table) * share(threshold) in alive")
	private void resize(int newCapacity) {
		int newLength = newCapacity * 2;
		try {
			entrySet_modCount_size_table_thresholdLock.writeLock().lock();
			Object[] oldTable = table;
			int oldLength = oldTable.length;
			if (oldLength == 2 * MAXIMUM_CAPACITY) {
				if (threshold == MAXIMUM_CAPACITY - 1)
					throw new IllegalStateException("Capacity exhausted.");
				threshold = MAXIMUM_CAPACITY - 1;
				return;
			}
			if (oldLength >= newLength)
				return;
			Object[] newTable = new Object[newLength];
			threshold = newLength / 3;
			for (int j = 0; j < oldLength; j += 2) {
				Object key = oldTable[j];
				if (key != null) {
					Object value = oldTable[j + 1];
					oldTable[j] = null;
					oldTable[j + 1] = null;
					int i = hash(key, newLength);
					while (newTable[i] != null)
						i = nextKeyIndex(i, newLength);
					newTable[i] = key;
					newTable[i + 1] = value;
				}
			}
			table = newTable;
		} finally {
			entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(threshold) in alive", ensures = "pure(threshold) in alive")
	public void putAll(Map<? extends K, ? extends V> m) {
		int n = m.size();
		if (n == 0)
			return;
		entrySet_modCount_size_table_thresholdLock.readLock().lock();
		if (n > threshold)
			resize(capacity(n));
		for (Entry<? extends K, ? extends V> e : m.entrySet())
			put(e.getKey(), e.getValue());
		entrySet_modCount_size_table_thresholdLock.readLock().unlock();
	}
	@Perm(requires = "share(table) * share(modCount) * share(size) in alive", ensures = "share(table) * share(modCount) * share(size) in alive")
	public V remove(Object key) {
		Object k = maskNull(key);
		try {
			entrySet_modCount_size_table_thresholdLock.writeLock().lock();
			Object[] tab = table;
			int len = tab.length;
			int i = hash(k, len);
			while (true) {
				Object item = tab[i];
				if (item == k) {
					modCount++;
					size--;
					V oldValue = (V) tab[i + 1];
					tab[i + 1] = null;
					tab[i] = null;
					closeDeletion(i);
					return oldValue;
				}
				if (item == null)
					return null;
				i = nextKeyIndex(i, len);
			}
		} finally {
			entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(table) * share(modCount) * share(size) in alive", ensures = "pure(table) * share(modCount) * share(size) in alive")
	private boolean removeMapping(Object key, Object value) {
		Object k = maskNull(key);
		try {
			entrySet_modCount_size_table_thresholdLock.readLock().lock();
			entrySet_modCount_size_table_thresholdLock.writeLock().lock();
			Object[] tab = table;
			int len = tab.length;
			int i = hash(k, len);
			while (true) {
				Object item = tab[i];
				if (item == k) {
					if (tab[i + 1] != value)
						return false;
					modCount++;
					size--;
					tab[i] = null;
					tab[i + 1] = null;
					closeDeletion(i);
					return true;
				}
				if (item == null)
					return false;
				i = nextKeyIndex(i, len);
			}
		} finally {
			entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
			entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(table) in alive", ensures = "share(table) in alive")
	private void closeDeletion(int d) {
		entrySet_modCount_size_table_thresholdLock.writeLock().lock();
		Object[] tab = table;
		entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
		int len = tab.length;
		Object item;
		for (int i = nextKeyIndex(d, len); (item = tab[i]) != null; i = nextKeyIndex(i, len)) {
			int r = hash(item, len);
			if ((i < r && (r <= d || d <= i)) || (r <= d && d <= i)) {
				tab[d] = item;
				tab[d + 1] = tab[i + 1];
				tab[i] = null;
				tab[i + 1] = null;
				d = i;
			}
		}
	}
	@Perm(requires = "share(modCount) * pure(table) * share(size) in alive", ensures = "share(modCount) * pure(table) * share(size) in alive")
	public void clear() {
		entrySet_modCount_size_table_thresholdLock.writeLock().lock();
		modCount++;
		Object[] tab = table;
		for (int i = 0; i < tab.length; i++)
			tab[i] = null;
		size = 0;
		entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
	}
	@Perm(requires = "pure(size) * none(table) in alive", ensures = "pure(size) * none(table) in alive")
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o instanceof IdentityHashMap) {
			IdentityHashMap m = (IdentityHashMap) o;
			try {
				entrySet_modCount_size_table_thresholdLock.readLock().lock();
				if (m.size() != size)
					return false;
			} finally {
				entrySet_modCount_size_table_thresholdLock.readLock().unlock();
			}
			Object[] tab = m.table;
			for (int i = 0; i < tab.length; i += 2) {
				Object k = tab[i];
				if (k != null && !containsMapping(k, tab[i + 1]))
					return false;
			}
			return true;
		} else if (o instanceof Map) {
			Map m = (Map) o;
			return entrySet().equals(m.entrySet());
		} else {
			return false;
		}
	}
	@Perm(requires = "pure(table) in alive", ensures = "pure(table) in alive")
	public int hashCode() {
		int result = 0;
		entrySet_modCount_size_table_thresholdLock.readLock().lock();
		Object[] tab = table;
		entrySet_modCount_size_table_thresholdLock.readLock().unlock();
		for (int i = 0; i < tab.length; i += 2) {
			Object key = tab[i];
			if (key != null) {
				Object k = unmaskNull(key);
				result += System.identityHashCode(k) ^ System.identityHashCode(tab[i + 1]);
			}
		}
		return result;
	}
	@Perm(requires = "unique(entrySet) * immutable(entrySet) * share(table) in alive", ensures = "unique(entrySet) * immutable(entrySet) * share(table) in alive")
	public Object clone() {
		try {
			IdentityHashMap<K, V> m = (IdentityHashMap<K, V>) super.clone();
			entrySet_modCount_size_table_thresholdLock.writeLock().lock();
			m.entrySet = null;
			m.table = (Object[]) table.clone();
			entrySet_modCount_size_table_thresholdLock.writeLock().unlock();
			return m;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	private abstract class IdentityHashMapIterator<T> implements Iterator<T> {
		public ReentrantReadWriteLock expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock = new ReentrantReadWriteLock();
		int index = (size != 0 ? 0 : table.length);
		int expectedModCount = modCount;
		int lastReturnedIndex = -1;
		boolean indexValid;
		Object[] traversalTable = table;
		@Perm(requires = "pure(traversalTable) * share(index) * share(indexValid) in alive", ensures = "pure(traversalTable) * share(index) * share(indexValid) in alive")
		public boolean hasNext() {
			try {
				expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock.writeLock()
						.lock();
				Object[] tab = traversalTable;
				for (int i = index; i < tab.length; i += 2) {
					Object key = tab[i];
					if (key != null) {
						index = i;
						return indexValid = true;
					}
				}
				index = tab.length;
			} finally {
				expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock.writeLock()
						.unlock();
			}
			return false;
		}
		@Perm(requires = "pure(modCount) * pure(expectedModCount) * share(indexValid) * share(lastReturnedIndex) * share(index) in alive", ensures = "pure(modCount) * pure(expectedModCount) * share(indexValid) * share(lastReturnedIndex) * share(index) in alive")
		protected int nextIndex() {
			try {
				expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock.writeLock()
						.lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				if (!indexValid && !hasNext())
					throw new NoSuchElementException();
				indexValid = false;
				lastReturnedIndex = index;
				index += 2;
				return lastReturnedIndex;
			} finally {
				expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock.writeLock()
						.unlock();
			}
		}
		@Perm(requires = "share(lastReturnedIndex) * share(modCount) * full(expectedModCount) * share(size) * share(index) * share(indexValid) * full(traversalTable) * pure(table) in alive", ensures = "share(lastReturnedIndex) * share(modCount) * full(expectedModCount) * share(size) * share(index) * share(indexValid) * full(traversalTable) * pure(table) in alive")
		public void remove() {
			try {
				expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock.writeLock()
						.lock();
				if (lastReturnedIndex == -1)
					throw new IllegalStateException();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				expectedModCount = ++modCount;
				int deletedSlot = lastReturnedIndex;
				lastReturnedIndex = -1;
				size--;
				index = deletedSlot;
				indexValid = false;
				Object[] tab = traversalTable;
				int len = tab.length;
				int d = deletedSlot;
				K key = (K) tab[d];
				tab[d] = null;
				tab[d + 1] = null;
				if (tab != IdentityHashMap.this.table) {
					IdentityHashMap.this.remove(key);
					expectedModCount = modCount;
					return;
				}
				Object item;
				for (int i = nextKeyIndex(d, len); (item = tab[i]) != null; i = nextKeyIndex(i, len)) {
					int r = hash(item, len);
					if ((i < r && (r <= d || d <= i)) || (r <= d && d <= i)) {
						if (i < deletedSlot && d >= deletedSlot && traversalTable == IdentityHashMap.this.table) {
							int remaining = len - deletedSlot;
							Object[] newTable = new Object[remaining];
							System.arraycopy(tab, deletedSlot, newTable, 0, remaining);
							traversalTable = newTable;
							index = 0;
						}
						tab[d] = item;
						tab[d + 1] = tab[i + 1];
						tab[i] = null;
						tab[i + 1] = null;
						d = i;
					}
				}
			} finally {
				expectedModCount_index_indexValid_lastReturnedIndex_modCount_size_table_traversalTableLock.writeLock()
						.unlock();
			}
		}
	}
	private class KeyIterator extends IdentityHashMapIterator<K> {
		public ReentrantReadWriteLock traversalTableLock = new ReentrantReadWriteLock();
		@Perm(requires = "none(traversalTable) in alive", ensures = "none(traversalTable) in alive")
		public K next() {
			return (K) unmaskNull(traversalTable[nextIndex()]);
		}
	}
	private class ValueIterator extends IdentityHashMapIterator<V> {
		public ReentrantReadWriteLock traversalTableLock = new ReentrantReadWriteLock();
		@Perm(requires = "none(traversalTable) in alive", ensures = "none(traversalTable) in alive")
		public V next() {
			return (V) traversalTable[nextIndex() + 1];
		}
	}
	private class EntryIterator extends IdentityHashMapIterator<Map.Entry<K, V>> implements Map.Entry<K, V> {
		public ReentrantReadWriteLock lastReturnedIndex_table_traversalTableLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Map.Entry<K, V> next() {
			nextIndex();
			return this;
		}
		@Perm(requires = "immutable(lastReturnedIndex) * pure(traversalTable) in alive", ensures = "immutable(lastReturnedIndex) * pure(traversalTable) in alive")
		public K getKey() {
			if (lastReturnedIndex < 0)
				throw new IllegalStateException("Entry was removed");
			try {
				lastReturnedIndex_table_traversalTableLock.readLock().lock();
				return (K) unmaskNull(traversalTable[lastReturnedIndex]);
			} finally {
				lastReturnedIndex_table_traversalTableLock.readLock().unlock();
			}
		}
		@Perm(requires = "immutable(lastReturnedIndex) * pure(traversalTable) in alive", ensures = "immutable(lastReturnedIndex) * pure(traversalTable) in alive")
		public V getValue() {
			if (lastReturnedIndex < 0)
				throw new IllegalStateException("Entry was removed");
			try {
				lastReturnedIndex_table_traversalTableLock.readLock().lock();
				return (V) traversalTable[lastReturnedIndex + 1];
			} finally {
				lastReturnedIndex_table_traversalTableLock.readLock().unlock();
			}
		}
		@Perm(requires = "immutable(lastReturnedIndex) * full(traversalTable) * pure(table) in alive", ensures = "immutable(lastReturnedIndex) * full(traversalTable) * pure(table) in alive")
		public V setValue(V value) {
			try {
				lastReturnedIndex_table_traversalTableLock.writeLock().lock();
				if (lastReturnedIndex < 0)
					throw new IllegalStateException("Entry was removed");
				V oldValue = (V) traversalTable[lastReturnedIndex + 1];
				traversalTable[lastReturnedIndex + 1] = value;
				if (traversalTable != IdentityHashMap.this.table)
					put((K) traversalTable[lastReturnedIndex], value);
			} finally {
				lastReturnedIndex_table_traversalTableLock.writeLock().unlock();
			}
			return oldValue;
		}
		@Perm(requires = "immutable(lastReturnedIndex) in alive", ensures = "immutable(lastReturnedIndex) in alive")
		public boolean equals(Object o) {
			if (lastReturnedIndex < 0)
				return super.equals(o);
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			return e.getKey() == getKey() && e.getValue() == getValue();
		}
		@Perm(requires = "immutable(lastReturnedIndex) in alive", ensures = "immutable(lastReturnedIndex) in alive")
		public int hashCode() {
			if (lastReturnedIndex < 0)
				return super.hashCode();
			return System.identityHashCode(getKey()) ^ System.identityHashCode(getValue());
		}
		@Perm(requires = "immutable(lastReturnedIndex) in alive", ensures = "immutable(lastReturnedIndex) in alive")
		public String toString() {
			if (lastReturnedIndex < 0)
				return super.toString();
			return getKey() + "=" + getValue();
		}
	}
	private transient Set<Map.Entry<K, V>> entrySet = null;
	public Set<K> keySet() {
		Set<K> ks = keySet;
		if (ks != null)
			return ks;
		else
			return keySet = new KeySet();
	}
	private class KeySet extends AbstractSet<K> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<K> iterator() {
			return new KeyIterator();
		}
		@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
		public int size() {
			try {
				sizeLock.readLock().lock();
				return size;
			} finally {
				sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return containsKey(o);
		}
		@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
		public boolean remove(Object o) {
			try {
				sizeLock.readLock().lock();
				int oldSize = size;
				IdentityHashMap.this.remove(o);
				return size != oldSize;
			} finally {
				sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean removeAll(Collection<?> c) {
			boolean modified = false;
			for (Iterator i = iterator(); i.hasNext();) {
				if (c.contains(i.next())) {
					i.remove();
					modified = true;
				}
			}
			return modified;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			IdentityHashMap.this.clear();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int hashCode() {
			int result = 0;
			for (K key : this)
				result += System.identityHashCode(key);
			return result;
		}
	}
	public Collection<V> values() {
		Collection<V> vs = values;
		if (vs != null)
			return vs;
		else
			return values = new Values();
	}
	private class Values extends AbstractCollection<V> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<V> iterator() {
			return new ValueIterator();
		}
		@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
		public int size() {
			try {
				sizeLock.readLock().lock();
				return size;
			} finally {
				sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return containsValue(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			for (Iterator i = iterator(); i.hasNext();) {
				if (i.next() == o) {
					i.remove();
					return true;
				}
			}
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			IdentityHashMap.this.clear();
		}
	}
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> es = entrySet;
		if (es != null)
			return es;
		else
			return entrySet = new EntrySet();
	}
	private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<Map.Entry<K, V>> iterator() {
			return new EntryIterator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry entry = (Map.Entry) o;
			return containsMapping(entry.getKey(), entry.getValue());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry entry = (Map.Entry) o;
			return removeMapping(entry.getKey(), entry.getValue());
		}
		@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
		public int size() {
			try {
				sizeLock.readLock().lock();
				return size;
			} finally {
				sizeLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			IdentityHashMap.this.clear();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean removeAll(Collection<?> c) {
			boolean modified = false;
			for (Iterator i = iterator(); i.hasNext();) {
				if (c.contains(i.next())) {
					i.remove();
					modified = true;
				}
			}
			return modified;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object[] toArray() {
			int size = size();
			Object[] result = new Object[size];
			Iterator<Map.Entry<K, V>> it = iterator();
			for (int i = 0; i < size; i++)
				result[i] = new AbstractMap.SimpleEntry<K, V>(it.next());
			return result;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		@SuppressWarnings("unchecked")
		public <T> T[] toArray(T[] a) {
			int size = size();
			if (a.length < size)
				a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
			Iterator<Map.Entry<K, V>> it = iterator();
			for (int i = 0; i < size; i++)
				a[i] = (T) new AbstractMap.SimpleEntry<K, V>(it.next());
			if (a.length > size)
				a[size] = null;
			return a;
		}
	}
	private static final long serialVersionUID = 8188218128353913216L;
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		s.defaultWriteObject();
		s.writeInt(size);
		Object[] tab = table;
		for (int i = 0; i < tab.length; i += 2) {
			Object key = tab[i];
			if (key != null) {
				s.writeObject(unmaskNull(key));
				s.writeObject(tab[i + 1]);
			}
		}
	}
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		int size = s.readInt();
		init(capacity((size * 4) / 3));
		for (int i = 0; i < size; i++) {
			K key = (K) s.readObject();
			V value = (V) s.readObject();
			putForCreate(key, value);
		}
	}
	private void putForCreate(K key, V value) throws IOException {
		K k = (K) maskNull(key);
		Object[] tab = table;
		int len = tab.length;
		int i = hash(k, len);
		Object item;
		while ((item = tab[i]) != null) {
			if (item == k)
				throw new java.io.StreamCorruptedException();
			i = nextKeyIndex(i, len);
		}
		tab[i] = k;
		tab[i + 1] = value;
	}
}
