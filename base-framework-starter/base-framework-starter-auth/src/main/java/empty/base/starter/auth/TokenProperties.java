package empty.base.starter.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yzm
 * @date 2021/9/27 - 23:06
 */
@Component
@ConfigurationProperties(prefix = "base.token")
public class TokenProperties {

    private final Long DEFAULT_TOKEN_EXPIRED_SECONDS = 60 * 60 * 24L * 7;

    private boolean enabled = false;
    private Long expiredInSeconds = DEFAULT_TOKEN_EXPIRED_SECONDS;
    private List<String> ignoreUrls = new ArrayList<>();
    private List<String> globalIgnoreUrls = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getExpiredInSeconds() {
        return expiredInSeconds;
    }

    public void setExpiredInSeconds(Long expiredInSeconds) {
        this.expiredInSeconds = expiredInSeconds;
    }

    public List<String> getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(List<String> ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    public List<String> getGlobalIgnoreUrls() {
        return globalIgnoreUrls;
    }

    public void setGlobalIgnoreUrls(List<String> globalIgnoreUrls) {
        this.globalIgnoreUrls = globalIgnoreUrls;
    }
}
