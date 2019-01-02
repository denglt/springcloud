package dlt.study.springcloud.zuulserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 1、 通过 ZuulServerAutoConfiguration 配置zuul
 *    配置 ZuulProperties
 *    1.1  create RouteLocator
 *    1.2  create ZuulController  (符合SimpleControllerHandlerAdapter要求)
 *    1.3  create ZuulHandlerMapping  (该 Mapping 将会被DispatcherServlet 处理)
 *
 *  2、ZuulProxyAutoConfiguration
 */
//@EnableZuulServer
@EnableZuulProxy  // 包含@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }

}

