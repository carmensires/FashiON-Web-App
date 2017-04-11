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
	
	public List<Publicacion> getListaPublicacionesUsuario(int idUsuario)
	{
		List<Publicacion> listaPublicaciones=ejb.getPublicacionesUsuario(idUsuario);
		return listaPublicaciones;
	}
	
	
	public List<Usuario> getListaUsuarios()
	{
		List<Usuario> listaUsuarios=ejb.getListaUsuarios();
		return listaUsuarios;
	}
	public List<Notificacion> getListaNotificaciones()
	{
		List<Notificacion> listaNotificaciones=ejb.getListaNotificaciones();
		return listaNotificaciones;
	}
	
	public List<Comentario> getListaComentariosPublicacion(int idPublicacion)
	{
		List<Comentario> listaComentarios = ejb.getListaComentariosPublicacion(idPublicacion);
		return listaComentarios;
	}
	

}
