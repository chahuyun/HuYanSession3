package cn.chahuyun.session.data.entity;

import cn.hutool.core.util.ArrayUtil;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 群组信息
 *
 * @author Moyuyanli
 * @date 2024/1/8 10:57
 */
@Entity
@Table(name = "group_list")
public class GroupList {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 群字符串
     */
    @Column(name = "`groups`")
    private String groups;

    /**
     * 群组名称
     */
    private String name;
    /**
     * 群list
     */
    @Transient
    private List<Long> groupList = new ArrayList<>();

    public GroupList() {
    }

    public GroupList(String name, List<Long> groupList) {
        this.name = name;
        this.setGroupList(groupList);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getGroupList() {
        for (String s : groups.split(",")) {
            groupList.add(Long.parseLong(s));
        }
        return groupList;
    }

    public void setGroupList(List<Long> groupList) {
        this.groupList = groupList;
        this.groups = ArrayUtil.join(groupList, ",");
    }
}
