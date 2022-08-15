package per.wsk.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //退出登录    /logout是注销接口的url, /test/hello是 退出登录后的跳转地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

        //如果没有权限访问，跳转自定义的403页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin()//自定义自己编写的登录页面
                .loginPage("/login.html") //登录页面设置
                .loginProcessingUrl("/user/login") //登录访问路径
                .defaultSuccessUrl("/success.html").permitAll() //登录成功之后，跳转路径
                .and().authorizeRequests()
                .antMatchers("/","/test/hello","/user/login").permitAll() //设置哪些路径可以直接访问，不需要认证
//                .antMatchers("/test/index").hasAuthority("admins") //表示只有具备admins权限的，才可以访问/test/index这个接口
//                .antMatchers("/test/index").hasAnyAuthority("admins,manager") //表示只要具备admins或manager两个权限的其中一个，就可以访问/test/index这个接口

//                .antMatchers("/test/index").hasRole("sale") //表示只有具备sale这个角色的，才有权限访问/test/index这个接口
                .antMatchers("/test/index").hasAnyRole("sale,admin") //表示只要具备sale或admin两个角色的其中之一，就有权限访问/test/index这个接口

                .anyRequest().authenticated() //除了上面一行的三个链接，其他任何请求都必须经过身份验证

                //以下三行代码：配置自动登录功能（和javaweb中的cookie功能类似）
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(120) //设置有效时间是120秒
                .userDetailsService(userDetailsService)

                .and().csrf().disable();//关闭 csrf防护
    }


    //注入数据源
    @Autowired
    private DataSource dataSource;
    //配置对象
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true); //启动时创建表，因为表已经手动创建了，这行代码可以不要
        return jdbcTokenRepository;
    }



}
