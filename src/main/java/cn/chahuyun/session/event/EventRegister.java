package cn.chahuyun.session.event;

import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.exception.ExceptionHandle;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;

/**
 * 事件注册
 *
 * @author Moyuyanli
 * @date 2024/1/15 11:07
 */
public class EventRegister {

    static EventChannel<Event> globalEvent;

    /**
     * 加载全局事件注册器，指定父作用域，指定异常处理器
     * @param plugin 插件本身
     */
    public static void init(HuYanSession plugin) {
        globalEvent = GlobalEventChannel.INSTANCE
                .parentScope(plugin)
                .exceptionHandler(new ExceptionHandle());
    }








}
