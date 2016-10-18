package br.com.visaocr;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;
import br.com.visaocr.domain.ResultadoVisionAPI;
import br.com.visaocrclient.GoogleVisionApiClient;
import br.com.visaocrcore.repositorio.RepositorioImagem;
import br.com.visaocrcore.validador.VerificadorDadosNota;
import br.com.visaocrparser.ProcessoExtracaoDadosNota;

@Component
public class BuscadorDeImagens {

	private static Logger LOGGER = Logger.getLogger(BuscadorDeImagens.class);

	@Autowired
	private RepositorioImagem imagemRepo;
	
	@Scheduled( fixedDelay = 5000 )
	public void executarTarefa() {
		LOGGER.info("Inicio da busca de imagens pendentes de analise");

		GoogleVisionApiClient client = new GoogleVisionApiClient();

		for (Imagem imagem : imagemRepo.listarImagensPendentes()) {

			ResultadoVisionAPI resultado = client.enviarImagemParaVisionAPI(imagem.getCaminho());
			ProcessoExtracaoDadosNota extrator = new ProcessoExtracaoDadosNota(resultado.getTextoNota());
			DadosNota dadosDaNota = extrator.extrairTodosOsDadosDaNota();
			
			dadosDaNota.setStatusNota(DadosNota.StatusNota.OK_OCR);
			LOGGER.info("Dados formatados e extraidos: "+dadosDaNota.toString());
			
			if (! new VerificadorDadosNota(dadosDaNota).isDadosNotaValidos()) {
				dadosDaNota.setStatusNota(DadosNota.StatusNota.INCONSISTENTE_OCR);
			}
			
			this.imagemRepo.atualizarImagem(imagem, resultado.getTextoNota(), dadosDaNota);
		
			LOGGER.info("Finalizando analise");
		}

		LOGGER.info("fim da busca de imagens pendentes de analise");
	}


}
