package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
/** 
 * T x.  This node represents the declaration of a variable in the source, i.e. a method parameter or local variable. Notice that temporary and keyword variables do <i>not</i> have an explicit declaration.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.VariableDeclaration
 */
public interface SourceVariableDeclaration extends TACInstruction {
  /** 
 * Returns the node this instruction is for.  Should be of type  {@link org.eclipse.jdt.core.dom.VariableDeclaration}.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Resolves the declared variable's binding.
 * @return the declared variable's binding.
 */
  public IVariableBinding resolveBinding();
  /** 
 * Returns the variable being declared.
 * @return The variable being declared.
 */
  public SourceVariable getDeclaredVariable();
  /** 
 * Is this variable being declared as the parameter to a catch block?
 * @return <code>true</code> if this variable is the parameter of a catch block.
 */
  public boolean isCaughtVariable();
  /** 
 * Is this variable being declared as a formal parameter to a method?
 * @return <code>true</code> if this is a formal parameter declaration,<code>false</code> otherwise.
 */
  public boolean isFormalParameter();
}
