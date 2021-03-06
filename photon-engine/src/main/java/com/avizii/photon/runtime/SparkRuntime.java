package com.avizii.photon.runtime;

import com.avizii.photon.common.GlintConstant;
import com.avizii.photon.session.SessionManager;
import com.google.common.base.Preconditions;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.util.Map;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
public class SparkRuntime extends GlintRuntime<SparkSession> {

  public static void create(Map<String, String> params) {
    // 构造SparkSession
    SparkSession session = new SparkRuntime(params).run();
    // 添加到Session管理器
    SessionManager.addSession(GlintConstant.GLINT_SESSION_GLOBAL, session);
  }

  private SparkRuntime(Map<String, String> params) {
    super(params);
  }

  @Override
  public SparkSession run() {
    SparkConf sparkConf = new SparkConf();

    // 设置spark官方提供的config
    params.keySet().stream()
        .filter(key -> key.startsWith("hive") || key.startsWith("spark"))
        .forEach(key -> sparkConf.set(key, params.get(key)));

    String glintAppName =
        Preconditions.checkNotNull(
            params.get(GlintConstant.GLINT_APP_NAME), "param - [glint.app.name] cannot be null!");
    String glintAppUrl =
        Preconditions.checkNotNull(
            params.get(GlintConstant.GLINT_APP_URL), "param - [glint.app.url] cannot be null!");

    sparkConf.setAppName(glintAppName);
    sparkConf.setMaster(glintAppUrl);

    SparkSession.Builder sessionBuilder = SparkSession.builder().config(sparkConf);

    // 判断是否开启hive支持
    String enableHiveSupport =
        params.getOrDefault(GlintConstant.GLINT_ENABLE_HIVE_SUPPORT, "false");
    if (Boolean.parseBoolean(enableHiveSupport)) {
      sessionBuilder.enableHiveSupport();
    }

    return sessionBuilder.getOrCreate();
  }
}
