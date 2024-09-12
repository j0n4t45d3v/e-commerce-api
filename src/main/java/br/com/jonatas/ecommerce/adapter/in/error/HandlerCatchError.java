package br.com.jonatas.ecommerce.adapter.in.error;

import br.com.jonatas.ecommerce.infra.common.exception.BaseException;
import br.com.jonatas.ecommerce.infra.common.http.ResponseErrorV0;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerCatchError {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ResponseErrorV0> handleBaseException(BaseException e) {
		var response = ResponseErrorV0.of(e.getStatus(), e.getMessage());
		return ResponseEntity.status(e.getStatus()).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseErrorV0> handleException(Exception e) {
		var response = ResponseErrorV0.of(500, e.getMessage());
		return ResponseEntity.internalServerError().body(response);
	}
}
