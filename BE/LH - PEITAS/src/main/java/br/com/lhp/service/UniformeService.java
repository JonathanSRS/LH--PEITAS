package br.com.lhp.service;

import java.sql.Connection;

import br.com.lhp.dao.Factory;
import br.com.lhp.dao.UniformeDao;
import br.com.lhp.model.Uniforme;

public class UniformeService {
	private Factory connection;
	
	public UniformeService() {
		this.connection = new Factory();
	}
	
	public void cadastrarUniforme(Uniforme uniforme, String time) {
		Connection conn = connection.recuperarConexao();
		new UniformeDao(conn).armazenar(uniforme, time);
	}
	
	public void atualizarInformativo(int cod, String informativo) {
		Connection conn = connection.recuperarConexao();
		new UniformeDao(conn).atualizar(cod, informativo);
	}
	

}
