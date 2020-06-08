package aeminium.fft.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Complex {
  public ReentrantReadWriteLock im_reLock=new ReentrantReadWriteLock();
  public double re;
  public double im;
  @Perm(requires="unique(re) * unique(im) in alive",ensures="unique(re) * unique(im) in alive") public Complex(  double real,  double imag){
    im_reLock.writeLock().lock();
    re=real;
    im=imag;
    im_reLock.writeLock().unlock();
  }
  @Perm(requires="immutable(re) * immutable(im) in alive",ensures="immutable(re) * immutable(im) in alive") public Complex plus(  Complex b){
    Complex a=this;
    double real=this.re + b.re;
    double imag=this.im + b.im;
    return null;
  }
  @Perm(requires="immutable(re) * immutable(im) in alive",ensures="immutable(re) * immutable(im) in alive") public Complex minus(  Complex b){
    Complex a=this;
    double real=this.re - b.re;
    double imag=this.im - b.im;
    return null;
  }
  @Perm(requires="immutable(re) * immutable(im) in alive",ensures="immutable(re) * immutable(im) in alive") public Complex times(  Complex b){
    Complex a=this;
    double real=this.re * b.re - this.im * b.im;
    double imag=this.re * b.im + this.im * b.re;
    return null;
  }
  public ReentrantReadWriteLock getIm_reLock(){
    Cloner cloner=new Cloner();
    im_reLock=cloner.deepClone(im_reLock);
    Cloner cloner=new Cloner();
    im_reLock=cloner.deepClone(im_reLock);
    return im_reLock;
  }
}
