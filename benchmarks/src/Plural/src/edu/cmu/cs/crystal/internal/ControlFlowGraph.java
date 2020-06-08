package edu.cmu.cs.crystal.internal;
import java.util.*;
import org.eclipse.jdt.core.dom.*;
public class ControlFlowGraph {
  /** 
 * stored mappings between ASTNodes and ControlFlowNodes
 */
  protected static Map<ASTNode,ControlFlowNode> controlFlowNodes=null;
  /** 
 * Adds the mapping from ASTNode to ControlFlowNode for later lookup
 * @param astNode	the ASTNode
 * @param cfn	the ControlFlowNode
 */
  public static void addControlFlowNode(  ASTNode astNode,  ControlFlowNode cfn){
    if (controlFlowNodes == null)     controlFlowNodes=new HashMap<ASTNode,ControlFlowNode>();
    controlFlowNodes.put(astNode,cfn);
  }
  /** 
 * Removes a mapping from the ASTNode to ControlFlowNode map.  It is important to remove mappings that get removed from the CFG.
 * @param astNode	the node to remove
 */
  public static void removeControlFlowNode(  ASTNode astNode){
    if (controlFlowNodes == null || astNode == null)     return;
    controlFlowNodes.remove(astNode);
  }
  /** 
 * Retrieves the ControlFlowNode that is associated with the ASTNode. If the ASTNode does not have a direct CFN mapping, then find a parent node that does.  Also responsible for caching.
 * @param astNode	the ASTNode
 * @return	the ControlFlowNode, or null if a CFG was never created.
 */
  public static ControlFlowNode getControlFlowNode(  ASTNode inNode){
    if (controlFlowNodes == null)     return null;
    if (inNode == null)     throw new CrystalRuntimeException("null ASTNode argument");
    ASTNode node=inNode;
    ControlFlowNode cfn;
    while (node != null) {
      if (controlFlowNodes.containsKey(node)) {
        cfn=controlFlowNodes.get(node);
        if (node != inNode)         addControlFlowNode(node,cfn);
        return cfn;
      }
      node=node.getParent();
    }
    return null;
  }
  /** 
 * the start dummy node in the CFG
 */
  protected ControlFlowNode startNode=null;
  /** 
 * the end dummy node in the CFG
 */
  protected ControlFlowNode endNode=null;
  /** 
 * Constructor.  Initializes an empty CFG.
 */
  public ControlFlowGraph(  ASTNode node){
    if (node == null)     throw new CrystalRuntimeException("null node");
    startNode=new ControlFlowNode(this,null);
    endNode=new ControlFlowNode(this,null);
    ControlFlowNode cfn=startNode.newControlFlowNode(node);
    startNode.addEdge(ControlFlowNode.Direction.FORWARDS,cfn);
    cfn.addEdge(ControlFlowNode.Direction.FORWARDS,endNode);
    cfn.evaluate();
  }
  /** 
 * Retrieves the start dummy node for the graph
 * @return	the starting node
 */
  public ControlFlowNode getStartNode(){
    Cloner cloner=new Cloner();
    startNode=cloner.deepClone(startNode);
    Cloner cloner=new Cloner();
    startNode=cloner.deepClone(startNode);
    return startNode;
  }
  /** 
 * Retrieves the end dummy node for the graph
 * @return	the end node
 */
  public ControlFlowNode getEndNode(){
    Cloner cloner=new Cloner();
    endNode=cloner.deepClone(endNode);
    Cloner cloner=new Cloner();
    endNode=cloner.deepClone(endNode);
    return endNode;
  }
  /** 
 * Creates a short textual representation of the CFG. (Currently only returns "A CFG")
 * @return	a string representing this CFG
 */
  public String toString(){
    return "A CFG";
  }
  /** 
 * Generates a set of all nodes in the CFG
 * @return	the set of all nodes in the CFG
 */
  public Set<ControlFlowNode> getNodeSet(){
    Set<ControlFlowNode> set=new HashSet<ControlFlowNode>();
    if (startNode != null)     buildNodeList(startNode,set);
    return set;
  }
  /** 
 * A recursive helper method to getNodeSet().  This method performs the CFG traversal to generate the set. 
 * @param cfn	the node to add & recursively visit children
 * @param set	the set to add all nodes into
 */
  private void buildNodeList(  ControlFlowNode cfn,  Set<ControlFlowNode> set){
    Iterator<ControlFlowNode> i=cfn.getIterator(ControlFlowNode.Direction.FORWARDS);
    ControlFlowNode next;
    for (; i.hasNext(); ) {
      next=i.next();
      if (set.contains(next))       continue;
 else {
        set.add(next);
        buildNodeList(next,set);
      }
    }
  }
  public static Map<ASTNode,ControlFlowNode> getControlFlowNodes(){
    Cloner cloner=new Cloner();
    controlFlowNodes=cloner.deepClone(controlFlowNodes);
    Cloner cloner=new Cloner();
    controlFlowNodes=cloner.deepClone(controlFlowNodes);
    return controlFlowNodes;
  }
}
