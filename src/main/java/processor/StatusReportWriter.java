package processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StatusReportWriter {
	
	Logger log = LoggerFactory.getLogger(StatusReportWriter.class);

	@Value("${output_dir}")
	private String output_dir;

	public void write(List<Record> recordList) {
		
//		Path path = Paths.get(output_dir);
		
		File outFile = new File(output_dir + File.separator + "OAStatusReport.csv");
		
		try {
			FileWriter fw = new FileWriter(outFile);
			fw.append("IP Address,Hostname,OS Version,Operations Agent Version,Operations Agent Status");
			fw.append("\n");
			recordList.forEach(r -> {
					try {
						fw.append(
								r.getIpAddressList().stream().filter(e -> !e.contains("::")).collect(Collectors.joining(" "))
								+ ","
								+ r.getCiName()
								+ ","
								+ r.getOperatingSystem()
								+ ","
								+ r.getOaVersion()
								+ ","
								+ Optional.ofNullable(r.getState()).orElse("")
								+ "\n"
								);
					} catch (IOException e) {
						e.printStackTrace();
					}

				
			});
			log.info("Records written to report: " + recordList.size());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
