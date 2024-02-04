package br.com.lhp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.lhp.model.Imagem;

public class ImagemDao {
	private Connection conn;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	public ImagemDao(Connection connection) {
		this.conn = connection;

		try {
			String sqlUser = "ALTER SESSION SET CURRENT_SCHEMA = LHP";
			preparedStatement = conn.prepareStatement(sqlUser);
			preparedStatement.execute();
			preparedStatement.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void armazenar(Imagem imagem, int cod) {
		String sql = "INSERT INTO T_LHP_IMAGEM(cd_camisa, ds_conteudo) VALUES (?, ?)";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, cod);
			preparedStatement.setString(2, imagem.getConteudo());
//			preparedStatement.setString(3, imagem.getPasta());
			preparedStatement.execute();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int delete(int cod) {
		String sql = "DELETE FROM T_LHP_IMAGEM where cd_imagem = ?";
		int retorno = 1;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, cod);

			retorno = preparedStatement.executeUpdate();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return retorno;
	}
}
