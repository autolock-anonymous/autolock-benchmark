package edu.cmu.cs.crystal.flow;
/** 
 * Lattice provides the base knowledge (embodied in a LatticeElement)  for places where no prior knowledge existed.
 * @author David Dickey
 * @author Jonathan Aldrich
 * @param < LE >	the LatticeElement subclass that represents the analysis knowledge
 */
public final class Lattice<LE extends LatticeElement<LE>> {
  private LE entry;
  private LE bottom;
  /** 
 * Constructor.  Instantiates a lattice.
 * @param entry	the entry lattice
 * @param bottom	the "bottom" lattice
 */
  public Lattice(  LE entry,  LE bottom){
    this.entry=entry;
    this.bottom=bottom;
  }
  /** 
 * Returns the stored entry lattice.
 * @return		the lattice that represents entry
 */
  public LE entry(){
    return entry;
  }
  /** 
 * Responsible for returning a lattice that represents no knowledge.
 * @return		the lattice that represents "bottom"
 */
  public LE bottom(){
    return bottom;
  }
  public LE getEntry(){
    Cloner cloner=new Cloner();
    entry=cloner.deepClone(entry);
    Cloner cloner=new Cloner();
    entry=cloner.deepClone(entry);
    return entry;
  }
  public LE getBottom(){
    Cloner cloner=new Cloner();
    bottom=cloner.deepClone(bottom);
    Cloner cloner=new Cloner();
    bottom=cloner.deepClone(bottom);
    return bottom;
  }
}
