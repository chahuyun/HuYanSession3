package cn.chahuyun.session.data;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限用户
 *
 * @author Moyuyanli
 * @date 2024/3/22 16:35
 */
@Setter
@Getter
public class PermUser {

    private boolean admin;
    private boolean session;
    private boolean hh;
    private boolean dct;
    private boolean ds;

    public PermUser() {
        this.admin = false;
        this.session = false;
        this.hh = false;
        this.dct = false;
        this.ds = false;
    }

    public PermUser(boolean admin, boolean session, boolean hh, boolean dct, boolean ds) {
        this.admin = admin;
        this.session = session;
        this.hh = hh;
        this.dct = dct;
        this.ds = ds;
    }

    /**
     * 该用户是否存在权限
     *
     * @return true 该用户存在某一项权限<br>
     * false 该用户一项权限都没有
     */
    public boolean isExist() {
        return admin || session || hh || ds || dct;
    }
}
