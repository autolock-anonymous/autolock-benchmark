package edu.cmu.cs.crystal.internal;
import java.util.List;
import java.util.Set;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ICompilationUnit;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.IAnalysisReporter;
import edu.cmu.cs.crystal.IRunCrystalCommand;
import edu.cmu.cs.crystal.StandardAnalysisReporter;
/** 
 * A class that will handle the "CrystalPlugin.runcrystal" command. It handles this command by running Crystal.
 * @author Nels E. Beckman
 */
public class RunCrystalHandler implements IHandler {
  Job j;
  IRunCrystalCommand run_command=null;
  public Object execute(  ExecutionEvent event) throws ExecutionException {
    final Crystal crystal=AbstractCrystalPlugin.getCrystalInstance();
    j=new Job("Crystal"){
      @Override protected IStatus run(      IProgressMonitor monitor){
        final Set<String> enabled=AbstractCrystalPlugin.getEnabledAnalyses();
        final List<ICompilationUnit> cus=WorkspaceUtilities.scanForCompilationUnits();
        run_command=new IRunCrystalCommand(){
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
    return null;
  }
  public boolean isEnabled(){
    return true;
  }
  public boolean isHandled(){
    return true;
  }
  public void addHandlerListener(  IHandlerListener handlerListener){
  }
  public void removeHandlerListener(  IHandlerListener handlerListener){
  }
  public void dispose(){
  }
  public Job getJ(){
    Cloner cloner=new Cloner();
    j=cloner.deepClone(j);
    Cloner cloner=new Cloner();
    j=cloner.deepClone(j);
    return j;
  }
  public IRunCrystalCommand getRun_command(){
    Cloner cloner=new Cloner();
    run_command=cloner.deepClone(run_command);
    Cloner cloner=new Cloner();
    run_command=cloner.deepClone(run_command);
    return run_command;
  }
}
