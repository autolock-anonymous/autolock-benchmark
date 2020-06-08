package edu.cmu.cs.crystal.internal;
import java.util.*;
import org.eclipse.jdt.core.dom.*;
/** 
 * Represents one node in a Control Flow Graph.
 * @author David Dickey
 */
public class ControlFlowNode {
  /** 
 * the ASTNode associated with this node
 */
  protected ASTNode astNode=null;
  /** 
 * the list of forward nodes
 */
  protected List<ControlFlowNode> forwardEdges=null;
  /** 
 * the list of backward nodes
 */
  protected List<ControlFlowNode> backwardEdges=null;
  /** 
 * the graph that this node belongs to 
 */
  protected ControlFlowGraph graph=null;
  /** 
 * Looping nodes have one path into the loop and one path exiting the loop.  If not null exitNode stores the node exiting the loop.
 */
  protected ControlFlowNode loopBreakNode=null;
  protected ControlFlowNode loopContinueNode=null;
  /** 
 * this stores the first CFN child of this node This is useful for looking up results BEFORE  a stucture like a loop, for example. 
 */
  protected ControlFlowNode firstChild=null;
  public enum Direction;
{
  }
  public Direction changeDirection(){
    return (this == FORWARDS) ? BACKWARDS : FORWARDS;
  }
  public String toString(){
    return (this == FORWARDS) ? "FORWARDS" : "BACKWARDS";
  }
{
  }
  /** 
 * Contructor.  Initializes the node.
 * @param cfg	the graph this node belongs to
 * @param inASTNode	the ASTNode to associate to this CFN
 */
  public ControlFlowNode(  ControlFlowGraph cfg,  ASTNode inASTNode){
    graph=cfg;
    astNode=inASTNode;
    forwardEdges=null;
    backwardEdges=null;
    if (inASTNode != null)     ControlFlowGraph.addControlFlowNode(inASTNode,this);
  }
  /** 
 * Creates a new ControlFlowNode from another ControlFlowNode.  Use this method when creating new nodes in the same graph as another node.
 * @param node	the ASTNode to create a CFN for
 * @return	the ControlFlowNode associated with this graph and the node
 */
  public ControlFlowNode newControlFlowNode(  ASTNode node){
    if (node == null)     throw new CrystalRuntimeException("should not create a dummy node using this method");
    ControlFlowNode cfn=new ControlFlowNode(graph,node);
    ControlFlowGraph.addControlFlowNode(node,cfn);
    return cfn;
  }
  /** 
 * Evaluates this ControlFlowNode for possible subnodes based on this node's ASTNode.  For example: a CFN containing a MethodDeclaration will generate new CFNs for each statement in the MethodDeclaration's body.
 */
  public void evaluate(){
    if (graph == null)     throw new CrystalRuntimeException("this ControlFlowNode must belong to a ControlFlowGraph before evaluating");
    ControlFlowVisitor visitor=new ControlFlowVisitor(this);
    visitor.performVisit();
  }
  /** 
 * Used to identify dummy nodes 
 * @return true if this node is a Dummy node, false otherwise 
 */
  public boolean isDummy(){
    return astNode == null;
  }
  /** 
 * Retrieves the ASTNode associated with this ControlFlowNode
 * @return	the ASTNode for this CFN
 */
  public ASTNode getASTNode(){
    Cloner cloner=new Cloner();
    astNode=cloner.deepClone(astNode);
    Cloner cloner=new Cloner();
    astNode=cloner.deepClone(astNode);
    return astNode;
  }
  /** 
 * loop paths are CFN pointers that record the edge that enters the loop and the edge that exits the loop.  This is used to  assist in break/continue/return jumps.
 * @param enter	the node that enters the loop
 * @param exit	the node that exits the loop
 */
  public void setLoopPaths(  ControlFlowNode enter,  ControlFlowNode exit){
    loopContinueNode=enter;
    loopBreakNode=exit;
  }
  /** 
 * Stores the CFN of the first CFN that is a child of this CFN 
 * @param child
 */
  public void setFirstChild(  ControlFlowNode child){
    firstChild=child;
  }
  /** 
 * Retrieves the ControlFlowGraph 
 * @return	the ControlFlowGraph that contains this node
 */
  public ControlFlowGraph getControlFlowGraph(){
    return graph;
  }
  /** 
 * Retrieves an iterator for either the forward or backward nodes in the CFG.
 * @param direction	the direction to retrieve the iterator for
 * @return	the edges iterator
 */
  public Iterator<ControlFlowNode> getIterator(  Direction direction){
    if (direction == Direction.FORWARDS && forwardEdges != null)     return forwardEdges.iterator();
 else     if (direction == Direction.BACKWARDS && backwardEdges != null)     return backwardEdges.iterator();
    return null;
  }
  /** 
 * Retrieves the only forward or backward node from this node. If there is more than one node in the direction, then an exception is thrown.
 * @param direction	the direction to retrieve the node from
 * @return	the node
 */
  public ControlFlowNode getNode(  Direction direction){
    List<ControlFlowNode> list=getEdges(direction);
    if (list == null)     throw new CrystalRuntimeException("No " + direction + " edges");
    if (list.size() != 1) {
      throw new CrystalRuntimeException("There must only be one " + direction + " edge");
    }
    return list.get(0);
  }
  /** 
 * Retrieves the number of edges in a direction.
 * @param direction	the direction to count
 * @return	the number of edges
 */
  public int getNumberOfEdges(  Direction direction){
    if (direction == Direction.FORWARDS && forwardEdges != null)     return forwardEdges.size();
 else     if (direction == Direction.BACKWARDS && backwardEdges != null)     return backwardEdges.size();
    return 0;
  }
  /** 
 * Adds an edge from this node to another. 
 * @param direction	the direction to add the edge to
 * @param to	the node to add
 */
  public void addEdge(  Direction direction,  ControlFlowNode node){
    this.addNode(direction,node);
    node.addNode(direction.changeDirection(),this);
  }
  /** 
 * Removes an edge of this node.
 * @param direction	the direction to remove the edge from
 * @param node	the node to remove the edge from
 */
  protected void removeEdge(  Direction direction,  ControlFlowNode node){
    this.removeNode(direction,node);
    node.removeNode(direction.changeDirection(),this);
  }
  /** 
 * Clears all edges in one direction from this node
 */
  protected void removeEdges(  Direction direction){
    Iterator<ControlFlowNode> i=getIterator(direction);
    if (i == null)     return;
    ControlFlowNode cfn;
    while (i.hasNext()) {
      cfn=i.next();
      i.remove();
      cfn.removeEdge(direction.changeDirection(),this);
    }
    if (direction == Direction.FORWARDS)     forwardEdges=null;
 else     backwardEdges=null;
  }
  /** 
 * Retrieves the edge list
 * @param direction	the direction whose list we want
 * @return	the list of edges
 */
  private List<ControlFlowNode> getEdges(  Direction direction){
    return (direction == Direction.FORWARDS) ? forwardEdges : backwardEdges;
  }
  /** 
 * Adds a node to one of the edge lists.  This creates a one way link in the CFG.
 * @param direction	the direction to add the node to
 * @param cfn	the node to add
 */
  private void addNode(  Direction direction,  ControlFlowNode cfn){
    if (this == cfn)     throw new CrystalRuntimeException("ControlFlow Self Loop Detected");
    if (direction == Direction.FORWARDS) {
      if (forwardEdges == null)       forwardEdges=new LinkedList<ControlFlowNode>();
      forwardEdges.add(cfn);
    }
 else {
      if (backwardEdges == null)       backwardEdges=new LinkedList<ControlFlowNode>();
      backwardEdges.add(cfn);
    }
  }
  /** 
 * Removes a node from one of the edge lists.
 * @param direction	the direction to remove the link from
 * @param cfn	the node to add
 */
  private void removeNode(  Direction direction,  ControlFlowNode cfn){
    if (direction == Direction.FORWARDS && forwardEdges != null)     forwardEdges.remove(cfn);
 else     if (direction == Direction.BACKWARDS && backwardEdges != null)     backwardEdges.remove(cfn);
  }
  /** 
 * Removes this Control Flow Node from the tree, connecting edges appropriately
 */
  public void remove(){
    if (backwardEdges != null && forwardEdges != null) {
      Iterator<ControlFlowNode> bi=backwardEdges.iterator();
      Iterator<ControlFlowNode> fi=backwardEdges.iterator();
      ControlFlowNode backwardNode;
      ControlFlowNode forwardNode;
      while (bi.hasNext()) {
        backwardNode=bi.next();
        bi.remove();
        backwardNode.removeNode(Direction.FORWARDS,this);
        while (fi.hasNext()) {
          forwardNode=fi.next();
          fi.remove();
          forwardNode.removeEdge(Direction.BACKWARDS,this);
          backwardNode.addEdge(Direction.FORWARDS,forwardNode);
        }
      }
      removeEdges(Direction.FORWARDS);
      removeEdges(Direction.BACKWARDS);
    }
 else     if (backwardEdges == null && forwardEdges != null) {
      throw new CrystalRuntimeException("[" + toString() + "] was dangling from the CFG with only forward edges");
    }
 else     if (backwardEdges != null && forwardEdges == null) {
      throw new CrystalRuntimeException("[" + toString() + "] was dangling from the CFG with only backward edges");
    }
    ControlFlowGraph.removeControlFlowNode(astNode);
  }
  /** 
 * Take all edges and move them to another node.  This operation removes all edges from this node, and creates edges to the  provided node.
 * @param direction the direction to move edges from
 * @param node	the node to move edges to
 */
  public void moveEdges(  Direction direction,  ControlFlowNode node){
    Iterator<ControlFlowNode> i=getIterator(direction);
    ControlFlowNode cfn;
    if (i == null)     return;
    while (i.hasNext()) {
      cfn=i.next();
      i.remove();
      cfn.removeNode(direction.changeDirection(),this);
      node.addEdge(direction,cfn);
    }
    if (direction == Direction.FORWARDS)     forwardEdges=null;
 else     backwardEdges=null;
  }
  /** 
 * Inserts a node between this node and all its subsequent nodes depending on the direction.
 * @param direction	the direction to insert into
 * @param insertNode	the node to insert
 */
  public void insertNode(  Direction direction,  ControlFlowNode insertNode){
    Iterator<ControlFlowNode> i=getIterator(direction);
    ControlFlowNode cfn;
    if (i == null)     throw new CrystalRuntimeException("no " + direction + " nodes from "+ this);
    while (i.hasNext()) {
      cfn=i.next();
      i.remove();
      cfn.removeNode(direction.changeDirection(),this);
      insertNode.addEdge(direction,cfn);
    }
    addEdge(direction,insertNode);
  }
  /** 
 * Converts this node into a string representation
 */
  public String toString(){
    int sampleSize=16;
    String p, temp;
    if (astNode == null)     return "[Dummy CFN]";
    p="[" + astNode.getClass().getSimpleName() + "]";
    temp=astNode.toString().replace("\n",",");
    if (temp.length() > sampleSize)     p+=" \"" + temp.substring(0,sampleSize - 3) + "...\"";
 else     p+=" \"" + temp + "\"";
    return p;
  }
  /** 
 * Populates a string with a multi-line textual representation of the control flow. Useful for printing out the structure of the control flow.
 * @param direction	sets the traversal direction
 * @return	a textual control flow representation 
 */
  public String toStringGraphOverload(  Direction direction){
    Set<ControlFlowNode> set=new HashSet<ControlFlowNode>();
    return toStringGraph(this,0,set,direction);
  }
  /** 
 * A recursive helper method for generating the control flow string
 * @param cfn	the node to print and further traverse
 * @param depth	the current depth
 * @param seen	a set of nodes already visited, and to not visit again
 * @param isForward	sets the traversal direction
 * @return	a textual control flow representation
 */
  private static String toStringGraph(  ControlFlowNode cfn,  int depth,  Set<ControlFlowNode> seen,  Direction direction){
    String s="";
    if (cfn == null)     return "";
    for (int x=0; x < depth; x++)     s+=" ";
    if (seen.contains(cfn)) {
      s+=cfn.toString() + " (...)\n";
      return s;
    }
 else {
      s+=cfn.toString() + "\n";
      seen.add(cfn);
    }
    List<ControlFlowNode> list=cfn.getEdges(direction);
    if (list == null)     return s;
    for (    ControlFlowNode tempNode : list) {
      s+=toStringGraph(tempNode,depth + 1,seen,direction);
    }
    return s;
  }
  /** 
 * Searches for the first ASTNode type in the CFG.
 * @param direction	the direction to look
 * @param astNodeType	the node type to look for (see ASTNode.getNodeType())
 * @return	the first node of the type, or null if none found
 */
  public ControlFlowNode findNode(  Direction direction,  int astNodeType){
    if (backwardEdges == null || backwardEdges.size() == 0)     throw new CrystalRuntimeException("no backwardEdges");
    if (forwardEdges == null || forwardEdges.size() == 0)     throw new CrystalRuntimeException("no forwardEdges");
    ControlFlowNode cfn;
    if (loopBreakNode == null)     cfn=getNode(direction);
 else     cfn=loopBreakNode;
    if (cfn == null || cfn.isDummy())     return null;
    if (cfn.astNode.getNodeType() == astNodeType)     return cfn;
 else     return cfn.findNode(direction,astNodeType);
  }
  /** 
 * A recursive method for correcting the structure of a CFG in the context of a return statement.  After this method completes, the return statement will be properly connected to the methodDeclaration.
 * @return	the methodDeclaration to return to
 */
  public ControlFlowNode returning(){
    ControlFlowNode cfn=getNode(Direction.FORWARDS);
    ControlFlowNode resultCFN;
    if (cfn.astNode.getNodeType() != ASTNode.METHOD_DECLARATION) {
      int size=cfn.getNumberOfEdges(Direction.BACKWARDS);
      if (size == 0)       throw new CrystalRuntimeException("one way edge detected");
 else       if (size == 1) {
        resultCFN=cfn.returning();
        cfn.remove();
      }
 else       resultCFN=cfn.findNode(Direction.FORWARDS,ASTNode.METHOD_DECLARATION);
    }
 else     resultCFN=cfn;
    if (astNode.getNodeType() != ASTNode.RETURN_STATEMENT)     return resultCFN;
    if (resultCFN == null || resultCFN.astNode.getNodeType() != ASTNode.METHOD_DECLARATION)     throw new CrystalRuntimeException("could not find method declaration to return from");
    if (resultCFN == cfn)     return cfn;
    removeEdges(Direction.FORWARDS);
    addEdge(Direction.FORWARDS,resultCFN);
    return resultCFN;
  }
  /** 
 * A recursive method for correcting the structure of a CFG in the context of a break statement.  After this method completes, the break statement will be properly connected to whatever the target of the break is.
 * @param label	the label of the break, or null if none
 * @param keepRemovingNodes	if true then remove forward nodes
 * @return	the target of the break
 */
  public ControlFlowNode breaking(  String label,  boolean keepRemovingNodes){
    ControlFlowNode nextCFN;
    if (loopBreakNode == null)     nextCFN=getNode(Direction.FORWARDS);
 else     nextCFN=loopBreakNode;
    if (nextCFN.isDummy())     throw new CrystalRuntimeException("reached end of graph while breaking");
    int size=nextCFN.getNumberOfEdges(Direction.BACKWARDS);
    int cfnType=nextCFN.astNode.getNodeType();
    ControlFlowNode resultCFN;
    String nextCFNLabel=null;
    if (cfnType == ASTNode.LABELED_STATEMENT) {
      LabeledStatement ls=(LabeledStatement)nextCFN.astNode;
      SimpleName sn=ls.getLabel();
      nextCFNLabel=sn.getIdentifier();
    }
    if (label == null && (cfnType == ASTNode.SWITCH_STATEMENT || cfnType == ASTNode.DO_STATEMENT || cfnType == ASTNode.ENHANCED_FOR_STATEMENT || cfnType == ASTNode.FOR_STATEMENT || cfnType == ASTNode.WHILE_STATEMENT))     resultCFN=nextCFN;
 else     if (label != null && label.equals(nextCFNLabel)) {
      resultCFN=nextCFN;
    }
 else     if (size == 0)     throw new CrystalRuntimeException("one way edge detected");
 else     if (size == 1) {
      resultCFN=nextCFN.breaking(label,keepRemovingNodes);
      if (keepRemovingNodes)       nextCFN.remove();
    }
 else {
      resultCFN=nextCFN.breaking(label,false);
    }
    if (astNode.getNodeType() != ASTNode.BREAK_STATEMENT)     return resultCFN;
    if (resultCFN == null)     throw new CrystalRuntimeException("null result");
    if (resultCFN.loopBreakNode != null)     resultCFN=resultCFN.loopBreakNode;
    if (resultCFN == nextCFN)     return nextCFN;
    removeEdges(Direction.FORWARDS);
    addEdge(Direction.FORWARDS,resultCFN);
    return resultCFN;
  }
  /** 
 * A recursive method for correcting the structure of a CFG in the context of a continue statement.  After this method completes, the continue statement will be properly connected to whatever the target of the continue is.
 * @param label	the label of the continue, or null if none
 * @param keepRemovingNodes	if true then remove forward nodes
 * @return	the target of the continue
 */
  public ControlFlowNode continuing(  String label,  boolean keepRemovingNodes){
    ControlFlowNode nextCFN;
    if (loopBreakNode == null)     nextCFN=getNode(Direction.FORWARDS);
 else     nextCFN=loopBreakNode;
    if (nextCFN.isDummy())     throw new CrystalRuntimeException("reached end of graph while breaking");
    int size=nextCFN.getNumberOfEdges(Direction.BACKWARDS);
    int cfnType=nextCFN.astNode.getNodeType();
    ControlFlowNode resultCFN;
    String nextCFNLabel=null;
    if (cfnType == ASTNode.LABELED_STATEMENT) {
      LabeledStatement ls=(LabeledStatement)nextCFN.astNode;
      SimpleName sn=ls.getLabel();
      nextCFNLabel=sn.getIdentifier();
    }
    if (label == null && (cfnType == ASTNode.DO_STATEMENT || cfnType == ASTNode.ENHANCED_FOR_STATEMENT || cfnType == ASTNode.FOR_STATEMENT || cfnType == ASTNode.WHILE_STATEMENT)) {
      resultCFN=nextCFN;
    }
 else     if (label != null && label.equals(nextCFNLabel)) {
      cfnType=this.astNode.getNodeType();
      if (cfnType == ASTNode.DO_STATEMENT || cfnType == ASTNode.ENHANCED_FOR_STATEMENT || cfnType == ASTNode.FOR_STATEMENT || cfnType == ASTNode.WHILE_STATEMENT) {
        return this;
      }
 else       throw new CrystalRuntimeException("label MUST be attached to a loop");
    }
 else     if (size == 0)     throw new CrystalRuntimeException("one way edge detected");
 else     if (size == 1) {
      resultCFN=nextCFN.continuing(label,keepRemovingNodes);
      if (keepRemovingNodes)       nextCFN.remove();
    }
 else {
      resultCFN=nextCFN.continuing(label,false);
    }
    if (astNode.getNodeType() != ASTNode.CONTINUE_STATEMENT)     return resultCFN;
    if (resultCFN == null)     throw new CrystalRuntimeException("null result");
    if (resultCFN.loopContinueNode != null) {
      resultCFN=resultCFN.loopContinueNode;
    }
    if (resultCFN == nextCFN)     return nextCFN;
    removeEdges(Direction.FORWARDS);
    addEdge(Direction.FORWARDS,resultCFN);
    return resultCFN;
  }
  public ControlFlowNode getFirstChild(){
    Cloner cloner=new Cloner();
    firstChild=cloner.deepClone(firstChild);
    Cloner cloner=new Cloner();
    firstChild=cloner.deepClone(firstChild);
    return firstChild;
  }
  public List<ControlFlowNode> getBackwardEdges(){
    Cloner cloner=new Cloner();
    backwardEdges=cloner.deepClone(backwardEdges);
    Cloner cloner=new Cloner();
    backwardEdges=cloner.deepClone(backwardEdges);
    return backwardEdges;
  }
  public enum getDirection(){
    Cloner cloner=new Cloner();
    Direction=cloner.deepClone(Direction);
    Cloner cloner=new Cloner();
    Direction=cloner.deepClone(Direction);
    return Direction;
  }
  public ControlFlowNode getLoopBreakNode(){
    Cloner cloner=new Cloner();
    loopBreakNode=cloner.deepClone(loopBreakNode);
    Cloner cloner=new Cloner();
    loopBreakNode=cloner.deepClone(loopBreakNode);
    return loopBreakNode;
  }
  public ControlFlowGraph getGraph(){
    Cloner cloner=new Cloner();
    graph=cloner.deepClone(graph);
    Cloner cloner=new Cloner();
    graph=cloner.deepClone(graph);
    return graph;
  }
  public ControlFlowNode getLoopContinueNode(){
    Cloner cloner=new Cloner();
    loopContinueNode=cloner.deepClone(loopContinueNode);
    Cloner cloner=new Cloner();
    loopContinueNode=cloner.deepClone(loopContinueNode);
    return loopContinueNode;
  }
  public List<ControlFlowNode> getForwardEdges(){
    Cloner cloner=new Cloner();
    forwardEdges=cloner.deepClone(forwardEdges);
    Cloner cloner=new Cloner();
    forwardEdges=cloner.deepClone(forwardEdges);
    return forwardEdges;
  }
}
