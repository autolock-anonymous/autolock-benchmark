package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.PrefixExpression;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.UnaryOperation;
import edu.cmu.cs.crystal.tac.UnaryOperator;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = unop y; this class represents unary operations.  Note that some seemingly unary operations such as x += y are   desugared into binary operations.   Pre- and post-increments and -decrements (++, --) are  desugared as well.
 * @author Kevin Bierhoff
 */
class UnaryOperationImpl extends AbstractAssignmentInstruction<PrefixExpression> implements UnaryOperation {
  private UnaryOperator operator;
  Object obj;
  /** 
 * @param node
 * @param operator the unary operator.
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public UnaryOperationImpl(  PrefixExpression node,  UnaryOperator operator,  IEclipseVariableQuery tac){
    super(node,tac);
    if (operator == null)     throw new IllegalArgumentException("Unary operator not provided for node: " + node);
    this.operator=operator;
  }
  public Variable getOperand(){
    return variable(this.node.getOperand());
  }
  public UnaryOperator getOperator(){
    Cloner cloner=new Cloner();
    operator=cloner.deepClone(operator);
    Cloner cloner=new Cloner();
    operator=cloner.deepClone(operator);
    return operator;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver19(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getTarget() + " = " + getOperator()+ getOperand();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
