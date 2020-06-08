package edu.cmu.cs.crystal.tac;
import java.util.List;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LabeledSingleResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * Abstract base class for defining branch-sensitive flow analyses  based on 3-address code instructions (  {@link TACInstruction}).   It returns   {@link LabeledSingleResult}s in all cases.   Override the <code>transfer</code> methods for instructions that your analysis needs to consider.
 * @author Kevin Bierhoff
 * @param < LE > LatticeElement subclass that represents the analysis knowledge.
 */
public abstract class AbstractTACBranchSensitiveTransferFunction<LE extends LatticeElement<LE>> implements ITACBranchSensitiveTransferFunction<LE> {
  private ITACAnalysisContext analysisContext;
  /** 
 * Default constructor.
 */
  public AbstractTACBranchSensitiveTransferFunction(){
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
  public IResult<LE> transferOver2(  ArrayInitInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transferOver4(  BinaryOperation binop,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transferO1(  CastInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transferOverload4(  DotClassInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  ConstructorCallInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  CopyInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  EnhancedForConditionInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  InstanceofInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  LoadLiteralInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  LoadArrayInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  LoadFieldInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  MethodCallInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  NewArrayInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  NewObjectInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  ReturnInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  StoreArrayInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  StoreFieldInstruction instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  SourceVariableDeclaration instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  SourceVariableRead instr,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<LE> transfer(  UnaryOperation unop,  List<ILabel> labels,  LE value){
    return LabeledSingleResult.createResult(value,labels);
  }
}
