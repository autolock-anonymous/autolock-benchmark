package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class PriceStock extends Universal {
  public ReentrantReadWriteLock DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock=new ReentrantReadWriteLock();
  public static boolean DEBUG=true;
  protected static String prompt="PriceStock> ";
  private MonteCarloPath mcPath;
  private String taskHeader;
  private long randomSeed=-1;
  private double pathStartValue=Double.NaN;
  private ToResult result;
  private double expectedReturnRate=Double.NaN;
  private double volatility=Double.NaN;
  private double volatility2=Double.NaN;
  private double finalStockPrice=Double.NaN;
  private double[] pathValue;
  @Perm(requires="unique(mcPath) * none(prompt) * none(DEBUG) * unique(DEBUG) * unique(prompt) in alive",ensures="unique(mcPath) * none(prompt) * none(DEBUG) * unique(DEBUG) * unique(prompt) in alive") public PriceStock(){
    super();
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().lock();
    mcPath=new MonteCarloPath();
    setprompt(prompt);
    setDEBUG(DEBUG);
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().unlock();
  }
  @Perm(requires="immutable(mcPath) * share(pathStartValue) * pure(pathStartValue) * share(pathStartValue) in alive",ensures="immutable(mcPath) * share(pathStartValue) * pure(pathStartValue) * share(pathStartValue) in alive") public void setInitAllTasks(  Object obj){
    ToInitAllTasks initAllTasks=(ToInitAllTasks)obj;
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().lock();
    mcPath.setname(initAllTasks.getname());
    mcPath.setstartDate(initAllTasks.getstartDate());
    mcPath.setendDate(initAllTasks.getendDate());
    mcPath.setdTime(initAllTasks.getdTime());
    mcPath.setreturnDefinition(initAllTasks.getreturnDefinition());
    mcPath.setexpectedReturnRate(initAllTasks.getexpectedReturnRate());
    mcPath.setvolatility(initAllTasks.getvolatility());
    int nTimeSteps=initAllTasks.getnTimeSteps();
    mcPath.setnTimeSteps(nTimeSteps);
    this.pathStartValue=initAllTasks.getpathStartValue();
    mcPath.setpathStartValue(pathStartValue);
    mcPath.setpathValue(new double[nTimeSteps]);
    mcPath.setfluctuations(new double[nTimeSteps]);
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().unlock();
  }
  @Perm(requires="share(taskHeader) * share(randomSeed) * pure(randomSeed) in alive",ensures="share(taskHeader) * share(randomSeed) * pure(randomSeed) in alive") public void setTask(  Object obj){
    ToTask task=(ToTask)obj;
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().lock();
    this.taskHeader=task.getheader();
    this.randomSeed=task.getrandomSeed();
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().unlock();
  }
  @Perm(requires="immutable(mcPath) * pure(randomSeed) * pure(pathStartValue) * share(expectedReturnRate) * share(volatility) * share(volatility2) * share(finalStockPrice) * share(pathValue) * pure(expectedReturnRate) * pure(volatility) * share(pathValue) * pure(pathValue) in alive",ensures="immutable(mcPath) * pure(randomSeed) * pure(pathStartValue) * share(expectedReturnRate) * share(volatility) * share(volatility2) * share(finalStockPrice) * share(pathValue) * pure(expectedReturnRate) * pure(volatility) * share(pathValue) * pure(pathValue) in alive") public void run(){
    try {
      DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.writeLock().lock();
      mcPath.computeFluctuationsGaussian(randomSeed);
      mcPath.computePathValue(pathStartValue);
      RatePath rateP=new RatePath(mcPath);
      ReturnPath returnP=rateP.getReturnCompounded();
      returnP.estimatePath();
      expectedReturnRate=returnP.getexpectedReturnRate();
      volatility=returnP.getvolatility();
      volatility2=returnP.getvolatility2();
      finalStockPrice=rateP.getEndPathValue();
      pathValue=mcPath.getpathValue();
      DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.readLock().unlock();
    }
 catch (    DemoException demoEx) {
      errPrintln(demoEx.toString());
    }
  }
  @Perm(requires="pure(taskHeader) * pure(randomSeed) * pure(pathStartValue) * pure(expectedReturnRate) * pure(volatility) * pure(volatility2) * pure(finalStockPrice) * pure(pathValue) in alive",ensures="pure(taskHeader) * pure(randomSeed) * pure(pathStartValue) * pure(expectedReturnRate) * pure(volatility) * pure(volatility2) * pure(finalStockPrice) * pure(pathValue) in alive") public Object getResult(){
    Cloner cloner=new Cloner();
    result=cloner.deepClone(result);
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.readLock().lock();
    String resultHeader="Result of task with Header=" + taskHeader + ": randomSeed="+ randomSeed+ ": pathStartValue="+ pathStartValue;
    ToResult res=new ToResult(resultHeader,expectedReturnRate,volatility,volatility2,finalStockPrice,pathValue);
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock.readLock().unlock();
    return (Object)res;
  }
  public static String getPrompt(){
    Cloner cloner=new Cloner();
    prompt=cloner.deepClone(prompt);
    return prompt;
  }
  public MonteCarloPath getMcPath(){
    Cloner cloner=new Cloner();
    mcPath=cloner.deepClone(mcPath);
    return mcPath;
  }
  public String getTaskHeader(){
    Cloner cloner=new Cloner();
    taskHeader=cloner.deepClone(taskHeader);
    return taskHeader;
  }
  public ReentrantReadWriteLock getDEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock(){
    Cloner cloner=new Cloner();
    DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock=cloner.deepClone(DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock);
    return DEBUG_expectedReturnRate_finalStockPrice_mcPath_pathStartValue_pathValue_prompt_randomSeed_taskHeader_volatility_volatility2Lock;
  }
}
