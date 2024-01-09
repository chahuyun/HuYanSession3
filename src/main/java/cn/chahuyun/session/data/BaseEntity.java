package cn.chahuyun.session.data;

import cn.chahuyun.session.data.api.ScopeAcquisition;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * 基础类
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:26
 */
@MappedSuperclass
public class BaseEntity implements ScopeAcquisition {

    @Column(name = "scope_marker")
    private String scopeMarker;

    /**
     * 获取所属作用域
     *
     * @return 作用域
     */
    @Override
    public Scope getScope() {
        return new Scope(scopeMarker);
    }

    public String getScopeMarker() {
        return scopeMarker;
    }

    public void setScopeMarker(String scopeMarker) {
        this.scopeMarker = scopeMarker;
    }
}
