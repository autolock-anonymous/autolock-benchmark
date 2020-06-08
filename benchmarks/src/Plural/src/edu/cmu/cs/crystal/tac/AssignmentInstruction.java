package edu.cmu.cs.crystal.tac;
/** 
 * Abstract x = ?.
 * @author Kevin Bierhoff
 */
public interface AssignmentInstruction extends TACInstruction {
  /** 
 * Returns the target variable of this assignment.
 * @return the target variable of this assignment.
 */
  public Variable getTarget();
}
