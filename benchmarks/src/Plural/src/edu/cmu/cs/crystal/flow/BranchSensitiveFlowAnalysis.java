package edu.cmu.cs.crystal.flow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrefixExpression;
import edu.cmu.cs.crystal.BooleanLabel;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.cfg.ICFGEdge;
import edu.cmu.cs.crystal.cfg.ICFGNode;
import edu.cmu.cs.crystal.cfg.IControlFlowGraph;
import edu.cmu.cs.crystal.cfg.eclipse.EclipseCFG;
/** 
 * This class implements a branch-sensitive flow analysis.  Implement   {@link IBranchSensitiveTransferFunction} to define a specific analysis.  
 * @author Kevin Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 * @see edu.cmu.cs.crystal.tac.BranchSensitiveTACAnalysis
 * @deprecated Use FlowAnalysis instead.
 */
public class BranchSensitiveFlowAnalysis<LE extends LatticeElement<LE>> extends MotherFlowAnalysis<LE> {
  protected IBranchSensitiveTransferFunction<LE> transferFunction;
  public BranchSensitiveFlowAnalysis(  Crystal crystal,  IBranchSensitiveTransferFunction<LE> transferFunction){
    super(crystal);
    this.transferFunction=transferFunction;
  }
  @Override protected IFlowAnalysisDefinition<LE> createTransferFunction(  MethodDeclaration method){
    return transferFunction;
  }
  public IBranchSensitiveTransferFunction<LE> getTransferFunction(){
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    return transferFunction;
  }
}
