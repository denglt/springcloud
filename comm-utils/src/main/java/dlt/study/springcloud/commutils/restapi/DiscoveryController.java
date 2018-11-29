package dlt.study.springcloud.commutils.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping
    @RequestMapping(value = "/{serviceId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ServiceInstance> instances(@PathVariable("serviceId") String serviceId) {

        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        instances.forEach(System.out::println);
        return instances;
/*        return instances.stream().map(t -> {
            if (t instanceof EurekaDiscoveryClient.EurekaServiceInstance) {
                 return ((EurekaDiscoveryClient.EurekaServiceInstance) t).getInstanceInfo().toString();
            } else
                return t.toString();
        }).collect(Collectors.toList());*/
    }


    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<String> services() {
        return discoveryClient.getServices();
    }

}
