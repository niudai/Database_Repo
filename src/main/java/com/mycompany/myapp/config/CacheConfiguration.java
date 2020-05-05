package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Campus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Campus.class.getName() + ".majors");
            createCache(cm, com.mycompany.myapp.domain.Major.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Major.class.getName() + ".schoolClasses");
            createCache(cm, com.mycompany.myapp.domain.Major.class.getName() + ".teachers");
            createCache(cm, com.mycompany.myapp.domain.Major.class.getName() + ".courses");
            createCache(cm, com.mycompany.myapp.domain.Record.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SchoolClass.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SchoolClass.class.getName() + ".students");
            createCache(cm, com.mycompany.myapp.domain.Grade.class.getName());
            createCache(cm, com.mycompany.myapp.domain.People.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Student.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Student.class.getName() + ".exceptions");
            createCache(cm, com.mycompany.myapp.domain.Student.class.getName() + ".records");
            createCache(cm, com.mycompany.myapp.domain.Teacher.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Teacher.class.getName() + ".courses");
            createCache(cm, com.mycompany.myapp.domain.Course.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Course.class.getName() + ".times");
            createCache(cm, com.mycompany.myapp.domain.Interval.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Semaster.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Exception.class.getName());
            createCache(cm, com.mycompany.myapp.domain.JException.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
