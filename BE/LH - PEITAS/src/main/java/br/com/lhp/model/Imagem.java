package br.com.lhp.model;

public class Imagem {
	private String pasta;
	private String conteudo;
	private int cod;
	

	public Imagem(String pasta, String conteudo) {
		this.pasta = pasta;
		this.conteudo = conteudo;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getPasta() {
		return pasta;
	}
	
	public String getConteudo() {
		return conteudo;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
}
