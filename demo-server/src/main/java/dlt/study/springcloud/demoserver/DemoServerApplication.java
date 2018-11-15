package dlt.study.springcloud.demoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * org.springframework.cloud.config.client.ConfigServiceBootstrapConfiguration
 *
 */
@ComponentScan("dlt.study.springcloud")
@EnableAutoConfiguration  // ConfigClientAutoConfiguration
@SpringBootApplication
public class DemoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServerApplication.class, args);
    }
}
