package cn.chahuyun.session;

import cn.chahuyun.session.command.SessionCommand;
import cn.chahuyun.session.config.SessionAnswerConfig;
import cn.chahuyun.session.config.SessionDataConfig;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.factory.DataFactory;
import cn.chahuyun.session.event.EventRegister;
import cn.chahuyun.session.manager.DataManager;
import cn.chahuyun.session.manager.PluginManager;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Slf4j(topic = Constant.LOG_TOPIC)
public final class HuYanSession extends JavaPlugin {

    public static final HuYanSession INSTANCE = new HuYanSession();
    public static final String VERSION = "1.0.0";
    /**
     * 插件配置
     */
    public static SessionPluginConfig pluginConfig = SessionPluginConfig.INSTANCE;
    /**
     * 数据配置
     */
    public static SessionDataConfig dataConfig = SessionDataConfig.INSTANCE;
    /**
     * 回答的配置
     */
    public static SessionAnswerConfig answerConfig = SessionAnswerConfig.INSTANCE;


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
        reloadPluginConfig(pluginConfig);
        reloadPluginConfig(dataConfig);
        reloadPluginConfig(answerConfig);
        PluginManager.INSTANCE.configLoad(pluginConfig);

        log.info("注册指令...");
        CommandManager.INSTANCE.registerCommand(new SessionCommand(), false);

        log.info("加载数据库...");
        DataManager.init(this);

        log.info("加载插件配置...");
        PluginManager.INSTANCE.pluginLoad();

        log.info("注册事件");
        EventRegister.init(this);

        log.info("HuYanSession3 加载完成!");
    }
}