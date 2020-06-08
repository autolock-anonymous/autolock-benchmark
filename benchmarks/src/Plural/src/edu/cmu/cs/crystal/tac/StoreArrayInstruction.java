package edu.cmu.cs.crystal.tac;
/** 
 * x[y] = z.
 * @author Kevin Bierhoff
 */
public interface StoreArrayInstruction extends StoreInstruction, TACArrayAccess {
  /** 
 * Returns the array being written into. This method is equivalent to   {@link TACArrayAccess#getAccessedArrayOperand()}
 * @return the array being written into.
 */
  public Variable getDestinationArray();
}
