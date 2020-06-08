package edu.cmu.cs.crystal;
import org.eclipse.jdt.core.dom.ITypeBinding;
/** 
 * Represents a label which has an exception. This maintains a link to the ITypeBinding of the exception for this label. This occurs in exceptional control flow from throws, method calls,etc.
 * @author cchristo
 */
public class ExceptionalLabel implements ILabel {
  private ITypeBinding exceptionType;
  public ExceptionalLabel(  ITypeBinding exceptionType){
    this.exceptionType=exceptionType;
  }
  public ITypeBinding getExceptionType(){
    Cloner cloner=new Cloner();
    exceptionType=cloner.deepClone(exceptionType);
    Cloner cloner=new Cloner();
    exceptionType=cloner.deepClone(exceptionType);
    return exceptionType;
  }
  public void setExceptionType(  ITypeBinding branchValue){
    this.exceptionType=branchValue;
  }
  public String getLabel(){
    return exceptionType.getQualifiedName();
  }
  @Override public String toString(){
    return getLabel();
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((exceptionType == null) ? 0 : exceptionType.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final ExceptionalLabel other=(ExceptionalLabel)obj;
    if (exceptionType == null) {
      if (other.exceptionType != null)       return false;
    }
 else     if (!exceptionType.equals(other.exceptionType))     return false;
    return true;
  }
  public boolean compareObjects(  Object obj){
    final ExceptionalLabel other=(ExceptionalLabel)obj;
    if (exceptionType == null) {
      if (other.exceptionType != null)       return false;
    }
 else     if (!exceptionType.equals(other.exceptionType))     return false;
    return true;
  }
}
