package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ToInitAllTasks implements java.io.Serializable {
  public ReentrantReadWriteLock dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock=new ReentrantReadWriteLock();
  private String header;
  private String name;
  private int startDate;
  private int endDate;
  private double dTime;
  private int returnDefinition;
  private double expectedReturnRate;
  private double volatility;
  private int nTimeSteps;
  private double pathStartValue;
  @Perm(requires="unique(header) * unique(name) * unique(startDate) * unique(endDate) * unique(dTime) * unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathStartValue) in alive",ensures="unique(header) * unique(name) * unique(startDate) * unique(endDate) * unique(dTime) * unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathStartValue) in alive") public ToInitAllTasks(  String header,  String name,  int startDate,  int endDate,  double dTime,  int returnDefinition,  double expectedReturnRate,  double volatility,  double pathStartValue){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.header=header;
    this.name=name;
    this.startDate=startDate;
    this.endDate=endDate;
    this.dTime=dTime;
    this.returnDefinition=returnDefinition;
    this.expectedReturnRate=expectedReturnRate;
    this.volatility=volatility;
    this.nTimeSteps=nTimeSteps;
    this.pathStartValue=pathStartValue;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="none(pathStartValue) * unique(name) * unique(startDate) * unique(endDate) * unique(dTime) * unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathStartValue) * none(name) * none(startDate) * none(endDate) * none(dTime) in alive",ensures="none(pathStartValue) * unique(name) * unique(startDate) * unique(endDate) * unique(dTime) * unique(returnDefinition) * unique(expectedReturnRate) * unique(volatility) * unique(nTimeSteps) * unique(pathStartValue) * none(name) * none(startDate) * none(endDate) * none(dTime) in alive") public ToInitAllTasks(  ReturnPath obj,  int nTimeSteps,  double pathStartValue) throws DemoException {
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.name=obj.getname();
    this.startDate=obj.getstartDate();
    this.endDate=obj.getendDate();
    this.dTime=obj.getdTime();
    this.returnDefinition=obj.getreturnDefinition();
    this.expectedReturnRate=obj.getexpectedReturnRate();
    this.volatility=obj.getvolatility();
    this.nTimeSteps=nTimeSteps;
    this.pathStartValue=pathStartValue;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(header) in alive",ensures="pure(header) in alive") public String getheader(){
    Cloner cloner=new Cloner();
    header=cloner.deepClone(header);
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.header);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(header) in alive",ensures="full(header) in alive") public void setheader(  String header){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.header=header;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(name) in alive",ensures="pure(name) in alive") public String getname(){
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.name);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(name) in alive",ensures="full(name) in alive") public void setname(  String name){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.name=name;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(startDate) in alive",ensures="pure(startDate) in alive") public int getstartDate(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.startDate);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(startDate) in alive",ensures="full(startDate) in alive") public void setstartDate(  int startDate){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.startDate=startDate;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(endDate) in alive",ensures="pure(endDate) in alive") public int getendDate(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.endDate);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(endDate) in alive",ensures="full(endDate) in alive") public void setendDate(  int endDate){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.endDate=endDate;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(dTime) in alive",ensures="pure(dTime) in alive") public double getdTime(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.dTime);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(dTime) in alive",ensures="full(dTime) in alive") public void setDTime(  double dTime){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.dTime=dTime;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(returnDefinition) in alive",ensures="pure(returnDefinition) in alive") public int getreturnDefinition(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.returnDefinition);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(returnDefinition) in alive",ensures="full(returnDefinition) in alive") public void setReturnDefinition(  int returnDefinition){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.returnDefinition=returnDefinition;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(expectedReturnRate) in alive",ensures="pure(expectedReturnRate) in alive") public double getexpectedReturnRate(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.expectedReturnRate);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(expectedReturnRate) in alive",ensures="full(expectedReturnRate) in alive") public void setExpectedReturnRate(  double expectedReturnRate){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.expectedReturnRate=expectedReturnRate;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(volatility) in alive",ensures="pure(volatility) in alive") public double getvolatility(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.volatility);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(volatility) in alive",ensures="full(volatility) in alive") public void setVolatility(  double volatility){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.volatility=volatility;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(nTimeSteps) in alive",ensures="pure(nTimeSteps) in alive") public int getnTimeSteps(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.nTimeSteps);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(nTimeSteps) in alive",ensures="full(nTimeSteps) in alive") public void setnTimeSteps(  int nTimeSteps){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.nTimeSteps=nTimeSteps;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  @Perm(requires="pure(pathStartValue) in alive",ensures="pure(pathStartValue) in alive") public double getpathStartValue(){
    try {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().lock();
      return (this.pathStartValue);
    }
  finally {
      dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.readLock().unlock();
    }
  }
  @Perm(requires="full(pathStartValue) in alive",ensures="full(pathStartValue) in alive") public void setpathStartValue(  double pathStartValue){
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().lock();
    this.pathStartValue=pathStartValue;
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getDTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock(){
    Cloner cloner=new Cloner();
    dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock=cloner.deepClone(dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock);
    return dTime_endDate_expectedReturnRate_header_nTimeSteps_name_pathStartValue_returnDefinition_startDate_volatilityLock;
  }
}
