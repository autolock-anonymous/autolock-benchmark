package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ToResult implements java.io.Serializable {
	public ReentrantReadWriteLock expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock = new ReentrantReadWriteLock();
	private String header;
	private double expectedReturnRate = Double.NaN;
	private double volatility = Double.NaN;
	private double volatility2 = Double.NaN;
	private double finalStockPrice = Double.NaN;
	private double[] pathValue;
	@Perm(requires = "none(expectedReturnRate) * none(volatility) * none(volatility2) * none(finalStockPrice) * none(pathValue) * unique(header) * unique(expectedReturnRate) * unique(volatility) * unique(volatility2) * unique(finalStockPrice) * unique(pathValue) in alive", ensures = "none(expectedReturnRate) * none(volatility) * none(volatility2) * none(finalStockPrice) * none(pathValue) * unique(header) * unique(expectedReturnRate) * unique(volatility) * unique(volatility2) * unique(finalStockPrice) * unique(pathValue) in alive")
	public ToResult(String header, double expectedReturnRate, double volatility, double volatility2,
			double finalStockPrice, double[] pathValue) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.header = header;
		this.expectedReturnRate = expectedReturnRate;
		this.volatility = volatility;
		this.volatility2 = volatility2;
		this.finalStockPrice = finalStockPrice;
		this.pathValue = pathValue;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
	@Perm(requires = "pure(header) in alive", ensures = "pure(header) in alive")
	public String toString() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (header);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(header) in alive", ensures = "pure(header) in alive")
	public String getheader() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (this.header);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "full(header) in alive", ensures = "full(header) in alive")
	public void setheader(String header) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.header = header;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
	@Perm(requires = "pure(expectedReturnRate) in alive", ensures = "pure(expectedReturnRate) in alive")
	public double getexpectedReturnRate() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (this.expectedReturnRate);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "full(expectedReturnRate) in alive", ensures = "full(expectedReturnRate) in alive")
	public void setexpectedReturnRate(double expectedReturnRate) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.expectedReturnRate = expectedReturnRate;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
	@Perm(requires = "pure(volatility) in alive", ensures = "pure(volatility) in alive")
	public double getvolatility() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (this.volatility);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "full(volatility) in alive", ensures = "full(volatility) in alive")
	public void setvolatility(double volatility) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.volatility = volatility;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
	@Perm(requires = "pure(volatility2) in alive", ensures = "pure(volatility2) in alive")
	public double getVolatility2() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (this.volatility2);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "full(volatility2) in alive", ensures = "full(volatility2) in alive")
	public void setvolatility2(double volatility2) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.volatility2 = volatility2;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
	@Perm(requires = "pure(finalStockPrice) in alive", ensures = "pure(finalStockPrice) in alive")
	public double getfinalStockPrice() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (this.finalStockPrice);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "full(finalStockPrice) in alive", ensures = "full(finalStockPrice) in alive")
	public void setfinalStockPrice(double finalStockPrice) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.finalStockPrice = finalStockPrice;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
	@Perm(requires = "pure(pathValue) in alive", ensures = "pure(pathValue) in alive")
	public double[] getpathValue() {
		try {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().lock();
			return (this.pathValue);
		} finally {
			expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.readLock().unlock();
		}
	}
	@Perm(requires = "full(pathValue) in alive", ensures = "full(pathValue) in alive")
	public void setpathValue(double[] pathValue) {
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().lock();
		this.pathValue = pathValue;
		expectedReturnRate_finalStockPrice_header_pathValue_volatility_volatility2Lock.writeLock().unlock();
	}
}
