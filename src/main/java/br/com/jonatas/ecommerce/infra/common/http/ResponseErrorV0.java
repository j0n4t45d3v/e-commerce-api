package br.com.jonatas.ecommerce.infra.common.http;

import java.time.LocalDateTime;

public record ResponseErrorV0(
				int status,
				LocalDateTime timestamp,
				String error
) {
	public static ResponseErrorV0 of(int status, String error) {
		return new ResponseErrorV0(status, LocalDateTime.now(), error);
	}
}
