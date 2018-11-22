package dlt.study.springcloud.demoserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Package: dlt.study.springcloud.demoserver
 * @Author: denglt
 * @Date: 2018/11/22 8:35 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class RestTemplateConfig {

    @LoadBalanced  // do restTemplate.setInterceptors  (LoadBalancerInterceptor)
    @Primary
    @Bean("cloudRestTemplate")
    RestTemplate balancedTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

}
