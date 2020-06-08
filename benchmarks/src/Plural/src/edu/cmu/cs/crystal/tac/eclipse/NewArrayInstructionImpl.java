package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.NewArrayInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * x = new T[y1]...[yn] or x = new T[]...[] = z.
 * @author Kevin Bierhoff
 */
class NewArrayInstructionImpl extends AbstractAssignmentInstruction<ArrayCreation> implements NewArrayInstruction {
  Object obj;
  /** 
 * @param node
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public NewArrayInstructionImpl(  ArrayCreation node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,Variable,IEclipseVariableQuery)
 */
  public NewArrayInstructionImpl(  ArrayCreation node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  public ArrayType getArrayType(){
    return this.node.getType();
  }
  public List<Variable> getDimensionOperands(){
    return variables(this.node.dimensions());
  }
  public int getUnallocated(){
    return isInitialized() ? 0 : getArrayType().getDimensions();
  }
  public int getDimensions(){
    return getArrayType().getDimensions();
  }
  public boolean isInitialized(){
    return this.node.getInitializer() != null;
  }
  public Variable getInitOperand(){
    return variable(this.node.getInitializer());
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver12(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    String baseType="<Type>";
    if (getArrayType().getElementType().resolveBinding() != null)     baseType=getArrayType().getElementType().resolveBinding().getName();
    String result=getTarget() + " = new " + baseType;
    for (    Variable x : getDimensionOperands())     result+="[" + x + "]";
    for (int i=0; i < getUnallocated(); i++)     result+="[]";
    if (isInitialized() == false)     return result;
    return result + " = " + getInitOperand().toString();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
