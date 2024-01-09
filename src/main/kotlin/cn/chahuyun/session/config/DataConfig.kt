package cn.chahuyun.session.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object DataConfig : AutoSavePluginConfig("dataConfig") {

    @ValueDescription("mysql数据库链接地址")
    val url: String by value("localhost:3306/huyan?autoReconnect=true")

    @ValueDescription("mysql数据库用户名")
    val user: String by value("root")

    @ValueDescription("mysql数据库密码")
    val password:String by value("123456")


}