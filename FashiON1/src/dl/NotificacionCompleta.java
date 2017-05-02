package dl;

public class NotificacionCompleta {
	
	private String fraseAnterior=new String();
	private Notificacion notificacion=new Notificacion();
	private String frasePosterior=new String();
	private boolean pub;
	private boolean com;
	private boolean pet;
	
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
		else if(this.notificacion.getAccion().equalsIgnoreCase("valoracion"))
			this.frasePosterior=" ";
		else if(this.notificacion.getAccion().equalsIgnoreCase("peticion"))
			this.frasePosterior=" ";
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
		else if(this.notificacion.getAccion().equalsIgnoreCase("valoracion"))
			this.frasePosterior="  ha valorado tu comentario.";
		else if(this.notificacion.getAccion().equalsIgnoreCase("peticion"))
			this.frasePosterior="  ha solicitado seguirte.";
	}
	public boolean isPub() {
		setPub();
		return pub;
	}
	public void setPub() {
		if(this.notificacion.getAccion().equalsIgnoreCase("comentario") || this.notificacion.getAccion().equalsIgnoreCase("like"))
			pub=true;
		else
			pub=false;
	}
	public boolean isCom() {
		setCom();
		return com;
	}
	
	public boolean isPet(){
		setPet();
		return pet;
	}
	
	public void setPet() {
		if(this.notificacion.getAccion().equalsIgnoreCase("peticion"))
			this.pet=true;
		else
			this.pet=false;
	}
	public void setCom() {
		if(this.notificacion.getAccion().equalsIgnoreCase("valoracion"))
			this.com=true;
		else
			this.com=false;
	}
	
	
	
	
	
	
		
	

}
