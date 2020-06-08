package xyseries.entity.withlock;
import org.jfree.data.SeriesChangeEvent;
import org.jfree.data.SeriesChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Series implements Cloneable, Serializable {
	public ReentrantReadWriteLock description_listeners_name_propertyChangeSupportLock = new ReentrantReadWriteLock();
	private String name;
	private String description;
	private List listeners;
	private PropertyChangeSupport propertyChangeSupport;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected Series(String name) {
		this(name, null);
	}
	@Perm(requires = "unique(name) * unique(description) * unique(listeners) * unique(propertyChangeSupport) in alive", ensures = "unique(name) * unique(description) * unique(listeners) * unique(propertyChangeSupport) in alive")
	protected Series(String name, String description) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		this.name = name;
		this.description = description;
		this.listeners = new java.util.ArrayList();
		propertyChangeSupport = new PropertyChangeSupport(this);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "pure(name) in alive", ensures = "pure(name) in alive")
	public String getName() {
		try {
			description_listeners_name_propertyChangeSupportLock.readLock().lock();
			return this.name;
		} finally {
			description_listeners_name_propertyChangeSupportLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(name) * share(propertyChangeSupport) in alive", ensures = "full(name) * share(propertyChangeSupport) in alive")
	public void setName(String name) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		String old = this.name;
		this.name = name;
		propertyChangeSupport.firePropertyChange("Name", old, name);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "pure(description) in alive", ensures = "pure(description) in alive")
	public String getDescription() {
		try {
			description_listeners_name_propertyChangeSupportLock.readLock().lock();
			return this.description;
		} finally {
			description_listeners_name_propertyChangeSupportLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(description) * share(propertyChangeSupport) in alive", ensures = "full(description) * share(propertyChangeSupport) in alive")
	public void setDescription(String description) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		String old = this.description;
		this.description = description;
		propertyChangeSupport.firePropertyChange("Description", old, description);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "unique(listeners) * unique(propertyChangeSupport) in alive", ensures = "unique(listeners) * unique(propertyChangeSupport) in alive")
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Series.clone(): unexpected exception.");
		}
		Series clone = (Series) obj;
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		clone.listeners = new java.util.ArrayList();
		clone.propertyChangeSupport = new PropertyChangeSupport(clone);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
		return clone;
	}
	@Perm(requires = "share(listeners) in alive", ensures = "share(listeners) in alive")
	public void addChangeListener(SeriesChangeListener listener) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		this.listeners.add(listener);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "share(listeners) in alive", ensures = "share(listeners) in alive")
	public void removeChangeListener(SeriesChangeListener listener) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		this.listeners.remove(listener);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void fireSeriesChanged() {
		notifyListeners(new SeriesChangeEvent(this));
	}
	@Perm(requires = "share(listeners) in alive", ensures = "share(listeners) in alive")
	protected void notifyListeners(SeriesChangeEvent event) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		Iterator iterator = listeners.iterator();
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
		while (iterator.hasNext()) {
			SeriesChangeListener listener = (SeriesChangeListener) iterator.next();
			listener.seriesChanged(event);
		}
	}
	@Perm(requires = "share(propertyChangeSupport) in alive", ensures = "share(propertyChangeSupport) in alive")
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		this.propertyChangeSupport.addPropertyChangeListener(listener);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "share(propertyChangeSupport) in alive", ensures = "share(propertyChangeSupport) in alive")
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		this.propertyChangeSupport.removePropertyChangeListener(listener);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
	@Perm(requires = "share(propertyChangeSupport) in alive", ensures = "share(propertyChangeSupport) in alive")
	protected void firePropertyChange(String property, Object oldValue, Object newValue) {
		description_listeners_name_propertyChangeSupportLock.writeLock().lock();
		this.propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
		description_listeners_name_propertyChangeSupportLock.writeLock().unlock();
	}
}
