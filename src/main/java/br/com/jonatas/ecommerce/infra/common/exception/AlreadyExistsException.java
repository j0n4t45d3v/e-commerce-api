package br.com.jonatas.ecommerce.infra.common.exception;

public class AlreadyExistsException extends RuntimeException {
	private final int code;

	public AlreadyExistsException(String message) {
		super(message);
		this.code = 409;
	}

	public int getCode() {
		return code;
	}
}
