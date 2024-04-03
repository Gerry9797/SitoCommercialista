package com.commercialista.backend.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SwaggerUrlPrinter implements ApplicationRunner {
	
    @Autowired
    private Environment environment;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerUrlPrinter.class); 

    @Override
    public void run(ApplicationArguments args) {
    	String serverPort = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String swaggerUrl = "http://localhost" + ( serverPort != null ? ":" + serverPort : "") + contextPath + "/swagger-ui/index.html";
        LOGGER.info("Swagger UI is available at: " + swaggerUrl);
    }
}
