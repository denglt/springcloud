package dlt.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description:
 * @Package: dlt.study.service
 * @Author: denglt
 * @Date: 2018/11/22 5:33 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Configuration
public class UserService {

    @Autowired
    private RestTemplate restTemplate;


    public Map<String,Object> get(Long userId){
        System.out.println(restTemplate);
        return restTemplate.getForObject("http://userserver/" + "userserver/" + userId, Map.class);
    }

}
