package edu.cmu.cs.crystal.cfg.eclipse;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.WhileStatement;
/** 
 * A CFG which places the control flow node at the merge point.
 * @author Ciera Jaspan
 */
public class EclipseNodeFirstCFG extends EclipseCFG {
  public EclipseNodeFirstCFG(){
    super();
  }
  public EclipseNodeFirstCFG(  MethodDeclaration method){
    super(method);
  }
  @Override public boolean visit(  DoStatement node){
    EclipseCFGNode doBegin=nodeMap.get(node);
    EclipseCFGNode doEnd=new EclipseCFGNode(null);
    blockStack.pushUnlabeled(node,doEnd,doBegin);
    doBegin.setEnd(doEnd);
    return true;
  }
  @Override public void endVisit(  DoStatement node){
    EclipseCFGNode doEnd=nodeMap.get(node).getEnd();
    EclipseCFGNode cond=nodeMap.get(node.getExpression());
    EclipseCFGNode body=nodeMap.get(node.getBody());
    EclipseCFGNode doBegin=nodeMap.get(node);
    createEdge(doBegin,body.getStart());
    createEdge(body.getEnd(),cond.getStart());
    createBooleanEdge(cond.getEnd(),doBegin,true);
    createBooleanEdge(cond.getEnd(),doEnd,false);
    blockStack.popUnlabeled();
    doBegin.setName("do");
    doEnd.setName("od");
  }
  @Override public boolean visit(  EnhancedForStatement node){
    EclipseCFGNode eforBegin=nodeMap.get(node);
    EclipseCFGNode eforEnd=new EclipseCFGNode(null);
    blockStack.pushUnlabeled(node,eforEnd,eforBegin);
    eforBegin.setEnd(eforEnd);
    return true;
  }
  @Override public void endVisit(  EnhancedForStatement node){
    EclipseCFGNode list=nodeMap.get(node.getExpression());
    EclipseCFGNode body=nodeMap.get(node.getBody());
    EclipseCFGNode param=nodeMap.get(node.getParameter());
    EclipseCFGNode eforBegin=nodeMap.get(node);
    EclipseCFGNode eforEnd=eforBegin.getEnd();
    eforBegin.setStart(list.getStart());
    createEdge(list.getEnd(),eforBegin);
    createItrEdge(eforBegin,eforEnd,true);
    createItrEdge(eforBegin,param.getStart(),false);
    createEdge(param.getEnd(),body.getStart());
    createEdge(body.getEnd(),eforBegin);
    blockStack.popUnlabeled();
    eforBegin.setName("efor");
    eforEnd.setName("rofe");
  }
  @Override public boolean visit(  ForStatement node){
    EclipseCFGNode forBegin=nodeMap.get(node);
    EclipseCFGNode forEnd=new EclipseCFGNode(null);
    forBegin.setName("forBdummy");
    forEnd.setName("forEdummy");
    blockStack.pushUnlabeled(node,forEnd,forBegin);
    forBegin.setEnd(forEnd);
    return true;
  }
  @Override public void endVisit(  ForStatement node){
    EclipseCFGNode beginDummy=nodeMap.get(node);
    EclipseCFGNode cond=nodeMap.get(node.getExpression());
    EclipseCFGNode body=nodeMap.get(node.getBody());
    EclipseCFGNode endDummy=beginDummy.getEnd();
    EclipseCFGNode current, last=null;
    EclipseCFGNode forNode=new EclipseCFGNode(node);
    nodeMap.put(node,forNode);
    beginDummy.setASTNode(null);
    beginDummy.setEnd(beginDummy);
    for (    ASTNode init : (List<ASTNode>)node.initializers()) {
      current=nodeMap.get(init);
      if (last != null)       createEdge(last.getEnd(),current.getStart());
 else       forNode.setStart(current.getStart());
      last=current;
    }
    if (last != null)     createEdge(last.getEnd(),forNode);
    if (cond != null) {
      createEdge(forNode,cond.getStart());
      createBooleanEdge(cond.getEnd(),endDummy,false);
      createBooleanEdge(cond.getEnd(),body.getStart(),true);
    }
 else {
      createEdge(forNode,body.getStart());
    }
    createEdge(body.getEnd(),beginDummy);
    last=beginDummy;
    for (    ASTNode update : (List<ASTNode>)node.updaters()) {
      current=nodeMap.get(update);
      createEdge(last.getEnd(),current.getStart());
      last=current;
    }
    createEdge(last.getEnd(),forNode);
    blockStack.popUnlabeled();
    forNode.setName("for");
    forNode.setEnd(endDummy);
  }
  @Override public boolean visit(  WhileStatement node){
    EclipseCFGNode whileBegin=nodeMap.get(node);
    EclipseCFGNode whileEnd=new EclipseCFGNode(null);
    blockStack.pushUnlabeled(node,whileEnd,whileBegin);
    whileBegin.setEnd(whileEnd);
    return true;
  }
  @Override public void endVisit(  WhileStatement node){
    EclipseCFGNode cond=nodeMap.get(node.getExpression());
    EclipseCFGNode body=nodeMap.get(node.getBody());
    EclipseCFGNode whileBegin=nodeMap.get(node);
    EclipseCFGNode whileEnd=whileBegin.getEnd();
    createEdge(whileBegin,cond.getStart());
    createBooleanEdge(cond.getEnd(),whileEnd,false);
    createBooleanEdge(cond.getEnd(),body.getStart(),true);
    createEdge(body.getEnd(),whileBegin);
    blockStack.popUnlabeled();
    whileBegin.setName("while");
    whileEnd.setName("elihw");
  }
}
