package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * x = y[z].
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.ArrayAccess
 */
public interface LoadArrayInstruction extends LoadInstruction, TACArrayAccess {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.ArrayAccess}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the array from which a cell is loaded. This method is equivalent to   {@link TACArrayAccess#getAccessedArrayOperand()}.
 * @return the array from which a cell is loaded.
 */
  public Variable getSourceArray();
}
