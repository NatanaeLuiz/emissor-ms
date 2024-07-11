package com.emissor_ms.emissor_ms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "emissor-ms API",
				version = "1.0",
				contact = @Contact(
						name = "Natanael Luiz",
						url = "https://natanaeluiz.github.io/resume/",
						email = "natanael.10@hotmail.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0.html"
				)
		)
)
public class EmissorMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmissorMsApplication.class, args);
	}

}
