package edu.cmu.cs.crystal.flow;
import java.util.Set;
import org.eclipse.jdt.core.dom.ASTNode;
import edu.cmu.cs.crystal.ILabel;
/** 
 * Interface for mapping branch labels to analysis information. Clients do not usually have to implement this interface.  Instead, use one of the pre-defined implementing classes.
 * @author Kevin Bierhoff
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 */
public interface IResult<LE extends LatticeElement<LE>> {
  /** 
 * If label is null, provide a default value.
 * @param label
 * @return A valid lattice element or <code>null</code> if the labelis unknown.
 */
  public LE get(  ILabel label);
  /** 
 * Returns the set of labels mapped by this <code>IResult</code>.
 * @return The set of labels mapped by this <code>IResult</code>.This method must not return <code>null</code>
 */
  public Set<ILabel> keySet();
  /** 
 * Join two results "pointwise" by joining lattice elements with  the same label.  This method must not modify either <code>IResult</code> objects passed in.
 * @param otherResult <code>IResult</code> object to join this <code>IResult</code> with.
 * @return Pointwise joined lattice elements.
 * @see LatticeElement#join(LatticeElement)
 */
  public IResult<LE> join(  IResult<LE> otherResult);
}
