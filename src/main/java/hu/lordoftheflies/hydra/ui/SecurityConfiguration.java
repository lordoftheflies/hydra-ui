/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.boot.Banner.Mode.LOG;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(SecurityConfiguration.class.getName());

    private static final String X_REQUESTED_BY_HEADER = "X-Requested-By";

    private static final String BASE_URL = "/";

    private static final String LOGOUT_URL = "/logout";

    private static final String LOGIN_PAGE = "/#/login";

    private static final String LOGIN_URL = "/login";

    private static final String PARAMETER_USERNAME = "username";

    private static final String PARAMETER_PASSWORD = "password";

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
                        "/swagger-ui.html",
                        "/bower_components/**",
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
                .and().logout().permitAll().logoutSuccessUrl(BASE_URL).logoutUrl(LOGOUT_URL) //.deleteCookies(REMEMBER_ME_TOKEN, XXSRFTOKEN2)
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
