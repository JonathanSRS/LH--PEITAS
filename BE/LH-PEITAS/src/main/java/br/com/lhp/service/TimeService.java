package br.com.lhp.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import br.com.lhp.dao.Factory;
import br.com.lhp.dao.TimeDao;
import br.com.lhp.model.Base;
import br.com.lhp.model.Time;

public class TimeService {
	private Factory connection;

	public TimeService() {
		this.connection = new Factory();
	}

	public void cadastrarTime(Time time) {
		try {
			Connection conn = connection.recuperarConexao();
			new TimeDao(conn).armazenar(time);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Set<Time> buscarPorTime(String nome) {
			Connection conn = connection.recuperarConexao();			
			return new TimeDao(conn).likeTime(nome);
	}

	public Set<Time> listaTimes(){
		Connection conn = connection.recuperarConexao();
		return new TimeDao(conn).listar();
	}

	public Set<Base> BaseDeTimes(String nome){
		Connection conn = connection.recuperarConexao();
		return new TimeDao(conn).innerJoin(nome);
	}
	
	public Set<Object> listarTodasLigas(){
		Connection conn = connection.recuperarConexao();
		return new TimeDao(conn).listarLiga();
	}
	
	public int excluirTime(int id) {
		Connection conn = connection.recuperarConexao();
		return new TimeDao(conn).delete(id);
	}

}
