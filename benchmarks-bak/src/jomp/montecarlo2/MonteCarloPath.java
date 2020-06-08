package jomp.montecarlo2.withlock;
import java.util.*;
import java.io.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class MonteCarloPath extends PathId {
	public static ReentrantReadWriteLock DATUMFIELDLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock pathStartValueLock = new ReentrantReadWriteLock();
	public ReentrantReadWriteLock COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock = new ReentrantReadWriteLock();
	public static boolean DEBUG = true;
	protected static String prompt = "MonteCarloPath> ";
	public static int DATUMFIELD = RatePath.DATUMFIELD;
	private double[] fluctuations;
	private double[] pathValue;
	private int returnDefinition = 0;
	private double expectedReturnRate = Double.NaN;
	private double volatility = Double.NaN;
	private int nTimeSteps = 0;
	private double pathStartValue = Double.NaN;
	@Perm(requires = "none(prompt) * none(DEBUG) * unique(DEBUG) * unique(prompt) in alive", ensures = "none(prompt) * none(DEBUG) * unique(DEBUG) * unique(prompt) in alive")
	public MonteCarloPath() {
		super();
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		setprompt(prompt);
		setDEBUG(DEBUG);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "unique(nTimeSteps) * unique(pathValue) * unique(fluctuations) * none(prompt) * none(DEBUG) in alive", ensures = "unique(nTimeSteps) * unique(pathValue) * unique(fluctuations) * none(prompt) * none(DEBUG) in alive")
	public MonteCarloPath(ReturnPath returnPath, int nTimeSteps) throws DemoException {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		copyInstanceVariables(returnPath);
		this.nTimeSteps = nTimeSteps;
		this.pathValue = new double[nTimeSteps];
		this.fluctuations = new double[nTimeSteps];
		setprompt(prompt);
		setDEBUG(DEBUG);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathValue) * unique(fluctuations) * unique(prompt) * unique(DEBUG) in alive", ensures = "unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathValue) * unique(fluctuations) * unique(prompt) * unique(DEBUG) in alive")
	public MonteCarloPath(PathId pathId, int returnDefinition, double expectedReturnRate, double volatility,
			int nTimeSteps) throws DemoException {
		copyInstanceVariables(pathId);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.returnDefinition = returnDefinition;
		this.expectedReturnRate = expectedReturnRate;
		this.volatility = volatility;
		this.nTimeSteps = nTimeSteps;
		this.pathValue = new double[nTimeSteps];
		this.fluctuations = new double[nTimeSteps];
		setprompt(prompt);
		setDEBUG(DEBUG);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathValue) * unique(fluctuations) * none(prompt) * none(DEBUG) in alive", ensures = "unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathValue) * unique(fluctuations) * none(prompt) * none(DEBUG) in alive")
	public MonteCarloPath(String name, int startDate, int endDate, double dTime, int returnDefinition,
			double expectedReturnRate, double volatility, int nTimeSteps) {
		setname(name);
		setstartDate(startDate);
		setendDate(endDate);
		setdTime(dTime);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.returnDefinition = returnDefinition;
		this.expectedReturnRate = expectedReturnRate;
		this.volatility = volatility;
		this.nTimeSteps = nTimeSteps;
		this.pathValue = new double[nTimeSteps];
		this.fluctuations = new double[nTimeSteps];
		setprompt(prompt);
		setDEBUG(DEBUG);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(fluctuations) in alive", ensures = "pure(fluctuations) in alive")
	public double[] getfluctuations() throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			if (this.fluctuations == null)
				throw new DemoException("Variable fluctuations is undefined!");
			return (this.fluctuations);
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(fluctuations) in alive", ensures = "share(fluctuations) in alive")
	public void setfluctuations(double[] fluctuations) {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.fluctuations = fluctuations;
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(pathValue) in alive", ensures = "pure(pathValue) in alive")
	public double[] getpathValue() throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			if (this.pathValue == null)
				throw new DemoException("Variable pathValue is undefined!");
			return (this.pathValue);
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(pathValue) in alive", ensures = "share(pathValue) in alive")
	public void setpathValue(double[] pathValue) {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.pathValue = pathValue;
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(returnDefinition) in alive", ensures = "pure(returnDefinition) in alive")
	public int getreturnDefinition() throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			if (this.returnDefinition == 0)
				throw new DemoException("Variable returnDefinition is undefined!");
			return (this.returnDefinition);
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(returnDefinition) in alive", ensures = "share(returnDefinition) in alive")
	public void setreturnDefinition(int returnDefinition) {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.returnDefinition = returnDefinition;
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(expectedReturnRate) in alive", ensures = "pure(expectedReturnRate) in alive")
	public double getexpectedReturnRate() throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			if (this.expectedReturnRate == Double.NaN)
				throw new DemoException("Variable expectedReturnRate is undefined!");
			return (this.expectedReturnRate);
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(expectedReturnRate) in alive", ensures = "share(expectedReturnRate) in alive")
	public void setexpectedReturnRate(double expectedReturnRate) {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.expectedReturnRate = expectedReturnRate;
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(volatility) in alive", ensures = "pure(volatility) in alive")
	public double getvolatility() throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			if (this.volatility == Double.NaN)
				throw new DemoException("Variable volatility is undefined!");
			return (this.volatility);
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(volatility) in alive", ensures = "share(volatility) in alive")
	public void setvolatility(double volatility) {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.volatility = volatility;
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(nTimeSteps) in alive", ensures = "pure(nTimeSteps) in alive")
	public int getnTimeSteps() throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			if (this.nTimeSteps == 0)
				throw new DemoException("Variable nTimeSteps is undefined!");
			return (this.nTimeSteps);
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
		}
	}
	@Perm(requires = "share(nTimeSteps) in alive", ensures = "share(nTimeSteps) in alive")
	public void setnTimeSteps(int nTimeSteps) {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.nTimeSteps = nTimeSteps;
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(pathStartValue) in alive", ensures = "pure(pathStartValue) in alive")
	public double getpathStartValue() throws DemoException {
		try {
			pathStartValueLock.readLock().lock();
			if (this.pathStartValue == Double.NaN)
				throw new DemoException("Variable pathStartValue is undefined!");
			return (this.pathStartValue);
		} finally {
			pathStartValueLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(pathStartValue) * share(pathStartValue) in alive", ensures = "pure(pathStartValue) * share(pathStartValue) in alive")
	public void setpathStartValue(double pathStartValue) {
		pathStartValueLock.writeLock().lock();
		this.pathStartValue = pathStartValue;
		pathStartValueLock.writeLock().unlock();
	}
	@Perm(requires = "share(returnDefinition) * share(expectedReturnRate) * share(volatility) in alive", ensures = "share(returnDefinition) * share(expectedReturnRate) * share(volatility) in alive")
	private void copyInstanceVariables(ReturnPath obj) throws DemoException {
		setname(obj.getname());
		setstartDate(obj.getstartDate());
		setendDate(obj.getendDate());
		setdTime(obj.getdTime());
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		this.returnDefinition = obj.getreturnDefinition();
		this.expectedReturnRate = obj.getexpectedReturnRate();
		this.volatility = obj.getvolatility();
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "pure(nTimeSteps) * none(DATUMFIELD) * pure(pathValue) in alive", ensures = "pure(nTimeSteps) * none(DATUMFIELD) * pure(pathValue) in alive")
	public void writeFile(String dirName, String filename) throws DemoException {
		try {
			java.io.File ratesFile = new File(dirName, filename);
			if (ratesFile.exists() && !ratesFile.canWrite())
				throw new DemoException("Cannot write to specified filename!");
			java.io.PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ratesFile)));
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().lock();
			for (int i = 0; i < nTimeSteps; i++) {
				out.print("19990101,");
				for (int j = 1; j < DATUMFIELD; j++) {
					out.print("0.0000,");
				}
				out.print(pathValue[i] + ",");
				out.println("0.0000,0.0000");
			}
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.readLock().unlock();
			out.close();
		} catch (java.io.IOException ioex) {
			throw new DemoException(ioex.toString());
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public RatePath getRatePath() throws DemoException {
		return new RatePath(this);
	}
	@Perm(requires = "pure(nTimeSteps) * share(fluctuations) * pure(expectedReturnRate) * pure(volatility) in alive", ensures = "pure(nTimeSteps) * share(fluctuations) * pure(expectedReturnRate) * pure(volatility) in alive")
	public void computeFluctuationsGaussian(long randomSeed) throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.writeLock().lock();
			if (nTimeSteps > fluctuations.length)
				throw new DemoException("Number of timesteps requested is greater than the allocated array!");
			Random rnd;
			if (randomSeed == -1) {
				rnd = new Random();
			} else {
				rnd = new Random(randomSeed);
			}
			double mean = (expectedReturnRate - 0.5 * volatility * volatility) * getdTime();
			double sd = volatility * Math.sqrt(getdTime());
			double gauss, meanGauss = 0.0, variance = 0.0;
			for (int i = 0; i < nTimeSteps; i++) {
				gauss = rnd.nextGaussian();
				meanGauss += gauss;
				variance += (gauss * gauss);
				fluctuations[i] = mean + sd * gauss;
			}
			meanGauss /= (double) nTimeSteps;
			variance /= (double) nTimeSteps;
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.writeLock().unlock();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public void computeFluctuationsGaussianOverload() throws DemoException {
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().lock();
		computeFluctuationsGaussian((long) -1);
		COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
				.writeLock().unlock();
	}
	@Perm(requires = "share(pathValue) * pure(returnDefinition) * immutable(COMPOUNDED) * immutable(NONCOMPOUNDED) * pure(nTimeSteps) * pure(fluctuations) in alive", ensures = "share(pathValue) * pure(returnDefinition) * immutable(COMPOUNDED) * immutable(NONCOMPOUNDED) * pure(nTimeSteps) * pure(fluctuations) in alive")
	public void computePathValue(double startValue) throws DemoException {
		try {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.writeLock().lock();
			pathValue[0] = startValue;
			if (returnDefinition == ReturnPath.COMPOUNDED || returnDefinition == ReturnPath.NONCOMPOUNDED) {
				for (int i = 1; i < nTimeSteps; i++) {
					pathValue[i] = pathValue[i - 1] * Math.exp(fluctuations[i]);
				}
			} else {
				throw new DemoException("Unknown or undefined update method.");
			}
		} finally {
			COMPOUNDED_DEBUG_NONCOMPOUNDED_expectedReturnRate_fluctuations_nTimeSteps_pathValue_prompt_returnDefinition_volatilityLock
					.writeLock().unlock();
		}
	}
}
