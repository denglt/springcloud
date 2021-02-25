package dlt.study.service;

import com.google.common.collect.Maps;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import dlt.study.springcloud.mode.User;
import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import feign.hystrix.FallbackFactory;
import feign.hystrix.SetterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * like FeignClientsConfiguration(它是FeignClient的默认配置)
 * 在 @FeignClient 的configuration 中配置，将在FeignContext中被加载到spring的子容器中。
 * <p>
 * FeignContext 为每个FeignClient独立创建a Spring ApplicationContext，并做为当前容器的子容器。
 * 父容器中每个FeignClient 将有一个FeignClientSpecification对象，名称like "userserver.FeignClientSpecification" （其实跟ribbon的原理一样，RibbonClientSpecification）
 * FeignClientSpecification中将包含有 UserServiceFeignConfig.class.
 * <p>
 * FeignAutoConfiguration将自动配置FeignContext，并注入所有的FeignClientSpecification
 *      org.springframework.cloud.openfeign.FeignAutoConfiguration: {
 *          aliases: [ ],
 *          scope: "singleton",
 *          type: "org.springframework.cloud.openfeign.FeignAutoConfiguration$$EnhancerBySpringCGLIB$$9b507051",
 *          resource: null,
 *          dependencies: [
 *              "default.DemoServerApplication.FeignClientSpecification", // @EnableFeignClients.defaultConfiguration
 *              "userserver.FeignClientSpecification"
 *          ]
 *      }
 *
 * @Description:
 * @Package: dlt.study.service
 * @Author: denglt
 * @Date: 2019/1/23 11:15 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

/**
 * 自定义FeignClient的配置将覆盖FeignClientsConfiguration中的默认配置
 */
public class UserServiceFeignConfig {

    @Bean
    public UserServiceFallbackFactory getFallbackFactory() {
        return new UserServiceFallbackFactory();
    }


    @Bean
    public feign.Logger logger() {
        return new feign.Logger() {
            @Override
            protected void log(String configKey, String format, Object... args) {
                System.out.println(String.format(methodTag(configKey) + format, args));
            }
        };
    }


    /**
     * 可以用来拦截请求
     * @return
     */
    @Bean
    public RequestInterceptor headerInterceptor() {
        return requestTemplate -> {
            // 小示例，没什么卵用
            requestTemplate.header("Content-Type", "application/json");
        };
    }
    @Bean
    public feign.Logger.Level level() {
        return feign.Logger.Level.FULL;
    }

    @Bean
    public SetterFactory setterFactory() {
        return (target, method) -> {
            String groupKey = target.name();
            String commandKey = Feign.configKey(target.type(), method);
            return HystrixCommand.Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            .withExecutionTimeoutEnabled(true)
                            .withExecutionTimeoutInMilliseconds(6000)
                            .withExecutionIsolationSemaphoreMaxConcurrentRequests(10) // default 10
                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE) //为SEMAPHORE时，在调用线程执行run(),性能更好点
                    );
        };
    }

/*
    default SpringMvcContract  ( @see FeignClientsConfiguration)
    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();  // 解析@RequestLine等(Feign自己的标示)
    }
*/

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }
}

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