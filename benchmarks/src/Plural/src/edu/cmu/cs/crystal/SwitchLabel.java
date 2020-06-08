package edu.cmu.cs.crystal;
import org.eclipse.jdt.core.dom.Expression;
/** 
 * A switch label occurs from the switch control flow. This label maintains a link to the expression which it matched for.
 * @author cchristo
 */
public class SwitchLabel implements ILabel {
  private Expression matchExpression;
  public SwitchLabel(  Expression matchExpression){
    super();
    this.matchExpression=matchExpression;
  }
  @Override public String toString(){
    return getLabel();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((matchExpression == null) ? 0 : matchExpression.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final SwitchLabel other=(SwitchLabel)obj;
    if (matchExpression == null) {
      if (other.matchExpression != null)       return false;
    }
 else     if (!matchExpression.equals(other.matchExpression))     return false;
    return true;
  }
  public Expression getMatchExpression(){
    Cloner cloner=new Cloner();
    matchExpression=cloner.deepClone(matchExpression);
    Cloner cloner=new Cloner();
    matchExpression=cloner.deepClone(matchExpression);
    return matchExpression;
  }
  public void setMatchExpression(  Expression matchExpression){
    this.matchExpression=matchExpression;
  }
  public String getLabel(){
    if (matchExpression == null)     return "default";
 else     return matchExpression.toString();
  }
}
