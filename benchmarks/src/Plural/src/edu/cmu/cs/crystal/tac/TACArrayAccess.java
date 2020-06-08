package edu.cmu.cs.crystal.tac;
/** 
 * x[y]
 * @author Kevin Bierhoff
 * @since 6/13/2008
 */
public interface TACArrayAccess extends TACInstruction {
  /** 
 * Returns the array from which a cell is loaded.
 * @return the array from which a cell is loaded.
 */
  public Variable getAccessedArrayOperand();
  /** 
 * Returns the operand representing the index of the array access.
 * @return the operand representing the index of the array access.
 */
  public Variable getArrayIndex();
}
