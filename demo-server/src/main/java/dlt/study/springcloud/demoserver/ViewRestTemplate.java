package dlt.study.springcloud.demoserver;

import com.google.common.collect.Lists;
import dlt.study.springcloud.commutils.MyCreateFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Description:
 * @Package: dlt.study.springcloud.userserver
 * @Author: denglt
 * @Date: 2018/12/3 10:16 AM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Configuration
public class ViewRestTemplate {

    @LoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Lists.newArrayList();


    @MyCreateFlag
    @Autowired(required = false)
    private List<RestTemplate> restTemplates2 = Lists.newArrayList();

    @PostConstruct
    public void init() {
        System.out.println("LoadBalanced RestTemplates :");
        restTemplates.forEach(System.out::println);

        System.out.println("MyCreateFlag RestTemplates :");
        restTemplates2.forEach(System.out::println);
    }
}
