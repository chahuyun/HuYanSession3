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

    /**
     * 作用域标识
     */
    @Column(name = "scope_marker")
    private String scopeMarker;

    /**
     * 获取所属作用域
     *
     * @return 作用域
     */
    @Override
    public Scope getScope() {
        return Scope.fromScopeMarker(scopeMarker);
    }

    /**
     * 设置作用域，将他的mark保存
     *
     * @param scope 作用域
     */
    @Override
    public void setScope(Scope scope) {
        this.scopeMarker = scope.getMarker();
    }

    public String getScopeMarker() {
        return scopeMarker;
    }

    public void setScopeMarker(String scopeMarker) {
        this.scopeMarker = scopeMarker;
    }
}
