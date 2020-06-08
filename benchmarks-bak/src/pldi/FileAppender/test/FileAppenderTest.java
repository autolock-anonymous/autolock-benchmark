package fileappender.test;


import fileappender.entity.FileAppender;

@SuppressWarnings("deprecation")
public class FileAppenderTest {
	
	public void run() throws Exception {
		FileAppender var0 = new FileAppender(null,"b", true, false, -100);
		Thread t1 = new Thread(() -> {
			try {
				var0.activateOptions();
				var0.activateOptions();
			} catch (Throwable t) { }
		});
		Thread t2 = new Thread(() -> {
			try {
				var0.activateOptions();
				var0.setFile("[2", false, false,  1);
			} catch (Throwable t) {
				if (t instanceof NullPointerException) {
					System.out.println("\n--------------------\nBug found:\n");
					t.printStackTrace(System.out);
					System.out.println("---------------------\n");
					System.exit(0);
				}
			}
		});

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

}
