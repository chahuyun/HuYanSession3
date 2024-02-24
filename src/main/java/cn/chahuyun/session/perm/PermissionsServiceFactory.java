package cn.chahuyun.session.perm;

import cn.chahuyun.api.permission.api.HuYanPermissionService;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限工厂
 *
 * @author Moyuyanli
 * @Date 2024/2/22 22:10
 */
@Slf4j
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
        if (permissionService == null) {
            throw new RuntimeException("权限工厂初始化失败:权限服务为空!");
        }
        instance = new PermissionsServiceFactory(permissionService);
        log.debug("权限服务初始化完成!");
    }

    /**
     * 获取唯一权限工厂实例
     *
     * @return 权限工厂
     */
    public static PermissionsServiceFactory getInstance() {
        if (instance == null) {
            throw new RuntimeException("权限工厂获取失败:未初始化权限工厂!");
        }
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
