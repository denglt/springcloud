package dlt.study.service;

import com.google.common.collect.Maps;
import dlt.study.springcloud.commutils.MicroserviceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

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
    private MicroserviceHelper microserviceHelper;


    public Map<String, Object> get(Long userId) {
        return microserviceHelper.getForObject("userserver", "userserver/get/" + userId, Map.class);
    }

    public Map<String, Object> find(String name) {
        Map<String, String> params = Maps.newConcurrentMap();
        params.put("name", name);
        return microserviceHelper.postForObject("userserver", "userserver/find", params, Map.class);
    }
}
