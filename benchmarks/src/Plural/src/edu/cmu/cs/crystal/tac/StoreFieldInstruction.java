package edu.cmu.cs.crystal.tac;
/** 
 * x.f = y, where f is a field.
 * @author Kevin Bierhoff
 */
public interface StoreFieldInstruction extends StoreInstruction, TACFieldAccess {
  /** 
 * Returns the object being stored into. This method is equivalent to   {@link TACFieldAccess#getAccessedObjectOperand()}.
 * @return The object being stored into.
 */
  public Variable getDestinationObject();
}
