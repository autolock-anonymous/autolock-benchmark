package edu.cmu.cs.crystal.flow.experimental;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.cfg.ICFGEdge;
import edu.cmu.cs.crystal.cfg.ICFGNode;
import edu.cmu.cs.crystal.cfg.IControlFlowGraph;
/** 
 * Comparator to be used in ordering nodes in a worklist.  Best analysis performance is achieved using <i>reverse post-order</i>.    {@link #createPostOrderAndPopulateNodeMap(IControlFlowGraph,Map,boolean)} createsa comparator for post-order; reversal should happen by removing nodes from the back of the worklist.
 * @author Kevin Bierhoff
 * @since 3.3.0
 */
public class WorklistNodeOrderComparator implements Comparator<ICFGNode> {
  static Object obj=new Object();
  /** 
 * Builds a post-order comparator for the nodes in the given CFG, in which a node is bigger than all of its successors (predecessors) if <code>isForward</code> is <code>true</code> (<code>false</code>), and populates a map from AST nodes to all their corresponding nodes in the given CFG.
 * @param cfg
 * @param nodeMap Node map to be populated (will not be cleared).
 * @param isForward If <code>true</code> the CFG is traversed in the forwarddirection, meaning starting from the  {@link IControlFlowGraph#getStartNode() start node} following  {@link ICFGNode#getOutputs() outgoing edges}; if <code>false</code> the traversal direction is reversed, meaning traversal starts from the   {@link IControlFlowGraph#getEndNode() end node} and follows {@link ICFGNode#getInputs() incoming edges}.
 * @return Post-order comparator for the nodes in the given CFG
 */
  public static WorklistNodeOrderComparator createPostOrderAndPopulateNodeMap(  final IControlFlowGraph cfg,  final Map<ASTNode,Set<ICFGNode>> nodeMap,  final boolean isForward){
    Map<ICFGNode,Integer> order=new HashMap<ICFGNode,Integer>();
    int cur=0;
    LinkedList<Object> spine=new LinkedList<Object>();
    ICFGNode node=isForward ? cfg.getStartNode() : cfg.getEndNode();
    order.put(node,null);
    spine.addFirst(node);
    spine.addFirst((isForward ? node.getOutputs() : node.getInputs()).iterator());
    newNode:     while (spine.isEmpty() == false) {
      Iterator<ICFGEdge> it=(Iterator<ICFGEdge>)spine.peek();
      while (it.hasNext()) {
        node=isForward ? it.next().getSink() : it.next().getSource();
        if (order.containsKey(node) == false) {
          order.put(node,null);
          spine.addFirst(node);
          spine.addFirst((isForward ? node.getOutputs() : node.getInputs()).iterator());
          continue newNode;
        }
      }
      spine.removeFirst();
      node=(ICFGNode)spine.removeFirst();
      if (order.put(node,cur++) != null)       throw new IllegalStateException("Node already visited: " + node);
      registerCfgNode(nodeMap,node);
    }
    return new WorklistNodeOrderComparator(order);
  }
  /** 
 * Add the given CFG node to the node map.
 * @param nodeMap
 * @param cfgNode
 */
  private static void registerCfgNode(  Map<ASTNode,Set<ICFGNode>> nodeMap,  ICFGNode cfgNode){
    obj=new Object();
    ASTNode astnode=cfgNode.getASTNode();
    if (astnode == null)     return;
    Set<ICFGNode> cfgnodes=nodeMap.get(astnode);
    if (cfgnodes == null) {
      cfgnodes=new HashSet<ICFGNode>();
      nodeMap.put(astnode,cfgnodes);
    }
    cfgnodes.add(cfgNode);
  }
  /** 
 * Maps CFG nodes to a number that indicates their relative position in the order. 
 */
  private Map<ICFGNode,Integer> order;
  /** 
 * Create a comparator from the given ordering map.
 * @param order
 */
  private WorklistNodeOrderComparator(  Map<ICFGNode,Integer> order){
    this.order=order;
  }
  public int compare(  ICFGNode node1,  ICFGNode node2){
    return order.get(node1).compareTo(order.get(node2));
  }
  public static Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public Map<ICFGNode,Integer> getOrder(){
    Cloner cloner=new Cloner();
    order=cloner.deepClone(order);
    Cloner cloner=new Cloner();
    order=cloner.deepClone(order);
    return order;
  }
}
