package py.com.enter.enterclient.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class KwfCoreException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private boolean warning;
	
	public KwfCoreException(String mensaje) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = false;
	}

	public KwfCoreException(String mensaje, boolean warning) {
		super(mensaje);
		this.mensaje = mensaje;
		this.warning = warning;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public boolean isWarning(){
		return warning;
	}
}
