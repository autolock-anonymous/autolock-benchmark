package edu.cmu.cs.crystal.internal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.ICrystalAnalysis;
import edu.cmu.cs.crystal.annotations.ICrystalAnnotation;
/** 
 * Provided Crystal plugin functionality
 * @author David Dickey
 * @author Jonathan Aldrich
 */
public abstract class AbstractCrystalPlugin extends AbstractUIPlugin {
  private static final Logger log=Logger.getLogger(AbstractCrystalPlugin.class.getName());
  Class<? extends ICrystalAnnotation> annoClass;
  /** 
 * Contains the names of all registered analyses, as well as a boolean indicating whether or not that analysis is enabled.
 */
  private static final Map<String,Boolean> registeredAnalyses=Collections.synchronizedMap(new LinkedHashMap<String,Boolean>());
  /** 
 * Returns the set of analyses that are enabled at the moment this method is called.
 */
  public static Set<String> getEnabledAnalyses(){
    Set<String> result=new LinkedHashSet<String>();
synchronized (registeredAnalyses) {
      for (      Map.Entry<String,Boolean> entry : registeredAnalyses.entrySet()) {
        if (entry.getValue())         result.add(entry.getKey());
      }
    }
    return result;
  }
  /** 
 * Add the given name to the set of analyses that are enabled. Note that if there is no analysis with this name, no error will be reported!
 */
  public static void enableAnalysis(  String analysis_name){
    registeredAnalyses.put(analysis_name,Boolean.TRUE);
  }
  /** 
 * Remove the given name from the set of analyses that are enabled. Note that if there is no analysis with this name, no error will be reported!
 */
  public static void disableAnalysis(  String analysis_name){
    registeredAnalyses.put(analysis_name,Boolean.FALSE);
  }
  /** 
 * This method is called upon plug-in activation. Used to initialize the plugin for first time use. Invokes setupCrystalAnalyses, which is overridden by CrystalPlugin.java to register any necessary analyses with the framework.
 */
  static private Crystal crystal;
  static public Crystal getCrystalInstance(){
synchronized (AbstractCrystalPlugin.class) {
      return crystal;
    }
  }
  @Override public void start(  BundleContext context) throws Exception {
    super.start(context);
synchronized (AbstractCrystalPlugin.class) {
      if (crystal == null)       crystal=new Crystal();
    }
    setupCrystalAnalyses(crystal);
    for (    IConfigurationElement config : Platform.getExtensionRegistry().getConfigurationElementsFor("edu.cmu.cs.crystal.CrystalAnalysis")) {
      if ("analysis".equals(config.getName()) == false) {
        if (log.isLoggable(Level.WARNING))         log.warning("Unknown CrystalAnalysis configuration element: " + config.getName());
        continue;
      }
      try {
        ICrystalAnalysis analysis=(ICrystalAnalysis)config.createExecutableExtension("class");
        if (log.isLoggable(Level.CONFIG))         log.config("Registering analysis extension: " + analysis.getName());
        crystal.registerAnalysis(analysis);
        registeredAnalyses.put(analysis.getName(),Boolean.TRUE);
      }
 catch (      CoreException e) {
        log.log(Level.SEVERE,"Problem with configured analysis: " + config.getValue(),e);
      }
    }
    for (    IConfigurationElement config : Platform.getExtensionRegistry().getConfigurationElementsFor("edu.cmu.cs.crystal.CrystalAnnotation")) {
      if ("customAnnotation".equals(config.getName()) == false) {
        if (log.isLoggable(Level.WARNING))         log.warning("Unknown CrystalAnnotation configuration element: " + config.getName());
        continue;
      }
      try {
        try {
          annoClass=(Class<? extends ICrystalAnnotation>)Class.forName(config.getAttribute("parserClass"));
        }
 catch (        ClassNotFoundException x) {
          if (log.isLoggable(Level.WARNING))           log.warning("Having classloader problems.  Try to add to your MANIFEST.MF: " + "\"Eclipse-RegisterBuddy: edu.cmu.cs.crystal\"");
          annoClass=((ICrystalAnnotation)config.createExecutableExtension("parserClass")).getClass();
          if (log.isLoggable(Level.WARNING))           log.warning("Recovered from problem loading class: " + config.getAttribute("parserClass"));
        }
        if (config.getChildren("sourceAnnotation").length == 0) {
          if (log.isLoggable(Level.WARNING))           log.warning("No @Annotation classes associated with parser: " + annoClass);
          continue;
        }
        for (        IConfigurationElement anno : config.getChildren("sourceAnnotation")) {
          if (log.isLoggable(Level.CONFIG))           log.config("Registering annotation: " + anno.getAttribute("annotationClass"));
          boolean parseAsMeta=Boolean.parseBoolean(anno.getAttribute("parseFromMeta"));
          crystal.registerAnnotation(anno.getAttribute("annotationClass"),annoClass,parseAsMeta);
        }
      }
 catch (      Throwable e) {
        log.log(Level.SEVERE,"Problem with configured annotation parser: " + config.getValue(),e);
      }
    }
  }
  public abstract void setupCrystalAnalyses(  Crystal crystal);
  public static Logger getLog(){
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    Cloner cloner=new Cloner();
    log=cloner.deepClone(log);
    return log;
  }
  public static Crystal getCrystal(){
    Cloner cloner=new Cloner();
    crystal=cloner.deepClone(crystal);
    Cloner cloner=new Cloner();
    crystal=cloner.deepClone(crystal);
    return crystal;
  }
  public Class<? extends ICrystalAnnotation> getAnnoClass(){
    Cloner cloner=new Cloner();
    annoClass=cloner.deepClone(annoClass);
    Cloner cloner=new Cloner();
    annoClass=cloner.deepClone(annoClass);
    return annoClass;
  }
  public static Map<String,Boolean> getRegisteredAnalyses(){
    Cloner cloner=new Cloner();
    registeredAnalyses=cloner.deepClone(registeredAnalyses);
    Cloner cloner=new Cloner();
    registeredAnalyses=cloner.deepClone(registeredAnalyses);
    return registeredAnalyses;
  }
}
