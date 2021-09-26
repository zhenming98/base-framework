package empty.base.starter.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yzm
 * @date 2021/9/21 - 15:48
 */
@Configuration
public class ExceptionAutoConfiguration {

    @Bean
    public DefaultRestErrorResolver createRestErrorResolver() {
        return new DefaultRestErrorResolver();
    }

}
