package py.com.enter.enterclient.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import py.com.enter.enterclient.util.exception.KwfCoreException;
import py.com.enter.enterclient.util.exception.ValidacionException;

//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;

public class JeResponse {

	private Map<String, Object> retornoMap;
	private Map<String, Object> resultadoMap;
	private List<String> errorValList;
	private String mensajeExito;
	private String mensajeErrorFatal;
	private String errorForLog;
	private boolean warning;

	public JeResponse(String mensajeExito, String mensajeErrorFatal) {

		this.retornoMap = new HashMap<String, Object>();
		this.resultadoMap = new HashMap<String, Object>();
		this.errorValList = new LinkedList<String>();
		this.mensajeExito = mensajeExito;
		this.mensajeErrorFatal = mensajeErrorFatal;
		this.warning = false;
	}

	public JeResponse(Map<String, Object> retornoMap, Map<String, Object> resultadoMap, List<String> errorValList) {
		super();
		this.retornoMap = retornoMap;
		this.resultadoMap = resultadoMap;
		this.errorValList = errorValList;
	}

	public Map<String, Object> getRetornoMap() {
		return retornoMap;
	}

	public void setRetornoMap(Map<String, Object> retornoMap) {
		this.retornoMap = retornoMap;
	}

	public Map<String, Object> getResultadoMap() {
		return resultadoMap;
	}

	public void setResultadoMap(Map<String, Object> resultadoMap) {
		this.resultadoMap = resultadoMap;
	}

	public void putResultado(String key, Object value) {
		this.resultadoMap.put(key, value);
	}
	
	//public void putResultadoListar(PaginacionAux paginacionAux) {
	//	this.retornoMap.put("result", paginacionAux);
	//}

	public List<String> getErrorValList() {
		return errorValList;
	}

	public void setErrorValList(List<String> errorValList) {
		this.errorValList = errorValList;
	}

	public String getMensajeExito() {
		return mensajeExito;
	}

	public void setMensajeExito(String mensajeExito) {
		this.mensajeExito = mensajeExito;
	}

	public String getErrorForLog() {
		return this.errorForLog;
	}

	public void setErrorForLog(String errorForLog) {
		this.errorForLog = errorForLog;
	}

	public boolean isWarning() {
		return this.warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}


	public void addErrorValidacion(String errorValidacion) {
		this.errorValList.add(errorValidacion);
	}

	public boolean sinErrorValidacion() {
		return this.errorValList.size() == 0;
	}

	public void prepararRetornoMap() {

		if (this.errorValList.size() == 0) {
			this.retornoMap.put("ok", true);
			this.retornoMap.put("msg", new String[]{ mensajeExito});
			if(!this.retornoMap.containsKey("result"))
				this.retornoMap.put("result", resultadoMap);
			
		} else {
			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			
		}

	}

	public void putResult(String mensaje) {
		this.retornoMap.put("result", mensaje);
	}

	public void prepararRetornoErrorMap(Exception e) {

		if (e instanceof ValidacionException) {

			this.errorValList.add(e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "" + this.errorValList;

			if(((ValidacionException) e).isWarning()) this.warning=true;
		
		} else if (e instanceof KwfCoreException) {

			this.errorValList.add("[kwf]"+e.getMessage());

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", errorValList);
			this.errorForLog = "" + this.errorValList;

			if(((KwfCoreException) e).isWarning()) this.warning=true;
 
		}else {

			String mensajeError = this.mensajeErrorFatal;
			long codSeg = System.nanoTime();
			//mensajeError += JeBoot.esExcepcionKwf(e) + " CodSeg: " + codSeg;
			this.retornoMap.put("codSeg", codSeg);

			// log.fatal("<user>"+(userSession==null?request.getRemoteAddr():userSession.getEmpresaCuentaUsuario())+"</user>"+
			// mensajeError,e);

			this.retornoMap.put("ok", false);
			this.retornoMap.put("msg", new String[]{mensajeError});
			this.errorForLog = mensajeError;

		}

	}

}
