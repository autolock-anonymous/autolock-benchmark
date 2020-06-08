package edu.cmu.cs.plural.fractions.solver;
import edu.cmu.cs.plural.fractions.FractionConstraints;
/** 
 * @author Kevin Bierhoff
 * @since 5/16/2008
 */
public interface ConstraintsPrinter {
  /** 
 * Formats the given constraints as a string.
 * @param constraints
 * @param satisfiable <code>true</code> if known satisfiable,<code>false</code> if unsatisfiable, and <code>null</code> if unknown whether constraints are satisfiable or not.
 * @return
 */
  String toString(  FractionConstraints constraints,  Boolean satisfiable);
}
