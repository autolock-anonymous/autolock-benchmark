package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.AssignmentInstruction;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.KeywordVariable;
import edu.cmu.cs.crystal.tac.SourceVariable;
import edu.cmu.cs.crystal.tac.SourceVariableRead;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * This instruction indicates reading a variable that appears in the source program, i.e. the receiver, locals, or parameters.  This instruction is even generated when the source variable appears on the left-hand side of an assignment, to indicate that the variable is being touched. TODO Figure out if assignment targets should be a "SourceVariableRead" or not.
 * @author Kevin Bierhoff
 * @see AssignmentInstruction#getTarget()
 */
class SourceVariableReadImpl extends ResultfulInstruction<ASTNode> implements SourceVariableRead {
  private Variable variable;
  Object obj;
  /** 
 * @param node
 * @param variable the variable being read.
 * @param tac
 * @see ResultfulInstruction#ResultfulInstruction(ASTNode,IEclipseVariableQuery)
 */
  public SourceVariableReadImpl(  ASTNode node,  Variable variable,  IEclipseVariableQuery tac){
    super(node,tac);
    if (!(variable instanceof SourceVariable || variable instanceof KeywordVariable))     throw new IllegalArgumentException("Not a source or keyword variable: " + variable);
    this.variable=variable;
  }
  public Variable getVariable(){
    Cloner cloner=new Cloner();
    variable=cloner.deepClone(variable);
    Cloner cloner=new Cloner();
    variable=cloner.deepClone(variable);
    return variable;
  }
  @Override protected final Variable getResultVariable(){
    return getVariable();
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return tf.transferOver18(this,value);
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    obj=new Object();
    return tf.transfer(this,labels,value);
  }
  @Override public String toString(){
    return variable.toString();
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
