package cn.chahuyun.session.data.factory;

import cn.chahuyun.api.data.api.DataSpecification;
import org.hibernate.SessionFactory;

/**
 * 抽象数据操作
 *
 * @author Moyuyanli
 * @Date 2024/2/24 13:14
 */
public abstract class AbstractDataService implements DataSpecification {

    protected final SessionFactory sessionFactory;

    protected AbstractDataService(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;

    }

}
