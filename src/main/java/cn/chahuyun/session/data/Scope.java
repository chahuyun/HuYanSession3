package cn.chahuyun.session.data;

import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.factory.AbstractDataService;
import cn.chahuyun.session.data.factory.DataFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

/**
 * 作用域
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:40
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class Scope {

    /*
    作用域，编写壶言会话3前期遇见的第一个有复杂的需求
    设计时间 超过2小时

    解决时间 2024年2月24日17:51:41

    仍有大量的优化空间

    后期有时间回来慢慢优化，目前只实现功能

    //todo 预留的Scope优化
     */

    private final AbstractDataService dataService = DataFactory.getInstance().getDataService();

    /**
     * 作用域类型
     */
    public enum Type {
        /**
         * 全局
         */
        GLOBAL("global"),
        /**
         * 全局用户
         */
        GLOBAL_USER("global-%d"),
        /**
         * 群
         */
        GROUP("group-%d"),
        /**
         * 群用户
         */
        GROUP_MEMBER("group-%d-member-%d"),
        /**
         * 群分组用户
         */
        GROUP_MEMBERS("group-%d-membersName-%s"),
        /**
         * 分组群
         */
        LIST("list-%s"),
        /**
         * 分组用户
         */
        USERS("users-%s");

        private final String valueTemplate;

        Type(String valueTemplate) {
            this.valueTemplate = valueTemplate;
        }

        public String getValueTemplate() {
            return valueTemplate;
        }
    }

    /**
     * 标识
     */
    private final String marker;
    /**
     * 作用域类型
     */
    private final Type type;
    /**
     * 单一群
     */
    private final Long group;
    /**
     * 分组群
     */
    private final List<Long> groups;
    /**
     * 单一用户
     */
    private final Long user;
    /**
     * 分组用户
     */
    private final List<Long> users;
    /**
     * 群员
     */
    private final Long member;
    /**
     * 分组群员
     */
    private final List<Long> members;
    /**
     * 分组群员名称
     */
    private final String membersName;
    /**
     * 分组群名称
     */
    private final String listName;
    /**
     * 分组人名称
     */
    private final String usersName;


    /**
     * 传入 scopeMark 进行识别Scope
     *
     * @param marker scopeMark
     * @return scope
     */
    public static Scope fromScopeMarker(String marker) {
        String[] parts = marker.split("-");
        if (parts.length < 1) {
            throw new IllegalArgumentException("scopeMarker格式不正确");
        }

        Type type;
        try {
            //只能匹配到 GLOBAL GROUP LIST USERS 四中类型
            type = Type.valueOf(parts[0].toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("scopeMarker的类型格式不正确");
        }

        switch (type) {
            case GLOBAL:
                if (parts.length == 1) {
                    return new Scope(type);
                } else if (parts.length == 2) {
                    return new Scope(Type.GLOBAL_USER, Long.parseLong(parts[1]));
                } else {
                    throw new IllegalArgumentException("GLOBAL类型的scopeMarker格式不正确");
                }
            case GROUP:
                if (parts.length >= 2) {
                    Long groupId = Long.parseLong(parts[1]);
                    if (parts.length == 2) {
                        return new Scope(type, groupId);
                    } else if (parts.length == 4) {
                        if (parts[3].equals("member")) {
                            return new Scope(Type.GROUP_MEMBER, groupId, Long.parseLong(parts[3]));
                        } else if (parts[3].equals("membersName")) {
                            return new Scope(Type.GROUP_MEMBERS, groupId, parts[3]);
                        }
                    }
                }
                throw new IllegalArgumentException("GROUP类型的scopeMarker格式不正确");
            case LIST:
                if (parts.length != 2) {
                    throw new IllegalArgumentException("LIST类型的scopeMarker格式不正确");
                }
                return new Scope(type, parts[1]);
            case USERS:
                if (parts.length != 2) {
                    throw new IllegalArgumentException("USERS类型的scopeMarker格式不正确");
                }
                return new Scope(type, parts[1]);
            default:
                throw new IllegalArgumentException("无法识别的scopeMarker类型");
        }
    }

    /**
     * 构建全局作用域
     *
     * @param type 请输入全局作用域类型
     */
    public Scope(Type type) {
        if (type != Type.GLOBAL) {
            throw new IllegalArgumentException("作用域类型错误!");
        }
        this.marker = type.getValueTemplate();
        this.type = type;

        this.group = null;
        this.groups = null;
        this.user = null;
        this.users = null;
        this.member = null;
        this.usersName = null;
        this.listName = null;
        this.members = null;
        this.membersName = null;
    }

    /**
     * 构建全局用户或群作用域
     *
     * @param type        清输入 全局用户作用域类型 或 群作用域类型
     * @param groupOrUser 群id或用户id
     */
    public Scope(Type type, Long groupOrUser) {
        if (!Constant.SCOPE_TYPE_SINGLE_PARAMETER.contains(type)) {
            throw new IllegalArgumentException("作用域类型错误!");
        }
        this.type = type;
        if (type == Type.GLOBAL_USER) {
            this.user = groupOrUser;
        } else {
            this.user = null;
        }
        if (type == Type.GROUP) {
            this.group = groupOrUser;
        } else {
            this.group = null;
        }
        this.marker = String.format(type.getValueTemplate(), groupOrUser);

        this.groups = null;
        this.users = null;
        this.member = null;
        this.usersName = null;
        this.listName = null;
        this.members = null;
        this.membersName = null;
    }

    /**
     * 构建分组群或分组用户作用域
     *
     * @param type              请输入 分组群作用域类型 或 分组人作用域类型
     * @param listIdOrUsersName 分组群id或分组用户id
     */
    public Scope(Type type, String listIdOrUsersName) {
        if (!Constant.SCOPE_TYPE_SINGLE_PARAMETER.contains(type)) {
            throw new IllegalArgumentException("作用域类型错误!");
        }
        this.type = type;
        if (type == Type.USERS) {
            this.usersName = listIdOrUsersName;
            this.users = dataService.getGroupedLists(listIdOrUsersName).getValueList();
        } else {
            this.users = null;
            this.usersName = null;
        }
        if (type == Type.LIST) {
            this.listName = listIdOrUsersName;
            this.groups = dataService.getGroupedLists(listIdOrUsersName).getValueList();
        } else {
            this.listName = null;
            this.groups = null;
        }
        this.marker = String.format(type.getValueTemplate(), listIdOrUsersName);

        this.group = null;
        this.user = null;
        this.member = null;
        this.members = null;
        this.membersName = null;
    }

    /**
     * 构建群用户作用域
     *
     * @param type   清输入群用户作用域类型
     * @param group  群id
     * @param member 群员id
     */
    public Scope(Type type, Long group, Long member) {
        if (type != Type.GROUP_MEMBER) {
            throw new IllegalArgumentException("作用域类型错误!");
        }
        this.group = group;
        this.member = member;
        this.marker = String.format(type.valueTemplate, group, member);
        this.type = type;

        this.groups = null;
        this.user = null;
        this.users = null;
        this.members = null;
        this.listName = null;
        this.usersName = null;
        this.membersName = null;
    }

    /**
     * 构建分组群员作用域
     *
     * @param type       请输入分组群员作用域
     * @param group      群id
     * @param memberName 分组群员名称
     */
    public Scope(Type type, Long group, String memberName) {
        if (type != Type.GROUP_MEMBERS) {
            throw new IllegalArgumentException("作用域类型错误!");
        }
        this.group = group;

        this.members = dataService.getGroupedLists(memberName).getValueList();
        this.marker = String.format(type.valueTemplate, group, memberName);
        this.type = type;

        this.groups = null;
        this.member = null;
        this.user = null;
        this.users = null;
        this.listName = null;
        this.usersName = null;
        this.membersName = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scope scope = (Scope) o;
        return Objects.equals(marker, scope.marker) && type == scope.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(marker, type);
    }

    public String getMarker() {
        return marker;
    }

    public Type getType() {
        return type;
    }

    public Long getGroup() {
        return group;
    }

    public List<Long> getGroups() {
        return groups;
    }

    public Long getUser() {
        return user;
    }

    public List<Long> getUsers() {
        return users;
    }

    public Long getMember() {
        return member;
    }

    public List<Long> getMembers() {
        return members;
    }

    public String getListName() {
        return listName;
    }

    public String getUsersName() {
        return usersName;
    }

    public String getMembersName() {
        return membersName;
    }
}
