package treemap.entity.withlock;
import java.util.Comparator;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		TreeMap<String, String> map = new TreeMap<>();
		TreeMap<String, String> map1 = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		TreeMap<String, String> map2 = new TreeMap<>(map);
		map.size();
		map.containsKey("s");
		map.containsValue("s");
		map.get("s");
		map.comparator();
		map.firstKey();
		map.lastKey();
		map.putAll(map1);
		map.put("s", "S");
		map.remove("s");
		map.clear();
		map.firstEntry();
		map.lastEntry();
		map.pollFirstEntry();
		map.pollLastEntry();
		map.lowerEntry("s");
		map.lowerKey("s");
		map.floorEntry("s");
		map.floorKey("s");
		map.ceilingEntry("s");
		map.ceilingKey("s");
		map.higherEntry("s");
		map.higherKey("s");
		map.keySet();
		map.navigableKeySet();
		map.descendingKeySet();
		map.values();
		map.entrySet();
		map.descendingMap();
		map.subMap("s", true, "s", true);
		map.headMap("s", true);
		map.tailMap("s", true);
		map.subMap("s", "s");
		map.headMap("s");
		map.tailMap("s");
	}
}
