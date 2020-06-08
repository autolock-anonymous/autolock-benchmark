package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Type;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.InstanceofInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = y instanceof T.
 * @author Kevin Bierhoff
 */
class InstanceofInstructionImpl extends AbstractAssignmentInstruction<InstanceofExpression> implements InstanceofInstruction {
  Object obj;
  /** 
 * @param node
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public InstanceofInstructionImpl(  InstanceofExpression node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,Variable,IEclipseVariableQuery)
 */
  public InstanceofInstructionImpl(  InstanceofExpression node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public Type getTestedTypeNode(){
    return this.node.getRightOperand();
  }
  public Variable getOperand(){
    return variable(this.node.getLeftOperand());
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver7(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    ITypeBinding t=getTestedTypeNode().resolveBinding();
    if (t == null)     return getTarget() + " = " + getOperand()+ "instanceof <Type>";
    return getTarget() + " = " + getOperand()+ " instanceof "+ t.getName();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
