package redis.mq;

public class Main {

    private static String MESSAGE_ID = "informList";

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Producter.addQuene(MESSAGE_ID, "value_k" + i);
        }

    }
}
