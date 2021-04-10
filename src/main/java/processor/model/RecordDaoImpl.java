package processor.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RecordDaoImpl {
	
	Logger log = LoggerFactory.getLogger(RecordDaoImpl.class);

	public Record createRecord(String str) {
		Record r = null;
		
		log.debug("Inbound: " + str);
		List<String> valueList = Arrays.asList(str.split("\\|-\\|"))
				.stream()
				.filter(e -> e.contains("="))
				.collect(Collectors.toList());
		if(valueList.size()>1) {
			r = new Record();
		}
		for(String value: valueList) {
			log.debug("Processed property: " + value);
			if(value.startsWith("Node Ci ID")) {
				r.setNodeID(value.split("=")[1].trim());
			}
			if(value.startsWith("Ci Type")) {
				r.setCiType(value.split("=")[1].trim());
			}
			if(value.startsWith("Ci Name")) {
				r.setCiName(value.split("=")[1].trim());
			}
			if(value.startsWith("Primary DNS Name")) {
				r.setPrimaryDnsName(Optional.ofNullable(value.split("=")[1].trim()).orElse(null));
			}
			if(value.startsWith("Operating System")) {
				r.setOperatingSystem(Optional.ofNullable(value.split("=")[1].trim()).orElse(null));
			}
			if(value.startsWith("IP Address")) {
				r.addIpAddress(Optional.ofNullable(value.split("=")[1].trim()).orElse(null));
			}
			if(value.startsWith("OA Version")) {
				r.setOaVersion(Optional.ofNullable(value.split("=")[1].trim()).orElse(null));
			}
		}

//		Map<Object, List<String[]>> valueMap = Arrays.stream(str.split("\\|-\\|"))
//				.filter(e -> e.contains("="))
//				.map(e -> e.split("="))
//				.collect(Collectors.groupingBy(e -> (e[0]), Collectors.toList()));

		
		
//		String animals = "1 = Dog, 2 = Cat, 3 = Bird";
//
//	    Map<Integer, String> result = Arrays.stream(
//	      animals.split(", ")).map(next -> next.split(" = "))
//	      .collect(Collectors.toMap(entry -> Integer.parseInt(entry[0]), entry -> entry[1]));


		log.debug("New record:" + r.toString());
		
		Set<String> select = new HashSet<String>();
		select.addAll(Arrays.asList("nt", "unix"));
		
		if( ! select.contains(r.getCiType())) {
			r = null;
		}

		
		return r;
	}
}
