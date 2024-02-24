package cn.chahuyun.session.data.cache;

import lombok.extern.slf4j.Slf4j;

/**
 * 缓存工厂
 *
 * <p>构建时间: 2024/2/24 13:49</p>
 *
 * @author Moyuyanli
 */
@Slf4j
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
