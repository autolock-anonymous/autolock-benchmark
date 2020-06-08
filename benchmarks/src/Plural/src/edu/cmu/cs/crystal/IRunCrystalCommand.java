package edu.cmu.cs.crystal;
import java.util.Set;
import java.util.List;
import org.eclipse.jdt.core.ICompilationUnit;
/** 
 * A command to run certain crystal analyses on certain files. Should not require any internal knowledge of Crystal so that it can be created by Eclipse buttons and menus. Internally, Crystal will translate a command to run analyses into actual jobs.
 * @author Nels E. Beckman
 */
public interface IRunCrystalCommand {
  /** 
 * A set of analyses to run. Could be run in any order.
 */
  public Set<String> analyses();
  /** 
 * A list of compilation units that the analyses will be run on. Will be run in order.
 */
  public List<ICompilationUnit> compilationUnits();
  /** 
 * The reported that 
 */
  public IAnalysisReporter reporter();
}
