package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseNormalCallInstruction extends AbstractMethodCallInstruction<MethodInvocation> {
  /** 
 * @param node
 * @param tac
 */
  public EclipseNormalCallInstruction(  MethodInvocation node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 */
  public EclipseNormalCallInstruction(  MethodInvocation node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  @Override public boolean isSuperCall(){
    return false;
  }
  @Override public Variable getReceiverOperand(){
    if (this.node.getExpression() == null) {
      IMethodBinding method=resolveBinding();
      if (isStaticMethodCall()) {
        return typeVariable(method.getDeclaringClass());
      }
 else {
        return implicitThisVariable(method);
      }
    }
    return variable(this.node.getExpression());
  }
  public List<Variable> getArgOperands(){
    return variables(this.node.arguments());
  }
  @Override public String getMethodName(){
    return this.node.getName().getIdentifier();
  }
  public IMethodBinding resolveBinding(){
    return this.node.resolveMethodBinding();
  }
  @Override public String toString(){
    String receiver;
    if (getNode().getExpression() == null) {
      if (isStaticMethodCall())       receiver="<static>";
 else       receiver="<implicit-this>.";
    }
 else     receiver=getReceiverOperand() + ".";
    return getTarget() + " = " + receiver+ getMethodName()+ "("+ argsString(getArgOperands())+ ")";
  }
}
