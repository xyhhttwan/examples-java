package jvm;

public class jvm {


    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {

//        byte[] allocation1, allocation2, allocation3, allocation4;
//        allocation1 = new byte[2 * _1M];
//        allocation2 = new byte[2 * _1M];
//        allocation3 = new byte[2 * _1M];
//        allocation4 = new byte[5 * _1M];

        test();
    }

    private static void test() {
        {
            byte[] data = new byte[64 * 1024 * 1024];
        }

       // int a=0;
        System.gc();
    }


}
