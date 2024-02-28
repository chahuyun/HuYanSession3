package cn.chahuyun.session.data.entity;

import cn.chahuyun.session.data.BaseEntity;
import cn.chahuyun.session.enums.MatchTriggerType;
import cn.chahuyun.session.enums.MessageConversionType;
import cn.chahuyun.session.enums.SessionType;
import jakarta.persistence.*;

import java.util.Objects;

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
     * 匹配方式
     */
    @Enumerated(EnumType.STRING)
    private MatchTriggerType triggerType;
    /**
     * 消息类型
     */
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;

    /**
     * 转换类型
     */
    @Enumerated(EnumType.STRING)
    private MessageConversionType conversionType;

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

    public MatchTriggerType getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(MatchTriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
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

    public MessageConversionType getConversionType() {
        return conversionType;
    }

    public void setConversionType(MessageConversionType conversionType) {
        this.conversionType = Objects.requireNonNullElse(conversionType, MessageConversionType.MIRAI_CODE);
    }
}
