package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * Interface used internally to represent field accesses, x.f. This helps dealing with the different possible representations of field accesses in the Eclipse AST (see   {@link org.eclipse.jdt.core.dom.FieldAccess}).
 * @author Kevin Bierhoff
 */
public interface IEclipseFieldAccess {
  /** 
 * Returns the name of the accessed field.
 * @return Name of the accessed field.
 */
  public abstract SimpleName getFieldName();
  /** 
 * Resolves the binding for the accessed field. Bindings can usually be resolved, but the underlying Eclipse AST admits the possiblity that <code>null</code> is returned.  
 * @return The binding for the accessed field or <code>null</code> ifthe binding could not be resolved.
 */
  public abstract IVariableBinding resolveFieldBinding();
  /** 
 * Returns the variable representing the target of the field access. The accessed object can be a type or instance variable.
 * @return the variable representing the target of the field access.
 */
  public abstract Variable getAccessedObject();
  /** 
 * Indicates whether this is an implicit access to a receiver field (which could actually be a field of an outer class).  Accessing a static field does <i>not</i> constitute an implicit access to the receiver in the sense of this method.
 * @return <code>true</code> if this is an implicit access to a receiver fieldand <code>false</code> otherwise.
 */
  public abstract boolean isImplicitThisAccess();
  /** 
 * Indicates whether this is an explicit super-field access, <code>super.f</code>. The field being accessed may be a static or instance field.
 * @return <code>true</code> if this is an explicit super-field access,<code>false</code> otherwise.
 */
  public abstract boolean isExplicitSuperAccess();
}
