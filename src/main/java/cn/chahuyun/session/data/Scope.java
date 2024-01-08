package cn.chahuyun.session.data;

import cn.chahuyun.session.data.factory.DataFactory;
import cn.chahuyun.session.data.entity.GroupList;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用域
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:40
 */
@Slf4j
public class Scope {

    /**
     * 标识
     */
    private final String marker;
    /**
     * 群
     */
    private final List<Long> group = new ArrayList<>();
    /**
     * 全局
     */
    private Boolean global = false;

    public Scope(String marker) {
        this.marker = marker;
        String[] split = marker.split("/.");
        switch (split.length) {
            case 0:
                global = true;
                break;
            case 1:
                group.add(Long.parseLong(split[0]));
                break;
            case 2:
                GroupList groupList = DataFactory.INSTANCE.selectResultEntity(GroupList.class,
                        "from GroupList where name = '%s' ", split[1]);
                if (groupList == null) {
                    log.warn("群组查询为空，请检查你的群组是否正确！");
                } else {
                    group.addAll(groupList.getGroupList());
                }
                break;
        }
    }

    /**
     * 是包含在该群
     *
     * @param event 群事件
     * @return t 存在
     */
    public boolean containsGroup(GroupEvent event) {
        if (global) return true;
        for (Long aLong : group) {
            if (aLong == event.getGroup().getId()) {
                return true;
            }
        }
        return false;
    }

    public String getMarker() {
        return marker;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public List<Long> getGroup() {
        return group;
    }
}
