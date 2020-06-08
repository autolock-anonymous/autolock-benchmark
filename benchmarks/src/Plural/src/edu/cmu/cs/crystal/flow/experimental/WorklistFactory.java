package edu.cmu.cs.crystal.flow.experimental;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.flow.IBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.flow.ITransferFunction;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.flow.MotherFlowAnalysis;
/** 
 * Factory for worklist objects to be used by flow analysis implementations. Worklist objects can perform conventional or branch-sensitive flow analyses.
 * @author Kevin Bierhoff
 * @see MotherFlowAnalysis
 */
public class WorklistFactory {
  private IProgressMonitor monitor;
  /** 
 * Default worklist factory.
 * @see #setMonitor(IProgressMonitor) to use a cancellation monitor.
 */
  public WorklistFactory(){
    this.monitor=null;
  }
  /** 
 * Use the given progress monitor to listen to cancellation in subsequently created worklist instances.
 * @param monitor Monitor to listen to cancellation or <code>null</code>if worklists should not be cancelled.
 */
  public void setMonitor(  IProgressMonitor monitor){
    this.monitor=monitor;
  }
  /** 
 * Creates a worklist object that performs a conventional flow analysis on the given method with the given transfer function.
 * @param < LE >
 * @param method
 * @param transferFunction
 * @return Worklist object that performs a conventional flow analysis.
 * @see {@link #createBranchSensitiveWorklist(MethodDeclaration,IBranchSensitiveTransferFunction)}
 */
  public <LE extends LatticeElement<LE>>WorklistTemplate<LE> createBranchInsensitiveWorklist(  MethodDeclaration method,  ITransferFunction<LE> transferFunction){
    return new BranchInsensitiveWorklist<LE>(method,monitor,transferFunction);
  }
  /** 
 * Creates a worklist object that performs a branch-sensitive flow analysis on the given method with the given transfer function.  Branch sensitivity means that the analysis will maintain separate information about different outcomes of a given AST node, such as boolean tests or exceptional control flow.
 * @param < LE >
 * @param method
 * @param transferFunction
 * @return Worklist object that performs a branch-sensitive flow analysis.
 */
  public <LE extends LatticeElement<LE>>WorklistTemplate<LE> createBranchSensitiveWorklist(  MethodDeclaration method,  IBranchSensitiveTransferFunction<LE> transferFunction){
    return new BranchSensitiveWorklist<LE>(method,monitor,transferFunction);
  }
  public IProgressMonitor getMonitor(){
    Cloner cloner=new Cloner();
    monitor=cloner.deepClone(monitor);
    Cloner cloner=new Cloner();
    monitor=cloner.deepClone(monitor);
    return monitor;
  }
}
