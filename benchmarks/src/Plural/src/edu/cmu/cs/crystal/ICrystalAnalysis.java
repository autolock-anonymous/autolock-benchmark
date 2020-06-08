package edu.cmu.cs.crystal;
import java.util.Map;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;
import edu.cmu.cs.crystal.annotations.AnnotationDatabase;
import edu.cmu.cs.crystal.annotations.CrystalAnnotation;
/** 
 * Presents the interface for an analysis.
 * @author David Dickey
 * @author Jonathan Aldrich
 */
public interface ICrystalAnalysis {
  /** 
 * Run the analysis!
 * @param reporter The object that is used to report errors. Output.
 * @param input The input to this analysis.
 * @param compUnit The compilation unit
 * @param rootNode The root ASTNode of the compilation unit
 */
  public void runAnalysis(  IAnalysisReporter reporter,  IAnalysisInput input,  ICompilationUnit compUnit,  CompilationUnit rootNode);
  /** 
 * @return a unique name for this analysis
 */
  public String getName();
  /** 
 * Inform the analysis that all compilation units have been analyzed.
 */
  public void afterAllCompilationUnits();
  /** 
 * Inform the analysis that the analysis process is about to begin.
 */
  public void beforeAllCompilationUnits();
}
