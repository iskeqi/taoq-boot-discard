package tech.taoq.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class Generator {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/taoq-boot?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=GMT%2B8&allowMultiQueries=true";

		FastAutoGenerator.create(url, "root", "root")
				.globalConfig(builder -> {
					builder.author("baomidou") // 设置作者
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.outputDir("D://"); // 指定输出目录
				})
				.packageConfig(builder -> {
					builder.parent("com.baomidou.mybatisplus.samples.generator") // 设置父包名
							.moduleName("system") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.mapper, "D://")); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {
					builder.addInclude("sys_job") // 设置需要生成的表名
							.addTablePrefix("t_", "c_"); // 设置过滤表前缀
				})
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}