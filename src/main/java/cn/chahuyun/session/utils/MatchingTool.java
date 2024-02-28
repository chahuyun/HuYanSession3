package cn.chahuyun.session.utils;

import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.SingleSession;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.regex.Pattern;

/**
 * 匹配工具
 *
 * <p>构建时间: 2024/2/25 13:55</p>
 *
 * @author Moyuyanli
 */
public class MatchingTool {

    private MatchingTool() {
    }


    /**
     * 匹配作用域
     *
     * @param scope   作用域
     * @param subject 消息事件主体
     * @param sender  发送着
     * @return true 匹配成功
     */
    public static boolean matchScope(Scope scope, Contact subject, User sender) {
        switch (scope.getType()) {
            case USERS:
                return scope.getUsers().contains(sender.getId());
            case LIST:
                return scope.getGroups().contains(subject.getId());
            case GROUP_MEMBERS:
                return scope.getGroup() == subject.getId() && scope.getMembers().contains(sender.getId());
            case GROUP_MEMBER:
                return scope.getGroup() == subject.getId() && scope.getMember() == sender.getId();
            case GROUP:
                return scope.getGroup() == subject.getId();
            case GLOBAL_USER:
                return scope.getUser() == sender.getId();
            case GLOBAL:
                return true;
        }
        return false;
    }

    /**
     * 匹配单一消息的触发词
     *
     * @param singleSession 单一消息
     * @param messages      消息事件
     * @return true 成功
     */
    public static boolean matchTrigger(SingleSession singleSession, MessageChain messages) {
        String matchString;

        switch (singleSession.getConversionType()) {
            case STRING:
                matchString = messages.toString();
                break;
            case CONTENT:
                matchString = messages.contentToString();
                break;
            case JSON:
                matchString = MessageChain.serializeToJsonString(messages);
                break;
            case MIRAI_CODE:
                matchString = messages.serializeToMiraiCode();
                break;
            default:
                throw new RuntimeException("消息转换类型错误!");
        }


        String trigger = singleSession.getTrigger();
        switch (singleSession.getTriggerType()) {
            case PRECISION:
                return matchString.equals(trigger);
            case FUZZY:
                return matchString.contains(trigger);
            case HEAD:
                return matchString.startsWith(trigger);
            case TAIL:
                return matchString.endsWith(trigger);
            case REGULAR:
                return Pattern.matches(trigger, matchString);
            default:
                return false;
        }
    }

    /**
     * 匹配多词条消息的触发词
     *
     * @param manySession 多词条消息
     * @param messages    消息事件
     * @return true 成功
     */
    public static boolean matchTrigger(ManySession manySession, MessageChain messages) {
        return false;
    }


}
