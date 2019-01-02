package dlt.study.service;

import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import dlt.study.springcloud.commutils.MicroserviceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

/**
 * @Description:
 * @Package: dlt.study.service
 * @Author: denglt
 * @Date: 2018/11/22 5:33 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Configuration
public class UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MicroserviceHelper microserviceHelper;

    @HystrixCommand(fallbackMethod = "fallbackForGet",
            commandProperties = {  // @see HystrixCommandProperties
                    @HystrixProperty(name = HystrixPropertiesManager.FALLBACK_ENABLED, value = "false"), // when fallback_enabled = false ,throw RuntimeException : Hystrix circuit short-circuited and is OPEN
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"), // SEMAPHORE|THREAD
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "10"),
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "true"),
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "60000"),

                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "20"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_CLOSED, value = "false"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_FORCE_OPEN, value = "false"),

                    @HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS, value = "10000"),
                    @HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_NUM_BUCKETS, value = "10")

            },
            threadPoolProperties = {  // @see HystrixThreadPoolProperties
                    @HystrixProperty(name = HystrixPropertiesManager.CORE_SIZE, value = "10"),
                    @HystrixProperty(name = HystrixPropertiesManager.MAXIMUM_SIZE, value = "10"),
                    @HystrixProperty(name = HystrixPropertiesManager.MAX_QUEUE_SIZE, value = "100")
            },
            /*           ignoreExceptions = {  //配置HystrixCommand忽略的错误，这种错误会包装为HystrixBadRequestException，不会触发熔断 //
                               HttpServerErrorException.class},*/
            raiseHystrixExceptions = {HystrixException.RUNTIME_EXCEPTION}
    )
    public Map<String, Object> get(Long userId) {
        logger.info("user get -> {}", userId);
        return microserviceHelper.getForObject("userserver", "userserver/get/" + userId, Map.class);
    }

    public Map<String, Object> find(String name) {
        Map<String, String> params = Maps.newConcurrentMap();
        params.put("name", name);
        return microserviceHelper.postForObject("userserver", "userserver/find", params, Map.class);
    }

    public Map<String, Object> fallbackForGet(Long userId) {
        logger.info("fallback for user get -> {}", userId);
        Map<String, Object> result = Maps.newHashMap();
        result.put("id", -1);
        result.put("msg", "无法获取到用户StartHdp");
        return result;
    }
}
