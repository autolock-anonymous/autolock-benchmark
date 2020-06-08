package jomp.euler.withlock;
import java.io.*;
import jomp.jgfutil.JGFInstrumentor;
import jomp.jgfutil.JGFSection3;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class JGFEulerBench extends Tunnel implements JGFSection3 {
  public ReentrantReadWriteLock errorLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock sizeLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock=new ReentrantReadWriteLock();
  @Perm(requires="share(size) in alive",ensures="share(size) in alive") public void JGFsetsize(  int size){
    sizeLock.writeLock().lock();
    this.size=size;
    sizeLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFinitialise(){
    try {
      initialise();
    }
 catch (    Exception e) {
      e.printStackTrace();
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFapplication(){
    runiters();
  }
  @Perm(requires="pure(error) * pure(size) in alive",ensures="pure(error) * pure(size) in alive") public void JGFvalidate(){
    double refval[]={0.0033831416599344965,0.006812543658280322};
    sizeLock.readLock().lock();
    errorLock.readLock().lock();
    double dev=Math.abs(error - refval[size]);
    if (dev > 1.0e-12) {
      System.out.println("Validation failed");
      System.out.println("Computed RMS pressure error = " + error);
      System.out.println("Reference value = " + refval[size]);
    }
    errorLock.readLock().unlock();
    sizeLock.readLock().unlock();
  }
  @Perm(requires="unique(deltat) * unique(opg) * unique(pg) * unique(pg1) * unique(sxi) * unique(seta) * unique(tg) * unique(tg1) * unique(xnode) * unique(ynode) * unique(d) * unique(f) * unique(g) * unique(r) * unique(ug1) * unique(ug) in alive",ensures="unique(deltat) * unique(opg) * unique(pg) * unique(pg1) * unique(sxi) * unique(seta) * unique(tg) * unique(tg1) * unique(xnode) * unique(ynode) * unique(d) * unique(f) * unique(g) * unique(r) * unique(ug1) * unique(ug) in alive") public void JGFtidyup(){
    d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock.writeLock().lock();
    deltat=null;
    opg=null;
    pg=null;
    pg1=null;
    sxi=null;
    seta=null;
    tg=null;
    tg1=null;
    xnode=null;
    ynode=null;
    d=null;
    f=null;
    g=null;
    r=null;
    ug1=null;
    ug=null;
    d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock.writeLock().unlock();
    System.gc();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") public void JGFrun(  int size){
    sizeLock.writeLock().lock();
    JGFsetsize(size);
    JGFinitialise();
    JGFapplication();
    JGFvalidate();
    sizeLock.writeLock().unlock();
    d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock.writeLock().lock();
    JGFtidyup();
    d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getD_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock(){
    Cloner cloner=new Cloner();
    d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock=cloner.deepClone(d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock);
    return d_deltat_f_g_opg_pg_pg1_r_seta_sxi_tg_tg1_ug_ug1_xnode_ynodeLock;
  }
  public ReentrantReadWriteLock getErrorLock(){
    Cloner cloner=new Cloner();
    errorLock=cloner.deepClone(errorLock);
    return errorLock;
  }
  public ReentrantReadWriteLock getSizeLock(){
    Cloner cloner=new Cloner();
    sizeLock=cloner.deepClone(sizeLock);
    return sizeLock;
  }
}
