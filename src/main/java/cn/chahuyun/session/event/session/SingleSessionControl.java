package cn.chahuyun.session.event.session;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.ParameterSet;
import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.cache.Cache;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.cache.MemoryCache;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.enums.MatchTriggerType;
import cn.chahuyun.session.enums.SessionType;
import cn.chahuyun.session.send.LocalMessage;
import cn.chahuyun.session.send.cache.SendCacheEntity;
import cn.chahuyun.session.send.cache.SendMessageCache;
import cn.chahuyun.session.utils.AnswerTool;
import cn.chahuyun.session.utils.MessageTool;
import cn.chahuyun.session.utils.TimingUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static cn.chahuyun.session.HuYanSession.answerConfig;

/**
 * 单一消息处理
 *
 * @author Moyuyanli
 * @date 2024/2/28 14:10
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class SingleSessionControl {

    public static final SingleSessionControl INSTANCE = new SingleSessionControl();

    /**
     * 简单学习消息<br>
     * %xx trigger reply [scope|dynamic|rewrite|probability|localCache|matchTriggerType|conversionType]
     *
     * @param messages 消息
     * @param subject  消息事件主体
     * @param sender   发送着
     */
    public void studySimpleSingleSession(MessageChain messages, Contact subject, User sender) {

        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("匹配指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
        }

        String code = messages.serializeToMiraiCode();

        String[] params = code.split("\\s+");

        String trigger = params[1];
        String reply = params[2];

        ParameterSet parameterSet;
        SessionType sessionType = SessionType.TEXT;
        Scope scope = Scope.group(subject);


        if (params.length > 3) {
            String[] sub = ArrayUtil.sub(params, 3, params.length);
            parameterSet = new ParameterSet(scope, subject, sub);
        } else {
            parameterSet = new ParameterSet(scope, subject);
        }

        if (parameterSet.isException()) {
            subject.sendMessage(parameterSet.getExceptionMsg());
            return;
        }

        scope = parameterSet.getScope();

        SingleSession singleSession = new SingleSession();

        MemoryCache cacheService = (MemoryCache) CacheFactory.getInstall().getCacheService();
        List<SingleSession> cacheServiceSingSession = cacheService.getSingSession(scope);
        if (!cacheServiceSingSession.isEmpty()) {
            for (SingleSession session : cacheServiceSingSession) {
                if (session.getTrigger().equals(trigger)) {
                    //是否重写
                    if (parameterSet.isRewrite()) {
                        singleSession.setId(session.getId());
                    } else {
                        subject.sendMessage(AnswerTool.getAnswer(answerConfig.getStudyRepeat()));
                        return;
                    }
                }
            }
        }


        MessageChain singleMessages = MessageChain.deserializeFromMiraiCode(reply, subject);

        if (parameterSet.isLocalCache()) {
            if (!LocalMessage.localCacheImage(singleMessages)) {
                subject.sendMessage("图片缓存失败!");
                return;
            }
        }

        if (singleMessages.contains(Image.Key)) {
            if (singleMessages.contains(PlainText.Key)) {
                sessionType = SessionType.MIXING;
            } else {
                sessionType = SessionType.IMAGE;
            }

            MessageChainBuilder builder = new MessageChainBuilder();
            //麻烦的图片转换处理
            for (SingleMessage singleMessage : singleMessages) {
                if (singleMessage instanceof Image) {
                    Image baseImage = (Image) singleMessage;
                    for (SingleMessage source : messages) {
                        if (source instanceof Image) {
                            Image sourceImage = (Image) source;
                            if (sourceImage.getImageId().equals(baseImage.getImageId())) {
                                builder.append(sourceImage);
                            }
                        }
                    }
                } else {
                    builder.append(singleMessage);
                }
            }
            singleMessages = builder.build();
        } else if (singleMessages.size() >= 2) {
            sessionType = SessionType.OTHER;
        }

        if (singleMessages.size() == 1 && singleMessages.contains(Image.Key)) {
            sessionType = SessionType.IMAGE;
        } else if (singleMessages.size() == 2 && singleMessages.contains(Image.Key) && singleMessages.contains(PlainText.Key)) {
            sessionType = SessionType.MIXING;
        } else if (!singleMessages.isEmpty() && !singleMessages.contains(PlainText.Key)) {
            sessionType = SessionType.OTHER;
        }

        reply = MessageChain.serializeToJsonString(singleMessages);

        if (parameterSet.getMatchTriggerType() == MatchTriggerType.REGULAR) {
            trigger = trigger.replaceAll("\\\\(?!\\\\)", "");
        }

        singleSession.setTrigger(trigger);
        singleSession.setReply(reply);
        singleSession.setDynamic(parameterSet.isDynamic());
        singleSession.setLocal(parameterSet.isLocalCache());
        singleSession.setProbability(parameterSet.getProbability());
        singleSession.setSessionType(sessionType);
        singleSession.setMatchType(parameterSet.getMatchTriggerType());
        singleSession.setConversionType(parameterSet.getConversionType());
        singleSession.setScope(scope);


        String result;
        if (HibernateFactory.merge(singleSession).getId() != null) {
            result = AnswerTool.getAnswer(answerConfig.getStudySuccess());
            cacheService.putSession(singleSession);
        } else {
            result = AnswerTool.getAnswer(answerConfig.getStudyFailed());
        }
        subject.sendMessage(result);
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("简单学习消息指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
            TimingUtil.cleanTiming(Thread.currentThread().getName());
        }
    }


    /**
     * 对话的方式学习
     * %xx( +trigger)?
     *
     * @param messages 消息
     * @param subject  消息事件主体
     * @param sender   发送着
     */
    public void studyDialogue(MessageChain messages, Contact subject, User sender) {

        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("匹配指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
        }

        String[] split = messages.serializeToMiraiCode().split(" ");
        String trigger;
        if (split.length == 1) {
            subject.sendMessage("请输入触发词:");
            MessageEvent event = MessageTool.nextUserMessage(subject, sender);
            if (MessageTool.isQuit(event)) {
                return;
            }
            trigger = event.getMessage().serializeToMiraiCode();
        } else {
            trigger = split[1];
        }

        subject.sendMessage("请输入参数:");
        MessageEvent event = MessageTool.nextUserMessage(subject, sender);
        if (MessageTool.isQuit(event)) {
            return;
        }

        String[] params = event.getMessage().contentToString().split(" ");
        ParameterSet parameterSet = new ParameterSet(Scope.group(subject), subject, params);

        if (parameterSet.isException()) {
            subject.sendMessage(parameterSet.getExceptionMsg());
            return;
        }

        Scope scope = parameterSet.getScope();

        Cache cacheService = CacheFactory.getInstall().getCacheService();
        List<SingleSession> singSession = cacheService.getSingSession(scope);

        SingleSession singleSession = new SingleSession();
        singleSession.setTrigger(trigger);

        if (parameterSet.isRewrite()) {
            for (SingleSession session : singSession) {
                if (session.getTrigger().equals(singleSession.getTrigger())) {
                    singleSession = session;
                    break;
                }
            }
        }

        subject.sendMessage("请输入回复消息:");
        event = MessageTool.nextUserMessage(subject, sender);
        if (MessageTool.isQuit(event)) {
            return;
        }

        MessageChain message = event.getMessage();

        if (parameterSet.isLocalCache()) {
            if (!LocalMessage.localCacheImage(message)) {
                subject.sendMessage("学习失败,本地图片缓存失败!");
                return;
            }
        }

        String reply = MessageChain.serializeToJsonString(event.getMessage());

        singleSession.setReply(reply);
        singleSession.setScope(scope);
        singleSession.setDynamic(parameterSet.isDynamic());
        singleSession.setMatchType(parameterSet.getMatchTriggerType());
        singleSession.setConversionType(parameterSet.getConversionType());
        singleSession.setProbability(parameterSet.getProbability());
        singleSession.setLocal(parameterSet.isLocalCache());

        String result;
        if (HibernateFactory.merge(singleSession).getId() != null) {
            result = AnswerTool.getAnswer(answerConfig.getStudySuccess());
            cacheService.putSession(singleSession);
        } else {
            result = AnswerTool.getAnswer(answerConfig.getStudyFailed());
        }
        subject.sendMessage(result);
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("对话的方式学习指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
            TimingUtil.cleanTiming(Thread.currentThread().getName());
        }
    }

    /**
     * 删除词条
     * -xx trigger [scope|id]
     *
     * @param messages 消息
     * @param subject  消息事件主体
     * @param sender   发送着
     */
    public void removeSimpleSingleSession(MessageChain messages, Contact subject, User sender) {
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("匹配指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
        }
        String code = messages.serializeToMiraiCode();

        String[] params = code.split("\\s+");
        String trigger = params[1];

        ParameterSet parameterSet;
        Scope scope = Scope.group(subject);
        if (params.length > 2) {
            parameterSet = new ParameterSet(scope, ArrayUtil.sub(params, 2, params.length));
        } else {
            parameterSet = new ParameterSet(scope);
        }

        if (parameterSet.isException()) {
            subject.sendMessage(parameterSet.getExceptionMsg());
            return;
        }

        Cache cacheService = CacheFactory.getInstall().getCacheService();
        List<SingleSession> singSession = cacheService.getSingSession(scope);

        if (parameterSet.getId() != null) {
            for (SingleSession singleSession : singSession) {
                if (Objects.equals(singleSession.getId(), parameterSet.getId().intValue())) {
                    if (trigger.equals(singleSession.getTrigger())) {
                        if (HibernateFactory.delete(singleSession)) {
                            cacheService.removeSingSession(singleSession.getId());
                            subject.sendMessage(AnswerTool.getAnswer(answerConfig.getRemoveSuccess()));
                        } else {
                            subject.sendMessage(AnswerTool.getAnswer(answerConfig.getRemoveFailed()));
                        }
                    }
                }
            }
        } else {
            for (Iterator<SingleSession> iterator = singSession.iterator(); iterator.hasNext(); ) {
                SingleSession singleSession = iterator.next();
                if (trigger.equals(singleSession.getTrigger())) {
                    if (HibernateFactory.delete(singleSession)) {
                        iterator.remove();
                        subject.sendMessage(AnswerTool.getAnswer(answerConfig.getRemoveSuccess()));
                    } else {
                        subject.sendMessage(AnswerTool.getAnswer(answerConfig.getRemoveFailed()));
                    }
                }
            }
        }
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("删除词条指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
            TimingUtil.cleanTiming(Thread.currentThread().getName());
        }
    }

    public void refresh(Contact subject) {
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("匹配指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
        }
        Cache cacheService = CacheFactory.getInstall().getCacheService();
        List<SingleSession> SingleSessions = HibernateFactory.selectList(SingleSession.class);
        SingleSessions.forEach(cacheService::putSession);
        subject.sendMessage("单一缓存刷新成功!");
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("单一缓存刷新指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
            TimingUtil.cleanTiming(Thread.currentThread().getName());
        }
    }

    /**
     * 引用删除
     *
     * @param messages 消息
     * @param subject  载体
     */
    public void removeSimpleSingleSessionFormQuery(MessageChain messages, Contact subject) {
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("匹配指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
        }
        QuoteReply quoteReply = messages.get(QuoteReply.Key);
        if (quoteReply == null) {
            return;
        }
        MessageSource quoteReplySource = quoteReply.getSource();
        int msgId = quoteReplySource.getInternalIds()[0];

        SendMessageCache sendMessageCache = SendMessageCache.getInstance();
        SendCacheEntity sendMessage = sendMessageCache.getSendMessage(msgId);

        if (sendMessage == null || sendMessage.getType() != 0) {
            return;
        }

        Integer sessionId = sendMessage.getSessionId();

        Cache cacheService = CacheFactory.getInstall().getCacheService();
        SingleSession singSession = cacheService.getSingSession(sessionId);
        if (HibernateFactory.delete(singSession)) {
            cacheService.removeSingSession(sessionId);
            subject.sendMessage(AnswerTool.getAnswer(answerConfig.getRemoveSuccess()));
        } else {
            subject.sendMessage(AnswerTool.getAnswer(answerConfig.getRemoveFailed()));
        }
        if (HuYanSession.pluginConfig.getDevTool()) {
            log.debug("引用删除指令用时:{}ns", TimingUtil.getResults(Thread.currentThread().getName()));
            TimingUtil.cleanTiming(Thread.currentThread().getName());
        }
    }


}
