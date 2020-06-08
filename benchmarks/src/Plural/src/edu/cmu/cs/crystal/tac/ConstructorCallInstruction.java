package edu.cmu.cs.crystal.tac;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
/** 
 * x(y1, ..., yn), where x is "this" or "super".  This instruction can by definition only occur in a constructor.  It does <b>not</b> have to be the first instruction in the constructor if arguments to the constructor call are computed with preceding instructions.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.ConstructorInvocation
 * @see org.eclipse.jdt.core.dom.SuperConstructorInvocation
 */
public interface ConstructorCallInstruction extends TACInvocation {
  /** 
 * Returns the node this instruction is for.  This should be one of the following types: <ul> <li>  {@link org.eclipse.jdt.core.dom.ConstructorInvocation}<li>  {@link org.eclipse.jdt.core.dom.SuperConstructorInvocation}</ul>  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment.
 * @return the node this instruction is for.
 * @see TACInstruction#getNode()
 */
  public ASTNode getNode();
  /** 
 * Returns variable for the object being constructed, i.e.,  {@link ThisVariable <code>this</code>} or {@link SuperVariable <code>super</code>}.
 * @return {@link ThisVariable <code>this</code>} or {@link SuperVariable <code>super</code>}.
 */
  public abstract KeywordVariable getConstructionObject();
  /** 
 * Indicates whether this is a super-constructor call or a call to a constructor in the same class as the surrounding constructor.  Thus, if this method returns <code>true</code> then   {@link #getConstructionObject()}will return   {@link SuperVariable <code>super</code>} andotherwise  {@link ThisVariable <code>this</code>}.
 * @return <code>true</code> if this is a super-constructorcall, <code>false</code> if this is a call to a constructor in the same class as the surrounding constructor.
 */
  public abstract boolean isSuperCall();
  public abstract List<Variable> getArgOperands();
  /** 
 * Indicates whether there is an   {@link #getEnclosingInstanceSpecifier() enclosing instance specifier} passed into the constructor call.{@link #getEnclosingInstanceSpecifier()} will only returna non-<code>null</code> value if this method returns <code>true</code>. 
 * @return <code>true</code> if there is an enclosing instancespecifier, <code>false</code> otherwise.
 */
  public abstract boolean hasEnclosingInstanceSpecifier();
  /** 
 * Specifier of an enclosing instance passed into the constructor, if any.  Please see the Java language specification for the semantics of providing an enclosing instance specifier with a constructor call.
 * @return an enclosing instance passed into theconstructor or <code>null</code> if there is none.
 */
  public abstract Variable getEnclosingInstanceSpecifier();
  public abstract IMethodBinding resolveBinding();
}
