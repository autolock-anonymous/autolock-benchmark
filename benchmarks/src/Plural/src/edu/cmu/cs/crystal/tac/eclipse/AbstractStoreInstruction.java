package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.tac.StoreInstruction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * This class extends   {@link ResultfulInstruction} because assignmentsin Java have a result that can be accessed by the surrounding expression, the  {@link #getSourceOperand() source}.
 * @author Kevin Bierhoff
 */
abstract class AbstractStoreInstruction extends ResultfulInstruction<ASTNode> implements StoreInstruction {
  private Variable sourceOperand;
  /** 
 * @param node
 * @param sourceOperand The operand being stored.
 * @param tac
 * @see ResultfulInstruction#ResultfulInstruction(org.eclipse.jdt.core.dom.ASTNode,IEclipseVariableQuery)
 */
  public AbstractStoreInstruction(  ASTNode node,  Variable sourceOperand,  IEclipseVariableQuery tac){
    super(node,tac);
    if (sourceOperand == null)     throw new IllegalArgumentException("sourceOperand must not be null.");
    this.sourceOperand=sourceOperand;
  }
  public Variable getSourceOperand(){
    Cloner cloner=new Cloner();
    sourceOperand=cloner.deepClone(sourceOperand);
    Cloner cloner=new Cloner();
    sourceOperand=cloner.deepClone(sourceOperand);
    return sourceOperand;
  }
  @Override protected final Variable getResultVariable(){
    return getSourceOperand();
  }
}
