package dl;

public class NotificacionCompleta {
	
	private String fraseAnterior=new String();
	private Notificacion notificacion=new Notificacion();
	private String frasePosterior=new String();
	private boolean pub;
	
	public Notificacion getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}
	public String getFraseAnterior() {
		return fraseAnterior;
	}
	public void setFraseAnterior() {
		if(this.notificacion.getAccion().equalsIgnoreCase("seguir"))
			this.fraseAnterior=" ";
		else if(this.notificacion.getAccion().equalsIgnoreCase("comentario"))
			this.fraseAnterior=" ";
		else if(this.notificacion.getAccion().equalsIgnoreCase("like"))
			this.fraseAnterior=" A  ";
	}
	public String getFrasePosterior() {
		return frasePosterior;
	}
	public void setFrasePosterior() {
		if(this.notificacion.getAccion().equalsIgnoreCase("seguir"))
			this.frasePosterior="  ha comenzado a seguirte.";
		else if(this.notificacion.getAccion().equalsIgnoreCase("comentario"))
			this.frasePosterior="  ha comentado tu publicación.";
		else if(this.notificacion.getAccion().equalsIgnoreCase("like"))
			this.frasePosterior="  le ha gustado tu publicación.";
	}
	public boolean isPub() {
		return pub;
	}
	public void setPub() {
		if(this.notificacion.getAccion().equalsIgnoreCase("comentario") || this.notificacion.getAccion().equalsIgnoreCase("like"))
			pub=true;
		else
			pub=false;
	}
	
	
		
	

}
