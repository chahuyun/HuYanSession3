package cn.chahuyun.session.manager;

import cn.chahuyun.api.permission.api.HuYanPermissionService;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.perm.DefaultPermissions;
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
@Slf4j
public class PluginManager {

    public static PluginManager INSTANCE = new PluginManager();

    private SessionPluginConfig config;

    private HuYanPermissionService permissionService;

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
        Optional<HuYanPermissionService> first = ServiceLoader.load(HuYanPermissionService.class).findFirst();
        switch (config.getPermType()) {
            case AUTHORIZE:
                if (first.isPresent()) {
                    permissionService = first.get();
                } else {
                    log.error("HuYanAuthor does not exist! Please check and restart after mcl!");
                    MiraiConsole.shutdown();
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

    }


    public HuYanPermissionService getPermissionService() {
        return permissionService;
    }
}
