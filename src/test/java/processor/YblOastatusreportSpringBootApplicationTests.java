package processor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YblOastatusreportSpringBootApplicationTests {

//	@Test
//	void contextLoads() {
//	}
	
	@Test
	void testStream() {
		String s = "Operating System	= ";
		s.replaceAll("\\s", "");
		ArrayList<String> ar = (ArrayList<String>) Stream.of(s.split("=")).limit(2).map( e -> new String(e)).collect(Collectors.toList());
		assertTrue(ar.size()==2);
	}

}
