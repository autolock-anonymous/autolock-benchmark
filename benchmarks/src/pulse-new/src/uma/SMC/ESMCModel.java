package uma.SMC;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import uma.structure.EBoolInvariant;
import uma.structure.EClass;
import uma.structure.EDim;
import uma.structure.EInvariant;
import uma.structure.EMethod;
import uma.structure.EPackage;
import uma.structure.EField;
import uma.structure.EParameter;
import uma.structure.ESpecification;
import uma.structure.EState;
public class ESMCModel {
  static BufferedWriter out;
  static int currentobjectIndex;
  static String tab="    ";
  static Integer PRE=0;
  static Integer POST=1;
  static Integer K=0;
  static Integer NUM_AP=5;
  static Integer UNDEF=0;
  static Integer PURE=4;
  static EPackage pkg;
  static int sTestType;
  static String ap="";
  static int countMR=0;
  static int countSTM=0;
  public static void setK(  int k){
    K=k;
  }
  private static void declarationsAndinitilizations() throws IOException {
    out.write("Declarations\n");
    LinkedList<EClass> _listClasses=pkg.getClasses();
    for (    EClass _class : _listClasses) {
      out.write(comment(_class.getName() + "-->") + "\n");
      defineVariables(_class.getName(),0,_class,0);
      for (      EField _field : _class.getFields()) {
        if (_field.getClassIndex() != -1) {
          out.write(comment(_field.getName()) + "\n");
          defineVariables(_field.getType(),_field.getObjectIndex(),getClass(_field.getType()),_field.getModifier());
        }
      }
      out.write(comment("<--" + _class.getName()) + "\n\n");
      out.flush();
    }
    initialize(_listClasses);
  }
  private static void defineVariables(  String className,  int objectIndex,  EClass _class,  int modifier) throws IOException {
    out.write(tab + "state_" + className+ "_"+ objectIndex+ tab+ tab+ "[0, "+ _class.getStates().size()+ "]\n");
    out.write(tab + "tkrB_" + className+ "_"+ objectIndex+ tab+ tab+ " [0, "+ (K + 1)+ "]\n");
    out.write(tab + "tkwB_" + className+ "_"+ objectIndex+ tab+ tab+ " [0, "+ (K + 1)+ "]\n\n");
    for (    String boolVariable : _class.getVariablesofBooleanInvariants())     out.write(tab + "boolInv_" + boolVariable+ "_"+ className+ "_"+ objectIndex+ tab+ tab+ "[0, 1]\n\n");
    defineKVariables(className,objectIndex,_class,K);
  }
  private static void defineKVariables(  String className,  int objectIndex,  EClass _class,  int K) throws IOException {
    for (int refIndex=0; refIndex <= K; refIndex++) {
      out.write(tab + "pc_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ " [0, 1]\n");
      out.write(tab + "method_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ " [0, "+ _class.getMethods().size()+ "]\n");
      out.write(tab + "ap_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ " [0, "+ NUM_AP+ "]\n");
      out.write(tab + "tkr_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ "[0, "+ (K + 1)+ "]\n");
      out.write(tab + "tkw_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ "[0, "+ (K + 1)+ "]\n\n");
    }
  }
  private static void initialize(  LinkedList<EClass> _listClasses) throws IOException {
    out.write("Initial states\n");
    for (    EClass _class : _listClasses) {
      out.write(comment(_class.getName() + "-->") + "\n");
      initilizeVariables(_class.getName(),0,_class,2);
      for (      EField _field : _class.getFields()) {
        if (_field.getClassIndex() != -1) {
          out.write(comment(_field.getName()) + "\n");
          initilizeVariables(_field.getType(),_field.getObjectIndex(),getClass(_field.getType()),_field.getModifier());
        }
      }
      out.write(comment("<--" + _class.getName()) + "\n\n");
      out.flush();
    }
  }
  private static void initilizeVariables(  String className,  int objectIndex,  EClass _class,  int modifier) throws IOException {
    out.write(tab + "state_" + className+ "_"+ objectIndex+ tab+ tab+ "="+ UNDEF+ "\n");
    out.write(tab + "tkrB_" + className+ "_"+ objectIndex+ tab+ tab+ " ="+ (K + 1)+ "\n");
    out.write(tab + "tkwB_" + className+ "_"+ objectIndex+ tab+ tab+ " ="+ (K + 1)+ "\n\n");
    for (    String boolVariable : _class.getVariablesofBooleanInvariants())     out.write(tab + "boolInv_" + boolVariable+ "_"+ className+ "_"+ objectIndex+ tab+ tab+ " =0\n\n");
    initilizeKVariables(className,objectIndex,K);
  }
  private static void initilizeKVariables(  String className,  int objectIndex,  int K) throws IOException {
    for (int refIndex=0; refIndex <= K; refIndex++) {
      out.write(tab + "pc_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ " ="+ POST+ "\n");
      out.write(tab + "method_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ " ="+ UNDEF+ "\n");
      out.write(tab + "ap_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ " ="+ UNDEF+ "\n");
      out.write(tab + "tkr_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ "=0"+ "\n");
      out.write(tab + "tkw_" + className+ "_"+ objectIndex+ "_"+ refIndex+ tab+ tab+ "=0"+ "\n\n");
    }
  }
  private static void createAlias() throws IOException {
    for (int refIndex=0; refIndex <= K; refIndex++) {
      for (      EClass _class : pkg.getClasses()) {
        modelAlias(_class.getName(),0,refIndex);
        for (        EField _field : _class.getFields()) {
          if (_field.getModifier() == 0 || _field.getModifier() == 1)           modelAlias(_field.getType(),_field.getObjectIndex(),refIndex);
        }
      }
    }
    out.flush();
  }
  private static void modelAlias(  String className,  Integer objectIndex,  Integer refIndex) throws IOException {
    if (isClassExist(className) == true) {
      out.write(comment(className) + "\n");
      out.write(tab + "create_alias_" + className+ "_"+ objectIndex+ "_"+ refIndex+ ":");
      out.write(tab + "ap_" + className+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ UNDEF+ tab+ "/\\ tkrB_"+ className+ "_"+ objectIndex+ " > 0 "+ tab+ "/\\ state_"+ className+ "_"+ objectIndex+ " != "+ UNDEF);
      out.write(tab + "->");
      out.write(tab + "pc_" + className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ POST+ tab+ "/\\ method_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ UNDEF+ tab+ "/\\ ap_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ PURE+ "\n\n");
    }
  }
  private static boolean isClassExist(  String className){
    for (    EClass _class : pkg.getClasses()) {
      if (_class.getName().compareTo(className) == 0)       return true;
    }
    return false;
  }
  private static void Transitions() throws IOException {
    out.write("Transitions\n");
    for (int refIndex=0; refIndex <= K; refIndex++) {
      out.write(comment("K=" + refIndex + "-->") + "\n");
      for (      EClass _class : pkg.getClasses()) {
        out.write(comment(_class.getName() + "-->") + "\n");
        createInstanceInModel(_class,_class.getName(),0,refIndex);
        for (        EField _field : _class.getFields()) {
          EClass __class=getClass(_field.getType());
          if (__class != null) {
            if (isPrivateAndIndexEqualToZero(refIndex,_field))             createInstanceInModel(__class,_class.getName() + " Field " + _field.getName(),_field.getObjectIndex(),refIndex);
          }
        }
        out.write(comment("<--" + _class.getName()) + "\n\n");
      }
      out.write(comment("<--K=" + refIndex) + "\n\n");
    }
  }
  private static boolean isPrivateAndIndexEqualToZero(  int refIndex,  EField _field){
    System.out.println("ap=" + ap);
    return true;
  }
  private static EClass getClass(  String className){
    for (    EClass _class : pkg.getClasses()) {
      if (_class.getName().compareTo(className) == 0)       return _class;
    }
    return null;
  }
  private static void createInstanceInModel(  EClass _class,  String name,  int objectIndex,  int J) throws IOException {
    out.write(comment(name) + "\n");
    for (    EMethod _method : _class.getMethods()) {
      startMethod(_class,_method,objectIndex,J);
      endMethod(_class,_method,objectIndex,J);
    }
  }
  private static void startMethod(  EClass _class,  EMethod _method,  Integer objectIndex,  Integer refIndex) throws IOException {
    EClass _currentClass=_class;
    currentobjectIndex=objectIndex;
    out.write(tab + "start_" + _class.getName()+ "_"+ _method.getIdentifier()+ "_"+ objectIndex+ "_"+ refIndex+ ":");
    if (_method.getName().compareTo(_class.getName()) == 0)     modelPCConstructor(_class,objectIndex,refIndex,_class);
 else     modelPCMethod(_class,objectIndex,refIndex);
    LinkedList<ESpecification> _listRequires=_method.getRequiresAPTS();
    for (    ESpecification rAP_TS : _listRequires) {
      if (rAP_TS.getParentClass() != null) {
        objectIndex=getObjectIndex(_currentClass,rAP_TS.getFieldName());
        _class=getFieldClass(_currentClass,rAP_TS.getFieldName());
      }
      if (_currentClass.hasMoreThanOneDimension() && _listRequires.size() > 1)       objectIndex=getDimensionIndex(_currentClass,rAP_TS.getTS());
      startAPTS(_class,_method,rAP_TS.getAP(),rAP_TS.getTS(),objectIndex,refIndex);
    }
    startAPTSPARAM(_method,refIndex);
    out.write(tab + "->");
    if (_method.getName().compareTo(_class.getName()) == 0)     modelPrimePCConstructor(_class,_method,currentobjectIndex,refIndex,_class);
 else     modelPrimePCandMethod(_class,_method,currentobjectIndex,refIndex);
    LinkedList<ESpecification> _listEnsures=_method.getEnsuresAPTS();
    for (    ESpecification eAP_TS : _listEnsures) {
      if (eAP_TS.getParentClass() != null) {
        objectIndex=getObjectIndex(_currentClass,eAP_TS.getFieldName());
        _class=getFieldClass(_currentClass,eAP_TS.getFieldName());
      }
      if (_currentClass.hasMoreThanOneDimension() && _listEnsures.size() > 1)       objectIndex=getDimensionIndex(_currentClass,eAP_TS.getTS());
      starPrimeAP(eAP_TS.getAP(),_class,objectIndex,refIndex,eAP_TS.getTS());
    }
    startPrimeTSPARAM(_method,refIndex);
    out.write("\n");
  }
  private static EClass getFieldClass(  EClass _class,  String fieldName){
    for (    EField _field : _class.getFields()) {
      if (_field.getName().compareTo(fieldName) == 0)       return getClass(_field.getType());
    }
    return null;
  }
  private static void modelPCMethod(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(tab + "pc_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ POST);
    out.write(tab + "/\\" + " ap_"+ _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " != 0");
  }
  private static void modelPrimePCConstructor(  EClass _class,  EMethod _method,  Integer objectIndex,  Integer refIndex,  EClass _currentClass) throws IOException {
    modelPrimePCandMethod(_class,_method,objectIndex,refIndex);
    if (_class.getSuperClassName() != null) {
      EClass _parentClass=getClass(_class.getSuperClassName());
      if (_parentClass != null) {
        String var=_class.getSuperClassName() + "-parent-of-" + _class.getName();
        objectIndex=getObjectIndex(_currentClass,var);
        _method=_parentClass.getConstructor();
        out.write(tab + "/\\");
        modelPrimePCConstructor(_parentClass,_method,objectIndex,refIndex,_currentClass);
      }
    }
  }
  private static void modelPrimePCandMethod(  EClass _class,  EMethod _method,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(tab + "pc_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ PRE);
    out.write(tab + "/\\ method_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ _method.getIndex()+ comment(_method.getName()));
  }
  private static void modelPCConstructor(  EClass _class,  Integer objectIndex,  Integer refIndex,  EClass _currentClass) throws IOException {
    modelAPs(_class,objectIndex,refIndex);
    if (_class.getSuperClassName() != null) {
      EClass _parentClass=getClass(_class.getSuperClassName());
      if (_parentClass != null) {
        String var=_class.getSuperClassName() + "-parent-of-" + _class.getName();
        objectIndex=getObjectIndex(_currentClass,var);
        out.write(tab + "/\\");
        modelPCConstructor(_parentClass,objectIndex,refIndex,_currentClass);
      }
    }
  }
  private static void modelAPs(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(tab + "ap_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = 0");
    for (Integer x=0; x <= K; x++) {
      if (x != refIndex)       out.write(tab + "/\\ ap_" + _class.getName()+ "_"+ objectIndex+ "_"+ x+ " = 0");
    }
  }
  private static void modelPrimeConstructor(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    if (_class.getSuperClassName() != null) {
      String superClassName=_class.getSuperClassName();
      for (      EClass __class : pkg.getClasses()) {
        if (__class.getName().compareTo(superClassName) == 0) {
          out.write(comment("inheritance conjuncts->"));
          starPrimeAP("Pure",__class,0,refIndex,"alive");
          out.write(comment("<-inheritance conjuncts"));
          break;
        }
      }
    }
  }
  private static void modelInheritance(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    String superClassName=_class.getSuperClassName();
    for (    EClass __class : pkg.getClasses()) {
      if (__class.getName().compareTo(superClassName) == 0) {
        out.write(comment("inheritance conjuncts->"));
        out.write(tab + "/\\ ap_" + __class.getIndex()+ "_"+ objectIndex+ "_"+ refIndex+ " != 0");
        out.write(tab + "/\\ tkrB_" + __class.getIndex()+ "_"+ objectIndex+ " > 0");
        out.write(comment("<-inheritance conjuncts"));
        break;
      }
    }
  }
  private static void startPrimeTSPARAM(  EMethod method,  Integer refIndex) throws IOException {
    for (    EParameter _param : method.getParameters()) {
      for (      ESpecification rAP_TS : _param.getEnsuresAPTS()) {
        boolean find=false;
        for (        EClass _class : pkg.getClasses()) {
          if (_class.getName().compareTo(_param.getType()) == 0) {
            find=true;
            starPrimeAP(rAP_TS.getAP(),_class,0,refIndex,rAP_TS.getTS());
            break;
          }
        }
        if (find == false)         error(rAP_TS.getTS(),method.getName());
      }
    }
  }
  private static void startAPTSPARAM(  EMethod _method,  Integer J) throws IOException {
    for (    EParameter _param : _method.getParameters()) {
      for (      ESpecification rAP_TS : _param.getRequiresAPTS()) {
        boolean find=false;
        for (        EClass _class : pkg.getClasses()) {
          if (_class.getName().compareTo(_param.getType()) == 0) {
            find=true;
            startAPTS(_class,_method,rAP_TS.getAP(),rAP_TS.getTS(),0,J);
            break;
          }
        }
        if (find == false)         error(rAP_TS.getTS(),_method.getName());
      }
    }
  }
  private static void starPrimeAP(  String ap,  EClass _class,  Integer objectIndex,  Integer refIndex,  String stateName) throws IOException {
    modelPrimeAP(ap,_class.getName(),objectIndex,refIndex);
    modelPrimeAPStateInvariants(_class,refIndex,stateName);
  }
  private static void modelPrimeAPStateInvariants(  EClass _class,  Integer refIndex,  String stateName) throws IOException {
    for (    EState _state : _class.getStates()) {
      if (_state.getName().compareTo(stateName) == 0)       for (      EInvariant _inv : _state.getInvariants()) {
        ap=_inv.getAP();
        int cIndex=getClassIndex(_inv.getVariableType());
        if (cIndex != -1) {
          int objectIndex=getObjectIndex(_class,_inv.getVariable());
          out.write(comment("invariant of typestate-> " + stateName));
          modelPrimeAP(ap,_inv.getVariableType(),objectIndex,refIndex);
          out.write(comment("<-invariant of typestate " + stateName));
          LinkedList<EInvariant> invs=_inv.getStateInvariants(pkg);
          if (invs != null) {
            if (invs.size() > 0) {
              EClass innerClass=pkg.getClasses().get(getClassIndex(_inv.getVariableType()));
              modelPrimeAPStateInvariants(innerClass,refIndex,_inv.getStateName());
            }
          }
        }
      }
    }
  }
  private static void modelPrimeAP(  String ap,  String className,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(tab + "/\\ ap_" + className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ getAPId(ap)+ comment(ap));
    if (ap.compareToIgnoreCase("UNIQUE") == 0)     out.write(tab + "/\\ tkrB_" + className+ "_"+ objectIndex+ "'"+ " = 0"+ tab+ "/\\ tkwB_"+ className+ "_"+ objectIndex+ "'"+ " = 0"+ tab+ "/\\ tkr_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ (K + 1)+ tab+ "/\\ tkw_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ (K + 1));
 else     if (ap.compareToIgnoreCase("FULL") == 0)     out.write(tab + "/\\ tkrB_" + className+ "_"+ objectIndex+ "'"+ " = "+ "tkrB_"+ className+ "_"+ objectIndex+ " - 1"+ tab+ "/\\ tkwB_"+ className+ "_"+ objectIndex+ "'"+ " = 0"+ tab+ "/\\ tkr_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 1"+ tab+ "/\\ tkw_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ (K + 1));
 else     if (ap.compareToIgnoreCase("PURE") == 0)     out.write(tab + "/\\ tkrB_" + className+ "_"+ objectIndex+ "'"+ " = "+ "tkrB_"+ className+ "_"+ objectIndex+ " - 1"+ tab+ "/\\ tkr_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 1");
  }
  private static int getAPId(  String ap){
    System.out.println("ap=" + ESMCModel.ap);
    if (ap.compareToIgnoreCase("Unique") == 0)     return 1;
 else     if (ap.compareToIgnoreCase("Full") == 0)     return 2;
 else     if (ap.compareToIgnoreCase("Share") == 0)     return 3;
 else     if (ap.compareToIgnoreCase("Pure") == 0)     return 4;
 else     if (ap.compareToIgnoreCase("Immutable") == 0)     return 5;
    return 0;
  }
  private static int getDimensionIndex(  EClass _class,  String ts){
    int index=0;
    for (    EDim dim : _class.getDimensions()) {
      for (      String value : dim.getValues()) {
        if (value.compareTo(ts) == 0)         return index;
      }
      index++;
    }
    return 0;
  }
  private static void endMethod(  EClass _class,  EMethod _method,  Integer objectIndex,  Integer refIndex) throws IOException {
    EClass _currentClass=_class;
    out.write(tab + "end_" + _class.getName()+ "_"+ _method.getIdentifier()+ "_"+ objectIndex+ "_"+ refIndex+ ":");
    if (_method.getName().compareTo(_class.getName()) == 0)     modelEndPCConstructor(_class,_method,objectIndex,refIndex,_class);
 else     modelEndPCMethod(_class,_method,objectIndex,refIndex);
    out.write(tab + "->");
    if (_method.getName().compareTo(_class.getName()) == 0)     modelPrimePCConstructor(_class,objectIndex,refIndex,_class);
 else     modelPrimePC(_class,objectIndex,refIndex);
    LinkedList<ESpecification> _listEnsures=_method.getEnsuresAPTS();
    for (    ESpecification eAP_TS : _listEnsures) {
      if (eAP_TS.getParentClass() != null) {
        objectIndex=getObjectIndex(_currentClass,eAP_TS.getFieldName());
        _class=getFieldClass(_currentClass,eAP_TS.getFieldName());
      }
      if (_currentClass.hasMoreThanOneDimension() && _listEnsures.size() > 1)       objectIndex=getDimensionIndex(_currentClass,eAP_TS.getTS());
      endPrimeAPTS(_class,_method.getName(),eAP_TS.getAP(),eAP_TS.getTS(),objectIndex,refIndex);
    }
    endPrimeAPTSPARAM(_method,refIndex);
    out.write("\n\n");
    out.flush();
  }
  private static void modelPrimePCConstructor(  EClass _class,  Integer objectIndex,  Integer refIndex,  EClass _currentClass) throws IOException {
    modelPrimePC(_class,objectIndex,refIndex);
    if (_class.getSuperClassName() != null) {
      EClass _parentClass=getClass(_class.getSuperClassName());
      if (_parentClass != null) {
        String var=_class.getSuperClassName() + "-parent-of-" + _class.getName();
        objectIndex=getObjectIndex(_currentClass,var);
        out.write(tab + "/\\");
        modelPrimePCConstructor(_parentClass,objectIndex,refIndex,_currentClass);
      }
    }
  }
  private static void modelPrimePC(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(tab + "pc_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = "+ POST);
  }
  private static void modelEndPCConstructor(  EClass _class,  EMethod _method,  Integer objectIndex,  Integer refIndex,  EClass _currentClass) throws IOException {
    modelEndPCMethod(_class,_method,objectIndex,refIndex);
    if (_class.getSuperClassName() != null) {
      EClass _parentClass=getClass(_class.getSuperClassName());
      if (_parentClass != null) {
        String var=_class.getSuperClassName() + "-parent-of-" + _class.getName();
        objectIndex=getObjectIndex(_currentClass,var);
        _method=_parentClass.getConstructor();
        out.write(tab + "/\\");
        modelEndPCConstructor(_parentClass,_method,objectIndex,refIndex,_currentClass);
      }
    }
  }
  private static void modelEndPCMethod(  EClass _class,  EMethod _method,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(tab + "pc_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ PRE);
    out.write(tab + "/\\ method_" + _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ _method.getIndex()+ comment(_method.getName()));
  }
  private static void modelendConstructor(  EClass _class,  EMethod _method,  Integer refIndex) throws IOException {
    if (_class.getSuperClassName() != null) {
      String superClassName=_class.getSuperClassName();
      for (      EClass __class : pkg.getClasses()) {
        if (__class.getName().compareTo(superClassName) == 0) {
          out.write(comment("inheritance conjuncts->"));
          endPrimeAPTS(__class,_method.getIdentifier(),"Pure","alive",0,refIndex);
          out.write(comment("<-inheritance conjuncts"));
          break;
        }
      }
    }
  }
  private static void endPrimeAPTSPARAM(  EMethod method,  Integer refIndex) throws IOException {
    for (    EParameter _param : method.getParameters()) {
      for (      ESpecification eAP_TS : _param.getEnsuresAPTS()) {
        for (        EClass _class : pkg.getClasses()) {
          if (_class.getName().compareTo(_param.getType()) == 0) {
            endPrimeAPTS(_class,method.getName(),eAP_TS.getAP(),eAP_TS.getTS(),0,refIndex);
            break;
          }
        }
      }
    }
  }
  private static void endPrimeAPTS(  EClass _class,  String methodName,  String ap,  String stateName,  Integer objectIndex,  Integer refIndex) throws IOException {
    updateState(methodName,stateName,_class,objectIndex);
    updateTokens(ap,_class.getName(),objectIndex,refIndex);
    updateStateInvariants(_class,methodName,stateName,refIndex);
    updateBoolStateInvariants(_class,methodName,stateName,objectIndex);
  }
  private static void updateBoolStateInvariants(  EClass _class,  String methodName,  String stateName,  Integer objectIndex) throws IOException {
    for (    EState _state : _class.getStates()) {
      if (_state.getName().compareTo(stateName) == 0) {
        for (        EBoolInvariant _boolInv : _state.getBoolInvariants()) {
          out.write(comment("bool inv of typestate-> " + stateName));
          out.write(tab + "/\\ boolInv_" + _boolInv.getVariable()+ "_"+ _class.getName()+ "_"+ objectIndex+ "'"+ " = "+ _boolInv.getValue());
          out.write(comment("<-bool inv of typestate " + stateName));
        }
      }
    }
  }
  private static void updateStateInvariants(  EClass _class,  String methodName,  String stateName,  Integer refIndex) throws IOException {
    for (    EState _state : _class.getStates()) {
      if (_state.getName().compareTo(stateName) == 0) {
        for (        EInvariant _inv : _state.getInvariants()) {
          int classIndex=getClassIndex(_inv.getVariableType());
          if (classIndex != -1) {
            out.write(comment("invariant of typestate-> " + stateName));
            int objectIndex=getObjectIndex(_class,_inv.getVariable());
            EClass _invClass=pkg.getClasses().get(classIndex);
            updateState(methodName,_inv.getStateName(),_invClass,objectIndex);
            updateTokens(_inv.getAP(),_invClass.getName(),objectIndex,refIndex);
            out.write(comment("<-invariant of typestate " + stateName));
            LinkedList<EInvariant> invs=_inv.getStateInvariants(pkg);
            if (invs != null) {
              if (invs.size() > 0) {
                EClass innerClass=pkg.getClasses().get(getClassIndex(_inv.getVariableType()));
                updateStateInvariants(innerClass,methodName,_inv.getStateName(),refIndex);
              }
            }
          }
        }
      }
    }
  }
  private static void updateTokens(  String ap,  String className,  Integer objectIndex,  Integer refIndex) throws IOException {
    if (ap.compareToIgnoreCase("UNIQUE") == 0)     out.write(tab + "/\\ tkrB_" + className+ "_"+ objectIndex+ "'"+ " = "+ (K + 1)+ tab+ "/\\ tkwB_"+ className+ "_"+ objectIndex+ "'"+ " = "+ (K + 1)+ tab+ "/\\ tkr_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 0"+ tab+ "/\\ tkw_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 0");
 else     if (ap.compareToIgnoreCase("FULL") == 0)     out.write(tab + "/\\ tkrB_" + className+ "_"+ objectIndex+ "'"+ " = "+ "tkrB_"+ className+ "_"+ objectIndex+ " + 1"+ tab+ "/\\ tkwB_"+ className+ "_"+ objectIndex+ "'"+ " = "+ (K + 1)+ tab+ "/\\ tkr_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 0"+ tab+ "/\\ tkw_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 0");
 else     if (ap.compareToIgnoreCase("PURE") == 0)     out.write(tab + "/\\ tkrB_" + className+ "_"+ objectIndex+ "'"+ " = "+ "tkrB_"+ className+ "_"+ objectIndex+ " + 1"+ tab+ "/\\ tkr_"+ className+ "_"+ objectIndex+ "_"+ refIndex+ "'"+ " = 0");
  }
  private static void updateState(  String methodName,  String state,  EClass _class,  Integer objectIndex) throws IOException {
    int stateId=pkg.getClasses().get(_class.getIndex()).findStateIndex(state);
    if (stateId == -1)     error(state,methodName);
 else     out.write(tab + "/\\ state_" + _class.getName()+ "_"+ objectIndex+ "'"+ " = "+ stateId+ " "+ comment(state));
  }
  private static void startAPTS(  EClass _class,  EMethod _method,  String ap,  String stateName,  Integer objectIndex,  Integer refIndex) throws IOException {
    modelState(_class,objectIndex,_method,stateName);
    modelAP(_class,objectIndex,refIndex,ap);
    modelStateInvariants(_class,refIndex,_method,stateName);
    modelBoolStateInvariants(_class,objectIndex,stateName);
  }
  private static void modelBoolStateInvariants(  EClass _class,  Integer objectIndex,  String stateName) throws IOException {
    for (    EState _state : _class.getStates()) {
      if (_state.getName().compareTo(stateName) == 0) {
        for (        EBoolInvariant _boolInv : _state.getBoolInvariants()) {
          out.write(comment("bool inv of typestate-> " + stateName));
          out.write(tab + "/\\ boolInv_" + _boolInv.getVariable()+ "_"+ _class.getName()+ "_"+ objectIndex+ " = "+ _boolInv.getValue());
          out.write(comment("<-bool inv of typestate " + stateName));
        }
      }
    }
  }
  private static void modelState(  EClass _class,  Integer objectIndex,  EMethod _method,  String stateName) throws IOException {
    if (stateName.compareTo("alive") == 0) {
      out.write(tab + "/\\ state_" + _class.getName()+ "_"+ objectIndex+ " > "+ 0+ " "+ comment(stateName));
      return;
    }
    EClass __class=pkg.getClasses().get(_class.getIndex());
    int stateId=pkg.getClasses().get(_class.getIndex()).findStateIndex(stateName);
    if (stateId == -1) {
      error(stateName,_method.getIdentifier());
      return;
    }
    out.write(tab + "/\\ state_" + _class.getName()+ "_"+ objectIndex+ " = "+ stateId+ " "+ comment(stateName));
  }
  private static void modelStateInvariants(  EClass _class,  int refIndex,  EMethod _method,  String stateName) throws IOException {
    for (    EState _state : _class.getStates()) {
      if (_state.getName().compareTo(stateName) == 0) {
        for (        EInvariant inv : _state.getInvariants()) {
          int classIndex=getClassIndex(inv.getVariableType());
          if (classIndex != -1) {
            int objectIndex=getObjectIndex(_class,inv.getVariable());
            out.write(comment("invariant of typestate-> " + stateName));
            modelState(pkg.getClasses().get(classIndex),objectIndex,_method,inv.getStateName());
            modelAP(pkg.getClasses().get(classIndex),objectIndex,refIndex,inv.getAP());
            out.write(comment("<-invariant of typestate " + stateName));
            LinkedList<EInvariant> invs=inv.getStateInvariants(pkg);
            if (invs != null) {
              if (invs.size() > 0) {
                EClass innerClass=pkg.getClasses().get(getClassIndex(inv.getVariableType()));
                modelStateInvariants(innerClass,refIndex,_method,inv.getStateName());
              }
            }
          }
        }
      }
    }
  }
  private static int getObjectIndex(  EClass _class,  String variable){
    for (    EField field : _class.getFields()) {
      if (field.getName().compareTo(variable) == 0)       return field.getObjectIndex();
    }
    return -1;
  }
  private static int getClassIndex(  String name){
    for (    EClass _class : pkg.getClasses()) {
      if (_class.getName().compareTo(name) == 0)       return _class.getIndex();
    }
    return -1;
  }
  private static void addIndexes(){
    int classIndex=0;
    for (    EClass _class : pkg.getClasses()) {
      _class.setIndex(classIndex);
      classIndex++;
      if (_class.getDimensions().size() > 1)       createDimensionsObject(_class);
      if (_class.getSuperClassName() != null)       createParentObject(_class);
    }
    for (    EClass _class : pkg.getClasses()) {
      for (      EField _field : _class.getFields()) {
        for (        EClass __class : pkg.getClasses()) {
          if (__class.getName().compareTo(_field.getType()) == 0) {
            _field.setClassIndex(__class.getIndex());
            __class.createObject();
            _field.setObjectIndex(__class.getLastObjectIndex());
          }
        }
      }
      int stateIndex=1;
      for (      EState _state : _class.getStates()) {
        _state.setIndex(stateIndex);
        stateIndex++;
      }
      int methodIndex=1;
      for (      EMethod _method : _class.getMethods()) {
        _method.setIndex(methodIndex);
        methodIndex++;
      }
      addInvariantStateIndex(_class);
    }
  }
  private static void createDimensionsObject(  EClass _class){
    int count=0;
    for (    EDim _dim : _class.getDimensions()) {
      createDimensionAsField(_class,_dim,count);
    }
  }
  private static void createDimensionAsField(  EClass _class,  EDim _dim,  int count){
    EField _field=new EField();
    _field.setName("dim_" + _class.getName() + "_"+ count);
    _field.setType(_class.getName());
    _field.setModifier(0);
    _class.addField(_field);
  }
  private static void createParentObject(  EClass _class){
    EClass _currentClass=_class;
    while (_class.getSuperClassName() != null) {
      EClass _parentClass=getClass(_class.getSuperClassName());
      if (_parentClass != null) {
        createParentAsField(_class,_currentClass);
        _class=_parentClass;
      }
 else {
        System.out.println("The class " + _class.getSuperClassName() + " does not exist");
        break;
      }
    }
  }
  private static void createParentAsField(  EClass _class,  EClass _currentClass){
    EField _field=new EField();
    _field.setName(_class.getSuperClassName() + "-parent-of-" + _class.getName());
    _field.setType(_class.getSuperClassName());
    _field.setModifier(0);
    _currentClass.addField(_field);
    EClass _parentClass=getClass(_class.getSuperClassName());
    EMethod _parentConstructor=_parentClass.getConstructor();
    for (    ESpecification _reqSpec : _parentConstructor.getRequiresAPTS()) {
      ESpecification aSpec=new ESpecification();
      aSpec.setAPTS(_reqSpec.getAP(),_reqSpec.getTS(),_currentClass,_field.getName());
      _currentClass.getConstructor().getRequiresAPTS().add(aSpec);
    }
    for (    ESpecification _ensSpec : _parentConstructor.getEnsuresAPTS()) {
      ESpecification aSpec=new ESpecification();
      aSpec.setAPTS(_ensSpec.getAP(),_ensSpec.getTS(),_currentClass,_field.getName());
      _currentClass.getConstructor().getEnsuresAPTS().add(aSpec);
    }
    _currentClass.getConstructor().getParameters().addAll(_parentConstructor.getParameters());
  }
  private static void addInvariantStateIndex(  EClass _class){
    for (    EState _state : _class.getStates()) {
      for (      EInvariant inv : _state.getInvariants()) {
        setInvariantVariableType(_class,inv);
        for (        EClass __class : pkg.getClasses()) {
          if (__class.getName().compareTo(inv.getVariableType()) == 0) {
            for (            EState __state : __class.getStates()) {
              if (__state.getName().compareTo(inv.getStateName()) == 0) {
                inv.setStateIndex(__state.getStateIndex());
              }
            }
          }
        }
      }
    }
  }
  private static void setInvariantVariableType(  EClass _class,  EInvariant inv){
    for (    EField _field : _class.getFields()) {
      if (_field.getName().compareTo(inv.getVariable()) == 0) {
        inv.setVariableType(_field.getType());
      }
    }
  }
  private static void modelAP(  EClass _class,  Integer objectIndex,  Integer refIndex,  String ap) throws IOException {
    if (ap.compareToIgnoreCase("UNIQUE") == 0)     out.write(tab + "/\\ tkrB_" + _class.getName()+ "_"+ objectIndex+ " = "+ (K + 1)+ tab+ "/\\ tkwB_"+ _class.getName()+ "_"+ objectIndex+ " = "+ (K + 1));
 else     if (ap.compareToIgnoreCase("FULL") == 0)     out.write(tab + "/\\ tkrB_" + _class.getName()+ "_"+ objectIndex+ " > 0"+ tab+ "/\\ tkwB_"+ _class.getName()+ "_"+ objectIndex+ " = "+ (K + 1));
 else     if (ap.compareToIgnoreCase("PURE") == 0)     out.write(tab + "/\\ tkrB_" + _class.getName()+ "_"+ objectIndex+ " > 0");
  }
  private static void error(  String state,  String method){
    if (state.compareToIgnoreCase("alive") != 0)     System.out.println("WARNING: The state '" + state + "' in "+ method+ " is undefined. There are two possible reasons for this error, either state is not defined for this class"+ " or the class where state is defined is not added in the analysis");
  }
  private static String comment(  String str){
    System.out.println("ap = " + ap);
    return "/*" + str + "*/";
  }
  private static void Spec() throws IOException {
    countMR=0;
    countSTM=0;
    out.write("\nProperties\n\n");
    for (    EClass _class : pkg.getClasses()) {
      if (sTestType == 2 || sTestType == 0) {
        methodsReachability(_class,0,0);
      }
      if (sTestType == 3 || sTestType == 0)       statesAdjancyMatrix(_class,0);
      if (sTestType == 4 || sTestType == 0) {
        for (int refIndex=0; refIndex < K; refIndex++)         concurrentMethods(_class,0,refIndex);
      }
    }
    if (sTestType == 1 || sTestType == 0)     sinkStates();
  }
  private static void concurrentMethods(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write("\n\n");
    for (    EMethod m1 : _class.getMethods()) {
      out.write("\n");
      for (      EMethod m2 : _class.getMethods()) {
        out.write("concurrentMethods_" + _class.getName() + "_"+ objectIndex+ "_"+ refIndex+ "_"+ m1.getIdentifier()+ "_and_"+ _class.getName()+ "_"+ objectIndex+ "_"+ (refIndex + 1)+ "_"+ m2.getIdentifier()+ ":"+ tab+ "method_"+ _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ m1.getIndex()+ " /\\ "+ "pc_"+ _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ PRE+ tab+ "/\\ "+ "method_"+ _class.getName()+ "_"+ objectIndex+ "_"+ (refIndex + 1)+ " = "+ m2.getIndex()+ " /\\ "+ "pc_"+ _class.getName()+ "_"+ objectIndex+ "_"+ (refIndex + 1)+ " = "+ PRE+ "\n");
      }
    }
  }
  private static void sinkStates() throws IOException {
    out.write("sinkstates_deadlocks:" + tab + "!EX(true)\n\n");
  }
  private static void statesAdjancyMatrix(  EClass _class,  Integer objectIndex) throws IOException {
    out.write("\n\n");
    for (    EState s1 : _class.getStates()) {
      for (      EState s2 : _class.getStates()) {
        out.write("stateTransition_of_" + _class.getName() + "_from_"+ s1.getName()+ "_to_"+ s2.getName()+ ":"+ tab+ "state_"+ _class.getName()+ "_"+ objectIndex+ " = "+ s1.getStateIndex()+ tab+ "/\\ EX(state_"+ _class.getName()+ "_"+ objectIndex+ " = "+ s2.getStateIndex()+ ")\n");
        countSTM++;
      }
      out.write("\n");
    }
  }
  private static void methodsReachability(  EClass _class,  Integer objectIndex,  Integer refIndex) throws IOException {
    out.write(comment(_class.getName()) + "\n");
    for (    EMethod _method : _class.getMethods()) {
      out.write("requires_clause_of_" + _class.getName() + "_"+ _method.getIdentifier()+ "_"+ objectIndex+ "_"+ refIndex+ ":"+ tab+ "method_"+ _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ _method.getIndex()+ tab+ "/\\ pc_"+ _class.getName()+ "_"+ objectIndex+ "_"+ refIndex+ " = "+ PRE+ "\n");
      countMR++;
    }
    out.flush();
  }
  public static void generateSMCmodelCommandLine(  int testType){
    try {
      sTestType=testType;
      pkg=EVMDDSMCGenerator.getPkgObject();
      Transitions();
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public static void generateSMCmodelPlugin(  EPackage _pkg,  int testType){
    K=4;
    sTestType=testType;
    pkg=_pkg;
    IWorkspace workspace=ResourcesPlugin.getWorkspace();
    String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
    System.out.println("\n model.stm is created in the folder " + folder);
  }
  public static Integer getNUM_AP(){
    Cloner cloner=new Cloner();
    NUM_AP=cloner.deepClone(NUM_AP);
    return NUM_AP;
  }
  public static Integer getUNDEF(){
    Cloner cloner=new Cloner();
    UNDEF=cloner.deepClone(UNDEF);
    return UNDEF;
  }
  public static String getTab(){
    Cloner cloner=new Cloner();
    tab=cloner.deepClone(tab);
    return tab;
  }
  public static Integer getK(){
    Cloner cloner=new Cloner();
    K=cloner.deepClone(K);
    return K;
  }
  public static Integer getPOST(){
    Cloner cloner=new Cloner();
    POST=cloner.deepClone(POST);
    return POST;
  }
  public static EPackage getPkg(){
    Cloner cloner=new Cloner();
    pkg=cloner.deepClone(pkg);
    return pkg;
  }
  public static Integer getPURE(){
    Cloner cloner=new Cloner();
    PURE=cloner.deepClone(PURE);
    return PURE;
  }
  public static Integer getPRE(){
    Cloner cloner=new Cloner();
    PRE=cloner.deepClone(PRE);
    return PRE;
  }
  public static BufferedWriter getOut(){
    Cloner cloner=new Cloner();
    out=cloner.deepClone(out);
    return out;
  }
  public static String getAp(){
    Cloner cloner=new Cloner();
    ap=cloner.deepClone(ap);
    return ap;
  }
}
