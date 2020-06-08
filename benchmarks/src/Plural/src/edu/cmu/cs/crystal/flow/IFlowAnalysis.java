package edu.cmu.cs.crystal.flow;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
/** 
 * All flow analyses must be able to return the information defined by this interface.
 * @author Nels Beckman
 * @date Jan 24, 2008
 * @param < LE >
 */
public interface IFlowAnalysis<LE extends LatticeElement<LE>> {
  /** 
 * Retrieves the analysis state that exists <b>before</b> analyzing the node. Before is respective to normal program flow and not the direction of the analysis.
 * @param node		the {@link ASTNode} of interest 
 * @return			the lattice that represents the analysis state before analyzing the node.  Or null if the node doesn't have a corresponding control flow node.
 */
  public LE getResultsBefore(  ASTNode node);
  /** 
 * Retrieves the analysis state that exists <b>after</b> analyzing the node. After is respective to normal program flow and not the direction of the analysis.
 * @param node		the {@link ASTNode} of interest 
 * @return			the lattice that represents the analysis state before analyzing the node.  Or null if the node doesn't have a corresponding control flow node.
 */
  public LE getResultsAfter(  ASTNode node);
  /** 
 * Get the analysis lattice before the first ASTNode in the CFG.
 * @param d Method declaration for the method you need the results for.
 */
  public LE getStartResults(  MethodDeclaration d);
  /** 
 * Get the analysis lattice after the last ASTNode in the CFG.
 * @param d Method declaration for the method you need the results for.
 */
  public LE getEndResults(  MethodDeclaration d);
  /** 
 * Retrieves the analysis state that exists <b>before</b> analyzing the node. Before is respective to normal program flow and not the direction of the analysis.
 * @param node		the {@link ASTNode} of interest 
 * @return			the lattice that represents the analysis state after analyzing the node.  Or null if the node doesn't have a corresponding control flow node.
 */
  public IResult<LE> getLabeledResultsBefore(  ASTNode node);
  /** 
 * Retrieves the analysis state that exists <b>after</b> analyzing the node. After is respective to normal program flow and not the direction of the analysis.
 * @param node		the {@link ASTNode} of interest 
 * @return			the lattice that represents the analysis state after analyzing the node.  Or null if the node doesn't have a corresponding control flow node.
 */
  public IResult<LE> getLabeledResultsAfter(  ASTNode node);
  /** 
 * Get the analysis lattice before the first ASTNode in the CFG, with labels.
 * @param d Method declaration for the method you need the results for.
 */
  public IResult<LE> getLabeledStartResult(  MethodDeclaration d);
  /** 
 * Get the analysis lattice after the last ASTNode in the CFG, which is a merge of all the actual end nodes, with labels.
 * @param d Method declaration for the method you need the results for.
 */
  public IResult<LE> getLabeledEndResult(  MethodDeclaration d);
}
