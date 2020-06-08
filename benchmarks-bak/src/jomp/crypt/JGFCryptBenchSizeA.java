package jomp.crypt.withlock;
import jomp.jgfutil.JGFInstrumentor;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFCryptBenchSizeA {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String argv[]) {
		long start = System.nanoTime();
		JGFCryptBench cb = new JGFCryptBench();
		cb.JGFrun(0);
		long end = System.nanoTime();
		long elapsedTime = end - start;
		double ms = (double) elapsedTime / 1000000.0;
		System.out.println(" Milli Seconds Time = " + ms);
	}
}
