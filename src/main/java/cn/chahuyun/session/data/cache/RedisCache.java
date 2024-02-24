package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.data.entity.GroupList;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;

/**
 * redis中缓存
 *
 * <p>构建时间: 2024/2/24 13:48</p>
 *
 * @author Moyuyanli
 */
public class RedisCache implements Cache {
    /**
     * 缓存单一消息
     *
     * @param session 单一消息
     */
    @Override
    public void CacheSession(SingleSession session) {

    }

    /**
     * 缓存多词条消息
     *
     * @param session 多词条消息
     */
    @Override
    public void CacheSession(ManySession session) {

    }

    /**
     * 缓存定时消息
     *
     * @param session 定时消息
     */
    @Override
    public void CacheSession(TimingSession session) {

    }

    /**
     * 缓存劝阻信息
     *
     * @param groupList 群组信息
     */
    @Override
    public void CacheGroupList(GroupList groupList) {

    }
}
