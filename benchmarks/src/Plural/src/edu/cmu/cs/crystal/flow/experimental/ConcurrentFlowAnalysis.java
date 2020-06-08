package edu.cmu.cs.crystal.flow.experimental;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import edu.cmu.cs.crystal.Crystal;
import edu.cmu.cs.crystal.flow.FlowAnalysis;
import edu.cmu.cs.crystal.flow.IBranchSensitiveTransferFunction;
import edu.cmu.cs.crystal.flow.IFlowAnalysis;
import edu.cmu.cs.crystal.flow.IResult;
import edu.cmu.cs.crystal.flow.ITransferFunction;
import edu.cmu.cs.crystal.flow.LatticeElement;
import edu.cmu.cs.crystal.util.Utilities;
/** 
 * An implementation of IFlowAnalysis that analyzes methods in concurrently. Creates a thread pool, and when analyzedPreemtively is called (or the constructor that takes a list of methods) we generate a future for each method declaration, force its analysis in a brand new FlowAnalysis object, and store that in a mapping from method declarations to IFlowAnalysis objects. Later, we can delegate the standard flow analysis methods on the future, forcing its completion. This class is EXPERIMENTAL because certain shared classes, most notably EclipseTAC, are not yet thought to be thread-safe. The biggest worry is that there is no memory barrier between different analyses.
 * @author Nels Beckman
 * @date Jan 24, 2008
 * @param < LE >
 */
public class ConcurrentFlowAnalysis<LE extends LatticeElement<LE>> implements IFlowAnalysis<LE> {
  /** 
 * Thread pool for executing concurrent flow analyses.
 */
  private ExecutorService threadPool=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  private final Map<MethodDeclaration,Future<IFlowAnalysis<LE>>> analyzedMethods=new HashMap<MethodDeclaration,Future<IFlowAnalysis<LE>>>();
  private final Crystal myCrystal;
  private final ITransferFunction<LE> transferFunction;
  protected final IFlowAnalysis<LE> defaultFlowAnalysis;
  @SuppressWarnings("unused") private ConcurrentFlowAnalysis(){
    throw new RuntimeException("Bug: Invalid constuctor called");
  }
  /** 
 * Creates a new concurrent flow analysis and begins to analyze the given method bodies immediately. 
 * @param transferFunction The transfer function defining the analysis.
 * @param methods Starts analyzing these methods immediately in background threads.
 * @param crystal
 */
  public ConcurrentFlowAnalysis(  ITransferFunction<LE> transferFunction,  List<MethodDeclaration> methods,  Crystal crystal){
    this(transferFunction,crystal);
    analyzePreemitively(methods);
  }
  /** 
 * Creates a new concurrent flow analysis but does not analyze any methods immediately. 
 * @see analyzePreemitively
 * @param transferFunction
 * @param crystal
 */
  public ConcurrentFlowAnalysis(  ITransferFunction<LE> transferFunction,  Crystal crystal){
    this.myCrystal=crystal;
    this.transferFunction=transferFunction;
    this.defaultFlowAnalysis=this.createNewFlowAnalysis(transferFunction,crystal);
  }
  /** 
 * Perform dataflow analysis asynchronously on the list of methods given. This method should return relatively quickly, but analysis will be performed in another thread. If you have already called the constructor that takes a list of methods, calling this method is not required unless you have added new methods to analyze.
 * @param methods
 */
  public void analyzePreemitively(  List<MethodDeclaration> methods){
    for (    final MethodDeclaration decl : methods) {
      if (this.analyzedMethods.containsKey(decl))       continue;
      Future<IFlowAnalysis<LE>> future=threadPool.submit(new Callable<IFlowAnalysis<LE>>(){
        public IFlowAnalysis<LE> call() throws Exception {
          IFlowAnalysis<LE> analyzer=ConcurrentFlowAnalysis.this.createNewFlowAnalysis(transferFunction,myCrystal);
          analyzer.getResultsAfter(decl);
          return analyzer;
        }
      }
);
      analyzedMethods.put(decl,future);
    }
  }
  @SuppressWarnings("unchecked") private IFlowAnalysis<LE> createNewFlowAnalysis(  ITransferFunction<LE> transferFunction,  Crystal crystal){
    if (transferFunction instanceof IBranchSensitiveTransferFunction) {
      return new FlowAnalysis<LE>((IBranchSensitiveTransferFunction<LE>)transferFunction);
    }
 else {
      return new FlowAnalysis<LE>(transferFunction);
    }
  }
  public IResult<LE> getLabeledResultsAfter(  ASTNode node){
    MethodDeclaration decl=Utilities.getMethodDeclaration(node);
    if (decl != null && this.analyzedMethods.containsKey(decl)) {
      try {
        return this.analyzedMethods.get(decl).get().getLabeledResultsAfter(node);
      }
 catch (      Exception e) {
      }
    }
    return addAsFakeFuture(decl,defaultFlowAnalysis).getLabeledResultsAfter(node);
  }
  public IResult<LE> getLabeledResultsBefore(  ASTNode node){
    MethodDeclaration decl=Utilities.getMethodDeclaration(node);
    if (decl != null && this.analyzedMethods.containsKey(decl)) {
      try {
        return this.analyzedMethods.get(decl).get().getLabeledResultsBefore(node);
      }
 catch (      Exception e) {
      }
    }
    return addAsFakeFuture(decl,defaultFlowAnalysis).getLabeledResultsBefore(node);
  }
  public LE getResultsAfter(  ASTNode node){
    MethodDeclaration decl=Utilities.getMethodDeclaration(node);
    if (decl != null && this.analyzedMethods.containsKey(decl)) {
      try {
        return this.analyzedMethods.get(decl).get().getResultsAfter(node);
      }
 catch (      Exception e) {
      }
    }
    return addAsFakeFuture(decl,defaultFlowAnalysis).getResultsAfter(node);
  }
  public LE getResultsBefore(  ASTNode node){
    MethodDeclaration decl=Utilities.getMethodDeclaration(node);
    if (decl != null && this.analyzedMethods.containsKey(decl)) {
      try {
        return this.analyzedMethods.get(decl).get().getResultsBefore(node);
      }
 catch (      Exception e) {
      }
    }
    return addAsFakeFuture(decl,defaultFlowAnalysis).getResultsBefore(node);
  }
  private IFlowAnalysis<LE> addAsFakeFuture(  MethodDeclaration decl,  final IFlowAnalysis<LE> fa){
    this.analyzedMethods.put(decl,new Future<IFlowAnalysis<LE>>(){
      public boolean cancel(      boolean mayInterruptIfRunning){
        return false;
      }
      public IFlowAnalysis<LE> get() throws InterruptedException, ExecutionException {
        return fa;
      }
      public IFlowAnalysis<LE> get(      long timeout,      TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return fa;
      }
      public boolean isCancelled(){
        return false;
      }
      public boolean isDone(){
        return true;
      }
    }
);
    return fa;
  }
  protected Map<MethodDeclaration,Future<IFlowAnalysis<LE>>> getAnalyzedMethods(){
    Cloner cloner=new Cloner();
    analyzedMethods=cloner.deepClone(analyzedMethods);
    Cloner cloner=new Cloner();
    analyzedMethods=cloner.deepClone(analyzedMethods);
    return analyzedMethods;
  }
  public LE getEndResults(  MethodDeclaration d){
    throw new UnsupportedOperationException("Unimplemented");
  }
  public IResult<LE> getLabeledEndResult(  MethodDeclaration d){
    throw new UnsupportedOperationException("Unimplemented");
  }
  public IResult<LE> getLabeledStartResult(  MethodDeclaration d){
    throw new UnsupportedOperationException("Unimplemented");
  }
  public LE getStartResults(  MethodDeclaration d){
    throw new UnsupportedOperationException("Unimplemented");
  }
  public Crystal getMyCrystal(){
    Cloner cloner=new Cloner();
    myCrystal=cloner.deepClone(myCrystal);
    Cloner cloner=new Cloner();
    myCrystal=cloner.deepClone(myCrystal);
    return myCrystal;
  }
  public ITransferFunction<LE> getTransferFunction(){
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    Cloner cloner=new Cloner();
    transferFunction=cloner.deepClone(transferFunction);
    return transferFunction;
  }
  public IFlowAnalysis<LE> getDefaultFlowAnalysis(){
    Cloner cloner=new Cloner();
    defaultFlowAnalysis=cloner.deepClone(defaultFlowAnalysis);
    Cloner cloner=new Cloner();
    defaultFlowAnalysis=cloner.deepClone(defaultFlowAnalysis);
    return defaultFlowAnalysis;
  }
  public ExecutorService getThreadPool(){
    Cloner cloner=new Cloner();
    threadPool=cloner.deepClone(threadPool);
    Cloner cloner=new Cloner();
    threadPool=cloner.deepClone(threadPool);
    return threadPool;
  }
}
