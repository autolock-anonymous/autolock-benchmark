package sharedpooldatasource.entity.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
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
