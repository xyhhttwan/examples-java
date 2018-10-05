package annotation;

import com.imzhiliao.mss.common.annotation.enums.APIEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiControl {

    //是否公开API，默认不公开
    APIEnum.ApiType apiType() default APIEnum.ApiType.PRIVATE_API;
}
