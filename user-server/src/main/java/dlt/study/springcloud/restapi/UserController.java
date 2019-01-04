package dlt.study.springcloud.restapi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dlt.study.springcloud.userserver.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Package: dlt.study.springcloud.restapi
 * @Author: denglt
 * @Date: 2018/11/22 5:35 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@RestController
public class UserController {

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
    public User get(@PathVariable("id") Integer userId , HttpServletResponse response) {
        System.out.println("收到请求 -> get -> " + userId);
        response.addHeader("autoor","denglt");
        if (userId == 1)
            throw new RuntimeException("就是要报错！");
        Optional<User> user = users.stream().filter(u -> u.getId().equals(userId)).findFirst();
        return user.isPresent() ? user.get() : EMPTY;
    }

    @RequestMapping(value = "/find", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User find(@RequestBody User dto) {
        System.out.println("收到请求 -> find ->  " + dto.getName());
        Optional<User> user = users.stream().filter(u -> u.getName().equals(dto.getName())).findFirst();
        return user.isPresent() ? user.get() : EMPTY;
    }

    @RequestMapping(value = "/new/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> newUser(@PathVariable("id") Integer userId) {
        System.out.println("收到请求 -> get -> " + userId);
        Map<String, Object> user = Maps.newHashMap();
        user.put("userId", userId);
        user.put("name", "hello");
        user.put("age", 10);
        return user;
    }

}
