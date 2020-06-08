package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.DotClassInstruction;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = T.class.
 * @author Kevin Bierhoff
 */
class DotClassInstructionImpl extends AbstractAssignmentInstruction<TypeLiteral> implements DotClassInstruction {
  Object obj=new Object();
  /** 
 * @param node
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public DotClassInstructionImpl(  TypeLiteral node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,Variable,IEclipseVariableQuery)
 */
  public DotClassInstructionImpl(  TypeLiteral node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public Type getTypeNode(){
    return this.node.getType();
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver4(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transferOverload4(this,labels,value);
  }
  @Override public String toString(){
    if (getTypeNode().resolveBinding() == null)     return getTarget() + " = <Type>.class";
    return getTarget() + " = " + getTypeNode().resolveBinding().getName()+ ".class";
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
