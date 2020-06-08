package treemap.entity.withlock;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class TreeMap<K, V> extends AbstractMap<K, V> implements NavigableMap<K, V>, Cloneable, java.io.Serializable {
	public ReentrantReadWriteLock valuesLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock valueLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock = new ReentrantReadWriteLock();
	private final Comparator<? super K> comparator;
	private transient Entry<K, V> root = null;
	private transient int size = 0;
	private transient int modCount = 0;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TreeMap() {
		comparator = null;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TreeMap(Comparator<? super K> comparator) {
		this.comparator = comparator;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TreeMap(Map<? extends K, ? extends V> m) {
		comparator = null;
		putAll(m);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TreeMap(SortedMap<K, ? extends V> m) {
		comparator = m.comparator();
		try {
			buildFromSorted(m.size(), m.entrySet().iterator(), null, null);
		} catch (java.io.IOException cannotHappen) {
		} catch (ClassNotFoundException cannotHappen) {
		}
	}
	@Perm(requires = "pure(size) in alive", ensures = "pure(size) in alive")
	public int size() {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock().lock();
			return size;
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}
	@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
	public boolean containsValue(Object value) {
		try {
			valueLock.readLock().lock();
			for (Entry<K, V> e = getFirstEntry(); e != null; e = successor(e))
				if (valEquals(value, e.value))
					return true;
		} finally {
			valueLock.readLock().unlock();
		}
		return false;
	}
	@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
	public V get(Object key) {
		Entry<K, V> p = getEntry(key);
		try {
			valueLock.readLock().lock();
			return (p == null ? null : p.value);
		} finally {
			valueLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Comparator<? super K> comparator() {
		return comparator;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public K firstKey() {
		return key(getFirstEntry());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public K lastKey() {
		return key(getLastEntry());
	}
	@Perm(requires = "pure(size) * share(modCount) in alive", ensures = "pure(size) * share(modCount) in alive")
	public void putAll(Map<? extends K, ? extends V> map) {
		int mapSize = map.size();
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			if (size == 0 && mapSize != 0 && map instanceof SortedMap) {
				Comparator c = ((SortedMap) map).comparator();
				if (c == comparator || (c != null && c.equals(comparator))) {
					++modCount;
					try {
						buildFromSorted(mapSize, map.entrySet().iterator(), null, null);
					} catch (java.io.IOException cannotHappen) {
					} catch (ClassNotFoundException cannotHappen) {
					}
					return;
				}
			}
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
		super.putAll(map);
	}
	@Perm(requires = "share(root) * pure(root) * pure(key) * share(left) * immutable(left) * pure(right) * pure(right) in alive", ensures = "share(root) * pure(root) * pure(key) * share(left) * immutable(left) * pure(right) * pure(right) in alive")
	final Entry<K, V> getEntry(Object key) {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock().lock();
			if (comparator != null)
				return getEntryUsingComparator(key);
			if (key == null)
				throw new NullPointerException();
			Comparable<? super K> k = (Comparable<? super K>) key;
			Entry<K, V> p = root;
			while (p != null) {
				int cmp = k.compareTo(p.key);
				if (cmp < 0)
					p = p.left;
				else if (cmp > 0)
					p = p.right;
				else
					return p;
			}
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(root) * pure(root) * pure(key) * share(left) * immutable(left) * pure(right) * pure(right) in alive", ensures = "share(root) * pure(root) * pure(key) * share(left) * immutable(left) * pure(right) * pure(right) in alive")
	final Entry<K, V> getEntryUsingComparator(Object key) {
		K k = (K) key;
		Comparator<? super K> cpr = comparator;
		if (cpr != null) {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock().lock();
			Entry<K, V> p = root;
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock().unlock();
			while (p != null) {
				try {
					descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock()
							.lock();
					int cmp = cpr.compare(k, p.key);
					if (cmp < 0)
						p = p.left;
					else if (cmp > 0)
						p = p.right;
					else
						return p;
				} finally {
					descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.readLock()
							.unlock();
				}
			}
		}
		return null;
	}
	@Perm(requires = "share(root) * pure(root) * pure(key) * immutable(left) * share(left) * pure(right) * pure(right) * share(parent) * immutable(parent) in alive", ensures = "share(root) * pure(root) * pure(key) * immutable(left) * share(left) * pure(right) * pure(right) * share(parent) * immutable(parent) in alive")
	final Entry<K, V> getCeilingEntry(K key) {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			Entry<K, V> p = root;
			while (p != null) {
				int cmp = compare(key, p.key);
				if (cmp < 0) {
					if (p.left != null)
						p = p.left;
					else
						return p;
				} else if (cmp > 0) {
					if (p.right != null) {
						p = p.right;
					} else {
						Entry<K, V> parent = p.parent;
						Entry<K, V> ch = p;
						while (parent != null && ch == parent.right) {
							ch = parent;
							parent = parent.parent;
						}
						return parent;
					}
				} else
					return p;
			}
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(root) * pure(root) * pure(key) * pure(right) * share(right) * immutable(left) * pure(left) * share(parent) * immutable(parent) in alive", ensures = "share(root) * pure(root) * pure(key) * pure(right) * share(right) * immutable(left) * pure(left) * share(parent) * immutable(parent) in alive")
	final Entry<K, V> getFloorEntry(K key) {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			Entry<K, V> p = root;
			while (p != null) {
				int cmp = compare(key, p.key);
				if (cmp > 0) {
					if (p.right != null)
						p = p.right;
					else
						return p;
				} else if (cmp < 0) {
					if (p.left != null) {
						p = p.left;
					} else {
						Entry<K, V> parent = p.parent;
						Entry<K, V> ch = p;
						while (parent != null && ch == parent.left) {
							ch = parent;
							parent = parent.parent;
						}
						return parent;
					}
				} else
					return p;
			}
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(root) * pure(root) * pure(key) * immutable(left) * share(left) * pure(right) * pure(right) * share(parent) * immutable(parent) in alive", ensures = "share(root) * pure(root) * pure(key) * immutable(left) * share(left) * pure(right) * pure(right) * share(parent) * immutable(parent) in alive")
	final Entry<K, V> getHigherEntry(K key) {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			Entry<K, V> p = root;
			while (p != null) {
				int cmp = compare(key, p.key);
				if (cmp < 0) {
					if (p.left != null)
						p = p.left;
					else
						return p;
				} else {
					if (p.right != null) {
						p = p.right;
					} else {
						Entry<K, V> parent = p.parent;
						Entry<K, V> ch = p;
						while (parent != null && ch == parent.right) {
							ch = parent;
							parent = parent.parent;
						}
						return parent;
					}
				}
			}
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(root) * pure(root) * pure(key) * pure(right) * share(right) * immutable(left) * pure(left) * share(parent) * immutable(parent) in alive", ensures = "share(root) * pure(root) * pure(key) * pure(right) * share(right) * immutable(left) * pure(left) * share(parent) * immutable(parent) in alive")
	final Entry<K, V> getLowerEntry(K key) {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			Entry<K, V> p = root;
			while (p != null) {
				int cmp = compare(key, p.key);
				if (cmp > 0) {
					if (p.right != null)
						p = p.right;
					else
						return p;
				} else {
					if (p.left != null) {
						p = p.left;
					} else {
						Entry<K, V> parent = p.parent;
						Entry<K, V> ch = p;
						while (parent != null && ch == parent.left) {
							ch = parent;
							parent = parent.parent;
						}
						return parent;
					}
				}
			}
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "unique(root) * full(root) * share(size) * share(modCount) * pure(key) * share(left) * immutable(left) * share(right) * pure(right) in alive", ensures = "unique(root) * full(root) * share(size) * share(modCount) * pure(key) * share(left) * immutable(left) * share(right) * pure(right) in alive")
	public V put(K key, V value) {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			Entry<K, V> t = root;
			if (t == null) {
				root = new Entry<K, V>(key, value, null);
				size = 1;
				modCount++;
				return null;
			}
			int cmp;
			Entry<K, V> parent;
			Comparator<? super K> cpr = comparator;
			if (cpr != null) {
				do {
					parent = t;
					cmp = cpr.compare(key, t.key);
					if (cmp < 0)
						t = t.left;
					else if (cmp > 0)
						t = t.right;
					else
						return t.setValue(value);
				} while (t != null);
			} else {
				if (key == null)
					throw new NullPointerException();
				Comparable<? super K> k = (Comparable<? super K>) key;
				do {
					parent = t;
					cmp = k.compareTo(t.key);
					if (cmp < 0)
						t = t.left;
					else if (cmp > 0)
						t = t.right;
					else
						return t.setValue(value);
				} while (t != null);
			}
			Entry<K, V> e = new Entry<K, V>(key, value, parent);
			if (cmp < 0)
				parent.left = e;
			else
				parent.right = e;
			fixAfterInsertion(e);
			size++;
			modCount++;
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(value) in alive", ensures = "share(value) in alive")
	public V remove(Object key) {
		Entry<K, V> p = getEntry(key);
		if (p == null)
			return null;
		valueLock.writeLock().lock();
		V oldValue = p.value;
		valueLock.writeLock().unlock();
		deleteEntry(p);
		return oldValue;
	}
	@Perm(requires = "share(modCount) * share(size) * unique(root) * pure(root) in alive", ensures = "share(modCount) * share(size) * unique(root) * pure(root) in alive")
	public void clear() {
		descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
		modCount++;
		size = 0;
		root = null;
		descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
	}
	@Perm(requires = "unique(root) * pure(root) * pure(size) * pure(modCount) * unique(entrySet) * unique(navigableKeySet) * pure(navigableKeySet) * unique(descendingMap) * immutable(descendingMap) in alive", ensures = "unique(root) * pure(root) * pure(size) * pure(modCount) * unique(entrySet) * unique(navigableKeySet) * pure(navigableKeySet) * unique(descendingMap) * immutable(descendingMap) in alive")
	public Object clone() {
		TreeMap<K, V> clone = null;
		try {
			clone = (TreeMap<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
		clone.root = null;
		clone.size = 0;
		clone.modCount = 0;
		clone.entrySet = null;
		clone.navigableKeySet = null;
		clone.descendingMap = null;
		try {
			clone.buildFromSorted(size, entrySet().iterator(), null, null);
		} catch (java.io.IOException cannotHappen) {
		} catch (ClassNotFoundException cannotHappen) {
		}
		descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		return clone;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> firstEntry() {
		return exportEntry(getFirstEntry());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> lastEntry() {
		return exportEntry(getLastEntry());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> pollFirstEntry() {
		Entry<K, V> p = getFirstEntry();
		Map.Entry<K, V> result = exportEntry(p);
		if (p != null)
			deleteEntry(p);
		return result;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> pollLastEntry() {
		Entry<K, V> p = getLastEntry();
		Map.Entry<K, V> result = exportEntry(p);
		if (p != null)
			deleteEntry(p);
		return result;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> lowerEntry(K key) {
		return exportEntry(getLowerEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public K lowerKey(K key) {
		return keyOrNull(getLowerEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> floorEntry(K key) {
		return exportEntry(getFloorEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public K floorKey(K key) {
		return keyOrNull(getFloorEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> ceilingEntry(K key) {
		return exportEntry(getCeilingEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public K ceilingKey(K key) {
		return keyOrNull(getCeilingEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Map.Entry<K, V> higherEntry(K key) {
		return exportEntry(getHigherEntry(key));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public K higherKey(K key) {
		return keyOrNull(getHigherEntry(key));
	}
	private transient EntrySet entrySet = null;
	private transient KeySet<K> navigableKeySet = null;
	private transient NavigableMap<K, V> descendingMap = null;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Set<K> keySet() {
		return navigableKeySet();
	}
	@Perm(requires = "unique(navigableKeySet) * full(navigableKeySet) in alive", ensures = "unique(navigableKeySet) * full(navigableKeySet) in alive")
	public NavigableSet<K> navigableKeySet() {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			KeySet<K> nks = navigableKeySet;
			return (nks != null) ? nks : (navigableKeySet = new KeySet(this));
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public NavigableSet<K> descendingKeySet() {
		return descendingMap().navigableKeySet();
	}
	@Perm(requires = "immutable(values) * unique(values) in alive", ensures = "immutable(values) * unique(values) in alive")
	public Collection<V> values() {
		try {
			valuesLock.writeLock().lock();
			Collection<V> vs = values;
			return (vs != null) ? vs : (values = new Values());
		} finally {
			valuesLock.writeLock().unlock();
		}
	}
	@Perm(requires = "unique(entrySet) in alive", ensures = "unique(entrySet) in alive")
	public Set<Map.Entry<K, V>> entrySet() {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			EntrySet es = entrySet;
			return (es != null) ? es : (entrySet = new EntrySet());
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
	}
	@Perm(requires = "immutable(descendingMap) * unique(descendingMap) in alive", ensures = "immutable(descendingMap) * unique(descendingMap) in alive")
	public NavigableMap<K, V> descendingMap() {
		try {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().lock();
			NavigableMap<K, V> km = descendingMap;
			return (km != null) ? km : (descendingMap = new DescendingSubMap(this, true, null, true, true, null, true));
		} finally {
			descendingMap_entrySet_key_left_modCount_navigableKeySet_parent_right_root_sizeLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
		return new AscendingSubMap(this, false, fromKey, fromInclusive, false, toKey, toInclusive);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
		return new AscendingSubMap(this, true, null, true, false, toKey, inclusive);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
		return new AscendingSubMap(this, false, fromKey, inclusive, true, null, true);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public SortedMap<K, V> subMap(K fromKey, K toKey) {
		return subMap(fromKey, true, toKey, false);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public SortedMap<K, V> headMap(K toKey) {
		return headMap(toKey, false);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public SortedMap<K, V> tailMap(K fromKey) {
		return tailMap(fromKey, true);
	}
	class Values extends AbstractCollection<V> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<V> iterator() {
			return new ValueIterator(getFirstEntry());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return TreeMap.this.size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			return TreeMap.this.containsValue(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			for (Entry<K, V> e = getFirstEntry(); e != null; e = successor(e)) {
				if (valEquals(e.getValue(), o)) {
					deleteEntry(e);
					return true;
				}
			}
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			TreeMap.this.clear();
		}
	}
	class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<Map.Entry<K, V>> iterator() {
			return new EntryIterator(getFirstEntry());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
			V value = entry.getValue();
			Entry<K, V> p = getEntry(entry.getKey());
			return p != null && valEquals(p.getValue(), value);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
			V value = entry.getValue();
			Entry<K, V> p = getEntry(entry.getKey());
			if (p != null && valEquals(p.getValue(), value)) {
				deleteEntry(p);
				return true;
			}
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return TreeMap.this.size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			TreeMap.this.clear();
		}
	}
	Iterator<K> keyIterator() {
		return new KeyIterator(getFirstEntry());
	}
	Iterator<K> descendingKeyIterator() {
		return new DescendingKeyIterator(getLastEntry());
	}
	static final class KeySet<E> extends AbstractSet<E> implements NavigableSet<E> {
		private final NavigableMap<E, Object> m;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		KeySet(NavigableMap<E, Object> map) {
			m = map;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<E> iterator() {
			if (m instanceof TreeMap)
				return ((TreeMap<E, Object>) m).keyIterator();
			else
				return (Iterator<E>) (((TreeMap.NavigableSubMap) m).keyIterator());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Iterator<E> descendingIterator() {
			if (m instanceof TreeMap)
				return ((TreeMap<E, Object>) m).descendingKeyIterator();
			else
				return (Iterator<E>) (((TreeMap.NavigableSubMap) m).descendingKeyIterator());
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
		public boolean contains(Object o) {
			return m.containsKey(o);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public void clear() {
			m.clear();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E lower(E e) {
			return m.lowerKey(e);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E floor(E e) {
			return m.floorKey(e);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E ceiling(E e) {
			return m.ceilingKey(e);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E higher(E e) {
			return m.higherKey(e);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E first() {
			return m.firstKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E last() {
			return m.lastKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super E> comparator() {
			return m.comparator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E pollFirst() {
			Map.Entry<E, Object> e = m.pollFirstEntry();
			return e == null ? null : e.getKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public E pollLast() {
			Map.Entry<E, Object> e = m.pollLastEntry();
			return e == null ? null : e.getKey();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean remove(Object o) {
			int oldSize = size();
			m.remove(o);
			return size() != oldSize;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
			return new TreeSet<E>(m.subMap(fromElement, fromInclusive, toElement, toInclusive));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableSet<E> headSet(E toElement, boolean inclusive) {
			return new TreeSet<E>(m.headMap(toElement, inclusive));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
			return new TreeSet<E>(m.tailMap(fromElement, inclusive));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> subSet(E fromElement, E toElement) {
			return subSet(fromElement, true, toElement, false);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> headSet(E toElement) {
			return headSet(toElement, false);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedSet<E> tailSet(E fromElement) {
			return tailSet(fromElement, true);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableSet<E> descendingSet() {
			return new TreeSet(m.descendingMap());
		}
	}
	abstract class PrivateEntryIterator<T> implements Iterator<T> {
		public ReentrantReadWriteLock expectedModCount_lastReturned_left_modCount_next_rightLock = new ReentrantReadWriteLock();
		Entry<K, V> next;
		Entry<K, V> lastReturned;
		int expectedModCount;
		@Perm(requires = "unique(expectedModCount) * none(modCount) * unique(lastReturned) * none(lastReturned) * unique(next) * none(next) in alive", ensures = "unique(expectedModCount) * none(modCount) * unique(lastReturned) * none(lastReturned) * unique(next) * none(next) in alive")
		PrivateEntryIterator(Entry<K, V> first) {
			expectedModCount_lastReturned_left_modCount_next_rightLock.writeLock().lock();
			expectedModCount = modCount;
			lastReturned = null;
			next = first;
			expectedModCount_lastReturned_left_modCount_next_rightLock.writeLock().unlock();
		}
		@Perm(requires = "immutable(next) in alive", ensures = "immutable(next) in alive")
		public final boolean hasNext() {
			return next != null;
		}
		@Perm(requires = "share(next) * immutable(next) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive", ensures = "share(next) * immutable(next) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive")
		final Entry<K, V> nextEntry() {
			Entry<K, V> e = next;
			if (e == null)
				throw new NoSuchElementException();
			try {
				expectedModCount_lastReturned_left_modCount_next_rightLock.readLock().lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
			} finally {
				expectedModCount_lastReturned_left_modCount_next_rightLock.readLock().unlock();
			}
			next = successor(e);
			lastReturned = e;
			return e;
		}
		@Perm(requires = "share(next) * immutable(next) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive", ensures = "share(next) * immutable(next) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive")
		final Entry<K, V> prevEntry() {
			Entry<K, V> e = next;
			if (e == null)
				throw new NoSuchElementException();
			try {
				expectedModCount_lastReturned_left_modCount_next_rightLock.readLock().lock();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
			} finally {
				expectedModCount_lastReturned_left_modCount_next_rightLock.readLock().unlock();
			}
			next = predecessor(e);
			lastReturned = e;
			return e;
		}
		@Perm(requires = "immutable(lastReturned) * pure(modCount) * full(expectedModCount) * immutable(left) * pure(right) * pure(next) * unique(lastReturned) * immutable(next) in alive", ensures = "immutable(lastReturned) * pure(modCount) * full(expectedModCount) * immutable(left) * pure(right) * pure(next) * unique(lastReturned) * immutable(next) in alive")
		public void remove() {
			try {
				expectedModCount_lastReturned_left_modCount_next_rightLock.writeLock().lock();
				if (lastReturned == null)
					throw new IllegalStateException();
				if (modCount != expectedModCount)
					throw new ConcurrentModificationException();
				if (lastReturned.left != null && lastReturned.right != null)
					next = lastReturned;
				deleteEntry(lastReturned);
				expectedModCount = modCount;
				lastReturned = null;
			} finally {
				expectedModCount_lastReturned_left_modCount_next_rightLock.writeLock().unlock();
			}
		}
	}
	final class EntryIterator extends PrivateEntryIterator<Map.Entry<K, V>> {
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		EntryIterator(Entry<K, V> first) {
			super(first);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Map.Entry<K, V> next() {
			return nextEntry();
		}
	}
	final class ValueIterator extends PrivateEntryIterator<V> {
		public ReentrantReadWriteLock valueLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		ValueIterator(Entry<K, V> first) {
			super(first);
		}
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
	final class KeyIterator extends PrivateEntryIterator<K> {
		public ReentrantReadWriteLock keyLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		KeyIterator(Entry<K, V> first) {
			super(first);
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		public K next() {
			try {
				keyLock.readLock().lock();
				return nextEntry().key;
			} finally {
				keyLock.readLock().unlock();
			}
		}
	}
	final class DescendingKeyIterator extends PrivateEntryIterator<K> {
		public ReentrantReadWriteLock keyLock = new ReentrantReadWriteLock();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		DescendingKeyIterator(Entry<K, V> first) {
			super(first);
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		public K next() {
			try {
				keyLock.readLock().lock();
				return prevEntry().key;
			} finally {
				keyLock.readLock().unlock();
			}
		}
	}
	final int compare(Object k1, Object k2) {
		return comparator == null ? ((Comparable<? super K>) k1).compareTo((K) k2) : comparator.compare((K) k1, (K) k2);
	}
	final static boolean valEquals(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}
	static <K, V> Map.Entry<K, V> exportEntry(TreeMap.Entry<K, V> e) {
		return e == null ? null : new AbstractMap.SimpleImmutableEntry<K, V>(e);
	}
	static <K, V> K keyOrNull(TreeMap.Entry<K, V> e) {
		return e == null ? null : e.key;
	}
	static <K> K key(Entry<K, ?> e) {
		if (e == null)
			throw new NoSuchElementException();
		return e.key;
	}
	static abstract class NavigableSubMap<K, V> extends AbstractMap<K, V>
			implements
				NavigableMap<K, V>,
				java.io.Serializable {
		public ReentrantReadWriteLock navigableKeySetViewLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock keyLock = new ReentrantReadWriteLock();
		final TreeMap<K, V> m;
		final K lo, hi;
		final boolean fromStart, toEnd;
		final boolean loInclusive, hiInclusive;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		NavigableSubMap(TreeMap<K, V> m, boolean fromStart, K lo, boolean loInclusive, boolean toEnd, K hi,
				boolean hiInclusive) {
			if (!fromStart && !toEnd) {
				if (m.compare(lo, hi) > 0)
					throw new IllegalArgumentException("fromKey > toKey");
			} else {
				if (!fromStart)
					m.compare(lo, lo);
				if (!toEnd)
					m.compare(hi, hi);
			}
			this.m = m;
			this.fromStart = fromStart;
			this.lo = lo;
			this.loInclusive = loInclusive;
			this.toEnd = toEnd;
			this.hi = hi;
			this.hiInclusive = hiInclusive;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final boolean tooLow(Object key) {
			if (!fromStart) {
				int c = m.compare(key, lo);
				if (c < 0 || (c == 0 && !loInclusive))
					return true;
			}
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final boolean tooHigh(Object key) {
			if (!toEnd) {
				int c = m.compare(key, hi);
				if (c > 0 || (c == 0 && !hiInclusive))
					return true;
			}
			return false;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final boolean inRange(Object key) {
			return !tooLow(key) && !tooHigh(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final boolean inClosedRange(Object key) {
			return (fromStart || m.compare(key, lo) >= 0) && (toEnd || m.compare(hi, key) >= 0);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final boolean inRange(Object key, boolean inclusive) {
			return inclusive ? inRange(key) : inClosedRange(key);
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		final TreeMap.Entry<K, V> absLowest() {
			TreeMap.Entry<K, V> e = (fromStart
					? m.getFirstEntry()
					: (loInclusive ? m.getCeilingEntry(lo) : m.getHigherEntry(lo)));
			try {
				keyLock.readLock().lock();
				return (e == null || tooHigh(e.key)) ? null : e;
			} finally {
				keyLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		final TreeMap.Entry<K, V> absHighest() {
			TreeMap.Entry<K, V> e = (toEnd
					? m.getLastEntry()
					: (hiInclusive ? m.getFloorEntry(hi) : m.getLowerEntry(hi)));
			try {
				keyLock.readLock().lock();
				return (e == null || tooLow(e.key)) ? null : e;
			} finally {
				keyLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		final TreeMap.Entry<K, V> absCeiling(K key) {
			try {
				keyLock.readLock().lock();
				if (tooLow(key))
					return absLowest();
				TreeMap.Entry<K, V> e = m.getCeilingEntry(key);
				return (e == null || tooHigh(e.key)) ? null : e;
			} finally {
				keyLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		final TreeMap.Entry<K, V> absHigher(K key) {
			try {
				keyLock.readLock().lock();
				if (tooLow(key))
					return absLowest();
				TreeMap.Entry<K, V> e = m.getHigherEntry(key);
				return (e == null || tooHigh(e.key)) ? null : e;
			} finally {
				keyLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		final TreeMap.Entry<K, V> absFloor(K key) {
			try {
				keyLock.readLock().lock();
				if (tooHigh(key))
					return absHighest();
				TreeMap.Entry<K, V> e = m.getFloorEntry(key);
				return (e == null || tooLow(e.key)) ? null : e;
			} finally {
				keyLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		final TreeMap.Entry<K, V> absLower(K key) {
			try {
				keyLock.readLock().lock();
				if (tooHigh(key))
					return absHighest();
				TreeMap.Entry<K, V> e = m.getLowerEntry(key);
				return (e == null || tooLow(e.key)) ? null : e;
			} finally {
				keyLock.readLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final TreeMap.Entry<K, V> absHighFence() {
			return (toEnd ? null : (hiInclusive ? m.getHigherEntry(hi) : m.getCeilingEntry(hi)));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		final TreeMap.Entry<K, V> absLowFence() {
			return (fromStart ? null : (loInclusive ? m.getLowerEntry(lo) : m.getFloorEntry(lo)));
		}
		abstract TreeMap.Entry<K, V> subLowest();
		abstract TreeMap.Entry<K, V> subHighest();
		abstract TreeMap.Entry<K, V> subCeiling(K key);
		abstract TreeMap.Entry<K, V> subHigher(K key);
		abstract TreeMap.Entry<K, V> subFloor(K key);
		abstract TreeMap.Entry<K, V> subLower(K key);
		abstract Iterator<K> keyIterator();
		abstract Iterator<K> descendingKeyIterator();
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public boolean isEmpty() {
			return (fromStart && toEnd) ? m.isEmpty() : entrySet().isEmpty();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public int size() {
			return (fromStart && toEnd) ? m.size() : entrySet().size();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final boolean containsKey(Object key) {
			return inRange(key) && m.containsKey(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final V put(K key, V value) {
			if (!inRange(key))
				throw new IllegalArgumentException("key out of range");
			return m.put(key, value);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final V get(Object key) {
			return !inRange(key) ? null : m.get(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final V remove(Object key) {
			return !inRange(key) ? null : m.remove(key);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> ceilingEntry(K key) {
			return exportEntry(subCeiling(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K ceilingKey(K key) {
			return keyOrNull(subCeiling(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> higherEntry(K key) {
			return exportEntry(subHigher(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K higherKey(K key) {
			return keyOrNull(subHigher(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> floorEntry(K key) {
			return exportEntry(subFloor(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K floorKey(K key) {
			return keyOrNull(subFloor(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> lowerEntry(K key) {
			return exportEntry(subLower(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K lowerKey(K key) {
			return keyOrNull(subLower(key));
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K firstKey() {
			return key(subLowest());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final K lastKey() {
			return key(subHighest());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> firstEntry() {
			return exportEntry(subLowest());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> lastEntry() {
			return exportEntry(subHighest());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> pollFirstEntry() {
			TreeMap.Entry<K, V> e = subLowest();
			Map.Entry<K, V> result = exportEntry(e);
			if (e != null)
				m.deleteEntry(e);
			return result;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Map.Entry<K, V> pollLastEntry() {
			TreeMap.Entry<K, V> e = subHighest();
			Map.Entry<K, V> result = exportEntry(e);
			if (e != null)
				m.deleteEntry(e);
			return result;
		}
		transient NavigableMap<K, V> descendingMapView = null;
		transient EntrySetView entrySetView = null;
		transient KeySet<K> navigableKeySetView = null;
		@Perm(requires = "unique(navigableKeySetView) * unique(navigableKeySetView) in alive", ensures = "unique(navigableKeySetView) * unique(navigableKeySetView) in alive")
		public final NavigableSet<K> navigableKeySet() {
			try {
				navigableKeySetViewLock.writeLock().lock();
				KeySet<K> nksv = navigableKeySetView;
				return (nksv != null) ? nksv : (navigableKeySetView = new TreeMap.KeySet(this));
			} finally {
				navigableKeySetViewLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final Set<K> keySet() {
			return navigableKeySet();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableSet<K> descendingKeySet() {
			return descendingMap().navigableKeySet();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final SortedMap<K, V> subMap(K fromKey, K toKey) {
			return subMap(fromKey, true, toKey, false);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final SortedMap<K, V> headMap(K toKey) {
			return headMap(toKey, false);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public final SortedMap<K, V> tailMap(K fromKey) {
			return tailMap(fromKey, true);
		}
		abstract class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
			public ReentrantReadWriteLock modCount_size_sizeModCountLock = new ReentrantReadWriteLock();
			public ReentrantReadWriteLock keyLock = new ReentrantReadWriteLock();
			private transient int size = -1, sizeModCount;
			@Perm(requires = "unique(size) * unique(sizeModCount) * pure(modCount) in alive", ensures = "unique(size) * unique(sizeModCount) * pure(modCount) in alive")
			public int size() {
				try {
					modCount_size_sizeModCountLock.writeLock().lock();
					if (fromStart && toEnd)
						return m.size();
					if (size == -1 || sizeModCount != m.modCount) {
						sizeModCount = m.modCount;
						size = 0;
						Iterator i = iterator();
						while (i.hasNext()) {
							size++;
							i.next();
						}
					}
					return size;
				} finally {
					modCount_size_sizeModCountLock.writeLock().unlock();
				}
			}
			@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
			public boolean isEmpty() {
				TreeMap.Entry<K, V> n = absLowest();
				try {
					keyLock.readLock().lock();
					return n == null || tooHigh(n.key);
				} finally {
					keyLock.readLock().unlock();
				}
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean contains(Object o) {
				if (!(o instanceof Map.Entry))
					return false;
				Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
				K key = entry.getKey();
				if (!inRange(key))
					return false;
				TreeMap.Entry node = m.getEntry(key);
				return node != null && valEquals(node.getValue(), entry.getValue());
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public boolean remove(Object o) {
				if (!(o instanceof Map.Entry))
					return false;
				Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
				K key = entry.getKey();
				if (!inRange(key))
					return false;
				TreeMap.Entry<K, V> node = m.getEntry(key);
				if (node != null && valEquals(node.getValue(), entry.getValue())) {
					m.deleteEntry(node);
					return true;
				}
				return false;
			}
		}
		abstract class SubMapIterator<T> implements Iterator<T> {
			public ReentrantReadWriteLock expectedModCount_key_lastReturned_left_modCount_next_rightLock = new ReentrantReadWriteLock();
			TreeMap.Entry<K, V> lastReturned;
			TreeMap.Entry<K, V> next;
			final K fenceKey;
			int expectedModCount;
			@Perm(requires = "unique(expectedModCount) * none(modCount) * unique(lastReturned) * none(lastReturned) * unique(next) * none(next) * none(key) in alive", ensures = "unique(expectedModCount) * none(modCount) * unique(lastReturned) * none(lastReturned) * unique(next) * none(next) * none(key) in alive")
			SubMapIterator(TreeMap.Entry<K, V> first, TreeMap.Entry<K, V> fence) {
				expectedModCount_key_lastReturned_left_modCount_next_rightLock.writeLock().lock();
				expectedModCount = m.modCount;
				lastReturned = null;
				next = first;
				fenceKey = fence == null ? null : fence.key;
				expectedModCount_key_lastReturned_left_modCount_next_rightLock.writeLock().unlock();
			}
			@Perm(requires = "immutable(next) * pure(key) in alive", ensures = "immutable(next) * pure(key) in alive")
			public final boolean hasNext() {
				try {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.readLock().lock();
					return next != null && next.key != fenceKey;
				} finally {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.readLock().unlock();
				}
			}
			@Perm(requires = "share(next) * immutable(next) * pure(key) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive", ensures = "share(next) * immutable(next) * pure(key) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive")
			final TreeMap.Entry<K, V> nextEntry() {
				TreeMap.Entry<K, V> e = next;
				try {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.readLock().lock();
					if (e == null || e.key == fenceKey)
						throw new NoSuchElementException();
					if (m.modCount != expectedModCount)
						throw new ConcurrentModificationException();
				} finally {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.readLock().unlock();
				}
				next = successor(e);
				lastReturned = e;
				return e;
			}
			@Perm(requires = "share(next) * immutable(next) * pure(key) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive", ensures = "share(next) * immutable(next) * pure(key) * pure(modCount) * pure(expectedModCount) * share(lastReturned) * immutable(lastReturned) in alive")
			final TreeMap.Entry<K, V> prevEntry() {
				TreeMap.Entry<K, V> e = next;
				try {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.readLock().lock();
					if (e == null || e.key == fenceKey)
						throw new NoSuchElementException();
					if (m.modCount != expectedModCount)
						throw new ConcurrentModificationException();
				} finally {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.readLock().unlock();
				}
				next = predecessor(e);
				lastReturned = e;
				return e;
			}
			@Perm(requires = "immutable(lastReturned) * pure(modCount) * share(expectedModCount) * immutable(left) * pure(right) * pure(next) * unique(lastReturned) * immutable(next) in alive", ensures = "immutable(lastReturned) * pure(modCount) * share(expectedModCount) * immutable(left) * pure(right) * pure(next) * unique(lastReturned) * immutable(next) in alive")
			final void removeAscending() {
				try {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.writeLock().lock();
					if (lastReturned == null)
						throw new IllegalStateException();
					if (m.modCount != expectedModCount)
						throw new ConcurrentModificationException();
					if (lastReturned.left != null && lastReturned.right != null)
						next = lastReturned;
					m.deleteEntry(lastReturned);
					lastReturned = null;
					expectedModCount = m.modCount;
				} finally {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.writeLock().unlock();
				}
			}
			@Perm(requires = "immutable(lastReturned) * pure(modCount) * share(expectedModCount) * unique(lastReturned) in alive", ensures = "immutable(lastReturned) * pure(modCount) * share(expectedModCount) * unique(lastReturned) in alive")
			final void removeDescending() {
				try {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.writeLock().lock();
					if (lastReturned == null)
						throw new IllegalStateException();
					if (m.modCount != expectedModCount)
						throw new ConcurrentModificationException();
					m.deleteEntry(lastReturned);
					lastReturned = null;
					expectedModCount = m.modCount;
				} finally {
					expectedModCount_key_lastReturned_left_modCount_next_rightLock.writeLock().unlock();
				}
			}
		}
		final class SubMapEntryIterator extends SubMapIterator<Map.Entry<K, V>> {
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			SubMapEntryIterator(TreeMap.Entry<K, V> first, TreeMap.Entry<K, V> fence) {
				super(first, fence);
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Map.Entry<K, V> next() {
				return nextEntry();
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public void remove() {
				removeAscending();
			}
		}
		final class SubMapKeyIterator extends SubMapIterator<K> {
			public ReentrantReadWriteLock keyLock = new ReentrantReadWriteLock();
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			SubMapKeyIterator(TreeMap.Entry<K, V> first, TreeMap.Entry<K, V> fence) {
				super(first, fence);
			}
			@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
			public K next() {
				try {
					keyLock.readLock().lock();
					return nextEntry().key;
				} finally {
					keyLock.readLock().unlock();
				}
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public void remove() {
				removeAscending();
			}
		}
		final class DescendingSubMapEntryIterator extends SubMapIterator<Map.Entry<K, V>> {
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			DescendingSubMapEntryIterator(TreeMap.Entry<K, V> last, TreeMap.Entry<K, V> fence) {
				super(last, fence);
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Map.Entry<K, V> next() {
				return prevEntry();
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public void remove() {
				removeDescending();
			}
		}
		final class DescendingSubMapKeyIterator extends SubMapIterator<K> {
			public ReentrantReadWriteLock keyLock = new ReentrantReadWriteLock();
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			DescendingSubMapKeyIterator(TreeMap.Entry<K, V> last, TreeMap.Entry<K, V> fence) {
				super(last, fence);
			}
			@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
			public K next() {
				try {
					keyLock.readLock().lock();
					return prevEntry().key;
				} finally {
					keyLock.readLock().unlock();
				}
			}
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public void remove() {
				removeDescending();
			}
		}
	}
	static final class AscendingSubMap<K, V> extends NavigableSubMap<K, V> {
		public ReentrantReadWriteLock descendingMapViewLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 912986545866124060L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		AscendingSubMap(TreeMap<K, V> m, boolean fromStart, K lo, boolean loInclusive, boolean toEnd, K hi,
				boolean hiInclusive) {
			super(m, fromStart, lo, loInclusive, toEnd, hi, hiInclusive);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super K> comparator() {
			return m.comparator();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
			if (!inRange(fromKey, fromInclusive))
				throw new IllegalArgumentException("fromKey out of range");
			if (!inRange(toKey, toInclusive))
				throw new IllegalArgumentException("toKey out of range");
			return new AscendingSubMap(m, false, fromKey, fromInclusive, false, toKey, toInclusive);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
			if (!inRange(toKey, inclusive))
				throw new IllegalArgumentException("toKey out of range");
			return new AscendingSubMap(m, fromStart, lo, loInclusive, false, toKey, inclusive);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
			if (!inRange(fromKey, inclusive))
				throw new IllegalArgumentException("fromKey out of range");
			return new AscendingSubMap(m, false, fromKey, inclusive, toEnd, hi, hiInclusive);
		}
		@Perm(requires = "immutable(descendingMapView) * unique(descendingMapView) in alive", ensures = "immutable(descendingMapView) * unique(descendingMapView) in alive")
		public NavigableMap<K, V> descendingMap() {
			try {
				descendingMapViewLock.writeLock().lock();
				NavigableMap<K, V> mv = descendingMapView;
				return (mv != null)
						? mv
						: (descendingMapView = new DescendingSubMap(m, fromStart, lo, loInclusive, toEnd, hi,
								hiInclusive));
			} finally {
				descendingMapViewLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		Iterator<K> keyIterator() {
			return new SubMapKeyIterator(absLowest(), absHighFence());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		Iterator<K> descendingKeyIterator() {
			return new DescendingSubMapKeyIterator(absHighest(), absLowFence());
		}
		final class AscendingEntrySetView extends EntrySetView {
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Iterator<Map.Entry<K, V>> iterator() {
				return new SubMapEntryIterator(absLowest(), absHighFence());
			}
		}
		public Set<Map.Entry<K, V>> entrySet() {
			EntrySetView es = entrySetView;
			return (es != null) ? es : new AscendingEntrySetView();
		}
		TreeMap.Entry<K, V> subLowest() {
			return absLowest();
		}
		TreeMap.Entry<K, V> subHighest() {
			return absHighest();
		}
		TreeMap.Entry<K, V> subCeiling(K key) {
			return absCeiling(key);
		}
		TreeMap.Entry<K, V> subHigher(K key) {
			return absHigher(key);
		}
		TreeMap.Entry<K, V> subFloor(K key) {
			return absFloor(key);
		}
		TreeMap.Entry<K, V> subLower(K key) {
			return absLower(key);
		}
	}
	static final class DescendingSubMap<K, V> extends NavigableSubMap<K, V> {
		public ReentrantReadWriteLock descendingMapViewLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = 912986545866120460L;
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		DescendingSubMap(TreeMap<K, V> m, boolean fromStart, K lo, boolean loInclusive, boolean toEnd, K hi,
				boolean hiInclusive) {
			super(m, fromStart, lo, loInclusive, toEnd, hi, hiInclusive);
		}
		private final Comparator<? super K> reverseComparator = Collections.reverseOrder(m.comparator);
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super K> comparator() {
			return reverseComparator;
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
			if (!inRange(fromKey, fromInclusive))
				throw new IllegalArgumentException("fromKey out of range");
			if (!inRange(toKey, toInclusive))
				throw new IllegalArgumentException("toKey out of range");
			return new DescendingSubMap(m, false, toKey, toInclusive, false, fromKey, fromInclusive);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
			if (!inRange(toKey, inclusive))
				throw new IllegalArgumentException("toKey out of range");
			return new DescendingSubMap(m, false, toKey, inclusive, toEnd, hi, hiInclusive);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
			if (!inRange(fromKey, inclusive))
				throw new IllegalArgumentException("fromKey out of range");
			return new DescendingSubMap(m, fromStart, lo, loInclusive, false, fromKey, inclusive);
		}
		@Perm(requires = "immutable(descendingMapView) * unique(descendingMapView) in alive", ensures = "immutable(descendingMapView) * unique(descendingMapView) in alive")
		public NavigableMap<K, V> descendingMap() {
			try {
				descendingMapViewLock.writeLock().lock();
				NavigableMap<K, V> mv = descendingMapView;
				return (mv != null)
						? mv
						: (descendingMapView = new AscendingSubMap(m, fromStart, lo, loInclusive, toEnd, hi,
								hiInclusive));
			} finally {
				descendingMapViewLock.writeLock().unlock();
			}
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		Iterator<K> keyIterator() {
			return new DescendingSubMapKeyIterator(absHighest(), absLowFence());
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		Iterator<K> descendingKeyIterator() {
			return new SubMapKeyIterator(absLowest(), absHighFence());
		}
		final class DescendingEntrySetView extends EntrySetView {
			@Perm(requires = "no permission in alive", ensures = "no permission in alive")
			public Iterator<Map.Entry<K, V>> iterator() {
				return new DescendingSubMapEntryIterator(absHighest(), absLowFence());
			}
		}
		public Set<Map.Entry<K, V>> entrySet() {
			EntrySetView es = entrySetView;
			return (es != null) ? es : new DescendingEntrySetView();
		}
		TreeMap.Entry<K, V> subLowest() {
			return absHighest();
		}
		TreeMap.Entry<K, V> subHighest() {
			return absLowest();
		}
		TreeMap.Entry<K, V> subCeiling(K key) {
			return absFloor(key);
		}
		TreeMap.Entry<K, V> subHigher(K key) {
			return absLower(key);
		}
		TreeMap.Entry<K, V> subFloor(K key) {
			return absCeiling(key);
		}
		TreeMap.Entry<K, V> subLower(K key) {
			return absHigher(key);
		}
	}
	private class SubMap extends AbstractMap<K, V> implements SortedMap<K, V>, java.io.Serializable {
		public ReentrantReadWriteLock fromKeyLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock fromStartLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock toEndLock = new ReentrantReadWriteLock();
		public ReentrantReadWriteLock toKeyLock = new ReentrantReadWriteLock();
		private static final long serialVersionUID = -6520786458950516097L;
		private boolean fromStart = false, toEnd = false;
		private K fromKey, toKey;
		@Perm(requires = "none(fromStart) * none(fromKey) * none(toEnd) * none(toKey) in alive", ensures = "none(fromStart) * none(fromKey) * none(toEnd) * none(toKey) in alive")
		private Object readResolve() {
			return new AscendingSubMap(TreeMap.this, fromStart, fromKey, true, toEnd, toKey, false);
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Set<Map.Entry<K, V>> entrySet() {
			throw new InternalError();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K lastKey() {
			throw new InternalError();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public K firstKey() {
			throw new InternalError();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> subMap(K fromKey, K toKey) {
			throw new InternalError();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> headMap(K toKey) {
			throw new InternalError();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public SortedMap<K, V> tailMap(K fromKey) {
			throw new InternalError();
		}
		@Perm(requires = "no permission in alive", ensures = "no permission in alive")
		public Comparator<? super K> comparator() {
			throw new InternalError();
		}
	}
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	static final class Entry<K, V> implements Map.Entry<K, V> {
		public ReentrantReadWriteLock key_parent_valueLock = new ReentrantReadWriteLock();
		K key;
		V value;
		Entry<K, V> left = null;
		Entry<K, V> right = null;
		Entry<K, V> parent;
		boolean color = BLACK;
		@Perm(requires = "unique(key) * unique(value) * unique(parent) * none(parent) in alive", ensures = "unique(key) * unique(value) * unique(parent) * none(parent) in alive")
		Entry(K key, V value, Entry<K, V> parent) {
			key_parent_valueLock.writeLock().lock();
			this.key = key;
			this.value = value;
			this.parent = parent;
			key_parent_valueLock.writeLock().unlock();
		}
		@Perm(requires = "pure(key) in alive", ensures = "pure(key) in alive")
		public K getKey() {
			try {
				key_parent_valueLock.readLock().lock();
				return key;
			} finally {
				key_parent_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "pure(value) in alive", ensures = "pure(value) in alive")
		public V getValue() {
			try {
				key_parent_valueLock.readLock().lock();
				return value;
			} finally {
				key_parent_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "share(value) in alive", ensures = "share(value) in alive")
		public V setValue(V value) {
			key_parent_valueLock.writeLock().lock();
			V oldValue = this.value;
			this.value = value;
			key_parent_valueLock.writeLock().unlock();
			return oldValue;
		}
		@Perm(requires = "pure(key) * pure(value) in alive", ensures = "pure(key) * pure(value) in alive")
		public boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			try {
				key_parent_valueLock.readLock().lock();
				return valEquals(key, e.getKey()) && valEquals(value, e.getValue());
			} finally {
				key_parent_valueLock.readLock().unlock();
			}
		}
		@Perm(requires = "full(key) * share(value) in alive", ensures = "full(key) * share(value) in alive")
		public int hashCode() {
			key_parent_valueLock.writeLock().lock();
			int keyHash = (key == null ? 0 : key.hashCode());
			int valueHash = (value == null ? 0 : value.hashCode());
			key_parent_valueLock.writeLock().unlock();
			return keyHash ^ valueHash;
		}
		@Perm(requires = "pure(key) * pure(value) in alive", ensures = "pure(key) * pure(value) in alive")
		public String toString() {
			try {
				key_parent_valueLock.readLock().lock();
				return key + "=" + value;
			} finally {
				key_parent_valueLock.readLock().unlock();
			}
		}
	}
	final Entry<K, V> getFirstEntry() {
		Entry<K, V> p = root;
		if (p != null)
			while (p.left != null)
				p = p.left;
		return p;
	}
	final Entry<K, V> getLastEntry() {
		Entry<K, V> p = root;
		if (p != null)
			while (p.right != null)
				p = p.right;
		return p;
	}
	static <K, V> TreeMap.Entry<K, V> successor(Entry<K, V> t) {
		if (t == null)
			return null;
		else if (t.right != null) {
			Entry<K, V> p = t.right;
			while (p.left != null)
				p = p.left;
			return p;
		} else {
			Entry<K, V> p = t.parent;
			Entry<K, V> ch = t;
			while (p != null && ch == p.right) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}
	static <K, V> Entry<K, V> predecessor(Entry<K, V> t) {
		if (t == null)
			return null;
		else if (t.left != null) {
			Entry<K, V> p = t.left;
			while (p.right != null)
				p = p.right;
			return p;
		} else {
			Entry<K, V> p = t.parent;
			Entry<K, V> ch = t;
			while (p != null && ch == p.left) {
				ch = p;
				p = p.parent;
			}
			return p;
		}
	}
	private static <K, V> boolean colorOf(Entry<K, V> p) {
		return (p == null ? BLACK : p.color);
	}
	private static <K, V> Entry<K, V> parentOf(Entry<K, V> p) {
		return (p == null ? null : p.parent);
	}
	private static <K, V> void setColor(Entry<K, V> p, boolean c) {
		if (p != null)
			p.color = c;
	}
	private static <K, V> Entry<K, V> leftOf(Entry<K, V> p) {
		return (p == null) ? null : p.left;
	}
	private static <K, V> Entry<K, V> rightOf(Entry<K, V> p) {
		return (p == null) ? null : p.right;
	}
	private void rotateLeft(Entry<K, V> p) {
		if (p != null) {
			Entry<K, V> r = p.right;
			p.right = r.left;
			if (r.left != null)
				r.left.parent = p;
			r.parent = p.parent;
			if (p.parent == null)
				root = r;
			else if (p.parent.left == p)
				p.parent.left = r;
			else
				p.parent.right = r;
			r.left = p;
			p.parent = r;
		}
	}
	private void rotateRight(Entry<K, V> p) {
		if (p != null) {
			Entry<K, V> l = p.left;
			p.left = l.right;
			if (l.right != null)
				l.right.parent = p;
			l.parent = p.parent;
			if (p.parent == null)
				root = l;
			else if (p.parent.right == p)
				p.parent.right = l;
			else
				p.parent.left = l;
			l.right = p;
			p.parent = l;
		}
	}
	private void fixAfterInsertion(Entry<K, V> x) {
		x.color = RED;
		while (x != null && x != root && x.parent.color == RED) {
			if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
				Entry<K, V> y = rightOf(parentOf(parentOf(x)));
				if (colorOf(y) == RED) {
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					x = parentOf(parentOf(x));
				} else {
					if (x == rightOf(parentOf(x))) {
						x = parentOf(x);
						rotateLeft(x);
					}
					setColor(parentOf(x), BLACK);
					setColor(parentOf(parentOf(x)), RED);
					rotateRight(parentOf(parentOf(x)));
				}
			} else {
				Entry<K, V> y = leftOf(parentOf(parentOf(x)));
				if (colorOf(y) == RED) {
					setColor(parentOf(x), BLACK);
					setColor(y, BLACK);
					setColor(parentOf(parentOf(x)), RED);
					x = parentOf(parentOf(x));
				} else {
					if (x == leftOf(parentOf(x))) {
						x = parentOf(x);
						rotateRight(x);
					}
					setColor(parentOf(x), BLACK);
					setColor(parentOf(parentOf(x)), RED);
					rotateLeft(parentOf(parentOf(x)));
				}
			}
		}
		root.color = BLACK;
	}
	private void deleteEntry(Entry<K, V> p) {
		modCount++;
		size--;
		if (p.left != null && p.right != null) {
			Entry<K, V> s = successor(p);
			p.key = s.key;
			p.value = s.value;
			p = s;
		}
		Entry<K, V> replacement = (p.left != null ? p.left : p.right);
		if (replacement != null) {
			replacement.parent = p.parent;
			if (p.parent == null)
				root = replacement;
			else if (p == p.parent.left)
				p.parent.left = replacement;
			else
				p.parent.right = replacement;
			p.left = p.right = p.parent = null;
			if (p.color == BLACK)
				fixAfterDeletion(replacement);
		} else if (p.parent == null) {
			root = null;
		} else {
			if (p.color == BLACK)
				fixAfterDeletion(p);
			if (p.parent != null) {
				if (p == p.parent.left)
					p.parent.left = null;
				else if (p == p.parent.right)
					p.parent.right = null;
				p.parent = null;
			}
		}
	}
	private void fixAfterDeletion(Entry<K, V> x) {
		while (x != root && colorOf(x) == BLACK) {
			if (x == leftOf(parentOf(x))) {
				Entry<K, V> sib = rightOf(parentOf(x));
				if (colorOf(sib) == RED) {
					setColor(sib, BLACK);
					setColor(parentOf(x), RED);
					rotateLeft(parentOf(x));
					sib = rightOf(parentOf(x));
				}
				if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(rightOf(sib)) == BLACK) {
						setColor(leftOf(sib), BLACK);
						setColor(sib, RED);
						rotateRight(sib);
						sib = rightOf(parentOf(x));
					}
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(rightOf(sib), BLACK);
					rotateLeft(parentOf(x));
					x = root;
				}
			} else {
				Entry<K, V> sib = leftOf(parentOf(x));
				if (colorOf(sib) == RED) {
					setColor(sib, BLACK);
					setColor(parentOf(x), RED);
					rotateRight(parentOf(x));
					sib = leftOf(parentOf(x));
				}
				if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
					setColor(sib, RED);
					x = parentOf(x);
				} else {
					if (colorOf(leftOf(sib)) == BLACK) {
						setColor(rightOf(sib), BLACK);
						setColor(sib, RED);
						rotateLeft(sib);
						sib = leftOf(parentOf(x));
					}
					setColor(sib, colorOf(parentOf(x)));
					setColor(parentOf(x), BLACK);
					setColor(leftOf(sib), BLACK);
					rotateRight(parentOf(x));
					x = root;
				}
			}
		}
		setColor(x, BLACK);
	}
	private static final long serialVersionUID = 919286545866124006L;
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		s.defaultWriteObject();
		s.writeInt(size);
		for (Iterator<Map.Entry<K, V>> i = entrySet().iterator(); i.hasNext();) {
			Map.Entry<K, V> e = i.next();
			s.writeObject(e.getKey());
			s.writeObject(e.getValue());
		}
	}
	private void readObject(final java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		int size = s.readInt();
		buildFromSorted(size, null, s, null);
	}
	void readTreeSet(int size, java.io.ObjectInputStream s, V defaultVal)
			throws java.io.IOException, ClassNotFoundException {
		buildFromSorted(size, null, s, defaultVal);
	}
	void addAllForTreeSet(SortedSet<? extends K> set, V defaultVal) {
		try {
			buildFromSorted(set.size(), set.iterator(), null, defaultVal);
		} catch (java.io.IOException cannotHappen) {
		} catch (ClassNotFoundException cannotHappen) {
		}
	}
	private void buildFromSorted(int size, Iterator it, java.io.ObjectInputStream str, V defaultVal)
			throws java.io.IOException, ClassNotFoundException {
		this.size = size;
		root = buildFromSorted(0, 0, size - 1, computeRedLevel(size), it, str, defaultVal);
	}
	private final Entry<K, V> buildFromSorted(int level, int lo, int hi, int redLevel, Iterator it,
			java.io.ObjectInputStream str, V defaultVal) throws java.io.IOException, ClassNotFoundException {
		if (hi < lo)
			return null;
		int mid = (lo + hi) / 2;
		Entry<K, V> left = null;
		if (lo < mid)
			left = buildFromSorted(level + 1, lo, mid - 1, redLevel, it, str, defaultVal);
		K key;
		V value;
		if (it != null) {
			if (defaultVal == null) {
				Map.Entry<K, V> entry = (Map.Entry<K, V>) it.next();
				key = entry.getKey();
				value = entry.getValue();
			} else {
				key = (K) it.next();
				value = defaultVal;
			}
		} else {
			key = (K) str.readObject();
			value = (defaultVal != null ? defaultVal : (V) str.readObject());
		}
		Entry<K, V> middle = new Entry<K, V>(key, value, null);
		if (level == redLevel)
			middle.color = RED;
		if (left != null) {
			middle.left = left;
			left.parent = middle;
		}
		if (mid < hi) {
			Entry<K, V> right = buildFromSorted(level + 1, mid + 1, hi, redLevel, it, str, defaultVal);
			middle.right = right;
			right.parent = middle;
		}
		return middle;
	}
	private static int computeRedLevel(int sz) {
		int level = 0;
		for (int m = sz - 1; m >= 0; m = m / 2 - 1)
			level++;
		return level;
	}
}
