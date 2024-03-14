package com.project.config;

import com.project.filter.JwtAuthenticationTokenFilter;
import com.project.service.impl.UserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration  {
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Resource
    private UserDetailsServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // 对于登录接口 允许匿名访问
                .requestMatchers("api/auth/login").anonymous()
                //注销接口需要认证才能访问
                .requestMatchers("/logout").authenticated()
//                .antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll()
                .and()
                .exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .build();
    }
    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        return new ProviderManager(provider);
    }

}
