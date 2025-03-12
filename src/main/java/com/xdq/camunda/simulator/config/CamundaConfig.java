package com.xdq.camunda.simulator.config;

// ES: This is here because the built-in class the camunda boot starter has seems to provide everything too late. then NPE.

import org.camunda.bpm.spring.boot.starter.configuration.impl.*;
import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableConfigurationProperties(CamundaBpmProperties.class)
public class CamundaConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Primary
    public DefaultProcessEngineConfiguration defaultProcessEngineConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultProcessEngineConfiguration config = new DefaultProcessEngineConfiguration();
        return config;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultDatasourceConfiguration defaultDatasourceConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultDatasourceConfiguration config = new DefaultDatasourceConfiguration();
        return config;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultAuthorizationConfiguration defaultAuthorizationConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultAuthorizationConfiguration config = new DefaultAuthorizationConfiguration();
        return config;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultDeploymentConfiguration defaultDeploymentConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultDeploymentConfiguration config = new DefaultDeploymentConfiguration();
        return config;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultHistoryConfiguration defaultHistoryConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultHistoryConfiguration config = new DefaultHistoryConfiguration();
        return config;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultJobConfiguration defaultJobConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultJobConfiguration config = new DefaultJobConfiguration();
        return config;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DefaultMetricsConfiguration defaultMetricsConfiguration(
            CamundaBpmProperties camundaBpmProperties) {
        DefaultMetricsConfiguration config = new DefaultMetricsConfiguration();
        return config;
    }

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public SpringBootSpinProcessEnginePlugin spinProcessEnginePlugin() {
//        SpringBootSpinProcessEnginePlugin plugin = new SpringBootSpinProcessEnginePlugin();
//        return plugin;
//    }

}
