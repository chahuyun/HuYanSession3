package cn.chahuyun.session.entity;

/**
 * 用于获取对于的实体的所属作用域
 *
 * @author Moyuyanli
 * @date 2024/1/3 15:39
 */
public interface ScopeAcquisition {

    /**
     * 获取所属作用域
     * @return 作用域
     */
    Scope getScope();

}
