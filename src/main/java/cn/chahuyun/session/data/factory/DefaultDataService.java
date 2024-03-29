package cn.chahuyun.session.data.factory;

import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认数据操作服务
 *
 * @author Moyuyanli
 * @Date 2024/2/24 13:16
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class DefaultDataService extends AbstractDataService {

    protected DefaultDataService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * 查询单一实体
     *
     * @param tClass 查询的实体类
     * @param hql    hql,会被String.format一次，参数会按照顺序传递
     * @param params 如果有条件，条件参数
     * @param <T>    结果类
     * @return 查询到的单一结果, 可能为null
     */
    public <T> T selectResultEntity(Class<T> tClass, @Language("hql") String hql, Object... params) {
        T t = null;
        try {
            t = this.sessionFactory.fromSession(session -> session.createQuery(String.format(hql, params), tClass).getSingleResultOrNull());
        } catch (Exception e) {
            log.error("查询实体错误", e);
        }
        return t;
    }

    /**
     * 查询集合实体
     *
     * @param tClass 查询的实体类
     * @param hql    hql,会被String.format一次，参数会按照顺序传递
     * @param params 如果有条件，条件参数
     * @param <T>    结果类
     * @return 查询到的全部结果, 不会为null，请用.isEmpty()判断空
     */
    public <T> List<T> selectListEntity(Class<T> tClass, @Language("hql") String hql, Object... params) {
        List<T> list;
        try {
            list = this.sessionFactory.fromSession(session -> session.createQuery(String.format(hql, params), tClass).getResultList());
        } catch (Exception e) {
            log.error("查询错误", e);
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 合并实体
     *
     * @param entity 需要保存或修改的实体
     * @return T 保存或修改后的实体
     * @author Moyuyanli
     * @date 2024/2/21 22:13
     */
    public <T> T mergeEntity(T entity) {
        T result = null;
        try {
            result = this.sessionFactory.fromTransaction(session -> session.merge(entity));
        } catch (Exception e) {
            log.error("保存实体错误", e);
        }
        return result;
    }


    /**
     * 合并实体
     *
     * @param entity 需要保存或修改的实体
     * @return boolean true 保存或修改成功
     * @author Moyuyanli
     * @date 2024/2/21 22:13
     */
    public boolean mergeEntityStatus(Object entity) {
        try {
            Object o = this.sessionFactory.fromTransaction(session -> session.merge(entity));
            if (o instanceof BaseEntity) {
                return ((BaseEntity) o).getId() != null;
            }
        } catch (Exception e) {
            log.error("保存实体错误", e);
            return false;
        }
        return true;
    }

    /**
     * 删除实体
     *
     * @param entity 需要删除的实体
     * @return boolean true 删除成功
     * @author Moyuyanli
     * @date 2024/2/21 22:17
     */
    @Override
    public boolean deleteEntity(Object entity) {
        try {
            this.sessionFactory.inTransaction(session -> session.remove(entity));
        } catch (Exception e) {
            log.error("保存实体错误", e);
            return false;
        }
        return true;
    }

    /**
     * 删除实体
     *
     * @param hql    hql,会被String.format一次，参数会按照顺序传递
     * @param params 参数
     * @return boolean true 删除成功
     * @author Moyuyanli
     * @date 2024/2/21 22:17
     */
    @Override
    public boolean deleteEntity(String hql, Object... params) {
        try {
            return this.sessionFactory.fromTransaction(session -> session.createMutationQuery(String.format(hql, params)).executeUpdate()) != 0;
        } catch (Exception e) {
            log.error("查询实体错误", e);
            return false;
        }
    }
}
