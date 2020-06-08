package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseSuperCallInstruction extends AbstractMethodCallInstruction<SuperMethodInvocation> {
  /** 
 * @param node
 * @param tac
 */
  public EclipseSuperCallInstruction(  SuperMethodInvocation node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  /** 
 * @param node
 * @param target
 * @param tac
 */
  public EclipseSuperCallInstruction(  SuperMethodInvocation node,  Variable target,  IEclipseVariableQuery tac){
    super(node,target,tac);
  }
  @Override public String getMethodName(){
    return this.node.getName().getIdentifier();
  }
  @Override public Variable getReceiverOperand(){
    if (isStaticMethodCall()) {
      return typeVariable(resolveBinding().getDeclaringClass());
    }
    return superVariable(this.node.getQualifier());
  }
  @Override public boolean isSuperCall(){
    return true;
  }
  public List<Variable> getArgOperands(){
    return variables(this.node.arguments());
  }
  public IMethodBinding resolveBinding(){
    return this.node.resolveMethodBinding();
  }
}
