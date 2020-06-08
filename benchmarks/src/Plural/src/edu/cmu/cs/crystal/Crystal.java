package edu.cmu.cs.crystal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import edu.cmu.cs.crystal.annotations.AnnotationDatabase;
import edu.cmu.cs.crystal.annotations.AnnotationFinder;
import edu.cmu.cs.crystal.annotations.ICrystalAnnotation;
import edu.cmu.cs.crystal.internal.ICrystalJob;
import edu.cmu.cs.crystal.internal.ISingleCrystalJob;
import edu.cmu.cs.crystal.internal.WorkspaceUtilities;
import edu.cmu.cs.crystal.tac.eclipse.CompilationUnitTACs;
import edu.cmu.cs.crystal.util.Option;
import edu.cmu.cs.crystal.util.Utilities;
/** 
 * Provides the ability to run the analyses. Provides output mechanisms for both the Static Analysis developer and the Static Analysis user. Also maintains several useful data structures. They can be accessed through several "get*" methods.
 * @author David Dickey
 * @author Jonathan Aldrich
 * @author cchristo
 * @author Nels E. Beckman
 */
public class Crystal {
static private class AnnoRegister {
    public String name;
    public boolean isMeta;
    public String getName(){
      Cloner cloner=new Cloner();
      name=cloner.deepClone(name);
      Cloner cloner=new Cloner();
      name=cloner.deepClone(name);
      return name;
    }
  }
  /** 
 * Currently unused default marker type for Crystal.
 * @see IMarker
 */
  public static final String MARKER_DEFAULT="edu.cmu.cs.crystal.crystalproblem";
  /** 
 * Currently unused marker attribute for markers of type   {@link #MARKER_DEFAULT}.
 */
  public static final String MARKER_ATTR_ANALYSIS="analysis";
  private static final Logger logger=Logger.getLogger(Crystal.class.getName());
  /** 
 * the list of analyses to perfrom
 */
  private LinkedList<ICrystalAnalysis> analyses;
  /** 
 * The names of the analyses that are enabled.
 */
  private Set<String> enabledAnalyses;
  /** 
 * Permanent registry for annotation parsers, populated at plugin initialization time.
 */
  private Map<AnnoRegister,Class<? extends ICrystalAnnotation>> annotationRegistry=new HashMap<AnnoRegister,Class<? extends ICrystalAnnotation>>();
  public Crystal(){
    analyses=new LinkedList<ICrystalAnalysis>();
    enabledAnalyses=new HashSet<String>();
  }
  /** 
 * Registers an analysis with the framework. All analyses must be registered in order for them to be invoked.
 * @param analysis the analysis to be used
 */
  public void registerAnalysis(  ICrystalAnalysis analysis){
    analyses.add(analysis);
    enabledAnalyses.add(analysis.getName());
  }
  /** 
 * Retrieves the declaring ASTNode of the binding. The first time this method is called, the mapping between bindings and nodes is created. The creation time will depend on the size of the workspace. Subsequent calls will simply look up the values from a mapping.
 * @param binding the binding from which you want the declaration
 * @return the declaration node
 */
  public ASTNode getASTNodeFromBinding(  IBinding binding){
    throw new UnsupportedOperationException("Retrieving AST nodes for bindings not supported");
  }
  public List<ICrystalAnalysis> getAnalyses(){
    Cloner cloner=new Cloner();
    analyses=cloner.deepClone(analyses);
    Cloner cloner=new Cloner();
    analyses=cloner.deepClone(analyses);
    return Collections.unmodifiableList(analyses);
  }
  public void runAnalyses(  IRunCrystalCommand command,  IProgressMonitor monitor){
    runCrystalJob(createJobFromCommand(command,monitor));
  }
  /** 
 * Run the crystal job. At the moment, this consists of calling the   {@code run} method on thejob parameter, but reserves the right to run many jobs in parallel.
 */
  private void runCrystalJob(  ICrystalJob job){
    job.runJobs();
  }
  /** 
 * Given a command to run some analyses on some compilation units, creates a job to run all of those analyses. This method does many of the things that runAnalysisOnMultiUnit and runAnalysisOnSingleUnit used to do, but now those activities are packaged up as ISingleCrystalJobs and in an ICrystalJob.
 * @throws IllegalArgumentException If any analysis name given doesn't exist!
 */
  private ICrystalJob createJobFromCommand(  final IRunCrystalCommand command,  final IProgressMonitor monitor){
    final int num_jobs=command.compilationUnits().size();
    final List<ISingleCrystalJob> jobs=new ArrayList<ISingleCrystalJob>(num_jobs);
    final List<ICrystalAnalysis> analyses_to_use=new ArrayList<ICrystalAnalysis>(command.analyses().size());
    for (    String analysis_name : command.analyses()) {
      Option<ICrystalAnalysis> analysis_=findAnalysisWithName(analysis_name);
      if (analysis_.isSome()) {
        analyses_to_use.add(analysis_.unwrap());
      }
 else {
        throw new IllegalArgumentException("Analysis with name \"" + analysis_name + "\" does not exist!");
      }
    }
    for (    final ICompilationUnit cu : command.compilationUnits()) {
      run(cu,null,monitor,command,analyses_to_use);
    }
    return createCrystalJobFromSingleJobs(command,monitor,num_jobs,jobs,analyses_to_use);
  }
  public void run(  final ICompilationUnit cu,  final AnnotationDatabase annoDB,  final IProgressMonitor monitor,  final IRunCrystalCommand command,  List<ICrystalAnalysis> analyses_to_use){
    if (cu == null) {
      if (logger.isLoggable(Level.WARNING))       logger.warning("Skipping null CompilationUnit");
    }
 else {
      if (monitor != null) {
        if (monitor.isCanceled())         return;
        monitor.subTask(cu.getElementName());
      }
      if (logger.isLoggable(Level.INFO))       logger.info("Running Crystal on: " + cu.getResource().getLocation().toOSString());
      CompilationUnit ast_comp_unit=(CompilationUnit)WorkspaceUtilities.getASTNodeFromCompilationUnit(cu);
      final CompilationUnitTACs compUnitTacs=new CompilationUnitTACs();
      if (monitor != null && monitor.isCanceled())       return;
      command.reporter().clearMarkersForCompUnit(cu);
      for (      ICrystalAnalysis analysis : analyses_to_use) {
        if (monitor != null && monitor.isCanceled())         return;
        IAnalysisInput input=new IAnalysisInput(){
          private Option<IProgressMonitor> mon=Option.wrap(monitor);
          public AnnotationDatabase getAnnoDB(){
            return annoDB;
          }
          public Option<CompilationUnitTACs> getComUnitTACs(){
            return Option.some(compUnitTacs);
          }
          public Option<IProgressMonitor> getProgressMonitor(){
            return mon;
          }
        }
;
        try {
          analysis.runAnalysis(command.reporter(),input,cu,ast_comp_unit);
        }
 catch (        CancellationException e) {
          if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE,"Ongoing Crystal analysis job canceled",e);
          }
 else           if (logger.isLoggable(Level.INFO)) {
            logger.info("Ongoing Crystal analysis job canceled");
          }
        }
      }
    }
    if (monitor != null && !monitor.isCanceled()) {
      monitor.worked(1);
    }
  }
  /** 
 * Given all of the single jobs, create the one analysis job. This basically packages the jobs into an interface, but it also runs the annotation finder. We may be getting rid of this pre-emptive annotation finder run soon.
 */
  private ICrystalJob createCrystalJobFromSingleJobs(  final IRunCrystalCommand command,  final IProgressMonitor monitor,  final int num_jobs,  final List<ISingleCrystalJob> jobs,  final List<ICrystalAnalysis> analyses_to_use){
    return new ICrystalJob(){
      public List<ISingleCrystalJob> analysisJobs(){
        return Collections.unmodifiableList(jobs);
      }
      public void runJobs(){
        if (monitor != null) {
          String task;
          if (num_jobs == 1)           task="Running Crystal on 1 compilation unit.";
 else           task="Running Crystal on " + num_jobs + " total compilation units.";
          monitor.beginTask(task,num_jobs);
        }
        AnnotationDatabase annoDB=new AnnotationDatabase();
        AnnotationFinder finder=new AnnotationFinder(annoDB);
        registerAnnotationsWithDatabase(annoDB);
        if (monitor != null)         monitor.subTask("Scanning annotations of analyzed compilation units");
        if (logger.isLoggable(Level.FINER))         logger.finer("Scanning annotations of analyzed compilation units");
        for (        ICompilationUnit compUnit : command.compilationUnits()) {
          if (compUnit == null)           continue;
          ASTNode node=WorkspaceUtilities.getASTNodeFromCompilationUnit(compUnit);
          if (monitor != null && monitor.isCanceled())           return;
          if (!(node instanceof CompilationUnit))           continue;
          IAnalysisInput input=new IAnalysisInput(){
            private AnnotationDatabase annoDB=new AnnotationDatabase();
            public AnnotationDatabase getAnnoDB(){
              return annoDB;
            }
            public Option<IProgressMonitor> getProgressMonitor(){
              return Option.none();
            }
            public Option<CompilationUnitTACs> getComUnitTACs(){
              return Option.none();
            }
          }
;
          finder.runAnalysis(command.reporter(),input,compUnit,(CompilationUnit)node);
        }
        for (        ICrystalAnalysis analysis : analyses_to_use) {
          analysis.beforeAllCompilationUnits();
        }
        for (        ISingleCrystalJob job : analysisJobs()) {
          if (monitor != null && monitor.isCanceled())           break;
          job.run(annoDB);
        }
        for (        ICrystalAnalysis analysis : analyses_to_use) {
          analysis.afterAllCompilationUnits();
        }
        if (monitor != null) {
          monitor.done();
        }
      }
    }
;
  }
  /** 
 * Register all of the annotations in the given annotation registry with the given annotation database.
 * @param annotationRegistry
 * @param annoDB
 */
  public void registerAnnotationsWithDatabase(  AnnotationDatabase annoDB){
    for (    Map.Entry<AnnoRegister,Class<? extends ICrystalAnnotation>> entry : annotationRegistry.entrySet()) {
      annoDB.register(entry.getKey().name,entry.getValue(),entry.getKey().isMeta);
    }
  }
  /** 
 * @param analysis_name
 * @return
 */
  private Option<ICrystalAnalysis> findAnalysisWithName(  String analysis_name){
    for (    ICrystalAnalysis analysis : this.getAnalyses()) {
      if (analysis.getName().equals(analysis_name))       return Option.some(analysis);
    }
    return Option.none();
  }
  public void registerAnnotation(  String annotationName,  Class<? extends ICrystalAnnotation> annoClass,  boolean parseAsMeta){
    AnnoRegister register=new AnnoRegister();
    register.isMeta=parseAsMeta;
    register.name=annotationName;
    annotationRegistry.put(register,annoClass);
  }
  public static String getMARKER_ATTR_ANALYSIS(){
    Cloner cloner=new Cloner();
    MARKER_ATTR_ANALYSIS=cloner.deepClone(MARKER_ATTR_ANALYSIS);
    Cloner cloner=new Cloner();
    MARKER_ATTR_ANALYSIS=cloner.deepClone(MARKER_ATTR_ANALYSIS);
    return MARKER_ATTR_ANALYSIS;
  }
  public Set<String> getEnabledAnalyses(){
    Cloner cloner=new Cloner();
    enabledAnalyses=cloner.deepClone(enabledAnalyses);
    Cloner cloner=new Cloner();
    enabledAnalyses=cloner.deepClone(enabledAnalyses);
    return enabledAnalyses;
  }
  public static String getMARKER_DEFAULT(){
    Cloner cloner=new Cloner();
    MARKER_DEFAULT=cloner.deepClone(MARKER_DEFAULT);
    Cloner cloner=new Cloner();
    MARKER_DEFAULT=cloner.deepClone(MARKER_DEFAULT);
    return MARKER_DEFAULT;
  }
  public static Logger getLogger(){
    Cloner cloner=new Cloner();
    logger=cloner.deepClone(logger);
    Cloner cloner=new Cloner();
    logger=cloner.deepClone(logger);
    return logger;
  }
  public Map<AnnoRegister,Class<? extends ICrystalAnnotation>> getAnnotationRegistry(){
    Cloner cloner=new Cloner();
    annotationRegistry=cloner.deepClone(annotationRegistry);
    Cloner cloner=new Cloner();
    annotationRegistry=cloner.deepClone(annotationRegistry);
    return annotationRegistry;
  }
}
