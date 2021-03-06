package edu.cmu.cs.crystal.cfg;
import java.util.Stack;
import org.eclipse.jdt.core.dom.ITypeBinding;
/** 
 * This class maintains the exception information for the CFG algorithm while it is building the graph. It is notified of certain points in the cfg algorithm (such as finding a finally or catch), and it can be queried for information about the finallys at a given point or the nearest catch. The ExceptionMap maintains links to ASTNodes and CFGNodes. It should be deleted when the CFG is done with it.
 * @author cchristo
 * @param < Node >
 */
public class ExceptionMap<Node extends ICFGNode> implements Cloneable {
private class CatchBlock {
    Node catchNode;
    ITypeBinding exception;
    Node finallyNode;
    public Node getFinallyNode(){
      Cloner cloner=new Cloner();
      finallyNode=cloner.deepClone(finallyNode);
      Cloner cloner=new Cloner();
      finallyNode=cloner.deepClone(finallyNode);
      return finallyNode;
    }
    public ITypeBinding getException(){
      Cloner cloner=new Cloner();
      exception=cloner.deepClone(exception);
      Cloner cloner=new Cloner();
      exception=cloner.deepClone(exception);
      return exception;
    }
  }
  private Stack<Node> finallyStack;
  private Stack<CatchBlock> catchStack;
  public ExceptionMap(){
    catchStack=new Stack<CatchBlock>();
    finallyStack=new Stack<Node>();
  }
  /** 
 * Notify the exception map that we've found a try node, and we have a finally for it. Finallys should be pushed before their corresponding catches. If a try has no finally, it should push on null.
 * @param finallyNode
 */
  public void pushFinally(  Node finallyNode){
    finallyStack.push(finallyNode);
  }
  /** 
 * Notify the exception map that we have an exception, and it is catching a particular type of exception. Catches should be pushed in the opposite order (bottom first).
 * @param catchNode
 * @param exception
 */
  public void pushCatch(  Node catchNode,  ITypeBinding exception){
    CatchBlock block=new CatchBlock();
    block.catchNode=catchNode;
    block.exception=exception;
    if (!finallyStack.isEmpty())     block.finallyNode=finallyStack.peek();
 else     block.finallyNode=null;
    catchStack.push(block);
  }
  /** 
 * Finish a try.
 * @return the finally node
 */
  public Node popFinally(){
    return finallyStack.pop();
  }
  /** 
 * Finish a catch.
 * @return
 */
  public Node popCatch(){
    return catchStack.pop().catchNode;
  }
  /** 
 * @param exception An exception to find a catch node for, considering subtyping
 * @return the catch node that will catch this exception, or null if one could not be found
 */
  public Node getCatchNode(  ITypeBinding exception){
    Cloner cloner=new Cloner();
    catchNode=cloner.deepClone(catchNode);
    Cloner cloner=new Cloner();
    catchNode=cloner.deepClone(catchNode);
    for (int ndx=catchStack.size() - 1; ndx >= 0; ndx--) {
      CatchBlock block=catchStack.get(ndx);
      if (exception.isSubTypeCompatible(block.exception))       return block.catchNode;
    }
    return null;
  }
  /** 
 * Returns a stack of nodes that are the finally nodes up to the exception. This will not include the finally node at the exception catch. The returned stack will be that the nearest finally is at the top of the stack. If the exception is null, then this returns the entire stack of nodes.
 * @param exceptionToStopAt The exception we should stop at. This might be a subtype of the actual exception that we stop at.
 * @return The stack to the exception point, or the entire stack if the exception is null.
 */
  public Stack<Node> getFinallyToException(  ITypeBinding exceptionToStopAt){
    Stack<Node> newStack=new Stack<Node>();
    CatchBlock block=null;
    if (exceptionToStopAt == null)     return (Stack<Node>)finallyStack.clone();
    for (int ndx=catchStack.size() - 1; ndx >= 0; ndx--) {
      block=catchStack.get(ndx);
      if (exceptionToStopAt.isSubTypeCompatible(block.exception))       break;
    }
    int ndx=0;
    if (block.finallyNode == null)     ndx=0;
 else     ndx=finallyStack.size() - finallyStack.search(block.finallyNode) + 1;
    for (    Node node : finallyStack.subList(ndx,finallyStack.size()))     newStack.push(node);
    return newStack;
  }
  public Object clone() throws CloneNotSupportedException {
    ExceptionMap map=(ExceptionMap)super.clone();
    map.catchStack=(Stack)catchStack.clone();
    map.finallyStack=(Stack)finallyStack.clone();
    return map;
  }
  public Stack<Node> getFinallyStack(){
    Cloner cloner=new Cloner();
    finallyStack=cloner.deepClone(finallyStack);
    Cloner cloner=new Cloner();
    finallyStack=cloner.deepClone(finallyStack);
    return finallyStack;
  }
  public Stack<CatchBlock> getCatchStack(){
    Cloner cloner=new Cloner();
    catchStack=cloner.deepClone(catchStack);
    Cloner cloner=new Cloner();
    catchStack=cloner.deepClone(catchStack);
    return catchStack;
  }
}
