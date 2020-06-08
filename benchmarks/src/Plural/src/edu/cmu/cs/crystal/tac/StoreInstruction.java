package edu.cmu.cs.crystal.tac;
/** 
 * This interface represents a <i>store</i>, i.e., a write into memory from a   {@link #getSourceOperand() source}.
 * @author Kevin Bierhoff
 * @see LoadInstruction
 */
public interface StoreInstruction extends TACInstruction {
  /** 
 * Returns the variable being stored.
 * @return the variable being stored.
 */
  public Variable getSourceOperand();
}
