package nullappender.entity.withlock;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.helpers.OnlyOnceErrorHandler;
import org.apache.log4j.helpers.LogLog;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public abstract class AppenderSkeleton implements Appender, OptionHandler {
	public ReentrantReadWriteLock closedLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock layoutLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock errorHandlerLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock thresholdLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock headFilter_tailFilterLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock nameLock = new ReentrantReadWriteLock();
	protected Layout layout;
	protected String name;
	protected Priority threshold;
	protected ErrorHandler errorHandler = new OnlyOnceErrorHandler();
	protected Filter headFilter;
	protected Filter tailFilter;
	protected boolean closed = false;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void activateOptions() {
	}
	@Perm(requires = "share(headFilter) * share(tailFilter) in alive", ensures = "share(headFilter) * share(tailFilter) in alive")
	public void addFilter(Filter newFilter) {
		headFilter_tailFilterLock.writeLock().lock();
		if (headFilter == null) {
			headFilter = tailFilter = newFilter;
		} else {
			tailFilter.next = newFilter;
			tailFilter = newFilter;
		}
		headFilter_tailFilterLock.writeLock().unlock();
	}
	abstract protected void append(LoggingEvent event);
	@Perm(requires = "share(headFilter) * unique(tailFilter) in alive", ensures = "share(headFilter) * unique(tailFilter) in alive")
	public void clearFilters() {
		headFilter_tailFilterLock.writeLock().lock();
		headFilter = tailFilter = null;
		headFilter_tailFilterLock.writeLock().unlock();
	}
	@Perm(requires = "immutable(closed) * pure(name) in alive", ensures = "immutable(closed) * pure(name) in alive")
	public void finalize() {
		if (this.closed)
			return;
		nameLock.readLock().lock();
		LogLog.debug("Finalizing appender named [" + name + "].");
		nameLock.readLock().unlock();
		close();
	}
	@Perm(requires = "pure(errorHandler) in alive", ensures = "pure(errorHandler) in alive")
	public ErrorHandler getErrorHandler() {
		try {
			errorHandlerLock.readLock().lock();
			return this.errorHandler;
		} finally {
			errorHandlerLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(headFilter) in alive", ensures = "pure(headFilter) in alive")
	public Filter getFilter() {
		try {
			headFilter_tailFilterLock.readLock().lock();
			return headFilter;
		} finally {
			headFilter_tailFilterLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(headFilter) in alive", ensures = "pure(headFilter) in alive")
	public final Filter getFirstFilter() {
		try {
			headFilter_tailFilterLock.readLock().lock();
			return headFilter;
		} finally {
			headFilter_tailFilterLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(layout) in alive", ensures = "pure(layout) in alive")
	public Layout getLayout() {
		try {
			layoutLock.readLock().lock();
			return layout;
		} finally {
			layoutLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(name) in alive", ensures = "pure(name) in alive")
	public final String getName() {
		try {
			nameLock.readLock().lock();
			return this.name;
		} finally {
			nameLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(threshold) in alive", ensures = "pure(threshold) in alive")
	public Priority getThreshold() {
		try {
			thresholdLock.readLock().lock();
			return threshold;
		} finally {
			thresholdLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(threshold) in alive", ensures = "pure(threshold) in alive")
	public boolean isAsSevereAsThreshold(Priority priority) {
		try {
			thresholdLock.readLock().lock();
			return ((threshold == null) || priority.isGreaterOrEqual(threshold));
		} finally {
			thresholdLock.readLock().unlock();
		}
	}
	@Perm(requires = "immutable(closed) * pure(name) * pure(headFilter) in alive", ensures = "immutable(closed) * pure(name) * pure(headFilter) in alive")
	public synchronized void doAppend(LoggingEvent event) {
		if (closed) {
			nameLock.readLock().lock();
			LogLog.error("Attempted to append to closed appender named [" + name + "].");
			nameLock.readLock().unlock();
			return;
		}
		try {
			thresholdLock.readLock().lock();
			if (!isAsSevereAsThreshold(event.getLevel())) {
				return;
			}
		} finally {
			thresholdLock.readLock().unlock();
		}
		headFilter_tailFilterLock.readLock().lock();
		Filter f = this.headFilter;
		headFilter_tailFilterLock.readLock().unlock();
		FILTER_LOOP : while (f != null) {
			switch (f.decide(event)) {
				case Filter.DENY :
					return;
				case Filter.ACCEPT :
					break FILTER_LOOP;
				case Filter.NEUTRAL :
					f = f.next;
			}
		}
		this.append(event);
	}
	@Perm(requires = "full(errorHandler) in alive", ensures = "full(errorHandler) in alive")
	public synchronized void setErrorHandler(ErrorHandler eh) {
		if (eh == null) {
			LogLog.warn("You have tried to set a null error-handler.");
		} else {
			errorHandlerLock.writeLock().lock();
			this.errorHandler = eh;
			errorHandlerLock.writeLock().unlock();
		}
	}
	@Perm(requires = "full(layout) in alive", ensures = "full(layout) in alive")
	public void setLayout(Layout layout) {
		layoutLock.writeLock().lock();
		this.layout = layout;
		layoutLock.writeLock().unlock();
	}
	@Perm(requires = "full(name) in alive", ensures = "full(name) in alive")
	public void setName(String name) {
		nameLock.writeLock().lock();
		this.name = name;
		nameLock.writeLock().unlock();
	}
	@Perm(requires = "share(threshold) in alive", ensures = "share(threshold) in alive")
	public void setThreshold(Priority threshold) {
		thresholdLock.writeLock().lock();
		this.threshold = threshold;
		thresholdLock.writeLock().unlock();
	}
}
