/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.ui;

import hu.lordoftheflies.hydra.uaac.services.JpaUserDetailsService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(SecurityConfiguration.class.getName());

    private static final String BASE_URL = "/";

    private static final String LOGOUT_URL = "/logout";

    private static final String LOGIN_PAGE = "/#/login";

    private static final String LOGIN_URL = "/view/login";

    private static final String PARAMETER_USERNAME = "username";

    private static final String PARAMETER_PASSWORD = "password";
    
    private static final String AC_REQUEST_HEADERS_HEADER = "Access-Control-Request-Headers";
    private static final String AC_REQUEST_METHOD_HEADER = "Access-Control-Request-Method";
    private static final String AC_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    private static final String AC_ALLOW_CREDENTIALS_HEADER = "Access-Control-Allow-Credentials";
    private static final String X_REQUESTED_WITH_HEADER = "X-Requested-With";
    private static final String X_REQUESTED_BY_HEADER = "X-Requested-By";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ORIGIN_HEADER = "Origin";
    private static final String ACCEPT_HEADER = "accept";
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String AUTHENTICATION_HEADER = "WWW-Authenticate";
    private static final String XXSRFTOKEN = "X-XSRF-TOKEN";
    private static final String XXSRFTOKEN2 = "XSRF-TOKEN";
    
    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
                    String requestedBy = request.getHeader(X_REQUESTED_BY_HEADER);
                    LOG.log(Level.INFO, "X-Requested-By: {0}", requestedBy);
                    if (requestedBy == null || requestedBy.isEmpty()) {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
//                        httpResponse.addHeader(AUTHENTICATION_HEADER, "Basic realm");
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    } else {
                        HttpServletResponse httpResponse = (HttpServletResponse) response;
//                        httpResponse.addHeader(AUTHENTICATION_HEADER, "Application driven");
                        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                    }
                })
                .and().authorizeRequests().antMatchers(
                        "/webjars/**",
                        "/bower_components/**",
                        
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/validator*",
                        "/swagger-ui.html",
                        
                        "/images/**",
                        "/src/**",
                        "/locales/**",
                        "/service-worker.js",
                        "/manifest.json",
                        "/index.html",
                        BASE_URL,
                        LOGIN_PAGE,
                        LOGIN_URL
                ).permitAll().anyRequest().authenticated()
                
//                .and().formLogin().loginPage(LOGIN_PAGE).loginProcessingUrl(LOGIN_URL).usernameParameter(PARAMETER_USERNAME).passwordParameter(PARAMETER_PASSWORD).defaultSuccessUrl(BASE_URL)
                
                .and().csrf().disable()
                
                .logout().permitAll().logoutSuccessUrl(BASE_URL).logoutUrl(LOGOUT_URL) //.deleteCookies(REMEMBER_ME_TOKEN, XXSRFTOKEN2)
                
//                .and().csrf().csrfTokenRepository(BackendCookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().userDetailsService(userDetailsService);
    }
    
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/")
                        .allowedOrigins("*")
                        .allowedMethods("POST, GET, OPTIONS, DELETE ,PUT")
                        .allowedHeaders(AUTHORIZATION_HEADER, CONTENT_TYPE_HEADER, X_REQUESTED_WITH_HEADER, ACCEPT_HEADER, ORIGIN_HEADER,
                                AC_REQUEST_METHOD_HEADER, AC_REQUEST_HEADERS_HEADER, AC_ALLOW_ORIGIN_HEADER, AC_ALLOW_CREDENTIALS_HEADER, XXSRFTOKEN, XXSRFTOKEN2)
                        .exposedHeaders(AUTHORIZATION_HEADER, CONTENT_TYPE_HEADER, X_REQUESTED_WITH_HEADER, ACCEPT_HEADER, ORIGIN_HEADER,
                                AC_REQUEST_METHOD_HEADER, AC_REQUEST_HEADERS_HEADER, AC_ALLOW_ORIGIN_HEADER, XXSRFTOKEN, XXSRFTOKEN2)
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }
}
