package com.avizii.glint.session;

import com.avizii.glint.common.GlintConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @Author : Avizii @Create : 2021.05.14 @Description : Session管理器，用来处理多租户请求 */
@Slf4j
public class SessionManager {

  private static final Map<String, SparkSession> sessionMap = new ConcurrentHashMap<>(3);

  public static void addSession(String token, SparkSession session) {
    if (sessionMap.containsKey(token)) {
      log.warn("token [{}] session existed!", token);
      return;
    }
    sessionMap.put(token, session);
  }

  public static SparkSession getGlobalSession() {
    return sessionMap.get(GlintConstant.GLINT_SESSION_GLOBAL);
  }

  public static synchronized SparkSession getUserSession(String token) {
    if (sessionMap.containsKey(token)) {
      return sessionMap.get(token);
    }
    SparkSession session = getGlobalSession().cloneSession();
    addSession(token, session);
    return session;
  }

  // TODO 注意 V3.0 SparkSession cloneSession 导致 内存泄漏问题
  public static SparkSession getRequestSession() {
    return getGlobalSession().cloneSession();
  }

  public static SparkSession getSessionByType(String sessionType, String token) {
    SparkSession session = null;
    switch (sessionType) {
      case GlintConstant.GLINT_SESSION_USER:
        session = getUserSession(token);
        break;
      case GlintConstant.GLINT_SESSION_REQUEST:
        session = getRequestSession();
        break;
      default:
        session = getGlobalSession();
        break;
    }
    return session;
  }
}
