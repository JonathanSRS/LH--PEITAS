package br.com.lhp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public int buscar(String nome) {
		String sql = "SELECT * FROM T_LHP_TIME WHERE nm_time LIKE '%"+nome+"%'";
		int cod_time = 0;
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())cod_time = resultSet.getInt(1);
			
			resultSet.close();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cod_time;
	}
	
	
	public Set<Time> listar(){
		String sql = "SELECT * FROM T_LHP_TIME";
		Set<Time> times = new HashSet<>();
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String nome = resultSet.getString(2);
				String pais = resultSet.getString(3);
				String liga = resultSet.getString(4);
				times.add(new Time(nome,pais,liga));
			}
			resultSet.close();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return times;
	}
	
	public Set<Object> innerJoin(String nome){
		Set<Object> query = new HashSet<>();
		ArrayList<String> list = new ArrayList<>();
		
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
				
				list.add("Time: "+time);
				list.add("Camisa "+camisa);
				list.add("Temporada: "+temporada);
				list.add("Link: "+conteudo);
				
				query.add(list);
			}
			resultSet.close();
			preparedStatement.close();
			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return query;
	}
	
	public void delete(int cod) {
		String sql = "DELETE FROM T_LHP_TIME where cd_time = ?";
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, cod);
			
			preparedStatement.execute();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
