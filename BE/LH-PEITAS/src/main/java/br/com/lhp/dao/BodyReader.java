package br.com.lhp.dao;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public interface BodyReader {
	public default JsonObject ler(BufferedReader data) throws IOException{
		try {
			StringBuilder obj = new StringBuilder();
			String linha;
			
			while((linha = data.readLine()) != null ){
				obj.append(linha);
			}
			JsonObject jsonTxt = new Gson().fromJson(obj.toString(), JsonObject.class);
			return jsonTxt;			
		}catch(NullPointerException e) {
			throw new NullPointerException();
		}
	}
}
