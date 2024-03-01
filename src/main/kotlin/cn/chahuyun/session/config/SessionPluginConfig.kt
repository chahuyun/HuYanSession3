package cn.chahuyun.session.config

import cn.chahuyun.session.data.Scope
import cn.chahuyun.session.enums.CacheType
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
object SessionPluginConfig : AutoSavePluginConfig("config") {

    @ValueDescription("主人")
    var owner: Long by value()

    @ValueDescription("数据库链接方式")
    var dataType: DataType by value(DataType.H2)

    @ValueDescription("权限类型")
    var permType: PermType by value(PermType.DEFAULT)

    @ValueDescription("缓存类型")
    var cacheType: CacheType by value(CacheType.MEMORY)

    @ValueDescription("是否默认开起本地缓存")
    var localCache: Boolean by value(false)

    @ValueDescription("是否匹配全部消息(true:响应所有能匹配成功的消息;false:只响应按照下列顺序匹配到的第一个)")
    var matchAll: Boolean by value(false)

    @ValueDescription("数据查询排序(也可以理解为匹配优先级)")
    val scopeSort: List<Scope.Type> by value(arrayListOf<Scope.Type>().apply {
        add(Scope.Type.USERS)
        add(Scope.Type.LIST)
        add(Scope.Type.GROUP_MEMBERS)
        add(Scope.Type.GROUP_MEMBER)
        add(Scope.Type.GROUP)
        add(Scope.Type.GLOBAL_USER)
        add(Scope.Type.GLOBAL)
    })
}


