package redis.bits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class UserStatus {

    private static final String key = "online";

    @Autowired
    private RedisTemplate redisTemplate;

    public void setOnlineStatus(long userId) {
        redisTemplate.opsForValue().setBit(key, userId, true);
    }

    public void setOfflineStatus(long userId) {
        redisTemplate.opsForValue().setBit(key, userId, false);
    }

    public boolean isOnLine(long userId) {
        return redisTemplate.opsForValue().getBit(key, userId);
    }
}
