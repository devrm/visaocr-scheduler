package br.com.visaocr.repositorio;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.visaocr.dao.ImagemDao;
import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;

@Component
public class RepositorioImagemArquivo implements RepositorioImagem {

	private static final Logger LOGGER = Logger.getLogger(RepositorioImagemArquivo.class);
	
	@Autowired
	private ImagemDao imagemDao;
	
	@Value("${caminho.repositorio}")
	private String caminhoRepositorioImagem;
	
	@Value("${caminho.repositoriolidas}")
	private String caminhoRepositorioImagensLidas;
	
	
	@Override
	public List<Imagem> listarImagensPendentes() {
		
		List<Imagem> listaImagensPendentes = new ArrayList<Imagem>();
		try {
			Path diretorioRepositorio = FileSystems.getDefault().getPath(caminhoRepositorioImagem);
			DirectoryStream<Path> stream = Files.newDirectoryStream(diretorioRepositorio);
			
			for (Path path : stream) {
				Imagem imagem = new Imagem();
				imagem.setCaminho(path.toString());
				listaImagensPendentes.add(imagem);
			}
			
		} catch (IOException e) {
			LOGGER.error("Ocorreu um erro ao acessar o repositorio de imagens.", e);
		}
		return listaImagensPendentes;
	}

	@Override
	@Transactional
	public void atualizarImagem(Imagem imagem, String jsonResultado, DadosNota dadosNota) {
		imagemDao.atualizarImagem(imagem.getId(), jsonResultado, dadosNota);
		
		moverImagemAnalisada(imagem.getCaminho());
		
	}
	
	private void moverImagemAnalisada(final String caminhoImagem) {
		
		try {
			LocalDate data = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			Path diretorioRepositorioLidas = FileSystems.getDefault().getPath(caminhoRepositorioImagensLidas+data.format(formatter)+File.separatorChar);
			
			Path diretorioLidas = diretorioRepositorioLidas;
			if (! Files.exists(diretorioRepositorioLidas)) {
				diretorioLidas = Files.createDirectory(diretorioRepositorioLidas);
			} 
			
			Path arquivoDestino = diretorioLidas.resolve("lida.jpg");
			
			Files.move(FileSystems.getDefault().getPath(caminhoImagem), arquivoDestino);
			
		} catch (IOException e) {
			LOGGER.error("Problemas ao realizar a leitura da imagem analisada.", e);
		}
		
	}
	

}
