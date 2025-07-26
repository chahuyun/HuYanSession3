package cn.chahuyun.session.data;

import cn.chahuyun.hibernateplus.HibernateFactory;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.entity.*;
import lombok.val;

/**
 * 抽象数据操作
 *
 * @author Moyuyanli
 * @Date 2024/2/24 13:14
 */
public abstract class DataService {

    /**
     * 获取分组信息，通过分组名称
     *
     * @param name 分组名称
     * @return 分组信息
     */
    public static GroupedLists getGroupedLists(String name) {
        GroupedLists groupedLists = HibernateFactory.selectOne(GroupedLists.class, "name", name);
        if (groupedLists == null) {
            throw new RuntimeException("根据分组名查询分组信息错误:结果为空!");
        }
        return groupedLists;
    }


    public static void refresh() {
        val singleSessions = HibernateFactory.selectList(SingleSession.class);
        val manySessions = HibernateFactory.selectList(ManySession.class);
        val timingSessions = HibernateFactory.selectList(TimingSession.class);
        val permissions = HibernateFactory.selectList(Permission.class);

        val cacheService = CacheFactory.getInstall().getCacheService();

        singleSessions.forEach(cacheService::putSession);
        manySessions.forEach(cacheService::putSession);
        timingSessions.forEach(cacheService::putSession);
        permissions.forEach(cacheService::putPermission);
    }

}
