package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Type;
/** 
 * x = T.class.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.TypeLiteral
 */
public interface DotClassInstruction extends LoadInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.TypeLiteral}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns the type of the class object being loaded.
 * @return the type of the class object being loaded.
 */
  public Type getTypeNode();
}
