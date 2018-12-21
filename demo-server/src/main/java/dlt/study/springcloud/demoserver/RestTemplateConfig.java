package dlt.study.springcloud.demoserver;

import com.google.common.collect.Maps;
import dlt.study.springcloud.commutils.MyCreateFlag;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Package: dlt.study.springcloud.demoserver
 * @Author: denglt
 * @Date: 2018/11/22 8:35 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class RestTemplateConfig implements ApplicationContextAware, SmartInitializingSingleton {

    @MyCreateFlag
    @Bean("myRestTemplate")
    RestTemplate restTemplate(RestTemplateBuilder builder) {
         return builder.build();
       // return new RestTemplate(); //这个RestTemplate无法被 RestTemplateCustomizer感知(@see RestTemplateAutoConfiguration)
    }

    @Bean("emptyRestTemplateCustomizer")
    @ConditionalOnMissingBean(value = RestTemplateCustomizer.class)
    RestTemplateCustomizer restTemplateCustomizer() {
        //System.out.println("restTemplateCustomizer");
        return (t -> System.out.println("restTemplateCustomizer -> " + t));
    }

    @Bean("emptyRestTemplateCustomizer2")
    @ConditionalOnMissingBean(/*value = RestTemplateCustomizer.class*/)
    RestTemplateCustomizer restTemplateCustomizer2() {
        System.out.println("restTemplateCustomizer2");
        return (System.out::println);
    }


    @Override
    public void afterSingletonsInstantiated() {
        applicationContext.getBeansOfType(RestTemplate.class).forEach((k, v) -> {
            System.out.println(k + " -> " + v);
            restTemplateMap.put(k,v);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private ApplicationContext applicationContext;

    private Map<String, RestTemplate> restTemplateMap = Maps.newConcurrentMap();
}
