package edu.cmu.cs.crystal.cfg;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
/** 
 * Abstract
 * @author aldrich
 * @author cchristo
 */
public interface ICFGNode {
  public Set<? extends ICFGEdge> getInputs();
  public Set<? extends ICFGEdge> getOutputs();
  /** 
 * @returns The Node from which this CFGNode was created,may be null if this node is a dummy node. These nodes will still have input/output edges for control flow purposes.
 */
  public ASTNode getASTNode();
  public Set<? extends ICFGEdge> getInputEdges(  ILabel label);
  public Set<? extends ICFGEdge> getOutputEdges(  ILabel label);
}
