package cn.chahuyun.session.config

import cn.chahuyun.session.enums.DataType
import cn.chahuyun.session.enums.PermType
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 插件配置
 *
 * @author Moyuyanli
 * @date 2024/1/3 10:41
 */
object PluginConfig : AutoSavePluginConfig("config") {

    @ValueDescription("主人")
    var owner: Long by value()

    @ValueDescription("数据库链接方式")
    var dataType:DataType by value(DataType.SQLITE)

    @ValueDescription("权限前置")
    var permType:PermType by value(PermType.DEFAULT)
}


