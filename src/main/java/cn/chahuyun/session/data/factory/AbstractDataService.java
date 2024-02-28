package cn.chahuyun.session.data.factory;

import cn.chahuyun.api.data.api.DataSpecification;
import cn.chahuyun.session.data.entity.GroupedLists;
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

    /**
     * 获取分组信息，通过分组名称
     *
     * @param name 分组名称
     * @return 分组信息
     */
    public GroupedLists getGroupedLists(String name) {
        GroupedLists groupedLists = selectResultEntity(GroupedLists.class, "from GroupedLists where name = '%s'", name);
        if (groupedLists == null) {
            throw new RuntimeException("根据分组名查询分组信息错误:结果为空!");
        }
        return groupedLists;
    }

}
