package redis.bits;

import com.google.common.base.Strings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;

public class SimpleRateLimiter {

    private Jedis jedis;

    public SimpleRateLimiter(Jedis jedis) {
        this.jedis = jedis;
    }

    public boolean isActionAllowd(String userId, String actionKey, int period, int maxCount) throws IOException {
        //构建key
        String key = String.format("hist:%s:s%", userId, actionKey);
        //获取当前时间
        long nowTs = System.currentTimeMillis();

        Pipeline pipe = jedis.pipelined();
        pipe.multi();
        // 记录行为
        // value 和 score 都使用毫秒时间戳
        pipe.zadd(key, nowTs, "" + nowTs);
        //period =5
        // 0<=time <=(nowTs - period * 1000) 移除
        //移除时间窗口之前的行为记录，剩下的都是时间窗口内的
        pipe.zremrangeByScore(key, 0, nowTs - period * 1000);
        // 获取窗口内的行为数量
        pipe.zcard(key);
        // 设置 zset 过期时间，避免冷用户持续占用内存
        // 过期时间应该等于时间窗口的长度，再多宽限 1s
        pipe.expire(key, period + 1);
        // 批量执行...
        Response<Long> count = pipe.zcard(key);
        pipe.exec();
        pipe.close();
        return count.get() <= maxCount;

    }
}
