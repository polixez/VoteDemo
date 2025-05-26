package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.example.demo.model.User user = userService.findByUsername(username);
            if (user != null) {
                return User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("Пользователь не найден");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключение CSRF защиты (не рекомендуется для production)
                .csrf(csrf -> csrf.disable())

                // Настройка Content Security Policy для разрешения фреймов из того же источника
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp.policyDirectives("frame-ancestors 'self'"))
                )

                // Настройка авторизации HTTP запросов
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/h2-console/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                // Настройка формы логина
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                // Настройка выхода из системы
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                // Установка провайдера аутентификации
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
