package com.tjr.springairag.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "tjr.aiapp")
@Getter
@Setter
public class SimpleVectorStoreProperties {
    private String vectorStorePath;
    private List<Resource> documentsToLoad;
}
