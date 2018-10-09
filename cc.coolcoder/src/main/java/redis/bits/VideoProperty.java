package redis.bits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class VideoProperty {


    @Autowired
    private RedisTemplate redisTemplate;

    //假设 分片id =10000,key 由 视频的属性id+视频id

    //使用redis的bitmap进行存储。
    // key由属性id＋视频分片id组成。value按照视频id对分片范围取模来决定偏移量offset。10亿视频一个属性约120m还是挺划算的。

    private static final int SPLIT = 10000;

    /**
     * 存储视频的一个属性值
     *
     * @param businessId
     * @param mediaId
     */
    public void setVideoProperty(String businessId, long mediaId) {
        String key = getKey(businessId, mediaId);
        long offset = getOffset(mediaId);
        redisTemplate.opsForValue().setBit(key, offset, true);
    }

    /**
     * 获取视频的属性值
     *
     * @param businessId
     * @param mediaId
     * @return
     */
    public boolean getVideoProperty(String businessId, long mediaId) {
        String key = getKey(businessId, mediaId);
        long offset = getOffset(mediaId);
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    private long getOffset(long mediaId) {
        return mediaId % 10000;
    }

    private String getKey(String businessId, long mediaId) {
        return new StringBuffer(businessId).append(":").append(mediaId / SPLIT).toString();
    }

}
