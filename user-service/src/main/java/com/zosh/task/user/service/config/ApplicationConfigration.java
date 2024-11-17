    package com.zosh.task.user.service.config;

    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;

    import java.util.Arrays;
    import java.util.Collection;
    import java.util.Collections;

//    @Configuration
//    public class ApplicationConfigration {
//
//        @Bean
//        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http.sessionManagement(
//                    managment->managment.sessionCreationPolicy(
//                            SessionCreationPolicy.STATELESS
//                    )
//            ).authorizeHttpRequests(
//                    Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll()
//            ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
//                    .csrf(csrf->csrf.disable())
//    //                .cors(cors->cors.configurationSource(corsConfigurationSource()))
//                    .httpBasic(Customizer.withDefaults())
//                    .formLogin(Customizer.withDefaults());
//            return http.build();
//        }
//
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//    }
@Configuration
public class ApplicationConfigration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(
                        managment -> managment.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                ).authorizeHttpRequests(
                        Authorize -> Authorize
                                .requestMatchers("/api/**").permitAll()  // Không yêu cầu xác thực cho /api/**
                                .anyRequest().permitAll()  // Các yêu cầu khác cũng không yêu cầu xác thực
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) // Thêm bộ lọc JWT
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

