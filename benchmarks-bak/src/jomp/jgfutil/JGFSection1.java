package jomp.jgfutil.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface JGFSection1 {
	public final int INITSIZE = 10000;
	public final int MAXSIZE = 1000000000;
	public final double TARGETTIME = 1.0;
	public void JGFrun();
}
