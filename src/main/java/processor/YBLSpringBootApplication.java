package processor;

import java.util.List;
import java.util.Map;
//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/*
 * Author: Abhishek Danej
 * Date: 1 April 2021
 */

@SpringBootApplication
@ConfigurationPropertiesScan
public class YBLSpringBootApplication implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private OprNodeFileReader nodeReader;

	@Autowired
	private OprHCFileReader hCReader;

	@Autowired
	private StatusReportWriter reportWriter;
	
	@Autowired
	private ConfigProperties props;

	public static void main(String[] args) {
		try {
			SpringApplication.run(YBLSpringBootApplication.class, args);
		} catch (Exception e) {
			printUsage();
		}

	}

	private static void printUsage() {
		System.out.println("=== USAGE ===");
		System.out.println("java -jar <jarFile> --opr_node_file=/opt/<path to node file>"
				+ " --opr_hc_file=/opt/<path to hc file --output_dir=/opt/<output directory for report>");
		System.out.println("OR");
		System.out.println("java -jar <jarFile> --spring.config.location=/opt/<path_to_config_file>/config.properties");
	}

	@Override
	public void run(String... args) throws Exception {

		log.info("Application starting.");
		log.info("Config Properties username:" + props.getUsername());
		log.debug("Config Properties password:" + props.getPassword());

//		boolean isValidated = false;
//			isValidated = validatorService.validateInputs(args); 

		Map<String, Record> map = nodeReader.reader();

		List<Record> recordList = hCReader.enrich(map);

		log.info("Final processed record count: " + recordList.size());

		reportWriter.write(recordList);
		log.info("Application ending.");
	}

}
