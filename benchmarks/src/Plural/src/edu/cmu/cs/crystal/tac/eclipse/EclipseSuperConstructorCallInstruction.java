package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import edu.cmu.cs.crystal.tac.KeywordVariable;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseSuperConstructorCallInstruction extends AbstractConstructorCallInstruction<SuperConstructorInvocation> {
  /** 
 * @param node
 * @param constructionObject
 * @param tac
 */
  public EclipseSuperConstructorCallInstruction(  SuperConstructorInvocation node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  @Override public KeywordVariable getConstructionObject(){
    return superVariable(null);
  }
  @Override public boolean isSuperCall(){
    return true;
  }
  @Override public List<Variable> getArgOperands(){
    return variables(this.node.arguments());
  }
  @Override public Variable getEnclosingInstanceSpecifier(){
    if (getNode().getExpression() == null)     return null;
    return variable(this.node.getExpression());
  }
  @Override public boolean hasEnclosingInstanceSpecifier(){
    return this.node.getExpression() != null;
  }
  @Override public IMethodBinding resolveBinding(){
    return this.node.resolveConstructorBinding();
  }
}
