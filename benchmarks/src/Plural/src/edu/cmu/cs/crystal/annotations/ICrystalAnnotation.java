package edu.cmu.cs.crystal.annotations;
/** 
 * Interface for accessing annotations through the Crystal annotation database.
 * @author Ciera Christopher
 */
public interface ICrystalAnnotation {
  /** 
 * Returns the fully qualified name of the original annotation class used in the source.
 * @return The fully qualified name of the original annotation class used in the source.
 */
  public String getName();
  /** 
 * The annotation database will use this method to populate the fully qualified name of the original annotation class.
 * @param name The fully qualified name of the original annotation class used in the source.
 * @see #getName()
 */
  public void setName(  String name);
  /** 
 * Returns the value of an annotation parameter with the given name. Values are represented as follows: <ul> <li>Primitive type - the equivalent boxed object</li> <li>java.lang.Class - the <code>ITypeBinding</code> for the class object</li> <li>java.lang.String - the string value itself</li> <li>enum type - the <code>IVariableBinding</code> for the enum constant</li> <li>annotation type - an <code>ICrystalAnnotation</code></li> <li>array type - an <code>Object[]</code> whose elements are as per above (the language only allows single dimensional arrays in annotations)</li> </ul>
 * @key Name of the annotation parameter, e.g., "value".
 * @return The value of an annotation parameter with the given name or <code>null</code> ifthe parameter is unknown.
 * @see org.eclipse.jdt.core.dom.IMemberValuePairBinding#getValue();
 */
  public Object getObject(  String key);
  /** 
 * The annotation database will use this method to populate the explicit <i>and implicit</i> parameters of an annotation instance present in the analyzed source code. The values set <b>must</b> conform to the rules for representing parameter values described in  {@link #getObject(String)}
 * @param key Parameter name.
 * @param value Parameter value.
 */
  public void setObject(  String key,  Object value);
}
