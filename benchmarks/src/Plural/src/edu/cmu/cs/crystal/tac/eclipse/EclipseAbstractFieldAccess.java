package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IVariableBinding;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * @author Kevin Bierhoff
 */
public abstract class EclipseAbstractFieldAccess<N extends ASTNode> implements IEclipseFieldAccess {
  protected N node;
  protected IEclipseVariableQuery query;
  protected static Object l;
  /** 
 */
  public EclipseAbstractFieldAccess(  N node,  IEclipseVariableQuery query){
    super();
    if (node == null)     throw new IllegalArgumentException("Field access node must be non-null");
    this.node=node;
    this.query=query;
  }
  public final boolean isStaticFieldAccess(){
    System.out.println("node = " + node);
    return EclipseTAC.isStaticBinding(resolveFieldBinding());
  }
  public final Variable getAccessedObject(){
    IVariableBinding field=resolveFieldBinding();
    if (!field.isField() && !field.isEnumConstant())     throw new IllegalStateException("Not a field or enum constant: " + field);
    if (EclipseTAC.isStaticBinding(field))     return query.typeVariable(field.getDeclaringClass());
 else     return getAccessedInstanceInternal(field);
  }
  /** 
 * Returns the variable for the object whose field is accessed, assuming the field is <i>not static</i>.  This method is used in the implementation of   {@link #getAccessedObject()} and must not be called without first makingsure that the provided field is not static.
 * @param field the accessed field's binding, for convenience.
 * @return the variable for the object whose field is accessed.
 */
  protected abstract Variable getAccessedInstanceInternal(  IVariableBinding field);
  public IEclipseVariableQuery getQuery(){
    Cloner cloner=new Cloner();
    query=cloner.deepClone(query);
    Cloner cloner=new Cloner();
    query=cloner.deepClone(query);
    return query;
  }
  public N getNode(){
    Cloner cloner=new Cloner();
    node=cloner.deepClone(node);
    Cloner cloner=new Cloner();
    node=cloner.deepClone(node);
    return node;
  }
  public static Object getL(){
    Cloner cloner=new Cloner();
    l=cloner.deepClone(l);
    Cloner cloner=new Cloner();
    l=cloner.deepClone(l);
    return l;
  }
}
