package edu.cmu.cs.crystal.flow.experimental;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.cfg.ICFGNode;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.Lattice;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * Encapsulates the results of running an analysis. Package private, because we'd like to avoid this being referenced  throughout Crystal, but different flow analysis library classes might potentially want to use this. 
 * @author Nels Beckman
 * @date Jan 24, 2008
 */
public class AnalysisResult<LE extends LatticeElement<LE>> {
  private final Map<ASTNode,Set<ICFGNode>> nodeMap;
  private final Map<ICFGNode,IResult<LE>> labeledResultsAfter;
  private final Map<ICFGNode,IResult<LE>> labeledResultsBefore;
  private final Lattice<LE> lattice;
  private final ICFGNode cfgStartNode;
  private final ICFGNode cfgEndNode;
  /** 
 * Creates copies of the given maps to encapsulate a new,  un-modifiable result of an analysis. 
 * @param _nm
 * @param _lra
 * @param _lrb
 * @param _l
 */
  public AnalysisResult(  Map<ASTNode,Set<ICFGNode>> _nm,  Map<ICFGNode,IResult<LE>> _lra,  Map<ICFGNode,IResult<LE>> _lrb,  Lattice<LE> _l,  ICFGNode _startNode,  ICFGNode _endNode){
    nodeMap=java.util.Collections.unmodifiableMap(new java.util.HashMap<ASTNode,Set<ICFGNode>>(_nm));
    labeledResultsAfter=java.util.Collections.unmodifiableMap(new java.util.HashMap<ICFGNode,IResult<LE>>(_lra));
    labeledResultsBefore=java.util.Collections.unmodifiableMap(new java.util.HashMap<ICFGNode,IResult<LE>>(_lrb));
    lattice=_l;
    cfgStartNode=_startNode;
    cfgEndNode=_endNode;
  }
  public Map<ASTNode,Set<ICFGNode>> getNodeMap(){
    Cloner cloner=new Cloner();
    nodeMap=cloner.deepClone(nodeMap);
    Cloner cloner=new Cloner();
    nodeMap=cloner.deepClone(nodeMap);
    return nodeMap;
  }
  public Map<ICFGNode,IResult<LE>> getLabeledResultsAfter(){
    Cloner cloner=new Cloner();
    labeledResultsAfter=cloner.deepClone(labeledResultsAfter);
    Cloner cloner=new Cloner();
    labeledResultsAfter=cloner.deepClone(labeledResultsAfter);
    return labeledResultsAfter;
  }
  public Map<ICFGNode,IResult<LE>> getLabeledResultsBefore(){
    Cloner cloner=new Cloner();
    labeledResultsBefore=cloner.deepClone(labeledResultsBefore);
    Cloner cloner=new Cloner();
    labeledResultsBefore=cloner.deepClone(labeledResultsBefore);
    return labeledResultsBefore;
  }
  public Lattice<LE> getLattice(){
    Cloner cloner=new Cloner();
    lattice=cloner.deepClone(lattice);
    Cloner cloner=new Cloner();
    lattice=cloner.deepClone(lattice);
    return lattice;
  }
  public ICFGNode getCfgStartNode(){
    Cloner cloner=new Cloner();
    cfgStartNode=cloner.deepClone(cfgStartNode);
    Cloner cloner=new Cloner();
    cfgStartNode=cloner.deepClone(cfgStartNode);
    return this.cfgStartNode;
  }
  public ICFGNode getCfgEndNode(){
    Cloner cloner=new Cloner();
    cfgEndNode=cloner.deepClone(cfgEndNode);
    Cloner cloner=new Cloner();
    cfgEndNode=cloner.deepClone(cfgEndNode);
    return this.cfgEndNode;
  }
}
