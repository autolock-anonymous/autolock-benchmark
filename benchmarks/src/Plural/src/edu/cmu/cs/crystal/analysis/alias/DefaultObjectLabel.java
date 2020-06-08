package edu.cmu.cs.crystal.analysis.alias;
import org.eclipse.jdt.core.dom.ITypeBinding;
public class DefaultObjectLabel implements ObjectLabel {
  private static int LABELINDEX=0;
  private int label;
  private ITypeBinding type;
  private boolean isSummary;
  public DefaultObjectLabel(  ITypeBinding type,  boolean isSummaryLabel){
    label=++LABELINDEX;
    this.isSummary=isSummaryLabel;
    this.type=type;
  }
  public boolean isSummary(){
    return isSummary;
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + label;
    return result;
  }
  public String toString(){
    return "L" + Integer.toString(label);
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final DefaultObjectLabel other=(DefaultObjectLabel)obj;
    return label == other.label;
  }
  public ITypeBinding getType(){
    Cloner cloner=new Cloner();
    type=cloner.deepClone(type);
    Cloner cloner=new Cloner();
    type=cloner.deepClone(type);
    return type;
  }
}
