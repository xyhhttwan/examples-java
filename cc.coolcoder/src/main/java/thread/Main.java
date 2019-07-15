package thread;

public class Main {


    public static void main(String[] args) {

        BlockedQueue cc = new BlockedQueue();
        int produc = 0;
        while (true) {
            produc++;

            for (int k = 0; k < 3; k++) {
                cc.enq(produc);
            }

            cc.eq(produc);

        }

    }
}
