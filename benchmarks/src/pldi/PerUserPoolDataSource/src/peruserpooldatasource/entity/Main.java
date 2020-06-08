package peruserpooldatasource.entity;
public class Main {
  public static void main(  String[] args){
    PerUserPoolDataSource var0=new PerUserPoolDataSource();
    PerUserPoolDataSource var1=new PerUserPoolDataSource();
    javax.sql.ConnectionPoolDataSource var2=var1.getConnectionPoolDataSource();
    var0.setConnectionPoolDataSource(var2);
    var0.close();
    var0.close();
    var1=new PerUserPoolDataSource();
    var2=var1.getConnectionPoolDataSource();
    var0.setConnectionPoolDataSource(var2);
  }
}
