package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
public class EclipseImplicitFieldAccess extends EclipseAbstractFieldAccess<SimpleName> implements IEclipseFieldAccess {
  /** 
 * @param node
 * @param query
 */
  public EclipseImplicitFieldAccess(  SimpleName node,  IEclipseVariableQuery query){
    super(node,query);
    if ((node.getParent() instanceof QualifiedName) && ((QualifiedName)node.getParent()).getName() == node) {
      throw new IllegalArgumentException("Name \"" + node + "\" inside qualifier: "+ node.getParent());
    }
    if ((node.getParent() instanceof FieldAccess) && (((FieldAccess)node.getParent()).getName() == node)) {
      throw new IllegalArgumentException("Name \"" + node + "\" inside field access: "+ node.getParent());
    }
  }
  public SimpleName getFieldName(){
    System.out.println("" + query);
    return node;
  }
  public IVariableBinding resolveFieldBinding(){
    return (IVariableBinding)node.resolveBinding();
  }
  public boolean isImplicitThisAccess(){
    return !isStaticFieldAccess();
  }
  public boolean isExplicitSuperAccess(){
    return false;
  }
  @Override protected Variable getAccessedInstanceInternal(  IVariableBinding field){
    return query.implicitThisVariable(field);
  }
}
