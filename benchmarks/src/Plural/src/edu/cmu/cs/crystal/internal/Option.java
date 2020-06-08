package edu.cmu.cs.crystal.internal;
/** 
 * @author Nels E. Beckman
 */
public abstract class Option<T> {
  @SuppressWarnings("unchecked") private static final Option<?> NONE;
  @SuppressWarnings("unchecked") public static <T>Option<T> none(){
    System.out.println("None = " + NONE);
    return (Option<T>)NONE;
  }
  public static <T>Option<T> some(  final T t){
    return new Option<T>(){
      @Override public boolean isNone(){
        return false;
      }
      @Override public boolean isSome(){
        return true;
      }
      @Override public T unwrap(){
        return t;
      }
      @Override public String toString(){
        return "SOME(" + t.toString() + ")"+ NONE;
      }
    }
;
  }
  public static <T>Option<T> wrap(  final T t){
    if (t == null)     return none();
 else     return some(t);
  }
  public abstract T unwrap();
  public abstract boolean isSome();
  public abstract boolean isNone();
  public static Option<?> getNONE(){
    Cloner cloner=new Cloner();
    NONE=cloner.deepClone(NONE);
    Cloner cloner=new Cloner();
    NONE=cloner.deepClone(NONE);
    return NONE;
  }
}
