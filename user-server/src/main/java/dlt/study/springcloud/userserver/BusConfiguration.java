package dlt.study.springcloud.userserver;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Package: dlt.study.springcloud.userserver
 * @Author: denglt
 * @Date: 2019/3/26 4:10 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */
@Configuration
@RemoteApplicationEventScan(basePackages = {"dlt.study"})
public class BusConfiguration {
}
