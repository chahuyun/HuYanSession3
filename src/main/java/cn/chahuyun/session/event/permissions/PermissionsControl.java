package cn.chahuyun.session.event.permissions;

import cn.chahuyun.session.constant.Constant;
import cn.chahuyun.session.data.ParameterSet;
import cn.chahuyun.session.data.Scope;
import cn.chahuyun.session.data.cache.Cache;
import cn.chahuyun.session.data.cache.CacheFactory;
import cn.chahuyun.session.data.entity.Permission;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.List;

/**
 * 权限控制
 *
 * @author Moyuyanli
 * @date 2024/3/8 13:52
 */
public class PermissionsControl {

    public static final PermissionsControl INSTANCE = new PermissionsControl();


    private PermissionsControl() {
    }

    /**
     * 添加权限
     * @param messages 消息
     * @param subject 发送载体
     * @param sender 发送者
     */
    public void addPermissions(MessageChain messages, Contact subject, User sender) {
        String content = messages.contentToString();
        String code = messages.serializeToMiraiCode();

        String[] split = code.split(" +");
        Scope scope = new Scope(Scope.Type.GROUP);
        ParameterSet parameterSet = new ParameterSet(scope, subject, split[0]);
        scope = parameterSet.getScope();

        Cache cacheService = CacheFactory.getInstall().getCacheService();
        List<Permission> permissions = cacheService.getPermissions(scope);
        Permission permission;
        if (permissions.isEmpty()) {
            permission = new Permission();
        } else {
            permission = permissions.get(0);
        }

        MessageChainBuilder chainBuilder = new MessageChainBuilder();
        chainBuilder.append("为").append(scope.toString()).append("添加一下权限:\n");


        boolean allFilesExist = false;

        for (int i = 1; i < split.length; i++) {
            String s = split[i];
            if (Constant.HUYAN_SESSION_PERM_LIST.contains(s)) {

            }
        }




    }



}


