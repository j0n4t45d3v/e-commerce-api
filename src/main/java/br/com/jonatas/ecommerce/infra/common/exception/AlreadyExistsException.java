package br.com.jonatas.ecommerce.infra.common.exception;

public class AlreadyExistsException extends BaseException {

	public AlreadyExistsException(String message) {
		super(409, message);
	}

}
