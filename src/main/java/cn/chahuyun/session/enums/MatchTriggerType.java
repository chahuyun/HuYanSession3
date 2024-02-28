package cn.chahuyun.session.enums;

/**
 * 触发词匹配方式
 *
 * @author Moyuyanli
 * @date 2024/2/28 10:29
 */
public enum MatchTriggerType {
    /**
     * 精准匹配
     */
    PRECISION(1),
    /**
     * 模糊匹配
     */
    FUZZY(2),
    /**
     * 头部匹配
     */
    HEAD(3),
    /**
     * 尾部匹配
     */
    TAIL(4),
    /**
     * 正则匹配
     */
    REGULAR(5);

    private final int type;

    MatchTriggerType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
