package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ArrayAccess;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.LoadArrayInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = y[z].
 * @author Kevin Bierhoff
 */
class LoadArrayInstructionImpl extends AbstractAssignmentInstruction<ArrayAccess> implements LoadArrayInstruction {
  Object obj;
  /** 
 * @param node
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public LoadArrayInstructionImpl(  ArrayAccess node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,Variable,IEclipseVariableQuery)
 */
  public LoadArrayInstructionImpl(  ArrayAccess node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public Variable getSourceArray(){
    return variable(this.node.getArray());
  }
  public Variable getAccessedArrayOperand(){
    return variable(this.node.getArray());
  }
  public Variable getArrayIndex(){
    return variable(this.node.getIndex());
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver9(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getTarget() + " = " + getSourceArray()+ "["+ getArrayIndex()+ "]";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
