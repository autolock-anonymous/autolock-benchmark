package vectorjava142.test;

import vectorjava142.entity.Vector;

public class VectorTest {

	public static void main(String[] args) {
		new VectorTest().run();
	}
	
	private void run() {
		final Vector var0 = new Vector((int) 100, (int) 1);
		final Vector var1 = new Vector( var0);
		final java.util.ListIterator var2 = var1.listIterator();
		var1.sort( null);
		final int var3 = var1.indexOf( var0);
		final java.util.Iterator var4 = var1.iterator();
		final Vector var5 = new Vector();
		final boolean var6 = var1.add( var5);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					var1.sort( null);
					final java.lang.Object var7 = var0.clone();
					var1.addElement(var7);
				} catch (Throwable t) {
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					var1.addElement(null);
					var1.addElement(var5);
				} catch (Throwable t) {
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

