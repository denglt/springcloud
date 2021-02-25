package dlt.study.service;

import dlt.study.springcloud.mode.User;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
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

@FeignClient(value = "userserver",  configuration = UserServiceFeignConfig.class ,fallbackFactory = UserServiceFallbackFactory.class)
public interface UserServiceWithFeign {

    @RequestMapping(value = "/userserver/get/{userId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Map<String, Object> get(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/userserver/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User find(/*@RequestBody*/ User user);

}

