package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * x = unop y; this class represents unary operations.  Note that some seemingly unary operations such as x += y are   desugared into binary operations.   Pre- and post-increments and -decrements (++, --) are  desugared as well.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.PrefixExpression
 */
public interface UnaryOperation extends OneOperandInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.PrefixExpression}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the unary operator.
 * @return the unary operator.
 */
  public UnaryOperator getOperator();
}
