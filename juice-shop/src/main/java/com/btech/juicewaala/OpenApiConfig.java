package com.btech.juicewaala;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Setting up custom servers (like staging, production)
                .servers(getServers())
                // API general information
                .info(new Info()
                        .title("Btech Juice Waala API")
                        .description("API for managing a juice shop, providing operations to view, add, and delete juices.")
                        .version("1.0.0")
                        .termsOfService("https://btechjuicewaala.com/terms")
                        .contact(new Contact()
                                .name("Juice Support Team")
                                .url("https://btechjuicewaala.com/contact")
                                .email("support@btechjuicewaala.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                )
                // Tags for grouping APIs logically
                .tags(Arrays.asList(
                        new Tag().name("Juices").description("Operations related to juice management"),
                        new Tag().name("Admin").description("Administrative operations")
                ))
                // Adding external documentation (extra information related to API usage)
                .externalDocs(new ExternalDocumentation()
                        .description("Btech Juice Waala External Documentation")
                        .url("https://btechjuicewaala.com/docs"));
    }

    // Define multiple server environments (like production, staging)
    private List<Server> getServers() {
        return Arrays.asList(
                new Server().url("http://localhost:8080").description("Local Development Server"),
                new Server().url("https://staging.btechjuicewaala.com").description("Staging Environment"),
                new Server().url("https://btechjuicewaala.com").description("Production Environment")
        );
    }
}
