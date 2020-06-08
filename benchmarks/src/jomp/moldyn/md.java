package jomp.moldyn.withlock;
import java.text.NumberFormat;
import jomp.moldyn.md;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class md {
  public static ReentrantReadWriteLock PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock=new ReentrantReadWriteLock();
  public static final int ITERS=100;
  public static final double LENGTH=50e-10;
  public static final double m=4.0026;
  public static final double mu=1.66056e-27;
  public static final double kb=1.38066e-23;
  public static final double TSIM=50;
  public static final double deltat=5e-16;
  public static particle one[]=null;
  public static double epot=0.0;
  public static double vir=0.0;
  public static double count=0.0;
  int size;
  int datasizes[]={8,13};
  public static int interactions=0;
  int i, j, k, lg, mdsize, move, mm;
  double l, rcoff, rcoffs, side, sideh, hsq, hsq2, vel;
  double a, r, sum, tscale, sc, ekin, ek, ts, sp;
  double den=0.83134;
  double tref=0.722;
  double h=0.064;
  double vaver, vaverh, rand;
  double etot, temp, pres, rp;
  double u1, u2, v1, v2, s;
  int ijk, npartm, PARTSIZE, iseed, tint;
  int irep=10;
  int istop=19;
  int iprint=10;
  int movemx=50;
  random randnum;
  NumberFormat nbf;
  NumberFormat nbf2;
  NumberFormat nbf3;
  @Perm(requires="share(nbf) * share(nbf2) * share(nbf3) * share(mm) * immutable(datasizes) * pure(size) * share(PARTSIZE) * share(mdsize) * unique(one) * share(l) * share(side) * immutable(den) * share(rcoff) * share(a) * share(sideh) * share(hsq) * immutable(h) * share(hsq2) * share(npartm) * share(rcoffs) * share(tscale) * share(vaver) * immutable(tref) * share(vaverh) * unique(ijk) * share(lg) * share(i) * share(j) * share(k) * share(iseed) * share(v1) * share(v2) * unique(randnum) * pure(v1) * share(r) * share(xvelocity) * pure(v2) * share(yvelocity) * share(zvelocity) * share(ekin) * share(sp) * share(ts) * share(sc) in alive",ensures="share(nbf) * share(nbf2) * share(nbf3) * share(mm) * immutable(datasizes) * pure(size) * share(PARTSIZE) * share(mdsize) * unique(one) * share(l) * share(side) * immutable(den) * share(rcoff) * share(a) * share(sideh) * share(hsq) * immutable(h) * share(hsq2) * share(npartm) * share(rcoffs) * share(tscale) * share(vaver) * immutable(tref) * share(vaverh) * unique(ijk) * share(lg) * share(i) * share(j) * share(k) * share(iseed) * share(v1) * share(v2) * unique(randnum) * pure(v1) * share(r) * share(xvelocity) * pure(v2) * share(yvelocity) * share(zvelocity) * share(ekin) * share(sp) * share(ts) * share(sc) in alive") public void initialise(){
    PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock.writeLock().lock();
    nbf=NumberFormat.getInstance();
    nbf.setMaximumFractionDigits(4);
    nbf.setMinimumFractionDigits(4);
    nbf.setGroupingUsed(false);
    nbf2=NumberFormat.getInstance();
    nbf2.setMaximumFractionDigits(1);
    nbf2.setMinimumFractionDigits(1);
    nbf2.setMaximumFractionDigits(4);
    nbf2.setMinimumFractionDigits(1);
    nbf3=NumberFormat.getInstance();
    nbf3.setMaximumFractionDigits(6);
    nbf3.setMinimumFractionDigits(6);
    mm=datasizes[size];
    PARTSIZE=mm * mm * mm* 4;
    mdsize=PARTSIZE;
    one=new particle[mdsize];
    l=LENGTH;
    side=Math.pow((mdsize / den),0.3333333);
    rcoff=mm / 4.0;
    a=side / mm;
    sideh=side * 0.5;
    hsq=h * h;
    hsq2=hsq * 0.5;
    npartm=mdsize - 1;
    rcoffs=rcoff * rcoff;
    tscale=16.0 / (1.0 * mdsize - 1.0);
    vaver=1.13 * Math.sqrt(tref / 24.0);
    vaverh=vaver * h;
    ijk=0;
    for (lg=0; lg <= 1; lg++) {
      for (i=0; i < mm; i++) {
        for (j=0; j < mm; j++) {
          for (k=0; k < mm; k++) {
            one[ijk]=new particle((i * a + lg * a * 0.5),(j * a + lg * a * 0.5),(k * a),0.0,0.0,0.0,0.0,0.0,0.0);
            ijk=ijk + 1;
          }
        }
      }
    }
    for (lg=1; lg <= 2; lg++) {
      for (i=0; i < mm; i++) {
        for (j=0; j < mm; j++) {
          for (k=0; k < mm; k++) {
            one[ijk]=new particle((i * a + (2 - lg) * a * 0.5),(j * a + (lg - 1) * a * 0.5),(k * a + a * 0.5),0.0,0.0,0.0,0.0,0.0,0.0);
            ijk=ijk + 1;
          }
        }
      }
    }
    iseed=0;
    v1=0.0;
    v2=0.0;
    randnum=new random(iseed,v1,v2);
    System.out.println("testing Qualified name = " + randnum.v1);
    for (i=0; i < mdsize; i+=2) {
      r=randnum.seed();
      one[i].xvelocity=r * randnum.v1;
      one[i + 1].xvelocity=r * randnum.v2;
    }
    for (i=0; i < mdsize; i+=2) {
      r=randnum.seed();
      one[i].yvelocity=r * randnum.v1;
      one[i + 1].yvelocity=r * randnum.v2;
    }
    for (i=0; i < mdsize; i+=2) {
      r=randnum.seed();
      one[i].zvelocity=r * randnum.v1;
      one[i + 1].zvelocity=r * randnum.v2;
    }
    ekin=0.0;
    sp=0.0;
    for (i=0; i < mdsize; i++) {
      sp=sp + one[i].xvelocity;
    }
    sp=sp / mdsize;
    for (i=0; i < mdsize; i++) {
      one[i].xvelocity=one[i].xvelocity - sp;
      ekin=ekin + one[i].xvelocity * one[i].xvelocity;
    }
    sp=0.0;
    for (i=0; i < mdsize; i++) {
      sp=sp + one[i].yvelocity;
    }
    sp=sp / mdsize;
    for (i=0; i < mdsize; i++) {
      one[i].yvelocity=one[i].yvelocity - sp;
      ekin=ekin + one[i].yvelocity * one[i].yvelocity;
    }
    sp=0.0;
    for (i=0; i < mdsize; i++) {
      sp=sp + one[i].zvelocity;
    }
    sp=sp / mdsize;
    for (i=0; i < mdsize; i++) {
      one[i].zvelocity=one[i].zvelocity - sp;
      ekin=ekin + one[i].zvelocity * one[i].zvelocity;
    }
    ts=tscale * ekin;
    sc=h * Math.sqrt(tref / ts);
    for (i=0; i < mdsize; i++) {
      one[i].xvelocity=one[i].xvelocity * sc;
      one[i].yvelocity=one[i].yvelocity * sc;
      one[i].zvelocity=one[i].zvelocity * sc;
      runiters();
    }
    PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock.writeLock().unlock();
  }
  @Perm(requires="share(move) * immutable(movemx) * share(i) * pure(mdsize) * pure(one) * pure(side) * share(epot) * share(vir) * pure(rcoff) * share(sum) * pure(hsq2) * share(ekin) * pure(hsq) * share(vel) * share(count) * pure(vaverh) * immutable(h) * immutable(istop) * immutable(irep) * share(sc) * immutable(tref) * pure(tscale) * immutable(iprint) * share(ek) * share(etot) * share(temp) * share(pres) * immutable(den) * share(rp) in alive",ensures="share(move) * immutable(movemx) * share(i) * pure(mdsize) * pure(one) * pure(side) * share(epot) * share(vir) * pure(rcoff) * share(sum) * pure(hsq2) * share(ekin) * pure(hsq) * share(vel) * share(count) * pure(vaverh) * immutable(h) * immutable(istop) * immutable(irep) * share(sc) * immutable(tref) * pure(tscale) * immutable(iprint) * share(ek) * share(etot) * share(temp) * share(pres) * immutable(den) * share(rp) in alive") public void runiters(){
    PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock.writeLock().lock();
    move=0;
    for (move=0; move < movemx; move++) {
      for (i=0; i < mdsize; i++) {
        one[i].domove(side);
      }
      epot=0.0;
      vir=0.0;
      for (i=0; i < mdsize; i++) {
        one[i].force(side,rcoff,mdsize,i);
      }
      sum=0.0;
      for (i=0; i < mdsize; i++) {
        sum=sum + one[i].mkekin(hsq2);
      }
      ekin=sum / hsq;
      vel=0.0;
      count=0.0;
      for (i=0; i < mdsize; i++) {
        vel=vel + one[i].velavg(vaverh,h);
      }
      vel=vel / h;
      if ((move < istop) && (((move + 1) % irep) == 0)) {
        sc=Math.sqrt(tref / (tscale * ekin));
        for (i=0; i < mdsize; i++) {
          one[i].dscal(sc,1);
        }
        ekin=tref / tscale;
      }
      if (((move + 1) % iprint) == 0) {
        ek=24.0 * ekin;
        epot=4.0 * epot;
        etot=ek + epot;
        temp=tscale * ekin;
        pres=den * 16.0 * (ekin - vir) / mdsize;
        vel=vel / mdsize;
        rp=(count / mdsize) * 100.0;
      }
    }
    PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock.writeLock().unlock();
  }
  public NumberFormat getNbf(){
    Cloner cloner=new Cloner();
    nbf=cloner.deepClone(nbf);
    return nbf;
  }
  public NumberFormat getNbf2(){
    Cloner cloner=new Cloner();
    nbf2=cloner.deepClone(nbf2);
    return nbf2;
  }
  public NumberFormat getNbf3(){
    Cloner cloner=new Cloner();
    nbf3=cloner.deepClone(nbf3);
    return nbf3;
  }
  public random getRandnum(){
    Cloner cloner=new Cloner();
    randnum=cloner.deepClone(randnum);
    return randnum;
  }
  public static ReentrantReadWriteLock getPARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock(){
    Cloner cloner=new Cloner();
    PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock=cloner.deepClone(PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock);
    return PARTSIZE_a_count_datasizes_den_ek_ekin_epot_etot_h_hsq_hsq2_i_ijk_iprint_irep_iseed_istop_j_k_l_lg_mdsize_mm_move_movemx_nbf_nbf2_nbf3_npartm_one_pres_r_randnum_rcoff_rcoffs_rp_sc_side_sideh_size_sp_sum_temp_tref_ts_tscale_v1_v2_vaver_vaverh_vel_vir_xvelocity_yvelocity_zvelocityLock;
  }
  public static particle getOne(){
    Cloner cloner=new Cloner();
    one=cloner.deepClone(one);
    return one;
  }
}
class particle {
  public ReentrantReadWriteLock count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock=new ReentrantReadWriteLock();
  public double xcoord, ycoord, zcoord;
  public double xvelocity, yvelocity, zvelocity;
  public double xforce, yforce, zforce;
  @Perm(requires="unique(xcoord) * unique(ycoord) * unique(zcoord) * unique(xvelocity) * unique(yvelocity) * unique(zvelocity) * unique(xforce) * unique(yforce) * unique(zforce) in alive",ensures="unique(xcoord) * unique(ycoord) * unique(zcoord) * unique(xvelocity) * unique(yvelocity) * unique(zvelocity) * unique(xforce) * unique(yforce) * unique(zforce) in alive") public particle(  double xco,  double yco,  double zco,  double xvel,  double yvel,  double zvel,  double xfor,  double yfor,  double zfor){
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().lock();
    this.xcoord=xco;
    this.ycoord=yco;
    this.zcoord=zco;
    this.xvelocity=xvel;
    this.yvelocity=yvel;
    this.zvelocity=zvel;
    this.xforce=xfor;
    this.yforce=yfor;
    this.zforce=zfor;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().unlock();
  }
  @Perm(requires="full(xcoord) * share(xvelocity) * share(xforce) * full(ycoord) * share(yvelocity) * share(yforce) * full(zcoord) * share(zvelocity) * share(zforce) in alive",ensures="full(xcoord) * share(xvelocity) * share(xforce) * full(ycoord) * share(yvelocity) * share(yforce) * full(zcoord) * share(zvelocity) * share(zforce) in alive") public void domove(  double side){
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().lock();
    xcoord=xcoord + xvelocity + xforce;
    ycoord=ycoord + yvelocity + yforce;
    zcoord=zcoord + zvelocity + zforce;
    if (xcoord < 0) {
      xcoord=xcoord + side;
    }
    if (xcoord > side) {
      xcoord=xcoord - side;
    }
    if (ycoord < 0) {
      ycoord=ycoord + side;
    }
    if (ycoord > side) {
      ycoord=ycoord - side;
    }
    if (zcoord < 0) {
      zcoord=zcoord + side;
    }
    if (zcoord > side) {
      zcoord=zcoord - side;
    }
    xvelocity=xvelocity + xforce;
    yvelocity=yvelocity + yforce;
    zvelocity=zvelocity + zforce;
    xforce=0.0;
    yforce=0.0;
    zforce=0.0;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().unlock();
  }
  @Perm(requires="pure(xcoord) * pure(ycoord) * pure(zcoord) * pure(one) * share(epot) * share(vir) * share(xforce) * share(yforce) * share(zforce) * unique(interactions) in alive",ensures="pure(xcoord) * pure(ycoord) * pure(zcoord) * pure(one) * share(epot) * share(vir) * share(xforce) * share(yforce) * share(zforce) * unique(interactions) in alive") public void force(  double side,  double rcoff,  int mdsize,  int x){
    double sideh;
    double rcoffs;
    double xx, yy, zz, xi, yi, zi, fxi, fyi, fzi;
    double rd, rrd, rrd2, rrd3, rrd4, rrd6, rrd7, r148;
    double forcex, forcey, forcez;
    int i;
    sideh=0.5 * side;
    rcoffs=rcoff * rcoff;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().lock();
    xi=xcoord;
    yi=ycoord;
    zi=zcoord;
    fxi=0.0;
    fyi=0.0;
    fzi=0.0;
    for (i=x + 1; i < mdsize; i++) {
      xx=xi - md.one[i].xcoord;
      yy=yi - md.one[i].ycoord;
      zz=zi - md.one[i].zcoord;
      if (xx < (-sideh)) {
        xx=xx + side;
      }
      if (xx > (sideh)) {
        xx=xx - side;
      }
      if (yy < (-sideh)) {
        yy=yy + side;
      }
      if (yy > (sideh)) {
        yy=yy - side;
      }
      if (zz < (-sideh)) {
        zz=zz + side;
      }
      if (zz > (sideh)) {
        zz=zz - side;
      }
      rd=xx * xx + yy * yy + zz * zz;
      if (rd <= rcoffs) {
        rrd=1.0 / rd;
        rrd2=rrd * rrd;
        rrd3=rrd2 * rrd;
        rrd4=rrd2 * rrd2;
        rrd6=rrd2 * rrd4;
        rrd7=rrd6 * rrd;
        md.epot=md.epot + (rrd6 - rrd3);
        r148=rrd7 - 0.5 * rrd4;
        md.vir=md.vir - rd * r148;
        forcex=xx * r148;
        fxi=fxi + forcex;
        md.one[i].xforce=md.one[i].xforce - forcex;
        forcey=yy * r148;
        fyi=fyi + forcey;
        md.one[i].yforce=md.one[i].yforce - forcey;
        forcez=zz * r148;
        fzi=fzi + forcez;
        md.one[i].zforce=md.one[i].zforce - forcez;
        md.interactions++;
      }
    }
    xforce=xforce + fxi;
    yforce=yforce + fyi;
    zforce=zforce + fzi;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().unlock();
  }
  @Perm(requires="share(xforce) * share(yforce) * share(zforce) * share(xvelocity) * share(yvelocity) * share(zvelocity) in alive",ensures="share(xforce) * share(yforce) * share(zforce) * share(xvelocity) * share(yvelocity) * share(zvelocity) in alive") public double mkekin(  double hsq2){
    double sumt=0.0;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().lock();
    xforce=xforce * hsq2;
    yforce=yforce * hsq2;
    zforce=zforce * hsq2;
    xvelocity=xvelocity + xforce;
    yvelocity=yvelocity + yforce;
    zvelocity=zvelocity + zforce;
    sumt=(xvelocity * xvelocity) + (yvelocity * yvelocity) + (zvelocity * zvelocity);
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().unlock();
    return sumt;
  }
  @Perm(requires="pure(xvelocity) * pure(yvelocity) * pure(zvelocity) * share(count) in alive",ensures="pure(xvelocity) * pure(yvelocity) * pure(zvelocity) * share(count) in alive") public double velavg(  double vaverh,  double h){
    double velt;
    double sq;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().lock();
    sq=Math.sqrt(xvelocity * xvelocity + yvelocity * yvelocity + zvelocity * zvelocity);
    if (sq > vaverh) {
      md.count=md.count + 1.0;
    }
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().unlock();
    velt=sq;
    return velt;
  }
  @Perm(requires="share(xvelocity) * share(yvelocity) * share(zvelocity) in alive",ensures="share(xvelocity) * share(yvelocity) * share(zvelocity) in alive") public void dscal(  double sc,  int incx){
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().lock();
    xvelocity=xvelocity * sc;
    yvelocity=yvelocity * sc;
    zvelocity=zvelocity * sc;
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock.writeLock().unlock();
  }
  public ReentrantReadWriteLock getCount_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock(){
    Cloner cloner=new Cloner();
    count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock=cloner.deepClone(count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock);
    return count_epot_interactions_one_vir_xcoord_xforce_xvelocity_ycoord_yforce_yvelocity_zcoord_zforce_zvelocityLock;
  }
}
class random {
  public ReentrantReadWriteLock iseed_v1_v2Lock=new ReentrantReadWriteLock();
  public int iseed;
  public double v1, v2;
  @Perm(requires="none(iseed) * none(v1) * none(v2) * unique(iseed) * unique(v1) * unique(v2) in alive",ensures="none(iseed) * none(v1) * none(v2) * unique(iseed) * unique(v1) * unique(v2) in alive") public random(  int iseed,  double v1,  double v2){
    iseed_v1_v2Lock.writeLock().lock();
    this.iseed=iseed;
    this.v1=v1;
    this.v2=v2;
    iseed_v1_v2Lock.writeLock().unlock();
  }
  @Perm(requires="share(iseed) in alive",ensures="share(iseed) in alive") public double update(){
    double rand;
    double scale=4.656612875e-10;
    int is1, is2, iss2;
    int imult=16807;
    int imod=2147483647;
    iseed_v1_v2Lock.writeLock().lock();
    if (iseed <= 0) {
      iseed=1;
    }
    is2=iseed % 32768;
    is1=(iseed - is2) / 32768;
    iss2=is2 * imult;
    is2=iss2 % 32768;
    is1=(is1 * imult + (iss2 - is2) / 32768) % (65536);
    iseed=(is1 * 32768 + is2) % imod;
    rand=scale * iseed;
    iseed_v1_v2Lock.writeLock().unlock();
    return rand;
  }
  @Perm(requires="full(v1) * full(v2) in alive",ensures="full(v1) * full(v2) in alive") public double seed(){
    double s, u1, u2, r;
    do {
      iseed_v1_v2Lock.writeLock().lock();
      u1=update();
      u2=update();
      v1=2.0 * u1 - 1.0;
      v2=2.0 * u2 - 1.0;
      s=v1 * v1 + v2 * v2;
      iseed_v1_v2Lock.writeLock().unlock();
    }
 while (s >= 1.0);
    r=Math.sqrt(-2.0 * Math.log(s) / s);
    return r;
  }
  public ReentrantReadWriteLock getIseed_v1_v2Lock(){
    Cloner cloner=new Cloner();
    iseed_v1_v2Lock=cloner.deepClone(iseed_v1_v2Lock);
    return iseed_v1_v2Lock;
  }
}
