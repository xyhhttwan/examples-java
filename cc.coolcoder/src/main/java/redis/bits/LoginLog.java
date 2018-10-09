package redis.bits;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.BitSet;
import java.util.Date;

public class LoginLog {

    @Autowired
    private RedisTemplate redisTemplate;


    public void addLoginLog(String userId) {

        String logKey = buildKey(userId);
        redisTemplate.opsForValue().setBit(logKey, Long.valueOf(DateFormatUtils.format(new Date(), "d")), true);
    }


    public Long countsActive(String userId) {
        String logKey = buildKey(userId);
        return bitCount(logKey);
    }

    public Long bitCount(final String key) {
        return (Long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(key.getBytes());
            }
        });
    }

    public String buildKey(String userId) {
        return StringUtils.join("loginLog:", DateFormatUtils.format(new Date(), "yyyy-MM"), "userId:", userId);
    }


    /**
     * java读取bytes从小到大是从右往左读(大端)，
     * 而redis存储的bytes从小到大是从左往右(小端)，
     * 因而这里读取bytes转为BitSet需要逆向一下
     *
     * @param userId
     * @return
     */
    public BitSet getLoginLog(String userId) {
        BitSet bitSet = fromByteArrayReverse(redisTemplate.opsForValue().get(buildKey(userId)).toString().getBytes());

        return bitSet;
    }

    public BitSet fromByteArrayReverse(final byte[] bytes) {
        final BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[i / 8] & (1 << (7 - (i % 8)))) != 0) {
                bits.set(i);
            }
        }
        return bits;
    }


}
