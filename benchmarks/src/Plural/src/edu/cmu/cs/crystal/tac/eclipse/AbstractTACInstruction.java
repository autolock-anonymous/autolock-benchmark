package edu.cmu.cs.crystal.tac.eclipse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.tac.ITACTransferFunction;
import edu.cmu.cs.crystal.tac.SuperVariable;
import edu.cmu.cs.crystal.tac.TACInstruction;
import edu.cmu.cs.crystal.tac.ThisVariable;
import edu.cmu.cs.crystal.tac.TypeVariable;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * Abstract base class for 3-Address-Code instructions built from Eclipse AST nodes.  {@link ITACTransferFunction} lists subclasses that define the differenttypes of instructions.  Additional (abstract and/or package-private) classes  simplify 3-Address-Code generation from AST nodes.  
 * @author Kevin Bierhoff
 * @param < E > Parameter used internally to precisely type underlying AST node.<i>This parameter has no effect on 3-address-code clients</i> and does therefore not appear in  {@link TACInstruction} and derived interfaces.  Instead wedefine  {@link #getNode()} with a more precise return type in interfacesderived from  {@link TACInstruction}, where applicable.
 * @see ITACTransferFunction
 * @see ITACBranchSensitiveTransferFunction
 */
abstract class AbstractTACInstruction<E extends ASTNode> implements TACInstruction {
  protected final E node;
  private final IEclipseVariableQuery tac;
  List<Variable> result;
  /** 
 * Create TAC instruction for a given AST node and query object.
 * @param node AST node this instruction is based on (usuallyone instruction per AST node.
 * @param tac This interface determines variables forAST nodes.  Usually used to query the variables representing subexpressions of the given node.
 */
  public AbstractTACInstruction(  E node,  IEclipseVariableQuery tac){
    this.node=node;
    this.tac=tac;
  }
  public E getNode(){
    Cloner cloner=new Cloner();
    node=cloner.deepClone(node);
    Cloner cloner=new Cloner();
    node=cloner.deepClone(node);
    return node;
  }
  /** 
 * Helper method to access   {@link IEclipseVariableQuery#variable(ASTNode)}to determine the variable representing a given expression.
 * @param node An expression AST node.
 * @return Variable representing the result of the given expression.
 */
  protected Variable variable(  Expression node){
    return tac.variable(node);
  }
  /** 
 * Helper method to access   {@link IEclipseVariableQuery#variable(IVariableBinding)}to determine the variable for a given binding.
 * @param binding An expression AST node.
 * @return Variable representing the result of the given expression.
 */
  protected Variable targetVariable(  ASTNode node){
    if (node instanceof Expression)     return tac.variable(node);
    if (node instanceof VariableDeclaration)     return tac.sourceVariable(((VariableDeclaration)node).resolveBinding());
    throw new IllegalArgumentException("Node does not have a target: " + node);
  }
  /** 
 * Calls   {@link #variable(Expression)} for every expression in <code>nodes</code>and returns the resulting variables in an immutable list.
 * @param nodes A list of expression AST nodes.
 * @return Variables representing the results of the given expression expressions.
 */
  protected List<Variable> variables(  List<Expression> nodes){
    result=new ArrayList<Variable>(nodes.size());
    for (    Expression e : nodes) {
      result.add(variable(e));
    }
    return Collections.unmodifiableList(result);
  }
  /** 
 * Returns the unqualified <code>this</code> variable for the receiver, if the surrounding method is an instance method.
 * @return the unqualified <code>this</code> variable for the receiver, ifthe surrounding method is an instance method; <code>null</code> otherwise.
 */
  protected ThisVariable receiverVariable(){
    return tac.thisVariable();
  }
  /** 
 * Helper method to access   {@link IEclipseVariableQuery#implicitThisVariable(IBinding)}to determine the implicit <b>this</b> variable for a method call or field access.
 * @param accessedElement The element being accessed with an implicit <b>this</b>. Must be a  {@link IMethodBinding} for a method or constructoror a  {@link IVariableBinding} for a field.
 * @return Implicit <b>this</b> variable for a method call or field access.
 */
  protected ThisVariable implicitThisVariable(  IBinding accessedElement){
    return tac.implicitThisVariable(accessedElement);
  }
  /** 
 * Helper method to access   {@link IEclipseVariableQuery#superVariable(Name)}to get the <b>super</b> variable (based on its qualifier).
 * @param qualifier Qualifier for <b>super</b> access; <code>null</code> for unqualified <b>super</b>.
 * @return <b>super</b> variable for the given qualifier.
 */
  protected SuperVariable superVariable(  Name qualifier){
    return tac.superVariable(qualifier);
  }
  /** 
 * Helper method to access   {@link IEclipseVariableQuery#typeVariable(ITypeBinding)}to get a type variable based on its binding.
 * @param binding A type binding.
 * @return Variable representing the given type.
 */
  protected TypeVariable typeVariable(  ITypeBinding binding){
    return tac.typeVariable(binding);
  }
  /** 
 * Format list of variables as comma-separated string.
 * @param args List of variables.
 * @return Comma-separated string representation of the variable list.
 */
  protected static String argsString(  List<Variable> args){
    StringBuffer result=new StringBuffer();
    boolean first=true;
    for (    Variable x : args) {
      if (first)       first=false;
 else       result.append(", ");
      result.append(x.toString());
    }
    return result.toString();
  }
  /** 
 * Use this method to transfer over an instruction.  This method performs double-dispatch to call the appropriate <code>transfer</code> method on the transfer function being passed.
 * @param < LE > Lattice element used in the transfer function.
 * @param tf Transfer function.
 * @param value Incoming lattice value.
 * @return Outgoing lattice value after transfering over this instruction.
 */
  public abstract <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value);
  /** 
 * Use this method to transfer over an instruction.  This method performs double-dispatch to call the appropriate <code>transfer</code> method on the transfer function being passed.
 * @param < LE > Lattice element used in the transfer function.
 * @param tf Transfer function.
 * @param labels Branch labels to consider.
 * @param value Incoming lattice value.
 * @return Outgoing lattice values for given labels after transfering over this instruction.
 */
  public abstract <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value);
  public IEclipseVariableQuery getTac(){
    Cloner cloner=new Cloner();
    tac=cloner.deepClone(tac);
    Cloner cloner=new Cloner();
    tac=cloner.deepClone(tac);
    return tac;
  }
  public List<Variable> getResult(){
    Cloner cloner=new Cloner();
    result=cloner.deepClone(result);
    Cloner cloner=new Cloner();
    result=cloner.deepClone(result);
    return result;
  }
}
