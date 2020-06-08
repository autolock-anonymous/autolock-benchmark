package edu.cmu.cs.crystal.flow;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.tac.SourceVariable;
import edu.cmu.cs.crystal.tac.TACInstruction;
import edu.cmu.cs.crystal.tac.ThisVariable;
import edu.cmu.cs.crystal.tac.Variable;
/** 
 * This interface defines methods to map AST data structures to TAC variables. These methods can for instance be used by TAC analysis transfer functions. NEB: This used to be called ITACAnalysisContext, but I renamed it and added a common method getNode which was not previously part of this interface. 
 * @author Kevin Bierhoff
 * @author Nels Beckman
 * @see edu.cmu.cs.crystal.tac.ITACTransferFunction
 * @see edu.cmu.cs.crystal.tac.ITACBranchSensitiveTransferFunction
 */
public interface ITACFlowAnalysis<LE extends LatticeElement<LE>> extends IFlowAnalysis<LE> {
  /** 
 * Retrieves the analysis state that exists <b>before</b> analyzing the instruction. Before is respective to normal program flow and not the direction of the analysis.
 * @param instr		the {@link TACInstruction} of interest 
 * @return			the lattice that represents the analysis state before analyzing the instruction.  Or null if the node doesn't have a corresponding control flow node.
 */
  public LE getResultsBefore(  TACInstruction instr);
  /** 
 * Retrieves the analysis state that exists <b>after</b> analyzing the instruction. After is respective to normal program flow and not the direction of the analysis.
 * @param instr		the {@link TACInstruction} of interest 
 * @return			the lattice that represents the analysis state before analyzing the instruction.  Or null if the node doesn't have a corresponding control flow node.
 */
  public LE getResultsAfter(  TACInstruction instr);
  /** 
 * Retrieves the analysis state that exists <b>before</b> analyzing the instruction. Before is respective to normal program flow and not the direction of the analysis.
 * @param node		the {@link TACInstruction} of interest 
 * @return			the lattice that represents the analysis state after analyzing the instruction.  Or null if the node doesn't have a corresponding control flow node.
 */
  public IResult<LE> getLabeledResultsBefore(  TACInstruction instr);
  /** 
 * Retrieves the analysis state that exists <b>after</b> analyzing the instruction. After is respective to normal program flow and not the direction of the analysis.
 * @param node		the {@link TACInstruction} of interest 
 * @return			the lattice that represents the analysis state after analyzing the instruction.  Or null if the node doesn't have a corresponding control flow node.
 */
  public IResult<LE> getLabeledResultsAfter(  TACInstruction instr);
  /** 
 * Returns the TAC variable for a given ASTNode. It is the caller's responsibility to make sure to call this method only while the method surrounding the given node is analyzed.
 * @param node AST node in the previously analyzed method.
 * @return The TAC variable for a given ASTNode.
 */
  public Variable getVariable(  ASTNode node);
  /** 
 * Returns the <b>this</b> variable for a given method. It is the caller's responsibility to make sure to call this method only while the given method is analyzed.
 * @param methodDecl The method for which <b>this</b> is requested.
 * @return The <b>this</b> variable for the given method. 
 */
  public ThisVariable getThisVariable(  MethodDeclaration methodDecl);
  /** 
 * Returns the implicit <b>this</b> variable for accessing a given method or field  <i>after previously analyzing the method surrounding the access.</i> It is the caller's responsibility to make sure to call this method only when analysis results for the method surrounding the access are available.
 * @param accessedElement
 * @return
 */
  public ThisVariable getImplicitThisVariable(  IBinding accessedElement);
  /** 
 * Returns the variable for a given parameter or local. It is the caller's responsibility to make sure to call this method only while the method declaring the parameter or local is analyzed.
 * @param varBinding Binding of a local or parameter.
 * @return the variable for the given parameter or local.
 */
  public SourceVariable getSourceVariable(  IVariableBinding varBinding);
  /** 
 * Returns for error-reporting purposes a AST node that surrounds or is represented by a variable mentioned in a given instruction.
 * @param x A variable.
 * @param instruction Instruction that mentions <code>x</code>
 * @return A AST node that surrounds or is represented bya variable mentioned in a given instruction.
 */
  public ASTNode getNode(  Variable x,  TACInstruction instruction);
}
