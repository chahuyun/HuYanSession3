package cn.chahuyun.session.perm;

import cn.chahuyun.api.permission.api.HuYanPermissionService;

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
     * 初始化权限工厂
     */
    public static void init(HuYanPermissionService permissionService) {
        instance = new PermissionsServiceFactory(permissionService);
    }

    /**
     * 获取唯一权限工厂实例
     *
     * @return 权限工厂
     */
    public static PermissionsServiceFactory getInstance() {
        return instance;
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