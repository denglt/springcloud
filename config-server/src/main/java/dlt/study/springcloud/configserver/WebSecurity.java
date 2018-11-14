package dlt.study.springcloud.configserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @Package: com.springboot.actuator
 * @Author: denglt
 * @Date: 2018/9/18 下午4:53
 * @Copyright: 版权归 HSYUNTAI 所有
 */

//@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ADMIN")//ADMIN role can access /admin/**
                //.anyRequest().authenticated()//any other request just need authentication
                .and()
                .formLogin();//enable form login

    }

    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
}
