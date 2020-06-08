package bufferedinputstreamjava11.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class IOException extends Exception {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public IOException() {
		super();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public IOException(String s) {
		super(s);
	}
}
