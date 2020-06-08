package edu.cmu.cs.crystal.flow.experimental;
import java.util.concurrent.CancellationException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.cfg.ICFGNode;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.ITransferFunction;
import edu.cmu.cs.crystal.flow.Lattice;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.flow.SingleResult;
/** 
 * This is the branch-<b>in</b>sensitive version of the worklist algorithm. Call   {@link #performAnalysis()} to run the worklist.
 * @author Kevin Bierhoff
 * @see #checkBreakpoint(ASTNode) for breakpoint support
 * @see BranchSensitiveWorklist
 */
public class BranchInsensitiveWorklist<LE extends LatticeElement<LE>> extends AbstractWorklist<LE> {
  /** 
 * The analysis-specific transfer function. 
 */
  private final ITransferFunction<LE> transferFunction;
  Object obj;
  /** 
 * Creates a worklist instance for the given method and transfer function.
 * @param method
 * @param def
 */
  public BranchInsensitiveWorklist(  MethodDeclaration method,  ITransferFunction<LE> def){
    super(method);
    this.transferFunction=def;
  }
  /** 
 * Creates a worklist instance for the given method and transfer function.
 * @param method
 * @param monitor Monitor that will be checked for cancellation
 * @param def
 */
  public BranchInsensitiveWorklist(  MethodDeclaration method,  IProgressMonitor monitor,  ITransferFunction<LE> def){
    super(method);
    this.transferFunction=def;
  }
  @Override protected AnalysisDirection getAnalysisDirection(){
    return transferFunction.getAnalysisDirection();
  }
  @Override protected Lattice<LE> getLattice(){
    return transferFunction.getLattice(getMethod());
  }
  @Override protected IResult<LE> transferNode(  ICFGNode cfgNode,  LE incoming,  ILabel transferLabel) throws CancellationException {
    obj=new Object();
    checkCancel();
    final ASTNode astNode=cfgNode.getASTNode();
    if (astNode == null) {
      return new SingleResult<LE>(incoming);
    }
    checkBreakpoint(astNode);
    return new SingleResult<LE>(transferFunction.transfer(astNode,incoming));
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public ITransferFunction<LE> getTransferFunction(){
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    return transferFunction;
  }
}
