package synchronizedmapjava16.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Factory {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static Collections.SynchronizedMap createSyncMap() {
		return (Collections.SynchronizedMap) Collections.synchronizedMap(new Hashtable());
	}
}
