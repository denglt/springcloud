package dlt.study.springcloud.commutils;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * @Description:
 * @Package: dlt.study.springcloud.commutils
 * @Author: denglt
 * @Date: 2018/12/3 11:08 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface MyCreateFlag {
}