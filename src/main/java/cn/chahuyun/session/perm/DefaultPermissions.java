package cn.chahuyun.session.perm;

import cn.chahuyun.api.permission.abnormal.NotExistHuYanPermissionException;
import cn.chahuyun.api.permission.api.HuYanPermissionService;

/**
 * 默认权限实现
 *
 * @author Moyuyanli
 * @Date 2024/2/22 22:09
 */
public class DefaultPermissions implements HuYanPermissionService {
    @Override
    public boolean isPermissions(String id, String permCode) throws NotExistHuYanPermissionException {
        return false;
    }

    @Override
    public boolean addPermissions(String id, String permCode) throws NotExistHuYanPermissionException {
        return false;
    }

    @Override
    public boolean delPermissions(String id, String permCode) throws NotExistHuYanPermissionException {
        return false;
    }

    @Override
    public boolean cleanPermissions(String id) {
        return false;
    }

    @Override
    public boolean cleanPermUser(String id) {
        return false;
    }
}
