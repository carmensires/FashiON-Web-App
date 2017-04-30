package pl;

import java.util.ArrayList;
import java.util.Collections;
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
		Collections.reverse(listaPublicaciones);
		return listaPublicaciones;
	}
	
	public List<Publicacion> getListaPublicacionesUsuario(Usuario user)
	{
		
		List<Publicacion> listaPublicaciones = ejb.getPublicacionesUsuario(user.getIdUser());
		if(user.getTipoPerfil()==1){
			if(ejb.comprobarSeguido(user.getIdUser())){
				listaPublicaciones.clear();
			}
				
		}

		Collections.reverse(listaPublicaciones);
		return listaPublicaciones;
	}
	
	
	public List<Usuario> getListaUsuarios()
	{
		List<Usuario> listaUsuarios=ejb.getListaUsuarios();
		return listaUsuarios;
	}
	public List<NotificacionCompleta> getListaNotificaciones()
	{
		List<NotificacionCompleta> listaNotificaciones=new ArrayList<NotificacionCompleta>();
		List<Notificacion> lista=ejb.getListaNotificaciones();
		for(int i=0;i<lista.size();i++)
		{
			Notificacion n=lista.get(i);
			NotificacionCompleta nC=new NotificacionCompleta();
			nC.setNotificacion(n);
			nC.setFraseAnterior();
			nC.setFrasePosterior();
			nC.setPub();
			listaNotificaciones.add(nC);
		}
		Collections.reverse(listaNotificaciones);
		return listaNotificaciones;
	}
	
	public List<Comentario> getListaComentariosPublicacion(int idPublicacion)
	{
		List<Comentario> listaComentarios = ejb.getListaComentariosPublicacion(idPublicacion);
		Collections.reverse(listaComentarios);
		return listaComentarios;
	}
	
	public List<Publicacion> getListaPublicacionesFiltros()
	{
		List<Publicacion> lista=ejb.getListaPublicaciones();
		Busqueda busqueda=ejb.getBusqueda();
		ejb.setBusqueda(new Busqueda());
		lista=filtrar(lista,busqueda);
		Collections.reverse(lista);
		return lista;
	}
	
	public List<Publicacion> filtrar(List<Publicacion> lista, Busqueda busqueda)
	{
		if(busqueda.getMarca()!=null)
			lista=eliminar(lista,busqueda.getMarca(),1);
		if(busqueda.getColor()!=null)
			lista=eliminar(lista,busqueda.getColor(),2);
		if(busqueda.getGenero()!=null)
			lista=eliminar(lista,busqueda.getGenero(),3);
		if(busqueda.getTipo()!=null)
			lista=eliminar(lista,busqueda.getTipo(),4);
		if(busqueda.getMaterial()!=null)
			lista=eliminar(lista,busqueda.getMaterial(),5);
		if(busqueda.getEstilo()!=null)
			lista=eliminar(lista,busqueda.getEstilo(),6);
		if(busqueda.getDes()!="")
			lista=eliminar(lista,busqueda.getDes(),7);
		return lista;
	}
	
	public List<Publicacion> eliminar(List<Publicacion> lista, String filtro, int n)
	{
		List<Publicacion> listaFiltrada=new ArrayList<Publicacion>();
		for(int i=0;i<lista.size();i++)
		{
			switch(n)
			{
				case 1: if(lista.get(i).getEtMarca()!=null)
							if(lista.get(i).getEtMarca().equalsIgnoreCase(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				case 2: if(lista.get(i).getEtColor()!=null)
							if(lista.get(i).getEtColor().equalsIgnoreCase(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				case 3: if(lista.get(i).getEtGenero()!=null)
							if(lista.get(i).getEtGenero().equalsIgnoreCase(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				case 4: if(lista.get(i).getEtTipo()!=null)
							if(lista.get(i).getEtTipo().equalsIgnoreCase(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				case 5: if(lista.get(i).getEtMaterial()!=null)
							if(lista.get(i).getEtMaterial().equalsIgnoreCase(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				case 6: if(lista.get(i).getEtEstilo()!=null)
							if(lista.get(i).getEtEstilo().equalsIgnoreCase(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				case 7: if(lista.get(i).getEtMarca()!="")
							if(lista.get(i).getEtDescripcion().contains(filtro))
								listaFiltrada.add(lista.get(i));
						break;
				default: break;
			}
		}
		return listaFiltrada;
	}
	
	
	

}
