package com.bootcamp.hackathon.configs

import com.bootcamp.hackathon.jwt.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    var jwtFilter: JwtFilter
) {
    @Bean
    fun filterChain(http : HttpSecurity) : SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
        http.cors { cors -> cors.disable() }
        http.formLogin { form -> form.disable() }
        http.httpBasic { basic -> basic.disable() }
        http.sessionManagement {
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.authorizeHttpRequests {
            req -> (
                req
                    .requestMatchers("/v1/auth/**").permitAll()
                    .requestMatchers("/v1/routes").permitAll()
                    .requestMatchers("/v1/authorities").hasAuthority("ADMIN")
                    .requestMatchers("/api-docs").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            )
        }
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}