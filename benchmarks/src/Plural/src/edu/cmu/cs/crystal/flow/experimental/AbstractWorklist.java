package edu.cmu.cs.crystal.flow.experimental;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.cfg.IControlFlowGraph;
import edu.cmu.cs.crystal.cfg.eclipse.EclipseNodeFirstCFG;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * @author Kevin Bierhoff
 */
public abstract class AbstractWorklist<LE extends LatticeElement<LE>> extends WorklistTemplate<LE> {
  private static final Logger log=Logger.getLogger(AbstractWorklist.class.getName());
  private final MethodDeclaration method;
  private final IProgressMonitor monitor;
  private int lastLine=-1;
  public AbstractWorklist(  MethodDeclaration method){
    this.method=method;
    this.monitor=null;
  }
  public AbstractWorklist(  MethodDeclaration method,  IProgressMonitor monitor){
    this.method=method;
    this.monitor=monitor;
  }
  @Override protected IControlFlowGraph getControlFlowGraph(){
    return new EclipseNodeFirstCFG(method);
  }
  /** 
 * Returns the analyzed method.
 * @return the analyzed method.
 */
  protected final MethodDeclaration getMethod(){
    Cloner cloner=new Cloner();
    method=cloner.deepClone(method);
    Cloner cloner=new Cloner();
    method=cloner.deepClone(method);
    return method;
  }
  /** 
 * Call this method to check if the given node hits a breakpoint. This method can be used by analysis writers during debugging. Setting a breakpoint at the point in the method that returns  <code>true</code> allows breaking into the debugger at a point in the analyzed code that was marked with a breakpoint in the child eclipse.  This method is probably not useful if Crystal is not  executed in debug mode.
 * @param node
 * @return <code>true</code> if the given node hits a breakpoint, <code>false</code> otherwise.
 */
  protected final boolean checkBreakpoint(  ASTNode node){
    if (node == null)     return false;
    final CompilationUnit compUnit=(CompilationUnit)node.getRoot();
    int nodeLine=compUnit.getLineNumber(node.getStartPosition());
    if (nodeLine < 0 || lastLine == nodeLine)     return false;
    lastLine=nodeLine;
    IResource r=compUnit.getJavaElement().getResource();
    try {
      for (      IMarker m : r.findMarkers(IBreakpoint.BREAKPOINT_MARKER,true,IResource.DEPTH_INFINITE)) {
        if (((Integer)m.getAttribute(IMarker.LINE_NUMBER,0)) == nodeLine) {
          if (log.isLoggable(Level.FINEST))           log.finest("Hit breakpoint in " + r.getName() + " line "+ nodeLine);
          return true;
        }
      }
    }
 catch (    CoreException e) {
      log.log(Level.WARNING,"Exception checking breakpoints for node " + node,e);
    }
    return false;
  }
  /** 
 * Call this method to check if the progress monitor was canceled. This method throws an exception if the monitor was canceled and returns normally otherwise.
 * @throws CancellationException If progress monitor was canceled.
 */
  protected final void checkCancel(){
    if (monitor != null && monitor.isCanceled())     throw new CancellationException("Crystal flow analysis was canceled");
  }
  public static Logger getLog(){
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    return log;
  }
  public IProgressMonitor getMonitor(){
    Cloner cloner=new Cloner();
    monitor=cloner.deepClone(monitor);
    Cloner cloner=new Cloner();
    monitor=cloner.deepClone(monitor);
    return monitor;
  }
}
