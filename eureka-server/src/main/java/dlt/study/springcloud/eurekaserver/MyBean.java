package dlt.study.springcloud.eurekaserver;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Package: com.study.springcloud.eurekaserver
 * @Author: denglt
 * @Date: 2019/1/31 6:29 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Component
public class MyBean {

    public MyBean(){
        System.out.println("new MyBean");
    }
}
