package br.com.lhp.model;

public class Time {
	private  int id;
	private  String nome;
	private  String pais;
	private  String liga;

	public Time(String nome, String pais, String liga) {
		this.nome = nome;
		this.pais = pais;
		this.liga = liga;
	}
	
	public Time(int id, String nome, String pais, String liga) {
		this.nome = nome;
		this.pais = pais;
		this.liga = liga;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	public String getPais() {
		return pais;
	}

	public String getLiga() {
		return liga;
	}

//	@Override
//	public String toString() {
//		return "{"+
//				"\"Nome\""+":"+"\""+nome+"\""+
//				", \"Pais\""+":"+"\""+pais+"\""+
//				", \"Liga\""+":"+"\""+liga+"\""+"}"
//				;
//	}

}
