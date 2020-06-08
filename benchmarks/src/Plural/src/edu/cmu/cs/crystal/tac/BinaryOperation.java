package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * x = y binop z, representing <i>all</i> binary operations. Example:<br> <code>a = f + g;</code><br> To find out which type of binary operation this is, you have to call getOperator() and compare it with the   {@link BinaryOperator} enumerated type.<br>
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.InfixExpression
 */
public interface BinaryOperation extends AssignmentInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.Expression}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the first operand.
 * @return the first operand.
 */
  public abstract Variable getOperand1();
  /** 
 * Returns the binary operator.
 * @return The binary operator.
 */
  public BinaryOperator getOperator();
  /** 
 * Returns the second operand.
 * @return the second operand.
 */
  public abstract Variable getOperand2();
}
