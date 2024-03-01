package cn.chahuyun.session.manager;

import cn.chahuyun.session.HuYanSession;
import cn.chahuyun.session.config.SessionDataConfig;
import cn.chahuyun.session.config.SessionPluginConfig;
import cn.chahuyun.session.data.factory.DataFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import xyz.cssxsh.mirai.hibernate.MiraiHibernateConfiguration;

import java.util.Map;
import java.util.Properties;


/**
 * 数据库管理
 *
 * @author Moyuyanli
 * @date 2024/1/3 10:15
 */
@Slf4j(topic = "HuYanSession3")
public class DataManager {

    /**
     * Hibernate数据库驱动
     */
    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS_H2 = "org.h2.Driver";
    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS_SQLITE = "org.sqlite.JDBC";
    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS_MYSQL = "com.mysql.cj.jdbc.Driver";
    /**
     * 数据库连接前缀
     */
    private static final String H2_BASE_PATH = "jdbc:h2:file:./data/cn.chahuyun.huyan-session-3/HuYan";
    private static final String SQLITE_BASE_PATH = "jdbc:sqlite:file:./data/cn.chahuyun.huyan-session-3/HuYan.sqlite";
    private static final String MYSQL_BASE_PATH = "jdbc:mysql://";
    private static final SessionPluginConfig config = HuYanSession.pluginConfig;

    private static final SessionDataConfig dataConfig = HuYanSession.dataConfig;

    /**
     * 加载数据库
     *
     * @param plugin 插件
     */
    public static void init(HuYanSession plugin) {
        MiraiHibernateConfiguration configuration = new MiraiHibernateConfiguration(plugin);
        switch (config.getDataType()) {
            case SQLITE:
                sqliteBase(configuration);
                break;
            case MYSQL:
                mySqlBase(configuration);
                break;
            case H2:
            default:
                h2Base(configuration);
                break;
        }

        SessionFactory sessionFactory = null;
        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            log.error("数据库初始化错误!", e);
        }
        DataFactory.dataFactoryLoad(sessionFactory);

    }

    /**
     * sqlite数据库配置
     *
     * @param configuration config
     */
    private static Properties sqliteBase(MiraiHibernateConfiguration configuration) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", SQLITE_BASE_PATH);
        properties.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
        properties.setProperty("hibernate.connection.driver_class", HIBERNATE_CONNECTION_DRIVER_CLASS_SQLITE);
        properties.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        properties.setProperty("hibernate.connection.isolation", "1");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate-connection-autocommit", "true");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            configuration.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
        return properties;
    }


    /**
     * H2数据库配置
     *
     * @param configuration config
     */
    private static Properties h2Base(MiraiHibernateConfiguration configuration) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", H2_BASE_PATH);
        properties.setProperty("hibernate.connection.driver_class", HIBERNATE_CONNECTION_DRIVER_CLASS_H2);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        properties.setProperty("hibernate.hikari.connectionTimeout", "180000");
        properties.setProperty("hibernate.connection.isolation", "1");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate-connection-autocommit", "true");
        properties.setProperty("hibernate.autoReconnect", "true");
        properties.setProperty("hibernate.connection.username", "");
        properties.setProperty("hibernate.connection.password", "");
        properties.setProperty("hibernate.current_session_context_class", "thread");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            configuration.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
        return properties;
    }

    /**
     * MySql数据库配置
     *
     * @param configuration config
     */
    private static Properties mySqlBase(MiraiHibernateConfiguration configuration) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", MYSQL_BASE_PATH + dataConfig.getUrl());
        properties.setProperty("hibernate.connection.driver_class", HIBERNATE_CONNECTION_DRIVER_CLASS_MYSQL);
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.connection.CharSet", "utf8mb4");
        properties.setProperty("hibernate.connection.useUnicode", "true");
        properties.setProperty("hibernate.connection.username", dataConfig.getUser());
        properties.setProperty("hibernate.connection.password", dataConfig.getPassword());
        properties.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        properties.setProperty("hibernate.connection.isolation", "1");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.autoReconnect", "true");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            configuration.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
        return properties;
    }

}
