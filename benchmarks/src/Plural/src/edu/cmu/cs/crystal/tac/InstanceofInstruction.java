package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;
/** 
 * x = y instanceof T.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.InstanceofExpression
 */
public interface InstanceofInstruction extends OneOperandInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.InstanceofExpression}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the type tested with <code>instanceof</code> 
 * @return the type tested with <code>instanceof</code>
 */
  public Type getTestedTypeNode();
}
