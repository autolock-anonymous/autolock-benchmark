package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ReturnStatement;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.ReturnInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 * @since 3.3.2
 */
public class ReturnInstructionImpl extends AbstractTACInstruction<ReturnStatement> implements ReturnInstruction {
  Object obj;
  /** 
 * Creates a return instruction for the given return statement, which must return a value.
 * @param node Return statement with non-<code>null</code> expression.
 * @param tac
 */
  public ReturnInstructionImpl(  ReturnStatement node,  IEclipseVariableQuery tac){
    super(node,tac);
    if (this.node.getExpression() == null)     throw new IllegalArgumentException("Explicit return instructions only for actual values.");
  }
  public Variable getReturnedVariable(){
    return variable(this.node.getExpression());
  }
  public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver14(this,value);
  }
  public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
