package edu.cmu.cs.crystal.internal;
import java.io.PrintWriter;
import java.util.logging.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.TextConsoleViewer;
import org.eclipse.ui.part.ViewPart;
/** 
 * This is a text console for users to submit messages to.
 * @author David Dickey
 */
public class UserConsoleView extends ViewPart {
  private static final Logger log=Logger.getLogger(UserConsoleView.class.getName());
  private static UserConsoleView instance=null;
  private TextConsoleViewer viewer;
  private IOConsole ioConsole;
  private IOConsoleOutputStream ioConsoleOutputStream;
  /** 
 * The constructor.
 */
  public UserConsoleView(){
    log.fine("UserConsoleView Instantiated");
    instance=this;
  }
  /** 
 * The instance of this singleton can be retrieved through this method.
 * @return	the one and only UserConsoleView
 */
  public static UserConsoleView getInstance(){
    Cloner cloner=new Cloner();
    instance=cloner.deepClone(instance);
    Cloner cloner=new Cloner();
    instance=cloner.deepClone(instance);
    return instance;
  }
  /** 
 * Called by the framework to open the view.
 */
  public void createPartControl(  Composite parent){
    ioConsole=new IOConsole("Crystal User Console",ImageDescriptor.getMissingImageDescriptor());
    viewer=new TextConsoleViewer(parent,ioConsole);
    ioConsoleOutputStream=ioConsole.newOutputStream();
    PrintWriter pw=getPrintWriter();
    pw.println("[userOut]");
  }
  /** 
 * Creates a PrintWriter object to allow for text to be printed to the console
 * @return	the PrintWriter corresponding to this console.  Or null ifthe console has not been properly setup.
 */
  public PrintWriter getPrintWriter(){
    if (ioConsoleOutputStream == null) {
      log.warning("The User Console has not been properly initiated.");
      return null;
    }
    return new PrintWriter(ioConsoleOutputStream,true);
  }
  /** 
 * Clears all text from the console
 */
  public void clearConsole(){
  }
  /** 
 * Provides the focus to the user console.
 */
  public void setFocus(){
    viewer.getControl().setFocus();
  }
  public static Logger getLog(){
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    return log;
  }
  public IOConsole getIoConsole(){
    Cloner cloner=new Cloner();
    ioConsole=cloner.deepClone(ioConsole);
    Cloner cloner=new Cloner();
    ioConsole=cloner.deepClone(ioConsole);
    return ioConsole;
  }
  public IOConsoleOutputStream getIoConsoleOutputStream(){
    Cloner cloner=new Cloner();
    ioConsoleOutputStream=cloner.deepClone(ioConsoleOutputStream);
    Cloner cloner=new Cloner();
    ioConsoleOutputStream=cloner.deepClone(ioConsoleOutputStream);
    return ioConsoleOutputStream;
  }
  public TextConsoleViewer getViewer(){
    Cloner cloner=new Cloner();
    viewer=cloner.deepClone(viewer);
    Cloner cloner=new Cloner();
    viewer=cloner.deepClone(viewer);
    return viewer;
  }
}
