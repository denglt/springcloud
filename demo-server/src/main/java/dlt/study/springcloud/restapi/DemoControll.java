package dlt.study.springcloud.restapi;

import dlt.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Package: dlt.study.springcloud.restapi
 * @Author: denglt
 * @Date: 2018/11/22 5:22 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */


@RestController
public class DemoControll {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "user/id/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getUser(@PathVariable("id") Long id){
        return userService.get(id);
    }


    @RequestMapping(value = "user/name/{username}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map getUser(@PathVariable("username") String username){
        return userService.find(username);
    }



}
