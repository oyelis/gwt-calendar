package com.luxoft.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(RootConfig.BASE_PACKAGE)
@Import({PropertyPlaceholderConfig.class, PersistenceConfig.class})
public class RootConfig {
    public static final String BASE_PACKAGE = "com.luxoft.server";
    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".repository";
    public static final String ENTITY_PACKAGE = BASE_PACKAGE + ".entity";
}
