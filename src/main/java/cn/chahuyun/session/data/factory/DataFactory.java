package cn.chahuyun.session.data.factory;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据工具
 *
 * @author Moyuyanli
 * @date 2024/1/3 13:17
 */
@Slf4j
public class DataFactory {

    public static DataFactory INSTANCE;

    private final SessionFactory sessionFactory;

    private DataFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void dataFactoryLoad(SessionFactory sessionFactory) {
        INSTANCE = new DataFactory(sessionFactory);
    }

    /**
     * 查询单一实体
     *
     * @param tClass 查询的实体类
     * @param hql    hql
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
     * @param hql    hql
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


    public <T> T mergeEntity(T t) {
        T entity = null;
        try {
            entity = this.sessionFactory.fromTransaction(session -> mergeEntity(t));
        } catch (Exception e) {
            log.error("保存实体错误", e);
        }
        return entity;
    }


    public boolean mergeEntityStatus(Object o) {
        try {
            this.sessionFactory.fromTransaction(session -> mergeEntity(o));
        } catch (Exception e) {
            log.error("保存实体错误", e);
            return false;
        }
        return true;
    }

}
