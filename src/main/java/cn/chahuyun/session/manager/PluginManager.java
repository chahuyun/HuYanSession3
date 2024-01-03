package cn.chahuyun.session.manager;

import cn.chahuyun.authorize.HuYanAuthorize;
import cn.chahuyun.session.config.PluginConfig;
import cn.chahuyun.session.enums.PermType;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMemberEvent;

/**
 * 插件管理
 *
 * @author Moyuyanli
 * @date 2023/12/29 10:30
 */
@Slf4j
public class PluginManager {

    public static PluginManager INSTANCE = new PluginManager();

    private PluginConfig config;


    private PluginManager() {

    }

    /**
     * 是否接入壶言授权
     */
    public  boolean authorizeAccess = false;

    /**
     * 配置加载
     */
    public  void configLoad(PluginConfig config) {
        this.config = config;
    }

    /**
     * 加载插件的基础功能
     */
    public  void pluginLoad() {
        try {
            HuYanAuthorize authorize = HuYanAuthorize.INSTANCE;
        } catch (Exception e) {

        }
    }



}
