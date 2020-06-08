package aeminium.webserver.withlock;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Webserver {
	public static ReentrantReadWriteLock socketClientLock = new ReentrantReadWriteLock();
	public static Socket socketClient = null;
	public ServerSocket mysocket = null;
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void main(String[] args) {
		try {
			webserver();
		} catch (Exception e) {
			System.out.println("Failed to run webserver: " + e);
		}
	}
	@Perm(requires = "unique(socketClient) in alive", ensures = "unique(socketClient) in alive")
	public static void webserver() throws IOException {
		ServerSocket mysocket = new ServerSocket(3330);
		socketClientLock.writeLock().lock();
		socketClient = new Socket("localhost", 3330);
		while (true) {
			socketClient = mysocket.accept();
			serveClient(socketClient);
			socketClient.close();
		}
		socketClientLock.writeLock().unlock();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void serveClient(Socket socketClient) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
		File file = new File("/Users/asad22/Research/test/test.html");
		LOG("serving file '%s'", file.getAbsoluteFile());
		if (file.exists() && file.isFile()) {
			transfer(socketClient.getOutputStream(), file);
		}
		socketClient.close();
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void transfer(OutputStream outStream, File file) throws IOException {
		BufferedReader fileReader = new BufferedReader(new FileReader(file));
		Writer writer = new OutputStreamWriter(outStream);
		transferHeader(writer, file);
		transferData(writer, fileReader, file.length());
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void transferHeader(Writer writer, File file) throws IOException {
		writer.append("HTTP/1.1 200 Script output follows\n");
		if (file.getAbsolutePath().toLowerCase().endsWith(".html")
				|| file.getAbsolutePath().toLowerCase().endsWith(".htm")) {
			writer.append("Content-Type: text/html; charset=UTF-8\n");
		} else if (file.getAbsolutePath().toLowerCase().endsWith(".css")) {
			writer.append("Content-Type: text/css; charset=UTF-8\n");
		} else if (file.getAbsolutePath().toLowerCase().endsWith(".js")) {
			writer.append("Content-Type: text/javascript; charset=UTF-8\n");
		} else {
			LOG("unknown file type '%s'", file.getAbsoluteFile());
			writer.append("Content-Type: text/text; charset=UTF-8\n");
		}
		writer.append("Connection: close\n");
		writer.append("\n");
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void transferData(Writer outWriter, Reader inReader, long count) throws IOException {
		if (count > 0) {
			CharBuffer cb = CharBuffer.allocate((int) Math.max(4096, count));
			int result = inReader.read(cb);
			if (result >= 0) {
				outWriter.write(cb.array());
				count -= result;
				transferData(outWriter, inReader, count);
			}
		} else {
			outWriter.flush();
		}
	}
	@Perm(requires = "no permission in alive", ensures = "no permission in alive")
	public static void LOG(String msg, Object... args) {
		System.out.println(String.format("LOG: " + msg, args));
	}
}
