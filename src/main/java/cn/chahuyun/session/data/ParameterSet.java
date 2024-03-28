package cn.chahuyun.session.data;

import cn.chahuyun.session.enums.MatchTriggerType;
import cn.chahuyun.session.enums.MessageConversionType;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;

import static cn.chahuyun.session.HuYanSession.pluginConfig;

/**
 * 用于识别的参数集
 *
 * @author Moyuyanli
 * @date 2024/3/7 14:05
 */
public class ParameterSet {

    /**
     * 匹配方式
     */
    private MatchTriggerType matchTriggerType = MatchTriggerType.PRECISION;
    /**
     * 消息转换类型
     */
    private MessageConversionType conversionType = MessageConversionType.MIRAI_CODE;
    /**
     * 是否开启本地缓存
     */
    private boolean localCache = pluginConfig.getLocalCache();
    /**
     * 是否开启动态消息
     */
    private boolean dynamic = false;
    /**
     * 是否被重写
     */
    private boolean rewrite = false;
    /**
     * 回复触发概率
     */
    private double probability = 1.0;
    /**
     * 作用域
     */
    private Scope scope;
    /**
     * 载体
     */
    private Contact subject;
    /**
     * 匹配id
     */
    private Long id;
    /**
     * 是否异常
     */
    private boolean exception = false;
    /**
     * 异常信息
     */
    private String exceptionMsg;

    public ParameterSet(String... params) {
        identification(params);
    }

    public ParameterSet(Scope scope, String... params) {
        identification(params);
        this.scope = scope;
    }

    public ParameterSet(Scope scope, Contact subject, String... params) {
        identification(params);
        this.scope = scope;
        this.subject = subject;
    }


    private void identification(String... params) {
        for (String param : params) {
            switch (param) {
                case "全局":
                case "global":
                case "0":
                    scope = new Scope(Scope.Type.GLOBAL);
                    continue;
                case "模糊":
                case "2":
                    matchTriggerType = MatchTriggerType.FUZZY;
                    continue;
                case "头部":
                case "3":
                    matchTriggerType = MatchTriggerType.HEAD;
                    continue;
                case "尾部":
                case "4":
                    matchTriggerType = MatchTriggerType.TAIL;
                    continue;
                case "正则":
                case "5":
                    matchTriggerType = MatchTriggerType.REGULAR;
                    continue;
                case "rewrite":
                    rewrite = true;
                    break;
                case "dynamic":
                case "动态":
                    dynamic = true;
                    continue;
                case "cache":
                    localCache = true;
                    continue;
                case "STRING":
                    conversionType = MessageConversionType.STRING;
                    continue;
                case "CONTENT":
                    conversionType = MessageConversionType.CONTENT;
                    continue;
                case "JSON":
                    conversionType = MessageConversionType.JSON;
                    continue;
            }

            if (param.contains("global-")) {
                MessageChain userMessage = MessageChain.deserializeFromMiraiCode(param, subject);
                if (userMessage.contains(At.Key)) {
                    At at = (At) userMessage.get(At.Key);
                    if (at != null) {
                        scope = new Scope(Scope.Type.GLOBAL_USER, at.getTarget());
                        continue;
                    }
                }
                try {
                    long aLong = Long.parseLong(param.replace("global-", ""));
                    scope = new Scope(Scope.Type.GLOBAL_USER, aLong);
                } catch (NumberFormatException e) {
                    this.exception = true;
                    this.exceptionMsg = "你的参数有误:global 识别失败";
                }
            } else if (param.contains("member-")) {
                MessageChain userMessage = MessageChain.deserializeFromMiraiCode(param, subject);
                if (userMessage.contains(At.Key)) {
                    At at = (At) userMessage.get(At.Key);
                    if (at != null) {
                        scope = new Scope(Scope.Type.GROUP_MEMBER, subject.getId(), at.getTarget());
                        continue;
                    }
                }
                try {
                    long aLong = Long.parseLong(param.replace("member-", ""));
                    scope = new Scope(Scope.Type.GROUP_MEMBER, subject.getId(), aLong);
                } catch (NumberFormatException e) {
                    this.exception = true;
                    this.exceptionMsg = "你的参数有误:member 识别失败";
                }
            } else if (param.contains("list-")) {
                scope = new Scope(Scope.Type.LIST, param.replace("list-", ""));
            } else if (param.contains("users-")) {
                scope = new Scope(Scope.Type.USERS, param.replace("users-", ""));
            } else if (param.contains("members-")) {
                scope = new Scope(Scope.Type.GROUP_MEMBERS, subject.getId(), param.replace("members-", ""));
            } else if (param.contains("probability-")) {
                try {
                    probability = Double.parseDouble(param.replace("probability-", ""));
                } catch (NumberFormatException e) {
                    this.exception = true;
                    this.exceptionMsg = "你的参数有误:probability 识别失败";
                }
            } else if (param.contains("id-")) {
                try {
                    this.id = Long.parseLong(param.replace("id-", ""));
                } catch (NumberFormatException e) {
                    this.exception = true;
                    this.exceptionMsg = "你的参数有误:id 识别失败";
                }
            } else if (param.contains("+") || param.contains("-")) {
                MessageChain userMessage = MessageChain.deserializeFromMiraiCode(param, subject);
                if (userMessage.contains(At.Key)) {
                    At at = (At) userMessage.get(1);
                    if (at != null) {
                        scope = new Scope(Scope.Type.GROUP_MEMBER, subject.getId(), at.getTarget());
                    }
                } else {
                    try {
                        long aLong = Long.parseLong(param.replace("+", "").replace("-", ""));
                        scope = new Scope(Scope.Type.GROUP_MEMBER, subject.getId(), aLong);
                    } catch (NumberFormatException e) {
                        this.exception = true;
                        this.exceptionMsg = "你的参数有误:qq 识别失败";
                    }
                }
            }
        }
    }

    public MatchTriggerType getMatchTriggerType() {
        return matchTriggerType;
    }

    public void setMatchTriggerType(MatchTriggerType matchTriggerType) {
        this.matchTriggerType = matchTriggerType;
    }

    public MessageConversionType getConversionType() {
        return conversionType;
    }

    public void setConversionType(MessageConversionType conversionType) {
        this.conversionType = conversionType;
    }

    public boolean isLocalCache() {
        return localCache;
    }

    public void setLocalCache(boolean localCache) {
        this.localCache = localCache;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isRewrite() {
        return rewrite;
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Contact getSubject() {
        return subject;
    }

    public void setSubject(Contact subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isException() {
        return exception;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
