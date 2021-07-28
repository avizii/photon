package com.avizii.glint.execute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Avizii
 * @create : 2021.05.21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobInfo {
  private String token;
  private String jobName;
  private String jobId;
  private String jobType;
  private String jobContent;
  private Long startTime;
  private Long timeout;
}
