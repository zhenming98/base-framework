package empty.base.starter.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzm
 * @date 2021/9/21 - 15:38
 */
@Configuration
public class SpringAutoConfiguration {

    /**
     * SpringContext工具类注册
     *
     * @param context
     * @return SpringContextUtil
     */
    @Bean
    @ConditionalOnClass(SpringContextUtil.class)
    public SpringContextUtil getSpringContextUtil(ApplicationContext context) {
        SpringContextUtil.setApplicationContext(context);
        return new SpringContextUtil();
    }

}
