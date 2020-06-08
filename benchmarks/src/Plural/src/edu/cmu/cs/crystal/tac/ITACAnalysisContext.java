package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
/** 
 * This interface defines methods to map AST data structures to TAC variables. These methods can for instance be used by TAC analysis transfer functions.
 * @author Kevin Bierhoff
 * @see ITACTransferFunction
 * @see ITACBranchSensitiveTransferFunction
 */
public interface ITACAnalysisContext {
  /** 
 * Returns the TAC variable for a given ASTNode. It is the caller's responsibility to make sure to call this method only while the method surrounding the given node is analyzed.
 * @param node AST node in the previously analyzed method.
 * @return The TAC variable for a given ASTNode.
 */
  public Variable getVariable(  ASTNode node);
  /** 
 * Returns the <b>this</b> variable for the analyzed method.
 * @return The <b>this</b> variable for the analyzed method. 
 */
  public ThisVariable getThisVariable();
  /** 
 * Returns the <b>super</b> variable for the analyzed method, if any.
 * @return the <b>super</b> variable for the analyzed method or<code>null</code> if it doesn't exist.
 */
  public SuperVariable getSuperVariable();
  /** 
 * Returns the variable for a given parameter or local. It is the caller's responsibility to make sure to call this method only while the method declaring the parameter or local is analyzed.
 * @param varBinding Binding of a local or parameter.
 * @return the variable for the given parameter or local.
 */
  public SourceVariable getSourceVariable(  IVariableBinding varBinding);
  public MethodDeclaration getAnalyzedMethod();
}
