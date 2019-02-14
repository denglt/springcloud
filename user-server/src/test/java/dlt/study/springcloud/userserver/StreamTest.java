package dlt.study.springcloud.userserver;

import dlt.study.springcloud.mode.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

/**
 * @Description:
 * @Package: dlt.study.springcloud.userserver
 * @Author: denglt
 * @Date: 2019/2/12 4:44 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamTest {
    @Autowired
    private Sink sink;

    @Autowired
    private Source source;

    @Autowired
    private ApplicationContext applicationContext;

    static {
        System.setProperty("profile", "prod,insecure,eureka-secure");
    }


    @Test
    public void contextLoads() {
        BindingServiceProperties bean = applicationContext.getBean(BindingServiceProperties.class);
        System.out.println(bean);
    }


    @Test
    public void subscribableChannel() throws InterruptedException {
        System.out.println("SubscribableChannel  -> " + sink.input());
        Thread.currentThread().join();
    }

    @Test
    public void outputUser() {
        for (int i = 0; i < 100 ; i++) {
            User user = new User();
            user.setId(i);
            user.setName("admin");
            user.setPasswword("admin");
            user.setRole("ADMIN");
            source.output().send(MessageBuilder
                    .withPayload(user)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    //.setHeader(MessageHeaders.ID)
                    .build());
        }

    }
}
