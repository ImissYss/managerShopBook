package com.maidat.mybooks.configuration;

import com.maidat.mybooks.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                   .antMatchers("/admin/**").hasRole("ADMIN")
                   .antMatchers("/userpage").authenticated()
                   .antMatchers("/user/**").authenticated()
                   .antMatchers("/book/add").authenticated()
                   .antMatchers("/authors/add").authenticated()
                .and()
                .csrf().disable()
                .anonymous().disable()
                .formLogin()
                   .loginPage("/login")
                   //.successHandler(new CustomLoginSuccessHandler("/admin"))
                .defaultSuccessUrl("/")
                .and()
                .logout()
                   .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                   .logoutSuccessUrl("/");
                /*.and()
                .exceptionHandling()
                   .accessDeniedPage("/access-denied");*/
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**","/theme/**","/css/**","/static/**","/js/**","/images/**", "/lib/**");
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

        public CustomLoginSuccessHandler(String defaultSuccessUrl){
            setDefaultTargetUrl(defaultSuccessUrl);
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
            HttpSession session = request.getSession();
            if(session !=null){
                Object saveRequest = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                String redirectUrl = (String) session.getAttribute("previousURL");
                if(saveRequest ==null && redirectUrl != null){
                    redirectUrl = redirectUrl.contains("signup") ? redirectUrl.replace("signup","") : redirectUrl;
                    session.removeAttribute("previousURL");
                    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
                }else{
                    super.onAuthenticationSuccess(request, response, authentication);
                }
            }else{
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }*/
}
