package edu.cmu.cs.crystal.flow;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
/** 
 * This class maps a set of known labels to a single lattice element.
 * @author Kevin Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 */
public class LabeledSingleResult<LE extends LatticeElement<LE>> implements IResult<LE> {
  private Set<ILabel> labels;
  private LE singleValue;
  public static <LE extends LatticeElement<LE>>IResult<LE> createResult(  LE value,  Collection<ILabel> labels){
    return new LabeledSingleResult<LE>(value,labels);
  }
  public static <LE extends LatticeElement<LE>>IResult<LE> createResultOverload(  LE value,  ILabel... labels){
    return new LabeledSingleResult<LE>(value,Arrays.asList(labels));
  }
  /** 
 * Creates a result that maps the given labels to the given lattice element.
 * @param singleValue The lattice element all given labels will map to.
 * @param labels The labels known to this result.
 */
  public LabeledSingleResult(  LE singleValue,  Collection<ILabel> labels){
    this.labels=new HashSet<ILabel>(labels.size());
    this.labels.addAll(labels);
    this.labels=Collections.unmodifiableSet(this.labels);
    this.singleValue=singleValue;
  }
  public Set<ILabel> keySet(){
    return labels;
  }
  public LE get(  ILabel label){
    return singleValue;
  }
  public IResult<LE> join(  IResult<LE> otherResult){
    LE otherLattice, thisLattice;
    LabeledResult<LE> mergedResult;
    Set<ILabel> mergedLabels=new HashSet<ILabel>();
    Set<ILabel> otherLabels=otherResult.keySet();
    mergedLabels.addAll(keySet());
    mergedLabels.addAll(otherResult.keySet());
    otherLattice=otherResult.get(null).copy();
    mergedResult=new LabeledResult<LE>(singleValue.copy().join(otherLattice,null));
    for (    ILabel label : mergedLabels) {
      if (otherLabels.contains(label) && labels.contains(label)) {
        otherLattice=otherResult.get(label).copy();
        thisLattice=get(label).copy();
        mergedResult.put(label,thisLattice.join(otherLattice,null));
      }
 else       if (otherLabels.contains(label)) {
        otherLattice=otherResult.get(label).copy();
        mergedResult.put(label,otherLattice);
      }
 else {
        thisLattice=get(label).copy();
        mergedResult.put(label,thisLattice);
      }
    }
    return mergedResult;
  }
  public LE getSingleValue(){
    Cloner cloner=new Cloner();
    singleValue=cloner.deepClone(singleValue);
    Cloner cloner=new Cloner();
    singleValue=cloner.deepClone(singleValue);
    return singleValue;
  }
  public Set<ILabel> getLabels(){
    Cloner cloner=new Cloner();
    labels=cloner.deepClone(labels);
    Cloner cloner=new Cloner();
    labels=cloner.deepClone(labels);
    return labels;
  }
}
