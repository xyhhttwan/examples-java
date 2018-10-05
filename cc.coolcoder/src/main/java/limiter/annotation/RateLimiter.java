package limiter.annotation;

import java.lang.annotation.*;

/**
 * @author zuochi
 * @version 1.0.0
 * @date 2017/11/6
 *
 * 调用频率限制器
 *
 */

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RateLimiter {
    /**
     * 速率
     * @return
     */
    long rate() default 0;

    /**
     * 类型名，用来区分业务
     * @return
     */
    String typeName();

    /**
     * 业务日志索引
     * @return
     */
    long logIndex() default -1;
}
