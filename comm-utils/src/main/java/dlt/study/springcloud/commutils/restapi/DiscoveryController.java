package dlt.study.springcloud.commutils.restapi;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Package: dlt.study.springcloud.restapi
 * @Author: denglt
 * @Date: 2018/11/16 2:36 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@RestController
@RequestMapping("/service")
public class DiscoveryController {

    @Resource
    private DiscoveryClient discoveryClient; // ConsulDiscoveryClient, EurekaDiscoveryClient

    /**
     * 以Ribbon分析:RibbonLoadBalancerClient (RibbonAutoConfiguration)
     *  1、通过自动RibbonConsulAutoConfiguration中的@RibbonClients(defaultConfiguration = ConsulRibbonClientConfiguration.class) 在容器中注入RibbonClientSpecification
     *  2、通过自动RibbonAutoConfiguration
     *     （1）创建SpringClientFactory（默认配置RibbonClientConfiguration）实例，并注入容器中的所有RibbonClientSpecification（包含相关注册中心与Ribbon的相关配置）
     *     （2）创建RibbonLoadBalancerClient，并注入（1）中的SpringClientFactory
     *           在SpringClientFactory中通过RibbonClientSpecification指定的配置类和RibbonClientConfiguration来为每个ServiceId创建一个独立的Ribbon子容器（类似Feign），
     *           这样注册中心（如Consul）与 Ribbon进行了整合 ，RibbonLoadBalancerClient就能通过Consul来获取Service的相关配置信息。
     */

    @Resource
    private LoadBalancerClient loadBalancerClient; //

    @GetMapping(value = "/{serviceId}")
    public List<ServiceInstance> instances(@PathVariable("serviceId") String serviceId) {

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        instances.forEach(System.out::println);
        return instances;
    }

    @GetMapping(value = "/all")
    public List<String> services() {
        return discoveryClient.getServices();
    }

    @GetMapping(value = "/choose/{serviceId}")
    public ServiceInstance choose(@PathVariable("serviceId") String serviceId) {
        return loadBalancerClient.choose(serviceId);
    }
}
