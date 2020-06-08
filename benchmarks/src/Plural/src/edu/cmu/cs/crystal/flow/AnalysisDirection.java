package edu.cmu.cs.crystal.flow;
/** 
 * This enumeration is used to specify the direction of a flow analysis.
 * @author Kevin Bierhoff
 */
public class AnalysisDirection {
  public static final AnalysisDirection FORWARD_ANALYSIS=null;
  public static final AnalysisDirection BACKWARD_ANALYSIS=null;
  public static AnalysisDirection getBACKWARD_ANALYSIS(){
    Cloner cloner=new Cloner();
    BACKWARD_ANALYSIS=cloner.deepClone(BACKWARD_ANALYSIS);
    Cloner cloner=new Cloner();
    BACKWARD_ANALYSIS=cloner.deepClone(BACKWARD_ANALYSIS);
    return BACKWARD_ANALYSIS;
  }
  public static AnalysisDirection getFORWARD_ANALYSIS(){
    Cloner cloner=new Cloner();
    FORWARD_ANALYSIS=cloner.deepClone(FORWARD_ANALYSIS);
    Cloner cloner=new Cloner();
    FORWARD_ANALYSIS=cloner.deepClone(FORWARD_ANALYSIS);
    return FORWARD_ANALYSIS;
  }
}
