package edu.cmu.cs.crystal.internal;
import java.util.Collections;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.ICrystalAnalysis;
/** 
 * The dynamic menu populator for the "Crystal" menu that will add an item for each registered analysis. When   {@code getContributionItems} is called,it returns menu contributions, one for each analysis that is registered. These menu items are checkboxes, so they can be enabled or disabled.
 * @see edu.cmu.cs.crystal.internal.EnableAnalysisHandler
 * @author Nels E. Beckman
 */
public class AnalysisMenuPopulator extends CompoundContributionItem {
  public AnalysisMenuPopulator(){
  }
  public AnalysisMenuPopulator(  String id){
    super(id);
  }
  @Override protected IContributionItem[] getContributionItems(){
    final Crystal crystal=AbstractCrystalPlugin.getCrystalInstance();
    final IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    final int num_analyses=crystal.getAnalyses().size();
    IContributionItem[] result=new IContributionItem[num_analyses];
    int arr_index=0;
    for (    ICrystalAnalysis analysis : crystal.getAnalyses()) {
      final String analysis_name=analysis.getName();
      final CommandContributionItem item=new CommandContributionItem(window,null,"CrystalPlugin.enableanalysis",Collections.singletonMap("CrystalPlugin.analysisname",analysis_name),null,null,null,analysis_name,null,"Enable/Disable the Crystal analysis " + analysis_name,CommandContributionItem.STYLE_CHECK);
      result[arr_index]=item;
      arr_index++;
    }
    return result;
  }
}
