package br.com.lhp.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import br.com.lhp.dao.Factory;
import br.com.lhp.dao.TimeDao;
import br.com.lhp.exception.ZeroException;
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

	public int buscarPorTime(String nome) {
			Connection conn = connection.recuperarConexao();
			int result = new TimeDao(conn).buscar(nome);
			if(result == 0) {
				throw new ZeroException();
			}
			return result;
	}

	public Set<Time> listaTimes(){
		Connection conn = connection.recuperarConexao();
		return new TimeDao(conn).listar();
	}

	public Set<Base> BaseDeTimes(String nome){
		Connection conn = connection.recuperarConexao();
		return new TimeDao(conn).innerJoin(nome);
	}

}
