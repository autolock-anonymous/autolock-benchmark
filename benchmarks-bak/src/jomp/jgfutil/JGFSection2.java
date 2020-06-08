package jomp.jgfutil.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public interface JGFSection2 {
	public void JGFsetsize(int size);
	public void JGFinitialise();
	public void JGFkernel();
	public void JGFvalidate();
	public void JGFtidyup();
	public void JGFrun(int size);
}
