package br.com.visaocr;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BuscadorDeImagens {
	
	
	private static Logger LOGGER = Logger.getLogger(BuscadorDeImagens.class);
	
	
	@Scheduled( fixedRate = 5000 )
	public void executarTarefa() {
		LOGGER.info("Olá, estou executando!!!");
	}
	
	
}
