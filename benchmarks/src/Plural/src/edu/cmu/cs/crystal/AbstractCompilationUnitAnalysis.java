package edu.cmu.cs.crystal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
/** 
 * Carries out an analysis on each CompilationUnit.
 * @author David Dickey
 */
public abstract class AbstractCompilationUnitAnalysis implements ICrystalAnalysis {
  protected IAnalysisReporter reporter=null;
  protected IAnalysisInput analysisInput=null;
  /** 
 * This method is intended to be used to simply return an arbitrary name that can be used to help identify this analysis.
 * @return	a name
 */
  public String getName(){
    return reporter.getClass().getSimpleName();
  }
  /** 
 * Newest version of runAnalysis to run on a single compilation unit.
 * @param compUnit The ICompilationUnit that represents the file we are analyzing
 * @param reporter The IAnalysisReport that allows an analysis to report issues.
 * @param rootNode The ASTNode which represents this compilation unit.
 */
  public void runAnalysis(  IAnalysisReporter reporter,  IAnalysisInput input,  ICompilationUnit compUnit,  CompilationUnit rootNode){
    this.reporter=reporter;
    this.analysisInput=input;
    analyzeCompilationUnit(rootNode);
  }
  public void afterAllCompilationUnits(){
  }
  public void beforeAllCompilationUnits(){
  }
  /** 
 * Invoked once for each compilation unit.
 */
  public abstract void analyzeCompilationUnit(  CompilationUnit d);
  public IAnalysisInput getAnalysisInput(){
    Cloner cloner=new Cloner();
    analysisInput=cloner.deepClone(analysisInput);
    Cloner cloner=new Cloner();
    analysisInput=cloner.deepClone(analysisInput);
    return analysisInput;
  }
  public IAnalysisReporter getReporter(){
    Cloner cloner=new Cloner();
    reporter=cloner.deepClone(reporter);
    Cloner cloner=new Cloner();
    reporter=cloner.deepClone(reporter);
    return reporter;
  }
}
