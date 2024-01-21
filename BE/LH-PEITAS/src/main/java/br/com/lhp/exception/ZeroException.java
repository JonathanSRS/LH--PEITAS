package br.com.lhp.exception;

public class ZeroException extends RuntimeException{
	private static final long serialVersionUID = -6777102280511562230L;
	public ZeroException() {
		super("Time não encontrado");
	}
}
