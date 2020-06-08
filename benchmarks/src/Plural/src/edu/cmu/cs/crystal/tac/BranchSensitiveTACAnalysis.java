package edu.cmu.cs.crystal.tac;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.eclipse.CompilationUnitTACs;
/** 
 * This class implements a branch-sensitive flow analysis over 3-address code instructions (  {@link TACInstruction}).  Implement   {@link ITACBranchSensitiveTransferFunction} todefine a specific analysis.
 * @author Kevin Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 * @see BranchInsensitiveTACAnalysis
 * @deprecated Use TACFlowAnalysis instead.
 */
public class BranchSensitiveTACAnalysis<LE extends LatticeElement<LE>> extends TACFlowAnalysis<LE> implements ITACAnalysisContext {
  /** 
 * Creates a branch-sensitive flow analysis with the given transfer function.
 * @param crystal
 * @param tf
 */
  public BranchSensitiveTACAnalysis(  ITACBranchSensitiveTransferFunction<LE> tf,  CompilationUnitTACs compUnitTacs){
    super(tf,compUnitTacs);
  }
}
