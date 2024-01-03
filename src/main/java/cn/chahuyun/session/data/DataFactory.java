package cn.chahuyun.session.data;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

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

    public <T> T selectEntity(String sql, Class<T> tClass) {
        T t = null;
        try {
            t = this.sessionFactory.fromSession(session -> session.createQuery(sql, tClass).getSingleResultOrNull());
        } catch (Exception e) {
            log.error("查询错误",e);
        }
        return t;
    }
}
