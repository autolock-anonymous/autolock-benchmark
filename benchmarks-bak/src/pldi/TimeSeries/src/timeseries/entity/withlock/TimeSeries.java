package timeseries.entity.withlock;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jfree.data.Series;
import org.jfree.data.SeriesException;
import org.jfree.data.time.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class TimeSeries extends Series implements Serializable {
	public ReentrantReadWriteLock data_domain_historyCount_maximumItemCount_range_timePeriodClassLock = new ReentrantReadWriteLock();
	protected static final String DEFAULT_DOMAIN_DESCRIPTION = "Time";
	protected static final String DEFAULT_RANGE_DESCRIPTION = "Value";
	private String domain;
	private String range;
	private Class timePeriodClass;
	private List data;
	private int maximumItemCount;
	private int historyCount;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TimeSeries(String name) {
		this(name, DEFAULT_DOMAIN_DESCRIPTION, DEFAULT_RANGE_DESCRIPTION, Day.class);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TimeSeries(String name, Class timePeriodClass) {
		this(name, DEFAULT_DOMAIN_DESCRIPTION, DEFAULT_RANGE_DESCRIPTION, timePeriodClass);
	}
	@Perm(requires = "unique(domain) * unique(range) * unique(timePeriodClass) * unique(data) * unique(maximumItemCount) * unique(historyCount) in alive", ensures = "unique(domain) * unique(range) * unique(timePeriodClass) * unique(data) * unique(maximumItemCount) * unique(historyCount) in alive")
	public TimeSeries(String name, String domain, String range, Class timePeriodClass) {
		super(name);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		this.domain = domain;
		this.range = range;
		this.timePeriodClass = timePeriodClass;
		data = new java.util.ArrayList();
		this.maximumItemCount = Integer.MAX_VALUE;
		this.historyCount = 0;
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
	}
	@Perm(requires = "pure(domain) in alive", ensures = "pure(domain) in alive")
	public String getDomainDescription() {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return this.domain;
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(domain) in alive", ensures = "full(domain) in alive")
	public void setDomainDescription(String description) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		String old = this.domain;
		this.domain = description;
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		firePropertyChange("Domain", old, description);
	}
	@Perm(requires = "pure(range) in alive", ensures = "pure(range) in alive")
	public String getRangeDescription() {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return this.range;
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(range) in alive", ensures = "full(range) in alive")
	public void setRangeDescription(String description) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		String old = this.range;
		this.range = description;
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		firePropertyChange("Range", old, description);
	}
	@Perm(requires = "unique(data) in alive", ensures = "unique(data) in alive")
	public int getItemCount() {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
			return data.size();
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(maximumItemCount) in alive", ensures = "pure(maximumItemCount) in alive")
	public int getMaximumItemCount() {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return this.maximumItemCount;
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(maximumItemCount) in alive", ensures = "full(maximumItemCount) in alive")
	public void setMaximumItemCount(int maximum) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		this.maximumItemCount = maximum;
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
	}
	@Perm(requires = "pure(historyCount) in alive", ensures = "pure(historyCount) in alive")
	public int getHistoryCount() {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return this.historyCount;
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "full(historyCount) in alive", ensures = "full(historyCount) in alive")
	public void setHistoryCount(int periods) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		this.historyCount = periods;
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
	}
	@Perm(requires = "pure(timePeriodClass) in alive", ensures = "pure(timePeriodClass) in alive")
	public Class getTimePeriodClass() {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return this.timePeriodClass;
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(data) in alive", ensures = "pure(data) in alive")
	public TimeSeriesDataItem getDataPair(int index) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
		TimeSeriesDataItem dataItem = (TimeSeriesDataItem) data.get(index);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		return dataItem;
	}
	@Perm(requires = "pure(data) in alive", ensures = "pure(data) in alive")
	public TimeSeriesDataItem getDataPair1(RegularTimePeriod period) {
		if (period == null) {
			throw new IllegalArgumentException("TimeSeries.getDataPair(...): null time period not allowed.");
		}
		TimeSeriesDataItem dummy = new TimeSeriesDataItem(period, new Integer(0));
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			int index = Collections.binarySearch(data, dummy);
			if (index >= 0) {
				return (TimeSeriesDataItem) data.get(index);
			} else {
				return null;
			}
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public RegularTimePeriod getTimePeriod(int index) {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return getDataPair(index).getPeriod();
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public RegularTimePeriod getNextTimePeriod() {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		RegularTimePeriod last = getTimePeriod(getItemCount() - 1);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return last.next();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Collection getTimePeriods() {
		Collection result = new java.util.ArrayList();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		for (int i = 0; i < getItemCount(); i++) {
			result.add(getTimePeriod(i));
		}
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return result;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Collection getTimePeriodsUniqueToOtherSeries(TimeSeries series) {
		Collection result = new java.util.ArrayList();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		for (int i = 0; i < series.getItemCount(); i++) {
			RegularTimePeriod period = series.getTimePeriod(i);
			int index = getIndex(period);
			if (index < 0) {
				result.add(period);
			}
		}
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return result;
	}
	@Perm(requires = "pure(data) in alive", ensures = "pure(data) in alive")
	public int getIndex(RegularTimePeriod period) {
		if (period != null) {
			TimeSeriesDataItem dummy = new TimeSeriesDataItem(period, new Integer(0));
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			int index = Collections.binarySearch(data, dummy);
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
			return index;
		} else {
			return -1;
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Number getValue(int index) {
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			return getDataPair(index).getValue();
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Number getValue(RegularTimePeriod period) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
		int index = getIndex(period);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		if (index >= 0) {
			return getValue(index);
		} else {
			return null;
		}
	}
	@Perm(requires = "share(timePeriodClass) * unique(data) * pure(maximumItemCount) * pure(historyCount) in alive", ensures = "share(timePeriodClass) * unique(data) * pure(maximumItemCount) * pure(historyCount) in alive")
	public void add(TimeSeriesDataItem pair) throws SeriesException {
		if (pair == null) {
			throw new IllegalArgumentException("TimeSeries.add(...): null item not allowed.");
		}
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
			if (!pair.getPeriod().getClass().equals(timePeriodClass)) {
				String message = "TimeSeries.add(): you are trying to add data where the time ";
				message = message + "period class is " + pair.getPeriod().getClass().getName() + ", ";
				message = message + "but the TimeSeries is expecting an instance of " + timePeriodClass.getName() + ".";
				throw new SeriesException(message);
			}
			System.out.println("start " + pair.getValue() + " " + data.size());
			int index = Collections.binarySearch(data, pair);
			if (index < 0) {
				System.out.println("index " + pair.getValue() + " " + index);
				this.data.add(-index - 1, pair);
				System.out.println("end " + pair.getValue() + " " + data.size());
				if (getItemCount() > this.maximumItemCount) {
					this.data.remove(0);
					System.out.println("???");
				}
				if ((getItemCount() > 1) && (this.historyCount > 0)) {
					long latest = getTimePeriod(getItemCount() - 1).getSerialIndex();
					while ((latest - getTimePeriod(0).getSerialIndex()) >= historyCount) {
						this.data.remove(0);
						System.out.println("???");
					}
				}
				fireSeriesChanged();
			} else {
				throw new SeriesException("TimeSeries.add(...): time period already exists.");
			}
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(RegularTimePeriod period, double value) throws SeriesException {
		TimeSeriesDataItem pair = new TimeSeriesDataItem(period, value);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		add(pair);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void add(RegularTimePeriod period, Number value) throws SeriesException {
		TimeSeriesDataItem pair = new TimeSeriesDataItem(period, value);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		add(pair);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
	}
	@Perm(requires = "pure(data) in alive", ensures = "pure(data) in alive")
	public void update(RegularTimePeriod period, Number value) throws SeriesException {
		TimeSeriesDataItem temp = new TimeSeriesDataItem(period, value);
		try {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
			int index = Collections.binarySearch(data, temp);
			if (index >= 0) {
				TimeSeriesDataItem pair = (TimeSeriesDataItem) data.get(index);
				pair.setValue(value);
				fireSeriesChanged();
			} else {
				throw new SeriesException("TimeSeries.update(TimePeriod, Number): " + "period does not exist.");
			}
		} finally {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void update(int index, Number value) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().lock();
		TimeSeriesDataItem pair = getDataPair(index);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.readLock().unlock();
		pair.setValue(value);
		fireSeriesChanged();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TimeSeries addAndOrUpdate(TimeSeries series) {
		TimeSeries overwritten = new TimeSeries("Overwritten values from: " + getName());
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		for (int i = 0; i < series.getItemCount(); i++) {
			TimeSeriesDataItem pair = series.getDataPair(i);
			TimeSeriesDataItem oldPair = addOrUpdate(pair.getPeriod(), pair.getValue());
			if (oldPair != null) {
				try {
					overwritten.add(oldPair);
				} catch (SeriesException e) {
					System.err.println(
							"TimeSeries.addAndOrUpdate(series): " + "unable to add data to overwritten series.");
				}
			}
		}
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return overwritten;
	}
	@Perm(requires = "share(data) in alive", ensures = "share(data) in alive")
	public TimeSeriesDataItem addOrUpdate(RegularTimePeriod period, Number value) {
		TimeSeriesDataItem overwritten = null;
		TimeSeriesDataItem key = new TimeSeriesDataItem(period, value);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		int index = Collections.binarySearch(data, key);
		if (index >= 0) {
			TimeSeriesDataItem existing = (TimeSeriesDataItem) data.get(index);
			overwritten = (TimeSeriesDataItem) existing.clone();
			existing.setValue(value);
			fireSeriesChanged();
		} else {
			data.add(-index - 1, new TimeSeriesDataItem(period, value));
			fireSeriesChanged();
		}
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return overwritten;
	}
	@Perm(requires = "share(data) in alive", ensures = "share(data) in alive")
	public void delete(RegularTimePeriod period) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		int index = getIndex(period);
		data.remove(index);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
	}
	@Perm(requires = "share(data) in alive", ensures = "share(data) in alive")
	public void delete(int start, int end) {
		for (int i = 0; i <= (end - start); i++) {
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
			this.data.remove(start);
			data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		}
		fireSeriesChanged();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public Object clone() {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		Object clone = createCopy(0, getItemCount() - 1);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return clone;
	}
	@Perm(requires = "unique(data) in alive", ensures = "unique(data) in alive")
	public TimeSeries createCopy(int start, int end) {
		TimeSeries copy = (TimeSeries) super.clone();
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		copy.data = new java.util.ArrayList();
		if (data.size() > 0) {
			for (int index = start; index <= end; index++) {
				TimeSeriesDataItem pair = (TimeSeriesDataItem) this.data.get(index);
				TimeSeriesDataItem clone = (TimeSeriesDataItem) pair.clone();
				try {
					copy.add(clone);
				} catch (SeriesException e) {
					System.err.println("TimeSeries.createCopy(): unable to add cloned data pair.");
				}
			}
		}
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public TimeSeries createCopy(RegularTimePeriod start, RegularTimePeriod end) {
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
		int startIndex = getIndex(start);
		int endIndex = getIndex(end);
		TimeSeries copy = createCopy(startIndex, endIndex);
		data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
		return copy;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public boolean equals(Object target) {
		boolean result = false;
		if (this == target) {
			result = true;
		} else {
			if (target instanceof TimeSeries) {
				TimeSeries s = (TimeSeries) target;
				data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().lock();
				int count = getItemCount();
				boolean same = true;
				if (s.getItemCount() == count) {
					for (int i = 0; i < count; i++) {
						same = same && getDataPair(i).equals(s.getDataPair(i));
						if (!same) {
							continue;
						}
					}
				}
				data_domain_historyCount_maximumItemCount_range_timePeriodClassLock.writeLock().unlock();
				result = same;
			}
		}
		return result;
	}
}
