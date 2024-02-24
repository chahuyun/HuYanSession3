package cn.chahuyun.session.data.api;

import cn.chahuyun.session.data.Scope;

/**
 * 用于获取对于的实体的所属作用域
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:39
 */
public interface ScopeAcquisition {

    /**
     * 获取所属作用域
     *
     * @return 作用域
     */
    Scope getScope();

    /**
     * 设置作用域，将他的mark保存
     *
     * @param scope 作用域
     */
    void setScope(Scope scope);

}
