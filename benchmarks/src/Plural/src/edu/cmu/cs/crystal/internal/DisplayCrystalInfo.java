package edu.cmu.cs.crystal.internal;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import edu.cmu.cs.crystal.ICrystalAnalysis;
/** 
 * Our sample action implements workbench action delegate. The action proxy will be created by the workbench and shown in the UI. When the user tries to use the action, this delegate will be created and execution will be  delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class DisplayCrystalInfo implements IWorkbenchWindowActionDelegate {
  private IWorkbenchWindow window;
  /** 
 * The constructor.
 */
  public DisplayCrystalInfo(){
  }
  /** 
 * The action has been activated. The argument of the method represents the 'real' action sitting in the workbench UI.
 * @see IWorkbenchWindowActionDelegate#run
 */
  public void run(  IAction action){
    String info="Registered Crystal analyses:";
    for (    ICrystalAnalysis a : AbstractCrystalPlugin.getCrystalInstance().getAnalyses()) {
      info+="\n" + a.getName();
    }
    MessageDialog.openInformation(window.getShell(),"Crystal Analyses",info);
  }
  /** 
 * Selection in the workbench has been changed. We  can change the state of the 'real' action here if we want, but this can only happen after  the delegate has been created.
 * @see IWorkbenchWindowActionDelegate#selectionChanged
 */
  public void selectionChanged(  IAction action,  ISelection selection){
  }
  /** 
 * We can use this method to dispose of any system resources we previously allocated.
 * @see IWorkbenchWindowActionDelegate#dispose
 */
  public void dispose(){
  }
  /** 
 * We will cache window object in order to be able to provide parent shell for the message dialog.
 * @see IWorkbenchWindowActionDelegate#init
 */
  public void init(  IWorkbenchWindow window){
    this.window=window;
  }
  public IWorkbenchWindow getWindow(){
    Cloner cloner=new Cloner();
    window=cloner.deepClone(window);
    Cloner cloner=new Cloner();
    window=cloner.deepClone(window);
    return window;
  }
}
