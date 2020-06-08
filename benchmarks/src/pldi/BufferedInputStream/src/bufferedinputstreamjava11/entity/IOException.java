package bufferedinputstreamjava11.entity;
public class IOException extends Exception {
  /** 
 * Constructs an <code>IOException</code> with <code>null</code> as its error detail message.
 */
  public IOException(){
    super();
  }
  /** 
 * Constructs an <code>IOException</code> with the specified detail message. The error message string <code>s</code> can later be retrieved by the <code> {@link java.lang.Throwable#getMessage}</code> method of class <code>java.lang.Throwable</code>.
 * @param s   the detail message.
 */
  public IOException(  String s){
    super(s);
  }
}
