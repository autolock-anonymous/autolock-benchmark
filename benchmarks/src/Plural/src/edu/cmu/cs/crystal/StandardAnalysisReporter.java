package edu.cmu.cs.crystal;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import edu.cmu.cs.crystal.internal.UserConsoleView;
/** 
 * An analysis reporter to be used when running analyses through Eclipse. All methods will print/output to the standard locations in Eclipse.
 * @author Nels E. Beckman
 */
public class StandardAnalysisReporter implements IAnalysisReporter {
  public static final String REGRESSION_LOGGER="edu.cmu.cs.crystal.regression";
  private static final Logger logger=Logger.getLogger(Crystal.class.getName());
  private static final Logger regressionLogger=Logger.getLogger(REGRESSION_LOGGER);
  public void clearMarkersForCompUnit(  ICompilationUnit compUnit){
    try {
      compUnit.getResource().deleteMarkers(Crystal.MARKER_DEFAULT,true,IResource.DEPTH_INFINITE);
    }
 catch (    CoreException ce) {
      logger.log(Level.SEVERE,"CoreException when removing markers",ce);
    }
  }
  public PrintWriter debugOut(){
    return new PrintWriter(System.out,true);
  }
  public PrintWriter userOut(){
    UserConsoleView consoleView=UserConsoleView.getInstance();
    if (consoleView == null) {
      return new PrintWriter(System.out,true);
    }
    return consoleView.getPrintWriter();
  }
  public void reportUserProblem(  String problemDescription,  ASTNode node,  String analysisName){
    reportUserProblem(problemDescription,node,analysisName,SEVERITY.INFO);
  }
  public void reportUserProblem(  String problemDescription,  ASTNode node,  String analysisName,  SEVERITY severity){
    if (node == null)     throw new NullPointerException("null ASTNode argument in reportUserProblem");
    if (analysisName == null)     throw new NullPointerException("null analysis argument in reportUserProblem");
    if (logger.isLoggable(Level.FINE))     logger.fine("Reporting problem to user: " + problemDescription + "; node: "+ node);
    if (regressionLogger.isLoggable(Level.INFO)) {
      regressionLogger.info(problemDescription);
      regressionLogger.info(node.toString());
    }
    IResource resource;
    ASTNode root=node.getRoot();
    if (root.getNodeType() == ASTNode.COMPILATION_UNIT) {
      CompilationUnit cu=(CompilationUnit)root;
      IJavaElement je=cu.getJavaElement();
      resource=je.getResource();
    }
 else {
      resource=ResourcesPlugin.getWorkspace().getRoot();
    }
    int sevMarker;
    if (severity == SEVERITY.ERROR)     sevMarker=IMarker.SEVERITY_ERROR;
 else     if (severity == SEVERITY.WARNING)     sevMarker=IMarker.SEVERITY_WARNING;
 else     sevMarker=IMarker.SEVERITY_INFO;
    try {
      IMarker marker=resource.createMarker(Crystal.MARKER_DEFAULT);
      marker.setAttribute(IMarker.CHAR_START,node.getStartPosition());
      marker.setAttribute(IMarker.CHAR_END,node.getStartPosition() + node.getLength());
      marker.setAttribute(IMarker.MESSAGE,"[" + analysisName + "]: "+ problemDescription);
      marker.setAttribute(IMarker.PRIORITY,IMarker.PRIORITY_NORMAL);
      marker.setAttribute(IMarker.SEVERITY,sevMarker);
      marker.setAttribute(Crystal.MARKER_ATTR_ANALYSIS,analysisName);
      CompilationUnit cu=(CompilationUnit)node.getRoot();
      int line=cu.getLineNumber(node.getStartPosition());
      if (line >= 0)       marker.setAttribute(IMarker.LINE_NUMBER,line);
    }
 catch (    CoreException ce) {
      logger.log(Level.SEVERE,"CoreException when creating marker",ce);
    }
  }
  public static Logger getLogger(){
    Cloner cloner=new Cloner();
    logger=cloner.deepClone(logger);
    Cloner cloner=new Cloner();
    logger=cloner.deepClone(logger);
    return logger;
  }
  public static String getREGRESSION_LOGGER(){
    Cloner cloner=new Cloner();
    REGRESSION_LOGGER=cloner.deepClone(REGRESSION_LOGGER);
    Cloner cloner=new Cloner();
    REGRESSION_LOGGER=cloner.deepClone(REGRESSION_LOGGER);
    return REGRESSION_LOGGER;
  }
  public static Logger getRegressionLogger(){
    Cloner cloner=new Cloner();
    regressionLogger=cloner.deepClone(regressionLogger);
    Cloner cloner=new Cloner();
    regressionLogger=cloner.deepClone(regressionLogger);
    return regressionLogger;
  }
}
