package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ReturnPath extends PathId {
	public ReentrantReadWriteLock DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock = new ReentrantReadWriteLock();
	public static boolean DEBUG = true;
	protected static String prompt = "ReturnPath> ";
	public static int COMPOUNDED = 1;
	public static int NONCOMPOUNDED = 2;
	private double[] pathValue;
	private int nPathValue = 0;
	private int returnDefinition = 0;
	private double expectedReturnRate = Double.NaN;
	private double volatility = Double.NaN;
	private double volatility2 = Double.NaN;
	private double mean = Double.NaN;
	private double variance = Double.NaN;
	@Perm(requires = "none(prompt) * none(DEBUG) * unique(prompt) * unique(DEBUG) in alive", ensures = "none(prompt) * none(DEBUG) * unique(prompt) * unique(DEBUG) in alive")
	public ReturnPath() {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		setprompt(prompt);
		setDEBUG(DEBUG);
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "none(prompt) * none(DEBUG) * unique(pathValue) * unique(nPathValue) * unique(returnDefinition) * unique(prompt) * unique(DEBUG) in alive", ensures = "none(prompt) * none(DEBUG) * unique(pathValue) * unique(nPathValue) * unique(returnDefinition) * unique(prompt) * unique(DEBUG) in alive")
	public ReturnPath(double[] pathValue, int nPathValue, int returnDefinition) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		setprompt(prompt);
		setDEBUG(DEBUG);
		this.pathValue = pathValue;
		this.nPathValue = nPathValue;
		this.returnDefinition = returnDefinition;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(pathValue) in alive", ensures = "pure(pathValue) in alive")
	public double[] getpathValue() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.pathValue == null)
				throw new DemoException("Variable pathValue is undefined!");
			return pathValue;
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "full(pathValue) in alive", ensures = "full(pathValue) in alive")
	public void setpathValue(double[] pathValue) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.pathValue = pathValue;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(nPathValue) in alive", ensures = "pure(nPathValue) in alive")
	public int getnPathValue() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.nPathValue == 0)
				throw new DemoException("Variable nPathValue is undefined!");
			return (this.nPathValue);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "full(nPathValue) in alive", ensures = "full(nPathValue) in alive")
	public void setnPathValue(int nPathValue) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.nPathValue = nPathValue;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(returnDefinition) in alive", ensures = "pure(returnDefinition) in alive")
	public int getreturnDefinition() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.returnDefinition == 0)
				throw new DemoException("Variable returnDefinition is undefined!");
			return (this.returnDefinition);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "full(returnDefinition) in alive", ensures = "full(returnDefinition) in alive")
	public void setreturnDefinition(int returnDefinition) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.returnDefinition = returnDefinition;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(expectedReturnRate) in alive", ensures = "pure(expectedReturnRate) in alive")
	public double getexpectedReturnRate() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.expectedReturnRate == Double.NaN)
				throw new DemoException("Variable expectedReturnRate is undefined!");
			return (this.expectedReturnRate);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(expectedReturnRate) in alive", ensures = "share(expectedReturnRate) in alive")
	public void setexpectedReturnRate(double expectedReturnRate) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.expectedReturnRate = expectedReturnRate;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(volatility) in alive", ensures = "pure(volatility) in alive")
	public double getvolatility() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.volatility == Double.NaN)
				throw new DemoException("Variable volatility is undefined!");
			return (this.volatility);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(volatility) in alive", ensures = "share(volatility) in alive")
	public void setvolatility(double volatility) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.volatility = volatility;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(volatility2) in alive", ensures = "pure(volatility2) in alive")
	public double getvolatility2() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.volatility2 == Double.NaN)
				throw new DemoException("Variable volatility2 is undefined!");
			return (this.volatility2);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(volatility2) in alive", ensures = "share(volatility2) in alive")
	public void setvolatility2(double volatility2) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.volatility2 = volatility2;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(mean) in alive", ensures = "pure(mean) in alive")
	public double getmean() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.mean == Double.NaN)
				throw new DemoException("Variable mean is undefined!");
			return (this.mean);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(mean) in alive", ensures = "share(mean) in alive")
	public void setmean(double mean) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.mean = mean;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(variance) in alive", ensures = "pure(variance) in alive")
	public double getvariance() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			if (this.variance == Double.NaN)
				throw new DemoException("Variable variance is undefined!");
			return (this.variance);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(variance) in alive", ensures = "share(variance) in alive")
	public void setvariance(double variance) {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.variance = variance;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "share(expectedReturnRate) * pure(mean) * pure(volatility2) in alive", ensures = "share(expectedReturnRate) * pure(mean) * pure(volatility2) in alive")
	public void computeExpectedReturnRate() throws DemoException {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.readLock().lock();
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		this.expectedReturnRate = mean / getdTime() + 0.5 * volatility2;
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.readLock().unlock();
	}
	@Perm(requires = "pure(variance) * share(volatility2) * share(volatility) in alive", ensures = "pure(variance) * share(volatility2) * share(volatility) in alive")
	public void computeVolatility() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.writeLock().lock();
			if (this.variance == Double.NaN)
				throw new DemoException("Variable variance is not defined!");
			this.volatility2 = variance / getdTime();
			this.volatility = Math.sqrt(volatility2);
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(nPathValue) * share(mean) * pure(pathValue) in alive", ensures = "pure(nPathValue) * share(mean) * pure(pathValue) in alive")
	public void computeMean() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.writeLock().lock();
			if (this.nPathValue == 0)
				throw new DemoException("Variable nPathValue is undefined!");
			this.mean = 0.0;
			for (int i = 1; i < nPathValue; i++) {
				mean += pathValue[i];
			}
			this.mean /= ((double) (nPathValue - 1.0));
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.writeLock().unlock();
		}
	}
	@Perm(requires = "pure(mean) * pure(nPathValue) * share(variance) * pure(pathValue) in alive", ensures = "pure(mean) * pure(nPathValue) * share(variance) * pure(pathValue) in alive")
	public void computeVariance() throws DemoException {
		try {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().lock();
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.writeLock().lock();
			if (this.mean == Double.NaN || this.nPathValue == 0)
				throw new DemoException("Variable mean and/or nPathValue are undefined!");
			this.variance = 0.0;
			for (int i = 1; i < nPathValue; i++) {
				variance += (pathValue[i] - mean) * (pathValue[i] - mean);
			}
			this.variance /= ((double) (nPathValue - 1.0));
		} finally {
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.writeLock().unlock();
			DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
					.readLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void estimatePath() throws DemoException {
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().lock();
		computeMean();
		computeVariance();
		computeExpectedReturnRate();
		computeVolatility();
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(nPathValue) * pure(expectedReturnRate) * pure(volatility) * pure(volatility2) * pure(mean) * pure(variance) in alive", ensures = "pure(nPathValue) * pure(expectedReturnRate) * pure(volatility) * pure(volatility2) * pure(mean) * pure(variance) in alive")
	public void dbgDumpFields() {
		super.dbgDumpFields();
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.readLock().lock();
		dbgPrintln("nPathValue=" + this.nPathValue);
		dbgPrintln("expectedReturnRate=" + this.expectedReturnRate);
		dbgPrintln("volatility=" + this.volatility);
		dbgPrintln("volatility2=" + this.volatility2);
		dbgPrintln("mean=" + this.mean);
		dbgPrintln("variance=" + this.variance);
		DEBUG_expectedReturnRate_mean_nPathValue_pathValue_prompt_returnDefinition_variance_volatility_volatility2Lock
				.readLock().unlock();
	}
}
