package ru.biluta.memes.service.config.security

import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    private val whiteList = arrayOf(
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/memes/**",
            "/api/chat/**"
    )

    @Order(1)
    @Bean
    fun apiUserFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .securityMatcher("/api/users/**")
                .authorizeHttpRequests { authz ->
                    authz
                            .anyRequest()
                            .authenticated()
                }
                .httpBasic { }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .csrf { disable() }
                .cors { disable() }
        return http.build()
    }

    @Order(5)
    @Bean
    fun apiFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
                .authorizeHttpRequests { authz ->
                    whiteList.forEach {url ->
                        authz.requestMatchers(url).permitAll()
                    }
                }
                .csrf { disable() }
                .cors {  }
        return http.build()
    }

    @Bean
    fun users(): UserDetailsService {
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val admin: UserDetails = User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build()
        val moderator: UserDetails = User.withUsername("moder")
                .password(encoder.encode("moder"))
                .roles("MODERATOR")
                .build()
        return InMemoryUserDetailsManager(admin, moderator)
    }
}