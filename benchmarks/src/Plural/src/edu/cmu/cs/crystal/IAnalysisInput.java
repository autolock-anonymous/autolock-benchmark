package edu.cmu.cs.crystal;
import org.eclipse.core.runtime.IProgressMonitor;
import edu.cmu.cs.crystal.annotations.AnnotationDatabase;
import edu.cmu.cs.crystal.tac.eclipse.CompilationUnitTACs;
import edu.cmu.cs.crystal.util.Option;
/** 
 * This interface holds the input to an analysis.
 * @author Nels E. Beckman
 */
public interface IAnalysisInput {
  /** 
 * Every analysis is given an annotation database! Calling this method returns that annotation database.
 */
  public AnnotationDatabase getAnnoDB();
  /** 
 * If this analysis was given a CompilationUnitTACs cache as input, return it.
 */
  public Option<CompilationUnitTACs> getComUnitTACs();
  /** 
 * A progress monitor for canceling the ongoing analysis, if available.
 * @return A progress monitor for canceling the ongoinganalysis, or  {@link Option#none()} if it cannot be canceled.
 */
  public Option<IProgressMonitor> getProgressMonitor();
}
