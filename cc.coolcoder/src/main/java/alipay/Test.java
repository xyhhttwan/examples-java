package alipay;

public class Test {

    public synchronized void test1() {

    }

    public void test2() {

        System.out.println(1 << 30);
    }

    public static void main(String[] args) {
        new Test().test2();
    }
}
