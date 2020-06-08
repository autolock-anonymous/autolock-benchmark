package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Modifier;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.LoadFieldInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = y.f, where f is a field.
 * @author Kevin Bierhoff
 */
class LoadFieldInstructionImpl extends AbstractAssignmentInstruction<ASTNode> implements LoadFieldInstruction {
  private IEclipseFieldAccess access;
  Object obj;
  /** 
 * @param node
 * @param access
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(ASTNode,IEclipseVariableQuery)
 */
  public LoadFieldInstructionImpl(  ASTNode node,  IEclipseFieldAccess access,  IEclipseVariableQuery tac){
    super(node,tac);
    this.access=access;
  }
  public String getFieldName(){
    return access.getFieldName().getIdentifier();
  }
  public IVariableBinding resolveFieldBinding(){
    return access.resolveFieldBinding();
  }
  public Variable getSourceObject(){
    return access.getAccessedObject();
  }
  public Variable getAccessedObjectOperand(){
    return access.getAccessedObject();
  }
  public boolean isStaticFieldAccess(){
    return (resolveFieldBinding().getModifiers() & Modifier.STATIC) == Modifier.STATIC;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver10(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    if (access.isImplicitThisAccess()) {
      return getTarget() + " = <implicit-this>." + getFieldName();
    }
    return getTarget() + " = " + getSourceObject()+ "."+ getFieldName();
  }
  public IEclipseFieldAccess getAccess(){
    Cloner cloner=new Cloner();
    access=cloner.deepClone(access);
    Cloner cloner=new Cloner();
    access=cloner.deepClone(access);
    return access;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
