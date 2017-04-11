package pl;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import bl.Ejb;


@ManagedBean
@RequestScoped
public class BusquedaBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	

}
