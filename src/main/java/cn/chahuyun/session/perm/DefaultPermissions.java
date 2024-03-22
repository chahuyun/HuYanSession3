package cn.chahuyun.session.perm;

import cn.chahuyun.api.permission.abnormal.NotExistHuYanPermissionException;
import cn.chahuyun.api.permission.api.HuYanPermissionService;
import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.cache.Cache;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.entity.Permission;
import cn.chahuyun.session.data.factory.AbstractDataService;
import cn.chahuyun.session.data.factory.DataFactory;

import java.util.List;
import java.util.Optional;

/**
 * 默认权限实现
 *
 * @author Moyuyanli
 * @Date 2024/2/22 22:09
 */
public class DefaultPermissions implements HuYanPermissionService {

    private static final List<String> permList = Constant.HUYAN_SESSION_PERM_LIST;

    private final Cache cacheService;
    private final AbstractDataService dataService;

    public DefaultPermissions() {
        cacheService = CacheFactory.getInstall().getCacheService();
        dataService = DataFactory.getInstance().getDataService();
    }

    @Override
    public boolean isPermissions(String id, String permCode) throws NotExistHuYanPermissionException {
        Scope scope;
        try {
            scope = Scope.fromScopeMarker(id);
        } catch (Exception e) {
            return false;
        }
        if (!permList.contains(permCode)) {
            throw new NotExistHuYanPermissionException();
        }
        List<Permission> permissions = cacheService.getPermissions(scope);
        return Optional.ofNullable(permissions)
                .map(p -> p.stream()
                        .anyMatch(permission -> permission.getPermCode().equals(permCode)))
                .orElse(false);
    }

    @Override
    public boolean addPermissions(String id, String permCode) throws NotExistHuYanPermissionException {
        if (isPermissions(id, permCode)) {
            return true;
        }

        Scope scope;
        try {
            scope = Scope.fromScopeMarker(id);
        } catch (Exception e) {
            return false;
        }
        if (!permList.contains(permCode)) {
            throw new NotExistHuYanPermissionException();
        }

        Permission permission = new Permission();
        permission.setPermCode(permCode);
        permission.setScope(scope);
        if (!dataService.mergeEntityStatus(permission)) {
            return false;
        }

        cacheService.putPermission(permission);
        return true;
    }

    @Override
    public boolean delPermissions(String id, String permCode) throws NotExistHuYanPermissionException {
        if (!isPermissions(id, permCode)) {
            return true;
        }

        Scope scope;
        try {
            scope = Scope.fromScopeMarker(id);
        } catch (Exception e) {
            return false;
        }
        if (!permList.contains(permCode)) {
            throw new NotExistHuYanPermissionException();
        }

        List<Permission> permissions = cacheService.getPermissions(scope);
        return permissions
                .stream()
                .filter(it -> it.getPermCode().equals(permCode))
                .findFirst()
                .map(it -> {
                    boolean deleteEntity = dataService.deleteEntity(it);
                    if (deleteEntity) {
                        cacheService.removePermissions(it.getId());
                        return true;
                    } else {
                        return false;
                    }
                }).orElse(false);
    }

    @Override
    public boolean cleanPermissions(String id) {
        Scope scope;
        try {
            scope = Scope.fromScopeMarker(id);
        } catch (Exception e) {
            return false;
        }

        List<Permission> permissions = cacheService.getPermissions(scope);
        if (permissions == null || permissions.isEmpty()) {
            return true;
        }

        for (Permission permission : permissions) {
            if (dataService.deleteEntity(permission)) {
                cacheService.removePermissions(permission.getId());
            }
        }

        return true;
    }

    @Override
    @Deprecated(forRemoval = true)
    public boolean cleanPermUser(String id) {
        return false;
    }
}
