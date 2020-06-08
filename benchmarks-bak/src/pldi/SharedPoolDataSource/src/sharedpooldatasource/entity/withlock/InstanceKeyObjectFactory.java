package sharedpooldatasource.entity.withlock;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
abstract class InstanceKeyObjectFactory implements ObjectFactory {
	public ReentrantReadWriteLock jndiEnvironmentLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock instanceMapLock = new ReentrantReadWriteLock();
	private static Map instanceMap = new HashMap();
	@Perm(requires = "share(instanceMap) in alive", ensures = "share(instanceMap) in alive")
	synchronized static String registerNewInstance(InstanceKeyDataSource ds) {
		int max = 0;
		instanceMapLock.writeLock().lock();
		Iterator i = instanceMap.keySet().iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			if (obj instanceof String) {
				try {
					max = Math.max(max, Integer.valueOf((String) obj).intValue());
				} catch (NumberFormatException e) {
				}
			}
		}
		String instanceKey = String.valueOf(max + 1);
		instanceMap.put(instanceKey, ds);
		instanceMapLock.writeLock().unlock();
		return instanceKey;
	}
	@Perm(requires = "share(instanceMap) in alive", ensures = "share(instanceMap) in alive")
	static void removeInstance(String key) {
		instanceMapLock.writeLock().lock();
		instanceMap.remove(key);
		instanceMapLock.writeLock().unlock();
	}
	@Perm(requires = "share(instanceMap) in alive", ensures = "share(instanceMap) in alive")
	public static void closeAll() throws Exception {
		instanceMapLock.writeLock().lock();
		Iterator instanceIterator = instanceMap.entrySet().iterator();
		while (instanceIterator.hasNext()) {
			((InstanceKeyDataSource) ((Map.Entry) instanceIterator.next()).getValue()).close();
		}
		instanceMap.clear();
		instanceMapLock.writeLock().unlock();
	}
	@Perm(requires = "share(instanceMap) in alive", ensures = "share(instanceMap) in alive")
	public Object getObjectInstance(Object refObj, Name name, Context context, Hashtable env)
			throws IOException, ClassNotFoundException {
		Object obj = null;
		if (refObj instanceof Reference) {
			Reference ref = (Reference) refObj;
			if (isCorrectClass(ref.getClassName())) {
				RefAddr ra = ref.get("instanceKey");
				instanceMapLock.writeLock().lock();
				if (ra != null && ra.getContent() != null) {
					obj = instanceMap.get(ra.getContent());
				} else {
					String key = null;
					if (name != null) {
						key = name.toString();
						obj = instanceMap.get(key);
					}
					if (obj == null) {
						InstanceKeyDataSource ds = getNewInstance(ref);
						setCommonProperties(ref, ds);
						obj = ds;
						if (key != null) {
							instanceMap.put(key, ds);
						}
					}
				}
				instanceMapLock.writeLock().unlock();
			}
		}
		return obj;
	}
	@Perm(requires = "pure(jndiEnvironment) in alive", ensures = "pure(jndiEnvironment) in alive")
	private void setCommonProperties(Reference ref, InstanceKeyDataSource ikds)
			throws IOException, ClassNotFoundException {
		RefAddr ra = ref.get("dataSourceName");
		if (ra != null && ra.getContent() != null) {
			ikds.setDataSourceName(ra.getContent().toString());
		}
		ra = ref.get("defaultAutoCommit");
		if (ra != null && ra.getContent() != null) {
			ikds.setDefaultAutoCommit(Boolean.valueOf(ra.getContent().toString()).booleanValue());
		}
		ra = ref.get("defaultReadOnly");
		if (ra != null && ra.getContent() != null) {
			ikds.setDefaultReadOnly(Boolean.valueOf(ra.getContent().toString()).booleanValue());
		}
		ra = ref.get("description");
		if (ra != null && ra.getContent() != null) {
			ikds.setDescription(ra.getContent().toString());
		}
		ra = ref.get("jndiEnvironment");
		if (ra != null && ra.getContent() != null) {
			byte[] serialized = (byte[]) ra.getContent();
			jndiEnvironmentLock.readLock().lock();
			ikds.jndiEnvironment = (Properties) deserialize(serialized);
			jndiEnvironmentLock.readLock().unlock();
		}
		ra = ref.get("loginTimeout");
		if (ra != null && ra.getContent() != null) {
			ikds.setLoginTimeout(Integer.parseInt(ra.getContent().toString()));
		}
		ra = ref.get("testOnBorrow");
		if (ra != null && ra.getContent() != null) {
			ikds.setTestOnBorrow(Boolean.getBoolean(ra.getContent().toString()));
		}
		ra = ref.get("testOnReturn");
		if (ra != null && ra.getContent() != null) {
			ikds.setTestOnReturn(Boolean.valueOf(ra.getContent().toString()).booleanValue());
		}
		ra = ref.get("timeBetweenEvictionRunsMillis");
		if (ra != null && ra.getContent() != null) {
			ikds.setTimeBetweenEvictionRunsMillis(Integer.parseInt(ra.getContent().toString()));
		}
		ra = ref.get("numTestsPerEvictionRun");
		if (ra != null && ra.getContent() != null) {
			ikds.setNumTestsPerEvictionRun(Integer.parseInt(ra.getContent().toString()));
		}
		ra = ref.get("minEvictableIdleTimeMillis");
		if (ra != null && ra.getContent() != null) {
			ikds.setMinEvictableIdleTimeMillis(Integer.parseInt(ra.getContent().toString()));
		}
		ra = ref.get("testWhileIdle");
		if (ra != null && ra.getContent() != null) {
			ikds.setTestWhileIdle(Boolean.valueOf(ra.getContent().toString()).booleanValue());
		}
		ra = ref.get("validationQuery");
		if (ra != null && ra.getContent() != null) {
			ikds.setValidationQuery(ra.getContent().toString());
		}
	}
	protected abstract boolean isCorrectClass(String className);
	protected abstract InstanceKeyDataSource getNewInstance(Reference ref) throws IOException, ClassNotFoundException;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected static final Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(data));
			return in.readObject();
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
		}
	}
}
