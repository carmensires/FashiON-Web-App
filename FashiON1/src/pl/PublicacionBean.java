package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dl.Publicacion;
import bl.Ejb;


@ManagedBean
@RequestScoped
public class PublicacionBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	private Publicacion publicacion=new Publicacion();
	boolean added;
	

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
	
	public void addPublicacion()
	{
		added=false;
		added=ejb.addPublicacion(publicacion);
	}

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	
	
	

}
