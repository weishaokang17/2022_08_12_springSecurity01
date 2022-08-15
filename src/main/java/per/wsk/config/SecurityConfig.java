package per.wsk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 通过配置类来配置项目的登录账号、密码
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //密码是123，这里是将123加密
        String password = passwordEncoder.encode("123");
        //账号是lucy,角色是admin
        auth.inMemoryAuthentication().withUser("lucy").password(password).roles("admin");
    }


    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }
}
