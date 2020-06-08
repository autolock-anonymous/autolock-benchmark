package edu.cmu.cs.crystal.tac.eclipse;
import org.eclipse.jdt.core.dom.Expression;
/** 
 * @author Kevin Bierhoff
 */
class EclipseLoadDesugaredLiteralInstruction extends LoadLiteralInstructionImpl {
  /** 
 * This instruction will always get a fresh variable.
 * @param node
 * @param literal
 * @param tac
 */
  public EclipseLoadDesugaredLiteralInstruction(  Expression node,  Object literal,  IEclipseVariableQuery tac){
    super(node,literal,true,tac);
  }
  @Override public Object getLiteral(){
    Object result=super.getLiteral();
    if (result instanceof Number)     return result.toString();
    return result;
  }
  @Override public boolean isNonNullString(){
    return super.getLiteral() != null && super.getLiteral() instanceof String;
  }
  @Override public boolean isNull(){
    return super.getLiteral() == null;
  }
  @Override public boolean isNumber(){
    return super.getLiteral() != null && super.getLiteral() instanceof Number;
  }
  @Override public boolean isPrimitive(){
    return !isNull() && !isNonNullString();
  }
}
