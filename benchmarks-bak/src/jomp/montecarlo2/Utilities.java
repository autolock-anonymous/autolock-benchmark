package jomp.montecarlo2.withlock;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public final class Utilities {
	public static ReentrantReadWriteLock classNameLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock DEBUG_pathsLock = new ReentrantReadWriteLock();
	public static boolean DEBUG = false;
	private static String className = "Utilities";
	static String paths[];
	@Perm(requires = "unique(paths) * immutable(DEBUG) in alive", ensures = "unique(paths) * immutable(DEBUG) in alive")
	public static String which(String executable, String pathEnv) {
		String executablePath;
		try {
			DEBUG_pathsLock.writeLock().lock();
			paths = splitString(System.getProperty("path.separator"), pathEnv);
			for (int i = 0; i < paths.length; i++) {
				if (paths[i].length() > 0) {
					java.io.File pathFile = new java.io.File(paths[i]);
					if (pathFile.isDirectory()) {
						String filesInDirectory[];
						filesInDirectory = pathFile.list();
						for (int j = 0; j < filesInDirectory.length; j++) {
							if (DEBUG) {
								System.out.println("DBG: Matching " + filesInDirectory[j]);
							}
							if (filesInDirectory[j].equals(executable)) {
								executablePath = paths[i] + System.getProperty("file.separator") + executable;
								return executablePath;
							}
						}
					} else {
						if (DEBUG) {
							System.out.println("DBG: path " + paths[i] + " is not a directory!");
						}
					}
				}
			}
		} finally {
			DEBUG_pathsLock.writeLock().unlock();
		}
		executablePath = executable + " not found.";
		return executablePath;
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static String joinString(String joinChar, String stringArray[]) {
		return joinStringOverloaded(joinChar, stringArray, 0);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static String joinStringOverloaded(String joinChar, String stringArray[], int index) {
		String methodName = "join";
		StringBuffer tmpString;
		int nStrings = java.lang.reflect.Array.getLength(stringArray);
		if (nStrings <= index) {
			tmpString = new StringBuffer();
		} else {
			tmpString = new StringBuffer(stringArray[index]);
			for (int i = (index + 1); i < nStrings; i++) {
				tmpString.append(joinChar).append(stringArray[i]);
			}
		}
		return tmpString.toString();
	}
	@Perm(requires = "immutable(DEBUG) * none(className) in alive", ensures = "immutable(DEBUG) * none(className) in alive")
	public static String[] splitString(String splitChar, String arg) {
		String methodName = "split";
		String myArgs[];
		int nArgs = 0;
		int foundIndex = 0, fromIndex = 0;
		while ((foundIndex = arg.indexOf(splitChar, fromIndex)) > -1) {
			nArgs++;
			fromIndex = foundIndex + 1;
		}
		if (DEBUG) {
			System.out.println("DBG " + className + "." + methodName + ": " + nArgs);
		}
		myArgs = new String[nArgs + 1];
		nArgs = 0;
		fromIndex = 0;
		while ((foundIndex = arg.indexOf(splitChar, fromIndex)) > -1) {
			if (DEBUG) {
				System.out.println("DBG " + className + "." + methodName + ": " + fromIndex + " " + foundIndex);
			}
			myArgs[nArgs] = arg.substring(fromIndex, foundIndex);
			nArgs++;
			fromIndex = foundIndex + 1;
		}
		myArgs[nArgs] = arg.substring(fromIndex);
		return myArgs;
	}
}
