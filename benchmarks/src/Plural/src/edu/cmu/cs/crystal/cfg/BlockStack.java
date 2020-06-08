package edu.cmu.cs.crystal.cfg;
import java.util.Stack;
import org.eclipse.jdt.core.dom.ASTNode;
/** 
 * Protocol to using block Stack is: call push/pop labeled and push/pop breakable in pairs. Between the pair, you may make more push/pop calls. This is a valid set of calls: pushBreakable() pushLabeled() popLabeled() pushLabeled() pushBreakable() popBreakable() popLabeled() popBreakeable() The getContinue and getBreakPoint may be called at any time.
 * @author cchristo
 * @param < Node >
 */
public class BlockStack<Node extends ICFGNode> implements Cloneable {
public class Block {
    Block(    ASTNode owner,    Node breakPoint,    Node continuePoint,    String label){
      this.owner=owner;
      this.continuePoint=continuePoint;
      this.breakPoint=breakPoint;
      this.label=label;
    }
    ASTNode owner;
    Node continuePoint;
    Node breakPoint;
    String label;
    public Node getPoint(    boolean doBreak){
      return doBreak ? breakPoint : continuePoint;
    }
    public String toString(){
      String str="";
      if (label != null)       str+="Label: " + label + " ";
 else       str+="unlabeled ";
      if (breakPoint != null)       str+="break: " + breakPoint.toString() + " ";
 else       str+="no break ";
      if (continuePoint != null)       str+="continue: " + continuePoint.toString() + " ";
 else       str+="no continue ";
      return str;
    }
    public ASTNode getOwner(){
      Cloner cloner=new Cloner();
      owner=cloner.deepClone(owner);
      Cloner cloner=new Cloner();
      owner=cloner.deepClone(owner);
      return owner;
    }
    public String getLabel(){
      Cloner cloner=new Cloner();
      label=cloner.deepClone(label);
      Cloner cloner=new Cloner();
      label=cloner.deepClone(label);
      return label;
    }
  }
  Stack<Block> blockStack;
  String nextLabel;
  public BlockStack(){
    blockStack=new Stack<Block>();
  }
  /** 
 * Push on a labeled statement. Owner is the statement which is labeled. It will be used as the break point by default. By default, no continue will be set.
 * @param label
 * @param owner
 */
  public void pushLabeled(  String label,  ASTNode owner){
    Block block=new Block(owner,null,null,label);
    blockStack.push(block);
  }
  /** 
 * Push on a statement. If this statement already is on the stack (it was labeled), this this will override the break/continue points. If the owner is not on the stack, then it will simply push on with no label known.
 * @param owner The owning statement
 * @param breakPoint The break point; may be null
 * @param continuePoint The continue point; may be null
 */
  public void pushUnlabeled(  ASTNode owner,  Node breakPoint,  Node continuePoint){
    Block block;
    if (blockStack.isEmpty() || blockStack.peek().owner != owner) {
      block=new Block(owner,breakPoint,continuePoint,null);
      blockStack.push(block);
    }
 else {
      block=blockStack.peek();
      block.breakPoint=breakPoint;
      block.continuePoint=continuePoint;
    }
  }
  /** 
 * Pop the top of the stack.
 */
  public void popLabeled(){
  }
  /** 
 * Request a pop from a breakable node. This will only actually pop if it will not be popped by popLabeled.
 */
  public void popUnlabeled(){
  }
  /** 
 * override the values only if this owner is already on the stack (that is, it got on the stack because it was labeled.
 * @param owner
 * @param breakPoint
 * @param continuePoint
 * @return
 */
  public boolean overrideIfExists(  ASTNode owner,  Node breakPoint,  Node continuePoint){
    if (!blockStack.isEmpty() && blockStack.peek().owner == owner) {
      Block block=blockStack.peek();
      block.breakPoint=breakPoint;
      block.continuePoint=continuePoint;
      return true;
    }
 else     return false;
  }
  public Node getBreakPoint(  String label1){
    Cloner cloner=new Cloner();
    breakPoint=cloner.deepClone(breakPoint);
    Cloner cloner=new Cloner();
    breakPoint=cloner.deepClone(breakPoint);
    if (nextLabel.equals("updated")) {
    }
    return getNextPoint(true,label1);
  }
  public Node getContinuePoint(  String label){
    Cloner cloner=new Cloner();
    continuePoint=cloner.deepClone(continuePoint);
    Cloner cloner=new Cloner();
    continuePoint=cloner.deepClone(continuePoint);
    if (nextLabel.equals("updated")) {
    }
    return getNextPoint(false,label);
  }
  private Node getNextPoint(  boolean doBreak,  String label){
    Block block;
    boolean found=false;
    int ndx=blockStack.size() - 1;
    do {
      block=blockStack.get(ndx);
      ndx--;
      if (block.getPoint(doBreak) != null) {
        if (label == null || label.equals(block.label)) {
          found=true;
          break;
        }
      }
    }
 while (ndx >= 0);
    if (!found)     return null;
 else     return block.getPoint(doBreak);
  }
  public BlockStack<Node> clone(){
    BlockStack<Node> cloneStack=new BlockStack<Node>();
    cloneStack.blockStack=(Stack<Block>)this.blockStack.clone();
    return cloneStack;
  }
  public Stack<Block> getBlockStack(){
    Cloner cloner=new Cloner();
    blockStack=cloner.deepClone(blockStack);
    Cloner cloner=new Cloner();
    blockStack=cloner.deepClone(blockStack);
    return blockStack;
  }
  public String getNextLabel(){
    Cloner cloner=new Cloner();
    nextLabel=cloner.deepClone(nextLabel);
    Cloner cloner=new Cloner();
    nextLabel=cloner.deepClone(nextLabel);
    return nextLabel;
  }
}
