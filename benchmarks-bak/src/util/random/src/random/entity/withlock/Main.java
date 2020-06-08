package random.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		Random random = new Random();
		Random random1 = new Random(System.currentTimeMillis());
		random.setSeed(System.currentTimeMillis());
		random.nextInt();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		random.nextInt(1);
		random.nextLong();
		random.nextBoolean();
		random.nextFloat();
		random.nextDouble();
		random.nextGaussian();
	}
}
