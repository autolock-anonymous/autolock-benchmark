package edu.cmu.cs.crystal.tac.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.IMethodBinding;
import edu.cmu.cs.crystal.tac.KeywordVariable;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
class EclipseThisConstructorCallInstruction extends AbstractConstructorCallInstruction<ConstructorInvocation> {
  public EclipseThisConstructorCallInstruction(  ConstructorInvocation node,  IEclipseVariableQuery tac){
    super(node,tac);
  }
  @Override public KeywordVariable getConstructionObject(){
    return implicitThisVariable(this.resolveBinding());
  }
  @Override public boolean isSuperCall(){
    return false;
  }
  @Override public List<Variable> getArgOperands(){
    return variables(this.node.arguments());
  }
  @Override public boolean hasEnclosingInstanceSpecifier(){
    return false;
  }
  @Override public Variable getEnclosingInstanceSpecifier(){
    return null;
  }
  @Override public IMethodBinding resolveBinding(){
    return this.node.resolveConstructorBinding();
  }
}
