package linkedhashmap.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		LinkedHashMap<String, String> map1 = new LinkedHashMap<>(5);
		LinkedHashMap<String, String> map2 = new LinkedHashMap<>(5, 0.75f);
		LinkedHashMap<String, String> map3 = new LinkedHashMap<>(map);
		map3.containsValue("s");
		map.get("s");
		map.clear();
	}
}
