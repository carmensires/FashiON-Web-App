package pl;

import java.util.ArrayList;
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
	
	public List<Publicacion> getListaPublicacionesFiltros(String des, String marca, String color, String genero, String tipo, String material, String estilo)
	{
		List<Publicacion> lista=ejb.getListaPublicaciones();
		lista=filtrar(lista,des,marca,color,genero,tipo,material,estilo);
		return lista;
	}
	
	public List<Publicacion> filtrar(List<Publicacion> lista, String des, String marca, String color, String genero, String tipo, String material, String estilo)
	{
		if(marca!="")
			lista=eliminar(lista,marca,1);
		if(color!="")
			lista=eliminar(lista,color,2);
		if(genero!="")
			lista=eliminar(lista,genero,3);
		if(tipo!="")
			lista=eliminar(lista,tipo,4);
		if(material!="")
			lista=eliminar(lista,material,5);
		if(estilo!="")
			lista=eliminar(lista,estilo,6);
		if(des!="")
			lista=eliminar(lista,des,7);
		return lista;
	}
	
	public List<Publicacion> eliminar(List<Publicacion> lista, String filtro, int n)
	{
		List<Publicacion> listaFiltrada=new ArrayList<Publicacion>();
		for(int i=0;i<lista.size();i++)
		{
			switch(n)
			{
				case 1: if(lista.get(i).getEtMarca()==filtro)
							listaFiltrada.add(lista.get(i));
						break;
				case 2: if(lista.get(i).getEtColor()==filtro)
							listaFiltrada.add(lista.get(i));
						break;
				case 3: if(lista.get(i).getEtGenero()==filtro)
							listaFiltrada.add(lista.get(i));
						break;
				case 4: if(lista.get(i).getEtTipo()==filtro)
							listaFiltrada.add(lista.get(i));
						break;
				case 5: if(lista.get(i).getEtMaterial()==filtro)
							listaFiltrada.add(lista.get(i));
						break;
				case 6: if(lista.get(i).getEtEstilo()==filtro)
							listaFiltrada.add(lista.get(i));
						break;
				case 7: if(lista.get(i).getEtDescripcion().contains(filtro))
							listaFiltrada.add(lista.get(i));
						break;
				default: break;
			}
		}
		return listaFiltrada;
	}
	
	
	

}
