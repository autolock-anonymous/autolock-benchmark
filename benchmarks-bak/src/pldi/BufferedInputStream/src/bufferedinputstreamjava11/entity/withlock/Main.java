package bufferedinputstreamjava11.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) throws Exception {
		final BufferedInputStream var0 = new BufferedInputStream(null);
		final BufferedInputStream var1 = new BufferedInputStream(var0);
		final long var2 = var1.skip(-2L);
		var1.mark((int) 3);
		final long var3 = var1.skip((long) var2);
		var1.reset();
		final long var4 = var1.skip((long) var2);
		var1.close();
		var1.close();
	}
}
