package br.com.lhp.service;

import java.sql.Connection;
import java.util.Set;

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

	public int atualizarInformativo(int cod, String informativo) {
		Connection conn = connection.recuperarConexao();
		return new UniformeDao(conn).atualizar(cod, informativo);
	}
	
	public Set<Uniforme> listarUniforme() {
		Connection conn = connection.recuperarConexao();
		return new UniformeDao(conn).listar(); 
	}
	
	public Set<Object> listarCores() {
		Connection conn = connection.recuperarConexao();
		return new UniformeDao(conn).listarPorCor(); 
	}
	
	public int excluir(int id) {
		Connection conn = connection.recuperarConexao();
		return new UniformeDao(conn).delete(id);
	}

}
