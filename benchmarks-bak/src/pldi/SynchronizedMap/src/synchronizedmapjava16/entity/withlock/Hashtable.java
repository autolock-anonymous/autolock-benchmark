package synchronizedmapjava16.entity.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Hashtable<K, V> extends Dictionary<K, V> implements Map<K, V>, Cloneable, Serializable {
	public static ReentrantReadWriteLock emptyIteratorLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock emptyEnumeratorLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock = new ReentrantReadWriteLock();
	private transient Entry[] table;
	private transient int count;
	private int threshold;
	private float loadFactor;
	private transient int modCount = 0;
	private static final long serialVersionUID = 1421746759512286392L;
	@Perm(requires = "unique(loadFactor) * unique(table) * unique(threshold) in alive", ensures = "unique(loadFactor) * unique(table) * unique(threshold) in alive")
	public Hashtable(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal Load: " + loadFactor);
		if (initialCapacity == 0)
			initialCapacity = 1;
		count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
		this.loadFactor = loadFactor;
		table = new Entry[initialCapacity];
		threshold = (int) (initialCapacity * loadFactor);
		count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Hashtable(int initialCapacity) {
		this(initialCapacity, 0.75f);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Hashtable() {
		this(11, 0.75f);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Hashtable(Map<? extends K, ? extends V> t) {
		this(Math.max(2 * t.size(), 11), 0.75f);
		putAll(t);
	}
	@Perm(requires = "pure(count) in alive", ensures = "pure(count) in alive")
	public synchronized int size() {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock().lock();
			return count;
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock()
					.unlock();
		}
	}
	@Perm(requires = "pure(count) in alive", ensures = "pure(count) in alive")
	public synchronized boolean isEmpty() {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock().lock();
			return count == 0;
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock()
					.unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized Enumeration<K> keys() {
		return this.<K>getEnumeration(KEYS);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized Enumeration<V> elements() {
		return this.<V>getEnumeration(VALUES);
	}
	@Perm(requires = "share(table) * pure(next) * immutable(next) * share(value) in alive", ensures = "share(table) * pure(next) * immutable(next) * share(value) in alive")
	public synchronized boolean contains(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			Entry tab[] = table;
			for (int i = tab.length; i-- > 0;) {
				for (Entry<K, V> e = tab[i]; e != null; e = e.next) {
					if (e.value.equals(value)) {
						return true;
					}
				}
			}
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
		}
		return false;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean containsValue(Object value) {
		return contains(value);
	}
	@Perm(requires = "pure(table) * pure(next) * immutable(next) * immutable(hash) * share(key) in alive", ensures = "pure(table) * pure(next) * immutable(next) * immutable(hash) * share(key) in alive")
	public synchronized boolean containsKey(Object key) {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			Entry tab[] = table;
			int hash = key.hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;
			for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
				if ((e.hash == hash) && e.key.equals(key)) {
					return true;
				}
			}
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
		}
		return false;
	}
	@Perm(requires = "pure(table) * pure(next) * immutable(next) * immutable(hash) * share(key) * pure(value) in alive", ensures = "pure(table) * pure(next) * immutable(next) * immutable(hash) * share(key) * pure(value) in alive")
	public synchronized V get(Object key) {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			Entry tab[] = table;
			int hash = key.hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;
			for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
				if ((e.hash == hash) && e.key.equals(key)) {
					return e.value;
				}
			}
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
		}
		return null;
	}
	@Perm(requires = "share(table) * share(modCount) * full(threshold) * pure(loadFactor) * pure(next) * immutable(next) * immutable(hash) in alive", ensures = "share(table) * share(modCount) * full(threshold) * pure(loadFactor) * pure(next) * immutable(next) * immutable(hash) in alive")
	public void rehash() {
		count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
		int oldCapacity = table.length;
		Entry[] oldMap = table;
		int newCapacity = oldCapacity * 2 + 1;
		Entry[] newMap = new Entry[newCapacity];
		modCount++;
		threshold = (int) (newCapacity * loadFactor);
		table = newMap;
		for (int i = oldCapacity; i-- > 0;) {
			for (Entry<K, V> old = oldMap[i]; old != null;) {
				Entry<K, V> e = old;
				old = old.next;
				int index = (e.hash & 0x7FFFFFFF) % newCapacity;
				e.next = newMap[index];
				newMap[index] = e;
			}
		}
		count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().unlock();
	}
	@Perm(requires = "share(table) * share(next) * immutable(next) * immutable(hash) * share(key) * share(value) * share(modCount) * share(count) * pure(threshold) in alive", ensures = "share(table) * share(next) * immutable(next) * immutable(hash) * share(key) * share(value) * share(modCount) * share(count) * pure(threshold) in alive")
	public synchronized V put(K key, V value) {
		if (value == null) {
			throw new NullPointerException();
		}
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			Entry tab[] = table;
			int hash = key.hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;
			for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
				if ((e.hash == hash) && e.key.equals(key)) {
					V old = e.value;
					e.value = value;
					return old;
				}
			}
			modCount++;
			if (count >= threshold) {
				rehash();
				tab = table;
				index = (hash & 0x7FFFFFFF) % tab.length;
			}
			Entry<K, V> e = tab[index];
			tab[index] = new Entry<K, V>(hash, key, value, e);
			count++;
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
		}
		return null;
	}
	@Perm(requires = "share(table) * share(next) * immutable(next) * immutable(hash) * share(key) * share(modCount) * share(count) * unique(value) in alive", ensures = "share(table) * share(next) * immutable(next) * immutable(hash) * share(key) * share(modCount) * share(count) * unique(value) in alive")
	public synchronized V remove(Object key) {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			Entry tab[] = table;
			int hash = key.hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;
			for (Entry<K, V> e = tab[index], prev = null; e != null; prev = e, e = e.next) {
				if ((e.hash == hash) && e.key.equals(key)) {
					modCount++;
					if (prev != null) {
						prev.next = e.next;
					} else {
						tab[index] = e.next;
					}
					count--;
					V oldValue = e.value;
					e.value = null;
					return oldValue;
				}
			}
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
		}
		return null;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized void putAll(Map<? extends K, ? extends V> t) {
		for (Map.Entry<? extends K, ? extends V> e : t.entrySet())
			put(e.getKey(), e.getValue());
	}
	@Perm(requires = "pure(table) * share(modCount) * share(count) in alive", ensures = "pure(table) * share(modCount) * share(count) in alive")
	public synchronized void clear() {
		count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
		Entry tab[] = table;
		modCount++;
		for (int index = tab.length; --index >= 0;)
			tab[index] = null;
		count = 0;
		count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().unlock();
	}
	@Perm(requires = "unique(table) * unique(keySet) * immutable(keySet) * unique(entrySet) * immutable(entrySet) * unique(values) * immutable(values) * pure(modCount) in alive", ensures = "unique(table) * unique(keySet) * immutable(keySet) * unique(entrySet) * immutable(entrySet) * unique(values) * immutable(values) * pure(modCount) in alive")
	public synchronized Object clone() {
		try {
			Hashtable<K, V> t = (Hashtable<K, V>) super.clone();
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			t.table = new Entry[table.length];
			for (int i = table.length; i-- > 0;) {
				t.table[i] = (table[i] != null) ? (Entry<K, V>) table[i].clone() : null;
			}
			t.keySet = null;
			t.entrySet = null;
			t.values = null;
			t.modCount = 0;
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
			return t;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public synchronized String toString() {
		int max = size() - 1;
		if (max == -1)
			return "{}";
		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<K, V>> it = entrySet().iterator();
		sb.append('{');
		for (int i = 0;; i++) {
			Map.Entry<K, V> e = it.next();
			K key = e.getKey();
			V value = e.getValue();
			sb.append(key == this ? "(this Map)" : key.toString());
			sb.append('=');
			sb.append(value == this ? "(this Map)" : value.toString());
			if (i == max)
				return sb.append('}').toString();
			sb.append(", ");
		}
	}
	@Perm(requires = "pure(count) * none(emptyEnumerator) in alive", ensures = "pure(count) * none(emptyEnumerator) in alive")
	private <T> Enumeration<T> getEnumeration(int type) {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock().lock();
			if (count == 0) {
				return (Enumeration<T>) emptyEnumerator;
			} else {
				return new Enumerator<T>(type, false);
			}
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock()
					.unlock();
		}
	}
	@Perm(requires = "pure(count) * none(emptyIterator) in alive", ensures = "pure(count) * none(emptyIterator) in alive")
	private <T> Iterator<T> getIterator(int type) {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock().lock();
			if (count == 0) {
				return (Iterator<T>) emptyIterator;
			} else {
				return new Enumerator<T>(type, true);
			}
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.readLock()
					.unlock();
		}
	}
	private transient volatile Set<K> keySet = null;
	private transient volatile Set<Map.Entry<K, V>> entrySet = null;
	private transient volatile Collection<V> values = null;
	@Perm(requires = "immutable(keySet) * share(keySet) in alive", ensures = "immutable(keySet) * share(keySet) in alive")
	public Set<K> keySet() {
		try {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock().lock();
			if (keySet == null)
				keySet = Collections.synchronizedSet(new KeySet(), this);
			return keySet;
		} finally {
			count_entrySet_hash_key_keySet_loadFactor_modCount_next_table_threshold_value_valuesLock.writeLock()
					.unlock();
		}
	}
	private class KeySet extends AbstractSet<K> {
		public ReentrantReadWriteLock countLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<K> iterator() {
			return getIterator(KEYS);
		}
		@Perm(requires = "pure(count) in alive", ensures = "pure(count) in alive")
		public int size() {
			try {
				countLock.readLock().lock();
				return count;
			} finally {
				countLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return containsKey(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			return Hashtable.this.remove(o) != null;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			Hashtable.this.clear();
		}
	}
	public Set<Map.Entry<K, V>> entrySet() {
		if (entrySet == null)
			entrySet = Collections.synchronizedSet(new EntrySet(), this);
		return entrySet;
	}
	private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public ReentrantReadWriteLock count_hash_modCount_next_table_valueLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<Map.Entry<K, V>> iterator() {
			return getIterator(ENTRIES);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean add(Map.Entry<K, V> o) {
			return super.add(o);
		}
		@Perm(requires = "pure(table) * immutable(next) * none(hash) in alive", ensures = "pure(table) * immutable(next) * none(hash) in alive")
		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry entry = (Map.Entry) o;
			Object key = entry.getKey();
			count_hash_modCount_next_table_valueLock.readLock().lock();
			Entry[] tab = table;
			count_hash_modCount_next_table_valueLock.readLock().unlock();
			int hash = key.hashCode();
			int index = (hash & 0x7FFFFFFF) % tab.length;
			for (Entry e = tab[index]; e != null; e = e.next)
				if (e.hash == hash && e.equals(entry))
					return true;
			return false;
		}
		@Perm(requires = "share(table) * share(next) * immutable(next) * immutable(hash) * share(modCount) * share(count) * unique(value) in alive", ensures = "share(table) * share(next) * immutable(next) * immutable(hash) * share(modCount) * share(count) * unique(value) in alive")
		public boolean remove(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
			K key = entry.getKey();
			try {
				count_hash_modCount_next_table_valueLock.writeLock().lock();
				Entry[] tab = table;
				int hash = key.hashCode();
				int index = (hash & 0x7FFFFFFF) % tab.length;
				for (Entry<K, V> e = tab[index], prev = null; e != null; prev = e, e = e.next) {
					if (e.hash == hash && e.equals(entry)) {
						modCount++;
						if (prev != null)
							prev.next = e.next;
						else
							tab[index] = e.next;
						count--;
						e.value = null;
						return true;
					}
				}
			} finally {
				count_hash_modCount_next_table_valueLock.writeLock().unlock();
			}
			return false;
		}
		@Perm(requires = "pure(count) in alive", ensures = "pure(count) in alive")
		public int size() {
			try {
				count_hash_modCount_next_table_valueLock.readLock().lock();
				return count;
			} finally {
				count_hash_modCount_next_table_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			Hashtable.this.clear();
		}
	}
	public Collection<V> values() {
		if (values == null)
			values = Collections.synchronizedCollection(new ValueCollection(), this);
		return values;
	}
	private class ValueCollection extends AbstractCollection<V> {
		public ReentrantReadWriteLock countLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<V> iterator() {
			return getIterator(VALUES);
		}
		@Perm(requires = "pure(count) in alive", ensures = "pure(count) in alive")
		public int size() {
			try {
				countLock.readLock().lock();
				return count;
			} finally {
				countLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return containsValue(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			Hashtable.this.clear();
		}
	}
	public synchronized boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Map))
			return false;
		Map<K, V> t = (Map<K, V>) o;
		if (t.size() != size())
			return false;
		try {
			Iterator<Map.Entry<K, V>> i = entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry<K, V> e = i.next();
				K key = e.getKey();
				V value = e.getValue();
				if (value == null) {
					if (!(t.get(key) == null && t.containsKey(key)))
						return false;
				} else {
					if (!value.equals(t.get(key)))
						return false;
				}
			}
		} catch (ClassCastException unused) {
			return false;
		} catch (NullPointerException unused) {
			return false;
		}
		return true;
	}
	public synchronized int hashCode() {
		int h = 0;
		if (count == 0 || loadFactor < 0)
			return h;
		loadFactor = -loadFactor;
		Entry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			for (Entry e = tab[i]; e != null; e = e.next)
				h += e.key.hashCode() ^ e.value.hashCode();
		loadFactor = -loadFactor;
		return h;
	}
	private synchronized void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(table.length);
		s.writeInt(count);
		for (int index = table.length - 1; index >= 0; index--) {
			Entry entry = table[index];
			while (entry != null) {
				s.writeObject(entry.key);
				s.writeObject(entry.value);
				entry = entry.next;
			}
		}
	}
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int origlength = s.readInt();
		int elements = s.readInt();
		int length = (int) (elements * loadFactor) + (elements / 20) + 3;
		if (length > elements && (length & 1) == 0)
			length--;
		if (origlength > 0 && length > origlength)
			length = origlength;
		Entry[] table = new Entry[length];
		count = 0;
		for (; elements > 0; elements--) {
			K key = (K) s.readObject();
			V value = (V) s.readObject();
			reconstitutionPut(table, key, value);
		}
		this.table = table;
	}
	private void reconstitutionPut(Entry[] tab, K key, V value) throws StreamCorruptedException {
		if (value == null) {
			throw new StreamCorruptedException();
		}
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<K, V> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && e.key.equals(key)) {
				throw new StreamCorruptedException();
			}
		}
		Entry<K, V> e = tab[index];
		tab[index] = new Entry<K, V>(hash, key, value, e);
		count++;
	}
	private static class Entry<K, V> implements Map.Entry<K, V> {
		public ReentrantReadWriteLock hash_key_next_valueLock = new ReentrantReadWriteLock();
		int hash;
		K key;
		V value;
		Entry<K, V> next;
		@Perm(requires = "unique(hash) * unique(key) * unique(value) * unique(next) * none(next) in alive", ensures = "unique(hash) * unique(key) * unique(value) * unique(next) * none(next) in alive")
		protected Entry(int hash, K key, V value, Entry<K, V> next) {
			hash_key_next_valueLock.writeLock().lock();
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
			hash_key_next_valueLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(hash) * pure(key) * pure(value) * immutable(next) in alive", ensures = "immutable(hash) * pure(key) * pure(value) * immutable(next) in alive")
		public Object clone() {
			try {
				hash_key_next_valueLock.readLock().lock();
				return new Entry<K, V>(hash, key, value, (next == null ? null : (Entry<K, V>) next.clone()));
			} finally {
				hash_key_next_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		public K getKey() {
			try {
				hash_key_next_valueLock.readLock().lock();
				return key;
			} finally {
				hash_key_next_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
		public V getValue() {
			try {
				hash_key_next_valueLock.readLock().lock();
				return value;
			} finally {
				hash_key_next_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "share(value) in alive", ensures = "share(value) in alive")
		public V setValue(V value) {
			if (value == null)
				throw new NullPointerException();
			hash_key_next_valueLock.writeLock().lock();
			V oldValue = this.value;
			this.value = value;
			hash_key_next_valueLock.writeLock().unlock();
			return oldValue;
		}
		@Perm(requires = "share(key) * share(value) in alive", ensures = "share(key) * share(value) in alive")
		public boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			try {
				hash_key_next_valueLock.writeLock().lock();
				return (key == null ? e.getKey() == null : key.equals(e.getKey()))
						&& (value == null ? e.getValue() == null : value.equals(e.getValue()));
			} finally {
				hash_key_next_valueLock.writeLock().unlock();
			}
		}
		@Perm(requires = "immutable(hash) * share(value) in alive", ensures = "immutable(hash) * share(value) in alive")
		public int hashCode() {
			try {
				hash_key_next_valueLock.writeLock().lock();
				return hash ^ (value == null ? 0 : value.hashCode());
			} finally {
				hash_key_next_valueLock.writeLock().unlock();
			}
		}
		@Perm(requires = "share(key) * share(value) in alive", ensures = "share(key) * share(value) in alive")
		public String toString() {
			try {
				hash_key_next_valueLock.writeLock().lock();
				return key.toString() + "=" + value.toString();
			} finally {
				hash_key_next_valueLock.writeLock().unlock();
			}
		}
	}
	private static final int KEYS = 0;
	private static final int VALUES = 1;
	private static final int ENTRIES = 2;
	private class Enumerator<T> implements Enumeration<T>, Iterator<T> {
		public ReentrantReadWriteLock count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock = new ReentrantReadWriteLock();
		Entry[] table = Hashtable.this.table;
		int index = table.length;
		Entry<K, V> entry = null;
		Entry<K, V> lastReturned = null;
		int type;
		boolean iterator;
		protected int expectedModCount = modCount;
		@Perm(requires = "unique(type) * unique(iterator) in alive", ensures = "unique(type) * unique(iterator) in alive")
		Enumerator(int type, boolean iterator) {
			count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
					.writeLock().lock();
			this.type = type;
			this.iterator = iterator;
			count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
					.writeLock().unlock();
		}
		@Perm(requires = "share(entry) * immutable(entry) * share(index) * immutable(table) in alive", ensures = "share(entry) * immutable(entry) * share(index) * immutable(table) in alive")
		public boolean hasMoreElements() {
			count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
					.writeLock().lock();
			Entry<K, V> e = entry;
			int i = index;
			Entry[] t = table;
			while (e == null && i > 0) {
				e = t[--i];
			}
			entry = e;
			index = i;
			count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
					.writeLock().unlock();
			return e != null;
		}
		@Perm(requires = "share(entry) * immutable(entry) * share(index) * immutable(table) * pure(lastReturned) * immutable(lastReturned) * pure(next) * immutable(next) * none(type) * pure(key) * pure(value) in alive", ensures = "share(entry) * immutable(entry) * share(index) * immutable(table) * pure(lastReturned) * immutable(lastReturned) * pure(next) * immutable(next) * none(type) * pure(key) * pure(value) in alive")
		public T nextElement() {
			try {
				count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
						.writeLock().lock();
				Entry<K, V> et = entry;
				int i = index;
				Entry[] t = table;
				while (et == null && i > 0) {
					et = t[--i];
				}
				entry = et;
				index = i;
				if (et != null) {
					Entry<K, V> e = lastReturned = entry;
					entry = e.next;
					return type == KEYS ? (T) e.key : (type == VALUES ? (T) e.value : (T) e);
				}
			} finally {
				count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
						.writeLock().unlock();
			}
			throw new NoSuchElementException("Hashtable Enumerator");
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean hasNext() {
			return hasMoreElements();
		}
		@Perm(requires = "pure(modCount) * pure(expectedModCount) in alive", ensures = "pure(modCount) * pure(expectedModCount) in alive")
		public T next() {
			try {
				count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
						.readLock().lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
			} finally {
				count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
						.readLock().unlock();
			}
			return nextElement();
		}
		@Perm(requires = "unique(iterator) * immutable(lastReturned) * share(modCount) * full(expectedModCount) * share(table) * immutable(hash) * pure(next) * immutable(next) * share(count) * unique(lastReturned) in alive", ensures = "unique(iterator) * immutable(lastReturned) * share(modCount) * full(expectedModCount) * share(table) * immutable(hash) * pure(next) * immutable(next) * share(count) * unique(lastReturned) in alive")
		public void remove() {
			try {
				count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
						.writeLock().lock();
				if (!iterator)
					throw new UnsupportedOperationException();
				if (lastReturned == null)
					throw new IllegalStateException("Hashtable Enumerator");
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				synchronized (Hashtable.this) {
					Entry[] tab = Hashtable.this.table;
					int index = (lastReturned.hash & 0x7FFFFFFF) % tab.length;
					for (Entry<K, V> e = tab[index], prev = null; e != null; prev = e, e = e.next) {
						if (e == lastReturned) {
							modCount++;
							expectedModCount++;
							if (prev == null)
								tab[index] = e.next;
							else
								prev.next = e.next;
							count--;
							lastReturned = null;
							return;
						}
					}
					throw new ConcurrentModificationException();
				}
			} finally {
				count_entry_expectedModCount_hash_index_iterator_key_lastReturned_modCount_next_table_type_valueLock
						.writeLock().unlock();
			}
		}
	}
	private static Enumeration emptyEnumerator = new EmptyEnumerator();
	private static Iterator emptyIterator = new EmptyIterator();
	private static class EmptyEnumerator implements Enumeration<Object> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		EmptyEnumerator() {
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean hasMoreElements() {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object nextElement() {
			throw new NoSuchElementException("Hashtable Enumerator");
		}
	}
	private static class EmptyIterator implements Iterator<Object> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		EmptyIterator() {
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean hasNext() {
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Object next() {
			throw new NoSuchElementException("Hashtable Iterator");
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void remove() {
			throw new IllegalStateException("Hashtable Iterator");
		}
	}
}
