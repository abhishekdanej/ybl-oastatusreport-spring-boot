package processor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import processor.config.ConfigProperties;

@Service
public class CommandExecutor {

	Logger log = LoggerFactory.getLogger(CommandExecutor.class);

	@Autowired
	private ConfigProperties props;

	public String getOprNodeOutput() {
		String command = props.getOprnode();
		return executeCmd(command);
	}

	private String executeCmd(String command) {
		log.info("Command is: " + command);
		StringBuilder sb = new StringBuilder();

		try {
//			ProcessBuilder pb = new ProcessBuilder("git status");
//	        pb.directory(new File(pathToDirectory));
			Process process = Runtime.getRuntime().exec(command);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			System.out.println("before while");
			while ((line = reader.readLine()) != null) {
//				System.out.println(line);
				sb.append(line);
				sb.append("|-|");
			}

			reader.close();

		} catch (IOException e) {
//			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
		log.info("Command output length: " + sb.length());
		
		return sb.toString();
	}

	public String getHCConfigOutput() {
		String command = props.getHcconfig();
		return executeCmd(command);
	}
}
