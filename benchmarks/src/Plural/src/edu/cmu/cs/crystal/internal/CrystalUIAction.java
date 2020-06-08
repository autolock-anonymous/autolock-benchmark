package edu.cmu.cs.crystal.internal;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.IAnalysisReporter;
import edu.cmu.cs.crystal.IRunCrystalCommand;
import edu.cmu.cs.crystal.StandardAnalysisReporter;
/** 
 * Begins the execution of the Crystal framework when the corresponding GUI element is triggered. See the "plugin.xml" file for the mapping between GUI element and this class.
 * @author David Dickey
 */
public class CrystalUIAction implements IWorkbenchWindowActionDelegate {
  /** 
 * required by the IWorkbenchWindowActionDelegate interface
 */
  public void run(  IAction action){
    final Crystal crystal=AbstractCrystalPlugin.getCrystalInstance();
    Job j=new Job("Crystal"){
      @Override protected IStatus run(      IProgressMonitor monitor){
        final Set<String> enabled=AbstractCrystalPlugin.getEnabledAnalyses();
        final List<ICompilationUnit> cus=WorkspaceUtilities.scanForCompilationUnits();
        IRunCrystalCommand run_command=new IRunCrystalCommand(){
          public Set<String> analyses(){
            return enabled;
          }
          public List<ICompilationUnit> compilationUnits(){
            return cus;
          }
          public IAnalysisReporter reporter(){
            return new StandardAnalysisReporter();
          }
        }
;
        crystal.runAnalyses(run_command,monitor);
        if (monitor.isCanceled())         return Status.CANCEL_STATUS;
        return Status.OK_STATUS;
      }
    }
;
    j.setUser(true);
    j.schedule();
  }
  /** 
 * required by the IWorkbenchWindowActionDelegate interface
 */
  public void selectionChanged(  IAction action,  ISelection selection){
  }
  /** 
 * required by the IWorkbenchWindowActionDelegate interface
 */
  public void dispose(){
  }
  /** 
 * required by the IWorkbenchWindowActionDelegate interface
 */
  public void init(  IWorkbenchWindow window){
  }
}
