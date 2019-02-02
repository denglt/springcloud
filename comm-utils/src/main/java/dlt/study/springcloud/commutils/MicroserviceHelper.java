package dlt.study.springcloud.commutils;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Package: dlt.study.springcloud.commutils.event.dlt.study.springcloud.commutils
 * @Author: denglt
 * @Date: 2018/11/28 10:33 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class MicroserviceHelper {

    private RestTemplate restTemplate;

    /**
       LoadBalanced 由 LoadBalancerAutoConfiguration 进行装配请求中断ClientHttpRequestInterceptor来实现 （ restTemplate.setInterceptors ）
       ClientHttpRequestInterceptor 有两大实现：
        1、LoadBalancerInterceptor (无spring retry)
          construct参数（LoadBalancerClient  （实现类：RibbonLoadBalancerClient,由RibbonAutoConfiguration配置完成）
               LoadBalancerRequestFactory(LoadBalancerClient loadBalancer)
               ）
        2、RetryLoadBalancerInterceptor(有spring retry)
          construct参数（
              LoadBalancerClient （实现类：RibbonLoadBalancerClient）
              LoadBalancerRetryProperties （spring.cloud.loadbalancer.retry.enabled = true）
              LoadBalancerRequestFactory
              LoadBalancedRetryFactory （实现类：RibbonLoadBalancedRetryFactory )
                 该类创建： LoadBalancedRetryPolicy
                          RetryListener  （RetryTemplate需要）
                          BackOffPolicy   RetryTemplate需要）
          ）
          依赖：
           InterceptorRetryPolicy （springretry 的 RetryPolicy）

     */
    @LoadBalanced
    @Bean("cloudRestTemplate")
    RestTemplate balancedTemplate() {
        restTemplate = new RestTemplate();
        return restTemplate;
    }


    public RestTemplate getRestTemplate() {
        return restTemplate;
    }


    public <T> T getForObject(String serviceName, String path, Class<T> resultType) {
        return restTemplate.getForObject(url(serviceName, path), resultType);
    }

    public <T> ResponseEntity<T> getForEntity(String serviceName, String path, Class<T> resultType) {
        return restTemplate.getForEntity(url(serviceName, path), resultType);
    }

    public <T> T postForObject(String serviceName, String path, Object request, Class<T> resultType) {
        return restTemplate.postForObject(url(serviceName, path), request, resultType);
    }

    public <T> ResponseEntity<T> postForEntity(String serviceName, String path, Object request, Class<T> resultType) {
        return restTemplate.postForEntity(url(serviceName, path), request, resultType);
    }

    private String url(String serviceName, String path) {
        if (!serviceName.startsWith("http")) {
            serviceName = "http://" + serviceName;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return serviceName + path;
    }

}
