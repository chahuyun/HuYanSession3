package cn.chahuyun.session;

import cn.chahuyun.session.config.DataConfig;
import cn.chahuyun.session.config.PluginConfig;
import cn.chahuyun.session.data.DataFactory;
import cn.chahuyun.session.manager.DataManager;
import cn.chahuyun.session.manager.PluginManager;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import org.jetbrains.annotations.NotNull;

@Slf4j
public final class HuYanSession extends JavaPlugin {

    public static final HuYanSession INSTANCE = new HuYanSession();

    /**
     * 插件配置
     */
    public static PluginConfig config = PluginConfig.INSTANCE;


    private HuYanSession() {
        super(new JvmPluginDescriptionBuilder("cn.chahuyun.huyan-session-3", "0.0.1")
                .name("HuYanSession")
                .dependsOn("xyz.cssxsh.mirai.plugin.mirai-hibernate-plugin",false)
                .dependsOn("cn.chahuyun.HuYanAuthorize",true)
                .info("屎山3")
                .author("Moyuyanli")
                .build());
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        log.info("HuYanSession3 开始加载!");
        super.onLoad($this$onLoad);
    }

    @Override
    public void onEnable() {
        log.info("加载配置文件...");
        reloadPluginConfig(config);
        reloadPluginConfig(DataConfig.INSTANCE);
        PluginManager.INSTANCE.configLoad(config);

        log.info("加载数据库...");
        DataManager.init(this);


        getLogger().info("Plugin loaded!");
    }
}