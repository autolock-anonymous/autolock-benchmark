package edu.cmu.cs.plural.fractions;
import java.util.HashMap;
import java.util.Map;
/** 
 * Encapsulates mappings between named fractions, basically for alpha-converting them.
 * @author Kevin Bierhoff
 * @since Apr 11, 2009
 */
public class NamedFractionMapping {
  /** 
 * Bidirectional named fraction map. If named fractions f1 and f2 are mapped to each other then they must  both be keys in this map, with the other as their value.  
 */
  private Map<NamedFraction,NamedFraction> mapping=new HashMap<NamedFraction,NamedFraction>();
  /** 
 * Attempts to map the two given named fractions to each other; has no effect if the desired mapping is not possible or already in place. Mapping can fail, namely if at least one of the given named fractions already participates in another mapping.
 * @param f1
 * @param f2
 * @return <code>true</code> if the given named fractions could be orwere already mapped; <code>false</code> otherwise.
 */
  public boolean map(  NamedFraction f1,  NamedFraction f2){
    if (f1 == null || f2 == null)     return false;
    if (f1.equals(f2))     return true;
    NamedFraction m1=mapping.get(f1);
    NamedFraction m2=mapping.get(f2);
    if (m1 == null && m2 == null) {
      mapping.put(f1,f2);
      mapping.put(f2,f1);
      return true;
    }
    if (m1 != null && m2 != null) {
      return m1.equals(f2) && m2.equals(f1);
    }
    return false;
  }
  public Map<NamedFraction,NamedFraction> getMapping(){
    Cloner cloner=new Cloner();
    mapping=cloner.deepClone(mapping);
    Cloner cloner=new Cloner();
    mapping=cloner.deepClone(mapping);
    Cloner cloner=new Cloner();
    mapping=cloner.deepClone(mapping);
    return mapping;
  }
}
