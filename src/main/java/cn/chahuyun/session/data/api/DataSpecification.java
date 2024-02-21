package cn.chahuyun.session.data.api;

import cn.chahuyun.session.data.BaseEntity;
import org.intellij.lang.annotations.Language;

import java.util.List;

/**
 * 用于操作数据库的统一api
 *
 * @author Moyuyanli
 * @Date 2024/2/21 22:09
 */
public interface DataSpecification {

    /**
     * 查询单一实体
     *
     * @param tClass 查询的实体类
     * @param hql    hql,会被String.format一次，参数会按照顺序传递
     * @param params 如果有条件，条件参数
     * @param <T>    结果类
     * @return 查询到的单一结果, 可能为null
     */
    <T> T selectResultEntity(Class<T> tClass, @Language("hql") String hql, Object... params);

    /**
     * 查询集合实体
     *
     * @param tClass 查询的实体类
     * @param hql    hql,会被String.format一次，参数会按照顺序传递
     * @param params 如果有条件，条件参数
     * @param <T>    结果类
     * @return 查询到的全部结果, 不会为null，请用.isEmpty()判断空
     */
    <T> List<T> selectListEntity(Class<T> tClass, @Language("hql") String hql, Object... params);

    /**
     * 合并实体
     *
     * @param entity 需要保存或修改的实体
     * @return T 保存或修改后的实体
     * @author Moyuyanli
     * @date 2024/2/21 22:13
     */
    <T> T mergeEntity(T entity);


    /**
     * 合并实体
     *
     * @param entity 需要保存或修改的实体
     * @return boolean true 保存或修改成功
     * @author Moyuyanli
     * @date 2024/2/21 22:13
     */
    boolean mergeEntityStatus(Object entity);

    /**
     * 删除实体
     *
     * @param entity 需要删除的实体
     * @return boolean true 删除成功
     * @author Moyuyanli
     * @date 2024/2/21 22:17
     */
    boolean deleteEntity(Object entity);

    /**
     * 删除实体
     *
     * @param hql hql,会被String.format一次，参数会按照顺序传递
     * @param params 参数
     * @return boolean true 删除成功
     * @author Moyuyanli
     * @date 2024/2/21 22:17
     */
    boolean deleteEntity(@Language("hql") String hql, Object... params);


}