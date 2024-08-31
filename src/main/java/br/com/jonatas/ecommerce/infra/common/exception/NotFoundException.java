package br.com.jonatas.ecommerce.infra.common.exception;

public class NotFoundException extends RuntimeException {
	private final int code;

	public NotFoundException(String message) {
		super(message);
		this.code = 404;
	}

	public int getCode() {
		return code;
	}
}
