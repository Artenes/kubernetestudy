package io.github.artenes.anotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ANotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ANotesApplication.class, args);
	}

}
