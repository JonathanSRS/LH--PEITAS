package br.com.lhp.exception;

public class ValorNaoExiste extends NullPointerException{
	private static final long serialVersionUID = -9119044494820142345L;
	public ValorNaoExiste() {
		super("valor não encontrado");
	}
}
