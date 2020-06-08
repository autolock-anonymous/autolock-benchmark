package edu.cmu.cs.crystal;
/** 
 * A boolean label is a label which is either true or false. The true and false labels can be retrieved with getBooleanLabel(boolean). It occurs from branching control flow.
 * @author cchristo
 */
public class BooleanLabel implements ILabel {
  static private BooleanLabel TRUE_LABEL=new BooleanLabel(true);
  static private BooleanLabel FALSE_LABEL=new BooleanLabel(false);
  private boolean branchValue;
  private BooleanLabel(  boolean branchValue){
    this.branchValue=branchValue;
  }
  /** 
 * @param labelValue
 * @return the BooleanLabel for the boolean passed in
 */
  static public BooleanLabel getBooleanLabel(  boolean labelValue){
    return labelValue ? TRUE_LABEL : FALSE_LABEL;
  }
  public String getLabel(){
    return Boolean.toString(branchValue);
  }
  public boolean getBranchValue(){
    return branchValue;
  }
  @Override public String toString(){
    return getLabel();
  }
  public static BooleanLabel getFALSE_LABEL(){
    Cloner cloner=new Cloner();
    FALSE_LABEL=cloner.deepClone(FALSE_LABEL);
    Cloner cloner=new Cloner();
    FALSE_LABEL=cloner.deepClone(FALSE_LABEL);
    return FALSE_LABEL;
  }
  public static BooleanLabel getTRUE_LABEL(){
    Cloner cloner=new Cloner();
    TRUE_LABEL=cloner.deepClone(TRUE_LABEL);
    Cloner cloner=new Cloner();
    TRUE_LABEL=cloner.deepClone(TRUE_LABEL);
    return TRUE_LABEL;
  }
}
