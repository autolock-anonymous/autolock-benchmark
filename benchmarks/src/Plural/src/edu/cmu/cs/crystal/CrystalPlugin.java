package edu.cmu.cs.crystal;
import java.util.logging.Logger;
import edu.cmu.cs.crystal.internal.AbstractCrystalPlugin;
/** 
 * The eclipse plugin that will launch and maintain the analysis. Eventually, this should be an extension point. For now, just plug in your analyses here.
 * @author David Dickey
 * @author Jonathan Aldrich
 */
public class CrystalPlugin extends AbstractCrystalPlugin {
  private static final Logger logger=Logger.getLogger(CrystalPlugin.class.getName());
  /** 
 * Modify this method to instantiate and register the analyses you want the framework to run.
 */
  public void setupCrystalAnalyses(  Crystal crystal){
    logger.info("CrystalPlugin::setupCrystalAnalyses() Begin");
    logger.info("CrystalPlugin::setupCrystalAnalyses() End");
  }
  public static Logger getLogger(){
    Cloner cloner=new Cloner();
    logger=cloner.deepClone(logger);
    Cloner cloner=new Cloner();
    logger=cloner.deepClone(logger);
    return logger;
  }
}
