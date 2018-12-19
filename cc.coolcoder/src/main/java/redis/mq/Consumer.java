package redis.mq;

import redis.client.RedisClient;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;

public class Consumer {

    public static int consumerQuene(String messageId) {

        try {
            List<String> resultList = RedisClient.Instance().blpop(1000, messageId);
            for (String res : resultList) {
                System.out.println(res);
            }
        } catch (JedisConnectionException exception) {
            System.out.println("======");
            exception.printStackTrace();
        }

        return 1;

    }

    public static void start(String messageId) {
        consumerQuene(messageId);
    }
}
