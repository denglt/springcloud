package dlt.study.springcloud.commutils.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class LogApplicationEvent implements ApplicationListener<ApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LogApplicationEvent.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
       // logger.info("ApplicationEvent -> " + event.toString());
        if (event instanceof ContextRefreshedEvent) {
            logger.info("spring容器初始化完毕================================================888");
        }
    }
}
