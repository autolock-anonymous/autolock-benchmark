package edu.cmu.cs.crystal.flow;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
/** 
 * Interface for defining branch-sensitive flow analyses. To create a flow analysis, pass an instance of this interface to  {@link BranchSensitiveFlowAnalysis}.
 * @author Kevin Bierhoff
 */
public interface IBranchSensitiveTransferFunction<LE extends LatticeElement<LE>> extends IFlowAnalysisDefinition<LE> {
  /** 
 * Transfer over a given AST node and return analysis information for each possible outcome of the given node.  The list of labels specifies  the different kinds of branches leaving this node.  The result of this method must include analysis information for each of the given labels.
 * @param astNode The node to transfer over.
 * @param labels Branches leaving the given node.
 * @param value Incoming analysis information.
 * @return Analysis information after transferring over the given nodefor each of the given labels.
 */
  public IResult<LE> transfer(  ASTNode astNode,  List<ILabel> labels,  LE value);
}
