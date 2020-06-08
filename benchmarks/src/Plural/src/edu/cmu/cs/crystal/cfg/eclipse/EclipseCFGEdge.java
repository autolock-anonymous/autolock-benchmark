package edu.cmu.cs.crystal.cfg.eclipse;
import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.Node;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.cfg.ICFGEdge;
public class EclipseCFGEdge implements ICFGEdge {
  EclipseCFGNode sink;
  EclipseCFGNode source;
  ILabel label;
  EclipseCFGEdge(  EclipseCFGNode source,  EclipseCFGNode sink,  ILabel label){
    this.source=source;
    this.sink=sink;
    this.label=label;
  }
  public EclipseCFGNode getSink(){
    Cloner cloner=new Cloner();
    sink=cloner.deepClone(sink);
    Cloner cloner=new Cloner();
    sink=cloner.deepClone(sink);
    return sink;
  }
  public EclipseCFGNode getSource(){
    Cloner cloner=new Cloner();
    source=cloner.deepClone(source);
    Cloner cloner=new Cloner();
    source=cloner.deepClone(source);
    return source;
  }
  public String toString(){
    return getSource().toString() + "->" + getSink().toString();
  }
  public void addToGraph(  Graph graph,  Node source,  Node sink){
    Edge dotEdge=new Edge(graph,source,sink);
    dotEdge.setAttribute(Edge.LABEL_ATTR,label.getLabel());
  }
  public ILabel getLabel(){
    Cloner cloner=new Cloner();
    label=cloner.deepClone(label);
    Cloner cloner=new Cloner();
    label=cloner.deepClone(label);
    return label;
  }
}
