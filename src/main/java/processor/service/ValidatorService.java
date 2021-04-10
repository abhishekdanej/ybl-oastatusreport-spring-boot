package processor.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

	Logger log = LoggerFactory.getLogger(ValidatorService.class);
	
	// way to read properties from configuration file.
	@Value("${output_dir}")
	private String output_dir;

	public boolean validateInputs(String[] args) {
//		log.info("opr_node_file: "+ opr_node_file + " -validation: " + new File(opr_node_file).exists());
//		log.info("opr_hc_file: "+ opr_hc_file + " -validation: " + new File(opr_hc_file).exists());
		log.info("output_dir: "+ output_dir + " -validation: " + new File(output_dir).exists());
		
		if (Files.isDirectory(Paths.get(output_dir))) {
			log.info("File validations succeeded");
			return true;
		} else {
			log.error("====USAGE===");
			log.error("java -jar target/ybl-oastatusreport-spring-boot-0.0.1-SNAPSHOT.jar \\"
					+ "--spring.config.location=/mnt/c/Users/ADanej/Documents/My\\ Stuff/workspace_sts4/ybl-oastatusreport-spring-boot/config.properties \\"
					+ "--spring.profiles.active=<prod or dev>");
			return false;
		}
	}
}
