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

import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;

@Component
public class ImagemDaoJdbcImpl implements ImagemDao {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ImagemDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public List<Imagem> listarImagensPendentes() {
		return this.jdbcTemplate.query("SELECT ID, CAMINHO_IMAGEM FROM TBL_DADOS_NOTA", new ResultSetExtractor<List<Imagem>>() {

			@Override
			public List<Imagem> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				List<Imagem> imagens = new ArrayList<Imagem>();
				
				while (rs.next()) {
					Imagem imagem = new Imagem();
					imagem.setCaminho(rs.getString("CAMINHO_IMAGEM"));
					imagem.setId(rs.getInt("ID"));
					
					imagens.add(imagem);
				}
				return imagens;
			}
		});
	}


	@Override
	public void atualizarImagem(Integer idImagem, String jsonResultado, DadosNota dadosNota) {
		
		StringBuilder sql = new StringBuilder("UPDATE TBL_DADOS_NOTA SET ");
		sql.append("VALOR_NOTA = ?, ")
		   .append("COO = ?, ")
		   .append("CNPJ = ?, ")
		   .append("DATA_NOTA = ? ");
		sql.append("WHERE ID = ?");
		
		Object [] params = {
			dadosNota.getValorTotal(),
			dadosNota.getCoo(),
			dadosNota.getCnpj(),
			dadosNota.getData(),
			idImagem
		};
		
		this.jdbcTemplate.update(sql.toString(), params);
	}
	
	
	
}
