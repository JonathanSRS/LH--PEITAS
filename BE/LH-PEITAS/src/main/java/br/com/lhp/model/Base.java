package br.com.lhp.model;

public class Base {
	private String nome;
	private String uniforme;
	private String temporada;
	private String link;
	
	public Base(String nome, String uniforme, String temporada, String link) {
		this.nome = nome;
		this.uniforme = uniforme;
		this.temporada = temporada;
		this.link = link;
	}
	public String getNome() {
		return nome;
	}

	public String getUniforme() {
		return uniforme;
	}

	public String getTemporada() {
		return temporada;
	}

	public String getLink() {
		return link;
	}

}
