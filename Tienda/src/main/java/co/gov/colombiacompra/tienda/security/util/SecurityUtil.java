package co.gov.colombiacompra.tienda.security.util;

import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.Jwts;

/**
 * Clase de utilitarios de seguridad
 * 
 * @author David.Murcia
 *
 */
public class SecurityUtil {
	private static String secretKey ="tiendaJWTSecret";

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
	
	public static List getAuthorities(String authorization) {
		String jwtToken = authorization.replace("Bearer ", "");
		List valor = new ArrayList<String>();
		try {
			valor = (ArrayList) Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwtToken).getBody().get("authorities");
		} catch (Exception e) {
			// TODO Definir Estrategia de manejo de Excepciones
			e.printStackTrace();
		}

		return valor;
	}
	
		
	
	/**
	 * Método que dado el token, obtiene el id del usuario autenticado
	 * 
	 * @param Authorization header de autorización del request que invoca
	 *        éste método
	 *        
	 * @return Identificador del usuario autenticado.
	 * 
	 * @author David.Murcia
	 */
	public static Long ObtenerUid(String authorization) {
		String jwtToken = authorization.replace("Bearer ", "");

		Long uid = Long.parseLong(getTokenParam(jwtToken, "uid"));

		return uid;
	}
	
}
