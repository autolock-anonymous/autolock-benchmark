package edu.cmu.cs.crystal.flow;
import org.eclipse.jdt.core.dom.MethodDeclaration;
/** 
 * This class implements a standard flow analysis.   Implement   {@link ITransferFunction} or {@link IBranchSensitiveTransferFunction}and pass an instance to the respective constructor to create a specific flow analysis.
 * @author Kevin Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 * @see edu.cmu.cs.crystal.tac.BranchInsensitiveTACAnalysis
 */
public class FlowAnalysis<LE extends LatticeElement<LE>> extends MotherFlowAnalysis<LE> {
  protected IFlowAnalysisDefinition<LE> def;
  public FlowAnalysis(  ITransferFunction<LE> def){
    this.def=def;
  }
  public FlowAnalysis(  IBranchSensitiveTransferFunction<LE> def){
    this.def=def;
  }
  @Override protected IFlowAnalysisDefinition<LE> createTransferFunction(  MethodDeclaration method){
    return def;
  }
  public IFlowAnalysisDefinition<LE> getDef(){
    Cloner cloner=new Cloner();
    def=cloner.deepClone(def);
    Cloner cloner=new Cloner();
    def=cloner.deepClone(def);
    return def;
  }
}
