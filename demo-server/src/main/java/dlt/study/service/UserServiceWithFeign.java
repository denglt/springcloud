package dlt.study.service;

import com.google.common.collect.Maps;
import dlt.study.springcloud.mode.User;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 *  @see FeignAutoConfiguration  FeignRibbonClientAutoConfiguration
 *
 * @Description:
 * @Package: dlt.study.service
 * @Author: denglt
 * @Date: 2019/1/21 2:23 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 *
 */

@FeignClient(value = "userserver", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserServiceWithFeign {

    @RequestMapping(value = "/userserver/get/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Map<String, Object> get(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/userserver/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User find(/*@RequestBody*/ User user);

    @Bean
    default UserServiceFallbackFactory getFallbackFactory() {
        return new UserServiceFallbackFactory();
    }

}

@Component
class UserServiceFallbackFactory implements FallbackFactory<UserServiceWithFeign> {
    private Logger logger = LoggerFactory.getLogger(UserServiceFallbackFactory.class);

    @Override
    public UserServiceWithFeign create(Throwable throwable) {
        logger.error("error ", throwable);
        return new UserServiceWithFeign() {
            @Override
            public Map<String, Object> get(Long userId) {
                logger.info("fallback for user get -> {}", userId);
                Map<String, Object> result = Maps.newHashMap();
                result.put("id", -1);
                result.put("msg", "无法获取到用户!");
                return result;
            }

            @Override
            public User find(User user) {
                throw new RuntimeException(throwable);
            }
        };
    }
}