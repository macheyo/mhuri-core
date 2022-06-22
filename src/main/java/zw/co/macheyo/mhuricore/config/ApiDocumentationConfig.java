package zw.co.macheyo.mhuricore.config;

import io.swagger.annotations.*;


@SwaggerDefinition(
        info = @Info(
                description = "Mhuri Resources",
                version = "V1.0.0",
                title = "Mhuri Resource API",
                contact = @Contact(
                        name = "Kudzai Justice Macheyo",
                        email = "kudzai@macheyo.co.zw",
                        url = "https://www.mhuri.macheyo.co.zw"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json", "application/xml"},
        produces = {"application/json", "application/xml"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "Read This For Sure", url = "https://www.mhuri.macheyo.co.zw")
)
public interface ApiDocumentationConfig {
}
