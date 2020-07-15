package com.jack.mybatis.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

public class MysqlGenerator {

    private static final String projectPath = "F:/cloud-dev/spring-boot-mybaits-plus";


    public static void main(String[] args) throws Exception {

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 设置全局配置
        globalConfig(generator);

        // 配置数据源
        datasource(generator);

        // 配置包信息
        packageConfig(generator);

        // 自定义配置
        injectionConfig(generator);

        // 模板配置
        templateConfig(generator);

        // 策略配置
        strategyConfig(generator);

        // 选择freemarker引擎需要指定如下，注意需要在pom依赖中添加freemarker依赖
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * 设置全局配置
     */
    private static void globalConfig(AutoGenerator generator) throws Exception {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(projectPath + "/src/main/java");

        // 设置类作者
        gc.setAuthor("jack luo");
        gc.setOpen(true);
        // service 命名方式
        gc.setServiceName("%sService");
        // service impl 命名方式
        gc.setServiceImplName("%sServiceImpl");
        // 自定义文件命名，注意%s会自动填充表实体属性
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        // 是否覆盖同名文件，默认是false
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(true);
        // 主键策略
        gc.setIdType(IdType.AUTO);
        // XML二级缓存
        gc.setEnableCache(false);
        // XML ResultMap 生成基本的resultMap
        gc.setBaseResultMap(true);
        // XML columnList 生成基本的sql片段
        gc.setBaseColumnList(false);
        //实体属性 Swagger2 注解
        // gc.setSwagger2(true);
        generator.setGlobalConfig(gc);
    }

    /**
     * 配置数据源
     */
    private static void datasource(AutoGenerator generator) {
        DataSourceConfig config = new DataSourceConfig();
        // 数据源类型，默认是MySQL
        config.setDbType(DbType.MYSQL);
        // 数据类型转换
        config.setTypeConvert(new MySqlTypeConvert(){
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });

        config.setUrl("jdbc:mysql://192.168.4.203:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        config.setDriverName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("nihaoma");
        generator.setDataSource(config);
    }

    /**
     * 配置包信息
     */
    private static void packageConfig(AutoGenerator generator) {
        PackageConfig config = new PackageConfig();
        // config.setModuleName("模块名");
        config.setParent("com.jack.mybatis");
        config.setEntity("entity");
        config.setService("service");
        config.setServiceImpl("service.impl");
        generator.setPackageInfo(config);
    }

    /**
     * 自定义配置
     * 例：这里向自定义配置中添加了一个abc属性
     * 可通过 generator.getCfg().getMap().get("abc")来获取
     */
    private static void injectionConfig(AutoGenerator generator) throws Exception {
        InjectionConfig config = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-lzyforever");
                this.setMap(map);
            }
        };

        List<FileOutConfig> outConfigList = new ArrayList<>();
        outConfigList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名，如果 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        /*
        config.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */

        config.setFileOutConfigList(outConfigList);
        generator.setCfg(config);
    }

    /**
     * 模板配置
     */
    private static void templateConfig(AutoGenerator generator) {
        // 配置自定义输出模板
        TemplateConfig config = new TemplateConfig();
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // config.setEntity("templates/entity.java");
        // config.setController("xxxxx");
        // config.setService("xxxxx");
        config.setXml(null);
        generator.setTemplate(config);
    }


    /**
     * 策略配置
     */
    private static void strategyConfig(AutoGenerator generator) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // 公共父类
        // strategy.setSuperControllerClass("com.jack.mybatis.controller.BaseController");
        // strategy.setSuperEntityClass("com.jack.mybatis.entity.BaseEntity");

        // 用于父类中的公共字段
        // strategy.setSuperEntityColumns("id");

        // 实体是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);

        // 实体是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);

        // 实体类使用lombok
        strategy.setEntityLombokModel(true);
        // 设置Controller以restFull风格
        strategy.setRestControllerStyle(true);
        // 全局大写命名 ORACLE 注意
        // strategy.setCapitalMode(true);
        // 此处可以修改为您的表前缀
        // strategy.setTablePrefix(new String[]{"test_"});
        // 设置逻辑删除键
        strategy.setLogicDeleteFieldName("deleted");
        // 指定生成的bean的数据库表名，如果有多个，多个之间就用英文逗号分隔，这是个可变参数
        strategy.setInclude("student");
        // 排除生成的表
        // strategy.setExclude(new String[]{"test"});
        // 驼峰连接字符
        strategy.setControllerMappingHyphenStyle(true);

        // 自定义填充字段
        tableFillColumn(strategy);

        generator.setStrategy(strategy);
    }


    /**
     * 自定义需要填充的字段
     * 如：每张表都有一个创建时间、修改时间，而且这基本上就是通用的
     * 新增时，创建时间和修改时间同时修改，修改时，修改时间会修改
     * 虽然像MySQL数据库有自动更新机制，但像Oracle的数据库就没有
     * 使用公共字段填充功能，就可以实现，自动按场景更新
     * 可以做如下配置
     */
    private static void tableFillColumn(StrategyConfig strategy) {
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill createTimeField = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTimeField = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        tableFillList.add(createTimeField);
        tableFillList.add(updateTimeField);
        strategy.setTableFillList(tableFillList);
    }


}
