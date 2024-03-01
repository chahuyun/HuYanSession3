package cn.chahuyun.session.event;

import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.cache.Cache;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.event.api.EventHanding;
import cn.chahuyun.session.event.session.SingleSessionControl;
import cn.chahuyun.session.send.DefaultSendMessage;
import cn.chahuyun.session.utils.MatchingTool;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 事件service
 *
 * <p>构建时间: 2024/2/25 13:51</p>
 *
 * @author Moyuyanli
 */
public class EventServices extends SimpleListenerHost implements EventHanding {

    public EventServices(@NotNull CoroutineContext coroutineContext) {
        super(coroutineContext);
    }


    /**
     * 匹配消息
     *
     * @param messageEvent 消息事件
     */
    @Override
    @EventHandler
    public void messageMatching(MessageEvent messageEvent) {
        MessageChain messageChain = messageEvent.getMessage();
        Contact subject = messageEvent.getSubject();
        User sender = messageEvent.getSender();



        Cache cacheService = CacheFactory.getInstall().getCacheService();
        List<Scope> mateSessionScope = cacheService.getMatchSingSessionScope();
        for (Scope scope : mateSessionScope) {
            if (MatchingTool.matchScope(scope, subject, sender)) {
                List<SingleSession> singleSessions = cacheService.getSingSession(scope);
                for (SingleSession singleSession : singleSessions) {
                    if (MatchingTool.matchTrigger(singleSession, messageChain)) {
                        new DefaultSendMessage(singleSession, messageEvent).send();
                        if (!HuYanSession.pluginConfig.getMatchAll()) return;
                    }
                }
            }
        }
    }

    /**
     * 匹配指令
     *
     * @param messageEvent 消息事件
     */
    @Override
    @EventHandler
    public void commandMatching(MessageEvent messageEvent) {
        Contact subject = messageEvent.getSubject();
        User sender = messageEvent.getSender();
        MessageChain message = messageEvent.getMessage();
        String content = message.contentToString();

        //todo 匹配指令
        String addStudyPattern = "壶言3消息测试";
        if (Pattern.matches(addStudyPattern, content)) {
            subject.sendMessage(MessageChain.serializeToJsonString(message));
        }

        if (sender.getId() == 572490972 && content.lastIndexOf("!") == 0) {
            subject.sendMessage(MessageChain.serializeToJsonString(message));
        }

        String studySimpleSession = "^xx( +\\S+){2,7}|^学习( +\\S+){2,7}";
        if (Pattern.matches(studySimpleSession, content)) {
            SingleSessionControl.INSTANCE.studySimpleSingleSession(message, subject, sender);
        }
    }
}
