package edu.cmu.cs.crystal.tac;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * x = { y1, ..., yn }.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.ArrayInitializer
 */
public interface ArrayInitInstruction extends AssignmentInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.ArrayInitializer}. Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the operands initializing the array.  In  <code>x = { y1, ..., yn }</code>, this would be the list y1, ..., yn.
 * @return the operands initializing the array.
 */
  public List<Variable> getInitOperands();
}
