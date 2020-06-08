package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.Assignment;
import edu.cmu.cs.crystal.tac.BinaryOperator;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseBinaryAssignOperation extends AbstractBinaryOperation<Assignment> {
  /** 
 * @param node
 * @param operator
 * @param tac
 */
  public EclipseBinaryAssignOperation(  Assignment node,  BinaryOperator operator,  IEclipseVariableQuery tac){
    super(node,operator,tac);
  }
  @Override public Variable getOperand1(){
    return variable(this.node.getLeftHandSide());
  }
  @Override public Variable getOperand2(){
    return variable(this.node.getRightHandSide());
  }
}
