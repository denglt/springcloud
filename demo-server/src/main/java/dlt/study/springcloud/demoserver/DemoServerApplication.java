package dlt.study.springcloud.demoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * org.springframework.cloud.config.client.ConfigServiceBootstrapConfiguration
 */

@EnableFeignClients("dlt.study.service")
@Configuration
@EnableDiscoveryClient  // 配置 DiscoveryClient
//@EnableEurekaClient  // 配置 EurekaDiscoveryClient
@EnableHystrix   // @EnableCircuitBreaker
@EnableHystrixDashboard // Hystrix 监控
@ComponentScan(value = {"dlt.study.springcloud", "dlt.study.service"})
@EnableAutoConfiguration  // ConfigClientAutoConfiguration
@SpringBootApplication
public class DemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServerApplication.class, args);
    }
}
