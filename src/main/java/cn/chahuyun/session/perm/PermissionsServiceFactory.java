package cn.chahuyun.session.perm;

import cn.chahuyun.api.permission.api.HuYanPermissionService;
import cn.chahuyun.session.manager.PluginManager;

/**
 * 权限工厂
 *
 * @author Moyuyanli
 * @Date 2024/2/22 22:10
 */
public class PermissionsServiceFactory {

    private final HuYanPermissionService permissionService;

    private static PermissionsServiceFactory instance;

    public PermissionsServiceFactory(HuYanPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * 获取权限工厂实例
     *
     * @return 权限工厂
     */
    public static PermissionsServiceFactory getInstance() {
        return instance != null ? instance : new PermissionsServiceFactory(PluginManager.INSTANCE.getPermissionService());
    }

    /**
     * 获取权限服务
     *
     * @return PermissionService 权限服务
     */
    public HuYanPermissionService getPermissionService() {
        return permissionService;
    }
}
