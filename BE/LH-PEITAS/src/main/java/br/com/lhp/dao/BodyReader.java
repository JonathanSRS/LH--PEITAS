package br.com.lhp.dao;

import java.io.BufferedReader;
import java.io.IOException;

public interface BodyReader {
	public default Object ler(BufferedReader data) throws IOException{
		StringBuilder obj = new StringBuilder();
		String linha;
		
		while((linha = data.readLine()) != null ){
			obj.append(linha);
		}
		return obj;
	}
}
