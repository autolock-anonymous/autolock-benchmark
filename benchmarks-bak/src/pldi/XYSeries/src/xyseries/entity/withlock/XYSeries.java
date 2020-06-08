package xyseries.entity.withlock;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.jfree.util.ObjectUtils;
import org.jfree.data.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class XYSeries extends Series implements Serializable {
	public ReentrantReadWriteLock allowDuplicateXValues_data_maximumItemCountLock = new ReentrantReadWriteLock();
	private List data;
	private int maximumItemCount = Integer.MAX_VALUE;
	private boolean allowDuplicateXValues;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public XYSeries(String name) {
		this(name, true);
	}
	@Perm(requires = "unique(allowDuplicateXValues) * unique(data) in alive", ensures = "unique(allowDuplicateXValues) * unique(data) in alive")
	public XYSeries(String name, boolean allowDuplicateXValues) {
		super(name);
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
		this.allowDuplicateXValues = allowDuplicateXValues;
		this.data = new java.util.ArrayList();
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
	}
	@Perm(requires = "unique(data) in alive", ensures = "unique(data) in alive")
	public int getItemCount() {
		try {
			allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
			return data.size();
		} finally {
			allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(maximumItemCount) in alive", ensures = "pure(maximumItemCount) in alive")
	public int getMaximumItemCount() {
		try {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().lock();
			return this.maximumItemCount;
		} finally {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(maximumItemCount) in alive", ensures = "full(maximumItemCount) in alive")
	public void setMaximumItemCount(int maximum) {
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
		this.maximumItemCount = maximum;
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
	}
	@Perm(requires = "unique(data) * pure(maximumItemCount) * immutable(allowDuplicateXValues) in alive", ensures = "unique(data) * pure(maximumItemCount) * immutable(allowDuplicateXValues) in alive")
	public void add(XYDataPair pair) throws SeriesException {
		if (pair == null) {
			throw new IllegalArgumentException("XYSeries.add(...): null item not allowed.");
		}
		try {
			allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
			int index = Collections.binarySearch(data, pair);
			if (index < 0) {
				data.add(-index - 1, pair);
				if (getItemCount() > this.maximumItemCount) {
					this.data.remove(0);
				}
				fireSeriesChanged();
			} else {
				if (allowDuplicateXValues == true) {
					data.add(index, pair);
					if (getItemCount() > this.maximumItemCount) {
						this.data.remove(0);
					}
					fireSeriesChanged();
				} else {
					throw new SeriesException("XYSeries.add(...): x-value already exists.");
				}
			}
		} finally {
			allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(double x, double y) throws SeriesException {
		add(new Double(x), new Double(y));
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(double x, Number y) throws SeriesException {
		add(new Double(x), y);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(Number x, Number y) throws SeriesException {
		XYDataPair pair = new XYDataPair(x, y);
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
		add(pair);
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
	}
	@Perm(requires = "share(data) in alive", ensures = "share(data) in alive")
	public void delete(int start, int end) {
		for (int i = start; i <= end; i++) {
			allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
			data.remove(start);
			allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
		}
		fireSeriesChanged();
	}
	@Perm(requires = "share(data) in alive", ensures = "share(data) in alive")
	public void clear() {
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
		if (data.size() > 0) {
			data.clear();
			fireSeriesChanged();
		}
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
	}
	@Perm(requires = "pure(data) in alive", ensures = "pure(data) in alive")
	public XYDataPair getDataPair(int index) {
		try {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().lock();
			return (XYDataPair) data.get(index);
		} finally {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Number getXValue(int index) {
		try {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().lock();
			return getDataPair(index).getX();
		} finally {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Number getYValue(int index) {
		try {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().lock();
			return getDataPair(index).getY();
		} finally {
			allowDuplicateXValues_data_maximumItemCountLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void update(int index, Number y) {
		allowDuplicateXValues_data_maximumItemCountLock.readLock().lock();
		XYDataPair pair = getDataPair(index);
		allowDuplicateXValues_data_maximumItemCountLock.readLock().unlock();
		pair.setY(y);
		fireSeriesChanged();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Object clone() {
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
		Object clone = createCopy(0, getItemCount() - 1);
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
		return clone;
	}
	@Perm(requires = "unique(data) in alive", ensures = "unique(data) in alive")
	public XYSeries createCopy(int start, int end) {
		XYSeries copy = (XYSeries) super.clone();
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().lock();
		copy.data = new java.util.ArrayList();
		if (data.size() > 0) {
			for (int index = start; index <= end; index++) {
				XYDataPair pair = (XYDataPair) this.data.get(index);
				XYDataPair clone = (XYDataPair) pair.clone();
				try {
					copy.add(clone);
				} catch (SeriesException e) {
					System.err.println("XYSeries.createCopy(): unable to add cloned data pair.");
				}
			}
		}
		allowDuplicateXValues_data_maximumItemCountLock.writeLock().unlock();
		return copy;
	}
	@Perm(requires = "pure(data) * pure(maximumItemCount) * immutable(allowDuplicateXValues) in alive", ensures = "pure(data) * pure(maximumItemCount) * immutable(allowDuplicateXValues) in alive")
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof XYSeries) {
			XYSeries s = (XYSeries) obj;
			allowDuplicateXValues_data_maximumItemCountLock.readLock().lock();
			boolean b0 = ObjectUtils.equalOrBothNull(this.data, s.data);
			boolean b1 = (this.maximumItemCount == s.maximumItemCount);
			allowDuplicateXValues_data_maximumItemCountLock.readLock().unlock();
			boolean b2 = (this.allowDuplicateXValues == s.allowDuplicateXValues);
			return b0 && b1 && b2;
		}
		return false;
	}
}
