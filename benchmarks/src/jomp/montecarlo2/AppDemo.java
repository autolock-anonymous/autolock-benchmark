package jomp.montecarlo2.withlock;
import java.util.*;
import java.awt.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class AppDemo extends Universal {
  public ReentrantReadWriteLock DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock=new ReentrantReadWriteLock();
  public static double JGFavgExpectedReturnRateMC=0.0;
  public static boolean DEBUG=true;
  protected static String prompt="AppDemo> ";
  public static final int Serial=1;
  private String dataDirname;
  private String dataFilename;
  private int nTimeStepsMC=0;
  private int nRunsMC=0;
  private double dTime=1.0 / 365.0;
  private boolean initialised=false;
  private int runMode;
  private Vector tasks=new Vector(nRunsMC);
  private Vector results;
  @Perm(requires="unique(dataDirname) * unique(dataFilename) * unique(nTimeStepsMC) * unique(nRunsMC) * unique(initialised) * none(prompt) * none(DEBUG) * unique(prompt) * unique(DEBUG) in alive",ensures="unique(dataDirname) * unique(dataFilename) * unique(nTimeStepsMC) * unique(nRunsMC) * unique(initialised) * none(prompt) * none(DEBUG) * unique(prompt) * unique(DEBUG) in alive") public AppDemo(  String dataDirname,  String dataFilename,  int nTimeStepsMC,  int nRunsMC){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.dataDirname=dataDirname;
    this.dataFilename=dataFilename;
    this.nTimeStepsMC=nTimeStepsMC;
    this.nRunsMC=nRunsMC;
    this.initialised=false;
    setprompt(prompt);
    setDEBUG(DEBUG);
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  PriceStock psMC;
  double pathStartValue=100.0;
  double avgExpectedReturnRateMC=0.0;
  double avgVolatilityMC=0.0;
  ToInitAllTasks initAllTasks=null;
  RatePath rateP=null;
  @Perm(requires="pure(dataDirname) * pure(dataFilename) * unique(initAllTasks) * pure(nTimeStepsMC) * immutable(pathStartValue) * pure(nRunsMC) in alive",ensures="pure(dataDirname) * pure(dataFilename) * unique(initAllTasks) * pure(nTimeStepsMC) * immutable(pathStartValue) * pure(nRunsMC) in alive") public void initSerial(){
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
      RatePath rateP=new RatePath(dataDirname,dataFilename);
      rateP.dbgDumpFields();
      ReturnPath returnP=rateP.getReturnCompounded();
      returnP.estimatePath();
      returnP.dbgDumpFields();
      double expectedReturnRate=returnP.getexpectedReturnRate();
      double volatility=returnP.getvolatility();
      initAllTasks=new ToInitAllTasks(returnP,nTimeStepsMC,pathStartValue);
      String slaveClassName="MonteCarlo.PriceStock";
      initTasks(nRunsMC);
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
    }
 catch (    DemoException demoEx) {
      dbgPrintln(demoEx.toString());
      System.exit(-1);
    }
  }
  @Perm(requires="unique(results) * pure(nRunsMC) * pure(initAllTasks) * share(tasks) in alive",ensures="unique(results) * pure(nRunsMC) * pure(initAllTasks) * share(tasks) in alive") public void runSerial(){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    results=new Vector(nRunsMC);
    PriceStock ps;
    for (int iRun=0; iRun < nRunsMC; iRun++) {
      ps=new PriceStock();
      ps.setInitAllTasks(initAllTasks);
      ps.setTask(tasks.elementAt(iRun));
      ps.run();
{
        results.addElement(ps.getResult());
      }
    }
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void processSerial(){
    try {
      processResults();
    }
 catch (    DemoException demoEx) {
      dbgPrintln(demoEx.toString());
      System.exit(-1);
    }
  }
  @Perm(requires="unique(tasks) in alive",ensures="unique(tasks) in alive") private void initTasks(  int nRunsMC){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    tasks=new Vector(nRunsMC);
    for (int i=0; i < nRunsMC; i++) {
      String header="MC run " + String.valueOf(i);
      ToTask task=new ToTask(header,(long)i * 11);
      tasks.addElement((Object)task);
    }
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(nRunsMC) * share(results) * full(JGFavgExpectedReturnRateMC) in alive",ensures="pure(nRunsMC) * share(results) * full(JGFavgExpectedReturnRateMC) in alive") private void processResults() throws DemoException {
    double avgExpectedReturnRateMC=0.0;
    double avgVolatilityMC=0.0;
    double runAvgExpectedReturnRateMC=0.0;
    double runAvgVolatilityMC=0.0;
    ToResult returnMC;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    if (nRunsMC != results.size()) {
      errPrintln("Fatal: TaskRunner managed to finish with no all the results gathered in!");
      System.exit(-1);
    }
    for (int i=0; i < nRunsMC; i++) {
      returnMC=(ToResult)results.elementAt(i);
      avgExpectedReturnRateMC+=returnMC.getexpectedReturnRate();
      avgVolatilityMC+=returnMC.getvolatility();
      runAvgExpectedReturnRateMC=avgExpectedReturnRateMC / ((double)(i + 1));
      runAvgVolatilityMC=avgVolatilityMC / ((double)(i + 1));
    }
    avgExpectedReturnRateMC/=nRunsMC;
    avgVolatilityMC/=nRunsMC;
    JGFavgExpectedReturnRateMC=avgExpectedReturnRateMC;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(dataDirname) in alive",ensures="pure(dataDirname) in alive") public String getdataDirname(){
    Cloner cloner=new Cloner();
    dataDirname=cloner.deepClone(dataDirname);
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().lock();
      return this.dataDirname;
    }
  finally {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().unlock();
    }
  }
  @Perm(requires="full(dataDirname) in alive",ensures="full(dataDirname) in alive") public void setdataDirname(  String dataDirname){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.dataDirname=dataDirname;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(dataFilename) in alive",ensures="pure(dataFilename) in alive") public String getdataFilename(){
    Cloner cloner=new Cloner();
    dataFilename=cloner.deepClone(dataFilename);
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().lock();
      return (this.dataFilename);
    }
  finally {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().unlock();
    }
  }
  @Perm(requires="full(dataFilename) in alive",ensures="full(dataFilename) in alive") public void setdataFilename(  String dataFilename){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.dataFilename=dataFilename;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(nTimeStepsMC) in alive",ensures="pure(nTimeStepsMC) in alive") public int getnTimeStepsMC(){
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().lock();
      return (this.nTimeStepsMC);
    }
  finally {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().unlock();
    }
  }
  @Perm(requires="full(nTimeStepsMC) in alive",ensures="full(nTimeStepsMC) in alive") public void setnTimeStepsMC(  int nTimeStepsMC){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.nTimeStepsMC=nTimeStepsMC;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(nRunsMC) in alive",ensures="pure(nRunsMC) in alive") public int getnRunsMC(){
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().lock();
      return (this.nRunsMC);
    }
  finally {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().unlock();
    }
  }
  @Perm(requires="full(nRunsMC) in alive",ensures="full(nRunsMC) in alive") public void setnRunsMC(  int nRunsMC){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.nRunsMC=nRunsMC;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(tasks) in alive",ensures="pure(tasks) in alive") public Vector gettasks(){
    Cloner cloner=new Cloner();
    tasks=cloner.deepClone(tasks);
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().lock();
      return this.tasks;
    }
  finally {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().unlock();
    }
  }
  @Perm(requires="share(tasks) in alive",ensures="share(tasks) in alive") public void settasks(  Vector tasks){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.tasks=tasks;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  @Perm(requires="pure(results) in alive",ensures="pure(results) in alive") public Vector getresults(){
    Cloner cloner=new Cloner();
    results=cloner.deepClone(results);
    try {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().lock();
      return this.results;
    }
  finally {
      DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.readLock().unlock();
    }
  }
  @Perm(requires="share(results) in alive",ensures="share(results) in alive") public void setresults(  Vector results){
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().lock();
    this.results=results;
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock.writeLock().unlock();
  }
  public static String getPrompt(){
    Cloner cloner=new Cloner();
    prompt=cloner.deepClone(prompt);
    return prompt;
  }
  public ReentrantReadWriteLock getDEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock(){
    Cloner cloner=new Cloner();
    DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock=cloner.deepClone(DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock);
    return DEBUG_JGFavgExpectedReturnRateMC_dataDirname_dataFilename_initAllTasks_initialised_nRunsMC_nTimeStepsMC_pathStartValue_prompt_results_tasksLock;
  }
  public RatePath getRateP(){
    Cloner cloner=new Cloner();
    rateP=cloner.deepClone(rateP);
    return rateP;
  }
  public PriceStock getPsMC(){
    Cloner cloner=new Cloner();
    psMC=cloner.deepClone(psMC);
    return psMC;
  }
  public ToInitAllTasks getInitAllTasks(){
    Cloner cloner=new Cloner();
    initAllTasks=cloner.deepClone(initAllTasks);
    return initAllTasks;
  }
}
