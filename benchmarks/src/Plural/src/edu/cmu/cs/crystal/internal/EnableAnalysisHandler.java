package edu.cmu.cs.crystal.internal;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;
import edu.cmu.cs.crystal.Crystal;
/** 
 * A handler for the "CrystalPlugin.enableanalysis" command. When this command is issued, this handler responds by either enabling or disabling the analysis that was passed as a parameter.
 * @author Nels E. Beckman
 */
public class EnableAnalysisHandler implements IHandler, IElementUpdater {
  public void addHandlerListener(  IHandlerListener handlerListener){
  }
  public void dispose(){
  }
  /** 
 * Will enable or disable an analysis based on which menu item was chosen.
 */
  public Object execute(  ExecutionEvent event) throws ExecutionException {
    final String analysis_name=event.getParameter("CrystalPlugin.analysisname");
    final Set<String> enabled=AbstractCrystalPlugin.getEnabledAnalyses();
    if (enabled.contains(analysis_name))     AbstractCrystalPlugin.disableAnalysis(analysis_name);
 else     AbstractCrystalPlugin.enableAnalysis(analysis_name);
    return null;
  }
  public boolean isEnabled(){
    return true;
  }
  public boolean isHandled(){
    return true;
  }
  public void removeHandlerListener(  IHandlerListener handlerListener){
  }
  /** 
 * Method called to determine if the checkbox next to an analysis name should be checked or not.
 */
  public void updateElement(  UIElement element,  Map parameters){
    final String analysis_name=(String)parameters.get("CrystalPlugin.analysisname");
    final Set<String> enabled=AbstractCrystalPlugin.getEnabledAnalyses();
    if (enabled.contains(analysis_name))     element.setChecked(true);
 else     element.setChecked(false);
  }
}
