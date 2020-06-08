package myexample;

public class Test{
    int a, b, c, d;

    public void test(){
        if(a > 0){
            b --;
        }
    }

    public void foo(){
        a -= 10;
    }

    public static void main(String[] args) {
        new Test().test();
    }
}