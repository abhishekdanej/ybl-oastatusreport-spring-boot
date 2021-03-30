package processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/*
Node Ci ID		= 425d3b58e490c23aa6766fa5c21817b3
Ci Type			= unix
Ci Name			= servernt103
Primary DNS Name	= servernt103.yl.com
Operating System	= Linux Red Hat 7.4 3.10.0
Processor Family	= x86_64
IP Address		= fe80::250:56ff:feb6:db36
IP Address		= fe80::250:56ff:feb6:166c
IP Address		= 152.158.151.157
IP Address		= 20.24.26.21
Core ID			= bacb60e8-c4b0-75a0-17aa-df4b4ec0eb08
OA Version		= 12.04.006
*/

@Service
public class OprNodeFileReader {

	Logger log = LoggerFactory.getLogger(OprNodeFileReader.class);

	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	RecordDaoImpl recordDao;

	@Value("${opr_node_file}")
	private String opr_node_file;

	public Map<String,Record> reader() {
//		ArrayList<Record> recordList = new ArrayList<Record>();
		
		Map<String,Record> recordMap = new HashMap<>();
		
		try {
			log.info("Reading file: " + opr_node_file);
			Resource resource = resourceLoader.getResource("file:" + opr_node_file);
			BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
			
			String content = br.lines().collect(Collectors.joining("|-|"));
//			log.info(content.substring(0, 1020));
			
			List<String> data = Arrays.asList(content.split("----------------------------------------------------"));
			
//			log.info("D0: " + data.get(0));
//			log.info("D1: " + data.get(1));
			data.forEach(e -> {
				if(e.length()>2 && e.contains("=")) {
					log.debug("Data Size: " + e.length());
					Record r = recordDao.createRecord(e);
					if(r != null) {
						recordMap.put(r.getNodeID(), r);
					}
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	log.info("Total records processed: " + recordMap.size());
	return recordMap;
//	log.info("Printing first 2 records");
//	log.info(recordList.get(0).toString());
//	log.info(recordList.get(1).toString());
//	recordList.subList(0, 10).forEach(e -> {
//		log.info(e.toString());
//		});
	}
}
