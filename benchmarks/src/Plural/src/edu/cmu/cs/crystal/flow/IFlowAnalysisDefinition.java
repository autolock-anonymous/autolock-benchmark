package edu.cmu.cs.crystal.flow;
import org.eclipse.jdt.core.dom.MethodDeclaration;
/** 
 * @author Kevin Bierhoff
 * @param < LE >
 */
public interface IFlowAnalysisDefinition<LE extends LatticeElement<LE>> {
  /** 
 * Retrieves the lattice for a method.
 * @param methodDeclaration the method to get the entry lattice for
 * @return the entry lattice for the specified method
 */
  public Lattice<LE> getLattice(  MethodDeclaration methodDeclaration);
  /** 
 * Informs the FlowAnalysis which direction to perform the analysis. Default is a Forward analysis. <p> Use AnalysisDirection enumeration. 
 * @return	the direction of the analysis
 */
  public AnalysisDirection getAnalysisDirection();
}
