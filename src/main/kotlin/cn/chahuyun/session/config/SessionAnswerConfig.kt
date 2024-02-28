package cn.chahuyun.session.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 回答的自定义配置
 */
object SessionAnswerConfig : AutoSavePluginConfig("answerConfig") {

    @ValueDescription("学习成功的回答")
    val studySuccess: List<String> by value(arrayListOf<String>().apply {
        add("学废了！")
    })

    @ValueDescription("学习失败的回答")
    val studyFailed: List<String> by value(arrayListOf<String>().apply {
        add("学不废！")
    })
}