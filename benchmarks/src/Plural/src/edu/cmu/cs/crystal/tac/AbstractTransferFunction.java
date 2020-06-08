package edu.cmu.cs.crystal.tac;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * Abstract base class for defining standard flow analyses based on 3-address code instructions (  {@link TACInstruction}).   It returns the incoming lattice element in all cases.   Override the <code>transfer</code> methods for instructions that your analysis needs to consider.
 * @author Kevin Bierhoff
 */
public abstract class AbstractTransferFunction<LE extends LatticeElement<LE>> implements ITACTransferFunction<LE> {
  private ITACAnalysisContext analysisContext;
  /** 
 * Default constructor.
 */
  public AbstractTransferFunction(){
    super();
  }
  /** 
 * Returns   {@link AnalysisDirection#FORWARD_ANALYSIS}.
 * @return {@link AnalysisDirection#FORWARD_ANALYSIS}.
 */
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
  public LE transferOver(  ArrayInitInstruction instr,  LE value){
    return value;
  }
  public LE transferOver2(  BinaryOperation binop,  LE value){
    return value;
  }
  public LE transferOver3(  CastInstruction instr,  LE value){
    return value;
  }
  public LE transferOver4(  DotClassInstruction instr,  LE value){
    return value;
  }
  public LE transferOver5(  ConstructorCallInstruction instr,  LE value){
    return value;
  }
  public LE transferOver6(  CopyInstruction instr,  LE value){
    return value;
  }
  public LE transferOver6(  EnhancedForConditionInstruction instr,  LE value){
    return value;
  }
  public LE transferOver7(  InstanceofInstruction instr,  LE value){
    return value;
  }
  public LE transferOver8(  LoadLiteralInstruction instr,  LE value){
    return value;
  }
  public LE transferOver9(  LoadArrayInstruction instr,  LE value){
    return value;
  }
  public LE transferOver10(  LoadFieldInstruction instr,  LE value){
    return value;
  }
  public LE transferOver11(  MethodCallInstruction instr,  LE value){
    return value;
  }
  public LE transferOver12(  NewArrayInstruction instr,  LE value){
    return value;
  }
  public LE transferOver13(  NewObjectInstruction instr,  LE value){
    return value;
  }
  public LE transferOver14(  ReturnInstruction instr,  LE value){
    return value;
  }
  public LE transferOver15(  StoreArrayInstruction instr,  LE value){
    return value;
  }
  public LE transferOver16(  StoreFieldInstruction instr,  LE value){
    return value;
  }
  public LE transferOver17(  SourceVariableDeclaration instr,  LE value){
    return value;
  }
  public LE transferOver18(  SourceVariableRead instr,  LE value){
    return value;
  }
  public LE transferOver19(  UnaryOperation unop,  LE value){
    return value;
  }
}
