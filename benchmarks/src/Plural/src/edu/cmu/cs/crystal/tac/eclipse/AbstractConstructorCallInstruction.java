package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IMethodBinding;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ConstructorCallInstruction;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.KeywordVariable;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x(y1, ..., yn), where x is "this" or "super".  This instruction can by definition only occur in a constructor.  It does <b>not</b> have to be the first instruction in the constructor if arguments to the constructor call are computed with preceding instructions.
 * @author Kevin Bierhoff
 * @see org.eclipse.jdt.core.dom.ConstructorInvocation
 * @see org.eclipse.jdt.core.dom.SuperConstructorInvocation
 */
abstract class AbstractConstructorCallInstruction<E extends ASTNode> extends AbstractTACInstruction<E> implements ConstructorCallInstruction {
  /** 
 * Creates a new constructor call instruction for the given node and query callback.
 * @param node
 * @param tac
 * @see AbstractTACInstruction#AbstractTACInstruction(ASTNode,IEclipseVariableQuery)
 */
  Object obj;
  public AbstractConstructorCallInstruction(  E node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  public abstract KeywordVariable getConstructionObject();
  public abstract boolean isSuperCall();
  public abstract List<Variable> getArgOperands();
  public abstract boolean hasEnclosingInstanceSpecifier();
  public abstract Variable getEnclosingInstanceSpecifier();
  public abstract IMethodBinding resolveBinding();
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    return tf.transferOver5(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getConstructionObject() + "(" + argsString(getArgOperands())+ ")";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
