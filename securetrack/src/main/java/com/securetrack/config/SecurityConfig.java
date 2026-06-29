package com.securetrack.config;

import com.securetrack.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger y OpenAPI (público)
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/v3/api-docs").permitAll()

                        .requestMatchers("/").permitAll()

                        // Endpoints públicos (no requieren token)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Endpoints GET de incidencias: accesibles para USER y ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/incidences/**").hasAnyRole("USER", "ADMIN")

                        // Endpoints POST de incidencias: USER y ADMIN pueden crear
                        .requestMatchers(HttpMethod.POST, "/api/incidences/**").hasAnyRole("USER", "ADMIN")

                        // Endpoints PUT y PATCH: USER (solo sus propias incidencias) y ADMIN
                        .requestMatchers(HttpMethod.PUT, "/api/incidences/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/incidences/**").hasRole("ADMIN")

                        // Endpoints DELETE: solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/api/incidences/**").hasRole("ADMIN")

                        // Endpoints de usuarios: solo ADMIN
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}