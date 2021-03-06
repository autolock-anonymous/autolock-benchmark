package hashmap.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		HashMap<String, String> hashMap = new HashMap<>();
		HashMap<String, String> h1 = new HashMap<>(1, 0.75f);
		HashMap<String, String> h2 = new HashMap<>(1);
		HashMap<String, String> h3 = new HashMap<>(hashMap);
		hashMap.put("s", "s");
		hashMap.size();
		hashMap.isEmpty();
		hashMap.get("s");
		hashMap.containsKey("s");
		h1.putAll(hashMap);
		hashMap.remove("s");
		h1.clear();
		hashMap.containsKey("s");
		hashMap.containsValue("s");
		hashMap.values();
		hashMap.entrySet();
	}
}
