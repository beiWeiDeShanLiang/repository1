package com.leyou.upload.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class LeyouCorsConfiguration {
//    @Bean
//    public CorsFilter corsFilter(){
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //api.leyou.com/api/upload/image
//        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedHeader("*");
//       // corsConfiguration.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
//
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }
}
