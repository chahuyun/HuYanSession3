package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.Permission;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 内存中缓存
 *
 * <p>构建时间: 2024/2/24 13:48</p>
 *
 * @author Moyuyanli
 */
public class MemoryCache implements Cache {

    public static Map<Scope, List<SingleSession>> singleSessionMap;
    public static Map<Scope, List<ManySession>> manySessionMap;
    public static Map<Scope, List<TimingSession>> timingSessionMap;
    public static Map<Scope, List<Permission>> permissionMap;


    /**
     * 缓存单一消息
     *
     * @param session 单一消息
     */
    @Override
    public void putSession(SingleSession session) {
        Scope scope = session.getScope();
        if (singleSessionMap.containsKey(scope)) {
            singleSessionMap.get(scope).add(session);
        } else {
            singleSessionMap.put(scope, new ArrayList<>() {{
                add(session);
            }});
        }
    }

    /**
     * 缓存多词条消息
     *
     * @param session 多词条消息
     */
    @Override
    public void putSession(ManySession session) {
        Scope scope = session.getScope();
        if (manySessionMap.containsKey(scope)) {
            manySessionMap.get(scope).add(session);
        } else {
            manySessionMap.put(scope, new ArrayList<>() {{
                add(session);
            }});
        }
    }

    /**
     * 缓存定时消息
     *
     * @param session 定时消息
     */
    @Override
    public void putSession(TimingSession session) {
        Scope scope = session.getScope();
        if (timingSessionMap.containsKey(scope)) {
            timingSessionMap.get(scope).add(session);
        } else {
            timingSessionMap.put(scope, new ArrayList<>() {{
                add(session);
            }});
        }
    }

    /**
     * 缓存权限信息
     *
     * @param permission 权限信息
     */
    @Override
    public void putPermission(Permission permission) {
        Scope scope = permission.getScope();
        if (permissionMap.containsKey(scope)) {
            permissionMap.get(scope).add(permission);
        } else {
            permissionMap.put(scope, new ArrayList<>() {{
                add(permission);
            }});
        }
    }

    /**
     * 获取单一消息<br>
     * 可能为null
     *
     * @param id 消息id
     * @return 单一消息
     */
    @Override
    public SingleSession getSingSession(Integer id) {
        ArrayList<SingleSession> singleSessions = new ArrayList<>();
        singleSessionMap.values().forEach(singleSessions::addAll);
        for (SingleSession singleSession : singleSessions) {
            if (Objects.equals(singleSession.getId(), id)) {
                return singleSession;
            }
        }
        return null;
    }

    /**
     * 获取多词条消息<br>
     * 可能为null
     *
     * @param id 消息id
     * @return 多词条消息
     */
    @Override
    public ManySession getManySession(Integer id) {
        ArrayList<ManySession> manySessions = new ArrayList<>();
        manySessionMap.values().forEach(manySessions::addAll);
        for (ManySession manySession : manySessions) {
            if (Objects.equals(manySession.getId(), id)) {
                return manySession;
            }
        }
        return null;
    }

    /**
     * 获取定时消息<br>
     * 可能为null
     *
     * @param id 消息id
     * @return 定时消息
     */
    @Override
    public TimingSession getTimingSession(Integer id) {
        ArrayList<TimingSession> timingSessions = new ArrayList<>();
        timingSessionMap.values().forEach(timingSessions::addAll);
        for (TimingSession timingSession : timingSessions) {
            if (Objects.equals(timingSession.getId(), id)) {
                return timingSession;
            }
        }
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
        ArrayList<Permission> permissions = new ArrayList<>();
        permissionMap.values().forEach(permissions::addAll);
        for (Permission permission : permissions) {
            if (Objects.equals(permission.getId(), id)) {
                return permission;
            }
        }
        return null;
    }

    /**
     * 删除单一消息
     *
     * @param id 消息id
     */
    @Override
    public void removeSingSession(Integer id) {
        ArrayList<SingleSession> singleSessions = new ArrayList<>();
        singleSessionMap.values().forEach(singleSessions::addAll);
        for (SingleSession singleSession : singleSessions) {
            if (Objects.equals(singleSession.getId(), id)) {
                singleSessionMap.get(singleSession.getScope()).remove(singleSession);
            }
        }
    }

    /**
     * 删除多词条消息
     *
     * @param id 消息id
     */
    @Override
    public void removeManySession(Integer id) {
        ArrayList<ManySession> manySessions = new ArrayList<>();
        manySessionMap.values().forEach(manySessions::addAll);
        for (ManySession manySession : manySessions) {
            if (Objects.equals(manySession.getId(), id)) {
                manySessionMap.get(manySession.getScope()).remove(manySession);
            }
        }
    }

    /**
     * 删除定时消息
     *
     * @param id 消息id
     */
    @Override
    public void removeTimingSession(Integer id) {
        ArrayList<TimingSession> timingSessions = new ArrayList<>();
        timingSessionMap.values().forEach(timingSessions::addAll);
        for (TimingSession timingSession : timingSessions) {
            if (Objects.equals(timingSession.getId(), id)) {
                timingSessionMap.get(timingSession.getScope()).remove(timingSession);
            }
        }
    }

    /**
     * 删除权限信息
     *
     * @param id 权限信息
     */
    @Override
    public void removePermissions(Integer id) {
        ArrayList<Permission> permissions = new ArrayList<>();
        permissionMap.values().forEach(permissions::addAll);
        for (Permission permission : permissions) {
            if (Objects.equals(permission.getId(), id)) {
                permissionMap.get(permission.getScope()).remove(permission);
            }
        }
    }
}
