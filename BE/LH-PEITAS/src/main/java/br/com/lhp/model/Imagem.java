package br.com.lhp.model;

import java.math.BigDecimal;

public class Imagem {
	private String pasta;
	private String conteudo;
	private BigDecimal cod;


	public Imagem(String pasta, String conteudo) {
		this.pasta = pasta;
		this.conteudo = conteudo;
	}

	public BigDecimal getCod() {
		return cod;
	}

	public String getPasta() {
		return pasta;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setCod(BigDecimal cod) {
		this.cod = cod;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
