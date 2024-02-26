package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.Permission;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;
import cn.chahuyun.session.data.factory.AbstractDataService;
import cn.chahuyun.session.data.factory.DataFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 缓存工厂
 *
 * <p>构建时间: 2024/2/24 13:49</p>
 *
 * @author Moyuyanli
 */
@Slf4j(topic = "HuYanSession3")
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
        AbstractDataService dataService = DataFactory.getInstance().getDataService();

        List<SingleSession> singleSessions = dataService.selectListEntity(SingleSession.class, "from SingleSession");
        List<ManySession> manySessions = dataService.selectListEntity(ManySession.class, "from ManySession");
        List<TimingSession> timingSessions = dataService.selectListEntity(TimingSession.class, "from TimingSession");
        List<Permission> permissions = dataService.selectListEntity(Permission.class, "from Permission");

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
