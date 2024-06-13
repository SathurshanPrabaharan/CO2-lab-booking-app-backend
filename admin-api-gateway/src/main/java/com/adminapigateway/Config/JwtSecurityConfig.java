//package com.adminapigateway.Config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//
//@RequiredArgsConstructor
//public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
//{
//    private final TokenSetting setting;
//    @Override
//    public void configure(HttpSecurity http) {
//        http.addFilterBefore(
//                new JwtFilter(setting), UsernamePasswordAuthenticationFilter.class
//        );
//    }
//}