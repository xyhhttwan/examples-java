package redis.client;

import redis.clients.jedis.Jedis;

public class RedisClient {

    private static Jedis redis;

    public static synchronized Jedis Instance() {

        if (null == redis) {
            Jedis jedis = new Jedis("101.200.62.224", 6379);
            jedis.auth("redis#@123456");
            redis = jedis;
        }

        return redis;

    }


}
