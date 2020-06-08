package edu.cmu.cs.crystal;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.internal.WorkspaceUtilities;
/** 
 * Responsible for carrying out the analysis logic on the methods of the target program.
 * @author David Dickey
 * @author Jonathan Aldrich
 */
public abstract class AbstractCrystalMethodAnalysis implements ICrystalAnalysis {
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
 * Carries out the analysis. <p>  {@link #beforeAllMethods()} is run before any method is analyzed.<br/>Then each method is analysed by  {@link #analyzeMethod(MethodDeclaration)}.<br/> Finally   {@link #afterAllMethods()} is run after all methods havebeen analyzed.
 */
  public final void runAnalysis(  IAnalysisReporter reporter,  IAnalysisInput input,  ICompilationUnit compUnit,  CompilationUnit rootNode){
    this.reporter=reporter;
    this.analysisInput=input;
    beforeAllMethods(compUnit,rootNode);
    List<MethodDeclaration> methods=WorkspaceUtilities.scanForMethodDeclarationsFromAST(rootNode);
    for (    MethodDeclaration md : methods) {
    }
    afterAllMethods(compUnit,rootNode);
  }
  public void afterAllCompilationUnits(){
  }
  public void beforeAllCompilationUnits(){
  }
  /** 
 * This method is invoked once before any methods are analyzed.  It can be used to perform pre-analysis functionality, if needed.
 */
  public void beforeAllMethods(  ICompilationUnit compUnit,  CompilationUnit rootNode){
  }
  /** 
 * Invoked for each method.
 */
  public abstract void analyzeMethod(  MethodDeclaration d);
  /** 
 * This method is invoked once after all methods are analyzed.  It can be used to perform post-analysis functionality, if needed.
 */
  public void afterAllMethods(  ICompilationUnit compUnit,  CompilationUnit rootNode){
  }
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
