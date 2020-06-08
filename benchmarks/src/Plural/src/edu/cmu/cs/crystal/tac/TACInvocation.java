package edu.cmu.cs.crystal.tac;
import java.util.List;
import org.eclipse.jdt.core.dom.IMethodBinding;
/** 
 * Interface defining instructions that represent some kind of invocation.  
 * @author Kevin Bierhoff
 */
public interface TACInvocation extends TACInstruction {
  /** 
 * Returns the operands for the arguments passed into an invocation.
 * @return the operands for the arguments passed into an invocation.
 */
  public List<Variable> getArgOperands();
  /** 
 * Returns the method binding for this invocation.
 * @return the method binding for this invocation.
 */
  public IMethodBinding resolveBinding();
}
