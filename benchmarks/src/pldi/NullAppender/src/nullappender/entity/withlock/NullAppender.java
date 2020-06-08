package nullappender.entity.withlock;
import org.apache.log4j.spi.LoggingEvent;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class NullAppender extends AppenderSkeleton {
	public static ReentrantReadWriteLock instanceLock = new ReentrantReadWriteLock();
	private static NullAppender instance = new NullAppender();
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public NullAppender() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void activateOptions() {
	}
	@Perm(requires = "immutable(instance) in alive", ensures = "immutable(instance) in alive")
	public NullAppender getInstance() {
		return instance;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void close() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void doAppend(LoggingEvent event) {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected void append(LoggingEvent event) {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean requiresLayout() {
		return false;
	}
}
