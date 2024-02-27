package cn.chahuyun.session.utils;

import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.SingleSession;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.MessageChain;

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


        return false;
    }

    /**
     * 匹配单一消息的触发词
     * @param singleSession 单一消息
     * @param messages 消息事件
     * @return true 成功
     */
    public static boolean matchTrigger(SingleSession singleSession, MessageChain messages) {
        return false;
    }

    /**
     * 匹配多词条消息的触发词
     * @param manySession 多词条消息
     * @param messages 消息事件
     * @return true 成功
     */
    public static boolean matchTrigger(ManySession manySession, MessageChain messages) {
        return false;
    }


}
