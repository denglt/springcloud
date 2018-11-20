package dlt.study.springcloud.demoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * org.springframework.cloud.config.client.ConfigServiceBootstrapConfiguration
 *
 */

@Configuration
@EnableDiscoveryClient
//@EnableEurekaClient
@ComponentScan(value = {"dlt.study.springcloud"})
@EnableAutoConfiguration  // ConfigClientAutoConfiguration
@SpringBootApplication
public class DemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServerApplication.class, args);
    }
}
