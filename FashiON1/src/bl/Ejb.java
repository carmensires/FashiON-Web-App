package bl;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.*;
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
	
	

}
