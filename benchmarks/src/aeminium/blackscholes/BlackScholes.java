package aeminium.blackscholes.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class BlackScholes {
  public static int DEFAULTTHRESHOLD=10000;
  public static double S=23.75;
  public static double X=15.00;
  public static double r=0.01;
  public static double sigma=0.35;
  public static double T=0.5;
}
