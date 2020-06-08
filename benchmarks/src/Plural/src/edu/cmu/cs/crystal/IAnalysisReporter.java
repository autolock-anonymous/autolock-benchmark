package edu.cmu.cs.crystal;
import java.io.PrintWriter;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * An interface that analyses use for reporting problems.
 * @author Nels E. Beckman
 */
public interface IAnalysisReporter {
  public enum SEVERITY;
{
  }
  /** 
 * Indicate that the running of this analysis on this compilation unit has created a problem. By default, this will have a severity level of "INFO".
 * @param problemDescription A textual description of the problem.
 * @param node The AST node where the problem was encountered.
 * @param analysisName The name of the Crystal analysis that is reporting the problem.
 */
  public void reportUserProblem(  String problemDescription,  ASTNode node,  String analysisName);
  /** 
 * Indicate that the running of this analysis on this compilation unit has created a problem.
 * @param problemDescription A textual description of the problem.
 * @param node The AST node where the problem was encountered.
 * @param analysisName The name of the Crystal analysis that is reporting the problem.
 * @param severity The severity level of this problem
 */
  public void reportUserProblem(  String problemDescription,  ASTNode node,  String analysisName,  SEVERITY severity);
  public PrintWriter debugOut();
  public PrintWriter userOut();
  /** 
 * For the given compilation unit, clear its markers from the screen, if necessary. Implementers are free to implement this method as necessary, including by doing nothing at all.
 */
  public void clearMarkersForCompUnit(  ICompilationUnit compUnit);
  public enum getSEVERITY(){
    Cloner cloner=new Cloner();
    SEVERITY=cloner.deepClone(SEVERITY);
  }
}
