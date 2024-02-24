package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.Permission;
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
    public void putSession(SingleSession session) {

    }

    /**
     * 缓存多词条消息
     *
     * @param session 多词条消息
     */
    @Override
    public void putSession(ManySession session) {

    }

    /**
     * 缓存定时消息
     *
     * @param session 定时消息
     */
    @Override
    public void putSession(TimingSession session) {

    }

    /**
     * 缓存权限信息
     *
     * @param permission 权限信息
     */
    @Override
    public void putPermission(Permission permission) {

    }

    /**
     * 获取单一消息
     *
     * @param id 消息id
     * @return 单一消息
     */
    @Override
    public SingleSession getSingSession(Integer id) {
        return null;
    }

    /**
     * 获取多词条消息
     *
     * @param id 消息id
     * @return 多词条消息
     */
    @Override
    public ManySession getManySession(Integer id) {
        return null;
    }

    /**
     * 获取定时消息
     *
     * @param id 消息id
     * @return 定时消息
     */
    @Override
    public TimingSession getTimingSession(Integer id) {
        return null;
    }

    /**
     * 获取权限信息
     *
     * @param id 权限信息id
     * @return 权限信息
     */
    @Override
    public Permission getPermissions(Integer id) {
        return null;
    }

    /**
     * 删除单一消息
     *
     * @param id 消息id
     */
    @Override
    public void removeSingSession(Integer id) {

    }

    /**
     * 删除多词条消息
     *
     * @param id 消息id
     */
    @Override
    public void removeManySession(Integer id) {

    }

    /**
     * 删除定时消息
     *
     * @param id 消息id
     */
    @Override
    public void removeTimingSession(Integer id) {

    }

    /**
     * 删除权限信息
     *
     * @param id 权限信息
     */
    @Override
    public void removePermissions(Integer id) {

    }
}
