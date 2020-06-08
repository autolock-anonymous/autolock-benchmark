package edu.cmu.cs.crystal.analysis.alias;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.flow.LatticeElement;
/** 
 * This is an immutable lattice.
 */
public class AliasLE implements LatticeElement<AliasLE>, Aliasing {
  protected final Set<ObjectLabel> labels;
  public static AliasLE create(  Set<ObjectLabel> newLabels){
    return new AliasLE(newLabels);
  }
  public static AliasLE createOverload(  ObjectLabel label){
    return new AliasLE(label);
  }
  public static AliasLE bottom(){
    return new AliasLE();
  }
  protected AliasLE(){
    this.labels=Collections.emptySet();
  }
  protected AliasLE(  ObjectLabel label){
    this.labels=Collections.singleton(label);
  }
  protected AliasLE(  Set<ObjectLabel> labels){
    this.labels=Collections.unmodifiableSet(labels);
  }
  public Set<ObjectLabel> getLabels(){
    Cloner cloner=new Cloner();
    labels=cloner.deepClone(labels);
    Cloner cloner=new Cloner();
    labels=cloner.deepClone(labels);
    return labels;
  }
  public boolean atLeastAsPrecise(  AliasLE other,  ASTNode node){
    return other.labels.containsAll(labels);
  }
  public AliasLE copy(){
    return this;
  }
  public AliasLE join(  AliasLE other,  ASTNode node){
    HashSet<ObjectLabel> copy;
    copy=new HashSet<ObjectLabel>(this.labels);
    copy.addAll(other.labels);
    return new AliasLE(copy);
  }
  @Override public int hashCode(){
    final int prime=31;
    int result=1;
    result=prime * result + ((labels == null) ? 0 : labels.hashCode());
    return result;
  }
  @Override public boolean equals(  Object obj){
    if (this == obj)     return true;
    if (obj == null)     return false;
    if (getClass() != obj.getClass())     return false;
    final AliasLE other=(AliasLE)obj;
    return labels.containsAll(other.labels) && other.labels.containsAll(labels);
  }
  public String toString(){
    return labels.toString();
  }
  public boolean hasAnyLabels(  Set<ObjectLabel> labelsToFind){
    for (    ObjectLabel label : labelsToFind)     if (labels.contains(label))     return true;
    return false;
  }
}
