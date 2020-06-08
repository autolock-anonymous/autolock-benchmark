package edu.cmu.cs.crystal.tac;
/** 
 * x = y.m(z1, ..., zn), where m is a method and y is possibly a type variable, in the case of a static method call.
 * @author Kevin Bierhoff
 * @see #isStaticMethodCall() determine whether this is a static method call
 * @see #isSuperCall() determine whether this is a <b>super</b> call.
 * @see ContructorCallInstruction calls between constructors
 */
public interface MethodCallInstruction extends InvocationInstruction {
  /** 
 * Returns the receiver of this call, if any.
 * @return the receiver of this call, or <code>null</code> if this is a static method call.
 * @see #isStaticMethodCall()
 */
  public Variable getReceiverOperand();
  /** 
 * Indicates whether this is a super-call
 * @return <code>true</code> if this is a super-call, <code>false</code> otherwise.
 * @see org.eclipse.jdt.core.dom.SuperMethodInvocation
 */
  public boolean isSuperCall();
  /** 
 * Indicates whether this is a call to a static method.
 * @return <code>true</code> if this is a call to a static method, <code>false</code> otherwise.
 */
  public boolean isStaticMethodCall();
  /** 
 * Returns the name of the called method.
 * @return the name of the called method.
 */
  public String getMethodName();
}
