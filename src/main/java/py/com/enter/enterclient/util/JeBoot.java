package py.com.enter.enterclient.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import py.com.enter.enterclient.util.exception.KwfCoreException;

@Slf4j
public class JeBoot {

	public static String esExcepcionKwf(Exception e) {
		String retorno = "";
		String mensaje = e.getMessage();
		String iniciaCon = "kwfException";
		if (mensaje != null && mensaje.trim().startsWith(iniciaCon)) {
			if (mensaje.trim().length() > iniciaCon.length() + 2) {
				retorno = "\n[" + mensaje.trim().substring(iniciaCon.length()).trim() + "]";

			}
		}
		return retorno;
	}


	public static void getTipoDatoCustom(Object obj, String atributo) throws NoSuchFieldException, SecurityException {

		Class<?> actualClass = obj.getClass();
		String className = obj.getClass().getName();
		// System.out.println("className= "+className);
		// System.out.println("atributo= "+atributo);

		Field field = actualClass.getDeclaredField(atributo);
		// System.out.println("field= "+field);
		// System.out.println("fieldType= "+field.getType());

	}

	public static String getTipoDato(Object obj, String atributo) { // cambiar

		String tipoDato = "";
		String[] atributoArray = atributo.split("\\.");

		try {

			Class<? extends Object> clase = obj.getClass();

			for (int i = 0; i < atributoArray.length; i++) {

				Field field = null;
				try {
					field = clase.getDeclaredField(atributoArray[i]);
				} catch (NoSuchFieldException e) {
					field = clase.getSuperclass().getDeclaredField(atributo);
				}
				tipoDato = field.getType().getCanonicalName().toString();

				if (tipoDato.compareTo("java.util.Date") == 0) {
					for (Annotation anotacion : field.getAnnotations()) {

						/*
						 * if(buscar(anotacion.toString(), "javax.persistence.Temporal", false)){
						 * String[] anotacionArray=anotacion.toString().split("=");
						 * if(anotacionArray.length==2){ String resultadoAnotacion=anotacionArray[1];
						 * resultadoAnotacion=resultadoAnotacion.replace(")", "");
						 * resultadoAnotacion=resultadoAnotacion.replace(" ", "");
						 * resultadoAnotacion.trim();
						 * 
						 * if(resultadoAnotacion.compareTo("DATE")==0){ tipoDato="date"; }
						 * if(resultadoAnotacion.compareTo("TIME")==0){ tipoDato="time"; }
						 * 
						 * if(resultadoAnotacion.compareTo("TIMESTAMP")==0){ tipoDato="datetime"; }
						 * 
						 * }
						 * 
						 * 
						 * }
						 */
					}

				} else {
					if (!tipoSimple(tipoDato)) {
						Object objTemp = Class.forName(tipoDato).getConstructor().newInstance();
						clase = objTemp.getClass();
					} else {
						if (tipoDato.compareTo("java.lang.Integer") == 0) {
							tipoDato = "integer";
						}

						if (tipoDato.compareTo("java.lang.String") == 0) {
							tipoDato = "string";
						}

						if (tipoDato.compareTo("java.math.BigDecimal") == 0) {
							tipoDato = "bigdecimal";
						}

					}
				}

			}

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);

		}

		return tipoDato;
	}

	public static boolean tipoSimple(String tipo) { // cambiar
		if (tipo.equals("java.lang.Integer") || tipo.equals("java.lang.String") || tipo.equals("java.math.BigDecimal")
				|| tipo.equals("java.util.Date") || tipo.equals("java.sql.Date") || tipo.equals("java.sql.Time")
				|| tipo.equals("java.sql.Timestamp") || tipo.equals("[B")) {
			return true;
		}

		return false;
	}

	public static String getPrimeroMayuscula(String palabra) {
		if (palabra != null) {
			String primeraLetraMayusucula = String.valueOf(palabra.charAt(0)).toUpperCase();
			String restoNombre = palabra.substring(1);
			return (primeraLetraMayusucula + restoNombre);
		}
		return null;
	}

	public static String getPrimeroMinuscula(String palabra) {
		if (palabra != null) {
			String primeraLetraMayusucula = String.valueOf(palabra.charAt(0)).toLowerCase();
			String restoNombre = palabra.substring(1);
			return (primeraLetraMayusucula + restoNombre);
		}
		return null;
	}

	public static String guionBajoToCamelCase(String nombre) {

		nombre = nombre.toLowerCase();

		String retorno = null;
		String[] nombreArray = nombre.split("_");

		retorno = nombreArray[0];

		if (nombreArray.length > 1) {
			for (int i = 1; i < nombreArray.length; i++) {
				retorno += nombreArray[i].substring(0, 1).toUpperCase() + nombreArray[i].substring(1).toLowerCase();
			}
		}

		return retorno;

	}

	public static String getMetodo(String atributo) {

		if (atributo != null) {
			String primeraLetraMayusucula = String.valueOf(atributo.charAt(0)).toUpperCase();
			String restoNombre = atributo.substring(1);

			return ("get" + primeraLetraMayusucula + restoNombre);

		}

		return null;
	}

	public static String setMetodo(String atributo) {

		if (atributo != null) {
			String primeraLetraMayusucula = String.valueOf(atributo.charAt(0)).toUpperCase();
			String restoNombre = atributo.substring(1);

			return ("set" + primeraLetraMayusucula + restoNombre);

		}

		return null;
	}

	public static String getHttpRequestInfo(HttpServletRequest request) {

		JSONObject requestJsonObject = new JSONObject();
		try {
			requestJsonObject.put("ipAddress", request.getRemoteAddr());
			requestJsonObject.put("xForwardedFor", request.getHeader("x-forwarded-for"));
			requestJsonObject.put("serverName", request.getServerName());
			requestJsonObject.put("requestURI", request.getRequestURI());
			requestJsonObject.put("referer", request.getHeader("referer"));
			requestJsonObject.put("origin", request.getHeader("origin"));
			requestJsonObject.put("userAgent", request.getHeader("User-Agent"));
		} catch (Exception e) {
			log.error("Error al recuperar datos de HttpServletRequest", e);
		}

		return requestJsonObject.toString();
	}

	// dd/MM/yyyy HH:mm:ss:SSS
	public static Date getDateFromString(String fechaString, String format) {
		try {
			if (fechaString == null)
				return null;
			if (fechaString.trim().equals(""))
				return null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			sdf.setLenient(false);
			return sdf.parse(fechaString);
		} catch (Exception e) {
			e.printStackTrace();
			throw new KwfCoreException("Error en conversion de fecha formato: [" + format + "] valor: " + fechaString);
		}
	}

	public static String getStringFromDate(Date fechaDate, String format) {
		try {
			if (fechaDate == null)
				return null;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);

			return sdf.format(fechaDate);

		} catch (Exception e) {
			e.printStackTrace();
			throw new KwfCoreException("Error en conversion de fecha formato: [" + format + "] valor: " + fechaDate);
		}
	}

	
	public static Date getFecha(String fechaString) {
		return getDateFromString(fechaString, "dd/MM/yyyy");
	}

	public static Date getFechaHHMM(String fechaString) {
		return getDateFromString(fechaString, "dd/MM/yyyy HH:mm");
	}

	public static Date getFechaHHMMSS(String fechaString) {
		return getDateFromString(fechaString, "dd/MM/yyyy HH:mm:ss");
	}

	public static String getFechaString(Date fechaDate) {
		return getStringFromDate(fechaDate, "dd/MM/yyyy");
	}

	public static String getFechaStringHHMM(Date fechaDate) {
		return getStringFromDate(fechaDate, "dd/MM/yyyy HH:mm");
	}

	public static String getFechaStringHHMMSS(Date fechaDate) {
		return getStringFromDate(fechaDate, "dd/MM/yyyy HH:mm:ss");
	}

	public static String getHoraStringHHMMSS(Date fechaDate) {
		return getStringFromDate(fechaDate, "HH:mm:ss");
	}

	public static Date getHoraHHMMSS(String horaString) {
		return getDateFromString(horaString, "HH:mm:ss");
	}
	
	public static Date getHoraHHMM(String horaString) {
		return getDateFromString(horaString, "HH:mm");
	}

	public static <E> E nvl(E expr1, E expr2) {
        return (null != expr1) ? expr1 : expr2;
    }

}
