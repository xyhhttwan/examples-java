package redis.mq;

public class ConMain {


    private static String MESSAGE_ID = "informList";

    public static void main(String[] args) {
        Consumer.start(MESSAGE_ID);
    }
}
