package edu.cmu.cs.crystal.tac.eclipse;
import java.util.Collections;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.NormalLabel;
import edu.cmu.cs.crystal.flow.AnalysisDirection;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LabeledSingleResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.TACInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
public class EclipseInstructionSequence extends ResultfulInstruction<ASTNode> {
  private final int useAsResult;
  private final TACInstruction[] instructions;
  public EclipseInstructionSequence(  ASTNode node,  TACInstruction[] instructions,  int useAsResult,  IEclipseVariableQuery tac){
    super(node,tac);
    if (instructions == null || useAsResult < 0 || useAsResult >= instructions.length) {
      throw new IllegalArgumentException("Illegal instruction sequence arguments");
    }
    if (!(instructions[useAsResult] instanceof ResultfulInstruction))     throw new IllegalArgumentException("Indicated instruction does not have result: " + instructions[useAsResult]);
    this.useAsResult=useAsResult;
    this.instructions=instructions;
  }
  public EclipseInstructionSequence(  ASTNode node,  TACInstruction[] instructions,  IEclipseVariableQuery tac){
    super(node,tac);
    if (instructions == null || instructions.length == 0) {
      throw new IllegalArgumentException("Illegal instruction sequence arguments");
    }
    if (!(instructions[instructions.length - 1] instanceof ResultfulInstruction))     throw new IllegalArgumentException("Last instruction does not have result: " + instructions[instructions.length - 1]);
    this.useAsResult=instructions.length - 1;
    this.instructions=instructions;
  }
  protected Variable getResultVariable(){
    return ((ResultfulInstruction<?>)instructions[useAsResult]).getResultVariable();
  }
  protected int getUseAsResult(){
    return useAsResult;
  }
  protected TACInstruction[] getInstructions(){
    return instructions;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    if (AnalysisDirection.FORWARD_ANALYSIS.equals(tf.getAnalysisDirection())) {
      for (      TACInstruction instr : instructions)       value=instr.transfer(tf,value);
    }
 else {
      for (int i=instructions.length - 1; i >= 0; i--)       value=instructions[i].transfer(tf,value);
    }
    return value;
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    ILabel normal=NormalLabel.getNormalLabel();
    List<ILabel> normalOnly=Collections.singletonList(normal);
switch (tf.getAnalysisDirection().toString()) {
case "FORWARD_ANALYSIS":
      for (int i=0; i < instructions.length - 1; i++) {
        IResult<LE> result=instructions[i].transfer(tf,normalOnly,value);
        value=result.get(normal);
      }
    return instructions[instructions.length - 1].transfer(tf,labels,value);
case "BACKWARD_ANALYSIS":
  for (int i=instructions.length - 1; i > 0; i--) {
    IResult<LE> result=instructions[i].transfer(tf,normalOnly,value);
    value=result.get(normal);
  }
return instructions[0].transfer(tf,labels,value);
default :
throw new UnsupportedOperationException("Unknown analysis direction: " + tf.getAnalysisDirection());
}
}
public <LE extends LatticeElement<LE>>LE deriveResult(ITACTransferFunction<LE> tf,TACInstruction targetInstr,LE value,boolean afterResult){
if (AnalysisDirection.FORWARD_ANALYSIS.equals(tf.getAnalysisDirection())) {
if (!afterResult && targetInstr == this) return value;
for (TACInstruction instr : instructions) {
if (!afterResult && instr == targetInstr) return value;
value=instr.transfer(tf,value);
if (afterResult && instr == targetInstr) return value;
}
if (afterResult && targetInstr == this) return value;
}
 else {
if (afterResult && targetInstr == this) return value;
for (int i=instructions.length - 1; i >= 0; i--) {
TACInstruction instr=instructions[i];
if (afterResult && instr == targetInstr) return value;
value=instr.transfer(tf,value);
if (!afterResult && instr == targetInstr) return value;
}
if (!afterResult && targetInstr == this) return value;
}
throw new IllegalArgumentException("Given instruction is unknown: " + targetInstr);
}
public <LE extends LatticeElement<LE>>IResult<LE> deriveResult(ITACBranchSensitiveTransferFunction<LE> tf,List<ILabel> labels,TACInstruction targetInstr,LE value,boolean afterResult){
ILabel normal=NormalLabel.getNormalLabel();
List<ILabel> normalOnly=Collections.singletonList(normal);
switch (tf.getAnalysisDirection().toString()) {
case "FORWARD_ANALYSIS":
if (!afterResult && targetInstr == this) return LabeledSingleResult.createResult(value,normalOnly);
for (int i=0; i < instructions.length - 1; i++) {
TACInstruction instr=instructions[i];
if (!afterResult && instr == targetInstr) return LabeledSingleResult.createResult(value,normalOnly);
IResult<LE> result=instr.transfer(tf,normalOnly,value);
if (afterResult && instr == targetInstr) return result;
value=result.get(normal);
}
TACInstruction last=instructions[instructions.length - 1];
if (!afterResult && last == targetInstr) return LabeledSingleResult.createResult(value,normalOnly);
 else if (afterResult && (last == targetInstr || this == targetInstr)) return last.transfer(tf,labels,value);
 else break;
case "BACKWARD_ANALYSIS":
if (afterResult && targetInstr == this) return LabeledSingleResult.createResult(value,normalOnly);
for (int i=instructions.length - 1; i > 0; i--) {
TACInstruction instr=instructions[i];
if (afterResult && instr == targetInstr) return LabeledSingleResult.createResult(value,normalOnly);
IResult<LE> result=instr.transfer(tf,normalOnly,value);
if (!afterResult && instr == targetInstr) return result;
value=result.get(normal);
}
TACInstruction instr=instructions[0];
if (afterResult && instr == targetInstr) return LabeledSingleResult.createResult(value,normalOnly);
 else if (!afterResult && (instr == targetInstr || this == targetInstr)) return instr.transfer(tf,labels,value);
 else break;
default :
throw new UnsupportedOperationException("Unknown analysis direction: " + tf.getAnalysisDirection());
}
throw new IllegalArgumentException("Given instruction is unknown: " + targetInstr);
}
}
