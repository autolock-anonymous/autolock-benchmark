package edu.cmu.cs.crystal.flow;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import edu.cmu.cs.crystal.ILabel;
/** 
 * @author Kevin Bierhoff
 */
public class LabeledResult<LE extends LatticeElement<LE>> implements IResult<LE> {
  private Map<ILabel,LE> labelMap;
  private LE defaultValue;
  /** 
 * I, Nels Beckman, declare this method to be deprecated because it does not leave the object in a valid state. A labeled result MUST have at least one label, and this factory method does not do this.
 * @see LabeledResult#createResultOverload(List,LatticeElement)
 */
  @Deprecated public static <LE extends LatticeElement<LE>>LabeledResult<LE> createResult(  LE defaultValue){
    return new LabeledResult<LE>(defaultValue);
  }
  /** 
 * Create a result for the given labels with the given default value.
 * @param < LE >
 * @param labels
 * @param defaultValue
 * @return A new result for the given labels with the given default value.
 */
  public static <LE extends LatticeElement<LE>>LabeledResult<LE> createResultOverload(  List<ILabel> labels,  LE defaultValue){
    return new LabeledResult<LE>(labels,defaultValue);
  }
  public LabeledResult(  List<ILabel> labels,  LE defaultValue){
    this(defaultValue);
    for (    ILabel lab : labels) {
      this.labelMap.put(lab,defaultValue);
    }
  }
  public LabeledResult(  LE defaultValue){
    labelMap=new HashMap<ILabel,LE>();
    this.defaultValue=defaultValue;
  }
  public void put(  ILabel label,  LE value){
    LE existing=labelMap.get(value);
    if (existing != null)     value=existing.join(value,null);
    labelMap.put(label,value);
  }
  public LE get(  ILabel label){
    LE value=labelMap.get(label);
    if (value == null)     value=defaultValue;
    return value;
  }
  public Set<ILabel> keySet(){
    return Collections.unmodifiableSet(labelMap.keySet());
  }
  public IResult<LE> join(  IResult<LE> otherResult){
    LE otherLattice, thisLattice, mergedLattice;
    LabeledResult<LE> mergedResult;
    Set<ILabel> mergedLabels=new HashSet<ILabel>();
    Set<ILabel> otherLabels=otherResult.keySet();
    mergedLabels.addAll(keySet());
    mergedLabels.addAll(otherResult.keySet());
    otherLattice=otherResult.get(null).copy();
    mergedResult=new LabeledResult<LE>(defaultValue.copy().join(otherLattice,null));
    for (    ILabel label : mergedLabels) {
      if (otherLabels.contains(label) && labelMap.containsKey(label)) {
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
  public LE getDefaultValue(){
    Cloner cloner=new Cloner();
    defaultValue=cloner.deepClone(defaultValue);
    Cloner cloner=new Cloner();
    defaultValue=cloner.deepClone(defaultValue);
    return defaultValue;
  }
  public Map<ILabel,LE> getLabelMap(){
    Cloner cloner=new Cloner();
    labelMap=cloner.deepClone(labelMap);
    Cloner cloner=new Cloner();
    labelMap=cloner.deepClone(labelMap);
    return labelMap;
  }
}
