package xyseries.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		final XYSeries var0 = new XYSeries(null, true);
		final XYSeries var1 = var0.createCopy(3, 3);
		var1.clear();
		final java.lang.String var2 = var1.getDescription();
		var1.addPropertyChangeListener(null);
		var1.add(-3.0d, -100.0d);
		final boolean var3 = var1.equals(var0);
		var1.clear();
		var1.add(-3.0d, null);
		var1.add(-1.0d, null);
		var1.clear();
	}
}
