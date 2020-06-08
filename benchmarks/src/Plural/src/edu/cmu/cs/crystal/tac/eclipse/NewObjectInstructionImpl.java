package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Modifier;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.NewObjectInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = new C(z1, ..., zn).
 * @author Kevin Bierhoff
 */
class NewObjectInstructionImpl extends AbstractAssignmentInstruction<ClassInstanceCreation> implements NewObjectInstruction {
  Object obj;
  /** 
 * @param node
 * @param tac
 */
  public NewObjectInstructionImpl(  ClassInstanceCreation node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 */
  public NewObjectInstructionImpl(  ClassInstanceCreation node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public IMethodBinding resolveBinding(){
    return this.node.resolveConstructorBinding();
  }
  public boolean isAnonClassType(){
    return this.node.getAnonymousClassDeclaration() != null;
  }
  public List<Variable> getArgOperands(){
    return variables(this.node.arguments());
  }
  public ITypeBinding resolveInstantiatedType(){
    return this.node.resolveTypeBinding();
  }
  public boolean hasOuterObjectSpecifier(){
    if (this.node.getExpression() != null) {
      return true;
    }
 else     if (getNode().resolveTypeBinding().isLocal()) {
      return false;
    }
 else     if (getNode().resolveTypeBinding().isNested()) {
      return !Modifier.isStatic(getNode().resolveTypeBinding().getDeclaredModifiers());
    }
 else     return false;
  }
  public Variable getOuterObjectSpecifierOperand(){
    if (this.node.getExpression() != null) {
      return variable(getNode().getExpression());
    }
 else     if (hasOuterObjectSpecifier()) {
      return receiverVariable();
    }
 else     return null;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver13(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    String qual="";
    String anon="";
    String type="<Type>";
    if (hasOuterObjectSpecifier())     qual=getOuterObjectSpecifierOperand().toString() + ".";
    if (isAnonClassType())     anon="<Anon>-";
    if (resolveBinding() != null)     type=resolveBinding().getName();
    return getTarget() + " = " + qual+ "new "+ anon+ type+ "("+ argsString(getArgOperands())+ ")";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
