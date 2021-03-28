package processor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

	Logger log = LoggerFactory.getLogger(ValidatorService.class);
	
	@Value("${opr_node_file}")
	private String opr_node_file;

	@Value("${opr_hc_file}")
	private String opr_hc_file;
	
	@Value("${output_dir}")
	private String output_dir;

	public boolean validateInputs(String[] args) {
		log.info("opr_node_file: "+ opr_node_file + " -validation: " + new File(opr_node_file).exists());
		log.info("opr_hc_file: "+ opr_hc_file + " -validation: " + new File(opr_hc_file).exists());
		log.info("output_dir: "+ output_dir + " -validation: " + new File(output_dir).exists());
		
		if (Files.isReadable(Paths.get(opr_node_file)) && Files.isReadable(Paths.get(opr_hc_file))
				&& Files.isDirectory(Paths.get(output_dir))) {
			log.info("File validations succeeded");
			return true;
		} else {
			log.error("java -jar <jarFile> --opr_node_file=\"<path to node file>\""
					+ " --opr_hc_file=\"<path to hc file\"> --output_dir=\"<output directory for report>\"");
			return false;
		}
	}
}
