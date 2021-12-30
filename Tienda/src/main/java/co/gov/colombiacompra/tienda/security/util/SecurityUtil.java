package co.gov.colombiacompra.tienda.security.util;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;

public class SecurityUtil {
	@Value("${jwt.secret}")
	private static String secretKey;

	public static String getTokenParam(String jwtToken, String parametro) {
		String valor = "";
		try {
			valor = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwtToken).getBody().get(parametro)
					.toString();
		} catch (Exception e) {
			// TODO Definir Estrategia de manejo de Excepciones
			e.printStackTrace();
		}

		return valor;
	}
}
