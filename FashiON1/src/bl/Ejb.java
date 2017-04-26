package bl;

import java.util.ArrayList;
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

	private Usuario usuario = new Usuario();
	
	private Busqueda busqueda=new Busqueda();
	

	// USUARIO HACE LOG IN
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int comprobarUsuario(String userName, String password) {
		int ok = 2;
		try {
			usuario = (Usuario) em.createNamedQuery("Usuario.find")
					.setParameter("userName", userName).getSingleResult();
		} catch (NoResultException e) {
			ok = 1;
		}
		if (usuario == null)
			ok = 1;
		if (password.equals(usuario.getPassword()))
			ok = 0;
		else
			usuario = null;
		return ok;
	}

	// USUARIO SE REGISTRA

	public int addUsuario(Usuario usuario) {
		int libre = 2;
		try {
			em.createNamedQuery("Usuario.findEmail")
					.setParameter("email", usuario.getEmail())
					.getSingleResult();
		} catch (NoResultException e) {
			try {
				em.createNamedQuery("Usuario.find")
						.setParameter("userName", usuario.getUserName())
						.getSingleResult();
			} catch (NoResultException e2) {
				libre = 0;
			}
			if (libre != 0) {
				libre = 1;
			}
		}
		if (libre == 0) {
			usuario.setNPost(0);
			usuario.setNValoraciones(0);
			usuario.setValoracionMedia(0);
			em.persist(usuario);
		}
		return libre;
	}

	// SUBIR UNA PUBLICACION

	public boolean addPublicacion(Publicacion publicacion) {
		boolean added = false;
		publicacion.setUsuario(usuario);
		if (publicacion.getDescripcion() != null
				|| publicacion.getMultimedia() != null) {
			em.persist(publicacion);
			added = true;
		}
		usuario = em.find(Usuario.class, usuario.getIdUser());
		int n = usuario.getNPost();
		n++;
		usuario.setNPost(n);
		em.persist(usuario);
		return added;

	}
	

	// OBTENER LISTA DE PUBLICACIONES

	@SuppressWarnings("unchecked")
	public List<Publicacion> getListaPublicaciones() {
		List<Publicacion> listaPublicaciones = (List<Publicacion>) em
				.createNamedQuery("Publicacion.findAll").getResultList();
		return listaPublicaciones;
	}

	// OBTENER LISTA DE USUARIOS

	@SuppressWarnings("unchecked")
	public List<Usuario> getListaUsuarios() {
		List<Usuario> listaUsuarios = (List<Usuario>) em.createNamedQuery(
				"Usuario.findAll").getResultList();
		return listaUsuarios;
	}

	// OBTENER SEGUIDORES Y SEGUIDOS

	@SuppressWarnings("unchecked")
	public List<Usuario> getSeguidores(int idUsuario) {
		List<Amigo> listaAmigos = (List<Amigo>) em
				.createNamedQuery("Amigo.findSeguidores")
				.setParameter("usuarioSeguido", idUsuario).getResultList();
		int tam = listaAmigos.size();
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario user;
		for (int i = 0; i < tam; i++) {
			user = listaAmigos.get(i).getUsuario1();
			listaUsuarios.add(i, user);
		}
		return listaUsuarios;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getSeguidos(int idUsuario) {
		List<Amigo> listaAmigos = (List<Amigo>) em
				.createNamedQuery("Amigo.findSeguidos")
				.setParameter("usuarioSigue", idUsuario).getResultList();
		int tam = listaAmigos.size();
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario user;
		for (int i = 0; i < tam; i++) {
			user = listaAmigos.get(i).getUsuario2();
			listaUsuarios.add(i, user);
		}
		return listaUsuarios;
	}

	// OBTENER PUBLICACIONES DE UN USUARIO

	@SuppressWarnings("unchecked")
	public List<Publicacion> getPublicacionesUsuario(int idUsuario) {
		List<Publicacion> listaPublicacionesUsuario = (List<Publicacion>) em
				.createNamedQuery("Publicacion.findUsuario")
				.setParameter("idUsuario", idUsuario).getResultList();
		return listaPublicacionesUsuario;
	}

	// DAR UN LIKE

	public void addLike(int idPublicacion) {

		Publicacion publicacion = em.find(Publicacion.class, idPublicacion);
		Like like = new Like();
		like.setUsuario(usuario);
		like.setPublicacionBean(publicacion);
		em.persist(like);

	}

	// OBTENER NUMERO DE LIKES

	@SuppressWarnings("unchecked")
	public int getLikes(int idPublicacion) {
		int tam = 0;
		List<Like> listaLikes = (List<Like>) em
				.createNamedQuery("Like.findPublicacion")
				.setParameter("idPublicacion", idPublicacion).getResultList();
		if (listaLikes != null)
			tam = listaLikes.size();
		return tam;
	}

	// COMPROBAR SI UNA PERSONA YA HA DADO LIKE A UNA PUBLICACION

	@SuppressWarnings("unused")
	public boolean getLiked(int idPublicacion) {
		boolean liked = false;
		try {
			Like like = (Like) em.createNamedQuery("Like.findUsuario")
					.setParameter("idPublicacion", idPublicacion)
					.setParameter("idUser", usuario.getIdUser())
					.getSingleResult();
		} catch (NoResultException e) {
			liked = true;
		}

		return liked;
	}

	// ELIMINAR LIKE

	public void removeLike(int idPublicacion) {
		Like like = (Like) em.createNamedQuery("Like.findUsuario")
				.setParameter("idPublicacion", idPublicacion)
				.setParameter("idUser", usuario.getIdUser()).getSingleResult();
		em.remove(like);
	}

	// SEGUIR A OTRO USUARIO

	public void addAmigo(int idUser) {
		Amigo a = new Amigo();
		a.setUsuario1(em.find(Usuario.class, usuario.getIdUser()));
		a.setUsuario2(em.find(Usuario.class, idUser));
		em.persist(a);
	}

	// COMPROBAR SI SE SIGUE AL USUARIO

	@SuppressWarnings("unused")
	public boolean comprobarSeguido(int idUser) {
		boolean comprobar = false;
		try {
			Amigo a = (Amigo) em.createNamedQuery("Amigo.findSeguidor")
					.setParameter("usuarioSigue", usuario.getIdUser())
					.setParameter("usuarioSeguido", idUser).getSingleResult();
		} catch (NoResultException e) {
			comprobar = true;
		}

		return comprobar;

	}

	// DEJAR DE SEGUIR USUARIO

	public void removeAmigo(int idUser) {

		Amigo a = (Amigo) em.createNamedQuery("Amigo.findSeguidor")
				.setParameter("usuarioSigue", usuario.getIdUser())
				.setParameter("usuarioSeguido", idUser).getSingleResult();
		em.remove(a);

	}

	// EDITAR PERFIL

	public boolean editarPerfil(Usuario user) {
		usuario = em.find(Usuario.class, usuario.getIdUser());
		usuario.setNombreCompleto(user.getNombreCompleto());
		usuario.setPassword(user.getPassword());
		usuario.setTipoPerfil(user.getTipoPerfil());
		usuario.setFoto(user.getFoto());
		em.persist(usuario);
		return true;
	}

	// EDITAR PUBLICACION

	public void editarPublicacion(Publicacion publicacion) {

		Publicacion publicacion2 = em.find(Publicacion.class,
				publicacion.getIdPublicacion());

		publicacion2.setDescripcion(publicacion.getDescripcion());
		publicacion2.setEtColor(publicacion.getEtColor());
		publicacion2.setEtDescripcion(publicacion.getEtDescripcion());
		publicacion2.setEtEstilo(publicacion.getEtEstilo());
		publicacion2.setEtGenero(publicacion.getEtGenero());
		publicacion2.setEtMarca(publicacion.getEtMarca());
		publicacion2.setEtMaterial(publicacion.getEtMaterial());
		publicacion2.setEtTipo(publicacion.getEtTipo());
		publicacion2.setMultimedia(publicacion.getMultimedia());
		publicacion2.setTitulo(publicacion.getTitulo());

		em.persist(publicacion2);
	}

	// AÑADIR NOTIFICACION LIKE

	@SuppressWarnings("unchecked")
	public void addNotificacionLike(int idPublicacion) {
		Notificacion notificacion = new Notificacion();
		Publicacion publicacion = em.find(Publicacion.class, idPublicacion);
		notificacion.setPublicacionBean(publicacion);
		notificacion.setAccion("like");
		notificacion.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
		notificacion.setUsuario1(em.find(Usuario.class, publicacion
				.getUsuario().getIdUser()));
		List<Notificacion> listaNot = em.createNamedQuery(
				"Notificacion.findAll").getResultList();
		int tam = listaNot.size();
		if (tam > 0)
			notificacion.setIdNotificacion(listaNot.get(tam - 1)
					.getIdNotificacion() + 1);
		else
			notificacion.setIdNotificacion(0);

		em.persist(notificacion);

	}

	// ELIMINAR NOTIFICACION LIKE

	public void removeNotificacionLike(int idPublicacion) {
		Notificacion notificacion = (Notificacion) em
				.createNamedQuery("Notificacion.findLike")
				.setParameter("idPublicacion", idPublicacion)
				.setParameter("idUser", usuario.getIdUser())
				.setParameter("accion", "like").getSingleResult();
		em.remove(notificacion);
	}

	// AÑADIR NOTIFICACION SEGUIMIENTO

	@SuppressWarnings("unchecked")
	public void addNotificacionSeguir(int idUser) {
		Notificacion notificacion = new Notificacion();
		notificacion.setAccion("seguir");
		notificacion.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
		notificacion.setUsuario1(em.find(Usuario.class, idUser));
		List<Notificacion> listaNot = em.createNamedQuery(
				"Notificacion.findAll").getResultList();
		int tam = listaNot.size();
		if (tam > 0)
			notificacion.setIdNotificacion(listaNot.get(tam - 1)
					.getIdNotificacion() + 1);
		else
			notificacion.setIdNotificacion(0);

		em.persist(notificacion);

	}

	// ELIMINAR NOTIFICACION SEGUIR

	public void removeNotificacionSeguir(int idUser) {
		Notificacion notificacion = (Notificacion) em
				.createNamedQuery("Notificacion.findSeguir")
				.setParameter("idUser1", idUser)
				.setParameter("idUser2", usuario.getIdUser())
				.setParameter("accion", "seguir").getSingleResult();
		em.remove(notificacion);
	}

	// BUSCAR NOTIFICACIONES DEL USUARIO

	@SuppressWarnings("unchecked")
	public List<Notificacion> getListaNotificaciones() {
		return (List<Notificacion>) em
				.createNamedQuery("Notificacion.findUsuario")
				.setParameter("idUser", getUsuario().getIdUser())
				.getResultList();
	}

	// BUSCAR COMENTARIOS DE UNA PUBLICACION
	@SuppressWarnings("unchecked")
	public List<Comentario> getListaComentariosPublicacion(int idPublicacion) {
		return (List<Comentario>) em
				.createNamedQuery("Comentario.findPublicaciones")
				.setParameter("idPublicacion", idPublicacion).getResultList();
	}

	// AÑADIR COMENTARIO
	public void addComentario(Comentario comentario, int idPublicacion) {

		comentario.setPublicacion(idPublicacion);
		comentario.setUsuarioComenta(usuario.getNombreCompleto());
		em.persist(comentario);

	}

	// ELIMINAR COMENTARIO

	public void removeComentario(int idComentario) {
		em.remove(em.find(Comentario.class, idComentario));

	}

	// AÑADIR NOTIFICACION COMENTARIO
	@SuppressWarnings("unchecked")
	public void addNotificacionComentario() {

		List<Comentario> listaCom = (List<Comentario>) em.createNamedQuery(
				"Comentario.findAll").getResultList();

		int idComentario = listaCom.get(listaCom.size()).getIdComentario();

		Notificacion notificacion = new Notificacion();

		Comentario comentario = em.find(Comentario.class, idComentario);

		int idUser = em.find(Publicacion.class, comentario.getPublicacion())
				.getUsuario().getIdUser();
		notificacion.setAccion("comentario");
		notificacion.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
		notificacion.setUsuario1(em.find(Usuario.class, idUser));
		notificacion.setComentarioBean(comentario);
		List<Notificacion> listaNot = em.createNamedQuery(
				"Notificacion.findAll").getResultList();
		int tam = listaNot.size();
		if (tam > 0)
			notificacion.setIdNotificacion(listaNot.get(tam - 1)
					.getIdNotificacion() + 1);
		else
			notificacion.setIdNotificacion(0);

		em.persist(notificacion);

	}

	// ELIMINAR NOTIFICACION COMENTARIO ---SIN HACER!!

	public void removeNotificacionComentario(int idUser) {
		Notificacion notificacion = (Notificacion) em
				.createNamedQuery("Notificacion.findSeguir")
				.setParameter("idUser1", idUser)
				.setParameter("idUser2", usuario.getIdUser())
				.setParameter("accion", "seguir").getSingleResult();
		em.remove(notificacion);
	}

	/*
	 * public String getNombreCompleto(int idUser) { String n; Usuario
	 * usuario=em.find(Usuario.class, idUser); n=usuario.getNombreCompleto();
	 * return n; }
	 */

	public Publicacion getPublicacion(int idPublicacion) {
		return em.find(Publicacion.class, idPublicacion);
	}

	// PEDIR FOTO DE PERFIL
	public byte[] getProfileImage(int idUser) {
		Usuario user = em.find(Usuario.class, idUser);
		return user.getFoto();
	}

	// PEDIR FOTO PUBLICACION
	public byte[] getPublicacionImage(int idPublicacion) {
		Publicacion publicacion = em.find(Publicacion.class, idPublicacion);
		return publicacion.getMultimedia();
	}
	
	
	//ESTABLECER LOS DATOS DE LA BÚSQUEDA
	public void setBusqueda(Busqueda busqueda)
	{
		this.busqueda=busqueda;
	}
	
	public Busqueda getBusqueda()
	{
		return this.busqueda;
	}

	
	//AUMENTAR NUMERO DE VISUALIZACIONES
	public void incrementarVisualizacion(int idPublicacion)
	{
		Publicacion publicacion=em.find(Publicacion.class, idPublicacion);
		int n=publicacion.getNVisualizaciones();
		n++;
		publicacion.setNVisualizaciones(n);
		em.persist(publicacion);
	}
	
	

}
