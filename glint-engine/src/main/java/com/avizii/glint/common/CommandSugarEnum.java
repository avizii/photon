package com.avizii.glint.common;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author : Avizii
 * @Create : 2021.06.25
 * @Description : Glint命令语法糖
 */
public enum CommandSugarEnum {

    DESC("desc", "run command as ShowTableExt.`` where parameters='''{:all}'''"),
    KILL("kill", "run command as Kill.`{}`"),
    JDBC("jdbc", "run command as JDBC.`{}` where `driver-statement-0`='''{}'''"),
    CACHE("cache", "run {} as CacheExt.`` where lifeTime=\"{}\""),
    UNCACHE("uncache", "run {} as CacheExt.`` where execute=\"uncache\""),
    CREATEPYTHONENV("createPythonEnv", "run command as PythonEnvExt.`{}` where condaFile=\"{}\" and command=\"create\""),
    REMOVEPYTHONENV("removePythonEnv", "run command as PythonEnvExt.`{}` where condaFile=\"{}\" and command=\"remove\""),
    CREATEPYTHONENVFROMFILE("createPythonEnvFromFile", "run command as PythonEnvExt.`{}` where condaYamlFilePath=\"${HOME}/{}\" and command=\"create\""),
    REMOVEPYTHONENVFROMFILE("removePythonEnvFromFile", "run command as PythonEnvExt.`{}` where condaYamlFilePath=\"${HOME}/{}\" and command=\"remove\""),
    RESOURCE("resource", "run command as EngineResource.`` where action=\"{0}\" and cpus=\"{1}\""),
    MODEL("model", "run command as ModelCommand.`{1}` where action=\"{0}\""),
    HDFS("hdfs", "run command as HDFSCommand.`` where parameters='''{:all}'''"),
    FS("fs", "run command as HDFSCommand.`` where parameters='''{:all}'''"),
    SPLIT("split", "run {0} as RateSampler.`` where labelCol=\"{2}\" and sampleRate=\"{4}\" as {6}"),
    SAVEUPLOADFILETOHOME("saveUploadFileToHome", "run command as DownloadExt.`` where from=\"{}\" and to=\"{}\""),
    WITHWARTERMARK("withWartermark", "register WaterMarkInPlace.`` where inputTable=\"{}\" and eventTimeCol=\"{}\" and delayThreshold=\"{}\""),
    DELTA("delta", "run command as DeltaCommandWrapper.`` where parameters='''{:all}'''"),
    SCHEDULER("scheduler", "run command as SchedulerCommand.`` where parameters='''{:all}'''"),
    PYTHON("python", "run command as PythonCommand.`` where parameters='''{:all}''' as {-1:next(named,uuid())}"),
    RAY("ray", "run command as Ray.`` where inputTable='''{1}''' and code='''{2}''' and outputTable=\"{4}\" as {4:uuid()}"),
    PLUGIN("plugin", "run command as PluginCommand.`` where parameters='''{:all}'''"),
    RUNSCRIPT("runScript", "run command as IteratorCommand.`` where parameters='''{:all}'''"),
    ITERATOR("iterator", "run command as IteratorCommand.`` where parameters='''{:all}'''"),
    IF("if", "run command as IfCommand.`` where parameters='''{:all}'''"),
    ELIF("elif", "run command as ElifCommand.`` where parameters='''{:all}'''"),
    THEN("then", "run command as ThenCommand.`` where parameters='''{:all}'''"),
    ELSE("else", "run command as ElseCommand.`` where parameters='''{:all}'''"),
    FI("fi", "run command as FiCommand.`` where parameters='''{:all}'''"),
    PRINTLN("println", "run command as PrintCommand.`` where parameters='''{:all}'''"),
    KAFKATOOL("kafkaTool", "run command as KafkaCommand.`kafka` where parameters='''{:all}'''"),
    CALLBACK("callback", "run command as  MLSQLEventCommand.`` where eventName=\"{3}\" and handleHttpUrl=\"{1}\" and method=\"{0}\""),
    SHOW("show", "run command as ShowCommand.`{}/{}/{}/{}/{}/{}/{}/{}/{}/{}/{}/{}`"),
    ;

    private final String keyword;
    private final String syntax;

    private static final Map<String, String> commandMap = new ConcurrentHashMap<>();

    CommandSugarEnum(String keyword, String syntax) {
        this.keyword = keyword;
        this.syntax = syntax;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSyntax() {
        return syntax;
    }

    public static Map<String, String> toMap() {
        final Map<String, String> map = Arrays.stream(CommandSugarEnum.values())
                .collect(Collectors.toMap(CommandSugarEnum::getKeyword, CommandSugarEnum::getSyntax));
        map.putAll(commandMap);
        return map;
    }

    public static void addCommand(Map<String, String> commands) {
        commands.forEach((k, v) -> {
            String value = v.toLowerCase(Locale.ROOT);
            if (value.contains("run") && value.contains("where") && value.contains("as")) {
                commandMap.put(k, v);
            } else {
                String syntax = String.format("run command as %s.`` where parameters='''{:all}'''", value);
                commandMap.put(k, syntax);
            }
        });
    }

    public static void deleteCommand(String name) {
        commandMap.remove(name);
    }
}
