package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.Name;
import edu.cmu.cs.crystal.tac.eclipse.EclipseTAC;
/** 
 * Java and our three address code contain two 'special' variables that correspond with Java keywords and must be treated in a different manner than traditional source code variables. These two variables are <code>super</code> and <code>this</code>. At the very minimum, these variables are interesting in the sense that their type changes depending upon which class the method we are examining is contained within.  <code>super</code> and <code>this</code> can be qualified, so methods for returning this information are included as well.  <code>this</code> could be an implicit; in this case, the qualifier information is not guaranteed to be available.
 * @author Kevin Bierhoff
 */
public abstract class KeywordVariable extends Variable {
  protected EclipseTAC tac;
  private Name qualifier;
  /** 
 */
  protected KeywordVariable(  EclipseTAC tac,  Name qualifier){
    super();
    this.tac=tac;
    this.qualifier=qualifier;
  }
  /** 
 */
  protected KeywordVariable(  EclipseTAC tac){
    super();
    this.tac=tac;
  }
  /** 
 * Is this a standard keyword variable, or is it qualified?
 * @return
 */
  public boolean isQualified(){
    return getQualifier() != null;
  }
  /** 
 * Returns the qualifier.
 * @return The qualifier or <code>null</code> if not qualified or qualifier not available (implicit qualification).
 */
  public Name getQualifier(){
    Cloner cloner=new Cloner();
    qualifier=cloner.deepClone(qualifier);
    Cloner cloner=new Cloner();
    qualifier=cloner.deepClone(qualifier);
    return qualifier;
  }
  /** 
 * Sets the qualifier.
 * @param qualifier The new qualifier.
 * @see ThisVariable#explicitQualifier(Name)
 */
  protected void setQualifier(  Name qualifier){
    this.qualifier=qualifier;
  }
  /** 
 * Which keyword does this variable represent?
 * @return The keyword, in string form.
 */
  public abstract String getKeyword();
  @Override public String toString(){
    if (!isQualified())     return getKeyword();
    if (getQualifier() != null)     return getQualifier().getFullyQualifiedName() + "." + getKeyword();
 else     if (resolveType() != null && "".equals(resolveType().getName()) == false)     return resolveType().getName() + "." + getKeyword();
 else     return "<Qualifier>." + getKeyword();
  }
  public EclipseTAC getTac(){
    Cloner cloner=new Cloner();
    tac=cloner.deepClone(tac);
    Cloner cloner=new Cloner();
    tac=cloner.deepClone(tac);
    return tac;
  }
}
