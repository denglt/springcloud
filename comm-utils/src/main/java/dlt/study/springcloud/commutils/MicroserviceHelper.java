package dlt.study.springcloud.commutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

   // @Autowired
   // @Qualifier("cloudRestTemplate")
    private RestTemplate restTemplate;

    @LoadBalanced  // 由 LoadBalancerAutoConfiguration 进行（LoadBalancerInterceptor or RetryLoadBalancerInterceptor(依赖Spring Retry) 的装载）（ restTemplate.setInterceptors ）
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
