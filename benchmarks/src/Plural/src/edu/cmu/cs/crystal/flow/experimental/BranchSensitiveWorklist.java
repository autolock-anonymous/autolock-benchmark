package edu.cmu.cs.crystal.flow.experimental;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CancellationException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrefixExpression;
import edu.cmu.cs.crystal.BooleanLabel;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.cfg.ICFGEdge;
import edu.cmu.cs.crystal.cfg.ICFGNode;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.IBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LabeledSingleResult;
import edu.cmu.cs.crystal.flow.Lattice;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * This is the branch-sensitive version of the worklist algorithm. Call   {@link #performAnalysis()} to run the worklist.
 * @author Kevin Bierhoff
 * @see #checkBreakpoint(ASTNode) for breakpoint support
 * @see BranchInsensitiveWorklist
 */
public class BranchSensitiveWorklist<LE extends LatticeElement<LE>> extends AbstractWorklist<LE> {
  /** 
 * The analysis-specific transfer function. 
 */
  private final IBranchSensitiveTransferFunction<LE> transferFunction;
  /** 
 * Cache of label lists for recently visited nodes. 
 */
  private final WeakHashMap<ICFGNode,List<ILabel>> labelMap=new WeakHashMap<ICFGNode,List<ILabel>>();
  Object obj=new Object();
  /** 
 * Creates a worklist instance for the given method and transfer function.
 * @param method
 * @param transfer
 */
  public BranchSensitiveWorklist(  MethodDeclaration method,  IBranchSensitiveTransferFunction<LE> transfer){
    super(method);
    this.transferFunction=transfer;
  }
  /** 
 * Creates a worklist instance for the given method and transfer function.
 * @param method
 * @param monitor Progress monitor that will be checked for cancellation.
 * @param transfer
 */
  public BranchSensitiveWorklist(  MethodDeclaration method,  IProgressMonitor monitor,  IBranchSensitiveTransferFunction<LE> transfer){
    super(method,monitor);
    this.transferFunction=transfer;
  }
  @Override protected AnalysisDirection getAnalysisDirection(){
    return transferFunction.getAnalysisDirection();
  }
  @Override protected Lattice<LE> getLattice(){
    return transferFunction.getLattice(getMethod());
  }
  @Override protected IResult<LE> transferNode(  ICFGNode cfgNode,  LE incoming,  ILabel transferLabel) throws CancellationException {
    checkCancel();
    obj=new Object();
    final ASTNode astNode=cfgNode.getASTNode();
    if (astNode == null) {
      return LabeledSingleResult.createResult(incoming,getLabels(cfgNode));
    }
    checkBreakpoint(astNode);
    if (transferLabel instanceof BooleanLabel) {
      if (astNode instanceof InfixExpression) {
        if (InfixExpression.Operator.CONDITIONAL_AND.equals(((InfixExpression)astNode).getOperator()) || InfixExpression.Operator.CONDITIONAL_OR.equals(((InfixExpression)astNode).getOperator()))         return transferFunction.transfer(astNode,Collections.singletonList(transferLabel),incoming);
      }
 else       if (astNode instanceof PrefixExpression) {
        if (PrefixExpression.Operator.NOT.equals(((PrefixExpression)astNode).getOperator())) {
          ILabel otherLabel=BooleanLabel.getBooleanLabel(!((BooleanLabel)transferLabel).getBranchValue());
          return transferFunction.transfer(astNode,Collections.singletonList(otherLabel),incoming);
        }
      }
    }
    return transferFunction.transfer(astNode,getLabels(cfgNode),incoming);
  }
  /** 
 * Returns the labels out of the given node (relative to the analysis direction)  as a list.
 * @param cfgNode
 * @return the labels out of the given node (relative to the analysis direction).
 */
  private List<ILabel> getLabels(  ICFGNode cfgNode){
    if (labelMap.containsKey(cfgNode))     return labelMap.get(cfgNode);
    Set<? extends ICFGEdge> edges=(AnalysisDirection.FORWARD_ANALYSIS == getAnalysisDirection() ? cfgNode.getOutputs() : cfgNode.getInputs());
    List<ILabel> labels=new LinkedList<ILabel>();
    for (    ICFGEdge e : edges) {
      if (labels.contains(e.getLabel()) == false)       labels.add(e.getLabel());
    }
    labels=Collections.unmodifiableList(labels);
    labelMap.put(cfgNode,labels);
    return labels;
  }
  public WeakHashMap<ICFGNode,List<ILabel>> getLabelMap(){
    Cloner cloner=new Cloner();
    labelMap=cloner.deepClone(labelMap);
    Cloner cloner=new Cloner();
    labelMap=cloner.deepClone(labelMap);
    return labelMap;
  }
  public IBranchSensitiveTransferFunction<LE> getTransferFunction(){
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    return transferFunction;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
