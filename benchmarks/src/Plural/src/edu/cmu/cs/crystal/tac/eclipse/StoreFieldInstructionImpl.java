package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.StoreFieldInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x.f = y, where f is a field.
 * @author Kevin Bierhoff
 */
class StoreFieldInstructionImpl extends AbstractStoreInstruction implements StoreFieldInstruction {
  private IEclipseFieldAccess target;
  Object obj;
  /** 
 * @param node
 * @param source The operand being stored.
 * @param target The field being written to.
 * @param tac
 * @see AbstractStoreInstruction#AbstractStoreInstruction(Expression,Variable,IEclipseVariableQuery)
 */
  public StoreFieldInstructionImpl(  ASTNode node,  Variable source,  IEclipseFieldAccess target,  IEclipseVariableQuery tac){
    super(node,source,tac);
    this.target=target;
  }
  public Variable getDestinationObject(){
    return target.getAccessedObject();
  }
  public Variable getAccessedObjectOperand(){
    return target.getAccessedObject();
  }
  public String getFieldName(){
    return target.getFieldName().getIdentifier();
  }
  public IVariableBinding resolveFieldBinding(){
    return target.resolveFieldBinding();
  }
  public boolean isStaticFieldAccess(){
    return (resolveFieldBinding().getModifiers() & Modifier.STATIC) == Modifier.STATIC;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver16(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    if (target.isImplicitThisAccess())     return "<implicit-this>." + getFieldName() + " = "+ getSourceOperand();
    return getDestinationObject() + "." + getFieldName()+ " = "+ getSourceOperand();
  }
  public IEclipseFieldAccess getTarget(){
    Cloner cloner=new Cloner();
    target=cloner.deepClone(target);
    Cloner cloner=new Cloner();
    target=cloner.deepClone(target);
    return target;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
