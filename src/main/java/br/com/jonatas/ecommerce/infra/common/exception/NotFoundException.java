package br.com.jonatas.ecommerce.infra.common.exception;

public class NotFoundException extends BaseException {

	public NotFoundException(String message) {
		super(404, message);
	}

}
