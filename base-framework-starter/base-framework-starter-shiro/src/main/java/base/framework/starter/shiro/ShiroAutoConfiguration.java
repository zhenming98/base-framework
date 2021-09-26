package base.framework.starter.shiro;

import base.framework.starter.shiro.realm.AbstractRealm;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

/**
 * @author yzm
 * @date 2021/9/26 - 21:21
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroAutoConfiguration.class);

    /**
     * securityManager
     */
    @Bean
    public SecurityManager securityManager(AbstractRealm abstractRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(abstractRealm);
        return securityManager;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 开启Shiro的注解
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Configuration
    @ConditionalOnMissingBean(ShiroConfigurationAdapter.class)
    public static class ShiroConfigurationAdapter {
        /**
         * Shiro 的 Web 过滤器 Factory 命名:shiroFilter
         */
        @Bean(name = "shiroFilter")
        public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroProperties shiroProperties, SecurityManager securityManager) {
            LOGGER.info("初始化 shiroFilter", ShiroFilterFactoryBean.class);
            ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
            // Shiro的核心安全接口,这个属性是必须的
            shiroFilterFactoryBean.setSecurityManager(securityManager);
            // 要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
            shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
            // 登录成功后要跳转的连接,逻辑也可以自定义，例如返回上次请求的页面
            shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());
            // 用户访问未对其授权的资源时,所显示的连接
            shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getForbiddenUrl());
            /*
             * 定义shiro过滤链 Map结构
             * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的
             * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种
             * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.
             */
            Map<String, String> filterChainDefinitionMap = shiroProperties.getFilterChainDefinition();
            if (MapUtils.isEmpty(filterChainDefinitionMap)) {
                // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
                // filterChainDefinitionMap.put(shiroProperties.getLogoutUrl(), "logout");
                filterChainDefinitionMap.put(shiroProperties.getLoginUrl(), "anon");
                filterChainDefinitionMap.put("/**", "authc");
            }
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            // 应用自定义配置
            applyShiroFilterFactoryBean(shiroFilterFactoryBean);
            return shiroFilterFactoryBean;
        }

        /**
         * 自定义ShiroFilterFactoryBean
         * 如：定义shiro过滤器,例如实现自定义的FormAuthenticationFilter，需要继承FormAuthenticationFilter
         */
        protected void applyShiroFilterFactoryBean(ShiroFilterFactoryBean shiroFilterFactoryBean) {

        }

    }

}
