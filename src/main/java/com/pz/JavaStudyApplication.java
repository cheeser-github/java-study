package com.pz;

import com.pz.shiro.common.init.InitDatabase;
import com.pz.shiro.realm.UserRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;


@Configuration
@SpringBootApplication
@MapperScan("com.pz.shiro.mapper")  //要具体到mapper所在的包这一级，否则会出现service绑定mapper失败
public class JavaStudyApplication {


    public static void main(String[] args) {

        SpringApplication applicationContext = new SpringApplication(JavaStudyApplication.class);
        //添加数据库初始化操作
        applicationContext.addInitializers(new InitDatabase());
        applicationContext.run(args);

//        ConfigurableApplicationContext context = SpringApplication.run(JavaStudyApplication.class, args);

    }


    /**
     * 直接注入自定义的realm就可以使用呢 springboot集成下的用起来就是简单
     * @return
     */
    @Bean
    public Realm realm() {
        return new UserRealm();
    }




    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {

        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/main", "authc");
        chainDefinition.addPathDefinition("/test1", "authc");

        chainDefinition.addPathDefinition("/login.html", "anon"); // need to accept POSTs from the login form
        chainDefinition.addPathDefinition("/logout", "logout");
        chainDefinition.addPathDefinition("/**", "anon");  //一些静态资源需要放行
        return chainDefinition;
    }

    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }



}
