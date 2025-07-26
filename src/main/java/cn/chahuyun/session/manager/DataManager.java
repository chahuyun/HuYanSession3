package cn.chahuyun.session.manager;

import cn.chahuyun.hibernateplus.Configuration;
import cn.chahuyun.hibernateplus.DriveType;
import cn.chahuyun.hibernateplus.HibernatePlusService;
import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.config.SessionDataConfig;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;


/**
 * 数据库管理
 *
 * @author Moyuyanli
 * @date 2024/1/3 10:15
 */
@Slf4j(topic = Constant.LOG_TOPIC)
public class DataManager {


    /**
     * 加载数据库
     *
     * @param plugin 插件
     */
    public static void init(HuYanSession plugin) {
        Configuration configuration = HibernatePlusService.createConfiguration(HuYanSession.class);

        var config = SessionPluginConfig.INSTANCE;
        var dataConfig = SessionDataConfig.INSTANCE;

        switch (config.getDataType()) {
            case SQLITE:
                configuration.setDriveType(DriveType.SQLITE);
                configuration.setAddress(plugin.getDataFolderPath().resolve("session").toString());
                break;
            case MYSQL:
                configuration.setDriveType(DriveType.MYSQL);
                configuration.setAddress(dataConfig.getUrl());
                configuration.setUser(dataConfig.getUser());
                configuration.setPassword(dataConfig.getPassword());
                break;
            case H2:
            default:
                configuration.setDriveType(DriveType.H2);
                configuration.setAddress(plugin.getDataFolderPath().resolve("session.h2").toString());
                break;
        }


        try {
            HibernatePlusService.loadingService(configuration);
        } catch (HibernateException e) {
            log.error("数据库初始化错误!", e);
        }
    }

}
