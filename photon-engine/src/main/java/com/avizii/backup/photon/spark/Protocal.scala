package com.avizii.backup.photon.spark

case class MLSQLScriptJobGroup(groupId: String,
                               activeJobsNum: Int,
                               completedJobsNum: Int,
                               failedJobsNum: Int,
                               activeJobs: Seq[MLSQLScriptJob]
                              )

case class MLSQLScriptJob(jobId: Int,
                          submissionTime: Option[java.sql.Date],
                          completionTime: Option[java.sql.Date],
                          numTasks: Int,
                          numActiveTasks: Int,
                          numCompletedTasks: Int,
                          numSkippedTasks: Int,
                          numFailedTasks: Int,
                          numKilledTasks: Int,
                          numCompletedIndices: Int,
                          numActiveStages: Int,
                          numCompletedStages: Int,
                          numSkippedStages: Int,
                          numFailedStages: Int,
                          duration: Long)

case class MLSQLResourceRender(currentJobGroupActiveTasks: Int,
                               activeTasks: Int,
                               failedTasks: Int,
                               completedTasks: Int,
                               totalTasks: Int,
                               taskTime: Double,
                               gcTime: Double,
                               activeExecutorNum: Int,
                               totalExecutorNum: Int,
                               totalCores: Int,
                               usedMemory: Double,
                               totalMemory: Double,
                               shuffleData: MLSQLShufflePerfRender)

case class MLSQLShufflePerfRender(memoryBytesSpilled: Long,
                                  diskBytesSpilled: Long,
                                  inputRecords: Long)
