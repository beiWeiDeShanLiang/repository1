package com.leyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class LeyouCorsConfiguration {
    @Bean
    public CorsFilter corsFilter(){

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
//registry.addMapping("/**")
//        .allowedOrigins("*")
//        .allowCredentials(true)
//        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
//        .maxAge(3600);