package aeminium.blackscholes.withlock;
import aeminium.blackscholes.MersenneTwisterFast;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public final class StdRandom {
  public ReentrantReadWriteLock random_seedLock=new ReentrantReadWriteLock();
  private static MersenneTwisterFast random;
  private static long seed;
static {
    seed=System.currentTimeMillis();
    random=new MersenneTwisterFast(seed);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private StdRandom(){
  }
  @Perm(requires="share(seed) * unique(random) in alive",ensures="share(seed) * unique(random) in alive") public static void setSeed(  long s){
    random_seedLock.writeLock().lock();
    seed=s;
    random=new MersenneTwisterFast(seed);
    random_seedLock.writeLock().unlock();
  }
  @Perm(requires="pure(seed) in alive",ensures="pure(seed) in alive") public static long getSeed(){
    try {
      random_seedLock.readLock().lock();
      return seed;
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(random) in alive",ensures="pure(random) in alive") public static double uniformO1(){
    try {
      random_seedLock.readLock().lock();
      return random.nextDouble();
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="pure(random) in alive",ensures="pure(random) in alive") public static int uniformO2(  int N){
    try {
      random_seedLock.readLock().lock();
      return random.nextInt(N);
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double random(){
    try {
      random_seedLock.readLock().lock();
      return uniformO1();
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static int uniformO3(  int a,  int b){
    try {
      random_seedLock.readLock().lock();
      return a + uniformO2(b - a);
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double uniform(  double a,  double b){
    try {
      random_seedLock.readLock().lock();
      return a + uniformO1() * (b - a);
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static boolean bernoulliO1(  double p){
    try {
      random_seedLock.readLock().lock();
      return uniformO1() < p;
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static boolean bernoulliO2(){
    return bernoulliO1(0.5);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double gaussianO1(){
    double r, x, y;
    do {
      x=uniform(-1.0,1.0);
      y=uniform(-1.0,1.0);
      r=x * x + y * y;
    }
 while (r >= 1 || r == 0);
    return x * Math.sqrt(-2 * Math.log(r) / r);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double gaussianO2(  double mean,  double stddev){
    return mean + stddev * gaussianO1();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static int geometric(  double p){
    try {
      random_seedLock.readLock().lock();
      return (int)Math.ceil(Math.log(uniformO1()) / Math.log(1.0 - p));
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static int poisson(  double lambda){
    int k=0;
    double p=1.0;
    double L=Math.exp(-lambda);
    do {
      k++;
      random_seedLock.readLock().lock();
      p*=uniformO1();
      random_seedLock.readLock().unlock();
    }
 while (p >= L);
    return k - 1;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double pareto(  double alpha){
    try {
      random_seedLock.readLock().lock();
      return Math.pow(1 - uniformO1(),-1.0 / alpha) - 1.0;
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double cauchy(){
    try {
      random_seedLock.readLock().lock();
      return Math.tan(Math.PI * (uniformO1() - 0.5));
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static int discrete(  double[] a){
    double EPSILON=1E-14;
    double sum=0.0;
    for (int i=0; i < a.length; i++) {
      if (a[i] < 0.0)       throw new IllegalArgumentException("array entry " + i + " is negative: "+ a[i]);
      sum=sum + a[i];
    }
    if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON)     throw new IllegalArgumentException("sum of array entries not equal to one: " + sum);
    while (true) {
      random_seedLock.readLock().lock();
      double r=uniformO1();
      random_seedLock.readLock().unlock();
      sum=0.0;
      for (int i=0; i < a.length; i++) {
        sum=sum + a[i];
        if (sum > r)         return i;
      }
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static double exp(  double lambda){
    try {
      random_seedLock.readLock().lock();
      return -Math.log(1 - uniformO1()) / lambda;
    }
  finally {
      random_seedLock.readLock().unlock();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void shuffleO1(  Object[] a){
    int N=a.length;
    for (int i=0; i < N; i++) {
      random_seedLock.readLock().lock();
      int r=i + uniformO2(N - i);
      random_seedLock.readLock().unlock();
      Object temp=a[i];
      a[i]=a[r];
      a[r]=temp;
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void shuffleO2(  double[] a){
    int N=a.length;
    for (int i=0; i < N; i++) {
      random_seedLock.readLock().lock();
      int r=i + uniformO2(N - i);
      random_seedLock.readLock().unlock();
      double temp=a[i];
      a[i]=a[r];
      a[r]=temp;
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void shuffleO3(  int[] a){
    int N=a.length;
    for (int i=0; i < N; i++) {
      random_seedLock.readLock().lock();
      int r=i + uniformO2(N - i);
      random_seedLock.readLock().unlock();
      int temp=a[i];
      a[i]=a[r];
      a[r]=temp;
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void shuffleO4(  Object[] a,  int lo,  int hi){
    if (lo < 0 || lo > hi || hi >= a.length)     throw new RuntimeException("Illegal subarray range");
    for (int i=lo; i <= hi; i++) {
      random_seedLock.readLock().lock();
      int r=i + uniformO2(hi - i + 1);
      random_seedLock.readLock().unlock();
      Object temp=a[i];
      a[i]=a[r];
      a[r]=temp;
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void shuffleO5(  double[] a,  int lo,  int hi){
    if (lo < 0 || lo > hi || hi >= a.length)     throw new RuntimeException("Illegal subarray range");
    for (int i=lo; i <= hi; i++) {
      random_seedLock.readLock().lock();
      int r=i + uniformO2(hi - i + 1);
      random_seedLock.readLock().unlock();
      double temp=a[i];
      a[i]=a[r];
      a[r]=temp;
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public static void shuffleO6(  int[] a,  int lo,  int hi){
    if (lo < 0 || lo > hi || hi >= a.length)     throw new RuntimeException("Illegal subarray range");
    for (int i=lo; i <= hi; i++) {
      random_seedLock.readLock().lock();
      int r=i + uniformO2(hi - i + 1);
      random_seedLock.readLock().unlock();
      int temp=a[i];
      a[i]=a[r];
      a[r]=temp;
    }
  }
  @Perm(requires="share(seed) * unique(random) in alive",ensures="share(seed) * unique(random) in alive") public static void main(  String[] args){
    int N=Integer.parseInt(args[0]);
    random_seedLock.writeLock().lock();
    if (args.length == 2)     StdRandom.setSeed(Long.parseLong(args[1]));
    double[] t={.5,.3,.1,.1};
    StdOut.println("seed = " + StdRandom.getSeed());
    for (int i=0; i < N; i++) {
      StdOut.printf("%2d ",uniformO2(100));
      StdOut.printf("%8.5f ",uniform(10.0,99.0));
      StdOut.printf("%5b ",bernoulliO1(.5));
      StdOut.printf("%7.5f ",gaussianO2(9.0,.2));
      StdOut.printf("%2d ",discrete(t));
      StdOut.println();
    }
    random_seedLock.writeLock().unlock();
    String[] a="A B C D E F G".split(" ");
    for (    String s : a)     StdOut.print(s + " ");
    StdOut.println();
  }
  public ReentrantReadWriteLock getRandom_seedLock(){
    Cloner cloner=new Cloner();
    random_seedLock=cloner.deepClone(random_seedLock);
    Cloner cloner=new Cloner();
    random_seedLock=cloner.deepClone(random_seedLock);
    return random_seedLock;
  }
  public static MersenneTwisterFast getRandom(){
    Cloner cloner=new Cloner();
    random=cloner.deepClone(random);
    Cloner cloner=new Cloner();
    random=cloner.deepClone(random);
    return random;
  }
}
