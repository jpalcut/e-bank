package cz.jpalcut.pia.config;

import cz.jpalcut.pia.service.UserService;
import cz.jpalcut.pia.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Třída sloužící pro konfiguraci Spring Security
 */
@Configuration
@EnableWebSecurity
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private IUserService userService;

    /**
     * Konstruktor třídy
     *
     * @param userService UserService
     */
    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Vratí BCryptPasswordEncoder
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Definuje způsob hashování hesla
     *
     * @param auth
     * @throws Exception vyjímka
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((UserService) userService).passwordEncoder(passwordEncoder());
    }

    /**
     * Konfigurace Spring Security - přihlašování a přístup ke stránkám
     *
     * @param http HttpSecurity
     * @throws Exception vyjímka
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        //definování přístupu v aplikaci
        http.authorizeRequests()
                //anonymní uživatel
                .antMatchers("/login", "/logoutSuccessful").anonymous()
                //všichni uživatelé
                .antMatchers("/user", "/user/edit", "/logout", "/request/delete/{id}").authenticated()
                //admin
                .antMatchers("/user/list", "/user/new", "/user/edit/{id}", "/user/{id}",
                        "/user/new/add", "/request/confirm/{id}", "/user/delete/{id}", "/bankcode/list",
                        "/bankcode/update", "/request/list").hasAuthority("ADMIN")
                //user
                .antMatchers("/account", "/transaction/**", "/template/**",
                        "/account/changeValueLimitBelow/{id}", "/account/changeInternationalPayment/{id}").hasAuthority("USER");

        //přihlašovací formulář
        http.authorizeRequests().and().formLogin()//
                //přihlášení
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/user")
                .failureUrl("/login?error=true")
                .usernameParameter("login_id")
                .passwordParameter("pin")
                //odhlášení
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

    }

}
