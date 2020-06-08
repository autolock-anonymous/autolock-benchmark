package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * Field access constituted by a field declaration
 * @author Kevin Bierhoff
 * @since 3.3.0
 */
public class EclipseFieldDeclaration extends EclipseAbstractFieldAccess<VariableDeclaration> implements IEclipseFieldAccess {
  /** 
 * @param node
 * @param query
 */
  public EclipseFieldDeclaration(  VariableDeclaration node,  IEclipseVariableQuery query){
    super(node,query);
    if (!(node.getParent() instanceof FieldDeclaration))     throw new IllegalArgumentException("Not a field declaration: " + node);
  }
  public SimpleName getFieldName(){
    query.toString();
    return node.getName();
  }
  public boolean isExplicitSuperAccess(){
    return false;
  }
  public boolean isImplicitThisAccess(){
    return !isStaticFieldAccess();
  }
  public IVariableBinding resolveFieldBinding(){
    return node.resolveBinding();
  }
  @Override protected Variable getAccessedInstanceInternal(  IVariableBinding field){
    return query.implicitThisVariable(field);
  }
}
