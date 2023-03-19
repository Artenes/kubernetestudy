package io.github.artenes.anotes;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ANotesApplicationTests {

	@Test
	void contextLoads() {

		Assertions.assertTrue("    ".isBlank());

	}

}
