package br.com.visaocr.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import br.com.visaocr.condicoes.CondicaoArquivo;
import br.com.visaocr.condicoes.CondicaoBancoDeDados;
import br.com.visaocrcore.repositorio.RepositorioImagem;
import br.com.visaocrcore.repositorio.RepositorioImagemArquivo;
import br.com.visaocrcore.repositorio.RepositorioImagemJdbc;

@Configuration
public class ImagemRepoConfig {
	
	@Bean(name="imagemRepo")
	@Conditional(value = CondicaoArquivo.class)
	public RepositorioImagem getRepositorioImagemArquivo() {
		return new RepositorioImagemArquivo();
	}
	
	@Bean(name="imagemRepo")
	@Conditional(value = CondicaoBancoDeDados.class)
	public RepositorioImagem getRepositorioImagemJdbc() {
		return new RepositorioImagemJdbc();
	}

	
}
