package cn.chahuyun.session.data.entity;

import cn.chahuyun.session.data.BaseEntity;
import cn.chahuyun.session.enums.SessionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 会话信息
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:25
 */
@Entity
@Table(name = "session_single")
public class SingleSession extends BaseEntity {

    /**
     * 触发词
     */
    @Column(name = "`trigger`")
    private String trigger;
    /**
     * 回复词
     */
    private String reply;
    /**
     * 是否为动态消息
     */
    private boolean dynamic;
    /**
     * 是否为本地缓存
     */
    private boolean local;
    /**
     * 概率触发
     */
    private Double probability;
    /**
     * 消息类型
     */
    private SessionType type;

    public SingleSession() {
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public SessionType getType() {
        return type;
    }

    public void setType(SessionType type) {
        this.type = type;
    }

    public void setType(Integer type) {
        switch (type) {
            case 2:
                this.type = SessionType.IMAGE;
                break;
            case 3:
                this.type = SessionType.OTHER;
                break;
            case 4:
                this.type = SessionType.MIXING;
                break;
            case 1:
            default:
                this.type = SessionType.TEXT;
        }
    }


    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        if (probability != null) {
            this.probability = Math.min(Math.max(probability, 0.0), 1.0);
        } else {
            this.probability = 1.0;
        }
    }

    public SessionType getSessionType() {
        return type;
    }

    public void setSessionType(SessionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SingleSession{" +
                "id=" + getId() +
                ", trigger='" + trigger + '\'' +
                ", reply='" + reply + '\'' +
                ", dynamic='" + dynamic + '\'' +
                ", type=" + type +
                '}';
    }
}
