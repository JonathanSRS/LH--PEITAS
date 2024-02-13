package br.com.lhp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.lhp.model.Base;
import br.com.lhp.model.Time;

public class TimeDao {
	private Connection conn;
	protected PreparedStatement preparedStatement;
	protected PreparedStatement statement;
	protected ResultSet resultSet;

	public TimeDao(Connection connection) {
		this.conn = connection;
		try {
			String sqlUser = "ALTER SESSION SET CURRENT_SCHEMA = LHP";
			statement = conn.prepareStatement(sqlUser);
			statement.execute();
			statement.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void armazenar(Time time) {
		String sql = "INSERT INTO T_LHP_TIME (nm_time, nm_pais, ds_liga)"+
				"VALUES (?,?,?)";

		try {
			preparedStatement = conn.prepareStatement(sql);

			preparedStatement.setString(1, time.getNome());
			preparedStatement.setString(2, time.getPais());
			preparedStatement.setString(3, time.getLiga());

			preparedStatement.execute();
			preparedStatement.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Set<Time> likeTime(String nome) {
		String sql = "SELECT * FROM T_LHP_TIME WHERE nm_time LIKE '%"+nome.toLowerCase()+"%'";
		Set<Time> times = new HashSet<>();
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int id =resultSet.getInt(1);
				String nomeTime = resultSet.getString(2);
				String pais = resultSet.getString(3);
				String liga = resultSet.getString(4);
				times.add(new Time(id,nomeTime,pais,liga));
			}

			resultSet.close();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return times;
	}


	public Set<Time> listar(){
		String sql = "SELECT * FROM T_LHP_TIME";
		Set<Time> times = new HashSet<>();

		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				int id =resultSet.getInt(1);
				String nome = resultSet.getString(2);
				String pais = resultSet.getString(3);
				String liga = resultSet.getString(4);
				times.add(new Time(id, nome,pais,liga));
			}
			resultSet.close();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return times;
	}
	public Set<Object> listarLiga(){
		String sql = "SELECT DISTINCT ds_liga FROM T_LHP_TIME";
		Set<Object> ligas = new HashSet<>();
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String liga = resultSet.getString(1);
				ligas.add(liga);
			}
			resultSet.close();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ligas;
	}

	public Set<Base> innerJoin(String nome){
		Set<Base> list = new HashSet<>();

		String sql ="SELECT "
				+"t.cd_time"
	    	+",t.nm_time"
	    	+",c.nm_camisa"
	    	+",c.dt_ano"
	    	+",i.ds_conteudo"
	    +" FROM T_LHP_TIME t"
	    	+" INNER JOIN T_LHP_CAMISA c ON t.cd_time = c.cd_time"
	    	+" INNER JOIN T_LHP_IMAGEM i ON c.cd_camisa = i.cd_camisa"
	    +" WHERE t.nm_time LIKE '%"+nome+"%'";
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				String time = resultSet.getString(2);
				String camisa = resultSet.getString(3);
				String temporada = resultSet.getString(4);
				String conteudo = resultSet.getString(5);

				list.add(new Base(time,camisa,temporada,conteudo));

			}
			resultSet.close();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	public int delete(int cod) {
		String sql = "DELETE FROM T_LHP_TIME where cd_time = ?";
		int retorno = 0; 
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, cod);

			retorno = preparedStatement.executeUpdate();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return retorno;
	}
}
