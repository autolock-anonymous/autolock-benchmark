package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.CastInstruction;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = (T) y.
 * @author Kevin Bierhoff
 */
class CastInstructionImpl extends AbstractAssignmentInstruction<CastExpression> implements CastInstruction {
  /** 
 * @param node
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  Object obj;
  public CastInstructionImpl(  CastExpression node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,Variable,IEclipseVariableQuery)
 */
  public CastInstructionImpl(  CastExpression node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public Type getCastToTypeNode(){
    return this.node.getType();
  }
  public Variable getOperand(){
    return variable(this.node.getExpression());
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver3(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    return tf.transferO1(this,labels,value);
  }
  @Override public String toString(){
    ITypeBinding t=getCastToTypeNode().resolveBinding();
    if (t == null)     return getTarget() + " = (<Cast>) " + getOperand();
    return getTarget() + " = (" + t.getName()+ ") "+ getOperand();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
