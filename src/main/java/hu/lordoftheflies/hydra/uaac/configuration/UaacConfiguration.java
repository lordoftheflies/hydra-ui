/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.lordoftheflies.hydra.uaac.configuration;

import hu.lordoftheflies.hydra.uaac.dal.AccountRepository;
import hu.lordoftheflies.hydra.uaac.dal.AuthorityRepository;
import hu.lordoftheflies.hydra.uaac.entities.AccountDetailsEntity;
import hu.lordoftheflies.hydra.uaac.entities.AuthorityEntity;
import hu.lordoftheflies.hydra.uaac.services.JpaUserDetailsService;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author lordoftheflies
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = {
    AccountDetailsEntity.class,
    AuthorityEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
    AccountRepository.class,
    AuthorityRepository.class
})
public class UaacConfiguration {

    @Value("${uaac.database.host}")
    private String host;

    @Value("${uaac.database.port}")
    private String port;

    @Value("${uaac.database.name}")
    private String databaseName;

    @Value("${uaac.database.login}")
    private String login;

    @Value("${uaac.database.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("org.postgresql.Driver");
        driver.setUrl("jdbc:postgresql://" + host + ":" + port + "/" + databaseName);
        driver.setUsername(login);
        driver.setPassword(password);
        return driver;
    }

    @Bean
    public JpaUserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }
}
