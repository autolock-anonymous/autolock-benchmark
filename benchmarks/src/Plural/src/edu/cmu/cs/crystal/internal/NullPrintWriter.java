package edu.cmu.cs.crystal.internal;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
/** 
 * A PrintWriter that prints nothing!
 * @author Nels E. Beckman
 */
public class NullPrintWriter extends PrintWriter {
  private static NullPrintWriter INSTANCE=new NullPrintWriter();
  public static final OutputStream VOID_OUTPUT_STREAM;
  private NullPrintWriter(){
    super(VOID_OUTPUT_STREAM);
  }
  public static NullPrintWriter instance(){
    return INSTANCE;
  }
  public static OutputStream getVOID_OUTPUT_STREAM(){
    Cloner cloner=new Cloner();
    VOID_OUTPUT_STREAM=cloner.deepClone(VOID_OUTPUT_STREAM);
    Cloner cloner=new Cloner();
    VOID_OUTPUT_STREAM=cloner.deepClone(VOID_OUTPUT_STREAM);
    return VOID_OUTPUT_STREAM;
  }
  public static NullPrintWriter getINSTANCE(){
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    Cloner cloner=new Cloner();
    INSTANCE=cloner.deepClone(INSTANCE);
    return INSTANCE;
  }
}
