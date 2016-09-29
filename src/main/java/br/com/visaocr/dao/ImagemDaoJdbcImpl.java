package br.com.visaocr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import br.com.visaocr.domain.DadosNota;
import br.com.visaocr.domain.Imagem;

@Component
public class ImagemDaoJdbcImpl implements ImagemDao {

	
	private static Logger LOGGER = Logger.getLogger(ImagemDaoJdbcImpl.class);
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ImagemDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public List<Imagem> listarImagensPendentes() {

		Object [] params = {
			DadosNota.StatusNota.PENDENTE_OCR.getStatus()	
		};

		return this.jdbcTemplate.query("SELECT ID, CAMINHO_IMAGEM FROM TBL_DADOS_NOTA WHERE STATUS = ?", params,
				new ResultSetExtractor<List<Imagem>>() {

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

		DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		TemporalAccessor data = formater.parse(dadosNota.getData());

		NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

		StringBuilder sql = new StringBuilder("UPDATE TBL_DADOS_NOTA SET ");
		sql.append("VALOR_NOTA = ?, ")
		   .append("COO = ?, ")
		   .append("CNPJ = ?, ")
		   .append("STATUS = ?, ")
		   .append("DATA_NOTA = ? ");
		sql.append("WHERE ID = ?");
		Number parse = null;
		try {
			parse = nf.parse(dadosNota.getValorTotal());
		} catch (ParseException e) {
			LOGGER.error("Erro no parse do valor da nota.", e);
		}

		Object [] params = {
				parse.doubleValue(),
				dadosNota.getCoo(),
				dadosNota.getCnpj(),
				dadosNota.getStatusNota().getStatus(),
				LocalDate.from(data),
				idImagem
		};

		this.jdbcTemplate.update(sql.toString(), params);
	}



}
