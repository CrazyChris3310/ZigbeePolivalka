package com.example.zigbeepolivalka;

import org.springframework.context.annotation.Bean;

public class Configurations {
    //TODO Проверить без конфигурации
    @Bean(name="freemarkerConfiguration")
    public freemarker.template.Configuration getFreeMarkerConfiguration() {
        freemarker.template.Configuration config = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());
        config.setClassForTemplateLoading(this.getClass(), "/templates/");
        return config;
    }
}
