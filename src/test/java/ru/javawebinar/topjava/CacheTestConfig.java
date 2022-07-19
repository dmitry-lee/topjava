package ru.javawebinar.topjava;

import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import static ru.javawebinar.topjava.Profiles.USER_TEST;

@Configuration
@Profile(USER_TEST)
public class CacheTestConfig {

    @Bean
    public CacheManager cacheManager() {
        return new NoOpCacheManager();
    }

    @Bean
    @Profile("user-test & !jdbc")
    public static PropertyOverrideConfigurer propertyOverrideConfigurer() {
        PropertyOverrideConfigurer configurer = new PropertyOverrideConfigurer();
        configurer.setLocation(new ClassPathResource("beanOverride.cfg"));
        return configurer;
    }
}
