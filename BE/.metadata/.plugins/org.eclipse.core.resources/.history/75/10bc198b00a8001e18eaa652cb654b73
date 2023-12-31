package br.com.lhp.configuracao;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {
	private Properties props = new Properties();
	
	public Properties getProp(){
		try {
			props.load(new FileInputStream("./.properties/config.properties"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		return props;			
	}
}
