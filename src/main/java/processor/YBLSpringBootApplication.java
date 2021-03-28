package processor;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YBLSpringBootApplication implements CommandLineRunner {
	
	Logger log = LoggerFactory.getLogger(YBLSpringBootApplication.class);
	
	@Autowired
	private ValidatorService validatorService;
	
	@Autowired
	private OprNodeFileReader nodeReader;

	@Autowired
	private OprHCFileReader hCReader;
	
	@Autowired
	private StatusReportWriter reportWriter;
	
	public static void main(String[] args) {
		SpringApplication.run(YBLSpringBootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		log.info("Application starting.");
		if(validatorService.validateInputs(args)) {
			Map<String, Record> map = nodeReader.reader();
			
			List<Record> recordList = hCReader.enrich(map);
			
			log.info("Final processed record count: " + recordList.size());
			
			reportWriter.write(recordList);
		}
		log.info("Application ending.");
	}



}
