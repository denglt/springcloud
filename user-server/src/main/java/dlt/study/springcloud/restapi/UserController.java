package dlt.study.springcloud.restapi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dlt.study.springcloud.mode.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description:
 * @Package: dlt.study.springcloud.restapi
 * @Author: denglt
 * @Date: 2018/11/22 5:35 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private User EMPTY = new User();
    private List<User> users = Lists.newArrayList();

    {
        User user = new User();
        user.setId(1);
        user.setName("admin");
        user.setPasswword("admin");
        user.setRole("ADMIN");
        users.add(user);

        user = new User();
        user.setId(2);
        user.setName("denglt");
        user.setPasswword("denglt");
        user.setRole("ADMIN");
        users.add(user);

        user = new User();
        user.setId(3);
        user.setName("张三");
        user.setPasswword("123456");
        user.setRole("user");
        users.add(user);
    }

    @RequestMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(@PathVariable("id") Integer userId, HttpServletResponse response) {
        logger.info("UserController.get 收到请求 -> get -> " + userId);
        response.addHeader("autoor", "denglt");
        if (userId == 1) {
           // logger.error("就是要报错");
            throw new RuntimeException("就是要报错！");
        }
        Optional<User> user = users.stream().filter(u -> u.getId().equals(userId)).findFirst();
        return user.isPresent() ? user.get() : EMPTY;
    }

    @RequestMapping(value = "/find", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User find(@RequestBody User dto) {
        logger.info("UserController.find 收到请求 -> find ->  " + dto.getName());
        Optional<User> user = users.stream().filter(u -> u.getName().equals(dto.getName())).findFirst();
        return user.isPresent() ? user.get() : EMPTY;
    }

    @RequestMapping(value = "/new/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> newUser(@PathVariable("id") Integer userId) {
        logger.info("UserController.newUser 收到请求 -> get -> " + userId);
        Map<String, Object> user = Maps.newHashMap();
        user.put("userId", userId);
        user.put("name", "hello");
        user.put("age", 10);
        return user;
    }


    @RequestMapping(value = "/getnolog/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getnolog(@PathVariable("id") Integer userId) {
        Optional<User> user = users.stream().filter(u -> u.getId().equals(userId)).findFirst();
        return user.isPresent() ? user.get() : EMPTY;
    }

}
