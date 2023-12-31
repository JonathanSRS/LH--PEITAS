package br.com.lhp.service;

import java.sql.Connection;

import br.com.lhp.dao.Factory;
import br.com.lhp.dao.ImagemDao;
import br.com.lhp.model.Imagem;

public class ImagemService {
	private Factory connection;
	
	public ImagemService() {
		this.connection = new Factory();
	}
	
	public void cadastrarImagem(Imagem imagem, int cod) {
		Connection conn = connection.recuperarConexao();
		new ImagemDao(conn).armazenar(imagem, cod);
	}
	
	public void excluirImagem(int cod) {
		Connection conn = connection.recuperarConexao();
		new ImagemDao(conn).delete(cod);
	}

}
