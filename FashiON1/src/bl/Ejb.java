package bl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.*;
import javax.management.remote.NotificationResult;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import dl.*;


@LocalBean
@PermitAll
@Singleton
public class Ejb {
	
	@PersistenceContext
	EntityManager em;
	
	private Usuario usuario=new Usuario();
	
	//USUARIO HACE LOG IN
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public int comprobarUsuario(String userName, String password)
	{
		int ok=2;
		try{
			usuario=(Usuario) em.createNamedQuery("Usuario.find").setParameter("userName", userName).getSingleResult();
		}catch(NoResultException e)
		{
			ok=1;
		}
		if(usuario==null)
			ok=1;
		if(password.equals(usuario.getPassword()))
			ok=0;
		else
			usuario=null;
		return ok;
	}
	
	//USUARIO SE REGISTRA
	
	public int addUsuario(Usuario usuario)
	{
		int libre=2;
		try
		{
			em.createNamedQuery("Usuario.findEmail").setParameter("email", usuario.getEmail()).getSingleResult();
		}catch(NoResultException e)
		{
			try{
				em.createNamedQuery("Usuario.find").setParameter("userName", usuario.getUserName()).getSingleResult();
			}catch(NoResultException e2)
			{
				libre=0;
			}
			if(libre!=0)
			{
				libre=1;
			}
		}
		if(libre==0)
		{
			usuario.setNPost(0);
			usuario.setNValoraciones(0);
			usuario.setValoracionMedia(0);
			em.persist(usuario);
		}
		return libre;
	}
	
	//SUBIR UNA PUBLICACION

	public boolean addPublicacion(Publicacion publicacion)
	{
		boolean added=false;
		publicacion.setUsuario(usuario);
		if(publicacion.getDescripcion()!=null || publicacion.getMultimedia()!=null)
		{
			em.persist(publicacion);
			added=true;
		}
		usuario=em.find(Usuario.class, usuario.getIdUser());
		int n=usuario.getNPost();
		n++;
		usuario.setNPost(n);
		em.persist(usuario);
		return added;
		
	}
	
	//OBTENER LISTA DE PUBLICACIONES
	
	@SuppressWarnings("unchecked")
	public List<Publicacion> getListaPublicaciones()
	{
		List<Publicacion> listaPublicaciones=(List<Publicacion>)em.createNamedQuery("Publicacion.findAll").getResultList();
		return listaPublicaciones;
	}
	
	
	//OBTENER LISTA DE USUARIOS
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getListaUsuarios()
	{
		List<Usuario> listaUsuarios=(List<Usuario>)em.createNamedQuery("Usuario.findAll").getResultList();
		return listaUsuarios;
	}
	
	//OBTENER SEGUIDORES Y SEGUIDOS
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getSeguidores(int idUsuario)
	{
		List<Amigo> listaAmigos=(List<Amigo>)em.createNamedQuery("Amigos.findSeguidores").setParameter("usuarioSeguido", idUsuario).getResultList();
		int tam=listaAmigos.size();
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		Usuario user;
		for(int i=0;i<tam;i++)
		{
			user=listaAmigos.get(i).getUsuario1();
			listaUsuarios.add(i, user);
		}
		return listaUsuarios;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getSeguidos(int idUsuario)
	{
		List<Amigo> listaAmigos=(List<Amigo>)em.createNamedQuery("Amigos.findSeguidos").setParameter("usuarioSigue", idUsuario).getResultList();
		int tam=listaAmigos.size();
		List<Usuario> listaUsuarios=new ArrayList<Usuario>();
		Usuario user;
		for(int i=0;i<tam;i++)
		{
			user=listaAmigos.get(i).getUsuario2();
			listaUsuarios.add(i, user);
		}
		return listaUsuarios;
	}
	
	//OBTENER PUBLICACIONES DE UN USUARIO
	
	@SuppressWarnings("unchecked")
	public List<Publicacion> getPublicacionesUsuario(int idUsuario)
	{
		List<Publicacion> listaPublicacionesUsuario=(List<Publicacion>)em.createNamedQuery("Publicacion.findUsuario").setParameter("idUsuario", idUsuario).getResultList();
		return listaPublicacionesUsuario;
	}
	

}
