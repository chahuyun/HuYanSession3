package cn.chahuyun.session.enums;

/**
 * 消息类型
 *
 * @author Moyuyanli
 * @date 2024/1/8 15:26
 */
public enum SessionType {

    TEXT(1, "纯文本"),
    IMAGE(2, "图片消息"),
    OTHER(3, "其他类型"),
    MIXING(4, "图片文本消息");

    private Integer typeValue;

    private String typeName;

    SessionType(Integer typeValue, String typeName) {
        this.typeValue = typeValue;
        this.typeName = typeName;
    }

    public Integer getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(Integer typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
