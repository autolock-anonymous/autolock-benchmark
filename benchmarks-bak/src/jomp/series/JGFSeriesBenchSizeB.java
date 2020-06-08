package jomp.series.withlock;
import jomp.jgfutil.JGFInstrumentor;
import jomp.series.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFSeriesBenchSizeB {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String argv[]) {
		JGFSeriesBench se = new JGFSeriesBench();
		se.JGFrun(1);
	}
}
