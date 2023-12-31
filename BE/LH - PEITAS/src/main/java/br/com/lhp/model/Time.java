package br.com.lhp.model;

public class Time {
	private  String nome;
	private  String pais;
	private  String liga;
	
	public Time(String nome, String pais, String liga) {
		this.nome = nome;
		this.pais = pais;
		this.liga = liga;
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

	@Override
	public String toString() {
		return "Time{"+
				"Nome "+nome+
				", Pais "+pais+
				", Liga "+liga+"}"
				;
	}

}
