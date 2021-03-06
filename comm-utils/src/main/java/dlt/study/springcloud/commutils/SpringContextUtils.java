package dlt.study.springcloud.commutils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public final class SpringContextUtils implements ApplicationContextAware, BeanFactoryAware {

    private static ApplicationContext applicationContext;

    private static BeanFactory beanFactory;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(applicationContext);
        System.out.println(beanFactory);
        if (applicationContext instanceof ConfigurableApplicationContext)
            System.out.println(((ConfigurableApplicationContext) applicationContext).getBeanFactory());
    }

    public static void init(String[] xmls) {
        applicationContext = new ClassPathXmlApplicationContext(xmls);

        if (applicationContext instanceof ConfigurableApplicationContext) {
            ((ConfigurableApplicationContext) applicationContext).registerShutdownHook();// 调用singleton
            // bean上的相应析构回调方法，需要在JVM里注册一个“关闭钩子”
        }

    }


    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);

    }

    public static <T> T getBean(Class<T> requiredType) {
        //return (T) applicationContext.getBean(requiredType);
        return applicationContext.<T>getBean(requiredType);
    }

    /*
     * 国际化信息 通过
     * org.springframework.context.support.ResourceBundleMessageSource配置
     */
    public static String getMessage(String code, Object[] args,
                                    String defaultMessage, Locale locale) {
        return applicationContext
                .getMessage(code, args, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args, Locale locale)
            throws NoSuchMessageException {

        return applicationContext.getMessage(code, args, locale);
    }

    public static String getMessage(MessageSourceResolvable resolvable,
                                    Locale locale) throws NoSuchMessageException {

        return applicationContext.getMessage(resolvable, locale);
    }

    public static Map<String, Object> getAllBeans() {
        Map<String, Object> beans = new HashMap<>();
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            Object bean = applicationContext.getBean(name);
            beans.put(name, bean);
        }
        return beans;
    }

}
