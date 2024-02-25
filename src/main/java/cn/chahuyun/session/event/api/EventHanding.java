package cn.chahuyun.session.event.api;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 事件处理
 *
 * @author Moyuyanli
 * @date 2024/1/15 10:51
 */
public interface EventHanding {

    /**
     * 匹配消息
     * @param messageEvent 消息事件
     */
    void messageMatching(MessageEvent messageEvent);

    /**
     * 匹配指令
     * @param messageEvent 消息事件
     */
    void commandMatching(MessageEvent messageEvent);

}
