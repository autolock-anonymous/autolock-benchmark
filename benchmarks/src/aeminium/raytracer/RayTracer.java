package aeminium.raytracer.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class RayTracer {
  public ReentrantReadWriteLock height_scene_widthLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock tLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock iorLock=new ReentrantReadWriteLock();
  public ReentrantReadWriteLock D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock=new ReentrantReadWriteLock();
  Scene scene;
  Light lights[];
  Primitive prim[];
  View view;
  static final int alpha=255 << 24;
  static final Vec voidVec=new Vec();
  int height;
  int width;
  int datasizes[]={150,500};
  long checksum=0;
  int size;
  int numobjects;
  @Perm(requires="immutable(surf) * immutable(shine) * share(ks) * share(kt) in alive",ensures="immutable(surf) * immutable(shine) * share(ks) * share(kt) in alive") Scene createScene(){
    int x=0;
    int y=0;
    Scene scene=new Scene();
    Primitive p;
    int nx=4;
    int ny=4;
    int nz=4;
    for (int i=0; i < nx; i++) {
      for (int j=0; j < ny; j++) {
        for (int k=0; k < nz; k++) {
          double xx=20.0 / (nx - 1) * i - 10.0;
          double yy=20.0 / (ny - 1) * j - 10.0;
          double zz=20.0 / (nz - 1) * k - 10.0;
          p=new Sphere(new Vec(xx,yy,zz),3);
          p.setColor(0,0,(i + j) / (double)(nx + ny - 2));
          D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().lock();
          p.surf.shine=15.0;
          p.surf.ks=1.5 - 1.0;
          p.surf.kt=1.5 - 1.0;
          D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().unlock();
          scene.addObject(p);
        }
      }
    }
    scene.addLight(new Light(100,100,-50,1.0));
    scene.addLight(new Light(-100,100,-50,1.0));
    scene.addLight(new Light(100,-100,-50,1.0));
    scene.addLight(new Light(-100,-100,-50,1.0));
    scene.addLight(new Light(200,200,0,1.0));
    View v=new View(new Vec(x,20,-30),new Vec(x,y,0),new Vec(0,1,0),1.0,35.0 * 3.14159265 / 180.0,1.0);
    scene.setView(v);
    return scene;
  }
  @Perm(requires="unique(lights) * unique(prim) * share(view) in alive",ensures="unique(lights) * unique(prim) * share(view) in alive") public void setScene(  Scene scene){
    int nLights=scene.getLights();
    int nObjects=scene.getObjects();
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().lock();
    lights=new Light[nLights];
    prim=new Primitive[nObjects];
    for (int l=0; l < nLights; l++) {
      lights[l]=scene.getLight(l);
    }
    for (int o=0; o < nObjects; o++) {
      prim[o]=scene.getObject(o);
    }
    view=scene.getView();
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="pure(view) * share(D) * pure(x) * pure(y) * pure(z) * share(checksum) in alive",ensures="pure(view) * share(D) * pure(x) * pure(y) * pure(z) * share(checksum) in alive") public void render(  Interval interval){
    int row[]=new int[interval.width * (interval.yto - interval.yfrom)];
    int pixCounter=0;
    int red, green, blue;
    double xlen, ylen;
    Vec viewVec;
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().lock();
    viewVec=Vec.sub(view.at,view.from);
    viewVec.normalize();
    Vec tmpVec=new Vec(viewVec);
    tmpVec.scale(Vec.dot(view.up,viewVec));
    Vec upVec=Vec.sub(view.up,tmpVec);
    upVec.normalize();
    Vec leftVec=Vec.cross(view.up,viewVec);
    leftVec.normalize();
    double frustrumwidth=view.dist * Math.tan(view.angle);
    upVec.scale(-frustrumwidth);
    leftVec.scale(view.aspect * frustrumwidth);
    Vec col=new Vec();
    for (int y=interval.yfrom; y < interval.yto; y++) {
      Ray r=new Ray(view.from,voidVec);
      pixCounter=(y - interval.yfrom) * interval.width;
      ylen=(double)(2.0 * y) / (double)interval.width - 1.0;
      for (int x=0; x < interval.width; x++) {
        xlen=(double)(2.0 * x) / (double)interval.width - 1.0;
        r.D=Vec.comb(xlen,leftVec,ylen,upVec);
        r.D.add(viewVec);
        r.D.normalize();
        col=trace(0,1.0,r);
        red=(int)(col.x * 255.0);
        if (red > 255)         red=255;
        green=(int)(col.y * 255.0);
        if (green > 255)         green=255;
        blue=(int)(col.z * 255.0);
        if (blue > 255)         blue=255;
        checksum+=red;
        checksum+=green;
        checksum+=blue;
        row[pixCounter++]=alpha | (red << 16) | (green << 8)| (blue);
      }
    }
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().unlock();
  }
  @Perm(requires="immutable(t) * pure(prim) * pure(prim) * pure(surf) * pure(enter) in alive",ensures="immutable(t) * pure(prim) * pure(prim) * pure(surf) * pure(enter) in alive") boolean intersect(  Ray r,  double maxt,  Isect inter){
    Isect tp;
    int i, nhits;
    nhits=0;
    inter.t=1e9;
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.readLock().lock();
    for (i=0; i < prim.length; i++) {
      tp=prim[i].intersect(r);
      if (tp != null && tp.t < inter.t) {
        inter.t=tp.t;
        inter.prim=tp.prim;
        inter.surf=tp.surf;
        inter.enter=tp.enter;
        nhits++;
      }
    }
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.readLock().unlock();
    return nhits > 0 ? true : false;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") int Shadow(  Ray r,  double tmax,  Isect inter){
    try {
      D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.readLock().lock();
      if (intersect(r,tmax,inter))       return 0;
    }
  finally {
      D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.readLock().unlock();
    }
    return 1;
  }
  @Perm(requires="no permission in alive",ensures="no permission in alive") Vec SpecularDirection(  Vec I,  Vec N){
    Vec r;
    r=Vec.comb(1.0 / Math.abs(Vec.dot(I,N)),I,2.0,N);
    r.normalize();
    return r;
  }
  @Perm(requires="immutable(ior) in alive",ensures="immutable(ior) in alive") Vec TransDir(  Surface m1,  Surface m2,  Vec I,  Vec N){
    double n1, n2, eta, c1, cs2;
    Vec r;
    n1=m1 == null ? 1.0 : m1.ior;
    n2=m2 == null ? 1.0 : m2.ior;
    eta=n1 / n2;
    c1=-Vec.dot(I,N);
    cs2=1.0 - eta * eta * (1.0 - c1 * c1);
    if (cs2 < 0.0)     return null;
    r=Vec.comb(eta,I,eta * c1 - Math.sqrt(cs2),N);
    r.normalize();
    return r;
  }
  @Perm(requires="share(D) * share(surf) * immutable(shine) * pure(lights) * immutable(pos) * immutable(P) * immutable(kd) * immutable(brightness) * immutable(color) * pure(x) * pure(y) * pure(z) * pure(ks) * pure(kt) * pure(enter) in alive",ensures="share(D) * share(surf) * immutable(shine) * pure(lights) * immutable(pos) * immutable(P) * immutable(kd) * immutable(brightness) * immutable(color) * pure(x) * pure(y) * pure(z) * pure(ks) * pure(kt) * pure(enter) in alive") Vec shade(  int level,  double weight,  Vec P,  Vec N,  Vec I,  Isect hit,  Isect inter){
    double n1, n2, eta, c1, cs2;
    Vec r;
    Vec tcol;
    Vec R;
    double t, diff, spec;
    Surface surf;
    Vec col;
    int l;
    Vec L=new Vec();
    Ray tRay=new Ray();
    col=new Vec();
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().lock();
    surf=hit.surf;
    R=new Vec();
    if (surf.shine > 1e-6) {
      R=SpecularDirection(I,N);
    }
    for (l=0; l < lights.length; l++) {
      L.sub2(lights[l].pos,P);
      if (Vec.dot(N,L) >= 0.0) {
        t=L.normalize();
        tRay.P=P;
        tRay.D=L;
        if (Shadow(tRay,t,inter) > 0) {
          diff=Vec.dot(N,L) * surf.kd * lights[l].brightness;
          col.adds(diff,surf.color);
          if (surf.shine > 1e-6) {
            spec=Vec.dot(R,L);
            if (spec > 1e-6) {
              spec=Math.pow(spec,surf.shine);
              col.x+=spec;
              col.y+=spec;
              col.z+=spec;
            }
          }
        }
      }
    }
    tRay.P=P;
    if (surf.ks * weight > 1e-3) {
      tRay.D=SpecularDirection(I,N);
      tcol=trace(level + 1,surf.ks * weight,tRay);
      col.adds(surf.ks,tcol);
    }
    if (surf.kt * weight > 1e-3) {
      if (hit.enter > 0)       tRay.D=TransDir(null,surf,I,N);
 else       tRay.D=TransDir(surf,null,I,N);
      tcol=trace(level + 1,surf.kt * weight,tRay);
      col.adds(surf.kt,tcol);
    }
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().unlock();
    tcol=null;
    surf=null;
    return col;
  }
  @Perm(requires="immutable(t) * pure(prim) * pure(D) * pure(prim) in alive",ensures="immutable(t) * pure(prim) * pure(D) * pure(prim) in alive") Vec trace(  int level,  double weight,  Ray r){
    Vec P, N;
    boolean hit;
    if (level > 6) {
      return new Vec();
    }
    Isect inter=new Isect();
    try {
      D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.readLock().lock();
      D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().lock();
      hit=intersect(r,1e6,inter);
      if (hit) {
        P=r.point(inter.t);
        N=inter.prim.normal(P);
        if (Vec.dot(r.D,N) >= 0.0) {
          N.negate();
        }
        return shade(level,weight,P,N,r.D,inter,inter);
      }
    }
  finally {
      D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().unlock();
      D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.readLock().unlock();
    }
    return voidVec;
  }
  @Perm(requires="share(scene) * pure(width) * pure(height) * immutable(width) in alive",ensures="share(scene) * pure(width) * pure(height) * immutable(width) in alive") public static void main(  String argv[]){
    RayTracer rt=new RayTracer();
    height_scene_widthLock.readLock().lock();
    height_scene_widthLock.writeLock().lock();
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().lock();
    rt.scene=rt.createScene();
    rt.setScene(rt.scene);
    Interval interval=new Interval(0,rt.width,rt.height,0,rt.height,1);
    rt.render(interval);
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock.writeLock().unlock();
    height_scene_widthLock.writeLock().unlock();
    height_scene_widthLock.readLock().unlock();
  }
  public Primitive getPrim(){
    Cloner cloner=new Cloner();
    prim=cloner.deepClone(prim);
    Cloner cloner=new Cloner();
    prim=cloner.deepClone(prim);
    return prim;
  }
  public View getView(){
    Cloner cloner=new Cloner();
    view=cloner.deepClone(view);
    Cloner cloner=new Cloner();
    view=cloner.deepClone(view);
    return view;
  }
  public ReentrantReadWriteLock getIorLock(){
    Cloner cloner=new Cloner();
    iorLock=cloner.deepClone(iorLock);
    Cloner cloner=new Cloner();
    iorLock=cloner.deepClone(iorLock);
    return iorLock;
  }
  public Scene getScene(){
    Cloner cloner=new Cloner();
    scene=cloner.deepClone(scene);
    Cloner cloner=new Cloner();
    scene=cloner.deepClone(scene);
    return scene;
  }
  public Light getLights(){
    Cloner cloner=new Cloner();
    lights=cloner.deepClone(lights);
    Cloner cloner=new Cloner();
    lights=cloner.deepClone(lights);
    return lights;
  }
  public static Vec getVoidVec(){
    Cloner cloner=new Cloner();
    voidVec=cloner.deepClone(voidVec);
    Cloner cloner=new Cloner();
    voidVec=cloner.deepClone(voidVec);
    return voidVec;
  }
  public ReentrantReadWriteLock getHeight_scene_widthLock(){
    Cloner cloner=new Cloner();
    height_scene_widthLock=cloner.deepClone(height_scene_widthLock);
    Cloner cloner=new Cloner();
    height_scene_widthLock=cloner.deepClone(height_scene_widthLock);
    return height_scene_widthLock;
  }
  public ReentrantReadWriteLock getTLock(){
    Cloner cloner=new Cloner();
    tLock=cloner.deepClone(tLock);
    Cloner cloner=new Cloner();
    tLock=cloner.deepClone(tLock);
    return tLock;
  }
  public ReentrantReadWriteLock getD_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock(){
    Cloner cloner=new Cloner();
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock=cloner.deepClone(D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock);
    Cloner cloner=new Cloner();
    D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock=cloner.deepClone(D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock);
    return D_P_brightness_checksum_color_enter_kd_ks_kt_lights_pos_prim_shine_surf_view_x_y_zLock;
  }
}
