package com.optimagrowth.license.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Load your message properties files here
        messageSource.setBasenames(
                "classpath:messages",  // Default messages (e.g., messages_en.properties)
                "classpath:messages_fr",  // French messages (e.g., messages_fr.properties)
                "classpath:messages_es"   // Spanish messages (e.g., messages_es.properties)
        );

        // Set encoding
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }
}
