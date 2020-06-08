package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
public class EclipseSuperFieldAccess extends EclipseAbstractFieldAccess<SuperFieldAccess> implements IEclipseFieldAccess {
  /** 
 * @param node
 * @param query
 */
  public EclipseSuperFieldAccess(  SuperFieldAccess node,  IEclipseVariableQuery query){
    super(node,query);
  }
  public SimpleName getFieldName(){
    System.out.println("" + query);
    return node.getName();
  }
  public IVariableBinding resolveFieldBinding(){
    return node.resolveFieldBinding();
  }
  public boolean isImplicitThisAccess(){
    return false;
  }
  public boolean isExplicitSuperAccess(){
    return true;
  }
  @Override protected Variable getAccessedInstanceInternal(  IVariableBinding field){
    return query.superVariable(node.getQualifier());
  }
}
