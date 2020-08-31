package co.edu.uniquindio.gri.utilities;

import java.io.Serializable;

public class Respuesta implements Serializable{

	
	private Integer codigoRespuesta;
	private String mensajeRespuesta;
	
	public Respuesta() {
		super();
	}
	
	public Respuesta( Integer codigoRespuesta, String mensajeRespuesta  ) {
		super();
		this.codigoRespuesta=codigoRespuesta;
		this.mensajeRespuesta=mensajeRespuesta;
		
	}
	
	public Integer getCodigoRespuesta() {
		return codigoRespuesta;
	}
	
	public void setCodigoRespuesta(Integer codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	
	

}
