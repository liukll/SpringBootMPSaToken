package com.fc.v2.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;

public class MybatisAutoGenerator {

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        String pkgPath = System.getProperty("user.dir") + "\\gencode_temp";
        //包名
        //String pkgPath ="E:\\v2-cloud\\gencode_temp";
        //输出地址
        //String outPath="E:\\v2-cloud\\v2-model\\v2-common";
        String outPath=System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/springbootv2?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "root")
                // 全局配置
                .globalConfig(builder -> builder.outputDir(pkgPath).author("v2")
                        .fileOverride().disableOpenDir())
                // 包配置
                .packageConfig(builder -> builder.parent("com.fc.v2").entity("model.auto").mapper("mapper.auto").service("service.plus")
                        .serviceImpl("service.plus.impl")

                )
                // 模板配置
                .templateConfig(builder -> builder.disable(TemplateType.CONTROLLER))
                // 注入配置
                .injectionConfig(builder -> builder.beforeOutputFile((tableInfo, objectMap) -> {
                            // 这里是预处理输出模板信息
                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                        })
                        // 这里是自定义传入模板参数值
                       // .customMap(Collections.singletonMap("test", "baomidou"))
//                        .customFile(new HashMap<String, String>() {{
//                            // 这里可以自定义自己的模板
//                            put("controller.java", "/templates/controller.java.vm");
//                            put("entity.java", "/templates/entity.java.vm");
//                        }})
                        .build())
                // 策略配置 sample
                .strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("请输入表名？")).addTablePrefix("t_","c_"))
                // 模板引擎的某个方法
                .templateEngine(new VelocityTemplateEngine() {
                    @Override
                    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
                        String otherPath = getPathInfo(OutputFile.other);

                        ConfigBuilder config= (ConfigBuilder) objectMap.get("config");
                        Map<OutputFile,String> map= config.getPathInfo();

//                        entity,
//                        service,
//                        serviceImpl,
//                        mapper,
//                        mapperXml,
//                        controller,
//                        other;


                        map.put(OutputFile.entity,outPath+"\\src\\main\\java\\com\\fc\\v2\\model\\auto");
                        map.put(OutputFile.service,outPath+"\\src\\main\\java\\com\\fc\\v2\\service\\plus");
                        map.put(OutputFile.serviceImpl,outPath+"\\src\\main\\java\\com\\fc\\v2\\service\\plus\\impl");
                        map.put(OutputFile.mapper,outPath+"\\src\\main\\java\\com\\fc\\v2\\mapper\\auto");
                        map.put(OutputFile.mapperXml,outPath+"\\src\\main\\resources\\mybatis-plus");



                        customFile.forEach((key, value) -> {

                            String fileName = String.format((otherPath + File.separator + "%s"), key);
                            outputFile(new File(fileName), objectMap, value);
                        });
                    }
                })
                // 执行
                .execute();
    }

}
