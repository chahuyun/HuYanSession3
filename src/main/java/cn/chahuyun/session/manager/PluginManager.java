package cn.chahuyun.session.manager;

import cn.chahuyun.authorize.HuYanAuthorize;
import cn.chahuyun.session.config.PluginConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 插件管理
 *
 * @author Moyuyanli
 * @date 2023/12/29 10:30
 */
@Slf4j
public class PluginManager {

    public static PluginManager INSTANCE = new PluginManager();
    /**
     * 是否接入壶言授权
     */
    public boolean authorizeAccess = false;

    private PluginConfig config;

    private PluginManager() {

    }

    /**
     * 配置加载
     */
    public void configLoad(PluginConfig config) {
        this.config = config;
    }

    /**
     * 加载插件的基础功能
     */
    public void pluginLoad() {
        try {
            HuYanAuthorize authorize = HuYanAuthorize.INSTANCE;
        } catch (Exception e) {

        }
    }


}
