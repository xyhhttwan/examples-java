package redis.hyperloglog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 一个简单的页面访问uv 统计
 * userId 代码访问页面的用户id
 * page 代表访问的页面
 * 
 */
@Service
public class HyperLogLog {

    @Autowired
    private RedisTemplate redisTemplate;

    private HyperLogLogOperations logOperations;

    private void setHyperLogLogOperations() {
        this.logOperations = redisTemplate.opsForHyperLogLog();
    }

    public void addUserVisit(String userId, String page) {
        setHyperLogLogOperations();
        logOperations.add(page, userId);
    }

    public long countVisit(String page) {
        setHyperLogLogOperations();
        Long counts = logOperations.size(page);
        return counts == null ? 0 : counts;
    }
}
