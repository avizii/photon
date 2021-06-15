package com.avizii.glint.common;

/**
 * @Author : Avizii
 * @Create : 2021.06.11
 */
public interface DataSourceConstant {

    String GLINT_STREAM_NAME = "streamName";

    String GLINT_STREAM = "stream";

    String[] DATA_SOURCE_ARRAY = {"kafka", "kafka8", "kafka9", "adHocKafka", "socket", "mockStream"};

    String STREAM_WATERMARK_EVENT_TIME_COLUMN = "eventTimeCol";

    String STREAM_WATERMARK_DELAY_THRESHOLD = "delayThreshold";

    String[] KAFKA_FIELDS = {"key", "partition", "offset", "timestamp", "timestampType", "topic"};



}
