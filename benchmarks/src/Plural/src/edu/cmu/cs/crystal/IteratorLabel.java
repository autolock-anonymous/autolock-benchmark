package edu.cmu.cs.crystal;
public class IteratorLabel implements ILabel {
  static private IteratorLabel EMPTY_LABEL=new IteratorLabel(true);
  static private IteratorLabel HAS_ITEM_LABEL=new IteratorLabel(false);
  private boolean isEmpty;
  private IteratorLabel(  boolean isEmpty){
    this.isEmpty=isEmpty;
  }
  /** 
 * @param labelValue
 * @return the BooleanLabel for the boolean passed in
 */
  static public IteratorLabel getItrLabel(  boolean isEmpty){
    return isEmpty ? EMPTY_LABEL : HAS_ITEM_LABEL;
  }
  public String getLabel(){
    return isEmpty ? "empty" : "has item";
  }
  public boolean getBranchValue(){
    return isEmpty;
  }
  @Override public String toString(){
    return getLabel();
  }
  public static IteratorLabel getHAS_ITEM_LABEL(){
    Cloner cloner=new Cloner();
    HAS_ITEM_LABEL=cloner.deepClone(HAS_ITEM_LABEL);
    Cloner cloner=new Cloner();
    HAS_ITEM_LABEL=cloner.deepClone(HAS_ITEM_LABEL);
    return HAS_ITEM_LABEL;
  }
  public static IteratorLabel getEMPTY_LABEL(){
    Cloner cloner=new Cloner();
    EMPTY_LABEL=cloner.deepClone(EMPTY_LABEL);
    Cloner cloner=new Cloner();
    EMPTY_LABEL=cloner.deepClone(EMPTY_LABEL);
    return EMPTY_LABEL;
  }
}
