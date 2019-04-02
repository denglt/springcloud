package dlt.study.springcloud.userserver;

import dlt.study.springcloud.mode.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;


/**
 * Spring Cloud Stream
 * @Description:
 * @Package: dlt.study.springcloud.userserver
 * @Author: denglt
 * @Date: 2019/2/12 2:55 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@EnableBinding(Processor.class) // BinderFactoryConfiguration -> DefaultBinderFactory (使用 spring.cloud.streams.binders.*)
public class StreamConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

 //   @Autowired  // 动态获取target channel  可以放在Controller中
 //   private BinderAwareChannelResolver resolver; //  resolver.resolveDestination(target)

    //@StreamListener(Sink.INPUT)
    public void processUserError(User user) {
        logger.info("consumer ->" + user);
        if (user.getId().intValue() != 90) // (kafka) 虽然抛出了异常，但是CURRENT-OFFSET会设置为partition最后成功记录的offset+1
            throw new RuntimeException("throw error");
    }

    @StreamListener(Sink.INPUT)
    public void processUser(User user) {
       // logger.info("consumer ->" + user);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
          //  e.printStackTrace();
        }
    }

    /**
     * 自定MessageConverter
     * @return
     */
    @Bean
    @StreamMessageConverter  // @see ContentTypeConfiguration
    public MessageConverter customMessageConverter() {
        return new FastJson2MessageConverter();
    }
}


