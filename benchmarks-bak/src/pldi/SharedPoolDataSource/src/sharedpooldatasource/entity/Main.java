package sharedpooldatasource.entity;


public class Main{
    public static void main(String[] args) throws Exception {
        final SharedPoolDataSource var0 = new SharedPoolDataSource();
        var0.setConnectionPoolDataSource(null);
        var0.close();
        var0.close();
        final SharedPoolDataSource var1 = new SharedPoolDataSource();
        final javax.sql.ConnectionPoolDataSource var2 = var1.getConnectionPoolDataSource();
        var0.setConnectionPoolDataSource(var2);

    }
}