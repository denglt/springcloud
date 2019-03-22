package dlt.study.springcloud.userserver;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import java.nio.charset.StandardCharsets;

/**
 * Spring Cloud Stream
 * @Description:
 * @Package:
 * @Author: denglt
 * @Date: 2019/3/19 4:58 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

/**
 *
 * @see ContentTypeConfiguration (收集所有 MessageConverter (用@StreamMessageConverter @Bean) )
 *   ContentTypeConfiguration
 *      -- CompositeMessageConverterFactory （spring cloud stream）
 *      -- ConfigurableCompositeMessageConverter
 *
 * @see MessageConverterConfigurer
 *
 *  1、所有的MessageConverter存放在一个List<MessageConverter>
 *  2、Message 或 Object 被 List<MessageConverter> 中的MessageConverter一个一个进行处理：
 *      如果MessageConverter.fromMessage或toMessage 返回 null，这剩下的MessageConverter 接这处理，否则返回处理后的Object
 *
 *   OutboundContentTypeConvertingInterceptor extends AbstractContentTypeInterceptor
 *       中加入 CompositeMessageConverter
 *
 *
 */
public class FastJson2MessageConverter extends AbstractMessageConverter {
    Logger logger = LoggerFactory.getLogger(FastJson2MessageConverter.class);

    public FastJson2MessageConverter() {
        super(new MimeType("application", "json", StandardCharsets.UTF_8));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        logger.info("convertFromInternal -> {}",targetClass);
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            String josnStr = new String((byte[]) payload, StandardCharsets.UTF_8);
            return  JSON.parseObject(josnStr, targetClass);
        } else {
            return JSON.parseObject(payload.toString(),targetClass);
        }
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        logger.info("convertToInternal -> {}" , payload);
        String jsonStr = JSON.toJSONString(payload);
        return jsonStr.getBytes(StandardCharsets.UTF_8);
    }
}
