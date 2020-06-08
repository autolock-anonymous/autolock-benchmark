package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import edu.cmu.cs.crystal.tac.eclipse.EclipseTAC;
/** 
 * This class represents the <code>this</code> keyword, which is treated much like any other variable. For example, if the original Java source had code that was similar to the following:<br> <code>a = this;</code><br> The right-hand side would be translated as a variable of this type.
 * @author Kevin Bierhoff
 */
public class ThisVariable extends KeywordVariable {
  private ITypeBinding typeBinding;
  private Object obj;
  /** 
 * Creates an implicitly qualified <b>this</b> variable.
 * @param tac
 * @param binding 
 */
  public ThisVariable(  EclipseTAC tac,  ITypeBinding typeBinding){
    super(tac);
    this.typeBinding=typeBinding;
  }
  /** 
 * Creates an unqualified <b>this</b> variable.
 * @param tac
 */
  public ThisVariable(  EclipseTAC tac){
    super(tac);
  }
  /** 
 * Creates a qualified <b>this</b> variable.
 * @param tac
 * @param qualifier 
 */
  public ThisVariable(  EclipseTAC tac,  Name qualifier){
    super(tac,qualifier);
  }
  @Override public String getKeyword(){
    return "this";
  }
  @Override public boolean isQualified(){
    return super.isQualified() || isImplicit();
  }
  @Override public ITypeBinding resolveType(){
    if (typeBinding != null)     return typeBinding;
    if (getQualifier() != null)     return getQualifier().resolveTypeBinding();
    return tac.resolveThisType();
  }
  public boolean isImplicit(){
    return typeBinding != null;
  }
  public void explicitQualifier(  Name qualifier){
    if (typeBinding == null)     throw new IllegalStateException("Not an implicitly qualified this variable");
    typeBinding=null;
    setQualifier(qualifier);
  }
  @Override public <T>T dispatch(  IVariableVisitor<T> visitor){
    obj=new Object();
    return visitor.thisVar(this);
  }
  @Override public boolean isUnqualifiedThis(){
    return !isQualified();
  }
  public ITypeBinding getTypeBinding(){
    Cloner cloner=new Cloner();
    typeBinding=cloner.deepClone(typeBinding);
    Cloner cloner=new Cloner();
    typeBinding=cloner.deepClone(typeBinding);
    return typeBinding;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
