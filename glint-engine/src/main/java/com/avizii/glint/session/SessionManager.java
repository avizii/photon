package com.avizii.glint.session;

import cn.hutool.core.lang.UUID;
import com.avizii.glint.exception.GlintException;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : Avizii
 * @Create : 2021.05.14
 * @Effect : Session管理器，用来处理多租户请求
 */
@Slf4j
public class SessionManager {

    private static final Map<String, SparkSession> sessionMap = new ConcurrentHashMap<>(3);

    public static void addSession(SparkSession session) {
        addSession(UUID.randomUUID().toString(true), session);
    }

    public static void addSession(String token, SparkSession session) {
        if (sessionMap.containsKey(token)) {
            log.warn("token [{}] session is exist!", token);
            return;
        }
        sessionMap.put(token, session);
    }

    public static SparkSession getSession() {
        return getSession(null);
    }

    public static SparkSession getSession(String token) {
        if (sessionMap.size() == 0) {
            throw new GlintException("No active spark session available!");
        }

        return sessionMap.getOrDefault(token,
                sessionMap.values().stream()
                        .findFirst()
                        .orElse(null)
        );
    }

}
