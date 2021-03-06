package dlt.study.springcloud.zuulserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * 1、 通过 ZuulServerAutoConfiguration 配置zuul
 *      配置 ZuulProperties
 *    1.1  create RouteLocator （SimpleRouteLocator）
 *    1.2  create ZuulController
 *            该类符合SimpleControllerHandlerAdapter要求
 *            内部会创建一个ZuulServlet
 *            ZuulController的处理请求实际会转到ZuulServlet执行
 *    1.3  create ZuulHandlerMapping  (该 HandlerMapping 将会被 DispatcherServlet 处理)
 *            自动注入（SimpleRouteLocator，ZuulController）
 *
 *    1.4 create ZuulServlet (通过 ServletRegistrationBean 加入WebServer ， 这儿的ZuulServer 与ZuulController中的是两个不同的实例)
 *     配置 Filter （Filter处理器：FilterProcessor ）
 *       pre filters: ServletDetectionFilter\FormBodyWrapperFilter\DebugFilter
 *       Post filters: SendResponseFilter (返回代理的Response。 writes responses from proxied requests to the current response)
 *       route filters: SendForwardFilter
 *       error filters: SendErrorFilter
 *
 *  2、ZuulProxyAutoConfiguration
 *      1) DiscoveryClientRouteLocator
 *      2) 配置 proxy filters :
 *          pre filters: PreDecorationFilter (在RequestContext中设置proxy相关信息（proxy、serviceId等等）)
 *          route filters: RibbonRoutingFilter、SimpleHostRoutingFilter
 *
 */
//@EnableZuulServer // no service discovery and no proxying
@EnableZuulProxy  // 包含@EnableCircuitBreaker
@EnableDiscoveryClient
@ComponentScan("dlt.study.springcloud")
@SpringBootApplication
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }

}

