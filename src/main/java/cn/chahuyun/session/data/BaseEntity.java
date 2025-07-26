package cn.chahuyun.session.data;

import cn.chahuyun.session.data.api.ScopeAcquisition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础类
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:26
 */
@Setter
@Getter
@MappedSuperclass
public class BaseEntity implements ScopeAcquisition {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

}
