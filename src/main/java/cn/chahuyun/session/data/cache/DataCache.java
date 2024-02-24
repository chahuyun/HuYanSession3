package cn.chahuyun.session.data.cache;

import cn.chahuyun.session.data.entity.ManySession;
import cn.chahuyun.session.data.entity.SingleSession;
import cn.chahuyun.session.data.entity.TimingSession;

import java.util.Map;

/**
 * 数据缓存
 *
 * @author Moyuyanli
 * @date 2024/1/15 11:26
 */
public class DataCache {

    public static Map<Long, SingleSession> singleSessionMap;
    public static Map<Long, ManySession> manySessionMap;
    public static Map<Long, TimingSession> timingSessionMap;



}
