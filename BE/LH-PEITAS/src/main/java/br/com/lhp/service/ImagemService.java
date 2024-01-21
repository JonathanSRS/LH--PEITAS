package br.com.lhp.service;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.lhp.dao.Factory;
import br.com.lhp.dao.ImagemDao;
import br.com.lhp.model.Imagem;

public class ImagemService {
	private Factory connection;

	public ImagemService() {
		this.connection = new Factory();
	}

	public void cadastrarImagem(Imagem imagem, int cod) {
		try {
			Connection conn = connection.recuperarConexao();
			new ImagemDao(conn).armazenar(imagem, cod);
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void excluirImagem(int cod){
		try {
			Connection conn = connection.recuperarConexao();
			new ImagemDao(conn).delete(cod);
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
