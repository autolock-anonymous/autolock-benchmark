package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.InfixExpression;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LabeledSingleResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * This is just a helper class so the two branches can write to a common target variable
 * @author Kevin Bierhoff
 */
final class EclipseMergeHelper extends AbstractAssignmentInstruction<ASTNode> {
  /** 
 * Constructor for ? :
 * @param node
 * @param tac
 */
  Object obj;
  public EclipseMergeHelper(  ConditionalExpression node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * Constructor for && and ||
 * @param node
 * @param tac
 */
  public EclipseMergeHelper(  InfixExpression node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 */
  public EclipseMergeHelper(  ASTNode node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  @Override public <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value){
    obj=new Object();
    return value;
  }
  @Override public <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value){
    return new LabeledSingleResult<LE>(value,labels);
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
