package edu.cmu.cs.crystal.tac;
/** 
 * x = f(y), i.e., the result of a one-operand instruction is somehow derived from a single operand.
 * @author Kevin Bierhoff
 */
public interface OneOperandInstruction extends AssignmentInstruction {
  /** 
 * Returns the one operand to this one operand-instruction.
 * @return the one operand to this one operand-instruction.
 */
  public Variable getOperand();
}
