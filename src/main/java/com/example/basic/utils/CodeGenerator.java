package com.example.basic.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import java.util.Collections;

/**
 * 代码生成器
 */
public class CodeGenerator {

    public static void main(String[] args){
        generate();
    }

    private static void generate(){
        // 数据库地址,用户名和密码
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/asset?serverTimezone=GMT%2b8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("") // 设置作者
                            .enableSwagger() // 开启swagger模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Software\\IntelliJ IDEA 2021.2.3\\workspack\\AssetSystem\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.asset") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Software\\IntelliJ IDEA 2021.2.3\\workspack\\AssetSystem\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("audit") // 设置需要生成的表名
                            .addTablePrefix(""); // 设置过滤表前缀

                })
                .execute();

    }

}
