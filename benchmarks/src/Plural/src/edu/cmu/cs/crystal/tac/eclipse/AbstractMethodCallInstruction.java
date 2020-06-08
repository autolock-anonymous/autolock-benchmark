package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.Expression;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.MethodCallInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = y.m(z1, ..., zn), where m is a method and y is possibly a type variable, in the case of a static method call, or <code>super</code>, in the case of a <b>super</b> call.
 * @author Kevin Bierhoff
 * @see #isStaticMethodCall() determine whether this is a static method call
 * @see #isSuperCall() determine whether this is a <b>super</b> call.
 */
abstract class AbstractMethodCallInstruction<E extends Expression> extends AbstractAssignmentInstruction<E> implements MethodCallInstruction {
  /** 
 * @param node
 * @param args
 */
  Object obj;
  public AbstractMethodCallInstruction(  E node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param args
 */
  public AbstractMethodCallInstruction(  E node,  Variable target,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  public abstract Variable getReceiverOperand();
  public abstract boolean isSuperCall();
  public boolean isStaticMethodCall(){
    return EclipseTAC.isStaticBinding(resolveBinding());
  }
  public abstract String getMethodName();
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver11(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getTarget() + " = " + getReceiverOperand()+ "."+ getMethodName()+ "("+ argsString(getArgOperands())+ ")";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
