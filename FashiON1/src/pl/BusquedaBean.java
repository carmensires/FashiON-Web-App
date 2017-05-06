package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dl.Busqueda;
import bl.Ejb;

@ManagedBean
@RequestScoped
public class BusquedaBean {

	@EJB
	Ejb ejb = new Ejb();

	Busqueda busqueda = new Busqueda();
	String busquedaUsuario;

	public Busqueda getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(Busqueda busqueda) {
		this.busqueda = busqueda;
	}

	public String getBusquedaUsuario() {
		return busquedaUsuario;
	}

	public void setBusquedaUsuario(String busquedaUsuario) {
		this.busquedaUsuario = busquedaUsuario;
	}

	public void setBusquedaEjb() {
		ejb.setBusqueda(this.busqueda);
	}

	public void setBusquedaUsuariosEjb() {
		ejb.setBusquedaUsuario(busquedaUsuario);
	}

}
