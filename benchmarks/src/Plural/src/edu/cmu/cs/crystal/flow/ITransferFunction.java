package edu.cmu.cs.crystal.flow;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * Interface for defining standard flow analyses.  Implement this interface directly or use   {@link FlowAnalysisDefinition}.  Use   {@link IBranchSensitiveTransferFunction}to define branch-sensitive analyses.  To create a flow analysis, pass an instance of this interface to   {@link FlowAnalysis}.
 * @author Kevin Bierhoff
 */
public interface ITransferFunction<LE extends LatticeElement<LE>> extends IFlowAnalysisDefinition<LE> {
  /** 
 * Transfer over a given AST node.  
 * @param astNode The node to transfer over.
 * @param value Incoming analysis information.
 * @return Analysis information after transferring over the given node.
 */
  public LE transfer(  ASTNode astNode,  LE value);
}
