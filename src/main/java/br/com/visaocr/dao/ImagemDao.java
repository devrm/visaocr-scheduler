package br.com.visaocr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import br.com.visaocr.Imagem;

@Component
public class ImagemDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ImagemDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public List<Imagem> listarImagensPendentes() {
		return this.jdbcTemplate.query("SELECT * FROM TBL_DADOS_NOTA", new ResultSetExtractor<List<Imagem>>() {

			@Override
			public List<Imagem> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<Imagem> imagens = new ArrayList<Imagem>();
				
				while (rs.next()) {
					Imagem imagem = new Imagem();
					imagem.setCaminho(rs.getString("CAMINHO_IMAGEM"));
					imagens.add(imagem);
				}
				return imagens;
			}
		});
	}
	
	
	
}
