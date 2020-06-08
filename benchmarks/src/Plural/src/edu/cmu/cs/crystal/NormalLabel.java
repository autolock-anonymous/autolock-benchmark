package edu.cmu.cs.crystal;
/** 
 * A normal label occurs from normal control flow.
 * @author cchristo
 */
public class NormalLabel implements ILabel {
  private static final NormalLabel NORMAL_LABEL=new NormalLabel();
  private NormalLabel(){
  }
  static public NormalLabel getNormalLabel(){
    return NORMAL_LABEL;
  }
  public String getLabel(){
    return NORMAL_LABEL.toString();
  }
  @Override public String toString(){
    return NORMAL_LABEL.toString();
  }
  public static NormalLabel getNORMAL_LABEL(){
    Cloner cloner=new Cloner();
    NORMAL_LABEL=cloner.deepClone(NORMAL_LABEL);
    Cloner cloner=new Cloner();
    NORMAL_LABEL=cloner.deepClone(NORMAL_LABEL);
    return NORMAL_LABEL;
  }
}
