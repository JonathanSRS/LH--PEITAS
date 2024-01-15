package br.com.lhp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.lhp.model.Uniforme;
import br.com.lhp.service.TimeService;

public class UniformeDao {
	private Connection conn;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;
	protected TimeService service = new TimeService();
	public UniformeDao(Connection connection) {
		this.conn = connection;
		try {
			String sqlUser = "ALTER SESSION SET CURRENT_SCHEMA = LHP";
			preparedStatement = conn.prepareStatement(sqlUser);
			preparedStatement.execute();
			preparedStatement.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void armazenar(Uniforme uniforme, String time) {
		String sql = "INSERT INTO T_LHP_CAMISA(dt_ano, nm_camisa, ds_cor, st_status, cd_time)" 
					+ "VALUES (TO_DATE(?, 'yyyy'),?,?,'A',?)";
		
		int cod = service.buscarPorTime(time);
		try {
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, uniforme.getTemporada());
//			preparedStatement.setString(2, uniforme.getInformativo());
			preparedStatement.setString(2, uniforme.getNome());
			preparedStatement.setString(3, uniforme.getCor());
			preparedStatement.setInt(4, cod);
			
			preparedStatement.execute();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int buscar(String nome) {
		String sql = "SELECT * FROM T_LHP_CAMISA WHERE ds_informativo LIKE '%"+nome+"%'";
		int cod_camisa = 0;
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())cod_camisa = resultSet.getInt(1);
			
			resultSet.close();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return cod_camisa;
	}
	
	
	public void atualizar(int cod, String informativo) {
		String sql = "UPDATE T_LHP_CAMISA SET ds_informativo = ? WHERE cd_camisa = ?";
		try {
			preparedStatement = conn.prepareStatement(sql);
			
			preparedStatement.setString(1, informativo);
			preparedStatement.setInt(2, cod);
			
			preparedStatement.execute();
			preparedStatement.close();
//			conn.commit();
//			conn.close();
		}catch(SQLException e) {
			try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
			throw new RuntimeException(e);
		}
	}
	
	public Set<Uniforme> listar(){
		String sql = "SELECT * FROM T_LHP_CAMISA WHERE ST_STATUS = 'A'";
		Set<Uniforme> uniforme = new HashSet<>();
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int cod_camisa = resultSet.getInt(1);
				String temporada = resultSet.getDate(2).toString();
				String informativo = resultSet.getString(3);
				String nome = resultSet.getString(4);
				String cor = resultSet.getString(5);
				Uniforme un = new Uniforme(nome,temporada,cor);
				un.setInformativo(informativo);
				un.setCod(cod_camisa);
				uniforme.add(un);
			}
			resultSet.close();
			preparedStatement.close();
//			conn.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return uniforme;
	}
	
	
	public void delete(int cod) {
		String sql = "DELETE FROM T_LHP_CAMISA where cd_camisa = ?";
		
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
