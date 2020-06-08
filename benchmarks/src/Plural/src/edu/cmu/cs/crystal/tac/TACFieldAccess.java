package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.IVariableBinding;
/** 
 * x.f, i.e., an access to a field.
 * @author Kevin Bierhoff
 */
public interface TACFieldAccess extends TACInstruction {
  /** 
 * Returns the name of the field being accessed.
 * @return the name of the field being accessed.
 */
  public String getFieldName();
  /** 
 * Returns the binding of the field being accessed.
 * @return the binding of the field being accessed.
 */
  public IVariableBinding resolveFieldBinding();
  /** 
 * Indicates whether this is an access to a static field  (including enum constants, I think).
 * @return <code>true</code> if a static field is accessed, <code>false</code> otherwise.
 */
  public boolean isStaticFieldAccess();
  /** 
 * Returns the object being accessed.
 * @return The object being accessed.
 */
  public Variable getAccessedObjectOperand();
}
