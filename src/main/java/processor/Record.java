package processor;


import java.util.ArrayList;

import lombok.Data;

@Data
public class Record {

	private String nodeID;
	private String ciType;
	private String ciName;
	private String primaryDnsName;
	private String operatingSystem;
	private ArrayList<String> ipAddressList = new ArrayList<>();
	private String oaVersion;
	private String healthCheck;
	private String state;

	public void addIpAddress(String ip) {
		this.ipAddressList.add(ip);
	}
}
