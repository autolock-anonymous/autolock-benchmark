package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.InfixExpression;
import edu.cmu.cs.crystal.tac.BinaryOperator;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseBinaryInfixOperation extends AbstractBinaryOperation<InfixExpression> {
  /** 
 * @param node
 * @param operator
 * @param tac
 */
  public EclipseBinaryInfixOperation(  InfixExpression node,  BinaryOperator operator,  IEclipseVariableQuery tac){
    super(node,operator,tac);
  }
  @Override public Variable getOperand1(){
    return variable(this.node.getLeftOperand());
  }
  @Override public Variable getOperand2(){
    return variable(this.node.getRightOperand());
  }
}
