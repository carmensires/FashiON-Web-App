package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dl.*;
import bl.Ejb;

@ManagedBean
@RequestScoped
public class AmigoBean {

	@EJB
	Ejb ejb = new Ejb();

	private Amigo amigo = new Amigo();

	public Amigo getAmigo() {
		return amigo;
	}

	public void setAmigo(Amigo amigo) {
		this.amigo = amigo;
	}

	public void addAmigo(int idNotificacion) {
		ejb.addAmigoNotificacion(idNotificacion);
	}

}
