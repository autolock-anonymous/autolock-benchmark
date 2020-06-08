package jomp.crypt.withlock;
import java.util.*;
import jomp.jgfutil.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class IDEATest {
  public ReentrantReadWriteLock DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock=new ReentrantReadWriteLock();
  public int array_rows;
  byte[] plain1;
  byte[] crypt1;
  byte[] plain2;
  short[] userkey;
  int[] Z;
  int[] DK;
  @Perm(requires="pure(plain1) * pure(crypt1) * pure(Z) * pure(plain2) * pure(DK) in alive",ensures="pure(plain1) * pure(crypt1) * pure(Z) * pure(plain2) * pure(DK) in alive") public void Do(){
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.readLock().lock();
    cipheridea(plain1,crypt1,Z);
    cipheridea(crypt1,plain2,DK);
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.readLock().unlock();
  }
  @Perm(requires="unique(plain1) * pure(array_rows) * unique(crypt1) * unique(plain2) * unique(userkey) * unique(Z) * unique(DK) in alive",ensures="unique(plain1) * pure(array_rows) * unique(crypt1) * unique(plain2) * unique(userkey) * unique(Z) * unique(DK) in alive") void buildTestData(){
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().lock();
    plain1=new byte[array_rows];
    crypt1=new byte[array_rows];
    plain2=new byte[array_rows];
    Random rndnum=new Random(136506717L);
    userkey=new short[8];
    Z=new int[52];
    DK=new int[52];
    for (int i=0; i < 8; i++) {
      userkey[i]=(short)rndnum.nextInt();
    }
    calcEncryptKey();
    calcDecryptKey();
    for (int i=0; i < array_rows; i++) {
      plain1[i]=(byte)i;
    }
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().unlock();
  }
  @Perm(requires="share(Z) * pure(userkey) in alive",ensures="share(Z) * pure(userkey) in alive") private void calcEncryptKey(){
    int j;
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().lock();
    for (int i=0; i < 52; i++)     Z[i]=0;
    for (int i=0; i < 8; i++) {
      Z[i]=userkey[i] & 0xffff;
    }
    for (int i=8; i < 52; i++) {
      j=i % 8;
      if (j < 6) {
        Z[i]=((Z[i - 7] >>> 9) | (Z[i - 6] << 7)) & 0xFFFF;
        continue;
      }
      if (j == 6) {
        Z[i]=((Z[i - 7] >>> 9) | (Z[i - 14] << 7)) & 0xFFFF;
        continue;
      }
      Z[i]=((Z[i - 15] >>> 9) | (Z[i - 14] << 7)) & 0xFFFF;
    }
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().unlock();
  }
  @Perm(requires="share(Z) * share(DK) in alive",ensures="share(Z) * share(DK) in alive") private void calcDecryptKey(){
    int j, k;
    int t1, t2, t3;
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().lock();
    t1=inv(Z[0]);
    t2=-Z[1] & 0xffff;
    t3=-Z[2] & 0xffff;
    DK[51]=inv(Z[3]);
    DK[50]=t3;
    DK[49]=t2;
    DK[48]=t1;
    j=47;
    k=4;
    for (int i=0; i < 7; i++) {
      t1=Z[k++];
      DK[j--]=Z[k++];
      DK[j--]=t1;
      t1=inv(Z[k++]);
      t2=-Z[k++] & 0xffff;
      t3=-Z[k++] & 0xffff;
      DK[j--]=inv(Z[k++]);
      DK[j--]=t2;
      DK[j--]=t3;
      DK[j--]=t1;
    }
    t1=Z[k++];
    DK[j--]=Z[k++];
    DK[j--]=t1;
    t1=inv(Z[k++]);
    t2=-Z[k++] & 0xffff;
    t3=-Z[k++] & 0xffff;
    DK[j--]=inv(Z[k++]);
    DK[j--]=t3;
    DK[j--]=t2;
    DK[j--]=t1;
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().unlock();
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private void cipheridea(  byte[] text1,  byte[] text2,  int[] key){
    int i1=0;
    int i2=0;
    int ik;
    int x1, x2, x3, x4, t1, t2;
    int r;
    for (int i=0; i < text1.length; i+=8) {
      i1=i;
      i2=i;
      ik=0;
      r=8;
      x1=text1[i1++] & 0xff;
      x1|=(text1[i1++] & 0xff) << 8;
      x2=text1[i1++] & 0xff;
      x2|=(text1[i1++] & 0xff) << 8;
      x3=text1[i1++] & 0xff;
      x3|=(text1[i1++] & 0xff) << 8;
      x4=text1[i1++] & 0xff;
      x4|=(text1[i1++] & 0xff) << 8;
      do {
        x1=(int)((long)x1 * key[ik++] % 0x10001L & 0xffff);
        x2=x2 + key[ik++] & 0xffff;
        x3=x3 + key[ik++] & 0xffff;
        x4=(int)((long)x4 * key[ik++] % 0x10001L & 0xffff);
        t2=x1 ^ x3;
        t2=(int)((long)t2 * key[ik++] % 0x10001L & 0xffff);
        t1=t2 + (x2 ^ x4) & 0xffff;
        t1=(int)((long)t1 * key[ik++] % 0x10001L & 0xffff);
        t2=t1 + t2 & 0xffff;
        x1^=t1;
        x4^=t2;
        t2^=x2;
        x2=x3 ^ t1;
        x3=t2;
      }
 while (--r != 0);
      x1=(int)((long)x1 * key[ik++] % 0x10001L & 0xffff);
      x3=x3 + key[ik++] & 0xffff;
      x2=x2 + key[ik++] & 0xffff;
      x4=(int)((long)x4 * key[ik++] % 0x10001L & 0xffff);
      text2[i2++]=(byte)x1;
      text2[i2++]=(byte)(x1 >>> 8);
      text2[i2++]=(byte)x3;
      text2[i2++]=(byte)(x3 >>> 8);
      text2[i2++]=(byte)x2;
      text2[i2++]=(byte)(x2 >>> 8);
      text2[i2++]=(byte)x4;
      text2[i2++]=(byte)(x4 >>> 8);
    }
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private int mul(  int a,  int b) throws ArithmeticException {
    long p;
    if (a != 0) {
      if (b != 0) {
        p=(long)a * b;
        b=(int)p & 0xFFFF;
        a=(int)p >>> 16;
        return (b - a + (b < a ? 1 : 0) & 0xFFFF);
      }
 else       return ((1 - a) & 0xFFFF);
    }
 else     return ((1 - b) & 0xFFFF);
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") private int inv(  int x){
    int t0, t1;
    int q, y;
    if (x <= 1)     return (x);
    t1=0x10001 / x;
    y=0x10001 % x;
    if (y == 1)     return ((1 - t1) & 0xFFFF);
    t0=1;
    do {
      q=x / y;
      x=x % y;
      t0+=q * t1;
      if (x == 1)       return (t0);
      q=y / x;
      y=y % x;
      t1+=q * t0;
    }
 while (y != 1);
    return ((1 - t1) & 0xFFFF);
  }
  @Perm(requires="unique(plain1) * unique(crypt1) * unique(plain2) * unique(userkey) * unique(Z) * unique(DK) in alive",ensures="unique(plain1) * unique(crypt1) * unique(plain2) * unique(userkey) * unique(Z) * unique(DK) in alive") void freeTestData(){
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().lock();
    plain1=null;
    crypt1=null;
    plain2=null;
    userkey=null;
    Z=null;
    DK=null;
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock.writeLock().unlock();
    System.gc();
  }
  public ReentrantReadWriteLock getDK_Z_array_rows_crypt1_plain1_plain2_userkeyLock(){
    Cloner cloner=new Cloner();
    DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock=cloner.deepClone(DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock);
    return DK_Z_array_rows_crypt1_plain1_plain2_userkeyLock;
  }
}
