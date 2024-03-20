package cn.chahuyun.session.data.entity;

import cn.chahuyun.session.data.BaseEntity;
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
     * 权限字符
     */
    private String permCode;

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }
}
