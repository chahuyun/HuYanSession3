package cn.chahuyun.session;

import cn.chahuyun.session.config.SessionAnswerConfig;
import cn.chahuyun.session.config.SessionDataConfig;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.factory.DataFactory;
import cn.chahuyun.session.event.EventRegister;
import cn.chahuyun.session.manager.DataManager;
import cn.chahuyun.session.manager.PluginManager;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Slf4j(topic = "HuYanSession3")
public final class HuYanSession extends JavaPlugin {

    public static final HuYanSession INSTANCE = new HuYanSession();

    /**
     * 插件配置
     */
    public static SessionPluginConfig config = SessionPluginConfig.INSTANCE;


    public static final String VERSION = "1.0.0";


    private HuYanSession() {
        super(new JvmPluginDescriptionBuilder("cn.chahuyun.huyan-session-3", VERSION)
                .name("HuYanSession3")
                .dependsOn("xyz.cssxsh.mirai.plugin.mirai-hibernate-plugin", false)
                .dependsOn("cn.chahuyun.HuYanAuthorize", true)
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
        reloadPluginConfig(SessionDataConfig.INSTANCE);
        reloadPluginConfig(SessionAnswerConfig.INSTANCE);
        PluginManager.INSTANCE.configLoad(config);

        log.info("加载数据库...");
        DataManager.init(this);

        log.info("加载插件配置...");
        PluginManager.INSTANCE.pluginLoad();

        log.info("注册事件");
        EventRegister.init(this);

        List<SingleSession> singleSession = DataFactory.getInstance().getDataService().selectListEntity(SingleSession.class, "from SingleSession where trigger = '%s'", 123);
        for (SingleSession singleMessage : singleSession) {
            log.info("singleMessage->" + singleMessage);
        }

        getLogger().info("HuYanSession3 加载完成!");
    }
}