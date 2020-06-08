package edu.cmu.cs.crystal.internal;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.IAnalysisReporter;
import edu.cmu.cs.crystal.IRunCrystalCommand;
import edu.cmu.cs.crystal.StandardAnalysisReporter;
/** 
 * An action that will be called when a popup menu is used to run Crystal.
 */
public class CrystalFileAction implements IObjectActionDelegate {
  private ISelection selection;
  IRunCrystalCommand run_command=null;
  /** 
 * Constructor for Action1.
 */
  public CrystalFileAction(){
    super();
  }
  /** 
 * @see IObjectActionDelegate#setActivePart(IAction,IWorkbenchPart)
 */
  public void setActivePart(  IAction action,  IWorkbenchPart targetPart){
  }
  /** 
 * @see IActionDelegate#run(IAction)
 */
  public void run(  IAction action){
    List<ICompilationUnit> reanalyzeList=null;
    if (!selection.isEmpty()) {
      if (selection instanceof IStructuredSelection) {
        for (        Object element : ((IStructuredSelection)selection).toList()) {
          List<ICompilationUnit> temp=WorkspaceUtilities.collectCompilationUnits((IJavaElement)element);
          if (temp == null)           continue;
          if (reanalyzeList == null)           reanalyzeList=temp;
 else           reanalyzeList.addAll(temp);
        }
      }
    }
    if (reanalyzeList != null) {
      final Crystal crystal=AbstractCrystalPlugin.getCrystalInstance();
      final List<ICompilationUnit> compUnits=reanalyzeList;
      Job j=new Job("Crystal"){
        @Override protected IStatus run(        IProgressMonitor monitor){
          final Set<String> enabled=AbstractCrystalPlugin.getEnabledAnalyses();
          run_command=new IRunCrystalCommand(){
            public Set<String> analyses(){
              return enabled;
            }
            public List<ICompilationUnit> compilationUnits(){
              return compUnits;
            }
            public IAnalysisReporter reporter(){
              return new StandardAnalysisReporter();
            }
          }
;
          crystal.runAnalyses(run_command,monitor);
          if (monitor.isCanceled())           return Status.CANCEL_STATUS;
          return Status.OK_STATUS;
        }
      }
;
      j.setUser(true);
      j.schedule();
    }
  }
  /** 
 * @see IActionDelegate#selectionChanged(IAction,ISelection)
 */
  public void selectionChanged(  IAction action,  ISelection selection){
    this.selection=selection;
  }
  public IRunCrystalCommand getRun_command(){
    Cloner cloner=new Cloner();
    run_command=cloner.deepClone(run_command);
    Cloner cloner=new Cloner();
    run_command=cloner.deepClone(run_command);
    return run_command;
  }
  public ISelection getSelection(){
    Cloner cloner=new Cloner();
    selection=cloner.deepClone(selection);
    Cloner cloner=new Cloner();
    selection=cloner.deepClone(selection);
    return selection;
  }
}
