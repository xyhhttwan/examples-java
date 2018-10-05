package redis.distributedLocks;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 * 分布式锁的事例
 */
public class distributedLocks {


    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(distributedLocks.class);

    private final static String okResult = "OK";

    public static final String UNLOCK_LUA;

    @Autowired
    private RedisTemplate redisTemplate;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * @param logIndex 日志的唯一id
     * @param key      分布式锁key
     * @param value    分布式锁的value值
     * @param seconds  过期时间
     * @return true 加锁成功 false 是加锁失败
     */
    public boolean lock(final long logIndex, final String key, final String value, final long seconds) {

        String logFlag = ".lock";
        Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                String result = null;
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    result = ((JedisCluster) nativeConnection).set(key, value, "NX", "EX", seconds);
                }
                // 单机模式
                if (nativeConnection instanceof Jedis) {
                    result = ((Jedis) nativeConnection).set(key, value, "NX", "EX", seconds);
                }

                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(result)) {
                    logger.info("[logIndex:{}][logFlag:{}]获取锁{}的时间：{}",
                            logIndex, logFlag, key, System.currentTimeMillis());
                }
                return StringUtils.equals(result, okResult);
            }
        });
    }


    public boolean unLock(Long logIndex, String key, String value) {
        String logFlag = ".unlcok";
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
            args.add(value);
            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                } // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            };
            Long result = (Long) redisTemplate.execute(callback);
            return result != null && result > 0;
        } catch (Exception e) {
            logger.error("[logIndex:{}][logFlag:{}]unlock occured an exceptio",
                    logIndex, logFlag, key, e);
        } finally {

        }
        return false;
    }
}
