package com.sales.manager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;


@OpenAPIDefinition(
        info = @Info(
                title = "Sales management project",
                version = "1.0.0",
                description = "This is a an assessment project",
                contact = @Contact(
                        name = "Tunde",
                        email = "Tunde4excel@gmail.com"
                ),
                license = @License(
                        name = "license",
                        url = "https://github.com/Engrtunze/manager"
                )
        )
)
public class SwaggerConfig {

}
