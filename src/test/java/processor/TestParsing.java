package processor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Test;

class TestParsing {

	public String getOprNodeOutput() {
		
//		String command = "opr-node-hc-config.bat -user " + props.getUsername() + " -password " + props.getPassword() + " -list_nodes -all -det";
		String command = "systeminfo";
		System.out.println("Command is: " + command);
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
			e.printStackTrace();
			return null;
		}
		
		return sb.toString();
	}

	
	@Test
	void test() {
		
		String input = getOprNodeOutput();
		String[] lines = input.split("\\|-\\|");
		System.out.println("Length is: " + lines[1]);
		assertTrue(lines[1].contains("INADANEJ01"));
	}

}
