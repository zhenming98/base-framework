package empty.base.starter.auth;

import empty.base.starter.auth.session.token.RedisTokenRepository;
import empty.base.starter.auth.session.token.TokenRepository;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * @author yzm
 * @date 2021/9/27 - 23:01
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
@ConditionalOnProperty(havingValue = "true", name = "base.token.enabled")
public class TokenAutoConfiguration {

    @Resource
    TokenProperties tokenProperties;

    @Bean
    public TokenRepository tokenRepository(RedissonClient redissonClient) {
        return new RedisTokenRepository(redissonClient, tokenProperties);
    }

    @Bean
    public TokenUtil tokenUtil(TokenRepository tokenRepository) {
        TokenUtil.setTokenRepository(tokenRepository);
        return new TokenUtil();
    }

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    @Bean
    @Order()
    public FilterRegistrationBean<TokenFilter> someFilterRegistration() {
        FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(tokenFilter());
        //过滤所有路径
        registration.addUrlPatterns("/*");
        registration.setName("tokenFilter");
        return registration;
    }
}
