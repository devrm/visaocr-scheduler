package br.com.visaocr;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.visaocr.dao.ImagemDao;
import br.com.visaocr.domain.Imagem;
import br.com.visaocr.domain.ResultadoVisionAPI;
import br.com.visaocrclient.GoogleVisionApiClient;

@Component
public class BuscadorDeImagens {
	
	private static Logger LOGGER = Logger.getLogger(BuscadorDeImagens.class);
	
	@Autowired
	private ImagemDao imagemDao;
	
	@Scheduled( fixedRate = 5000 )
	public void executarTarefa() {
		LOGGER.info("Inicio da busca de imagens pendentes de analise");
		
		GoogleVisionApiClient client = new GoogleVisionApiClient();
		
		for (Imagem imagem : imagemDao.listarImagensPendentes()) {
			List<String> caminhosImagens = new ArrayList<String>();
			
			caminhosImagens.add(imagem.getCaminho());
			List<ResultadoVisionAPI> listaResultado = client.enviarImagensParaEfetuarOCR(caminhosImagens);
			
			LOGGER.info("Resultado: "+listaResultado.get(0).getTextoNota());
		}
		
		LOGGER.info("fim da busca de imagens pendentes de analise");
	}
	
	
}
