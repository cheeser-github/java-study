package com.pz.shiro.common.init;


import com.pz.shiro.common.Exception.BusinessException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Package: com.sr.middletier.common.base.init
 *
 * @author: yangXueSi
 * @created Date: 2021/1/18
 * @decription: spring 容器启动前检查数据库是否存在，不存在就创建
 */
@Order(0)
public class InitDatabase implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    private static final Logger LOG = LoggerFactory.getLogger(InitDatabase.class);

    private static final String SCHEMA_NAME = "schema_name";

    private static final String APPLICATION_CONFIG = "applicationConfig: [classpath:/application.yml]";

    private static final String APPLICATION_YAML = "/application.yml";

    /**
     * 需要创建初始化数据库的驱动
     */
    private String driver;

    /**
     * 需要创建初始化数据库的URL地址
     */
    private String url;

    /**
     * 需要创建初始化数据库的需要创建初始化数据库的账号名称
     */
    private String username;

    /**
     * 需要创建初始化数据库的密码
     */
    private String password;

    /**
     * 需要创建初始化数据库的数据库名称
     */
    private String initDatabaseName;

    /**
     * 需要创建初始化数据库的文件名
     */
    private String initDatabaseFileName;


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        initCustomParam();

        URI uri;
        try {
            Class.forName(driver);
            uri = new URI(url.replace("jdbc:", ""));
        } catch (ClassNotFoundException | URISyntaxException e) {
            LOG.error("JDBC URL解析错误", e);
            throw new BusinessException("JDBC URL解析错误");
        }
        String host = uri.getHost();
        int port = uri.getPort();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port +
                "?useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai", username, password);
             Statement statement = connection.createStatement();
        ) {
            String sal = "select schema_name from information_schema.schemata where schema_name = " + "'" + initDatabaseName + "'";
            //查询返回的结果集
            ResultSet resultSet = statement.executeQuery(sal);
            if (!resultSet.next()) {

                //查不到数据库，执行数据库初始化脚本
                LOG.warn("查不到数据库({})", initDatabaseName);
                ScriptRunner scriptRunner = new ScriptRunner(connection);

                //打包后的数据库脚本文件位于项目同一目录下
                String filePath = Paths.get(System.getProperty("user.dir"), initDatabaseFileName + ".sql").toString();

                try (InputStream inputStream = new FileInputStream(filePath)) {
                    scriptRunner.runScript(new InputStreamReader(inputStream));
                    LOG.warn("执行项目数据脚本,创建数据库({})成功", initDatabaseFileName);
                }
            } else {
                String databaseName = resultSet.getString(SCHEMA_NAME);
                LOG.warn("已经存在数据库({})", databaseName);
            }
            if (resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException | IOException e) {
            LOG.error("启动项目检查数据库是否创建", e);
            throw new BusinessException("初始化数据库失败");
        }
    }

    /**
     * 初始化文件信息
     * 利用YamlPropertySourceLoader 加载配置文件，这样可以根据yaml文件的结构获取对应的值。
     * Properties获取的配置值可能出现同名的配置项覆盖现象
     */
    private void initCustomParam() {

        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        ClassLoader loader = this.getClass().getClassLoader();
        Resource resource = new ClassPathResource(APPLICATION_YAML, loader);
        Map<String, String> propertySourceSource = null;
        try {
            List<PropertySource<?>> load = yamlPropertySourceLoader.load(APPLICATION_CONFIG, resource);
            PropertySource<?> propertySource = load.get(0);
            propertySourceSource = (Map<String, String>) propertySource.getSource();
        } catch (IOException e) {
            LOG.error("加载初始化数据库配置文件失败", e);
        }

        driver = String.valueOf(propertySourceSource.getOrDefault("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver"));
        url = String.valueOf(propertySourceSource.getOrDefault("spring.datasource.url", ""));
        username = String.valueOf(propertySourceSource.get("spring.datasource.username"));
        password = String.valueOf(propertySourceSource.get("spring.datasource.password"));
        initDatabaseName = String.valueOf(propertySourceSource.get("initDatabase.initDatabaseName"));
        initDatabaseFileName = String.valueOf(propertySourceSource.get("initDatabase.initDatabaseFileName"));
        LOG.info("初始化数据库数据({})、({})、({})、({})、({})", driver, url, username, initDatabaseName, initDatabaseFileName);
    }

}