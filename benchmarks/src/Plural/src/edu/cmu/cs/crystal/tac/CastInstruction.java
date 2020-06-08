package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;
/** 
 * x = (T) y.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.CastExpression
 */
public interface CastInstruction extends OneOperandInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.CastExpression}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the type the   {@link #getOperand() operand} is being casted to.
 * @return the type the {@link #getOperand() operand} is being casted to.
 */
  public Type getCastToTypeNode();
}
