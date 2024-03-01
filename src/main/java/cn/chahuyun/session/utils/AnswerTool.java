package cn.chahuyun.session.utils;

import cn.hutool.core.util.RandomUtil;

import java.util.List;

/**
 * 回答工具
 *
 * @author Moyuyanli
 * @date 2024/3/1 15:01
 */
public class AnswerTool {

    private AnswerTool() {

    }

    public static String getAnswer(List<String> answer) {
        return answer.get(RandomUtil.randomInt(0, answer.size()));
    }



}
