package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.Permission;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;

import java.util.*;

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

    public MemoryCache init() {
        singleSessionMap = new HashMap<>();
        manySessionMap = new HashMap<>();
        timingSessionMap = new HashMap<>();
        permissionMap = new HashMap<>();
        return this;
    }

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
     * 获取单一消息
     *
     * @param scope 作用域
     * @return 单一消息集合
     */
    @Override
    public List<SingleSession> getSingSession(Scope scope) {
        return singleSessionMap.get(scope);
    }

    /**
     * 获取多词条消息
     *
     * @param scope 作用域
     * @return 多词条消息集合
     */
    @Override
    public List<ManySession> getManySession(Scope scope) {
        return manySessionMap.get(scope);
    }

    /**
     * 获取定时消息
     *
     * @param scope 作用域
     * @return 定时消息集合
     */
    @Override
    public List<TimingSession> getTimingSession(Scope scope) {
        return timingSessionMap.get(scope);
    }

    /**
     * 获取权限信息
     *
     * @param scope 作用域
     * @return 权限信息集合
     */
    @Override
    public List<Permission> getPermissions(Scope scope) {
        return permissionMap.get(scope);
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

    /**
     * 获取用于消息匹配的作用域<br>
     * 需要按照设置中的顺序进行排序
     *
     * @return List<Scope> 作用域集合
     */
    @Override
    public List<Scope> getMatchSingSessionScope() {
        if (singleSessionMap.isEmpty()) {
            return new ArrayList<>();
        }
        HashSet<Scope> scopes = new HashSet<>(singleSessionMap.keySet());
        return scopeSort(scopes);
    }

    /**
     * 获取用于多词条消息匹配的作用域<br>
     * 需要按照设置中的顺序进行排序
     *
     * @return List<Scope> 作用域集合
     */
    @Override
    public List<Scope> getMatchManySessionScope() {
        if (manySessionMap.isEmpty()) {
            return new ArrayList<>();
        }
        HashSet<Scope> scopes = new HashSet<>(manySessionMap.keySet());
        return scopeSort(scopes);
    }

    /**
     * 获取用于定时消息的作用域<br>
     * 需要按照设置中的顺序进行排序
     *
     * @return List<Scope> 作用域集合
     */
    @Override
    public List<Scope> getMatchTimingScope() {
        if (timingSessionMap.isEmpty()) {
            return new ArrayList<>();
        }
        HashSet<Scope> scopes = new HashSet<>(timingSessionMap.keySet());
        return scopeSort(scopes);
    }

    /**
     * 获取用于权限信息的作用域<br>
     * 需要按照设置中的顺序进行排序
     *
     * @return List<Scope> 作用域集合
     */
    @Override
    public List<Scope> getMatchPermScope() {
        if (permissionMap.isEmpty()) {
            return new ArrayList<>();
        }
        HashSet<Scope> scopes = new HashSet<>(permissionMap.keySet());
        return scopeSort(scopes);
    }


    /**
     * 对作用域进行设置好的顺序进行排序
     *
     * @param scopes set scope
     * @return 排序后的List
     */
    private List<Scope> scopeSort(HashSet<Scope> scopes) {
        // 将HashSet转为List以支持排序操作
        List<Scope> sortedScopes = new ArrayList<>(scopes);


        SessionPluginConfig config = HuYanSession.pluginConfig;
        // 定义Comparator用于比较Scope的Type在scopeSort列表中的位置
        Comparator<Scope> scopeComparator = (s1, s2) -> {
            int index1 = config.getScopeSort().indexOf(s1.getType());
            int index2 = config.getScopeSort().indexOf(s2.getType());

            // 如果两者都在范围内，则按索引值排序，否则保持原有顺序
            if (index1 != -1 && index2 != -1) {
                return Integer.compare(index1, index2);
            } else {
                return Integer.compare(index1 == -1 ? Integer.MAX_VALUE : index1,
                        index2 == -1 ? Integer.MAX_VALUE : index2);
            }
        };

        // 对sortedScopes进行排序
        sortedScopes.sort(scopeComparator);
        return sortedScopes;
    }
}
