package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.Expression;
import edu.cmu.cs.crystal.tac.BinaryOperator;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseBinaryDesugaredOperation extends AbstractBinaryOperation<Expression> {
  private Expression operand1node;
  private Variable operand2;
  /** 
 * @param node
 * @param operator
 * @param tac
 */
  public EclipseBinaryDesugaredOperation(  Expression node,  Expression operand1node,  BinaryOperator operator,  Variable operand2,  boolean fresh,  IEclipseVariableQuery tac){
    super(node,operator,fresh,tac);
    this.operand1node=operand1node;
    this.operand2=operand2;
  }
  @Override public Variable getOperand1(){
    return variable(operand1node);
  }
  @Override public Variable getOperand2(){
    Cloner cloner=new Cloner();
    operand2=cloner.deepClone(operand2);
    Cloner cloner=new Cloner();
    operand2=cloner.deepClone(operand2);
    return operand2;
  }
  public Expression getOperand1node(){
    Cloner cloner=new Cloner();
    operand1node=cloner.deepClone(operand1node);
    Cloner cloner=new Cloner();
    operand1node=cloner.deepClone(operand1node);
    return operand1node;
  }
}
