package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ArrayInitInstruction;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = { y1, ..., yn }.
 * @author Kevin Bierhoff
 */
class ArrayInitInstructionImpl extends AbstractAssignmentInstruction<ArrayInitializer> implements ArrayInitInstruction {
  Object obj;
  /** 
 * @param node
 * @param tac
 */
  public ArrayInitInstructionImpl(  ArrayInitializer node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 */
  public ArrayInitInstructionImpl(  ArrayInitializer node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public List<Variable> getInitOperands(){
    return variables(this.node.expressions());
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    return tf.transferOver2(this,labels,value);
  }
  @Override public String toString(){
    return getTarget() + " = { " + argsString(getInitOperands())+ " }";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
