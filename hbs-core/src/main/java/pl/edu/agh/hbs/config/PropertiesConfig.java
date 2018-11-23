package pl.edu.agh.hbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class PropertiesConfig {

    @Bean
    public Properties simulationProperties() throws IOException {
        final Resource resource = new ClassPathResource("/simulation.properties");
        return PropertiesLoaderUtils.loadProperties(resource);
    }
}
