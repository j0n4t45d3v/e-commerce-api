package br.com.jonatas.ecommerce.gateway.in.crypto;

public interface Crypto {
	String encode(String value);
	boolean match(String value, String encodedValue);
}
