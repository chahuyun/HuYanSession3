package cn.chahuyun.session.send;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 用于识别匹配动态消息
 *
 * @author Moyuyanli
 * @date 2024/2/26 11:25
 */
public class DynamicMessages {

    private String reply;

    private final MessageEvent event;

    public DynamicMessages(String reply, MessageEvent event) {
        this.reply = reply;
        this.event = event;
    }

    public String replace() {
        return reply;
    }




    public static boolean includeDynamic(String source) {
        return false;
    }

}
