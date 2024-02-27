package cn.chahuyun.session.send;

import net.mamoe.mirai.message.data.Image;

/**
 * 用于本地化缓存的消息
 *
 * @author Moyuyanli
 * @date 2024/2/26 11:26
 */
public class LocalMessage {

    private final Image reply;

    public LocalMessage(Image reply) {
        this.reply = reply;
    }

    public Image replace() {
        return reply;
    }
}
