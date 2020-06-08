package edu.cmu.cs.crystal.tac;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * This class implements additional transfer functions that abstract or group other transfer functions according to the instruction hierarchy. This is convenient for analyses that want to treat many different but related instructions in the same way.
 * @author Jonathan Aldrich
 * @param < LE > LatticeElement subclass that represents the analysis knowledge.
 */
public abstract class AbstractingTransferFunction<LE extends LatticeElement<LE>> implements ITACTransferFunction<LE> {
  private ITACAnalysisContext analysisContext;
  public AnalysisDirection getAnalysisDirection(){
    return AnalysisDirection.FORWARD_ANALYSIS;
  }
  public ITACAnalysisContext getAnalysisContext(){
    Cloner cloner=new Cloner();
    analysisContext=cloner.deepClone(analysisContext);
    Cloner cloner=new Cloner();
    analysisContext=cloner.deepClone(analysisContext);
    return analysisContext;
  }
  public void setAnalysisContext(  ITACAnalysisContext analysisContext){
    this.analysisContext=analysisContext;
  }
  public LE transfer(  TACInstruction instr,  LE value){
    return value;
  }
  public LE transfer(  AssignmentInstruction instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transfer(  InvocationInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transfer(  OneOperandInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transfer(  StoreInstruction instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transferOver(  ArrayInitInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transferOver2(  BinaryOperation binop,  LE value){
    return transfer((AssignmentInstruction)binop,value);
  }
  public LE transferOver3(  CastInstruction instr,  LE value){
    return transfer((OneOperandInstruction)instr,value);
  }
  public LE transferOver4(  DotClassInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transferOver5(  ConstructorCallInstruction instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transferOver6(  CopyInstruction instr,  LE value){
    return transfer((OneOperandInstruction)instr,value);
  }
  public LE transferOver6(  EnhancedForConditionInstruction instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transferOver7(  InstanceofInstruction instr,  LE value){
    return transfer((OneOperandInstruction)instr,value);
  }
  public LE transferOver8(  LoadLiteralInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transferOver9(  LoadArrayInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transferOver10(  LoadFieldInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transferOver11(  MethodCallInstruction instr,  LE value){
    return transfer((InvocationInstruction)instr,value);
  }
  public LE transferOver12(  NewArrayInstruction instr,  LE value){
    return transfer((AssignmentInstruction)instr,value);
  }
  public LE transferOver13(  NewObjectInstruction instr,  LE value){
    return transfer((InvocationInstruction)instr,value);
  }
  public LE transferOver14(  ReturnInstruction instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transferOver15(  StoreArrayInstruction instr,  LE value){
    return transfer((StoreInstruction)instr,value);
  }
  public LE transferOver16(  StoreFieldInstruction instr,  LE value){
    return transfer((StoreInstruction)instr,value);
  }
  public LE transferOver17(  SourceVariableDeclaration instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transferOver18(  SourceVariableRead instr,  LE value){
    return transfer((TACInstruction)instr,value);
  }
  public LE transferOver19(  UnaryOperation unop,  LE value){
    return transfer((OneOperandInstruction)unop,value);
  }
}
