package dlt.study.springcloud.restapi;

import dlt.study.springcloud.mode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Package: dlt.study.springcloud.restapi
 * @Author: denglt
 * @Date: 2019/3/22 2:20 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@RestController
public class StreamController {

    @Autowired
    @Qualifier("output")
    private MessageChannel messageChannel;

    @Autowired
    @Qualifier("input")
    private MessageChannel messageChannel2;

    @Autowired// 动态获取target channel
    private BinderAwareChannelResolver resolver; //  resolver.resolveDestination(target)


    @Autowired
    private UserController userController;

    @RequestMapping(value = "/send/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void sendUser(@PathVariable("id") Integer userId, HttpServletResponse response) {
        User user = userController.get(userId, response);
        //
        System.out.println("send user");
        messageChannel.send(MessageBuilder
                .withPayload(user)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());

//    input 发送的message，没有被 FastJson2MessageConverter 进行消息转换 ，？？？？
//      原因并没有真的向broker发送Message(因为我在kafka中没有看到data)，而是把Message直接投递了 @StreamListener(Sink.INPUT)的方法中
       User user2 = userController.get(userId + 1, response);
        System.out.println("send2 user2");
        messageChannel2.send(MessageBuilder
                .withPayload(user2)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());

       //  resolver.resolveDestination("input");
    }
}
