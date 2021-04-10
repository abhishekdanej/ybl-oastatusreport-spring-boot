package processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class OprHCFileReader {
	
	Logger log = LoggerFactory.getLogger(OprHCFileReader.class);

	@Autowired
	ResourceLoader resourceLoader;
	
	private CommandExecutor cmd;
	
	@Autowired
	RecordDaoImpl recordDao;

//	@Value("${opr_hc_file}")
//	private String opr_hc_file;
	
//	private int count = 0;

	public List<Record> enrich(Map<String, Record> map) {
		
		List<Record> recordList = new ArrayList<Record>();

//		log.info("Reading file: " + opr_hc_file);
		try {
//			Resource resource = resourceLoader.getResource("file:" + opr_hc_file);
//			BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
//			String content = br.lines().collect(Collectors.joining("|-|"));

			String content = cmd.getHCConfigOutput();
			
			List<String> data = Arrays.asList(content.split("----------------------------------------------------"));
			
			log.info("Data block size: " + data.size());
			log.debug("D0: " + data.get(0));
			log.debug("D1: " + data.get(1));
			data.forEach(e -> {
				
				Optional<String> nodeIdValue = Arrays.asList(e.split("\\|-\\|"))
						.stream()
						.filter( k -> k.startsWith("Node Ci ID"))
						.map(k -> k.replaceAll("\\s", ""))
						.map(k -> k.replaceAll("NodeCiID=", ""))
						.findFirst();

				log.debug("Checking Node Ci ID: " + nodeIdValue);
				if(nodeIdValue.isPresent() && map.containsKey(nodeIdValue.get())) {
					Record r = map.get(nodeIdValue.get());
					Record y = enrichRecord(r, e);
					recordList.add(y);
				} else {
					log.debug("No match, moving to next data block.");
				}
				
				/*
				 * if(e.contains("Node Ci ID") ) {
				 * 
				 * // String nodeId = e.split("\\|-\\|")[0].split("=")[0].trim(); // String
				 * nodeIdValue = e.split("\\|-\\|")[0].split("=")[1].trim();
				 * 
				 * Optional<String> nodeIdValue = Arrays.asList(e.split("\\|-\\|")) .stream()
				 * .filter( k -> k.startsWith("Node Ci ID")) .map(k -> k.replaceAll("\\s", ""))
				 * .map(k -> k.replaceAll("NodeCiID=", "")) .findFirst();
				 * 
				 * log.info("Checking Node Ci ID: " + nodeIdValue);
				 * if(map.containsKey(nodeIdValue)) { enrichRecord(map.get(nodeIdValue), e); }
				 * else { log.info("No match, moving to next data block."); } }
				 */
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Records enriched: " + recordList.size());
		
		return recordList;
	}

	private Record enrichRecord(Record record, String e) {
		log.debug("Match found: " + e);
//		List<String> propList = Arrays.asList(str.split("\\|-\\|"))
//				.stream()
//				.filter(e -> e.contains("="))
//				.collect(Collectors.toList());
		
	    Map<String, String> resultMap = Arrays.stream(e.split("\\|-\\|"))
	    		.filter(k -> k.contains("="))
	    		.map( k -> k.split("="))
	    		.collect(Collectors.toMap(entry -> (String) entry[0].trim(), entry -> Optional.ofNullable(entry[1].trim()).orElse(null)));

	    log.debug(resultMap.toString());
	    
	    if(resultMap.containsKey("State")) {
	    	record.setState(Optional.ofNullable(resultMap.get("State")).orElse(null));
//	    	count++;
	    }
	    
	    return record;

	    	//		String animals = "1 = Dog, 2 = Cat, 3 = Bird";
//
//	    Map<Integer, String> result = Arrays.stream(
//	      animals.split(", ")).map(next -> next.split(" = "))
//	      .collect(Collectors.toMap(entry -> Integer.parseInt(entry[0]), entry -> entry[1]));
	}

}
