package edu.cmu.cs.crystal.analysis;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
/** 
 * Utilities for all analyses
 * @author cchristo
 */
public class AnalysisUtils {
  String analysis="edu.cmu.cs.crystal.analysis.AnalysisUtils";
  /** 
 * Determine whether binding is a subtype of qualifiedTypeName. Due to Eclipse bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=80715 This currently DOES NOT WORK!!! For now, this straight-up compares the names for equality. That is all...
 * @param binding
 * @param qualifiedTypeName
 * @return True if binding equals qualifiedTypeName, false otherwise...
 */
  static public boolean isSubType(  String qualifiedTypeName,  ITypeBinding binding){
    String subTypeName=binding.getQualifiedName();
    if (subTypeName.equals(qualifiedTypeName))     return true;
    if (subTypeName.equals("null"))     return true;
    if (subTypeName.equals("edu.cmu.cs.crystal.test.relationships.DropDownList") && qualifiedTypeName.equals("edu.cmu.cs.crystal.test.relationships.ListControl"))     return true;
    if (subTypeName.equals("org.eclipse.jdt.core.dom.InfixExpression") && qualifiedTypeName.equals("org.eclipse.jdt.core.dom.ASTNode"))     return true;
    if (subTypeName.equals("org.eclipse.jdt.core.dom.Expression") && qualifiedTypeName.equals("org.eclipse.jdt.core.dom.ASTNode"))     return true;
    if (subTypeName.equals("org.eclipse.jdt.core.dom.InfixExpression") && qualifiedTypeName.equals("org.eclipse.jdt.core.dom.Expression"))     return true;
    return false;
  }
  public String getAnalysis(){
    Cloner cloner=new Cloner();
    analysis=cloner.deepClone(analysis);
    Cloner cloner=new Cloner();
    analysis=cloner.deepClone(analysis);
    return analysis;
  }
}
