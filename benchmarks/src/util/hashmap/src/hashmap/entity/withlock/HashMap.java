package hashmap.entity.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
	public ReentrantReadWriteLock entrySet_modCount_next_size_table_threshold_valueLock = new ReentrantReadWriteLock();
	static final int DEFAULT_INITIAL_CAPACITY = 16;
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	transient Entry[] table;
	transient int size;
	int threshold;
	final float loadFactor;
	transient volatile int modCount;
	@Perm(requires = "unique(threshold) * unique(table) in alive", ensures = "unique(threshold) * unique(table) in alive")
	public HashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		int capacity = 1;
		while (capacity < initialCapacity)
			capacity <<= 1;
		this.loadFactor = loadFactor;
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
		threshold = (int) (capacity * loadFactor);
		table = new Entry[capacity];
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		init();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public HashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}
	@Perm(requires = "unique(threshold) * unique(table) in alive", ensures = "unique(threshold) * unique(table) in alive")
	public HashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
		threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
		table = new Entry[DEFAULT_INITIAL_CAPACITY];
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		init();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public HashMap(Map<? extends K, ? extends V> m) {
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
		putAllForCreate(m);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	void init() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	static int indexFor(int h, int length) {
		return h & (length - 1);
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public int size() {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			return size;
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public boolean isEmpty() {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			return size == 0;
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(table) * pure(next) * immutable(next) * pure(value) in alive", ensures = "pure(table) * pure(next) * immutable(next) * pure(value) in alive")
	public V get(Object key) {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			if (key == null)
				return getForNullKey();
			int hash = hash(key.hashCode());
			for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
				Object k;
				if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
					return e.value;
			}
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
		return null;
	}
	@Perm(requires = "pure(table) * pure(next) * immutable(next) * pure(value) in alive", ensures = "pure(table) * pure(next) * immutable(next) * pure(value) in alive")
	private V getForNullKey() {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			for (Entry<K, V> e = table[0]; e != null; e = e.next) {
				if (e.key == null)
					return e.value;
			}
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
		return null;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}
	@Perm(requires = "pure(table) * pure(next) * immutable(next) in alive", ensures = "pure(table) * pure(next) * immutable(next) in alive")
	final Entry<K, V> getEntry(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			for (Entry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
				Object k;
				if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
					return e;
			}
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
		return null;
	}
	@Perm(requires = "pure(table) * share(next) * immutable(next) * share(value) * share(modCount) in alive", ensures = "pure(table) * share(next) * immutable(next) * share(value) * share(modCount) in alive")
	public V put(K key, V value) {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
			if (key == null)
				return putForNullKey(value);
			int hash = hash(key.hashCode());
			int i = indexFor(hash, table.length);
			for (Entry<K, V> e = table[i]; e != null; e = e.next) {
				Object k;
				if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
					V oldValue = e.value;
					e.value = value;
					e.recordAccess(this);
					return oldValue;
				}
			}
			modCount++;
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		}
		addEntry(hash, key, value, i);
		return null;
	}
	@Perm(requires = "pure(table) * share(next) * immutable(next) * share(value) * share(modCount) in alive", ensures = "pure(table) * share(next) * immutable(next) * share(value) * share(modCount) in alive")
	private V putForNullKey(V value) {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
			for (Entry<K, V> e = table[0]; e != null; e = e.next) {
				if (e.key == null) {
					V oldValue = e.value;
					e.value = value;
					e.recordAccess(this);
					return oldValue;
				}
			}
			modCount++;
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		}
		addEntry(0, null, value, 0);
		return null;
	}
	@Perm(requires = "pure(table) * share(next) * immutable(next) * share(value) in alive", ensures = "pure(table) * share(next) * immutable(next) * share(value) in alive")
	private void putForCreate(K key, V value) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
			int i = indexFor(hash, table.length);
			for (Entry<K, V> e = table[i]; e != null; e = e.next) {
				Object k;
				if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
					e.value = value;
					return;
				}
			}
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		}
		createEntry(hash, key, value, i);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	private void putAllForCreate(Map<? extends K, ? extends V> m) {
		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext();) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			putForCreate(e.getKey(), e.getValue());
		}
	}
	@Perm(requires = "share(table) * full(threshold) in alive", ensures = "share(table) * full(threshold) in alive")
	void resize(int newCapacity) {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
			Entry[] oldTable = table;
			int oldCapacity = oldTable.length;
			if (oldCapacity == MAXIMUM_CAPACITY) {
				threshold = Integer.MAX_VALUE;
				return;
			}
			Entry[] newTable = new Entry[newCapacity];
			transfer(newTable);
			table = newTable;
			threshold = (int) (newCapacity * loadFactor);
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(table) * pure(next) * immutable(next) in alive", ensures = "pure(table) * pure(next) * immutable(next) in alive")
	void transfer(Entry[] newTable) {
		entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
		Entry[] src = table;
		entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		int newCapacity = newTable.length;
		for (int j = 0; j < src.length; j++) {
			Entry<K, V> e = src[j];
			if (e != null) {
				src[j] = null;
				do {
					Entry<K, V> next = e.next;
					int i = indexFor(e.hash, newCapacity);
					e.next = newTable[i];
					newTable[i] = e;
					e = next;
				} while (e != null);
			}
		}
	}
	@Perm(requires = "pure(threshold) * pure(table) in alive", ensures = "pure(threshold) * pure(table) in alive")
	public void putAll(Map<? extends K, ? extends V> m) {
		int numKeysToBeAdded = m.size();
		if (numKeysToBeAdded == 0)
			return;
		entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
		if (numKeysToBeAdded > threshold) {
			int targetCapacity = (int) (numKeysToBeAdded / loadFactor + 1);
			if (targetCapacity > MAXIMUM_CAPACITY)
				targetCapacity = MAXIMUM_CAPACITY;
			int newCapacity = table.length;
			while (newCapacity < targetCapacity)
				newCapacity <<= 1;
			if (newCapacity > table.length)
				resize(newCapacity);
		}
		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext();) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			put(e.getKey(), e.getValue());
		}
		entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
	}
	@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
	public V remove(Object key) {
		Entry<K, V> e = removeEntryForKey(key);
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			return (e == null ? null : e.value);
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(table) * share(next) * immutable(next) * share(modCount) * share(size) in alive", ensures = "share(table) * share(next) * immutable(next) * share(modCount) * share(size) in alive")
	final Entry<K, V> removeEntryForKey(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
			int i = indexFor(hash, table.length);
			Entry<K, V> prev = table[i];
			Entry<K, V> e = prev;
			while (e != null) {
				Entry<K, V> next = e.next;
				Object k;
				if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
					modCount++;
					size--;
					if (prev == e)
						table[i] = next;
					else
						prev.next = next;
					e.recordRemoval(this);
					return e;
				}
				prev = e;
				e = next;
			}
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		}
		return e;
	}
	@Perm(requires = "share(table) * share(next) * immutable(next) * share(modCount) * share(size) in alive", ensures = "share(table) * share(next) * immutable(next) * share(modCount) * share(size) in alive")
	final Entry<K, V> removeMapping(Object o) {
		if (!(o instanceof Map.Entry))
			return null;
		Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
		Object key = entry.getKey();
		int hash = (key == null) ? 0 : hash(key.hashCode());
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
			int i = indexFor(hash, table.length);
			Entry<K, V> prev = table[i];
			Entry<K, V> e = prev;
			while (e != null) {
				Entry<K, V> next = e.next;
				if (e.hash == hash && e.equals(entry)) {
					modCount++;
					size--;
					if (prev == e)
						table[i] = next;
					else
						prev.next = next;
					e.recordRemoval(this);
					return e;
				}
				prev = e;
				e = next;
			}
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		}
		return e;
	}
	@Perm(requires = "share(modCount) * pure(table) * share(size) in alive", ensures = "share(modCount) * pure(table) * share(size) in alive")
	public void clear() {
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
		modCount++;
		Entry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			tab[i] = null;
		size = 0;
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
	}
	@Perm(requires = "pure(table) * immutable(next) * immutable(value) in alive", ensures = "pure(table) * immutable(next) * immutable(value) in alive")
	public boolean containsValue(Object value) {
		try {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
			if (value == null)
				return containsNullValue();
			Entry[] tab = table;
		} finally {
			entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		}
		for (int i = 0; i < tab.length; i++)
			for (Entry e = tab[i]; e != null; e = e.next)
				if (value.equals(e.value))
					return true;
		return false;
	}
	@Perm(requires = "pure(table) * immutable(next) * immutable(value) in alive", ensures = "pure(table) * immutable(next) * immutable(value) in alive")
	private boolean containsNullValue() {
		entrySet_modCount_next_size_table_threshold_valueLock.readLock().lock();
		Entry[] tab = table;
		entrySet_modCount_next_size_table_threshold_valueLock.readLock().unlock();
		for (int i = 0; i < tab.length; i++)
			for (Entry e = tab[i]; e != null; e = e.next)
				if (e.value == null)
					return true;
		return false;
	}
	@Perm(requires = "unique(table) * unique(entrySet) * immutable(entrySet) * pure(modCount) * pure(size) in alive", ensures = "unique(table) * unique(entrySet) * immutable(entrySet) * pure(modCount) * pure(size) in alive")
	public Object clone() {
		HashMap<K, V> result = null;
		try {
			result = (HashMap<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().lock();
		result.table = new Entry[table.length];
		result.entrySet = null;
		result.modCount = 0;
		result.size = 0;
		entrySet_modCount_next_size_table_threshold_valueLock.writeLock().unlock();
		result.init();
		result.putAllForCreate(this);
		return result;
	}
	static class Entry<K, V> implements Map.Entry<K, V> {
		public ReentrantReadWriteLock next_valueLock = new ReentrantReadWriteLock();
		final K key;
		V value;
		Entry<K, V> next;
		final int hash;
		@Perm(requires = "unique(value) * unique(next) * none(next) in alive", ensures = "unique(value) * unique(next) * none(next) in alive")
		Entry(int h, K k, V v, Entry<K, V> n) {
			next_valueLock.writeLock().lock();
			value = v;
			next = n;
			next_valueLock.writeLock().unlock();
			key = k;
			hash = h;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K getKey() {
			return key;
		}
		@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
		public final V getValue() {
			try {
				next_valueLock.readLock().lock();
				return value;
			} finally {
				next_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "share(value) in alive", ensures = "share(value) in alive")
		public final V setValue(V newValue) {
			next_valueLock.writeLock().lock();
			V oldValue = value;
			value = newValue;
			next_valueLock.writeLock().unlock();
			return oldValue;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
			return false;
		}
		@Perm(requires = "share(value) in alive", ensures = "share(value) in alive")
		public final int hashCode() {
			try {
				next_valueLock.writeLock().lock();
				return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
			} finally {
				next_valueLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final String toString() {
			return getKey() + "=" + getValue();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		void recordAccess(HashMap<K, V> m) {
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		void recordRemoval(HashMap<K, V> m) {
		}
	}
	void addEntry(int hash, K key, V value, int bucketIndex) {
		Entry<K, V> e = table[bucketIndex];
		table[bucketIndex] = new Entry<K, V>(hash, key, value, e);
		if (size++ >= threshold)
			resize(2 * table.length);
	}
	void createEntry(int hash, K key, V value, int bucketIndex) {
		Entry<K, V> e = table[bucketIndex];
		table[bucketIndex] = new Entry<K, V>(hash, key, value, e);
		size++;
	}
	private abstract class HashIterator<E> implements Iterator<E> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock current_expectedModCount_index_modCount_next_tableLock = new ReentrantReadWriteLock();
		Entry<K, V> next;
		int expectedModCount;
		int index;
		Entry<K, V> current;
		@Perm(requires = "unique(expectedModCount) * none(modCount) * none(size) * none(table) * unique(index) * unique(next) * none(next) in alive", ensures = "unique(expectedModCount) * none(modCount) * none(size) * none(table) * unique(index) * unique(next) * none(next) in alive")
		HashIterator() {
			current_expectedModCount_index_modCount_next_tableLock.writeLock().lock();
			expectedModCount = modCount;
			if (size > 0) {
				Entry[] t = table;
				while (index < t.length && (next = t[index++]) == null);
			}
			current_expectedModCount_index_modCount_next_tableLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(next) in alive", ensures = "immutable(next) in alive")
		public final boolean hasNext() {
			return next != null;
		}
		@Perm(requires = "pure(modCount) * pure(expectedModCount) * unique(next) * immutable(next) * share(next) * immutable(next) * pure(table) * unique(index) * share(current) * immutable(current) in alive", ensures = "pure(modCount) * pure(expectedModCount) * unique(next) * immutable(next) * share(next) * immutable(next) * pure(table) * unique(index) * share(current) * immutable(current) in alive")
		final Entry<K, V> nextEntry() {
			try {
				current_expectedModCount_index_modCount_next_tableLock.writeLock().lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				Entry<K, V> e = next;
				if (e == null)
					throw new NoSuchElementException();
				if ((next = e.next) == null) {
					Entry[] t = table;
					while (index < t.length && (next = t[index++]) == null);
				}
				current = e;
			} finally {
				current_expectedModCount_index_modCount_next_tableLock.writeLock().unlock();
			}
			return e;
		}
		@Perm(requires = "immutable(current) * pure(modCount) * full(expectedModCount) * unique(current) in alive", ensures = "immutable(current) * pure(modCount) * full(expectedModCount) * unique(current) in alive")
		public void remove() {
			try {
				current_expectedModCount_index_modCount_next_tableLock.writeLock().lock();
				if (current == null)
					throw new IllegalStateException();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				Object k = current.key;
				current = null;
				HashMap.this.removeEntryForKey(k);
				expectedModCount = modCount;
			} finally {
				current_expectedModCount_index_modCount_next_tableLock.writeLock().unlock();
			}
		}
	}
	private final class ValueIterator extends HashIterator<V> {
		public ReentrantReadWriteLock valueLock = new ReentrantReadWriteLock();
		@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
		public V next() {
			try {
				valueLock.readLock().lock();
				return nextEntry().value;
			} finally {
				valueLock.readLock().unlock();
			}
		}
	}
	private final class KeyIterator extends HashIterator<K> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K next() {
			return nextEntry().getKey();
		}
	}
	private final class EntryIterator extends HashIterator<Map.Entry<K, V>> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Map.Entry<K, V> next() {
			return nextEntry();
		}
	}
	Iterator<K> newKeyIterator() {
		return new KeyIterator();
	}
	Iterator<V> newValueIterator() {
		return new ValueIterator();
	}
	Iterator<Map.Entry<K, V>> newEntryIterator() {
		return new EntryIterator();
	}
	private transient Set<Map.Entry<K, V>> entrySet = null;
	public Set<K> keySet() {
		Set<K> ks = keySet;
		return (ks != null ? ks : (keySet = new KeySet()));
	}
	private final class KeySet extends AbstractSet<K> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<K> iterator() {
			return newKeyIterator();
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
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			return HashMap.this.removeEntryForKey(o) != null;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			HashMap.this.clear();
		}
	}
	public Collection<V> values() {
		Collection<V> vs = values;
		return (vs != null ? vs : (values = new Values()));
	}
	private final class Values extends AbstractCollection<V> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<V> iterator() {
			return newValueIterator();
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
		public void clear() {
			HashMap.this.clear();
		}
	}
	public Set<Map.Entry<K, V>> entrySet() {
		return entrySet0();
	}
	private Set<Map.Entry<K, V>> entrySet0() {
		Set<Map.Entry<K, V>> es = entrySet;
		return es != null ? es : (entrySet = new EntrySet());
	}
	private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public ReentrantReadWriteLock sizeLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<Map.Entry<K, V>> iterator() {
			return newEntryIterator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<K, V> e = (Map.Entry<K, V>) o;
			Entry<K, V> candidate = getEntry(e.getKey());
			return candidate != null && candidate.equals(e);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			return removeMapping(o) != null;
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
			HashMap.this.clear();
		}
	}
	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		Iterator<Map.Entry<K, V>> i = (size > 0) ? entrySet0().iterator() : null;
		s.defaultWriteObject();
		s.writeInt(table.length);
		s.writeInt(size);
		if (i != null) {
			while (i.hasNext()) {
				Map.Entry<K, V> e = i.next();
				s.writeObject(e.getKey());
				s.writeObject(e.getValue());
			}
		}
	}
	private static final long serialVersionUID = 362498820763181265L;
	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int numBuckets = s.readInt();
		table = new Entry[numBuckets];
		init();
		int size = s.readInt();
		for (int i = 0; i < size; i++) {
			K key = (K) s.readObject();
			V value = (V) s.readObject();
			putForCreate(key, value);
		}
	}
	int capacity() {
		return table.length;
	}
	float loadFactor() {
		return loadFactor;
	}
}
