package br.com.jonatas.ecommerce.infra.common.exception;

public class BadRequestException extends RuntimeException {
	private final int code;

	public BadRequestException(String message) {
		super(message);
		this.code = 400;
	}

	public int getCode() {
		return code;
	}
}
