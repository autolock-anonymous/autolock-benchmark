package edu.cmu.cs.crystal.tac;
/** 
 * <code>return</code> x, the return of a value.  This instruction is useful, among other things, for backwards analyses such as liveness to find the last variable being read.
 * @author Kevin Bierhoff
 * @since 3.3.2
 */
public interface ReturnInstruction extends TACInstruction {
  /** 
 * Returns the variable carrying the value being returned.
 * @return the variable carrying the value being returned.
 */
  public Variable getReturnedVariable();
}
