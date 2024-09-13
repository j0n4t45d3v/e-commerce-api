package br.com.jonatas.ecommerce.infra.common.exception;

public class BaseException extends RuntimeException {

	private final int status;

	public BaseException(String message) {
		super(message);
		this.status = 500;
	}

	public BaseException(int status, String message) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
