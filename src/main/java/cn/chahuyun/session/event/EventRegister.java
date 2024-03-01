package cn.chahuyun.session.event;

import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.enums.PermType;
import cn.chahuyun.session.exception.ExceptionHandle;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;

/**
 * 事件注册
 *
 * @author Moyuyanli
 * @date 2024/1/15 11:07
 */
@Slf4j(topic = "HuYanSession3")
public class EventRegister {

    static EventChannel<Event> globalEvent;

    static HuYanSession huYanSession;


    /**
     * 加载全局事件注册器，指定父作用域，指定异常处理器
     *
     * @param plugin 插件本身
     */
    public static void init(HuYanSession plugin) {
        huYanSession = plugin;
        globalEvent = GlobalEventChannel.INSTANCE
                .parentScope(plugin)
                .exceptionHandler(new ExceptionHandle());
        if (HuYanSession.pluginConfig.getPermType() == PermType.AUTHORIZE) {
            log.warn("暂未实现接入Authorize");
        } else {
            globalEvent.registerListenerHost(new EventServices((CoroutineContext) new ExceptionHandle()));
        }
    }


}
