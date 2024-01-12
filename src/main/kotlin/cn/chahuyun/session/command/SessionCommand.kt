package cn.chahuyun.session.command

import cn.chahuyun.session.HuYanSession
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand

class SessionCommand : CompositeCommand(
    HuYanSession.INSTANCE, "hys",
    description = "HuYanSession-3 Command"
) {


    @SubCommand("v")
    suspend fun CommandSender.version() {
        sendMessage("当前壶言会话3版本 ${HuYanSession.VERSION}")
    }
}