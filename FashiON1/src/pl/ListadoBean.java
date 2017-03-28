package pl;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import dl.*;
import bl.Ejb;

@ManagedBean
@ApplicationScoped
public class ListadoBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	public List<Publicacion> getListaPublicaciones()
	{
		List<Publicacion> listaPublicaciones=ejb.getListaPublicaciones();
		return listaPublicaciones;
	}
	
	public List<Usuario> getListaUsuarios()
	{
		List<Usuario> listaUsuarios=ejb.getListaUsuarios();
		return listaUsuarios;
	}

}
