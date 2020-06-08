package edu.cmu.cs.crystal.tac;
/** 
 * This instruction indicates reading a variable that appears in the source program, i.e. the receiver, locals, or parameters.  This instruction is even generated when the source variable appears on the left-hand side of an assignment, to indicate that the variable is being touched. TODO Figure out if assignment targets should be a "SourceVariableRead" or not.
 * @author Kevin Bierhoff
 * @see AssignmentInstruction#getTarget()
 */
public interface SourceVariableRead extends TACInstruction {
  /** 
 * Returns the variable being read, of type   {@link SourceVariable} or {@link KeywordVariable}.
 * @return The variable being read, of type {@link SourceVariable} or {@link KeywordVariable}.
 */
  public Variable getVariable();
}
