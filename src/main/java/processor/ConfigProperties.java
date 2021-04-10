package processor;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "admin")
public class ConfigProperties {

	private String username;
	private String password;
}
