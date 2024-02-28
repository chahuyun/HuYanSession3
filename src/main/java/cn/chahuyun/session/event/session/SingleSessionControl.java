package cn.chahuyun.session.event.session;

import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.config.SessionAnswerConfig;
import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.factory.DataFactory;
import cn.chahuyun.session.enums.MatchTriggerType;
import cn.chahuyun.session.enums.MessageConversionType;
import cn.chahuyun.session.enums.SessionType;
import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.*;

/**
 * 单一消息处理
 *
 * @author Moyuyanli
 * @date 2024/2/28 14:10
 */
public class SingleSessionControl {


    /**
     * 简单学习消息
     *
     * @param messages 消息
     * @param subject  消息事件主体
     * @param sender   发送着
     */
    public void studySimpleSingleSession(MessageChain messages, Contact subject, User sender) {
        String code = messages.serializeToMiraiCode();

        String[] params = code.split("\\s+");

        String trigger = params[1];
        String reply = params[2];

        MatchTriggerType matchTriggerType = MatchTriggerType.PRECISION;
        MessageConversionType conversionType = MessageConversionType.MIRAI_CODE;
        SessionType sessionType = SessionType.TEXT;
        boolean localCache = HuYanSession.config.getLocalCache();
        boolean dynamic = false;
        double probability = 1.0;
        Scope scope = new Scope(Scope.Type.GROUP, subject.getId());

        if (params.length > 3) {
            for (int i = 3; i < params.length; i++) {
                String param = params[i];

                switch (param) {
                    case "全局":
                    case "global":
                    case "0":
                        scope = new Scope(Scope.Type.GLOBAL);
                        continue;
                    case "模糊":
                    case "2":
                        matchTriggerType = MatchTriggerType.FUZZY;
                        continue;
                    case "头部":
                    case "3":
                        matchTriggerType = MatchTriggerType.HEAD;
                        continue;
                    case "尾部":
                    case "4":
                        matchTriggerType = MatchTriggerType.TAIL;
                        continue;
                    case "正则":
                    case "5":
                        matchTriggerType = MatchTriggerType.REGULAR;
                        continue;
                    case "dynamic":
                    case "动态":
                        dynamic = true;
                        continue;
                    case "cache":
                        localCache = true;
                        continue;
                    case "STRING":
                        conversionType = MessageConversionType.STRING;
                        continue;
                    case "CONTENT":
                        conversionType = MessageConversionType.CONTENT;
                        continue;
                    case "JSON":
                        conversionType = MessageConversionType.JSON;
                        continue;
                }

                if (param.contains("global-")) {
                    MessageChain userMessage = MessageChain.deserializeFromMiraiCode(param, subject);
                    if (userMessage.contains(At.Key)) {
                        At at = (At) userMessage.get(At.Key);
                        if (at != null) {
                            scope = new Scope(Scope.Type.GLOBAL_USER, at.getTarget());
                            continue;
                        }
                    }
                    try {
                        long aLong = Long.parseLong(param.replace("global-", ""));
                        scope = new Scope(Scope.Type.GLOBAL_USER, aLong);
                    } catch (NumberFormatException e) {
                        subject.sendMessage("你的参数有误:global识别失败");
                        return;
                    }
                } else if (param.contains("member-")) {
                    MessageChain userMessage = MessageChain.deserializeFromMiraiCode(param, subject);
                    if (userMessage.contains(At.Key)) {
                        At at = (At) userMessage.get(At.Key);
                        if (at != null) {
                            scope = new Scope(Scope.Type.GROUP_MEMBER, subject.getId(), at.getTarget());
                            continue;
                        }
                    }
                    try {
                        long aLong = Long.parseLong(param.replace("member-", ""));
                        scope = new Scope(Scope.Type.GROUP_MEMBER, subject.getId(), aLong);
                    } catch (NumberFormatException e) {
                        subject.sendMessage("你的参数有误:member 识别失败");
                        return;
                    }
                } else if (param.contains("list-")) {
                    scope = new Scope(Scope.Type.LIST, param.replace("list-", ""));
                } else if (param.contains("users-")) {
                    scope = new Scope(Scope.Type.USERS, param.replace("users-", ""));
                } else if (param.contains("members-")) {
                    scope = new Scope(Scope.Type.GROUP_MEMBERS, subject.getId(), param.replace("members-", ""));
                } else if (param.contains("probability-")) {
                    try {
                        probability = Double.parseDouble(param.replace("probability-", ""));
                    } catch (NumberFormatException e) {
                        subject.sendMessage("你的参数有误:probability 识别失败");
                        return;
                    }
                }
            }
        }

        if (localCache) {
            //todo 本地缓存
        }

        MessageChain singleMessages = MessageChain.deserializeFromMiraiCode(reply, subject);
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
        } else if (singleMessages.size() >= 1 && !singleMessages.contains(PlainText.Key)) {
            sessionType = SessionType.OTHER;
        }

        reply = MessageChain.serializeToJsonString(singleMessages);

        SingleSession singleSession = new SingleSession();
        singleSession.setTrigger(trigger);
        singleSession.setReply(reply);
        singleSession.setDynamic(dynamic);
        singleSession.setLocal(localCache);
        singleSession.setProbability(probability);
        singleSession.setSessionType(sessionType);
        singleSession.setTriggerType(matchTriggerType);
        singleSession.setConversionType(conversionType);
        singleSession.setScope(scope);

        SessionAnswerConfig instance = SessionAnswerConfig.INSTANCE;
        String result;
        if (DataFactory.getInstance().getDataService().mergeEntityStatus(singleSession)) {
            result = instance.getStudySuccess().get(RandomUtil.randomInt(0, instance.getStudySuccess().size()));
            CacheFactory.getInstall().getCacheService().putSession(singleSession);
        } else {
            result = instance.getStudyFailed().get(RandomUtil.randomInt(0, instance.getStudySuccess().size()));
        }
        subject.sendMessage(result);
    }

}
