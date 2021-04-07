package processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommandExecutor {

	Logger log = LoggerFactory.getLogger(CommandExecutor.class);

	@Value("${admin_username}")
	private String admin_username;

	@Value("${admin_password}")
	private String admin_password;

	public String getOprNodeOutput() {
//		String command = "cmd /c git status";
		String command = "git --help";
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
				System.out.println(line);
				sb.append(line);
				sb.append(System.lineSeparator());
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return sb.toString();
	}
}
