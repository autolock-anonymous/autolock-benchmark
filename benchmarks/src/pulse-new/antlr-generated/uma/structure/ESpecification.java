package uma.structure;
public class ESpecification implements Cloneable {
  boolean and;
  String ap;
  String ts;
  String ap_ts;
  private String fieldType;
  private String fieldName;
  private EClass _class;
  private EMethod _method;
  public Object clone(){
    try {
      return super.clone();
    }
 catch (    CloneNotSupportedException e) {
      throw new InternalError(e.toString());
    }
  }
  public ESpecification(){
    _class=null;
  }
  public String getAP(){
    Cloner cloner=new Cloner();
    ap=cloner.deepClone(ap);
    return ap.toString();
  }
  public String getTS(){
    Cloner cloner=new Cloner();
    ts=cloner.deepClone(ts);
    return ts.toString();
  }
  public void setAPTS(  String ap,  String ts){
    this.ap=ap;
    this.ts=ts;
  }
  public void setAP(  String ap){
    this.ap=ap;
  }
  public void setAPTS(  String ap,  String ts,  EClass _class,  String fieldName){
    this.ap=ap;
    this.ts=ts;
    this._class=_class;
    this.fieldName=fieldName;
  }
  public EClass getParentClass(){
    return _class;
  }
  public String getFieldName(){
    Cloner cloner=new Cloner();
    fieldName=cloner.deepClone(fieldName);
    return fieldName;
  }
  public String getFieldType(){
    Cloner cloner=new Cloner();
    fieldType=cloner.deepClone(fieldType);
    return fieldType;
  }
  public EClass get_class(){
    Cloner cloner=new Cloner();
    _class=cloner.deepClone(_class);
    return _class;
  }
  public EMethod get_method(){
    Cloner cloner=new Cloner();
    _method=cloner.deepClone(_method);
    return _method;
  }
  public String getAp_ts(){
    Cloner cloner=new Cloner();
    ap_ts=cloner.deepClone(ap_ts);
    return ap_ts;
  }
}
