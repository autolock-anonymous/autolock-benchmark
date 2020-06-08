package edu.cmu.cs.crystal.tac;
/** 
 * x = y.f, where f is a field.
 * @author Kevin Bierhoff
 */
public interface LoadFieldInstruction extends LoadInstruction, TACFieldAccess {
  /** 
 * Returns the object being read from. This method is equivalent to   {@link TACFieldAccess#getAccessedObjectOperand()}.
 * @return The object being read from.
 */
  public Variable getSourceObject();
}
