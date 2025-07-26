package cn.chahuyun.session.command

import cn.chahuyun.session.HuYanSession
import cn.chahuyun.session.data.DataService
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand


class SessionCommand : CompositeCommand(
    HuYanSession.INSTANCE, "hys",
    description = "HuYanSession-3 Command"
) {


    @SubCommand("v")
    @Description("查询当前壶言会话3版本")
    suspend fun CommandSender.version() {
        sendMessage("当前壶言会话3版本 ${HuYanSession.VERSION}")
    }

    @SubCommand("owner")
    @Description("设置主人")
    suspend fun CommandSender.owner(owner: Long) {
        HuYanSession.pluginConfig.owner = owner
        sendMessage("已将主人设置为:${owner}")
    }

    @SubCommand("ref")
    @Description("刷新缓存")
    suspend fun CommandSender.refresh() {

        DataService.refresh()
        sendMessage("缓存刷新成功！")
    }


    @SubCommand("repair")
    @Description("修复")
    suspend fun CommandSender.repair() {

        DataService.refresh()
        sendMessage("缓存刷新成功！")
    }

}