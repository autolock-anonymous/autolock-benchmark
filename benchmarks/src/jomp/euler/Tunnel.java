package jomp.euler.withlock;
import java.io.*;
import edu.cmu.cs.plural.annot.Perm;
import jomp.jgfutil.JGFInstrumentor;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Tunnel {
  public ReentrantReadWriteLock iterLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock nfLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock datasizesLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock machffLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock ntimeLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock secondOrderDampingLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock fourthOrderDampingLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock=new ReentrantReadWriteLock();
  int size;
  int datasizes[]={8,12};
  double machff=0.7;
  public double secondOrderDamping=1.0;
  public double fourthOrderDamping=1.0;
  public int ntime=1;
  int scale;
  double error;
  double aofTunnel[][];
  double deltat[][];
  double opg[][], pg[][], pg1[][];
  double sxi[][], seta[][];
  double tg[][], tg1[][];
  double xnode[][], ynode[][];
  double oldval[][][], newval[][][];
  double cff, uff, vff, pff, rhoff, tff, jplusff, jminusff;
  double datamax, datamin;
  int iter=100;
  int imax, jmax;
  int imaxin, jmaxin;
  int nf=6;
  Statevector d[][];
  Statevector f[][], g[][];
  Statevector r[][], ug1[][];
  Statevector ug[][];
  final double Cp=1004.5;
  final double Cv=717.5;
  final double gamma=1.4;
  final double rgas=287.0;
  final double fourthOrderNormalizer=0.02;
  final double secondOrderNormalizer=0.02;
  @Perm(requires="unique(scale) * none(datasizes) * pure(size) * unique(imaxin) * unique(jmaxin) * unique(oldval) * none(nf) * full(imax) * share(jmax) * unique(newval) * unique(deltat) * unique(opg) * unique(pg) * unique(pg1) * unique(sxi) * unique(seta) * unique(tg) * unique(tg1) * unique(ug) * unique(aofTunnel) * unique(d) * unique(f) * unique(g) * unique(r) * unique(ug1) * unique(xnode) * unique(ynode) * full(cff) * full(vff) * full(pff) * full(rhoff) * full(tff) * share(a) * share(b) * share(c) * share(d) in alive",ensures="unique(scale) * none(datasizes) * pure(size) * unique(imaxin) * unique(jmaxin) * unique(oldval) * none(nf) * full(imax) * share(jmax) * unique(newval) * unique(deltat) * unique(opg) * unique(pg) * unique(pg1) * unique(sxi) * unique(seta) * unique(tg) * unique(tg1) * unique(ug) * unique(aofTunnel) * unique(d) * unique(f) * unique(g) * unique(r) * unique(ug1) * unique(xnode) * unique(ynode) * full(cff) * full(vff) * full(pff) * full(rhoff) * full(tff) * share(a) * share(b) * share(c) * share(d) in alive") public void initialise() throws Exception {
    int i, j, k, n;
    double scrap, scrap2;
    try {
      a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
      scale=datasizes[size];
      FileReader instream=new FileReader("tunnel.dat");
      StreamTokenizer intokens=new StreamTokenizer(instream);
      if (intokens.nextToken() == StreamTokenizer.TT_NUMBER)       imaxin=(int)intokens.nval;
 else       throw new IOException();
      if (intokens.nextToken() == StreamTokenizer.TT_NUMBER)       jmaxin=(int)intokens.nval;
 else       throw new IOException();
      oldval=new double[nf][imaxin + 1][jmaxin + 1];
      for (i=0; i < imaxin; i++) {
        for (j=0; j < jmaxin; j++) {
          for (k=0; k < nf; k++) {
            if (intokens.nextToken() == StreamTokenizer.TT_NUMBER) {
              oldval[k][i][j]=(double)intokens.nval;
            }
 else {
              throw new IOException();
            }
          }
        }
      }
      imax=(imaxin - 1) * scale + 1;
      jmax=(jmaxin - 1) * scale + 1;
      newval=new double[nf][imax][jmax];
      for (k=0; k < nf; k++) {
        for (i=0; i < imax; i++) {
          for (j=0; j < jmax; j++) {
            int iold=i / scale;
            int jold=j / scale;
            double xf=((double)i % scale) / ((double)scale);
            double yf=((double)j % scale) / ((double)scale);
            newval[k][i][j]=(1.0 - xf) * (1.0 - yf) * oldval[k][iold][jold] + (1.0 - xf) * yf * oldval[k][iold][jold + 1] + xf * (1.0 - yf) * oldval[k][iold + 1][jold] + xf * yf * oldval[k][iold + 1][jold + 1];
          }
        }
      }
      deltat=new double[imax + 1][jmax + 2];
      opg=new double[imax + 2][jmax + 2];
      pg=new double[imax + 2][jmax + 2];
      pg1=new double[imax + 2][jmax + 2];
      sxi=new double[imax + 2][jmax + 2];
      ;
      seta=new double[imax + 2][jmax + 2];
      ;
      tg=new double[imax + 2][jmax + 2];
      tg1=new double[imax + 2][jmax + 2];
      ug=new Statevector[imax + 2][jmax + 2];
      aofTunnel=new double[imax][jmax];
      d=new Statevector[imax + 2][jmax + 2];
      f=new Statevector[imax + 2][jmax + 2];
      g=new Statevector[imax + 2][jmax + 2];
      r=new Statevector[imax + 2][jmax + 2];
      ug1=new Statevector[imax + 2][jmax + 2];
      xnode=new double[imax][jmax];
      ynode=new double[imax][jmax];
      for (i=0; i < imax + 2; i++)       for (j=0; j < jmax + 2; ++j) {
        d[i][j]=new Statevector();
        f[i][j]=new Statevector();
        g[i][j]=new Statevector();
        r[i][j]=new Statevector();
        ug[i][j]=new Statevector();
        ug1[i][j]=new Statevector();
      }
      cff=1.0;
      vff=0.0;
      pff=1.0 / gamma;
      rhoff=1.0;
      tff=pff / (rhoff * rgas);
      for (i=0; i < imax; i++) {
        for (j=0; j < jmax; j++) {
          xnode[i][j]=newval[0][i][j];
          ynode[i][j]=newval[1][i][j];
          ug[i + 1][j + 1].a=newval[2][i][j];
          ug[i + 1][j + 1].b=newval[3][i][j];
          ug[i + 1][j + 1].c=newval[4][i][j];
          ug[i + 1][j + 1].d=newval[5][i][j];
          scrap=ug[i + 1][j + 1].c / ug[i + 1][j + 1].a;
          scrap2=ug[i + 1][j + 1].b / ug[i + 1][j + 1].a;
          tg[i + 1][j + 1]=ug[i + 1][j + 1].d / ug[i + 1][j + 1].a - (0.5 * (scrap * scrap + scrap2 * scrap2));
          tg[i + 1][j + 1]=tg[i + 1][j + 1] / Cv;
          pg[i + 1][j + 1]=rgas * ug[i + 1][j + 1].a * tg[i + 1][j + 1];
        }
      }
      for (i=1; i < imax; i++)       for (j=1; j < jmax; ++j)       aofTunnel[i][j]=0.5 * ((xnode[i][j] - xnode[i - 1][j - 1]) * (ynode[i - 1][j] - ynode[i][j - 1]) - (ynode[i][j] - ynode[i - 1][j - 1]) * (xnode[i - 1][j] - xnode[i][j - 1]));
      oldval=null;
      newval=null;
    }
  finally {
      a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
    }
  }
  @Perm(requires="pure(imax) * pure(jmax) * pure(opg) * pure(pg) * pure(tg) * pure(ug) * share(a) * pure(ug1) * pure(deltat) * pure(aofTunnel) * pure(r) * pure(d) * share(b) * share(c) * share(d) * pure(pg1) * pure(tg1) * share(error) in alive",ensures="pure(imax) * pure(jmax) * pure(opg) * pure(pg) * pure(tg) * pure(ug) * share(a) * pure(ug1) * pure(deltat) * pure(aofTunnel) * pure(r) * pure(d) * share(b) * share(c) * share(d) * pure(pg1) * pure(tg1) * share(error) in alive") void doIteration(){
    double scrap;
    int i, j;
    Vector2 crap;
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
    for (i=1; i < imax; ++i)     for (j=1; j < jmax; ++j) {
      opg[i][j]=pg[i][j];
    }
    calculateDummyCells(pg,tg,ug);
    calculateDeltaT();
    calculateDamping(pg,ug);
    calculateF(pg,tg,ug);
    calculateG(pg,tg,ug);
    calculateR();
    for (i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        ug1[i][j].a=ug[i][j].a - 0.25 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].a - d[i][j].a);
        ug1[i][j].b=ug[i][j].b - 0.25 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].b - d[i][j].b);
        ug1[i][j].c=ug[i][j].c - 0.25 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].c - d[i][j].c);
        ug1[i][j].d=ug[i][j].d - 0.25 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].d - d[i][j].d);
      }
    }
    calculateStateVar(pg1,tg1,ug1);
    calculateDummyCells(pg1,tg1,ug1);
    calculateF(pg1,tg1,ug1);
    calculateG(pg1,tg1,ug1);
    calculateR();
    for (i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        ug1[i][j].a=ug[i][j].a - 0.33333 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].a - d[i][j].a);
        ug1[i][j].b=ug[i][j].b - 0.33333 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].b - d[i][j].b);
        ug1[i][j].c=ug[i][j].c - 0.33333 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].c - d[i][j].c);
        ug1[i][j].d=ug[i][j].d - 0.33333 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].d - d[i][j].d);
      }
    }
    calculateStateVar(pg1,tg1,ug1);
    calculateDummyCells(pg1,tg1,ug1);
    calculateF(pg1,tg1,ug1);
    calculateG(pg1,tg1,ug1);
    calculateR();
    for (i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        ug1[i][j].a=ug[i][j].a - 0.5 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].a - d[i][j].a);
        ug1[i][j].b=ug[i][j].b - 0.5 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].b - d[i][j].b);
        ug1[i][j].c=ug[i][j].c - 0.5 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].c - d[i][j].c);
        ug1[i][j].d=ug[i][j].d - 0.5 * deltat[i][j] / aofTunnel[i][j] * (r[i][j].d - d[i][j].d);
      }
    }
    calculateStateVar(pg1,tg1,ug1);
    calculateDummyCells(pg1,tg1,ug1);
    calculateF(pg1,tg1,ug1);
    calculateG(pg1,tg1,ug1);
    calculateR();
    for (i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        ug[i][j].a-=deltat[i][j] / aofTunnel[i][j] * (r[i][j].a - d[i][j].a);
        ug[i][j].b-=deltat[i][j] / aofTunnel[i][j] * (r[i][j].b - d[i][j].b);
        ug[i][j].c-=deltat[i][j] / aofTunnel[i][j] * (r[i][j].c - d[i][j].c);
        ug[i][j].d-=deltat[i][j] / aofTunnel[i][j] * (r[i][j].d - d[i][j].d);
      }
    }
    error=0.0;
    for (i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        scrap=pg[i][j] - opg[i][j];
        error+=scrap * scrap;
      }
    }
    error=Math.sqrt(error / (double)((imax - 1) * (jmax - 1)));
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
  }
  @Perm(requires="pure(imax) * pure(jmax) * pure(b) * pure(c) * pure(d) * pure(a) in alive",ensures="pure(imax) * pure(jmax) * pure(b) * pure(c) * pure(d) * pure(a) in alive") private void calculateStateVar(  double localpg[][],  double localtg[][],  Statevector localug[][]){
    double temp, temp2;
    int j;
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.readLock().lock();
    for (int i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        temp=localug[i][j].b;
        temp2=localug[i][j].c;
        localtg[i][j]=localug[i][j].d / localug[i][j].a - 0.5 * (temp * temp + temp2 * temp2) / (localug[i][j].a * localug[i][j].a);
        localtg[i][j]=localtg[i][j] / Cv;
        localpg[i][j]=localug[i][j].a * rgas * localtg[i][j];
      }
    }
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.readLock().unlock();
  }
  @Perm(requires="pure(imax) * pure(jmax) * share(a) * pure(r) * share(b) * share(c) * share(d) * pure(ynode) * pure(xnode) * pure(f) * pure(g) in alive",ensures="pure(imax) * pure(jmax) * share(a) * pure(r) * share(b) * share(c) * share(d) * pure(ynode) * pure(xnode) * pure(f) * pure(g) in alive") private void calculateR(){
    double deltax, deltay;
    double temp;
    int j;
    Statevector scrap;
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
    for (int i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        r[i][j].a=0.0;
        r[i][j].b=0.0;
        r[i][j].c=0.0;
        r[i][j].d=0.0;
        deltay=(ynode[i][j] - ynode[i][j - 1]);
        deltax=(xnode[i][j] - xnode[i][j - 1]);
        temp=0.5 * deltay;
        r[i][j].a+=temp * (f[i][j].a + f[i + 1][j].a);
        r[i][j].b+=temp * (f[i][j].b + f[i + 1][j].b);
        r[i][j].c+=temp * (f[i][j].c + f[i + 1][j].c);
        r[i][j].d+=temp * (f[i][j].d + f[i + 1][j].d);
        temp=-0.5 * deltax;
        r[i][j].a+=temp * (g[i][j].a + g[i + 1][j].a);
        r[i][j].b+=temp * (g[i][j].b + g[i + 1][j].b);
        r[i][j].c+=temp * (g[i][j].c + g[i + 1][j].c);
        r[i][j].d+=temp * (g[i][j].d + g[i + 1][j].d);
        deltay=(ynode[i][j - 1] - ynode[i - 1][j - 1]);
        deltax=(xnode[i][j - 1] - xnode[i - 1][j - 1]);
        temp=0.5 * deltay;
        r[i][j].a+=temp * (f[i][j].a + f[i][j - 1].a);
        r[i][j].b+=temp * (f[i][j].b + f[i][j - 1].b);
        r[i][j].c+=temp * (f[i][j].c + f[i][j - 1].c);
        r[i][j].d+=temp * (f[i][j].d + f[i][j - 1].d);
        temp=-0.5 * deltax;
        r[i][j].a+=temp * (g[i][j].a + g[i][j - 1].a);
        r[i][j].b+=temp * (g[i][j].b + g[i][j - 1].b);
        r[i][j].c+=temp * (g[i][j].c + g[i][j - 1].c);
        r[i][j].d+=temp * (g[i][j].d + g[i][j - 1].d);
        deltay=(ynode[i - 1][j - 1] - ynode[i - 1][j]);
        deltax=(xnode[i - 1][j - 1] - xnode[i - 1][j]);
        temp=0.5 * deltay;
        r[i][j].a+=temp * (f[i][j].a + f[i - 1][j].a);
        r[i][j].b+=temp * (f[i][j].b + f[i - 1][j].b);
        r[i][j].c+=temp * (f[i][j].c + f[i - 1][j].c);
        r[i][j].d+=temp * (f[i][j].d + f[i - 1][j].d);
        temp=-0.5 * deltax;
        r[i][j].a+=temp * (g[i][j].a + g[i - 1][j].a);
        r[i][j].b+=temp * (g[i][j].b + g[i - 1][j].b);
        r[i][j].c+=temp * (g[i][j].c + g[i - 1][j].c);
        r[i][j].d+=temp * (g[i][j].d + g[i - 1][j].d);
        deltay=(ynode[i - 1][j] - ynode[i][j]);
        deltax=(xnode[i - 1][j] - xnode[i][j]);
        temp=0.5 * deltay;
        r[i][j].a+=temp * (f[i][j].a + f[i + 1][j].a);
        r[i][j].b+=temp * (f[i][j].b + f[i + 1][j].b);
        r[i][j].c+=temp * (f[i][j].c + f[i + 1][j].c);
        r[i][j].d+=temp * (f[i][j].d + f[i + 1][j].d);
        temp=-0.5 * deltax;
        r[i][j].a+=temp * (g[i][j].a + g[i][j + 1].a);
        r[i][j].b+=temp * (g[i][j].b + g[i][j + 1].b);
        r[i][j].c+=temp * (g[i][j].c + g[i][j + 1].c);
        r[i][j].d+=temp * (g[i][j].d + g[i][j + 1].d);
      }
    }
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
  }
  @Perm(requires="pure(imax) * pure(jmax) * share(c) * share(a) * pure(g) * share(b) * share(d) in alive",ensures="pure(imax) * pure(jmax) * share(c) * share(a) * pure(g) * share(b) * share(d) in alive") private void calculateG(  double localpg[][],  double localtg[][],  Statevector localug[][]){
    double temp, temp2, temp3;
    double v;
    int j;
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
    for (int i=0; i < imax + 1; i++) {
      for (j=0; j < jmax + 1; ++j) {
        v=localug[i][j].c / localug[i][j].a;
        g[i][j].a=localug[i][j].c;
        g[i][j].b=localug[i][j].b * v;
        g[i][j].c=localug[i][j].c * v + localpg[i][j];
        temp=localug[i][j].b * localug[i][j].b;
        temp2=localug[i][j].c * localug[i][j].c;
        temp3=localug[i][j].a * localug[i][j].a;
        g[i][j].d=localug[i][j].c * (Cp * localtg[i][j] + (0.5 * (temp + temp2) / (temp3)));
      }
    }
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
  }
  @Perm(requires="pure(imax) * pure(jmax) * share(b) * share(a) * pure(f) * share(c) * share(d) in alive",ensures="pure(imax) * pure(jmax) * share(b) * share(a) * pure(f) * share(c) * share(d) in alive") private void calculateF(  double localpg[][],  double localtg[][],  Statevector localug[][]){
{
      double u;
      double temp1, temp2, temp3;
      int j;
      a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
      for (int i=0; i < imax + 1; i++) {
        for (j=0; j < jmax + 1; ++j) {
          u=localug[i][j].b / localug[i][j].a;
          f[i][j].a=localug[i][j].b;
          f[i][j].b=localug[i][j].b * u + localpg[i][j];
          f[i][j].c=localug[i][j].c * u;
          temp1=localug[i][j].b * localug[i][j].b;
          temp2=localug[i][j].c * localug[i][j].c;
          temp3=localug[i][j].a * localug[i][j].a;
          f[i][j].d=localug[i][j].b * (Cp * localtg[i][j] + (0.5 * (temp1 + temp2) / (temp3)));
        }
      }
      a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
    }
  }
  @Perm(requires="none(secondOrderDamping) * none(fourthOrderDamping) * pure(imax) * pure(jmax) * share(sxi) * share(seta) * pure(aofTunnel) * pure(deltat) * share(a) * share(b) * share(c) * share(d) * pure(d) in alive",ensures="none(secondOrderDamping) * none(fourthOrderDamping) * pure(imax) * pure(jmax) * share(sxi) * share(seta) * pure(aofTunnel) * pure(deltat) * share(a) * share(b) * share(c) * share(d) * pure(d) in alive") private void calculateDamping(  double localpg[][],  Statevector localug[][]){
    double adt, sbar;
    double nu2;
    double nu4;
    double tempdouble;
    int ascrap, j;
    Statevector temp=new Statevector();
    Statevector temp2=new Statevector();
    Statevector scrap2=new Statevector(), scrap4=new Statevector();
    nu2=secondOrderDamping * secondOrderNormalizer;
    nu4=fourthOrderDamping * fourthOrderNormalizer;
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
    for (int i=1; i < imax; i++)     for (j=1; j < jmax; ++j) {
      sxi[i][j]=Math.abs(localpg[i + 1][j] - 2.0 * localpg[i][j] + localpg[i - 1][j]) / localpg[i][j];
      seta[i][j]=Math.abs(localpg[i][j + 1] - 2.0 * localpg[i][j] + localpg[i][j - 1]) / localpg[i][j];
    }
    for (int i=1; i < imax; i++) {
      for (j=1; j < jmax; ++j) {
        if (i > 1 && i < imax - 1) {
          adt=(aofTunnel[i][j] + aofTunnel[i + 1][j]) / (deltat[i][j] + deltat[i + 1][j]);
          sbar=(sxi[i + 1][j] + sxi[i][j]) * 0.5;
        }
 else {
          adt=aofTunnel[i][j] / deltat[i][j];
          sbar=sxi[i][j];
        }
        tempdouble=nu2 * sbar * adt;
        scrap2.a=tempdouble * (localug[i + 1][j].a - localug[i][j].a);
        scrap2.b=tempdouble * (localug[i + 1][j].b - localug[i][j].b);
        scrap2.c=tempdouble * (localug[i + 1][j].c - localug[i][j].c);
        scrap2.d=tempdouble * (localug[i + 1][j].d - localug[i][j].d);
        if (i > 1 && i < imax - 1) {
          temp=localug[i + 2][j].svect(localug[i - 1][j]);
          temp2.a=3.0 * (localug[i][j].a - localug[i + 1][j].a);
          temp2.b=3.0 * (localug[i][j].b - localug[i + 1][j].b);
          temp2.c=3.0 * (localug[i][j].c - localug[i + 1][j].c);
          temp2.d=3.0 * (localug[i][j].d - localug[i + 1][j].d);
          tempdouble=-nu4 * adt;
          scrap4.a=tempdouble * (temp.a + temp2.a);
          scrap4.b=tempdouble * (temp.a + temp2.b);
          scrap4.c=tempdouble * (temp.a + temp2.c);
          scrap4.d=tempdouble * (temp.a + temp2.d);
        }
 else {
          scrap4.a=0.0;
          scrap4.b=0.0;
          scrap4.c=0.0;
          scrap4.d=0.0;
        }
        temp.a=scrap2.a + scrap4.a;
        temp.b=scrap2.b + scrap4.b;
        temp.c=scrap2.c + scrap4.c;
        temp.d=scrap2.d + scrap4.d;
        d[i][j]=temp;
        if (i > 1 && i < imax - 1) {
          adt=(aofTunnel[i][j] + aofTunnel[i - 1][j]) / (deltat[i][j] + deltat[i - 1][j]);
          sbar=(sxi[i][j] + sxi[i - 1][j]) * 0.5;
        }
 else {
          adt=aofTunnel[i][j] / deltat[i][j];
          sbar=sxi[i][j];
        }
        tempdouble=-nu2 * sbar * adt;
        scrap2.a=tempdouble * (localug[i][j].a - localug[i - 1][j].a);
        scrap2.b=tempdouble * (localug[i][j].b - localug[i - 1][j].b);
        scrap2.c=tempdouble * (localug[i][j].c - localug[i - 1][j].c);
        scrap2.d=tempdouble * (localug[i][j].d - localug[i - 1][j].d);
        if (i > 1 && i < imax - 1) {
          temp=localug[i + 1][j].svect(localug[i - 2][j]);
          temp2.a=3.0 * (localug[i - 1][j].a - localug[i][j].a);
          temp2.b=3.0 * (localug[i - 1][j].b - localug[i][j].b);
          temp2.c=3.0 * (localug[i - 1][j].c - localug[i][j].c);
          temp2.d=3.0 * (localug[i - 1][j].d - localug[i][j].d);
          tempdouble=nu4 * adt;
          scrap4.a=tempdouble * (temp.a + temp2.a);
          scrap4.b=tempdouble * (temp.a + temp2.b);
          scrap4.c=tempdouble * (temp.a + temp2.c);
          scrap4.d=tempdouble * (temp.a + temp2.d);
        }
 else {
          scrap4.a=0.0;
          scrap4.b=0.0;
          scrap4.c=0.0;
          scrap4.d=0.0;
        }
        d[i][j].a+=scrap2.a + scrap4.a;
        d[i][j].b+=scrap2.b + scrap4.b;
        d[i][j].c+=scrap2.c + scrap4.c;
        d[i][j].d+=scrap2.d + scrap4.d;
        if (j > 1 && j < jmax - 1) {
          adt=(aofTunnel[i][j] + aofTunnel[i][j + 1]) / (deltat[i][j] + deltat[i][j + 1]);
          sbar=(seta[i][j] + seta[i][j + 1]) * 0.5;
        }
 else {
          adt=aofTunnel[i][j] / deltat[i][j];
          sbar=seta[i][j];
        }
        tempdouble=nu2 * sbar * adt;
        scrap2.a=tempdouble * (localug[i][j + 1].a - localug[i][j].a);
        scrap2.b=tempdouble * (localug[i][j + 1].b - localug[i][j].b);
        scrap2.c=tempdouble * (localug[i][j + 1].c - localug[i][j].c);
        scrap2.d=tempdouble * (localug[i][j + 1].d - localug[i][j].d);
        if (j > 1 && j < jmax - 1) {
          temp=localug[i][j + 2].svect(localug[i][j - 1]);
          temp2.a=3.0 * (localug[i][j].a - localug[i][j + 1].a);
          temp2.b=3.0 * (localug[i][j].b - localug[i][j + 1].b);
          temp2.c=3.0 * (localug[i][j].c - localug[i][j + 1].c);
          temp2.d=3.0 * (localug[i][j].d - localug[i][j + 1].d);
          tempdouble=-nu4 * adt;
          scrap4.a=tempdouble * (temp.a + temp2.a);
          scrap4.b=tempdouble * (temp.a + temp2.b);
          scrap4.c=tempdouble * (temp.a + temp2.c);
          scrap4.d=tempdouble * (temp.a + temp2.d);
        }
 else {
          scrap4.a=0.0;
          scrap4.b=0.0;
          scrap4.c=0.0;
          scrap4.d=0.0;
        }
        d[i][j].a+=scrap2.a + scrap4.a;
        d[i][j].b+=scrap2.b + scrap4.b;
        d[i][j].c+=scrap2.c + scrap4.c;
        d[i][j].d+=scrap2.d + scrap4.d;
        if (j > 1 && j < jmax - 1) {
          adt=(aofTunnel[i][j] + aofTunnel[i][j - 1]) / (deltat[i][j] + deltat[i][j - 1]);
          sbar=(seta[i][j] + seta[i][j - 1]) * 0.5;
        }
 else {
          adt=aofTunnel[i][j] / deltat[i][j];
          sbar=seta[i][j];
        }
        tempdouble=-nu2 * sbar * adt;
        scrap2.a=tempdouble * (localug[i][j].a - localug[i][j - 1].a);
        scrap2.b=tempdouble * (localug[i][j].b - localug[i][j - 1].b);
        scrap2.c=tempdouble * (localug[i][j].c - localug[i][j - 1].c);
        scrap2.d=tempdouble * (localug[i][j].d - localug[i][j - 1].d);
        if (j > 1 && j < jmax - 1) {
          temp=localug[i][j + 1].svect(localug[i][j - 2]);
          temp2.a=3.0 * (localug[i][j - 1].a - localug[i][j].a);
          temp2.b=3.0 * (localug[i][j - 1].b - localug[i][j].b);
          temp2.c=3.0 * (localug[i][j - 1].c - localug[i][j].c);
          temp2.d=3.0 * (localug[i][j - 1].d - localug[i][j].d);
          tempdouble=nu4 * adt;
          scrap4.a=tempdouble * (temp.a + temp2.a);
          scrap4.b=tempdouble * (temp.a + temp2.b);
          scrap4.c=tempdouble * (temp.a + temp2.c);
          scrap4.d=tempdouble * (temp.a + temp2.d);
        }
 else {
          scrap4.a=0.0;
          scrap4.b=0.0;
          scrap4.c=0.0;
          scrap4.d=0.0;
        }
        d[i][j].a+=scrap2.a + scrap4.a;
        d[i][j].b+=scrap2.b + scrap4.b;
        d[i][j].c+=scrap2.c + scrap4.c;
        d[i][j].d+=scrap2.d + scrap4.d;
      }
    }
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
  }
  @Perm(requires="pure(imax) * pure(jmax) * pure(xnode) * pure(ynode) * pure(b) * pure(ug) * pure(c) * pure(tg) * share(deltat) * pure(aofTunnel) * pure(a) * none(ntime) in alive",ensures="pure(imax) * pure(jmax) * pure(xnode) * pure(ynode) * pure(b) * pure(ug) * pure(c) * pure(tg) * share(deltat) * pure(aofTunnel) * pure(a) * none(ntime) in alive") private void calculateDeltaT(){
    double xeta, yeta, xxi, yxi;
    int j;
    double mint;
    double c, q, r;
    double safety_factor=0.7;
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.readLock().lock();
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
    for (int i=1; i < imax; i++)     for (j=1; j < jmax; ++j) {
      xxi=(xnode[i][j] - xnode[i - 1][j] + xnode[i][j - 1] - xnode[i - 1][j - 1]) * 0.5;
      yxi=(ynode[i][j] - ynode[i - 1][j] + ynode[i][j - 1] - ynode[i - 1][j - 1]) * 0.5;
      xeta=(xnode[i][j] - xnode[i][j - 1] + xnode[i - 1][j] - xnode[i - 1][j - 1]) * 0.5;
      yeta=(ynode[i][j] - ynode[i][j - 1] + ynode[i - 1][j] - ynode[i - 1][j - 1]) * 0.5;
      q=(yeta * ug[i][j].b - xeta * ug[i][j].c);
      r=(-yxi * ug[i][j].b + xxi * ug[i][j].c);
      c=Math.sqrt(gamma * rgas * tg[i][j]);
      deltat[i][j]=safety_factor * 2.8284 * aofTunnel[i][j] / ((Math.abs(q) + Math.abs(r)) / ug[i][j].a + c * Math.sqrt(xxi * xxi + yxi * yxi + xeta * xeta + yeta * yeta + 2.0 * Math.abs(xeta * xxi + yeta * yxi)));
    }
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.readLock().unlock();
{
      if (ntime == 1) {
        mint=100000.0;
        for (int i=1; i < imax; i++)         for (j=1; j < jmax; ++j)         if (deltat[i][j] < mint)         mint=deltat[i][j];
        for (int i=1; i < imax; i++)         for (j=1; j < jmax; ++j)         deltat[i][j]=mint;
      }
    }
  }
  @Perm(requires="unique(uff) * none(machff) * unique(jplusff) * pure(cff) * unique(jminusff) * pure(imax) * full(ihat) * share(xnode) * full(jhat) * share(ynode) * share(a) * share(b) * share(c) * share(d) * share(jmax) * pure(rhoff) * pure(vff) * pure(tff) * pure(pff) in alive",ensures="unique(uff) * none(machff) * unique(jplusff) * pure(cff) * unique(jminusff) * pure(imax) * full(ihat) * share(xnode) * full(jhat) * share(ynode) * share(a) * share(b) * share(c) * share(d) * share(jmax) * pure(rhoff) * pure(vff) * pure(tff) * pure(pff) in alive") private void calculateDummyCells(  double localpg[][],  double localtg[][],  Statevector localug[][]){
    double c;
    double jminus;
    double jplus;
    double s;
    double rho, temp, u, v;
    double scrap, scrap2;
    double theta;
    double uprime;
    int i, j;
    Vector2 norm=new Vector2();
    Vector2 tan=new Vector2();
    Vector2 u1=new Vector2();
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
    uff=machff;
    jplusff=uff + 2.0 / (gamma - 1.0) * cff;
    jminusff=uff - 2.0 / (gamma - 1.0) * cff;
    for (i=1; i < imax; i++) {
      tan.ihat=xnode[i][0] - xnode[i - 1][0];
      tan.jhat=ynode[i][0] - ynode[i - 1][0];
      norm.ihat=-(ynode[i][0] - ynode[i - 1][0]);
      norm.jhat=xnode[i][0] - xnode[i - 1][0];
      scrap=tan.magnitude();
      tan.ihat=tan.ihat / scrap;
      tan.jhat=tan.jhat / scrap;
      scrap=norm.magnitude();
      norm.ihat=norm.ihat / scrap;
      norm.jhat=norm.jhat / scrap;
      rho=localug[i][1].a;
      localtg[i][0]=localtg[i][1];
      u1.ihat=localug[i][1].b / rho;
      u1.jhat=localug[i][1].c / rho;
      u=u1.dot(tan) + u1.dot(norm) * tan.jhat / norm.jhat;
      u=u / (tan.ihat - (norm.ihat * tan.jhat / norm.jhat));
      v=-(u1.dot(norm) + u * norm.ihat) / norm.jhat;
      localug[i][0]=localug[i][0];
      localug[i][0].a=localug[i][1].a;
      localug[i][0].b=rho * u;
      localug[i][0].c=rho * v;
      localug[i][0].d=rho * (Cv * localtg[i][0] + 0.5 * (u * u + v * v));
      localpg[i][0]=localpg[i][1];
      tan.ihat=xnode[i][jmax - 1] - xnode[i - 1][jmax - 1];
      tan.jhat=ynode[i][jmax - 1] - ynode[i - 1][jmax - 1];
      norm.ihat=ynode[i][jmax - 1] - ynode[i - 1][jmax - 1];
      norm.jhat=-(xnode[i][jmax - 1] - xnode[i - 1][jmax - 1]);
      scrap=tan.magnitude();
      tan.ihat=tan.ihat / scrap;
      tan.jhat=tan.jhat / scrap;
      scrap=norm.magnitude();
      norm.ihat=norm.ihat / scrap;
      norm.jhat=norm.jhat / scrap;
      rho=localug[i][jmax - 1].a;
      temp=localtg[i][jmax - 1];
      u1.ihat=localug[i][jmax - 1].b / rho;
      u1.jhat=localug[i][jmax - 1].c / rho;
      u=u1.dot(tan) + u1.dot(norm) * tan.jhat / norm.jhat;
      u=u / (tan.ihat - (norm.ihat * tan.jhat / norm.jhat));
      v=-(u1.dot(norm) + u * norm.ihat) / norm.jhat;
      localug[i][jmax].a=localug[i][jmax - 1].a;
      localug[i][jmax].b=rho * u;
      localug[i][jmax].c=rho * v;
      localug[i][jmax].d=rho * (Cv * temp + 0.5 * (u * u + v * v));
      localtg[i][jmax]=temp;
      localpg[i][jmax]=localpg[i][jmax - 1];
    }
    temp=0;
    for (j=1; j < jmax; ++j) {
      norm.ihat=ynode[0][j - 1] - ynode[0][j];
      norm.jhat=xnode[0][j] - xnode[0][j - 1];
      scrap=norm.magnitude();
      norm.ihat=norm.ihat / scrap;
      norm.jhat=norm.jhat / scrap;
      theta=Math.acos((ynode[0][j - 1] - ynode[0][j]) / Math.sqrt((xnode[0][j] - xnode[0][j - 1]) * (xnode[0][j] - xnode[0][j - 1]) + (ynode[0][j - 1] - ynode[0][j]) * (ynode[0][j - 1] - ynode[0][j])));
      u1.ihat=localug[1][j].b / localug[1][j].a;
      u1.jhat=localug[1][j].c / localug[1][j].a;
      uprime=u1.ihat * Math.cos(theta);
      c=Math.sqrt(gamma * rgas * localtg[1][j]);
      if (uprime < -c) {
        localug[0][j].a=rhoff;
        localug[0][j].b=rhoff * uff;
        localug[0][j].c=rhoff * vff;
        localug[0][j].d=rhoff * (Cv * tff + 0.5 * (uff * uff + vff * vff));
        localtg[0][j]=tff;
        localpg[0][j]=pff;
      }
 else       if (uprime < 0.0) {
        jminus=u1.ihat - 2.0 / (gamma - 1.0) * c;
        s=Math.log(pff) - gamma * Math.log(rhoff);
        v=vff;
        u=(jplusff + jminus) / 2.0;
        scrap=(jplusff - u) * (gamma - 1.0) * 0.5;
        localtg[0][j]=(1.0 / (gamma * rgas)) * scrap * scrap;
        localpg[0][j]=Math.exp(s) / Math.pow((rgas * localtg[0][j]),gamma);
        localpg[0][j]=Math.pow(localpg[0][j],1.0 / (1.0 - gamma));
        localug[0][j].a=localpg[0][j] / (rgas * localtg[0][j]);
        localug[0][j].b=localug[0][j].a * u;
        localug[0][j].c=localug[0][j].a * v;
        localug[0][j].d=localug[0][j].a * (Cv * tff + 0.5 * (u * u + v * v));
      }
 else {
        System.err.println("You have outflow at the inlet, which is not allowed.");
      }
      norm.ihat=ynode[0][j] - ynode[0][j - 1];
      norm.jhat=xnode[0][j - 1] - xnode[0][j];
      scrap=norm.magnitude();
      norm.ihat=norm.ihat / scrap;
      norm.jhat=norm.jhat / scrap;
      scrap=xnode[0][j - 1] - xnode[0][j];
      scrap2=ynode[0][j] - ynode[0][j - 1];
      theta=Math.acos((ynode[0][j] - ynode[0][j - 1]) / Math.sqrt(scrap * scrap + scrap2 * scrap2));
      u1.ihat=localug[imax - 1][j].b / localug[imax - 1][j].a;
      u1.jhat=localug[imax - 1][j].c / localug[imax - 1][j].a;
      uprime=u1.ihat * Math.cos(theta);
      c=Math.sqrt(gamma * rgas * localtg[imax - 1][j]);
      if (uprime > c) {
        localug[imax][j].a=2.0 * localug[imax - 1][j].a - localug[imax - 2][j].a;
        localug[imax][j].b=2.0 * localug[imax - 1][j].b - localug[imax - 2][j].b;
        localug[imax][j].c=2.0 * localug[imax - 1][j].c - localug[imax - 2][j].c;
        localug[imax][j].d=2.0 * localug[imax - 1][j].d - localug[imax - 2][j].d;
        localpg[imax][j]=2.0 * localpg[imax - 1][j] - localpg[imax - 2][j];
        localtg[imax][j]=2.0 * localtg[imax - 1][j] - localtg[imax - 2][j];
      }
 else       if (uprime < c && uprime > 0) {
        jplus=u1.ihat + 2.0 / (gamma - 1) * c;
        v=localug[imax - 1][j].c / localug[imax - 1][j].a;
        s=Math.log(localpg[imax - 1][j]) - gamma * Math.log(localug[imax - 1][j].a);
        u=(jplus + jminusff) / 2.0;
        scrap=(jplus - u) * (gamma - 1.0) * 0.5;
        localtg[imax][j]=(1.0 / (gamma * rgas)) * scrap * scrap;
        localpg[imax][j]=Math.exp(s) / Math.pow((rgas * localtg[imax][j]),gamma);
        localpg[imax][j]=Math.pow(localpg[imax][j],1.0 / (1.0 - gamma));
        rho=localpg[imax][j] / (rgas * localtg[imax][j]);
        localug[imax][j].a=rho;
        localug[imax][j].b=rho * u;
        localug[imax][j].c=rho * v;
        localug[imax][j].d=rho * (Cv * localtg[imax][j] + 0.5 * (u * u + v * v));
      }
 else       if (uprime < -c) {
        localug[0][j].a=rhoff;
        localug[0][j].b=rhoff * uff;
        localug[0][j].c=rhoff * vff;
        localug[0][j].d=rhoff * (Cv * tff + 0.5 * (uff * uff + vff * vff));
        localtg[0][j]=tff;
        localpg[0][j]=pff;
      }
 else       if (uprime < 0.0) {
        jminus=u1.ihat - 2.0 / (gamma - 1.0) * c;
        s=Math.log(pff) - gamma * Math.log(rhoff);
        v=vff;
        u=(jplusff + jminus) / 2.0;
        scrap=(jplusff - u) * (gamma - 1.0) * 0.5;
        localtg[0][j]=(1.0 / (gamma * rgas)) * scrap * scrap;
        localpg[0][j]=Math.exp(s) / Math.pow((rgas * localtg[0][j]),gamma);
        localpg[0][j]=Math.pow(localpg[0][j],1.0 / (1.0 - gamma));
        localug[0][j].a=localpg[0][j] / (rgas * localtg[0][j]);
        localug[0][j].b=localug[0][j].a * u;
        localug[0][j].c=localug[0][j].a * v;
        localug[0][j].d=localug[0][j].a * (Cv * tff + 0.5 * (u * u + v * v));
      }
 else {
        System.err.println("You have inflow at the outlet, which is not allowed.");
      }
    }
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
{
      localug[0][0]=localug[1][0];
      localug[imax][0]=localug[imax][1];
      localug[0][jmax]=localug[1][jmax];
      localug[imax][jmax]=localug[imax][jmax - 1];
    }
  }
  @Perm(requires="none(iter) in alive",ensures="none(iter) in alive") public void runiters(){
    for (int i=0; i < iter; i++) {
      a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().lock();
      doIteration();
      a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock.writeLock().unlock();
    }
  }
  public ReentrantReadWriteLock getNtimeLock(){
    Cloner cloner=new Cloner();
    ntimeLock=cloner.deepClone(ntimeLock);
    return ntimeLock;
  }
  public ReentrantReadWriteLock getIterLock(){
    Cloner cloner=new Cloner();
    iterLock=cloner.deepClone(iterLock);
    return iterLock;
  }
  public ReentrantReadWriteLock getMachffLock(){
    Cloner cloner=new Cloner();
    machffLock=cloner.deepClone(machffLock);
    return machffLock;
  }
  public Statevector getF(){
    Cloner cloner=new Cloner();
    f=cloner.deepClone(f);
    return f;
  }
  public ReentrantReadWriteLock getA_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock(){
    Cloner cloner=new Cloner();
    a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock=cloner.deepClone(a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock);
    return a_aofTunnel_b_c_cff_d_deltat_error_f_g_ihat_imax_imaxin_jhat_jmax_jmaxin_jminusff_jplusff_newval_oldval_opg_pff_pg_pg1_r_rhoff_scale_seta_size_sxi_tff_tg_tg1_uff_ug_ug1_vff_xnode_ynodeLock;
  }
  public Statevector getR(){
    Cloner cloner=new Cloner();
    r=cloner.deepClone(r);
    return r;
  }
  public ReentrantReadWriteLock getFourthOrderDampingLock(){
    Cloner cloner=new Cloner();
    fourthOrderDampingLock=cloner.deepClone(fourthOrderDampingLock);
    return fourthOrderDampingLock;
  }
  public ReentrantReadWriteLock getDatasizesLock(){
    Cloner cloner=new Cloner();
    datasizesLock=cloner.deepClone(datasizesLock);
    return datasizesLock;
  }
  public Statevector getD(){
    Cloner cloner=new Cloner();
    d=cloner.deepClone(d);
    return d;
  }
  public Statevector getUg(){
    Cloner cloner=new Cloner();
    ug=cloner.deepClone(ug);
    return ug;
  }
  public ReentrantReadWriteLock getNfLock(){
    Cloner cloner=new Cloner();
    nfLock=cloner.deepClone(nfLock);
    return nfLock;
  }
  public ReentrantReadWriteLock getSecondOrderDampingLock(){
    Cloner cloner=new Cloner();
    secondOrderDampingLock=cloner.deepClone(secondOrderDampingLock);
    return secondOrderDampingLock;
  }
}
class Statevector {
  public ReentrantReadWriteLock a_b_c_dLock=new ReentrantReadWriteLock();
  double a;
  double b;
  double c;
  double d;
  @Perm(requires="unique(a) * unique(b) * unique(c) * unique(d) in alive",ensures="unique(a) * unique(b) * unique(c) * unique(d) in alive") Statevector(){
    a_b_c_dLock.writeLock().lock();
    a=0.0;
    b=0.0;
    c=0.0;
    d=0.0;
    a_b_c_dLock.writeLock().unlock();
  }
  @Perm(requires="share(a) * share(b) * share(c) * share(d) in alive",ensures="share(a) * share(b) * share(c) * share(d) in alive") public Statevector amvect(  double m,  Statevector that){
    Statevector answer=new Statevector();
    a_b_c_dLock.writeLock().lock();
    answer.a=m * (this.a + that.a);
    answer.b=m * (this.b + that.b);
    answer.c=m * (this.c + that.c);
    answer.d=m * (this.d + that.d);
    a_b_c_dLock.writeLock().unlock();
    return answer;
  }
  @Perm(requires="share(a) * share(b) * share(c) * share(d) in alive",ensures="share(a) * share(b) * share(c) * share(d) in alive") public Statevector avect(  Statevector that){
    Statevector answer=new Statevector();
    a_b_c_dLock.writeLock().lock();
    answer.a=this.a + that.a;
    answer.b=this.b + that.b;
    answer.c=this.c + that.c;
    answer.d=this.d + that.d;
    a_b_c_dLock.writeLock().unlock();
    return answer;
  }
  @Perm(requires="share(a) * share(b) * share(c) * share(d) in alive",ensures="share(a) * share(b) * share(c) * share(d) in alive") public Statevector mvect(  double m){
    Statevector answer=new Statevector();
    a_b_c_dLock.writeLock().lock();
    answer.a=m * this.a;
    answer.b=m * this.b;
    answer.c=m * this.c;
    answer.d=m * this.d;
    a_b_c_dLock.writeLock().unlock();
    return answer;
  }
  @Perm(requires="share(a) * share(b) * share(c) * share(d) in alive",ensures="share(a) * share(b) * share(c) * share(d) in alive") public Statevector svect(  Statevector that){
    Statevector answer=new Statevector();
    a_b_c_dLock.writeLock().lock();
    answer.a=this.a - that.a;
    answer.b=this.b - that.b;
    answer.c=this.c - that.c;
    answer.d=this.d - that.d;
    a_b_c_dLock.writeLock().unlock();
    return answer;
  }
  @Perm(requires="share(a) * share(b) * share(c) * share(d) in alive",ensures="share(a) * share(b) * share(c) * share(d) in alive") public Statevector smvect(  double m,  Statevector that){
    Statevector answer=new Statevector();
    a_b_c_dLock.writeLock().lock();
    answer.a=m * (this.a - that.a);
    answer.b=m * (this.b - that.b);
    answer.c=m * (this.c - that.c);
    answer.d=m * (this.d - that.d);
    a_b_c_dLock.writeLock().unlock();
    return answer;
  }
  public ReentrantReadWriteLock getA_b_c_dLock(){
    Cloner cloner=new Cloner();
    a_b_c_dLock=cloner.deepClone(a_b_c_dLock);
    return a_b_c_dLock;
  }
}
class Vector2 {
  public ReentrantReadWriteLock ihat_jhatLock=new ReentrantReadWriteLock();
  double ihat;
  double jhat;
  @Perm(requires="unique(ihat) * unique(jhat) in alive",ensures="unique(ihat) * unique(jhat) in alive") Vector2(){
    ihat_jhatLock.writeLock().lock();
    ihat=0.0;
    jhat=0.0;
    ihat_jhatLock.writeLock().unlock();
  }
  @Perm(requires="pure(ihat) * pure(jhat) in alive",ensures="pure(ihat) * pure(jhat) in alive") public double magnitude(){
    double mag;
    ihat_jhatLock.readLock().lock();
    mag=Math.sqrt(this.ihat * this.ihat + this.jhat * this.jhat);
    ihat_jhatLock.readLock().unlock();
    return mag;
  }
  @Perm(requires="pure(ihat) * pure(jhat) in alive",ensures="pure(ihat) * pure(jhat) in alive") public double dot(  Vector2 that){
    double answer;
    ihat_jhatLock.readLock().lock();
    answer=this.ihat * that.ihat + this.jhat * that.jhat;
    ihat_jhatLock.readLock().unlock();
    return answer;
  }
  public ReentrantReadWriteLock getIhat_jhatLock(){
    Cloner cloner=new Cloner();
    ihat_jhatLock=cloner.deepClone(ihat_jhatLock);
    return ihat_jhatLock;
  }
}
