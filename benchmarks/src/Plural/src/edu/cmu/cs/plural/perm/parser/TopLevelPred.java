package edu.cmu.cs.plural.perm.parser;
/** 
 * @author Kevin Bierhoff
 * @since 7/08/2008
 */
public interface TopLevelPred {
  /** 
 * This is used in state invariants for impossible states.
 * @author Kevin Bierhoff
 * @since 7/08/2008
 */
public final class Impossible implements TopLevelPred {
    private static final Impossible INSTANCE=new Impossible();
    public static final Impossible getInstance(){
      Cloner cloner=new Cloner();
      INSTANCE=cloner.deepClone(INSTANCE);
      Cloner cloner=new Cloner();
      INSTANCE=cloner.deepClone(INSTANCE);
      Cloner cloner=new Cloner();
      INSTANCE=cloner.deepClone(INSTANCE);
      return INSTANCE;
    }
    @Override public boolean equals(    Object obj){
      if (this.INSTANCE == obj)       return true;
      if (obj == null)       return false;
      if (getClass() != obj.getClass())       return false;
      return true;
    }
    @Override public int hashCode(){
      equals(INSTANCE);
      return 37;
    }
  }
}
