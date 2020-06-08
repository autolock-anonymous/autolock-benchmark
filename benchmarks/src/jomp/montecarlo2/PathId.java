package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class PathId extends Universal {
  public static ReentrantReadWriteLock promptLock=new ReentrantReadWriteLock();
  public static ReentrantReadWriteLock DEBUGLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock dTime_endDate_name_startDateLock=new ReentrantReadWriteLock();
  public static boolean DEBUG=true;
  protected static String prompt="PathId> ";
  private String name;
  private int startDate=0;
  private int endDate=0;
  private double dTime=Double.NaN;
  @Perm(requires="none(prompt) * none(DEBUG) in alive",ensures="none(prompt) * none(DEBUG) in alive") public PathId(){
    super();
    setprompt(prompt);
    setDEBUG(DEBUG);
  }
  @Perm(requires="none(prompt) * none(DEBUG) * unique(name) in alive",ensures="none(prompt) * none(DEBUG) * unique(name) in alive") public PathId(  String name){
    setprompt(prompt);
    setDEBUG(DEBUG);
    dTime_endDate_name_startDateLock.writeLock().lock();
    this.name=name;
    dTime_endDate_name_startDateLock.writeLock().unlock();
  }
  @Perm(requires="pure(name) in alive",ensures="pure(name) in alive") public String getname() throws DemoException {
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    try {
      dTime_endDate_name_startDateLock.readLock().lock();
      if (this.name == null)       throw new DemoException("Variable name is undefined!");
      return this.name;
    }
  finally {
      dTime_endDate_name_startDateLock.readLock().unlock();
    }
  }
  @Perm(requires="share(name) in alive",ensures="share(name) in alive") public void setname(  String name){
    dTime_endDate_name_startDateLock.writeLock().lock();
    this.name=name;
    dTime_endDate_name_startDateLock.writeLock().unlock();
  }
  @Perm(requires="pure(startDate) in alive",ensures="pure(startDate) in alive") public int getstartDate() throws DemoException {
    try {
      dTime_endDate_name_startDateLock.readLock().lock();
      if (this.startDate == 0)       throw new DemoException("Variable startDate is undefined!");
      return (this.startDate);
    }
  finally {
      dTime_endDate_name_startDateLock.readLock().unlock();
    }
  }
  @Perm(requires="share(startDate) in alive",ensures="share(startDate) in alive") public void setstartDate(  int startDate){
    dTime_endDate_name_startDateLock.writeLock().lock();
    this.startDate=startDate;
    dTime_endDate_name_startDateLock.writeLock().unlock();
  }
  @Perm(requires="pure(endDate) in alive",ensures="pure(endDate) in alive") public int getendDate() throws DemoException {
    try {
      dTime_endDate_name_startDateLock.readLock().lock();
      if (this.endDate == 0)       throw new DemoException("Variable endDate is undefined!");
      return (this.endDate);
    }
  finally {
      dTime_endDate_name_startDateLock.readLock().unlock();
    }
  }
  @Perm(requires="share(endDate) in alive",ensures="share(endDate) in alive") public void setendDate(  int endDate){
    dTime_endDate_name_startDateLock.writeLock().lock();
    this.endDate=endDate;
    dTime_endDate_name_startDateLock.writeLock().unlock();
  }
  @Perm(requires="pure(dTime) in alive",ensures="pure(dTime) in alive") public double getdTime() throws DemoException {
    try {
      dTime_endDate_name_startDateLock.readLock().lock();
      if (this.dTime == Double.NaN)       throw new DemoException("Variable dTime is undefined!");
      return (this.dTime);
    }
  finally {
      dTime_endDate_name_startDateLock.readLock().unlock();
    }
  }
  @Perm(requires="share(dTime) in alive",ensures="share(dTime) in alive") public void setdTime(  double dTime){
    dTime_endDate_name_startDateLock.writeLock().lock();
    this.dTime=dTime;
    dTime_endDate_name_startDateLock.writeLock().unlock();
  }
  @Perm(requires="share(name) * share(startDate) * share(endDate) * share(dTime) in alive",ensures="share(name) * share(startDate) * share(endDate) * share(dTime) in alive") public void copyInstanceVariables(  PathId obj) throws DemoException {
    dTime_endDate_name_startDateLock.writeLock().lock();
    this.name=obj.getname();
    this.startDate=obj.getstartDate();
    this.endDate=obj.getendDate();
    this.dTime=obj.getdTime();
    dTime_endDate_name_startDateLock.writeLock().unlock();
  }
  @Perm(requires="pure(name) * pure(startDate) * pure(endDate) * pure(dTime) in alive",ensures="pure(name) * pure(startDate) * pure(endDate) * pure(dTime) in alive") public void dbgDumpFields(){
    dTime_endDate_name_startDateLock.readLock().lock();
    dbgPrintln("name=" + this.name);
    dbgPrintln("startDate=" + this.startDate);
    dbgPrintln("endDate=" + this.endDate);
    dbgPrintln("dTime=" + this.dTime);
    dTime_endDate_name_startDateLock.readLock().unlock();
  }
  public static String getPrompt(){
    Cloner cloner=new Cloner();
    prompt=cloner.deepClone(prompt);
    return prompt;
  }
  public ReentrantReadWriteLock getDTime_endDate_name_startDateLock(){
    Cloner cloner=new Cloner();
    dTime_endDate_name_startDateLock=cloner.deepClone(dTime_endDate_name_startDateLock);
    return dTime_endDate_name_startDateLock;
  }
  public static ReentrantReadWriteLock getPromptLock(){
    Cloner cloner=new Cloner();
    promptLock=cloner.deepClone(promptLock);
    return promptLock;
  }
  public static ReentrantReadWriteLock getDEBUGLock(){
    Cloner cloner=new Cloner();
    DEBUGLock=cloner.deepClone(DEBUGLock);
    return DEBUGLock;
  }
}
