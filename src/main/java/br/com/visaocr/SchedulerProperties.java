package br.com.visaocr;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("app.fonte")
public class SchedulerProperties {

	@Getter @Setter
	private String imagensDe = "bd";
	
}
