package dlt.study.springcloud.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

/*
org.springframework.cloud.config.server.bootstrap.ConfigServerBootstrapConfiguration
 */

@ComponentScan("dlt.study.springcloud")
@EnableAutoConfiguration  // ConfigServerAutoConfiguration
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication implements EnvironmentAware {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }


    public void setEnvironment(Environment environment) {
        this.environment = environment;
        System.out.println("Environment -> " + environment); // StandardServletEnvironment
        MutablePropertySources propSrcs = ((AbstractEnvironment) environment).getPropertySources();
        StreamSupport.stream(propSrcs.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .forEach(propName -> properties.setProperty(propName,
                        environment.getProperty(propName) == null ? "null" : environment.getProperty(propName)));

        properties.setProperty("active.profiles", Arrays.toString(environment.getActiveProfiles()));
        properties.list(System.out);

    }

    private Properties properties = new Properties();

    private Environment environment;


    /**
     * add feature
     * mappings : actuator/features  (中显示相应的信息)
     * @return
     */
    @Bean
    public HasFeatures localFeatures() {
        return HasFeatures.namedFeature("My Config Server",
                this.getClass());
    }
}
