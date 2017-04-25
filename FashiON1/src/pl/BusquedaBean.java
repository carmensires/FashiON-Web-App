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
	Ejb ejb=new Ejb();
	
	Busqueda busqueda=new Busqueda();

	public Busqueda getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(Busqueda busqueda) {
		this.busqueda = busqueda;
	}
	
	public void setBusquedaEjb()
	{
		ejb.setPrueba(true);
		ejb.setBusqueda(this.busqueda);
	}
	

}
