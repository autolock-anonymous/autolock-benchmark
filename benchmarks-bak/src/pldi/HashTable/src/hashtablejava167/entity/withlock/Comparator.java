package hashtablejava167.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface Comparator<T> {
	int compare(T o1, T o2);
	boolean equals(Object obj);
}
