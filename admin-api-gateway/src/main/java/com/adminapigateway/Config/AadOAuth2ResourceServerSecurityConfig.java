//package com.adminapigateway.Config;
//
//
//
//import com.azure.spring.cloud.autoconfigure.implementation.aad.security.AadResourceServerHttpSecurityConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration(proxyBeanMethods = false)
//@EnableWebSecurity
//@EnableMethodSecurity
//public class AadOAuth2ResourceServerSecurityConfig {
//
//    /**
//     * Add configuration logic as needed.
//     */
//    @Bean
//    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        AadResourceServerHttpSecurityConfigurer aadConfigurer = new AadResourceServerHttpSecurityConfigurer();
//
//        // Configure Azure AD Resource Server
//        aadConfigurer.configure(http);
//
//        // Configure authorization for HTTP requests
//        http.authorizeHttpRequests(authorize -> authorize
//                .anyRequest().authenticated()
//        );
//
//        return http.build();
//    }
//}
