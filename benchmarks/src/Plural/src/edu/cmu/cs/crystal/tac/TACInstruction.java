package edu.cmu.cs.crystal.tac;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * Abstract base class for 3-Address-Code instructions built from Eclipse AST nodes.  {@link ITACTransferFunction} lists subclasses that define the differenttypes of instructions.  Additional (abstract and/or package-private) classes  simplify 3-Address-Code generation from AST nodes.  
 * @author Kevin Bierhoff
 * @see ITACTransferFunction
 */
public interface TACInstruction {
  /** 
 * Returns the node this instruction is for.  Usually, one instruction exists per AST node, but can be more when AST nodes are desugared, such as for post-increment. Subtypes may give more specific information on the type of AST node returned, but more specific typing is not guaranteed due to possible evolution of the Eclipse AST or these interfaces.
 * @return The AST node this instruction is for.
 */
  public abstract ASTNode getNode();
  /** 
 * Use this method to transfer over an instruction.  This method performs double-dispatch to call the appropriate <code>transfer</code> method on the transfer function being passed.
 * @param < LE > Lattice element used in the transfer function.
 * @param tf Transfer function.
 * @param value Incoming lattice value.
 * @return Outgoing lattice value after transferring over this instruction.
 */
  public abstract <LE extends LatticeElement<LE>>LE transfer(  ITACTransferFunction<LE> tf,  LE value);
  /** 
 * Use this method to transfer over an instruction.  This method performs double-dispatch to call the appropriate <code>transfer</code> method on the transfer function being passed.
 * @param < LE > Lattice element used in the transfer function.
 * @param tf Transfer function.
 * @param labels Branch labels to consider.
 * @param value Incoming lattice value.
 * @return Outgoing lattice values for given labels after transferring over this instruction.
 */
  public abstract <LE extends LatticeElement<LE>>IResult<LE> transfer(  ITACBranchSensitiveTransferFunction<LE> tf,  List<ILabel> labels,  LE value);
}
