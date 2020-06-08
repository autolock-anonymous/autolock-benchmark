package edu.cmu.cs.crystal.tac;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.eclipse.CompilationUnitTACs;
/** 
 * This class implements a standard flow analysis over 3-address code instructions (  {@link TACInstruction}).  Implement   {@link ITACTransferFunction} todefine a specific analysis.  For branch-sensitivity,  use  {@link BranchSensitiveTACAnalysis}. 
 * @author Kevin Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 * @see BranchInsensitiveTACAnalysis
 * @deprecated Use TACFlowAnalysis instead
 */
public class BranchInsensitiveTACAnalysis<LE extends LatticeElement<LE>> extends TACFlowAnalysis<LE> implements ITACAnalysisContext {
  /** 
 * Creates a standard flow analysis with the given transfer function.
 * @param crystal
 * @param tf
 */
  public BranchInsensitiveTACAnalysis(  Crystal crystal,  ITACTransferFunction<LE> tf,  CompilationUnitTACs compUnitTacs){
    super(crystal,tf,compUnitTacs);
  }
}
