package br.com.lhp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.lhp.model.Imagem;
import br.com.lhp.model.Time;

class TimeDaoTest {
	private Connection conn;
	
	@BeforeEach
	void setUp() throws Exception {
		this.conn =  new Factory().recuperarConexao();
		conn.setAutoCommit(false);
	}
	
	@AfterEach 
	public void afterEach() throws SQLException{
		conn.close();
	}

	@Test
	void testBuscarTime() {
		Time time = new Time("São Paulo", "Brasileirão", "Brasil");
		new TimeDao(conn).armazenar(time);
		Assert.assertNotEquals(0,new TimeDao(conn).buscar("são paulo"));
	}
	
	@Test
	void testListaTimes() {
		ArrayList<String> listDeTimes = new ArrayList<String>();
		new TimeDao(conn)
				.listar()
				.stream().
				forEach((k) -> {
					if(k.getNome().contains("são paulo")) {
						listDeTimes.add(k.getNome() +", " +k.getLiga() + ", " +k.getPais());
					};
				});
		
		Assert.assertEquals("são paulo, brasileirão serie a, brasil", listDeTimes.get(0));
	}
	
	@Test
	void test() {
		
		Imagem imagem = new Imagem("","https://www.futebolreligiao.com.br/image/cache/catalog/al-nassr/camisa-i-al-nassr-2023-2024-home-1-900x900.webp?1688595021");
		new ImagemDao(conn).armazenar(imagem, 1);
		Set<Object> base = new TimeDao(conn).innerJoin("al-nassr");
//		base.stream().forEach(System.out::println);
		System.out.println(base);
		Assert.assertTrue(base.size()>0);
	}

}
