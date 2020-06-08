package jomp.montecarlo2.withlock;
import java.io.*;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class RatePath extends PathId {
  public static ReentrantReadWriteLock DEBUG_promptLock=new ReentrantReadWriteLock();
  public static ReentrantReadWriteLock NONCOMPOUNDEDLock=new ReentrantReadWriteLock();
  public static ReentrantReadWriteLock COMPOUNDEDLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock nAcceptedPathValue_pathDate_pathValueLock=new ReentrantReadWriteLock();
  public static boolean DEBUG=true;
  protected static String prompt="RatePath> ";
  public static int DATUMFIELD=4;
  public static final int MINIMUMDATE=19000101;
  public static final double EPSILON=10.0 * Double.MIN_VALUE;
  private double[] pathValue;
  private int[] pathDate;
  private int nAcceptedPathValue=0;
  @Perm(requires="none(prompt) * none(DEBUG) in alive",ensures="none(prompt) * none(DEBUG) in alive") public RatePath(  String filename) throws DemoException {
    setprompt(prompt);
    setDEBUG(DEBUG);
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    readRatesFile(null,filename);
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="unique(prompt) * unique(DEBUG) in alive",ensures="unique(prompt) * unique(DEBUG) in alive") public RatePath(  String dirName,  String filename) throws DemoException {
    DEBUG_promptLock.writeLock().lock();
    setprompt(prompt);
    setDEBUG(DEBUG);
    DEBUG_promptLock.writeLock().unlock();
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    readRatesFile(dirName,filename);
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="none(prompt) * none(DEBUG) * unique(pathValue) * unique(nAcceptedPathValue) in alive",ensures="none(prompt) * none(DEBUG) * unique(pathValue) * unique(nAcceptedPathValue) in alive") public RatePath(  double[] pathValue,  String name,  int startDate,  int endDate,  double dTime){
    setname(name);
    setstartDate(startDate);
    setendDate(endDate);
    setdTime(dTime);
    setprompt(prompt);
    setDEBUG(DEBUG);
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    this.pathValue=pathValue;
    this.nAcceptedPathValue=pathValue.length;
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="unique(pathValue) * unique(nAcceptedPathValue) * unique(pathDate) in alive",ensures="unique(pathValue) * unique(nAcceptedPathValue) * unique(pathDate) in alive") public RatePath(  MonteCarloPath mc) throws DemoException {
    setname(mc.getname());
    setstartDate(mc.getstartDate());
    setendDate(mc.getendDate());
    setdTime(mc.getdTime());
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    pathValue=mc.getpathValue();
    nAcceptedPathValue=mc.getnTimeSteps();
    pathDate=new int[nAcceptedPathValue];
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="none(prompt) * none(DEBUG) * unique(pathValue) * unique(nAcceptedPathValue) in alive",ensures="none(prompt) * none(DEBUG) * unique(pathValue) * unique(nAcceptedPathValue) in alive") public RatePath(  int pathValueLength,  String name,  int startDate,  int endDate,  double dTime){
    setname(name);
    setstartDate(startDate);
    setendDate(endDate);
    setdTime(dTime);
    setprompt(prompt);
    setDEBUG(DEBUG);
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    this.pathValue=new double[pathValueLength];
    this.nAcceptedPathValue=pathValue.length;
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="share(pathValue) in alive",ensures="share(pathValue) in alive") public void incpathValue(  double[] operandPath) throws DemoException {
    try {
      nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
      if (pathValue.length != operandPath.length)       throw new DemoException("The path to update has a different size to the path to update with!");
      for (int i=0; i < pathValue.length; i++)       pathValue[i]+=operandPath[i];
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
    }
  }
  @Perm(requires="share(pathValue) in alive",ensures="share(pathValue) in alive") public void incpathValue(  double scale) throws DemoException {
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    if (pathValue == null)     for (int i=0; i < pathValue.length; i++)     pathValue[i]*=scale;
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="pure(pathValue) in alive",ensures="pure(pathValue) in alive") public double[] getpathValue() throws DemoException {
    try {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().lock();
      if (this.pathValue == null)       throw new DemoException("Variable pathValue is undefined!");
      return (this.pathValue);
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().unlock();
    }
  }
  @Perm(requires="share(pathValue) in alive",ensures="share(pathValue) in alive") public void setpathValue(  double[] pathValue){
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    this.pathValue=pathValue;
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="pure(pathDate) in alive",ensures="pure(pathDate) in alive") public int[] getpathDate() throws DemoException {
    try {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().lock();
      if (this.pathDate == null)       throw new DemoException("Variable pathDate is undefined!");
      return (this.pathDate);
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().unlock();
    }
  }
  @Perm(requires="share(pathDate) in alive",ensures="share(pathDate) in alive") public void setpathDate(  int[] pathDate){
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    this.pathDate=pathDate;
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
  }
  @Perm(requires="pure(pathValue) in alive",ensures="pure(pathValue) in alive") public double getEndPathValue(){
    try {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().lock();
      return (getPathValue(pathValue.length - 1));
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(pathValue) in alive",ensures="pure(pathValue) in alive") public double getPathValue(  int index){
    try {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().lock();
      return (pathValue[index]);
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(pathValue) * pure(nAcceptedPathValue) * immutable(COMPOUNDED) in alive",ensures="pure(pathValue) * pure(nAcceptedPathValue) * immutable(COMPOUNDED) in alive") public ReturnPath getReturnCompounded() throws DemoException {
    try {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().lock();
      if (pathValue == null || nAcceptedPathValue == 0) {
        throw new DemoException("The Rate Path has not been defined!");
      }
      double[] returnPathValue=new double[nAcceptedPathValue];
      returnPathValue[0]=0.0;
      try {
        for (int i=1; i < nAcceptedPathValue; i++) {
          returnPathValue[i]=Math.log(pathValue[i] / pathValue[i - 1]);
        }
      }
 catch (      ArithmeticException aex) {
        throw new DemoException("Error in getReturnLogarithm:" + aex.toString());
      }
      ReturnPath rPath=new ReturnPath(returnPathValue,nAcceptedPathValue,ReturnPath.COMPOUNDED);
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().unlock();
    }
    rPath.copyInstanceVariables(this);
    rPath.estimatePath();
    return (rPath);
  }
  @Perm(requires="pure(pathValue) * pure(nAcceptedPathValue) * immutable(NONCOMPOUNDED) in alive",ensures="pure(pathValue) * pure(nAcceptedPathValue) * immutable(NONCOMPOUNDED) in alive") public ReturnPath getReturnNonCompounded() throws DemoException {
    try {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().lock();
      if (pathValue == null || nAcceptedPathValue == 0) {
        throw new DemoException("The Rate Path has not been defined!");
      }
      double[] returnPathValue=new double[nAcceptedPathValue];
      returnPathValue[0]=0.0;
      try {
        for (int i=1; i < nAcceptedPathValue; i++) {
          returnPathValue[i]=(pathValue[i] - pathValue[i - 1]) / pathValue[i];
        }
      }
 catch (      ArithmeticException aex) {
        throw new DemoException("Error in getReturnPercentage:" + aex.toString());
      }
      ReturnPath rPath=new ReturnPath(returnPathValue,nAcceptedPathValue,ReturnPath.NONCOMPOUNDED);
    }
  finally {
      nAcceptedPathValue_pathDate_pathValueLock.readLock().unlock();
    }
    rPath.copyInstanceVariables(this);
    rPath.estimatePath();
    return rPath;
  }
  @Perm(requires="unique(pathValue) * unique(pathDate) * full(nAcceptedPathValue) in alive",ensures="unique(pathValue) * unique(pathDate) * full(nAcceptedPathValue) in alive") private void readRatesFile(  String dirName,  String filename) throws DemoException {
    java.io.File ratesFile=new File(dirName,filename);
    java.io.BufferedReader in;
    if (!ratesFile.canRead()) {
      throw new DemoException("Cannot read the file " + ratesFile.toString());
    }
    try {
      in=new BufferedReader(new FileReader(ratesFile));
    }
 catch (    FileNotFoundException fnfex) {
      throw new DemoException(fnfex.toString());
    }
    int iLine=0, initNlines=100, nLines=0;
    String aLine;
    java.util.Vector allLines=new Vector(initNlines);
    try {
      while ((aLine=in.readLine()) != null) {
        iLine++;
        allLines.addElement(aLine);
      }
    }
 catch (    IOException ioex) {
      throw new DemoException("Problem reading data from the file " + ioex.toString());
    }
    nLines=iLine;
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().lock();
    this.pathValue=new double[nLines];
    this.pathDate=new int[nLines];
    nAcceptedPathValue=0;
    iLine=0;
    nAcceptedPathValue=iLine;
    setname(ratesFile.getName());
    setstartDate(pathDate[0]);
    setendDate(pathDate[nAcceptedPathValue - 1]);
    nAcceptedPathValue_pathDate_pathValueLock.writeLock().unlock();
    setdTime((double)(1.0 / 365.0));
  }
  public static String getPrompt(){
    Cloner cloner=new Cloner();
    prompt=cloner.deepClone(prompt);
    return prompt;
  }
  public ReentrantReadWriteLock getNAcceptedPathValue_pathDate_pathValueLock(){
    Cloner cloner=new Cloner();
    nAcceptedPathValue_pathDate_pathValueLock=cloner.deepClone(nAcceptedPathValue_pathDate_pathValueLock);
    return nAcceptedPathValue_pathDate_pathValueLock;
  }
  public static ReentrantReadWriteLock getCOMPOUNDEDLock(){
    Cloner cloner=new Cloner();
    COMPOUNDEDLock=cloner.deepClone(COMPOUNDEDLock);
    return COMPOUNDEDLock;
  }
  public static ReentrantReadWriteLock getDEBUG_promptLock(){
    Cloner cloner=new Cloner();
    DEBUG_promptLock=cloner.deepClone(DEBUG_promptLock);
    return DEBUG_promptLock;
  }
  public static ReentrantReadWriteLock getNONCOMPOUNDEDLock(){
    Cloner cloner=new Cloner();
    NONCOMPOUNDEDLock=cloner.deepClone(NONCOMPOUNDEDLock);
    return NONCOMPOUNDEDLock;
  }
}
