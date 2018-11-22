package dlt.study.springcloud.restapi;

import com.google.common.collect.Maps;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Package: dlt.study.springcloud.restapi
 * @Author: denglt
 * @Date: 2018/11/22 5:35 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@RestController
public class UserControll {

    @RequestMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> get(@PathVariable("id") Long userId) {
        System.out.println("收到请求 -> get -> " + userId);
        Map<String, Object> user = Maps.newHashMap();
        user.put("userId", userId);
        user.put("name", "hello");
        user.put("age", 10);
        return user;
    }

}
