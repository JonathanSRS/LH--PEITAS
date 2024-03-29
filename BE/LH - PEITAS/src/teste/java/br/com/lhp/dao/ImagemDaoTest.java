package br.com.lhp.dao;



import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.lhp.model.Imagem;

class ImagemDaoTest {
	private Connection conn;
	
	@BeforeEach
	public void beforeEach() throws SQLException {
		this.conn =  new Factory().recuperarConexao();
		conn.setAutoCommit(false);
	}
	
	@AfterEach 
	public void afterEach() throws SQLException{
		conn.close();
	}
	
	@Test
	void cadastrarImagem() {
		Imagem imagem = new Imagem("","https://www.futebolreligiao.com.br/image/cache/catalog/al-nassr/camisa-i-al-nassr-2023-2024-home-1-900x900.webp?1688595021");
		new ImagemDao(conn).armazenar(imagem, 1);
//		Assert.assertNotNull(imagem.getCod());
	}	
}
