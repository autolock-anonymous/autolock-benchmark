package linkedhashmap.entity.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class LinkedHashMap<K, V> extends HashMap<K, V> implements Map<K, V> {
	public ReentrantReadWriteLock after_before_header_next_valueLock = new ReentrantReadWriteLock();
	private static final long serialVersionUID = 3801124242820219131L;
	private transient Entry<K, V> header;
	private final boolean accessOrder;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public LinkedHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		accessOrder = false;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public LinkedHashMap(int initialCapacity) {
		super(initialCapacity);
		accessOrder = false;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public LinkedHashMap() {
		super();
		accessOrder = false;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public LinkedHashMap(Map<? extends K, ? extends V> m) {
		super(m);
		accessOrder = false;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
		super(initialCapacity, loadFactor);
		this.accessOrder = accessOrder;
	}
	@Perm(requires = "unique(header) * immutable(header) * pure(after) * immutable(before) * immutable(after) in alive", ensures = "unique(header) * immutable(header) * pure(after) * immutable(before) * immutable(after) in alive")
	void init() {
		header = new Entry<K, V>(-1, null, null, null);
		header.before = header.after = header;
	}
	@Perm(requires = "immutable(header) * immutable(after) * share(after) * share(next) * immutable(next) in alive", ensures = "immutable(header) * immutable(after) * share(after) * share(next) * immutable(next) in alive")
	void transfer(HashMap.Entry[] newTable) {
		int newCapacity = newTable.length;
		after_before_header_next_valueLock.writeLock().lock();
		for (Entry<K, V> e = header.after; e != header; e = e.after) {
			int index = indexFor(e.hash, newCapacity);
			e.next = newTable[index];
			newTable[index] = e;
		}
		after_before_header_next_valueLock.writeLock().unlock();
	}
	@Perm(requires = "immutable(header) * immutable(after) * unique(after) * immutable(value) in alive", ensures = "immutable(header) * immutable(after) * unique(after) * immutable(value) in alive")
	public boolean containsValue(Object value) {
		try {
			after_before_header_next_valueLock.writeLock().lock();
			if (value == null) {
				for (Entry e = header.after; e != header; e = e.after)
					if (e.value == null)
						return true;
			} else {
				for (Entry e = header.after; e != header; e = e.after)
					if (value.equals(e.value))
						return true;
			}
		} finally {
			after_before_header_next_valueLock.writeLock().unlock();
		}
		return false;
	}
	@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
	public V get(Object key) {
		Entry<K, V> e = (Entry<K, V>) getEntry(key);
		if (e == null)
			return null;
		e.recordAccess(this);
		try {
			after_before_header_next_valueLock.readLock().lock();
			return e.value;
		} finally {
			after_before_header_next_valueLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(after) * share(header) * immutable(header) * immutable(before) * immutable(after) in alive", ensures = "pure(after) * share(header) * immutable(header) * immutable(before) * immutable(after) in alive")
	public void clear() {
		super.clear();
		header.before = header.after = header;
	}
	private static class Entry<K, V> extends HashMap.Entry<K, V> {
		public ReentrantReadWriteLock header_modCountLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock after_beforeLock = new ReentrantReadWriteLock();
		Entry<K, V> before, after;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		Entry(int hash, K key, V value, HashMap.Entry<K, V> next) {
			super(hash, key, value, next);
		}
		@Perm(requires = "share(after) * immutable(before) * immutable(after) * pure(before) in alive", ensures = "share(after) * immutable(before) * immutable(after) * pure(before) in alive")
		private void remove() {
			after_beforeLock.readLock().lock();
			before.after = after;
			after.before = before;
			after_beforeLock.readLock().unlock();
		}
		@Perm(requires = "share(after) * immutable(after) * full(before) * immutable(before) in alive", ensures = "share(after) * immutable(after) * full(before) * immutable(before) in alive")
		private void addBefore(Entry<K, V> existingEntry) {
			after = existingEntry;
			before = existingEntry.before;
			before.after = this;
			after.before = this;
		}
		@Perm(requires = "share(modCount) * immutable(header) in alive", ensures = "share(modCount) * immutable(header) in alive")
		void recordAccess(HashMap<K, V> m) {
			LinkedHashMap<K, V> lm = (LinkedHashMap<K, V>) m;
			if (lm.accessOrder) {
				header_modCountLock.writeLock().lock();
				lm.modCount++;
				remove();
				addBefore(lm.header);
				header_modCountLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		void recordRemoval(HashMap<K, V> m) {
			remove();
		}
	}
	private abstract class LinkedHashIterator<T> implements Iterator<T> {
		public ReentrantReadWriteLock nextEntryLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock headerLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock afterLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock expectedModCount_lastReturned_modCountLock = new ReentrantReadWriteLock();
		Entry<K, V> nextEntry = header.after;
		Entry<K, V> lastReturned = null;
		int expectedModCount = modCount;
		@Perm(requires = "immutable(nextEntry) * immutable(header) in alive", ensures = "immutable(nextEntry) * immutable(header) in alive")
		public boolean hasNext() {
			return nextEntry != header;
		}
		@Perm(requires = "immutable(lastReturned) * pure(modCount) * full(expectedModCount) * unique(lastReturned) in alive", ensures = "immutable(lastReturned) * pure(modCount) * full(expectedModCount) * unique(lastReturned) in alive")
		public void remove() {
			try {
				expectedModCount_lastReturned_modCountLock.writeLock().lock();
				if (lastReturned == null)
					throw new IllegalStateException();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				LinkedHashMap.this.remove(lastReturned.key);
				lastReturned = null;
				expectedModCount = modCount;
			} finally {
				expectedModCount_lastReturned_modCountLock.writeLock().unlock();
			}
		}
		@Perm(requires = "pure(modCount) * pure(expectedModCount) * immutable(nextEntry) * immutable(header) * pure(lastReturned) * none(nextEntry) * immutable(lastReturned) * pure(after) * immutable(after) in alive", ensures = "pure(modCount) * pure(expectedModCount) * immutable(nextEntry) * immutable(header) * pure(lastReturned) * none(nextEntry) * immutable(lastReturned) * pure(after) * immutable(after) in alive")
		Entry<K, V> nextEntry() {
			try {
				expectedModCount_lastReturned_modCountLock.readLock().lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
			} finally {
				expectedModCount_lastReturned_modCountLock.readLock().unlock();
			}
			if (nextEntry == header)
				throw new NoSuchElementException();
			Entry<K, V> e = lastReturned = nextEntry;
			nextEntry = e.after;
			return e;
		}
	}
	private class KeyIterator extends LinkedHashIterator<K> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K next() {
			return nextEntry().getKey();
		}
	}
	private class ValueIterator extends LinkedHashIterator<V> {
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
	private class EntryIterator extends LinkedHashIterator<Map.Entry<K, V>> {
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
	void addEntry(int hash, K key, V value, int bucketIndex) {
		createEntry(hash, key, value, bucketIndex);
		Entry<K, V> eldest = header.after;
		if (removeEldestEntry(eldest)) {
			removeEntryForKey(eldest.key);
		} else {
			if (size >= threshold)
				resize(2 * table.length);
		}
	}
	void createEntry(int hash, K key, V value, int bucketIndex) {
		HashMap.Entry<K, V> old = table[bucketIndex];
		Entry<K, V> e = new Entry<K, V>(hash, key, value, old);
		table[bucketIndex] = e;
		e.addBefore(header);
		size++;
	}
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return false;
	}
}
