package edu.cmu.cs.crystal.internal;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import edu.cmu.cs.crystal.Crystal;
/** 
 * Assorted utility methods
 * @author David Dickey
 * @author Nels Beckman
 */
public class Utilities2 {
  /** 
 * Takes an ASTNode and creates a more useful textual representation of it.
 */
  public static String ASTNodeToString(  ASTNode node){
    if (node == null)     return " [null ASTNode]";
    String prefix="-";
    String nodeToString=node.toString().replaceAll("\n","*");
    if (node instanceof Statement)     prefix="S";
 else     if (node instanceof Expression)     prefix="E";
 else     if (node instanceof Modifier)     prefix="M";
 else     if (node instanceof Type)     prefix="T";
 else     if (node instanceof VariableDeclaration)     prefix="V";
 else     if (node instanceof BodyDeclaration)     prefix="D";
    return prefix + " [" + node.getClass().getSimpleName()+ "] \""+ nodeToString+ "\"";
  }
  /** 
 * Converts a modifier flag to a String representation of the modifers.
 * @param modifier	the modifier flag
 * @return	the textual representation of the modifiers
 */
  public static String ModifierToString(  int modifier){
    String output="";
    if (Modifier.isPrivate(modifier))     output+="private ";
    if (Modifier.isProtected(modifier))     output+="protected ";
    if (Modifier.isPublic(modifier))     output+="public ";
    if (Modifier.isAbstract(modifier))     output+="abstract ";
    if (Modifier.isFinal(modifier))     output+="final ";
    if (Modifier.isNative(modifier))     output+="native ";
    if (Modifier.isStatic(modifier))     output+="static ";
    if (Modifier.isStrictfp(modifier))     output+="strictfp ";
    if (Modifier.isSynchronized(modifier))     output+="synchronized ";
    if (Modifier.isTransient(modifier))     output+="transient ";
    if (Modifier.isVolatile(modifier))     output+="volatile ";
    return output.trim();
  }
  /** 
 * Finds the method declaration that this node is within.  If the node does not exist below a method declaration then null is returned.
 * @param node	the node whose method we wish to find
 * @return	the method declaration or null if not within one
 */
  public static MethodDeclaration getMethodDeclaration(  ASTNode node){
    while (node != null) {
      if (node.getNodeType() == ASTNode.COMPILATION_UNIT)       return null;
      if (node.getNodeType() == ASTNode.METHOD_DECLARATION)       return (MethodDeclaration)node;
      node=node.getParent();
    }
    return null;
  }
  public static String methodDeclarationToString(  MethodDeclaration md){
    String output="";
    output=md.getName() + "(";
    List params=md.parameters();
    if (params != null && params.size() > 0) {
      Iterator i=params.iterator();
      SingleVariableDeclaration svd;
      while (i.hasNext()) {
        svd=(SingleVariableDeclaration)i.next();
        output+=svd.toString();
        if (i.hasNext())         output+=", ";
      }
    }
    output+=")";
    return output;
  }
  /** 
 * Not Yet Implemented. Throws a runtime exception, and is of any type.
 */
  public static <T>T nyi(){
    return nyi("This code has not yet been implemented.");
  }
  /** 
 * Not Yet Implemented. Throws a runtime exception with the given error message.
 */
  public static <T>T nyi(  String err_msg){
    throw new RuntimeException(err_msg);
  }
}
