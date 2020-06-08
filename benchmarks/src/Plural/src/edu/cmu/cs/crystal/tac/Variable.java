package edu.cmu.cs.crystal.tac;
import org.eclipse.jdt.core.dom.ITypeBinding;
/** 
 * This class is the abstract super type of all variables in the three address code representation of a given Java program. Note that the only thing all the different types of variables have is that they all have a type. There are several different variable types that extend this type and in general are much more interesting than this class. These subclasses include 'temporary' variables in three address code as well as variables that correspond with actual Java source code variables.
 * @author Kevin Bierhoff
 */
public abstract class Variable {
  /** 
 * <code>null</code> or the type binding of this variable.
 * @see org.eclipse.jdt.core.Expression#resolveType()
 */
  public abstract ITypeBinding resolveType();
  /** 
 * Returns a string representing this variable in the source.
 * @return String representing this variable in the source.
 */
  public String getSourceString(){
    return toString();
  }
  public abstract <T>T dispatch(  IVariableVisitor<T> visitor);
  public boolean isUnqualifiedSuper(){
    return false;
  }
  public boolean isUnqualifiedThis(){
    return false;
  }
}
