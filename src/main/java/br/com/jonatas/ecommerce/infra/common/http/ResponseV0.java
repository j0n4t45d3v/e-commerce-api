package br.com.jonatas.ecommerce.infra.common.http;

import java.time.LocalDateTime;

public record ResponseV0<T>(
		int status,
		LocalDateTime timestamp,
		T data
) {

	public static <T> ResponseV0<T> of(int status, T data) {
		return new ResponseV0<>(status, LocalDateTime.now(), data);
	}

}
