package br.com.visaocr;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.visaocr.dao.ImagemDao;

@Component
public class BuscadorDeImagens {
	
	
	private static Logger LOGGER = Logger.getLogger(BuscadorDeImagens.class);
	@Autowired
	private ImagemDao imagemDao;
	
	
	@Scheduled( fixedRate = 5000 )
	public void executarTarefa() {
		LOGGER.info("Inicio da busca de imagens pendentes de analise");
		
		for (Imagem imagem : imagemDao.listarImagensPendentes()) {
			LOGGER.info("Que bagunca: "+imagem.getCaminho());
		}
		
		LOGGER.info("fim da busca de imagens pendentes de analise");
	}
	
	
}
