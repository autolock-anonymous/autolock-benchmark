package edu.cmu.cs.crystal.cfg;
import edu.cmu.cs.crystal.ILabel;
/** 
 * Abstract TODO: define subclasses for exceptions, normal, true, false, switch edges
 * @author aldrich
 */
public interface ICFGEdge {
  ICFGNode getSource();
  ICFGNode getSink();
  ILabel getLabel();
}
