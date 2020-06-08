package edu.cmu.cs.crystal.analysis.alias;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LabeledSingleResult;
import edu.cmu.cs.crystal.flow.Lattice;
import edu.cmu.cs.crystal.flow.TupleLatticeElement;
import edu.cmu.cs.crystal.tac.AbstractTACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ArrayInitInstruction;
import edu.cmu.cs.crystal.tac.AssignmentInstruction;
import edu.cmu.cs.crystal.tac.BinaryOperation;
import edu.cmu.cs.crystal.tac.CastInstruction;
import edu.cmu.cs.crystal.tac.CopyInstruction;
import edu.cmu.cs.crystal.tac.DotClassInstruction;
import edu.cmu.cs.crystal.tac.InstanceofInstruction;
import edu.cmu.cs.crystal.tac.LoadArrayInstruction;
import edu.cmu.cs.crystal.tac.LoadFieldInstruction;
import edu.cmu.cs.crystal.tac.LoadLiteralInstruction;
import edu.cmu.cs.crystal.tac.MethodCallInstruction;
import edu.cmu.cs.crystal.tac.NewArrayInstruction;
import edu.cmu.cs.crystal.tac.NewObjectInstruction;
import edu.cmu.cs.crystal.tac.SourceVariableDeclaration;
import edu.cmu.cs.crystal.tac.TACInstruction;
import edu.cmu.cs.crystal.tac.UnaryOperation;
import edu.cmu.cs.crystal.tac.Variable;
public class MayAliasTransferFunction extends AbstractTACBranchSensitiveTransferFunction<TupleLatticeElement<Variable,AliasLE>> {
  private Map<Variable,ObjectLabel> labelContext;
  private MayAliasAnalysis mainAnalysis;
  public MayAliasTransferFunction(  MayAliasAnalysis analysis){
    labelContext=new HashMap<Variable,ObjectLabel>();
    this.mainAnalysis=analysis;
  }
  public Lattice<TupleLatticeElement<Variable,AliasLE>> getLattice(  MethodDeclaration d){
    TupleLatticeElement<Variable,AliasLE> entry=new TupleLatticeElement<Variable,AliasLE>(new AliasLE(),new AliasLE());
    Variable thisVar=mainAnalysis.getThisVar(d);
    AliasLE alias=new AliasLE();
    return new Lattice<TupleLatticeElement<Variable,AliasLE>>(entry,entry.bottom());
  }
  private ObjectLabel getLabel(  Variable associatedVar,  ITypeBinding binding,  TACInstruction declaringInstr){
    if (labelContext.get(associatedVar) != null) {
      return labelContext.get(associatedVar);
    }
 else {
      boolean isInLoop=mainAnalysis.isInLoop(declaringInstr);
      ObjectLabel label=new DefaultObjectLabel(binding,isInLoop);
      labelContext.put(associatedVar,label);
      return label;
    }
  }
  private TupleLatticeElement<Variable,AliasLE> putSingletonLabel(  AssignmentInstruction instr,  TupleLatticeElement<Variable,AliasLE> value){
    ObjectLabel label=getLabel(instr.getTarget(),instr.getTarget().resolveType(),instr);
    AliasLE aliases=createSingletonLE(label);
    value.put(instr.getTarget(),aliases);
    return value;
  }
  private static AliasLE createSingletonLE(  ObjectLabel label){
    AliasLE result=AliasLE.createOverload(label);
    return result;
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  ArrayInitInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  BinaryOperation binop,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(binop,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  CastInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    value.put(instr.getTarget(),value.get(instr.getOperand()).copy());
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  DotClassInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  CopyInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    value.put(instr.getTarget(),value.get(instr.getOperand()).copy());
    return LabeledSingleResult.createResult(value,labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  InstanceofInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  LoadLiteralInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  LoadArrayInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  LoadFieldInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  MethodCallInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  NewArrayInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  NewObjectInstruction instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(instr,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  UnaryOperation unop,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    return LabeledSingleResult.createResult(putSingletonLabel(unop,value),labels);
  }
  public IResult<TupleLatticeElement<Variable,AliasLE>> transfer(  SourceVariableDeclaration instr,  List<ILabel> labels,  TupleLatticeElement<Variable,AliasLE> value){
    if (instr.isFormalParameter()) {
      ObjectLabel label=getLabel(instr.getDeclaredVariable(),instr.resolveBinding().getType(),instr);
      AliasLE aliases=createSingletonLE(label);
      value.put(instr.getDeclaredVariable(),aliases);
    }
    return LabeledSingleResult.createResult(value,labels);
  }
  public MayAliasAnalysis getMainAnalysis(){
    Cloner cloner=new Cloner();
    mainAnalysis=cloner.deepClone(mainAnalysis);
    Cloner cloner=new Cloner();
    mainAnalysis=cloner.deepClone(mainAnalysis);
    return mainAnalysis;
  }
  public Map<Variable,ObjectLabel> getLabelContext(){
    Cloner cloner=new Cloner();
    labelContext=cloner.deepClone(labelContext);
    Cloner cloner=new Cloner();
    labelContext=cloner.deepClone(labelContext);
    return labelContext;
  }
}
