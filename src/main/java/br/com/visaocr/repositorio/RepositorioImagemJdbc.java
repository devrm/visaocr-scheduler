package br.com.visaocr.repositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.visaocr.dao.ImagemDao;
import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;

@Component
public class RepositorioImagemJdbc implements RepositorioImagem {

	@Autowired
	private ImagemDao imagemDao;

	@Override
	public List<Imagem> listarImagensPendentes() {
		return imagemDao.listarImagensPendentes();
	}

	@Override
	public void atualizarImagem(Imagem imagem, String jsonResultado, DadosNota dadosNota) {
		imagemDao.atualizarImagem(imagem.getId(), jsonResultado, dadosNota);
	}


}
