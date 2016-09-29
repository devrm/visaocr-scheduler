package br.com.visaocr.repositorio;

import java.util.List;

import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;


public interface RepositorioImagem {

	 List<Imagem> listarImagensPendentes();
	 
	 void atualizarImagem(Imagem imagem, String jsonResultado, DadosNota dadosNota);
	
}
