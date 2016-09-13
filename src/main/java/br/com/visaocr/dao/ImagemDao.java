package br.com.visaocr.dao;

import java.util.List;

import br.com.visaocr.domain.Imagem;

public interface ImagemDao {
	 List<Imagem> listarImagensPendentes();
}
