package processor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "cmd")
public class ConfigProperties {

	private String oprnode;
	private String hcconfig;
}
