package redis.mq;

import redis.client.RedisClient;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;

public class Consumer {


    private static String destinationList="destinationList";

    public static void consumerQuene(String messageId) {

        while (true) {
            try {
                String resultList = RedisClient.Instance().brpoplpush(messageId, destinationList, 0);
                processMeaage(resultList);
            } catch (Exception exception) {
                System.err.println("发生异常:" + exception.getMessage());
                exception.printStackTrace();
                continue;
            }
        }

    }

    private static void processMeaage(String resultList) {
        System.out.println("处理结果:" + resultList);
        delDestinationList();
    }

    private static void delDestinationList() {

    }

    public static void start(String messageId) {
        consumerQuene(messageId);
    }
}
