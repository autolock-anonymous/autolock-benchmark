package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * This is an intermediate helper class that simplifies TAC generation. It should not be used by clients directly.
 * @author Kevin Bierhoff
 * @see EclipseTAC#variable(ASTNode)
 */
abstract class ResultfulInstruction<E extends ASTNode> extends AbstractTACInstruction<E> {
  /** 
 * Inherited constructor.
 * @param node
 * @param tac
 * @see AbstractTACInstruction#AbstractTACInstruction(ASTNode,IEclipseVariableQuery)
 */
  public ResultfulInstruction(  E node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * Returns the variable representing the result of this instruction that can be used as an operand to a subsequent instruction.
 * @return Variable representing the result of this instruction.
 */
  protected abstract Variable getResultVariable();
}
