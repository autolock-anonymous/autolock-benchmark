package hashtablejava167.test;


//import hashtablejava167test.hashtablejava167.Hashtable;


import hashtablejava167.entity.Hashtable;

public class MyTest {


    public static void main(String[] args) {
        Hashtable<Integer,String>a =new Hashtable<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    int tmp = a.getModCount();
                    a.rehash();
                    assert tmp + 1 == a.getModCount();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    int tmp = a.getModCount();
                    a.rehash();
                    assert tmp + 1 == a.getModCount();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    int tmp = a.getModCount();
                    a.rehash();
                    assert tmp + 1 == a.getModCount();
                }
            }
        }).start();
    }


}
