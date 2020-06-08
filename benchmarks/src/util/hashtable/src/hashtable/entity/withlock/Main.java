package hashtable.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		Hashtable<String, String> hashtable = new Hashtable<>();
		Hashtable<String, String> h1 = new Hashtable<>(1);
		Hashtable<String, String> h2 = new Hashtable<>(5, 0.75f);
		Hashtable<String, String> h3 = new Hashtable<>(hashtable);
		hashtable.size();
		hashtable.isEmpty();
		hashtable.keys();
		hashtable.elements();
		hashtable.contains("s");
		hashtable.containsKey("s");
		hashtable.containsValue("s");
		hashtable.get("s");
		hashtable.put("s", "s");
		hashtable.remove("s");
		hashtable.clear();
		hashtable.putAll(h1);
	}
}
