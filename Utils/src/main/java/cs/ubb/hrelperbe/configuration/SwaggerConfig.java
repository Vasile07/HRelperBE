package cs.ubb.hrelperbe.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Configuration class for Swagger documentation.
 * This class defines the security scheme for JWT-based authentication to be used in Swagger UI.
 * <p>
 * The `@SecurityScheme` annotation configures the details of the bearer token,
 * including its type, format, and where it is included in the HTTP request.
 */
@SecurityScheme(
        name = "bearerAuth",                    // The name of the security scheme to be referenced in controllers
        description = "JWT auth description",  // A brief description of the security scheme
        scheme = "bearer",                     // Specifies the type of authentication scheme (Bearer)
        type = SecuritySchemeType.HTTP,        // Indicates an HTTP-based authentication
        bearerFormat = "JWT",                  // Specifies the format of the token (JWT)
        in = SecuritySchemeIn.HEADER           // Indicates that the token will be passed in the HTTP header
)
public class SwaggerConfig {
// This class is intentionally left empty as it only serves as a configuration holder
// for Swagger's security scheme setup.
}