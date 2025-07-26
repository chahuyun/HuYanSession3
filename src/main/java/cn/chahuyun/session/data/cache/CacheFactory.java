package cn.chahuyun.session.data.cache;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.Permission;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 缓存工厂
 *
 * <p>构建时间: 2024/2/24 13:49</p>
 *
 * @author Moyuyanli
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class CacheFactory {

    private static CacheFactory instance;

    private final Cache cacheService;

    public CacheFactory(Cache cacheService) {
        this.cacheService = cacheService;
    }


    /**
     * 加载缓存工厂
     *
     * @param cache 缓存服务
     */
    public static void init(Cache cache) {
        if (cache == null) {
            throw new RuntimeException("缓存工厂初始化失败:缓存服务为空!");
        }
        instance = new CacheFactory(cache);

        List<SingleSession> singleSessions = HibernateFactory.selectList(SingleSession.class);
        List<ManySession> manySessions = HibernateFactory.selectList(ManySession.class);
        List<TimingSession> timingSessions = HibernateFactory.selectList(TimingSession.class);
        List<Permission> permissions = HibernateFactory.selectList(Permission.class);

        singleSessions.forEach(it -> instance.cacheService.putSession(it));
        manySessions.forEach(it -> instance.cacheService.putSession(it));
        timingSessions.forEach(it -> instance.cacheService.putSession(it));
        permissions.forEach(it -> instance.cacheService.putPermission(it));

        log.debug("缓存服务初始化完成!");
    }

    /**
     * 获取缓存工厂实例
     *
     * @return 缓存工厂
     */
    public static CacheFactory getInstall() {
        if (instance == null) {
            throw new RuntimeException("缓存工厂获取失败:未初始化缓存工厂!");
        }
        return instance;
    }


    public Cache getCacheService() {
        return cacheService;
    }
}
