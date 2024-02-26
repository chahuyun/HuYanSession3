package cn.chahuyun.session.send;

/**
 * 用于本地化缓存的消息
 *
 * @author Moyuyanli
 * @date 2024/2/26 11:26
 */
public class LocalMessage {

    private String reply;

    public LocalMessage(String reply) {
        this.reply = reply;
    }

    public String replace() {
        return reply;
    }
}
