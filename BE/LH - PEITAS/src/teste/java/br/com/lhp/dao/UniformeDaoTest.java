package br.com.lhp.dao;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.lhp.model.Uniforme;

class UniformeDaoTest {

	private Connection conn;
	
	@BeforeEach
	void setUp() throws Exception {
		this.conn = new Factory().recuperarConexao();
		conn.setAutoCommit(false);
	}
	
	@AfterEach
	void afterEach() throws SQLException {
		this.conn.close();
	}
	
	@Test
	void testAtualizarUniforme() {
		Uniforme testeSaopaulo = new Uniforme("primeiro uniforme", "2024/01", "Branco");
		new UniformeDao(conn).armazenar(testeSaopaulo, "são paulo");
		new UniformeDao(conn).atualizar(new TimeDao(conn).buscar("são paulo"), "new balance");
		Assert.assertNotEquals(0, new UniformeDao(conn).buscar("new balance"));
//		System.out.println("Atualizar Informativo");
	}

//		System.out.println("Teste de Cadastro de uniforme");
}
