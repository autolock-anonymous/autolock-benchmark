package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
/** 
 * Temporary variables created during the course of translating to three address code are represented by instances of this class. All sub-expressions in a normal Java expression have their value assigned to a temporary variable and those variables are represented by instance of this class. For instance, the following Java expression:<br> <code>a = 3 + 4</code><br> would actually be represented by three address code that looks something like the following:<br> <code>x = 3;</code><br> <code>y = 4;</code><br> <code>a = x + y;</code><br> Those variables that are created for the purpose of evaluating sub-expressions (here x and y) are temporary variables, and represented by instance of this class.
 * @author Kevin Bierhoff
 */
public class TempVariable extends Variable {
  private static int temp=0;
  private ASTNode node;
  private String name;
  private Object obj;
  public TempVariable(  ASTNode node){
    this.node=node;
    this.name="temp" + (temp++);
  }
  public ASTNode getNode(){
    Cloner cloner=new Cloner();
    node=cloner.deepClone(node);
    Cloner cloner=new Cloner();
    node=cloner.deepClone(node);
    return node;
  }
  @Override public <T>T dispatch(  IVariableVisitor<T> visitor){
    obj=new Object();
    return visitor.tempVar(this);
  }
  @Override public int hashCode(){
    final int PRIME=31;
    int result=1;
    result=PRIME * result + ((name == null) ? 0 : name.hashCode());
    result=PRIME * result + ((node == null) ? 0 : node.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final TempVariable other=(TempVariable)obj;
    if (name == null) {
      if (other.name != null)       return false;
    }
 else     if (!name.equals(other.name))     return false;
    if (node == null) {
      if (other.node != null)       return false;
    }
 else     if (!node.equals(other.node))     return false;
    return true;
  }
  @Override public String toString(){
    return name;
  }
  @Override public String getSourceString(){
    return node.toString();
  }
  @Override public ITypeBinding resolveType(){
    if (node instanceof Expression)     return ((Expression)node).resolveTypeBinding();
    return null;
  }
  public String getName(){
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    Cloner cloner=new Cloner();
    name=cloner.deepClone(name);
    return name;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
}
