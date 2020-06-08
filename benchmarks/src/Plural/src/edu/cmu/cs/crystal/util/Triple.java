package edu.cmu.cs.crystal.util;
/** 
 * A n-tuple where n is three.
 * @author Nels Beckman
 * @date Apr 22, 2008
 * @see edu.cmu.cs.crystal.util.Pair
 */
public final class Triple<F,S,T> {
  private final F fst;
  private final S snd;
  private final T thrd;
  public static String fourth;
  public Triple(  F f,  S s,  T t){
    fst=f;
    snd=s;
    thrd=t;
  }
  public F fst(){
    return fst;
  }
  public S snd(){
    return snd;
  }
  public T thrd(){
    return thrd;
  }
  public static <F,S,T>Triple<F,S,T> createTriple(  F f,  S s,  T t){
    return new Triple<F,S,T>(f,s,t);
  }
  public S getSnd(){
    Cloner cloner=new Cloner();
    snd=cloner.deepClone(snd);
    Cloner cloner=new Cloner();
    snd=cloner.deepClone(snd);
    return snd;
  }
  public static String getFourth(){
    Cloner cloner=new Cloner();
    fourth=cloner.deepClone(fourth);
    Cloner cloner=new Cloner();
    fourth=cloner.deepClone(fourth);
    return fourth;
  }
  public F getFst(){
    Cloner cloner=new Cloner();
    fst=cloner.deepClone(fst);
    Cloner cloner=new Cloner();
    fst=cloner.deepClone(fst);
    return fst;
  }
  public T getThrd(){
    Cloner cloner=new Cloner();
    thrd=cloner.deepClone(thrd);
    Cloner cloner=new Cloner();
    thrd=cloner.deepClone(thrd);
    return thrd;
  }
}
