package edu.cmu.cs.crystal.analysis.alias;
import java.util.Set;
/** 
 * This interface represents aliasing information about a program variable. The current interface pretty much forces to represent this information as a set of   {@link ObjectLabel}s that the variable may point to.  Implementations of this interface should override   {@link #equals(Object)} and {@link #hashCode()}.
 * @author Kevin Bierhoff
 */
public interface Aliasing {
  public Set<ObjectLabel> getLabels();
  public boolean hasAnyLabels(  Set<ObjectLabel> labelsToFind);
}
