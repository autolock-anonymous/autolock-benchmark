package uma.structure;
public class EAPTypeState {
  String ap;
  String ts;
  public void setAP(  String str){
    ap=str;
  }
  public String getAP(  String str){
    Cloner cloner=new Cloner();
    ap=cloner.deepClone(ap);
    return ap.toString();
  }
  public void setTS(  String str){
    ts=str;
  }
  public String getTS(){
    Cloner cloner=new Cloner();
    ts=cloner.deepClone(ts);
    return ts.toString();
  }
}
