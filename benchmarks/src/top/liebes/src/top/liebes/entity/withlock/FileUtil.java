package top.liebes.entity.withlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class FileUtil {
	public ReentrantReadWriteLock fileList_processedFileListLock = new ReentrantReadWriteLock();
	private Queue<String> fileList;
	private List<String> processedFileList;
	@Perm(requires = "unique(fileList) * none(fileList) * unique(processedFileList) * none(processedFileList) in alive", ensures = "unique(fileList) * none(fileList) * unique(processedFileList) * none(processedFileList) in alive")
	public FileUtil() {
		this.fileList = new LinkedBlockingQueue<>();
		this.processedFileList = new ArrayList<>();
	}
	@Perm(requires = "share(fileList) in alive", ensures = "share(fileList) in alive")
	public void getFile(String url) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fileList_processedFileListLock.writeLock().lock();
		fileList.add(url);
		fileList_processedFileListLock.writeLock().unlock();
	}
	@Perm(requires = "share(fileList) * share(processedFileList) in alive", ensures = "share(fileList) * share(processedFileList) in alive")
	public boolean handle() {
		try {
			fileList_processedFileListLock.writeLock().lock();
			if (fileList.size() > 0) {
				String file = fileList.remove();
				file = file + " processed";
				processedFileList.add(file);
				return true;
			}
		} finally {
			fileList_processedFileListLock.writeLock().unlock();
		}
		return false;
	}
	@Perm(requires = "share(processedFileList) in alive", ensures = "share(processedFileList) in alive")
	public void showFiles() {
		fileList_processedFileListLock.writeLock().lock();
		for (int i = 0; i < processedFileList.size(); i++) {
			System.out.println(String.format("File %d: %s", i, processedFileList.get(i)));
		}
		fileList_processedFileListLock.writeLock().unlock();
	}
}
