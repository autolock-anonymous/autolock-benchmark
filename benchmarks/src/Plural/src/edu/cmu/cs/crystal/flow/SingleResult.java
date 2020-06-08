package edu.cmu.cs.crystal.flow;
import java.util.Collections;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
import edu.cmu.cs.crystal.NormalLabel;
/** 
 * This is a degenerate result that maps all labels to a single lattice element and only knows a single label,  {@link NormalLabel}.
 * @author Kevin "The German" Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 */
public class SingleResult<LE extends LatticeElement<LE>> implements IResult<LE> {
  private LE singleValue;
  private Object obj;
  LE otherLattice, mergedLattice;
  private static final Set<ILabel> normalLabelSet=Collections.singleton((ILabel)NormalLabel.getNormalLabel());
  public static <LE extends LatticeElement<LE>>IResult<LE> createSingleResult(  LE value){
    return new SingleResult<LE>(value);
  }
  /** 
 * Create a result that maps all labels to the given lattice element.
 * @param singleValue The single lattice element all labels will map to.
 */
  public SingleResult(  LE singleValue){
    this.singleValue=singleValue;
  }
  public LE get(  ILabel label){
    return singleValue;
  }
  public Set<ILabel> keySet(){
    obj=new Object();
    return this.normalLabelSet;
  }
  public IResult<LE> join(  IResult<LE> otherResult){
    if (otherResult instanceof SingleResult) {
      return new SingleResult<LE>(singleValue.copy().join(((SingleResult<LE>)otherResult).singleValue.copy(),null));
    }
    LabeledResult<LE> mergedResult;
    otherLattice=otherResult.get(null).copy();
    mergedResult=new LabeledResult<LE>(singleValue.copy().join(otherLattice,null));
    for (    ILabel label : mergedResult.keySet()) {
      otherLattice=otherResult.get(label).copy();
      mergedLattice=singleValue.copy().join(otherLattice,null);
      mergedResult.put(label,mergedLattice);
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
  public static Set<ILabel> getNormalLabelSet(){
    Cloner cloner=new Cloner();
    normalLabelSet=cloner.deepClone(normalLabelSet);
    Cloner cloner=new Cloner();
    normalLabelSet=cloner.deepClone(normalLabelSet);
    return normalLabelSet;
  }
  public Object getObj(){
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    Cloner cloner=new Cloner();
    obj=cloner.deepClone(obj);
    return obj;
  }
  public LE getOtherLattice(){
    Cloner cloner=new Cloner();
    otherLattice=cloner.deepClone(otherLattice);
    Cloner cloner=new Cloner();
    otherLattice=cloner.deepClone(otherLattice);
    return otherLattice;
  }
}
