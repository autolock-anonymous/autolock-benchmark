package appenderattacableimpl.entity.withlock;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Appender;
import java.util.Vector;
import java.util.Enumeration;
import org.apache.log4j.helpers.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class AppenderAttachableImpl implements AppenderAttachable {
	public ReentrantReadWriteLock appenderListLock = new ReentrantReadWriteLock();
	protected Vector appenderList;
	@Perm(requires = "unique(appenderList) in alive", ensures = "unique(appenderList) in alive")
	public void addAppender(Appender newAppender) {
		if (newAppender == null)
			return;
		appenderListLock.writeLock().lock();
		if (appenderList == null) {
			appenderList = new Vector(1);
		}
		if (!appenderList.contains(newAppender))
			appenderList.addElement(newAppender);
		appenderListLock.writeLock().unlock();
	}
	@Perm(requires = "share(appenderList) in alive", ensures = "share(appenderList) in alive")
	public int appendLoopOnAppenders(LoggingEvent event) {
		int size = 0;
		Appender appender;
		appenderListLock.writeLock().lock();
		if (appenderList != null) {
			size = appenderList.size();
			for (int i = 0; i < size; i++) {
				appender = (Appender) appenderList.elementAt(i);
				appender.doAppend(event);
			}
		}
		appenderListLock.writeLock().unlock();
		return size;
	}
	@Perm(requires = "unique(appenderList) in alive", ensures = "unique(appenderList) in alive")
	public Enumeration getAllAppenders() {
		try {
			appenderListLock.writeLock().lock();
			if (appenderList == null)
				return null;
			else
				return appenderList.elements();
		} finally {
			appenderListLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(appenderList) in alive", ensures = "share(appenderList) in alive")
	public Appender getAppender(String name) {
		try {
			appenderListLock.writeLock().lock();
			if (appenderList == null || name == null)
				return null;
			int size = appenderList.size();
			Appender appender;
			for (int i = 0; i < size; i++) {
				appender = (Appender) appenderList.elementAt(i);
				if (name.equals(appender.getName()))
					return appender;
			}
		} finally {
			appenderListLock.writeLock().unlock();
		}
		return null;
	}
	@Perm(requires = "share(appenderList) in alive", ensures = "share(appenderList) in alive")
	public boolean isAttached(Appender appender) {
		try {
			appenderListLock.writeLock().lock();
			if (appenderList == null || appender == null)
				return false;
			int size = appenderList.size();
			Appender a;
			for (int i = 0; i < size; i++) {
				a = (Appender) appenderList.elementAt(i);
				if (a == appender)
					return true;
			}
		} finally {
			appenderListLock.writeLock().unlock();
		}
		return false;
	}
	@Perm(requires = "unique(appenderList) in alive", ensures = "unique(appenderList) in alive")
	public void removeAllAppenders() {
		appenderListLock.writeLock().lock();
		if (appenderList != null) {
			int len = appenderList.size();
			for (int i = 0; i < len; i++) {
				Appender a = (Appender) appenderList.elementAt(i);
				a.close();
			}
			appenderList.removeAllElements();
			appenderList = null;
		}
		appenderListLock.writeLock().unlock();
	}
	@Perm(requires = "share(appenderList) in alive", ensures = "share(appenderList) in alive")
	public void removeAppender(Appender appender) {
		try {
			appenderListLock.writeLock().lock();
			if (appender == null || appenderList == null)
				return;
			appenderList.removeElement(appender);
		} finally {
			appenderListLock.writeLock().unlock();
		}
	}
	@Perm(requires = "share(appenderList) in alive", ensures = "share(appenderList) in alive")
	public void removeAppender(String name) {
		try {
			appenderListLock.writeLock().lock();
			if (name == null || appenderList == null)
				return;
			int size = appenderList.size();
			for (int i = 0; i < size; i++) {
				if (name.equals(((Appender) appenderList.elementAt(i)).getName())) {
					appenderList.removeElementAt(i);
					break;
				}
			}
		} finally {
			appenderListLock.writeLock().unlock();
		}
	}
}
