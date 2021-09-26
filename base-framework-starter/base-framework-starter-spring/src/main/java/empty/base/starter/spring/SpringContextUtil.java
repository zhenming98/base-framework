package empty.base.starter.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author yzm
 * @date 2021/9/21 - 15:41
 */
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
}
