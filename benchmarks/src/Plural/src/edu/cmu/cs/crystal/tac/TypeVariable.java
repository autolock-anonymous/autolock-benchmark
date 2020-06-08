package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ITypeBinding;
/** 
 * In Java you can make calls that look like the following:<br> <code>System.exit(0);</code><br> Here System is a class, but because out is a static field we access it directly from the class name. In this example, System is a type variable and in three address code System would be represented by an instance of this class. <i>For some reason static fields are represented as   {@link SourceVariable}instances</i> and not as a field access to a type variable.
 * @author Kevin Bierhoff
 */
public class TypeVariable extends Variable {
  private ITypeBinding binding;
  public TypeVariable(  ITypeBinding binding){
    super();
    this.binding=binding;
  }
  /** 
 * Because this class represents a type variable, getType and resolveType both return the same value; the type represented by this variable.
 * @return the type
 */
  public ITypeBinding getType(){
    return binding;
  }
  @Override public int hashCode(){
    final int PRIME=31;
    int result=1;
    result=PRIME * result + ((binding == null) ? 0 : binding.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final TypeVariable other=(TypeVariable)obj;
    if (binding == null) {
      if (other.binding != null)       return false;
    }
 else     if (!binding.equals(other.binding))     return false;
    return true;
  }
  @Override public String toString(){
    return binding.getQualifiedName();
  }
  /** 
 * Because this class represents a type variable, getType and resolveType both return the same value; the type represented by this variable.
 */
  @Override public ITypeBinding resolveType(){
    return binding;
  }
  @Override public <T>T dispatch(  IVariableVisitor<T> visitor){
    return visitor.typeVar(this);
  }
  public ITypeBinding getBinding(){
    Cloner cloner=new Cloner();
    binding=cloner.deepClone(binding);
    Cloner cloner=new Cloner();
    binding=cloner.deepClone(binding);
    return binding;
  }
}
