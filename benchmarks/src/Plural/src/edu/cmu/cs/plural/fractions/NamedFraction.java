package edu.cmu.cs.plural.fractions;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.plural.fractions.elim.NormalizedFractionVisitor;
/** 
 * @author Kevin Bierhoff
 */
public class NamedFraction extends Fraction {
  private String varName;
  private static int nextID=0;
  private ASTNode joiningNode;
  Object obj;
  public NamedFraction(){
    this.varName="const" + (nextID++);
  }
  public NamedFraction(  String varName){
    this.varName=varName;
  }
  NamedFraction(  ASTNode joiningNode){
    this.varName="const" + (nextID++);
    this.joiningNode=joiningNode;
  }
  public String getVarName(){
    Cloner cloner=new Cloner();
    varName=cloner.deepClone(varName);
    Cloner cloner=new Cloner();
    varName=cloner.deepClone(varName);
    return varName;
  }
  boolean isVariable(  ASTNode node){
    return joiningNode != null && joiningNode == node;
  }
  boolean isJoinVariable(){
    return joiningNode != null;
  }
  @Override public boolean isNamed(){
    return true;
  }
  @Override public <T>T dispatch(  FractionVisitor<T> visitor){
    obj=new Object();
    return visitor.named(this);
  }
  @Override public <T>T dispatch(  NormalizedFractionVisitor<T> visitor){
    obj=new Object();
    return visitor.named(this);
  }
  @Override public String toString(){
    return joiningNode == null ? getVarName() : getVarName() + " [" + joiningNode.toString().replaceAll("\n","")+ "]";
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((varName == null) ? 0 : varName.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final NamedFraction other=(NamedFraction)obj;
    if (varName == null) {
      if (other.varName != null)       return false;
    }
 else     if (!varName.equals(other.varName))     return false;
    return true;
  }
  public ASTNode getJoiningNode(){
    Cloner cloner=new Cloner();
    joiningNode=cloner.deepClone(joiningNode);
    Cloner cloner=new Cloner();
    joiningNode=cloner.deepClone(joiningNode);
    return joiningNode;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
