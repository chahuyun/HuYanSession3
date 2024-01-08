package cn.chahuyun.session.entity;

import cn.chahuyun.session.enums.SessionType;
import jakarta.persistence.*;

/**
 * 会话信息
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:25
 */
@Entity
@Table(name = "session_single")
public class SingleSession extends BaseEntity {

    private Integer id;

    private String trigger;

    private String reply;

    private String dynamic;

    private SessionType type;

    public SingleSession() {
    }

    public SingleSession(String trigger, String reply, String dynamic, SessionType type) {
        this.trigger = trigger;
        this.reply = reply;
        this.dynamic = dynamic;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public Integer getType() {
        return type.getTypeValue();
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

    @Override
    public String getScopeMarker() {
        return super.getScopeMarker();
    }

    @Override
    public void setScopeMarker(String scopeMarker) {
        super.setScopeMarker(scopeMarker);
    }

    /**
     * 获取所属作用域
     *
     * @return 作用域
     */
    @Override
    @Transient
    public Scope getScope() {
        return super.getScope();
    }

    @Transient
    public SessionType getSessionType() {
        return type;
    }

    public void setSessionType(SessionType type) {
        this.type = type;
    }
}
