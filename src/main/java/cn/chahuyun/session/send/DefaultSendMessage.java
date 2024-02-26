package cn.chahuyun.session.send;

import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;
import cn.chahuyun.session.enums.SendType;
import cn.chahuyun.session.send.api.SendMessage;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * 发送消息
 *
 * @author Moyuyanli
 * @date 2024/2/26 11:24
 */
public class DefaultSendMessage  implements SendMessage {

    private SingleSession singleSession;

    private ManySession manySession;

    private TimingSession timingSession;

    private MessageEvent messageEvent;

    private SendType sendType;

    public DefaultSendMessage(SingleSession singleSession, MessageEvent messageEvent) {
        this.singleSession = singleSession;
        this.messageEvent = messageEvent;
        this.sendType = SendType.SING;
    }

    public DefaultSendMessage(ManySession manySession, MessageEvent messageEvent) {
        this.manySession = manySession;
        this.messageEvent = messageEvent;
        this.sendType = SendType.MANY;
    }

    public DefaultSendMessage(TimingSession timingSession, MessageEvent messageEvent) {
        this.timingSession = timingSession;
        this.messageEvent = messageEvent;
        this.sendType = SendType.TIMING;
    }


    /**
     * 发送消息，根据发送类型
     */
    @Override
    public void send() {
        switch (sendType) {
            case SING:sendSingMessage();break;
            case MANY:sendManyMessage();break;
            case TIMING:sendTimingMessage();break;
        }
    }


    private void sendSingMessage() {
        String reply = singleSession.getReply();
        MessageChain replyMessageChain = MessageChain.deserializeFromJsonString(reply);
        if (singleSession.isDynamic()) {
            reply = new DynamicMessages(reply, messageEvent).replace();
        }
        if (singleSession.isLocal()) {
        }
        messageEvent.getSubject().sendMessage(reply);
    }

    public void sendManyMessage() {

    }

    public void sendTimingMessage() {

    }

}
