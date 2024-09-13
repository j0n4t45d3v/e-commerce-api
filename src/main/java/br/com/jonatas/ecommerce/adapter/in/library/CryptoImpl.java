package br.com.jonatas.ecommerce.adapter.in.library;

import br.com.jonatas.ecommerce.gateway.in.crypto.Crypto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CryptoImpl implements Crypto {

	private final PasswordEncoder encoder;

	@Override
	public String encode(String value) {
		return encoder.encode(value);
	}

	@Override
	public boolean match(String value, String encodedValue) {
		return this.encoder.matches(value, encodedValue);
	}
}

