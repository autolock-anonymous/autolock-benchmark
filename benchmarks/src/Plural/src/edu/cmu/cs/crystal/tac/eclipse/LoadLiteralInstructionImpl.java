package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.StringLiteral;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.LoadLiteralInstruction;
/** 
 * x = l, an assignment of a literal value to a variable. Example:<br> <code>a = 4;</code>
 * @author Kevin Bierhoff
 */
class LoadLiteralInstructionImpl extends AbstractAssignmentInstruction<Expression> implements LoadLiteralInstruction {
  private Object literal;
  Object obj;
  /** 
 * @param node
 * @param literal
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public LoadLiteralInstructionImpl(  Expression node,  Object literal,  IEclipseVariableQuery tac){
    super(node,tac);
    this.literal=literal;
  }
  /** 
 * @param node
 * @param literal
 * @param target
 * @param tac
 * @see AbstractAssignmentInstruction#AbstractAssignmentInstruction(org.eclipse.jdt.core.dom.ASTNode,boolean,IEclipseVariableQuery)
 */
  public LoadLiteralInstructionImpl(  Expression node,  Object literal,  boolean fresh,  IEclipseVariableQuery tac){
    super(node,fresh,tac);
    this.literal=literal;
  }
  public Object getLiteral(){
    Cloner cloner=new Cloner();
    literal=cloner.deepClone(literal);
    Cloner cloner=new Cloner();
    literal=cloner.deepClone(literal);
    return literal;
  }
  public boolean isPrimitive(){
    return (this.node instanceof BooleanLiteral) || (getNode() instanceof CharacterLiteral) || (getNode() instanceof NumberLiteral);
  }
  public boolean isNumber(){
    return this.node instanceof NumberLiteral;
  }
  public boolean isNull(){
    return this.node instanceof NullLiteral;
  }
  public boolean isNonNullString(){
    return this.node instanceof StringLiteral;
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver8(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return getTarget() + " = " + getLiteral();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
