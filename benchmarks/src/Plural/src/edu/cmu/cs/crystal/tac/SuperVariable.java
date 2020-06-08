package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Name;
import edu.cmu.cs.crystal.tac.eclipse.EclipseTAC;
/** 
 * The Super class represents the <code>super</code> keyword. Note that in this sense, <code>super</code> is actually much like a variable.
 * @author Kevin Bierhoff
 */
public class SuperVariable extends KeywordVariable {
  /** 
 * Creates an unqualified <b>super</b> variable.
 * @param tac
 */
  public SuperVariable(  EclipseTAC tac){
    super(tac);
  }
  /** 
 * Creates an qualified <b>super</b> variable.
 * @param tac
 * @param qualifier
 */
  public SuperVariable(  EclipseTAC tac,  Name qualifier){
    super(tac,qualifier);
  }
  @Override public String getKeyword(){
    return "super";
  }
  @Override public ITypeBinding resolveType(){
    if (getQualifier() == null)     return tac.resolveThisType().getSuperclass();
    return getQualifier().resolveTypeBinding().getSuperclass();
  }
  @Override public <T>T dispatch(  IVariableVisitor<T> visitor){
    return visitor.superVar(this);
  }
  @Override public boolean isUnqualifiedSuper(){
    return !isQualified();
  }
}
