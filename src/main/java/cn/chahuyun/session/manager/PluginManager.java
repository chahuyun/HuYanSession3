package cn.chahuyun.session.manager;

import cn.chahuyun.api.permission.api.HuYanPermissionService;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.cache.Cache;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.cache.MemoryCache;
import cn.chahuyun.session.perm.DefaultPermissions;
import cn.chahuyun.session.perm.PermissionsServiceFactory;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.MiraiConsole;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * 插件管理
 *
 * @author Moyuyanli
 * @date 2023/12/29 10:30
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class PluginManager {

    public static PluginManager INSTANCE = new PluginManager();

    private SessionPluginConfig config;
    /**
     * 是否接入壶言授权
     */
    private boolean isAuthorize;

    private PluginManager() {
    }

    /**
     * 配置加载
     */
    public void configLoad(SessionPluginConfig config) {
        this.config = config;
    }

    /**
     * 加载插件的基础功能
     */
    public void pluginLoad() {

        //加载权限
        HuYanPermissionService permissionService;
        Optional<HuYanPermissionService> first = ServiceLoader.load(HuYanPermissionService.class).findFirst();
        switch (config.getPermType()) {
            case AUTHORIZE:
                if (first.isPresent()) {
                    permissionService = first.get();
                } else {
                    log.error("HuYanAuthorize does not exist! Please check and restart after mcl!");
                    MiraiConsole.shutdown();
                    return;
                }
                break;
            case INTERIOR:
                permissionService = new DefaultPermissions();
                break;
            case DEFAULT:
            default:
                permissionService = first.orElseGet(DefaultPermissions::new);
                break;
        }
        isAuthorize = !(permissionService instanceof DefaultPermissions);
        PermissionsServiceFactory.init(permissionService);


        //加载缓存服务
        Cache cache;
        switch (config.getCacheType()) {
            case REDIS:
                log.warn("暂不支持redis缓存，将使用默认内存缓存");
            case MEMORY:
            default:
                cache = new MemoryCache().init();
        }
        CacheFactory.init(cache);

    }

    public boolean isAuthorize() {
        return isAuthorize;
    }
}
