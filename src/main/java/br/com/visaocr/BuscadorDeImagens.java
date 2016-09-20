package br.com.visaocr;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.visaocr.dao.ImagemDao;
import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;
import br.com.visaocr.domain.ResultadoVisionAPI;
import br.com.visaocrclient.GoogleVisionApiClient;
import br.com.visaocrparser.ProcessoExtracaoDadosNota;

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

			ResultadoVisionAPI resultado = client.enviarImagemParaVisionAPI(imagem.getCaminho());
			ProcessoExtracaoDadosNota extrator = new ProcessoExtracaoDadosNota(resultado.getTextoNota());
			DadosNota dadosDaNota = extrator.extrairTodosOsDadosDaNota();
			
			LOGGER.info("Dados formatados e extraidos: "+dadosDaNota.toString());
			dadosDaNota.setStatusNota(DadosNota.StatusNota.OK_OCR);
			this.imagemDao.atualizarImagem(imagem.getId(), resultado.getJsonResultado().toString(), dadosDaNota);
			
			LOGGER.info("Dados da analise persistidos no banco de dados. Finalizando analise");
		}

		LOGGER.info("fim da busca de imagens pendentes de analise");
	}


}
