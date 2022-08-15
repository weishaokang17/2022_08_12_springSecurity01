package per.wsk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("per.wsk.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //该注解是开启认证授权的注解的使用，可以标到启动类上，也可以标到配置类上
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
