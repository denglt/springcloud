package dlt.study.springcloud.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Package: com.yuntai.med.utils
 * @Author: denglt
 * @Date: 2018/11/19 3:58 PM
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@RestController
public class WeiBoShortUrlGenerator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("myRestTemplate")
    private RestTemplate restTemplate;

    @RequestMapping("/shortUrl")
    public String getShortUrl(String longUrl) {
        String shortUrl = longUrl;
        try {
            Map<String, Object> jsonResult = restTemplate.getForObject(url + longUrl, Map.class);
            String error_code = (String) jsonResult.get("error_code");
            if (error_code == null) {
                Map<String, Object> urlResult = (Map<String, Object>) ((List<Object>) jsonResult.get("urls")).get(0);
                shortUrl = (String) urlResult.get("url_short");
            } else {
                logger.error("转换长连接失败，结果{}", jsonResult);
            }
        } catch (HttpClientErrorException ex) {
            logger.error("转换长连接失败", ex);
        } catch (Exception ex) {
            logger.error("转换长连接失败", ex);
        }
        return shortUrl;
    }

    private String url = "http://api.weibo.com/2/short_url/shorten.json?source=2849184197&url_long="; // 211160679

    public static <T> T get(Class<T> responseType) {
        return null;
    }

    public static Map map() {
        Map map = new HashMap();
        map.put("name", "denglt");
        map.put(1, "ddd");
        return map;
    }

    public static void main2(String[] args) {
        Map map1 = new HashMap<Object, Object>();
        Map<String, Object> map2 = map1;

        Map<Object, Object> map3 = WeiBoShortUrlGenerator.map();
        map3.forEach((k, v) -> System.out.println(k + "->" + v));

        Map<String, Object> map4 = WeiBoShortUrlGenerator.map();
        map4.forEach((k, v) -> System.out.println(k + "->" + v));

    }

    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap();
        map.put("1", "denglt");
        map.put("name", "denglt");
        // error Map<String,Object> mapxxx = (Map<String,Object>) map;
        Object o = map;
        Map<String, Object> map4 = (Map<String, Object>) o;
        map4.forEach((k, v) -> System.out.println(k + "->" + v));

    }
}
