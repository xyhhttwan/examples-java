package limiter.aspect;
import com.google.common.collect.Maps;

import limiter.InvokeRateLimiter;
import limiter.annotation.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;


@Aspect
@Component
public class RateLimiterAspect {

    private final Map<String,InvokeRateLimiter> invokeRateLimiterMap = Maps.newConcurrentMap();

    @Around("within(cc.coolcoder..*) && @annotation(rateLimiter)")
    public Object limit(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        // 在调用实际方法前调用
        if(limit(rateLimiter)) {
            throw new RuntimeException("");
        }

        return joinPoint.proceed();

    }

    /**
     * 接口限制器
     * @param rateLimiter
     * @return
     */
    public boolean limit(RateLimiter rateLimiter){

        InvokeRateLimiter invokeRateLimiter = null;
        if(!invokeRateLimiterMap.containsKey(rateLimiter.typeName())) {
            invokeRateLimiter = new InvokeRateLimiter();
            invokeRateLimiter.init(rateLimiter.logIndex(),rateLimiter.rate(),rateLimiter.typeName());
            invokeRateLimiterMap.put(rateLimiter.typeName(),invokeRateLimiter);
        }else {
            invokeRateLimiter = invokeRateLimiterMap.get(rateLimiter.typeName());
        }

        return invokeRateLimiter.limit(rateLimiter.logIndex());
    }

    /**
     * 一个typeName作为一个资源限制器
     * @param rateLimiter
     *
     * todo 和spring做集成效果更好，就不必在这里添加，spring启动的时候根据注解初始化
     */
    public void addLimiter(RateLimiter rateLimiter) {
        if(!invokeRateLimiterMap.containsKey(rateLimiter.typeName())) {
            InvokeRateLimiter invokeRateLimiter = new InvokeRateLimiter();
            invokeRateLimiter.init(rateLimiter.logIndex(),rateLimiter.rate(),rateLimiter.typeName());
            invokeRateLimiterMap.put(rateLimiter.typeName(),invokeRateLimiter);
        }
    }
}
