package empty.base.starter.redisson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 详细配置描述：
 * https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95
 *
 * @author yzm
 * @date 2021/9/25 - 18:09
 */
@Component
@ConfigurationProperties(prefix = "base.cache.redisson")
public class RedissonProperties {

    /**
     * platform:
     *   cache:
     *     redisson:
     *       singleServerConfig:
     *         address: ${redis.address:redis://127.0.0.1:6379}
     *         database: 0
     *       codec:
     *         class: org.redisson.codec.FstCodec
     *       threads: 0
     *       nettyThreads: 0
     *       useLinuxNativeEpoll: false
     */
    /**
     * address（节点地址）可以通过host:port的格式来指定节点地址。
     * database（数据库编号）默认值：0
     * password（密码）默认值：null 用于节点身份验证的密码。
     */
    private Map<String, Object> singleServerConfig = new HashMap<String, Object>();
    /**
     * threads（线程池数量）默认值: 当前处理核数量 * 2
     * 这个线程池数量被所有RTopic对象监听器，RRemoteService调用者和RExecutorService任务共同共享。
     */
    private Integer threads = 0;
    /**
     * nettyThreads （Netty线程池数量）默认值: 当前处理核数量 * 2
     * 这个线程池数量是在一个Redisson实例内，被其创建的所有分布式数据类型和服务，以及底层客户端所一同共享的线程池里保存的线程数量。
     */
    private Integer nettyThreads = 0;
    /**
     * 默认值: false,如果服务器的绑定地址是本地回路网络接口(loopback interface)则自动激活一个UNIX域套接字。
     * 并同时采用epoll作为传输方式。请自行添加 netty-transport-native-epoll 依赖库。
     */
    private boolean useLinuxNativeEpoll = false;
    /**
     * codec（编码）默认值: org.redisson.codec.JsonJacksonCodec
     * Redisson的对象编码类是用于将对象进行序列化和反序列化，以实现对该对象在Redis里的读取和存储。Redisson提供了以下几种的对象编码应用，以供大家选择：
     * org.redisson.codec.FstCodec	FST 10倍于JDK序列化性能而且100%兼容的编码
     */
    private Map<String, String> codec = new HashMap<>();

    public Map<String, Object> getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(Map<String, Object> singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(Integer nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public boolean isUseLinuxNativeEpoll() {
        return useLinuxNativeEpoll;
    }

    public void setUseLinuxNativeEpoll(boolean useLinuxNativeEpoll) {
        this.useLinuxNativeEpoll = useLinuxNativeEpoll;
    }

    public Map<String, String> getCodec() {
        return codec;
    }

    public void setCodec(Map<String, String> codec) {
        this.codec = codec;
    }
}
