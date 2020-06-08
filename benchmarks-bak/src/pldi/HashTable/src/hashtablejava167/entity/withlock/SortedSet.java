package hashtablejava167.entity.withlock;
import java.util.Set;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface SortedSet<E> extends Set<E> {
	Comparator<? super E> comparator();
	SortedSet<E> subSet(E fromElement, E toElement);
	SortedSet<E> headSet(E toElement);
	SortedSet<E> tailSet(E fromElement);
	E first();
	E last();
}
