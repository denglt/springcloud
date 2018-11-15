package dlt.study.springcloud.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

/*
org.springframework.cloud.config.server.bootstrap.ConfigServerBootstrapConfiguration
 */

@ComponentScan("dlt.study.springcloud")
@EnableAutoConfiguration  // ConfigServerAutoConfiguration
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
