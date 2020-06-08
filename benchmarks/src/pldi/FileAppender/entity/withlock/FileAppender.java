package fileappender.entity.withlock;
import java.io.IOException;
import java.io.Writer;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class FileAppender extends WriterAppender {
	public ReentrantReadWriteLock bufferSize_bufferedIO_fileAppend_fileNameLock = new ReentrantReadWriteLock();
	protected boolean fileAppend = true;
	protected String fileName = null;
	protected boolean bufferedIO = false;
	protected int bufferSize = 8 * 1024;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public FileAppender() {
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public FileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		this.layout = layout;
		this.setFile(filename, append, bufferedIO, bufferSize);
	}
	@Perm(requires = "none(bufferSize) in alive", ensures = "none(bufferSize) in alive")
	public FileAppender(Layout layout, String filename, boolean append) throws IOException {
		this.layout = layout;
		this.setFile(filename, append, false, bufferSize);
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public FileAppender(Layout layout, String filename) throws IOException {
		this(layout, filename, true);
	}
	@Perm(requires = "share(fileName) in alive", ensures = "share(fileName) in alive")
	public void setFile(String file) {
		String val = file.trim();
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
		fileName = val;
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
	}
	@Perm(requires = "pure(fileAppend) in alive", ensures = "pure(fileAppend) in alive")
	public boolean getAppend() {
		try {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().lock();
			return fileAppend;
		} finally {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(fileName) in alive", ensures = "pure(fileName) in alive")
	public String getFile() {
		try {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().lock();
			return fileName;
		} finally {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(fileName) * pure(fileAppend) * pure(bufferedIO) * pure(bufferSize) in alive", ensures = "share(fileName) * pure(fileAppend) * pure(bufferedIO) * pure(bufferSize) in alive")
	public void activateOptions() {
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
		if (fileName != null) {
			try {
				setFile(fileName, fileAppend, bufferedIO, bufferSize);
			} catch (java.io.IOException e) {
				errorHandler.error("setFile(" + fileName + "," + fileAppend + ") call failed.", e,
						ErrorCode.FILE_OPEN_FAILURE);
			}
		} else {
		}
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected void closeFile() {
		if (this.qw != null) {
			try {
				this.qw.close();
			} catch (java.io.IOException e) {
				LogLog.error("Could not close " + qw, e);
			}
		}
	}
	@Perm(requires = "pure(bufferedIO) in alive", ensures = "pure(bufferedIO) in alive")
	public boolean getBufferedIO() {
		try {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().lock();
			return this.bufferedIO;
		} finally {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().unlock();
		}
	}
	@Perm(requires = "pure(bufferSize) in alive", ensures = "pure(bufferSize) in alive")
	public int getBufferSize() {
		try {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().lock();
			return this.bufferSize;
		} finally {
			bufferSize_bufferedIO_fileAppend_fileNameLock.readLock().unlock();
		}
	}
	@Perm(requires = "share(fileAppend) in alive", ensures = "share(fileAppend) in alive")
	public void setAppend(boolean flag) {
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
		fileAppend = flag;
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
	}
	@Perm(requires = "share(bufferedIO) in alive", ensures = "share(bufferedIO) in alive")
	public void setBufferedIO(boolean bufferedIO) {
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
		this.bufferedIO = bufferedIO;
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
		if (bufferedIO) {
			immediateFlush = false;
		}
	}
	@Perm(requires = "share(bufferSize) in alive", ensures = "share(bufferSize) in alive")
	public void setBufferSize(int bufferSize) {
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
		this.bufferSize = bufferSize;
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
	}
	@Perm(requires = "share(fileName) * share(fileAppend) * share(bufferedIO) * share(bufferSize) in alive", ensures = "share(fileName) * share(fileAppend) * share(bufferedIO) * share(bufferSize) in alive")
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		LogLog.debug("setFile called: " + fileName + ", " + append);
		if (bufferedIO) {
			setImmediateFlush(false);
		}
		try {
			bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
			reset();
			FileOutputStream ostream = null;
			try {
				ostream = new FileOutputStream(fileName, append);
			} catch (FileNotFoundException ex) {
				String parentName = new File(fileName).getParent();
				if (parentName != null) {
					File parentDir = new File(parentName);
					if (!parentDir.exists() && parentDir.mkdirs()) {
						ostream = new FileOutputStream(fileName, append);
					} else {
						throw ex;
					}
				} else {
					throw ex;
				}
			}
			Writer fw = createWriter(ostream);
			if (bufferedIO) {
				fw = new BufferedWriter(fw, bufferSize);
			}
			this.setQWForFiles(fw);
			this.fileName = fileName;
			this.fileAppend = append;
			this.bufferedIO = bufferedIO;
			this.bufferSize = bufferSize;
		} finally {
			bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
		}
		writeHeader();
		LogLog.debug("setFile ended");
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	protected void setQWForFiles(Writer writer) {
		this.qw = new QuietWriter(writer, errorHandler);
	}
	@Perm(requires = "unique(fileName) in alive", ensures = "unique(fileName) in alive")
	protected void reset() {
		closeFile();
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().lock();
		this.fileName = null;
		bufferSize_bufferedIO_fileAppend_fileNameLock.writeLock().unlock();
		super.reset();
	}
}
