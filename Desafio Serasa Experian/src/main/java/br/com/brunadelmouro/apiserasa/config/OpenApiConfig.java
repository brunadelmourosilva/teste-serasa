package br.com.brunadelmouro.apiserasa.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private static Info metaData() {
    return new Info()
        .title("Desafio Serasa Experian")
        .description("")
        .license(
            new License()
                .name("Project hosted on Github")
                .url("https://github.com/brunadelmourosilva/teste-serasa/tree/main"))
        .version("1.0");
  }

  @Bean
  public OpenAPI microserviceOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .info(metaData());
  }
}
