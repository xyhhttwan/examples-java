package limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InvokeRateLimiter {
    private static Logger logger = LoggerFactory.getLogger(InvokeRateLimiter.class);
    // unit: requests/s
    private long rate = 0;

    public void setRate(long rate) {
        this.rate = rate;
    }

    private long allowance = 0;
    private long lastCheck = 0;
    public static long DEFAULT_VALUE = 1;
    private boolean isInit = false;
    private String typeName = null;

    public InvokeRateLimiter() {

    };

    public boolean isInit() {
        return isInit;
    }

    /**
     *
     * @param logIndex 日志索引
     * @param rate = 0 不限制，
     * @param typeName 类型名
     * @return
     */
    public synchronized boolean init(long logIndex, long rate, String typeName) {
        String logFlag = "RateLimiter.init";
        logger.info("[lid:{}][{}] rate:{}", logIndex, logFlag, rate);
        if (isInit()) {
            return true;
        }
        if (rate < DEFAULT_VALUE) {
            logger.error("[lid:{}][{}] init failed! rate invalid rate:{}", logIndex, logFlag, rate);
            return false;
        }
        this.rate = rate;
        this.allowance = rate;
        this.lastCheck = System.currentTimeMillis();
        this.isInit = true;
        this.typeName = typeName;
        return true;
    }

    /**
     * t
     * @param logIndex  日志索引
     * @return true 限制生效，false 限制失效
     */
    public synchronized boolean limit(long logIndex) {
        String logFlag = "RateLimiter.limit";
        if (!isInit()) {
            logger.warn("[lid:{}][{}] ratelimiter is not init!", logIndex, logFlag);
            return false;
        }
        String strFlag = String.format("%s.%s", typeName, logFlag);
        // Calculate time from last call
        long now = System.currentTimeMillis();
        long time_passed = now - lastCheck;
        lastCheck = now;
        // add to allowance
        this.allowance += (time_passed * this.rate) / 1000;
        if (this.allowance > this.rate) {
            this.allowance = this.rate;// throttle
        }

        logger.info("the allowance is {},{},{}",allowance,System.currentTimeMillis(),time_passed);

        if (this.allowance < DEFAULT_VALUE) {
            return true;
        }
        // not limit
        this.allowance--;
        return false;
    }
}