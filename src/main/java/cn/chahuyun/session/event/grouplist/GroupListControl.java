package cn.chahuyun.session.event.grouplist;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.DataService;
import cn.chahuyun.session.data.entity.GroupedLists;
import cn.chahuyun.session.utils.MessageTool;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Moyuyanli
 * @date 2024/8/5 14:29
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class GroupListControl {

    public static final GroupListControl INSTANCE = new GroupListControl();

    private GroupListControl() {
    }

    /**
     * 添加群组
     *
     * @param messages 消息事件
     * @param subject  载体
     * @param sender   发送者
     */
    public void addGroupList(MessageChain messages, Contact subject, User sender) {
        String code = messages.contentToString();

        String[] split = code.split(" +");
        String name = split[1];
        int type = Integer.parseInt(split[2]);

        if (type == 1) {
            subject.sendMessage("请发送群号,中间以空格隔开!");
        } else {
            subject.sendMessage("请发送QQ号,中间以空格隔开!");
        }
        MessageEvent message = MessageTool.nextUserMessage(subject, sender);

        if (message == null) {
            log.debug("下一条消息为空!");
            return;
        }

        String content = message.getMessage().contentToString();
        String[] qqs = content.split(" +");


        ArrayList<Long> longs = new ArrayList<>();

        for (String string : qqs) {
            try {
                longs.add(Long.parseLong(string));
            } catch (NumberFormatException e) {
                log.debug("qq识别错误:{}", string);
            }
        }

        if (longs.isEmpty()) {
            subject.sendMessage("添加qq信息为空,请重新添加!");
            return;
        }

        GroupedLists groupedLists = HibernateFactory.selectOne(GroupedLists.class, "name", name);
        if (groupedLists == null) {
            groupedLists = new GroupedLists(type, name, longs);
        } else {
            if (groupedLists.getType() != type) {
                subject.sendMessage("已经有同名类型不同的群组!");
                return;
            }
            List<Long> valueList = groupedLists.getValueList();
            for (Long asl : longs) {
                if (!valueList.contains(asl)) {
                    valueList.add(asl);
                }
            }
            groupedLists.setValueList(valueList);
        }

        if (HibernateFactory.merge(groupedLists).getId() != null) {
            subject.sendMessage("群组更新成功!");
        } else {
            subject.sendMessage("群组更新失败!");
        }

        DataService.refresh();
    }

}
