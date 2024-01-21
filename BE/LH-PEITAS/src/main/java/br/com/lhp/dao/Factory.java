package br.com.lhp.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Factory {
	public Connection recuperarConexao(){
		Context initContext;
			try {
				initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				DataSource ds = (DataSource) envContext.lookup("jdbc/dbTeste");
				 return ds.getConnection();
			} catch (NamingException|SQLException e) {
				throw new RuntimeException(e);
			}

	}
	
	public Connection recuperarConexaoTest(){
		try {
			return createDataSource()
					.getConnection();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Properties getProp(){
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("./.properties/config.properties"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		return props;
	}
	
	private HikariDataSource createDataSource() {
		Properties props = getProp();
		HikariConfig config = new HikariConfig();;
		config.setJdbcUrl("jdbc:"
				+props.getProperty("Banco")
				+":thin:@"+props.getProperty("Host")
				+":"+props.getProperty("Porta")
				+":"+props.getProperty("Sid"));
		
		config.setUsername( props.getProperty("Login") );
		config.setPassword( props.getProperty("Senha") );
		config.setMaximumPoolSize(10);
		return new HikariDataSource(config);
	}
	
}

// https://docs.oracle.com/cd/E11882_01/appdev.112/e13995/oracle/jdbc/OracleDriver.html