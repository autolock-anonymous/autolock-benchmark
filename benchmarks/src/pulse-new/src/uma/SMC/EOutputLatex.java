package uma.SMC;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import uma.structure.EClass;
import uma.structure.EMethod;
import uma.structure.EPackage;
import uma.structure.EState;
public class EOutputLatex {
  static BufferedWriter latex;
  static EPackage _prj;
  private static boolean bRequires=false;
  private static boolean bSTM=false;
  private static boolean bSinkStates=false;
  private static boolean bconcurrency=false;
  static String endOfRow="\\\\";
  static String hLine="\\hline";
  private static String beginSideWay="\\begin{sideways}";
  private static String endSideWay="\\end{sideways}";
  public static void create_Plugin(){
    IWorkspace workspace=ResourcesPlugin.getWorkspace();
    String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
    System.out.println("model.tex generated in the directory " + folder);
    FileWriter fstream;
    try {
      fstream=new FileWriter(folder + "/" + "model.tex");
      latex=new BufferedWriter(fstream);
      addUsePackages();
      latex.write("\n\\begin{document}\n");
      writeToLatex();
      latex.write("\n\\end{document}\n");
      latex.flush();
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  public static void create_CommandLine(){
    IWorkspace workspace=ResourcesPlugin.getWorkspace();
    String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
    FileWriter fstream;
    try {
      fstream=new FileWriter(folder + "/model.tex");
      Process p1=Runtime.getRuntime().exec("chmod 777 " + folder + "/model.tex");
      latex=new BufferedWriter(fstream);
      addUsePackages();
      latex.write("\n\\begin{document}\n");
      writeToLatex();
      latex.write("\n\\end{document}\n");
      latex.flush();
    }
 catch (    IOException e) {
      e.printStackTrace();
    }
  }
  private static void addUsePackages() throws IOException {
    latex.write("\\documentclass[10pt]{article}");
    latex.write("\n\\usepackage[margin=1.0in]{geometry}");
    latex.write("\n\\usepackage{longtable}");
    latex.write("\n\\usepackage[usenames,dvipsnames]{xcolor}");
    latex.write("\n\\usepackage{amsmath}");
    latex.write("\n\\usepackage{amssymb}");
    latex.write("\n\\usepackage{hyperref}");
    latex.write("\n\\hypersetup{colorlinks=true}");
    latex.write("\n\\usepackage{rotating}");
    latex.write("\n\\usepackage[utf8]{inputenc}");
    latex.write("\n\\usepackage{listings}");
    latex.write("\n\\usepackage{xcolor}");
    latex.write("\n\\lstdefinestyle{mystyle}{\n");
    latex.write("language = JAVA,framexleftmargin=4pt, framexrightmargin=0.3cm,xleftmargin=2pt,showstringspaces=false,numbers = left,numberblanklines=false," + "numberstyle=\\tiny, numbersep=6pt, firstnumber = 1, " + "breaklines=true,basicstyle=\\scriptsize\\ttfamily, keywordstyle=\\bfseries\\color{blue},stringstyle=\\color{orange},commentstyle=\\color{Green}\\ttfamily,"+ "frame=single,morekeywords={none,full,unique,ENDOFCLASS,ensures,requires,ALIVE,share,immutable,pure,main},morecomment={[s][\\color{violet}]{@}{( }},escapeinside={*@}{@*}}\n");
    latex.write("\n\\lstset{style=mystyle}\n");
    latex.flush();
  }
  public static void writeToLatex() throws IOException {
    WriteSummary();
    if ((bRequires == false) && (bSTM == false) && (bconcurrency == false))     return;
    latex.write("\n\\newpage");
    latex.write("\n\\tableofcontents");
    for (    EClass _class : _prj.getClasses()) {
      latex.write("\n\\newpage");
      latex.write("\n\n\\section{{\\color{Fuchsia}" + _class.getName() + "}}");
      latex.write("\n\\label{" + _class.getName() + "}");
      if (bRequires == true)       writeRequiresClauseSatisfiabilty(_class);
      if (bSTM == true)       writeStateTransitionMatrix(_class);
      if (bconcurrency == true)       writeMethodConcurrencyMatrix(_class);
    }
    writeAbbervations();
    bRequires=false;
    bSTM=false;
    bconcurrency=false;
  }
  private static void writeAbbervations() throws IOException {
    latex.write("\n\\newpage");
    latex.write("\n\n\\section{{\\color{Fuchsia}Abbreviation}}");
    latex.write("\n\\label{Abbreviation}");
    latex.write("\n\\begin{longtable}{|l|l|}");
    latex.write("\n\\caption{Used Abbreviation}\\\\\n\\hline");
    latex.write("\nSymbol & Meaning\\\\\n\\hline\n");
    latex.write("{\\color{blue}$\\surd$}" + "&" + "requires clause of the method is satisfiable");
    latex.write(endOfRow + "\n");
    latex.write("\\hline\n");
    latex.write("{\\color{red}$\\times$}" + "&" + "requires clause of the method is unsatisfiable");
    latex.write(endOfRow + "\n");
    latex.write("\\hline\n");
    latex.write("{\\color{blue}$\\uparrow$}" + "&" + "The row-state can be transitioned to the column-state");
    latex.write(endOfRow + "\n");
    latex.write("\\hline\n");
    latex.write("{\\color{BlueGreen}$\\times$}" + "&" + "The row-state cannot be transitioned to the column-state");
    latex.write(endOfRow + "\n");
    latex.write("\\hline\n");
    latex.write("{\\color{blue}$\\parallel$}" + "&" + "The row-method can be possibly executed parallel with the column-method");
    latex.write(endOfRow + "\n");
    latex.write("\\hline\n");
    latex.write("{\\color{BrickRed}$\\nparallel$}" + "&" + "The row-method cannot be executed parallel with the column-method");
    latex.write(endOfRow + "\n");
    latex.write("\\hline\n");
    latex.flush();
    latex.write("\\end{longtable}");
    latex.flush();
  }
  private static void WriteSummary() throws IOException {
    latex.write("\n\\begin{center}\\huge{Summary}\\end{center}");
    if (bSinkStates == true)     latex.write("\n\\textbf{Sink States}:" + "$" + _prj.getSinkStates().replace("x","\\times") + "$");
    addSummaryTableColumns();
    addSummaryTableHeaders();
    addSummaryTableRows();
    latex.write("\\end{longtable}");
    latex.flush();
  }
  private static void writeMethodConcurrencyMatrix(  EClass _class) throws IOException {
    if (_class.getMethods().size() < 2)     return;
    addConcurrencyMatrixColumns(_class);
    addConcurrencyMatrixHeaders(_class);
    addConcurrencyMatrixRows(_class);
    latex.write("\\end{longtable}");
    latex.flush();
  }
  private static void addConcurrencyMatrixRows(  EClass _class) throws IOException {
    for (    EMethod _method : _class.getMethods()) {
      latex.write(_method.getIdentifier().replace("_","\\_"));
      for (      EMethod __method : _class.getMethods()) {
        String value=getConcurrencyValue(_method,__method);
        latex.write("&" + value);
      }
      latex.write(endOfRow);
      latex.write("\n" + hLine + "\n");
    }
  }
  private static String getConcurrencyValue(  EMethod _method,  EMethod __method){
    String id=__method.getIdentifier();
    if (_method.isConcurrentMethod(id) == true)     return "{\\color{blue}$\\parallel$}";
 else     return "{\\color{BrickRed}$\\nparallel$}";
  }
  private static void addSummaryTableHeaders() throws IOException {
    latex.write(beginSideWay + "{\\color{BlueGreen}Classes}" + endSideWay);
    latex.write("&" + beginSideWay + "{\\color{BlueGreen}Methods}"+ endSideWay);
    latex.write("&" + beginSideWay + "{\\color{BlueGreen}States}"+ endSideWay);
    if (bRequires == true)     latex.write("&" + beginSideWay + "{\\color{BlueGreen}Unsatisfiable Clauses}"+ endSideWay);
    if (bSTM == true)     latex.write("&" + beginSideWay + "{\\color{BlueGreen}Unreachable States}"+ endSideWay);
    if (bconcurrency == true)     latex.write("&" + beginSideWay + "{\\color{BlueGreen}Possible Concuurent Methods}"+ endSideWay);
    latex.write(endOfRow);
    latex.write("\n\\hline\n");
  }
  private static void addSummaryTableColumns() throws IOException {
    latex.write("\n\\begin{longtable}{|l|l|l");
    if (bSinkStates == true)     latex.write("|l");
    if (bRequires == true)     latex.write("|l");
    if (bSTM == true)     latex.write("|l");
    latex.write("|l}");
    latex.write("\n\\caption{Pulse Analysis Summary}\\\\\n\\hline\n");
  }
  private static void writeStateTransitionMatrix(  EClass _class) throws IOException {
    if (_class.getStates().size() < 2)     return;
    latex.write("\n\\begin{longtable}{|l");
    addSTMNumberofColumns(_class);
    latex.write("|}");
    latex.write("\n\\caption{State Transition Matrix}\\\\\n\\hline\n");
    addSTMColumnsHeaders(_class);
    addSTMRows(_class);
    latex.write("\\end{longtable}");
    latex.flush();
  }
  private static void addSTMNumberofColumns(  EClass _class) throws IOException {
    for (    EState _state : _class.getStates()) {
      if (_state.getStateIndex() > 1)       latex.write("|l");
    }
  }
  private static void addSTMColumnsHeaders(  EClass _class) throws IOException {
    latex.write("");
    for (    EState _state : _class.getStates()) {
      if (_state.getStateIndex() > 1) {
        latex.write("&" + beginSideWay + _state.getName().replace("_","\\_")+ endSideWay);
      }
    }
    latex.write(endOfRow);
    latex.write("\n" + hLine + "\n");
    latex.flush();
  }
  private static void addSTMRows(  EClass _class) throws IOException {
    for (int i=1; i < _class.getStates().size(); i++) {
      EState _state=_class.getStates().get(i);
      latex.write(_state.getName().replace("_","\\_"));
      for (int j=1; j < _class.getStates().size(); j++) {
        EState __state=_class.getStates().get(j);
        String value=getStateReachabilityValue(_state,__state);
        latex.write("&" + value);
      }
      latex.write(endOfRow);
      latex.write("\n" + hLine + "\n");
    }
  }
  private static String getStateReachabilityValue(  EState _state,  EState __state){
    if (_state.isReachableState(__state.getName()) == true)     return "{\\color{blue}$\\uparrow$}";
 else     return "{\\color{BlueGreen}$\\times$}";
  }
  private static void writeRequiresClauseSatisfiabilty(  EClass _class) throws IOException {
    if (_class.getMethods().size() < 1)     return;
    latex.write("\n\\begin{longtable}{|l|l|}");
    latex.write("\n\\caption{Methods Requires Clause Satisfiability}\\\\\n\\hline");
    latex.write("\nMethod & Satisfiability\\\\\n\\hline\n");
    String value;
    for (    EMethod _method : _class.getMethods()) {
      if (_method.getRequiresClauseSatisfiability())       value="{\\color{blue}$\\surd$}";
 else       value="{\\color{red}$\\times$}";
      String methodName=_method.getIdentifier().replace("_","\\_");
      latex.write(methodName + "&" + value);
      latex.write(endOfRow + "\n");
      latex.write("\\hline\n");
      latex.flush();
    }
    latex.write("\\end{longtable}");
    latex.flush();
  }
  public static void setText(  String str){
    _prj=EVMDDSMCGenerator.getPkgObject();
    if (str.contains("requires")) {
      parseRequires(str);
    }
    if (str.contains("stateTransition")) {
      parseTransitions(str);
    }
    if (str.contains("concurrentMethods")) {
      parseConcurrentMethods(str);
    }
    if (str.contains("sinkstates")) {
      parseSinkStates(str);
    }
  }
  private static void parseSinkStates(  String str){
    bSinkStates=true;
    int i=str.indexOf(":");
    String sinkStates=str.substring(i + 1);
    _prj.setSinkStates(sinkStates);
  }
  private static void parseConcurrentMethods(  String str){
    bconcurrency=true;
    int i=str.indexOf("_");
    str=str.substring(i + 1);
    i=str.indexOf("_");
    String className=str.substring(0,i);
    str=str.substring(i + 5);
    i=str.indexOf("_");
    String fromMethod=str.substring(0,i);
    i=str.indexOf("_and_");
    str=str.substring(i + 5);
    i=str.lastIndexOf("_");
    int j=str.indexOf(":");
    String toMethod=str.substring(i + 1,j);
    String concuurent=str.substring(j + 1);
    concuurent=concuurent.trim();
    if (concuurent.substring(0,1).compareTo("0") != 0) {
      LinkedList<EClass> _listClasses=_prj.getClasses();
      for (      EClass _class : _listClasses) {
        if (_class.getName().compareTo(className) == 0) {
          for (          EMethod _method : _class.getMethods())           if (_method.getIdentifier().compareTo(fromMethod) == 0)           _method.setConcurrentMethod(toMethod);
        }
      }
    }
  }
  private static void parseTransitions(  String str){
    bSTM=true;
    int i=str.indexOf("_of_");
    int j=str.indexOf("_from_");
    int k=str.indexOf("_to_");
    int l=str.indexOf(":");
    String className=str.substring(i + 4,j);
    String fromState=str.substring(j + 6,k);
    String toState=str.substring(k + 4,l);
    String transition=str.substring(l + 1);
    transition=transition.trim();
    if (transition.substring(0,1).compareTo("0") != 0) {
      LinkedList<EClass> _listClasses=_prj.getClasses();
      for (      EClass _class : _listClasses) {
        if (_class.getName().compareTo(className) == 0) {
          for (          EState _state : _class.getStates())           if (_state.getName().compareTo(fromState) == 0)           _state.setReachability(toState);
        }
      }
    }
 else {
      int test=0;
    }
  }
  private static void parseRequires(  String str){
    bRequires=true;
    int j=str.indexOf("_of_") + 4;
    str=str.substring(j);
    j=str.indexOf("_");
    String className=str.substring(0,j);
    str=str.substring(j + 1);
    int i=str.indexOf(":");
    String methodName=str.substring(0,i - 4);
    String reachability=str.substring(i + 1);
    reachability=reachability.trim();
    EMethod _method=getMethod(className,methodName);
    if (_method != null) {
      if (reachability.substring(0,1).compareTo("0") == 0)       _method.setRequiresClauseSatisfiability(false);
 else       _method.setRequiresClauseSatisfiability(true);
    }
  }
  private static EMethod getMethod(  String className,  String methodName){
    for (    EClass _class : _prj.getClasses()) {
      if (_class.getName().compareTo(className) == 0) {
        for (        EMethod _method : _class.getMethods()) {
          if (_method.getIdentifier().compareTo(methodName) == 0)           return _method;
        }
      }
    }
    return null;
  }
  private static void addConcurrencyMatrixColumns(  EClass _class) throws IOException {
    latex.write("\n\\begin{longtable}{|l");
    for (    EMethod _method : _class.getMethods()) {
      latex.write("|l");
    }
    latex.write("|}");
    latex.write("\n\\caption{Methods Concurrency Matrix}\\\\\n\\hline\n");
  }
  private static void addConcurrencyMatrixHeaders(  EClass _class) throws IOException {
    latex.write("");
    for (    EMethod _method : _class.getMethods())     latex.write("&" + beginSideWay + _method.getIdentifier().replace("_","\\_")+ endSideWay);
    latex.write(endOfRow);
    latex.write("\n\\hline\n");
  }
  private static void addSummaryTableRows() throws IOException {
    int totalClasses=_prj.getClasses().size();
    int totalMethods=0;
    int totalunsatifiedRequireClauses=0;
    int totalStates=0;
    int totalunreachableStates=0;
    int totalconcurrentmethods=0;
    for (    EClass _class : _prj.getClasses()) {
      latex.write("\\hyperref[" + _class.getName() + "]"+ "{\\color{Fuchsia}"+ _class.getName()+ "}");
      latex.write("&" + _class.getMethods().size());
      latex.write("&" + (_class.getStates().size() - 1));
      totalStates=totalStates + _class.getStates().size() - 1;
      int unsatifiedRequireClauses=0;
      for (      EMethod _method : _class.getMethods()) {
        totalMethods++;
        if (_method.getRequiresClauseSatisfiability() == false) {
          unsatifiedRequireClauses++;
          totalunsatifiedRequireClauses++;
        }
      }
      int unreachableStates=0;
      for (      EState _state : _class.getStates())       if (_state.isReachableState() == false) {
        unreachableStates++;
        totalunreachableStates++;
      }
      int concurrentMethods=0;
      for (      EMethod _method : _class.getMethods()) {
        if (_method.isConcurrentMethod()) {
          concurrentMethods++;
          totalconcurrentmethods++;
        }
      }
      if (bRequires == true)       latex.write("&" + unsatifiedRequireClauses);
      if (bSTM == true)       latex.write("&" + unreachableStates);
      if (bconcurrency == true)       latex.write("&" + concurrentMethods);
      latex.write(endOfRow);
      latex.write("\n" + hLine + "\n");
    }
    latex.write("Total Classes=" + totalClasses + "&"+ totalMethods+ "&"+ totalStates);
    if (bRequires == true)     latex.write("&" + totalunsatifiedRequireClauses);
    if (bSTM == true)     latex.write("&" + totalunreachableStates);
    if (bconcurrency == true)     latex.write("&" + totalconcurrentmethods);
    latex.write(endOfRow);
    latex.write("\n" + hLine + "\n");
  }
  public static void reset(){
    bRequires=false;
    bSTM=false;
    bSinkStates=false;
    bconcurrency=false;
  }
  public static EPackage get_prj(){
    Cloner cloner=new Cloner();
    _prj=cloner.deepClone(_prj);
    return _prj;
  }
  public static String getEndOfRow(){
    Cloner cloner=new Cloner();
    endOfRow=cloner.deepClone(endOfRow);
    return endOfRow;
  }
  public static String getHLine(){
    Cloner cloner=new Cloner();
    hLine=cloner.deepClone(hLine);
    return hLine;
  }
  public static String getBeginSideWay(){
    Cloner cloner=new Cloner();
    beginSideWay=cloner.deepClone(beginSideWay);
    return beginSideWay;
  }
  public static BufferedWriter getLatex(){
    Cloner cloner=new Cloner();
    latex=cloner.deepClone(latex);
    return latex;
  }
  public static String getEndSideWay(){
    Cloner cloner=new Cloner();
    endSideWay=cloner.deepClone(endSideWay);
    return endSideWay;
  }
}
