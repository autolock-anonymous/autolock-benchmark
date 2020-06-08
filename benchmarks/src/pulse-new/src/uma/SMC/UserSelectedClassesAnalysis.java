package uma.SMC;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.core.resources.IFile;
import uma.structure.EPackage;
public class UserSelectedClassesAnalysis {
  static Time starttime, endtime;
  public BufferedWriter dot;
  public static int testType;
  private CompilationUnit getCompilationUnit(  String prog){
    ASTParser parser=ASTParser.newParser(3);
    parser.setSource(prog.toCharArray());
    CompilationUnit cu=(CompilationUnit)parser.createAST(null);
    return cu;
  }
  public static void analyzeFromCommandLine(  LinkedList<String> inputFiles,  String strType,  String strK){
    EVMDDSMCGenerator.reset();
    testType=Integer.parseInt(strType);
    Boolean JML;
    for (    String input : inputFiles) {
      JML=false;
      if (input.contains("//@") == true) {
        JML=true;
        JMLAnnotatedJavaClass JClass=new JMLAnnotatedJavaClass();
        input=JClass.translateJMLAnnotationsToPlural(input);
      }
      ASTParser parser=ASTParser.newParser(3);
      parser.setSource(input.toCharArray());
      CompilationUnit result=(CompilationUnit)parser.createAST(null);
      SMCVisitor visitor=new SMCVisitor();
      result.accept(visitor);
      if (JML == true) {
        input=EVMDDSMCGenerator.modifyConstructorSpecifications(input);
        EGeneratedPluralSpecification.createFromCommandLine(input,"pluralAnnotated");
      }
    }
    ESMCModel.setK(Integer.parseInt(strK));
    EPackage pkg=EVMDDSMCGenerator.getPkgObject();
    ESMCModel.generateSMCmodelCommandLine(testType);
    callModelCheckerThroughCommandLine();
  }
  public void analyzeFromPlugin(  List<ICompilationUnit> compilationUnitList,  int test){
    try {
      testType=test;
      EVMDDSMCGenerator.reset();
      Boolean JML;
      for (      ICompilationUnit cunit : compilationUnitList) {
        JML=false;
        String prog=getInputStream(cunit);
        CompilationUnit cu=null;
        if (prog.contains("//@") == true) {
          JML=true;
          JMLAnnotatedJavaClass JClass=new JMLAnnotatedJavaClass();
          prog=JClass.translateJMLAnnotationsToPlural(prog);
          cu=getCompilationUnit(prog);
        }
 else         cu=getCompilationUnit(cunit);
        SMCVisitor visitor=new SMCVisitor();
        cu.accept(visitor);
        if (JML == true) {
          prog=EVMDDSMCGenerator.modifyConstructorSpecifications(prog);
          String className=EVMDDSMCGenerator.getPkgObject().getClasses().getLast().getName();
          EGeneratedPluralSpecification.createFromPlugin(prog,className);
        }
      }
      ESMCModel.generateSMCmodelPlugin(EVMDDSMCGenerator.getPkgObject(),test);
      starttime=getTime();
      callModelCheckerThroughCommandLine();
    }
 catch (    Exception e) {
      e.printStackTrace();
    }
  }
  private CompilationUnit getCompilationUnit(  ICompilationUnit cunit){
    CompilationUnit compilationUnit=(CompilationUnit)WorkspaceUtilities.getASTNodeFromCompilationUnit(cunit);
    return compilationUnit;
  }
  public String getInputStream(  ICompilationUnit unit){
    InputStream in=null;
    if (unit != null) {
      try {
        in=((IFile)(unit.getCorrespondingResource())).getContents();
      }
 catch (      CoreException e) {
        e.printStackTrace();
      }
      BufferedInputStream bis=new BufferedInputStream(in);
      ByteArrayOutputStream buf=new ByteArrayOutputStream();
      int result;
      try {
        result=bis.read();
        while (result != -1) {
          byte b=(byte)result;
          buf.write(b);
          result=bis.read();
        }
      }
 catch (      IOException e) {
        e.printStackTrace();
      }
      return buf.toString();
    }
    return null;
  }
  public static void callModelCheckerThroughPlugin(){
    try {
      IWorkspace workspace=ResourcesPlugin.getWorkspace();
      String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
      EOutputLatex.reset();
      FileWriter fshell=new FileWriter(folder + "/location.sh");
      fshell.write(folder + "/evmdd-smc -q" + " < "+ folder+ "/model.stm");
      fshell.close();
      Process p1=Runtime.getRuntime().exec("chmod 777 " + "./location.sh");
      p1.waitFor();
      ArrayList<String> command=new ArrayList<String>();
      command.add(folder + "/location.sh");
      System.out.println("Checking model.stm ...");
      ProcessBuilder builder=new ProcessBuilder(command);
      final Process p=builder.start();
      InputStream is=p.getInputStream();
      InputStreamReader isr=new InputStreamReader(is);
      BufferedReader br=new BufferedReader(isr);
      String line;
      while ((line=br.readLine()) != null) {
        System.out.println(line);
        EOutputLatex.setText(line);
        if (testType == 3 || testType == 0)         EGrarphWriter.addTrnsitions(line);
        if (testType == 2 || testType == 0)         EGrarphWriter.parseMethodReachability(line);
      }
      p.waitFor();
      if (testType == 3 || testType == 0) {
        EGrarphWriter.createGraph();
        System.out.println("State Transition Graphs are created in folder " + folder);
        printMetrics();
      }
      if (testType == 2 || testType == 0) {
        EGrarphWriter.createGraph();
        printMethodMetrics();
      }
      endtime=getTime();
      System.out.println("Model Checked");
      createPdfSummaryPlugin();
      System.out.println("Start Time: " + starttime.toString());
      System.out.println("End Time: " + endtime.toString());
      System.out.println("Elapsed Time in milli seconds: " + (endtime.msecond - starttime.msecond));
    }
 catch (    Exception err) {
      err.printStackTrace();
    }
  }
  private static void createPdfSummaryPlugin() throws IOException {
    EOutputLatex.create_Plugin();
    try {
      makePdfPlugin();
    }
 catch (    InterruptedException e) {
      e.printStackTrace();
    }
  }
  private static void CreatePdfSummary_CommandLine() throws IOException {
    EOutputLatex.create_CommandLine();
    makePdfCommandLine();
  }
  private static void makePdfCommandLine() throws IOException {
    File workingDirectory=new File(".");
    System.out.println("model.pdf generated in the directory " + workingDirectory.getAbsolutePath());
    IWorkspace workspace=ResourcesPlugin.getWorkspace();
    String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
    String cmd="/usr/local/texlive/2015/bin/x86_64-darwin/pdflatex " + folder + "/model.tex";
    try {
      Runtime run=Runtime.getRuntime();
      Process pr=run.exec(cmd);
      BufferedReader buf=new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line="";
      while ((line=buf.readLine()) != null)       System.out.println(line);
      pr.waitFor();
      run.exec("open -a Preview " + workingDirectory.getAbsolutePath() + "/model.pdf");
      run.exec("open -a Preview " + folder + "/model.pdf");
    }
 catch (    InterruptedException e) {
      e.printStackTrace();
    }
  }
  private static void makePdfPlugin() throws IOException, InterruptedException {
    File workingDirectory=new File(".");
    System.out.println("model.pdf generated in the directory " + workingDirectory.getAbsolutePath());
    IWorkspace workspace=ResourcesPlugin.getWorkspace();
    String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
    String cmd="/usr/local/texlive/2015/bin/x86_64-darwin/pdflatex " + folder + "/model.tex";
    try {
      Runtime run=Runtime.getRuntime();
      Process pr=run.exec(cmd);
      BufferedReader buf=new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line="";
      while ((line=buf.readLine()) != null)       System.out.println(line);
      pr.waitFor();
      run.exec("open -a Preview " + workingDirectory.getAbsolutePath() + "/model.pdf");
    }
 catch (    InterruptedException e) {
      e.printStackTrace();
    }
  }
  public static void callModelCheckerThroughCommandLine(){
    IWorkspace workspace=ResourcesPlugin.getWorkspace();
    String folder=workspace.getRoot().getLocation().toFile().getPath().toString();
    String line="";
    try {
      if (testType == 3 || testType == 0)       EGrarphWriter.addTrnsitions(line);
      if (testType == 2 || testType == 0)       EGrarphWriter.parseMethodReachability(line);
      if (testType == 3 || testType == 0) {
        EGrarphWriter.createGraph();
        printMetrics();
      }
      if (testType == 2 || testType == 0) {
        EGrarphWriter.createGraph();
        printMethodMetrics();
      }
      endtime=getTime();
      CreatePdfSummary_CommandLine();
    }
 catch (    Exception err) {
      err.printStackTrace();
    }
  }
  private static void printMethodMetrics(){
    System.out.println("The total number of unreachable methods are :" + EGrarphWriter.getNumberofUnReachableMethods());
    EGrarphWriter.setNumberofUnReachableMethods();
  }
  private static void printMetrics(){
    EPackage _pkg=EVMDDSMCGenerator.getPkgObject();
    int totalstates=_pkg.getTotalStates();
    int reachablesates=_pkg.getTotalReachableStates();
    int unreachblestates=totalstates - reachablesates;
    System.out.println("The total number of states are 			 :" + totalstates);
    System.out.println("The total number of reachable states are :" + reachablesates);
    System.out.println("The total number of unreachable states are :" + unreachblestates);
  }
  public static Time getTime(){
    Time t=new Time();
    Calendar calendar=new GregorianCalendar();
    t.hour=calendar.get(Calendar.HOUR);
    t.minute=calendar.get(Calendar.MINUTE);
    t.second=calendar.get(Calendar.SECOND);
    t.msecond=calendar.getTimeInMillis();
    return t;
  }
  public BufferedWriter getDot(){
    Cloner cloner=new Cloner();
    dot=cloner.deepClone(dot);
    return dot;
  }
  public static Time getStarttime(){
    Cloner cloner=new Cloner();
    starttime=cloner.deepClone(starttime);
    return starttime;
  }
}
