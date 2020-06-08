package jomp.lufact.withlock;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Linpack {
  public ReentrantReadWriteLock a_one_dimLock=new ReentrantReadWriteLock();
  double a[][];
  double a_one_dim[][];
  double b[];
  double x[];
  double ops, total, norma, normx;
  double resid, time;
  double kf;
  int n, i, ntimes, info, lda, ldaa, kflops;
  int ipvt[];
  @Perm(requires="no permission in alive",ensures="no permission in alive") final double abs(  double d){
    return (d >= 0) ? d : -d;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final double matgen(  double a[][],  int lda,  int n,  double b[]){
    System.out.println("n = " + n);
    double norma;
    int init, i, j;
    init=1325;
    norma=0.0;
    for (i=0; i < n; i++) {
      for (j=0; j < n; j++) {
        init=3125 * init % 65536;
        a[j][i]=(init - 32768.0) / 16384.0;
        norma=(a[j][i] > norma) ? a[j][i] : norma;
      }
    }
    for (i=0; i < n; i++) {
      b[i]=0.0;
    }
    for (j=0; j < n; j++) {
      for (i=0; i < n; i++) {
        b[i]+=a[j][i];
      }
    }
    return norma;
  }
  @Perm(requires="unique(a_one_dim) in alive",ensures="unique(a_one_dim) in alive") final int dgefa(  double a[][],  int lda,  int n,  int ipvt[]){
    double[] col_k, col_j;
    double t;
    int k, kp1, l, nm1;
    int info;
    info=0;
    nm1=n - 1;
    if (nm1 >= 0) {
      for (k=0; k < nm1; k++) {
        a_one_dimLock.writeLock().lock();
        a_one_dim[k]=a[k];
        col_k=a_one_dim[k];
        a_one_dimLock.writeLock().unlock();
        kp1=k + 1;
        l=idamax(col_k,n,k,1) + k;
        ipvt[k]=l;
        if (col_k[l] != 0) {
          if (l != k) {
            t=col_k[l];
            col_k[l]=col_k[k];
            col_k[k]=t;
          }
          t=-1.0 / col_k[k];
          dscal(col_k,n,t,kp1,1);
          for (int j=kp1; j < n; j++) {
            col_j=a[j];
            t=col_j[l];
            if (l != k) {
              col_j[l]=col_j[k];
              col_j[k]=t;
            }
            daxpy(col_k,n,col_j,t,kp1,1,kp1,1);
          }
        }
 else {
          info=k;
        }
      }
    }
    ipvt[n - 1]=n - 1;
    if (a[(n - 1)][(n - 1)] == 0)     info=n - 1;
    return info;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final void dgesl(  double a[][],  int lda,  int n,  int ipvt[],  double b[],  int job){
    double t;
    int k, kb, l, nm1, kp1;
    nm1=n - 1;
    if (job == 0) {
      if (nm1 >= 1) {
        for (k=0; k < nm1; k++) {
          l=ipvt[k];
          t=b[l];
          if (l != k) {
            b[l]=b[k];
            b[k]=t;
          }
          kp1=k + 1;
          daxpy(a[k],n,b,t,kp1,1,kp1,1);
        }
      }
      for (kb=0; kb < n; kb++) {
        k=n - (kb + 1);
        b[k]/=a[k][k];
        t=-b[k];
        daxpy(a[k],k,b,t,0,1,0,1);
      }
    }
 else {
      for (k=0; k < n; k++) {
        t=ddot(a[k],b,k,0,1,0,1);
        b[k]=(b[k] - t) / a[k][k];
      }
      if (nm1 >= 1) {
        for (kb=1; kb < nm1; kb++) {
          k=n - (kb + 1);
          kp1=k + 1;
          b[k]+=ddot(a[k],b,n,kp1,1,kp1,1);
          l=ipvt[k];
          if (l != k) {
            t=b[l];
            b[l]=b[k];
            b[k]=t;
          }
        }
      }
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final void daxpy(  double dx[],  int n,  double dy[],  double da,  int dx_off,  int incx,  int dy_off,  int incy){
    int i, ix, iy;
    if ((n > 0) && (da != 0)) {
      if (incx != 1 || incy != 1) {
        ix=0;
        iy=0;
        if (incx < 0)         ix=(-n + 1) * incx;
        if (incy < 0)         iy=(-n + 1) * incy;
        for (i=0; i < n; i++) {
          dy[iy + dy_off]=dy[iy + dy_off] + da * dx[ix + dx_off];
          ix+=incx;
          iy+=incy;
        }
        return;
      }
 else {
        for (i=0; i < n; i++)         dy[i + dy_off]=dy[i + dy_off] + da * dx[i + dx_off];
      }
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final double ddot(  double dx[],  double dy[],  int n,  int dx_off,  int incx,  int dy_off,  int incy){
    double dtemp;
    int i, ix, iy;
    dtemp=0;
    if (n > 0) {
      if (incx != 1 || incy != 1) {
        ix=0;
        iy=0;
        if (incx < 0)         ix=(-n + 1) * incx;
        if (incy < 0)         iy=(-n + 1) * incy;
        for (i=0; i < n; i++) {
          dtemp=dtemp + dx[ix + dx_off] * dy[iy + dy_off];
          ix+=incx;
          iy+=incy;
        }
      }
 else {
        for (i=0; i < n; i++)         dtemp=dtemp + dx[i + dx_off] * dy[i + dy_off];
      }
    }
    return (dtemp);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final void dscal(  double dx[],  int n,  double da,  int dx_off,  int incx){
    int i, nincx;
    if (n > 0) {
      if (incx != 1) {
        nincx=n * incx;
        for (i=0; i < nincx; i+=incx)         dx[i + dx_off]*=da;
      }
 else {
        for (i=0; i < n; i++)         dx[i + dx_off]*=da;
      }
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final int idamax(  double dx[],  int n,  int dx_off,  int incx){
    double dmax, dtemp;
    int i, ix, itemp=0;
    if (n < 1) {
      itemp=-1;
    }
 else     if (n == 1) {
      itemp=0;
    }
 else     if (incx != 1) {
      dmax=abs(dx[0 + dx_off]);
      ix=1 + incx;
      for (i=1; i < n; i++) {
        dtemp=abs(dx[ix + dx_off]);
        if (dtemp > dmax) {
          itemp=i;
          dmax=dtemp;
        }
        ix+=incx;
      }
    }
 else {
      itemp=0;
      dmax=abs(dx[0 + dx_off]);
      for (i=1; i < n; i++) {
        dtemp=abs(dx[i + dx_off]);
        if (dtemp > dmax) {
          itemp=i;
          dmax=dtemp;
        }
      }
    }
    return (itemp);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final double epslon(  double x){
    double a, b, c, eps;
    a=4.0e0 / 3.0e0;
    eps=0;
    while (eps == 0) {
      b=a - 1.0;
      c=b + b + b;
      eps=abs(c - 1.0);
    }
    return (eps * abs(x));
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") final void dmxpy(  int n1,  double y[],  int n2,  double x[],  double m[][]){
    int j, i;
    for (j=0; j < n2; j++) {
      for (i=0; i < n1; i++) {
        y[i]=y[i] + x[j] * m[j][i];
      }
    }
  }
  public ReentrantReadWriteLock getA_one_dimLock(){
    Cloner cloner=new Cloner();
    a_one_dimLock=cloner.deepClone(a_one_dimLock);
    return a_one_dimLock;
  }
}
