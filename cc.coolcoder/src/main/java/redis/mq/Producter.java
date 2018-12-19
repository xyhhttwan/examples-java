package redis.mq;

import redis.client.RedisClient;

public class Producter {


    public static  Long addQuene(String messageId, String content) {

        return RedisClient.Instance().lpush(messageId, content);
    }


}
