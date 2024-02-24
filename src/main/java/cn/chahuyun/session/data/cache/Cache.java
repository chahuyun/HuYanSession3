package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.data.entity.GroupList;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;

/**
 * 缓存api
 *
 * @author Moyuyanli
 * @date 2024/1/15 11:29
 */
public interface Cache {

    /**
     * 缓存单一消息
     *
     * @param session 单一消息
     */
    void CacheSession(SingleSession session);

    /**
     * 缓存多词条消息
     *
     * @param session 多词条消息
     */
    void CacheSession(ManySession session);

    /**
     * 缓存定时消息
     *
     * @param session 定时消息
     */
    void CacheSession(TimingSession session);

    /**
     * 缓存劝阻信息
     *
     * @param groupList 群组信息
     */
    void CacheGroupList(GroupList groupList);


}
