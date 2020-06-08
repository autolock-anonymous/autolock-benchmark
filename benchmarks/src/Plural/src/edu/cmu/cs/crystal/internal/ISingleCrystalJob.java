package edu.cmu.cs.crystal.internal;
import java.util.Set;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ICrystalAnalysis;
import edu.cmu.cs.crystal.annotations.AnnotationDatabase;
/** 
 * An interface for a single analysis job. A single analysis job is the running of any number of analysis on one compilation unit. In the future, this may also  manage dependencies in some more meaningful way.
 * @author Nels E. Beckman
 */
public interface ISingleCrystalJob {
  /** 
 * Run this single analysis. The given annotation database can be used to poll for annotations.
 */
  public void run(  AnnotationDatabase annoDB);
}
