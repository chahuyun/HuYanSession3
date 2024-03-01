package cn.chahuyun.session.data.entity;

import cn.chahuyun.session.data.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 权限信息
 *
 * @author Moyuyanli
 * @date 2024/1/9 14:52
 */
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    /**
     * 管理权限
     */
    @Column(name = "`admin`")
    private boolean admin;
    /**
     * 会话权限
     */
    private boolean session;
    /**
     * 多词条
     */
    private boolean dct;
    /**
     * 定时
     */
    private boolean ds;
    /**
     * 会话
     */
    private boolean hh;
    /**
     * 群组
     */
    private boolean list;


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    public boolean isDct() {
        return dct;
    }

    public void setDct(boolean dct) {
        this.dct = dct;
    }

    public boolean isDs() {
        return ds;
    }

    public void setDs(boolean ds) {
        this.ds = ds;
    }

    public boolean isHh() {
        return hh;
    }

    public void setHh(boolean hh) {
        this.hh = hh;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }
}
