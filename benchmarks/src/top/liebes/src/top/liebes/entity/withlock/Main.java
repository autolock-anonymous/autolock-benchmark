package top.liebes.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		FileUtil fileUtil = new FileUtil();
		fileUtil.getFile("123");
		fileUtil.handle();
		fileUtil.showFiles();
	}
}
