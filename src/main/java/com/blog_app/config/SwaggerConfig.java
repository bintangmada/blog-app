package com.blog_app.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.stereotype.Component;

@Component
@OpenAPIDefinition(
        info = @Info(
                title = "Spring boot blog API",
                description = "Spring boot blog app rest api documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Bintang Mada S",
                        email = "mulaidarisehat@gmail.com",
                        url = "https://github.com/bintangmada"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring boot blog app dokumentation",
                url ="https://github.com/bintangmada"
        )
)
public class SwaggerConfig {
}
