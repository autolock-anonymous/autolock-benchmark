package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
public class EclipseReferenceFieldAccess extends EclipseAbstractFieldAccess<FieldAccess> implements IEclipseFieldAccess {
  /** 
 * @param node
 * @param query
 */
  public EclipseReferenceFieldAccess(  FieldAccess node,  IEclipseVariableQuery query){
    super(node,query);
  }
  public SimpleName getFieldName(){
    return node.getName();
  }
  public IVariableBinding resolveFieldBinding(){
    return node.resolveFieldBinding();
  }
  public boolean isImplicitThisAccess(){
    return false;
  }
  public boolean isExplicitSuperAccess(){
    return false;
  }
  @Override protected Variable getAccessedInstanceInternal(  IVariableBinding field){
    return query.variable(node.getExpression());
  }
}
