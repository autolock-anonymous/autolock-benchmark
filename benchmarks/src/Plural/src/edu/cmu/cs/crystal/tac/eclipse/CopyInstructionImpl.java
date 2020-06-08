package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.CopyInstruction;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = y.
 * @author Kevin Bierhoff
 */
class CopyInstructionImpl extends AbstractAssignmentInstruction<ASTNode> implements CopyInstruction {
  private Variable operand;
  private boolean operandIsResult;
  Object obj;
  /** 
 * Makes the   {@link #getTarget() target} be the result.
 * @param node
 * @param operand the operand.
 * @param tac
 * @see #CopyInstructionImpl(ASTNode,Variable,boolean,IEclipseVariableQuery)
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(ASTNode,IEclipseVariableQuery)
 */
  public CopyInstructionImpl(  ASTNode node,  Variable operand,  IEclipseVariableQuery tac){
    super(node,tac);
    this.operand=operand;
  }
  /** 
 * @param node
 * @param operand the operand.
 * @param operandIsResult <code>true</code> makes the operand be the {@link #getResultVariable() result} of this instruction, <code>false</code>makes the  {@link #getTarget() target} be the result (default). 
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(ASTNode,IEclipseVariableQuery)
 */
  public CopyInstructionImpl(  ASTNode node,  Variable operand,  boolean operandIsResult,  IEclipseVariableQuery tac){
    super(node,tac);
    this.operand=operand;
    this.operandIsResult=operandIsResult;
  }
  /** 
 * Makes the   {@link #getTarget() target} be the result.
 * @param node
 * @param operand the operand.
 * @param target
 * @param tac
 * @see #CopyInstructionImpl(ASTNode,Variable,boolean,Variable,IEclipseVariableQuery)
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(ASTNode,Variable,IEclipseVariableQuery)
 */
  public CopyInstructionImpl(  ASTNode node,  Variable operand,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
    this.operand=operand;
  }
  /** 
 * @param node
 * @param operand the operand.
 * @param operandIsResult <code>true</code> makes the operand be the {@link #getResultVariable() result} of this instruction, <code>false</code>makes the  {@link #getTarget() target} be the result (default).
 * @param target 
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(ASTNode,IEclipseVariableQuery)
 */
  public CopyInstructionImpl(  ASTNode node,  Variable operand,  boolean operandIsResult,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
    this.operand=operand;
    this.operandIsResult=operandIsResult;
  }
  public Variable getOperand(){
    Cloner cloner=new Cloner();
    operand=cloner.deepClone(operand);
    Cloner cloner=new Cloner();
    operand=cloner.deepClone(operand);
    return operand;
  }
  @Override public Variable getResultVariable(){
    if (operandIsResult)     return operand;
    return super.getResultVariable();
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver6(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getTarget() + " = " + getOperand();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
