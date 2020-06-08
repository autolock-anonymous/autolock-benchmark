package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.Expression;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.StoreArrayInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x[y] = z.
 * @author Kevin Bierhoff
 */
class StoreArrayInstructionImpl extends AbstractStoreInstruction implements StoreArrayInstruction {
  /** 
 * The array being written to. 
 */
  private ArrayAccess targetNode;
  Object obj;
  /** 
 * @param node
 * @param targetNode The array being written to.
 * @param source
 * @param tac
 * @see AbstractStoreInstruction#AbstractStoreInstruction(Expression,Variable,IEclipseVariableQuery)
 */
  public StoreArrayInstructionImpl(  Expression node,  ArrayAccess targetNode,  Variable source,  IEclipseVariableQuery tac){
    super(node,source,tac);
    this.targetNode=targetNode;
  }
  public Variable getDestinationArray(){
    return variable(getTargetNode().getArray());
  }
  public Variable getAccessedArrayOperand(){
    return variable(getTargetNode().getArray());
  }
  public Variable getArrayIndex(){
    return variable(getTargetNode().getIndex());
  }
  protected ArrayAccess getTargetNode(){
    Cloner cloner=new Cloner();
    targetNode=cloner.deepClone(targetNode);
    Cloner cloner=new Cloner();
    targetNode=cloner.deepClone(targetNode);
    return targetNode;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver15(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getDestinationArray() + "[" + getArrayIndex()+ "] = "+ getSourceOperand();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
