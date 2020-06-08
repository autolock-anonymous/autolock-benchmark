package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * Abstract x = call(z1, ..., zn).
 * @author Kevin Bierhoff
 */
public interface InvocationInstruction extends AssignmentInstruction, TACInvocation {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.Expression}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
}
