package br.com.lhp.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniformeDaoTest {

	private Connection conn;

	@BeforeEach
	void setUp() throws Exception {
		this.conn = new Factory().recuperarConexaoTest();
		conn.setAutoCommit(false);
	}

	@AfterEach
	void afterEach() throws SQLException {
		this.conn.close();
	}

//	@Test
//	void testAtualizarUniforme() {
//		Uniforme testeSaopaulo = new Uniforme("primeiro uniforme", "2024", "Branco");
//		new UniformeDao(conn).armazenar(testeSaopaulo, "são paulo");
//		new UniformeDao(conn).atualizar(new TimeDao(conn).buscar("são paulo"), "new balance");
//		Assert.assertNotEquals(0, new UniformeDao(conn).buscar("new balance"));
//		System.out.println("Atualizar Informativo");
//	}
//
	@Test
	void testListaUniformes() {
		ArrayList<String> listDeUniformes = new ArrayList<>();
		new UniformeDao(conn)
				.listar()
				.stream().
				forEach((k) -> {
					if(k.getNome().contains("away")) {
						listDeUniformes.add(k.getCod() +", " +k.getInformativo() + ", " +k.getTemporada()+", "+ k.getNome());
					}
				});

		Assert.assertEquals("1, segundo uniforme, 2024-05-01, away", listDeUniformes.get(0));
	}
}
