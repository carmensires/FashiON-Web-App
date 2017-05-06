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
	private Busqueda busqueda = new Busqueda();
	private String busquedaUsuario = "";

	// USUARIO HACE LOG IN
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int comprobarUsuario(String userName, String password) {
		int ok = 2; // contraseña incorrecta
		Usuario user = new Usuario();
		try {
			user = (Usuario) em.createNamedQuery("Usuario.find")
					.setParameter("userName", userName).getSingleResult();
		} catch (NoResultException e) {
			ok = 1; // no existe usuario
		}
		if (ok != 1) {
			if (password.equals(user.getPassword())) {
				ok = 0; // contraseña ok
				this.usuario = user;
			} else
				ok = 2;
		}
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

	// OBTENER LAS PUBLICACIONES DE LOS USUARIOS SEGUIDOS
	@SuppressWarnings("unchecked")
	public List<Publicacion> getListaPublicacionesSeguidos() {
		List<Publicacion> listaPublicacionesTotal = (List<Publicacion>) em
				.createNamedQuery("Publicacion.findAll").getResultList();
		List<Publicacion> listaPublicaciones = new ArrayList<Publicacion>();
		for (int i = 0; i < listaPublicacionesTotal.size(); i++) {
			Publicacion publicacion = listaPublicacionesTotal.get(i);
			if (this.getSeguido(this.usuario.getIdUser(), publicacion
					.getUsuario().getIdUser())
					|| publicacion.getUsuario().getIdUser() == this.usuario
							.getIdUser())
				listaPublicaciones.add(publicacion);
		}
		return listaPublicaciones;
	}

	@SuppressWarnings("unused")
	public boolean getSeguido(int seguidor, int seguido) {
		boolean sigue = true;
		try {
			Amigo a = (Amigo) em.createNamedQuery("Amigo.findSeguidor")
					.setParameter("usuarioSigue", seguidor)
					.setParameter("usuarioSeguido", seguido).getSingleResult();
		} catch (NoResultException e) {
			sigue = false;
		}
		return sigue;
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
		Usuario usuarioSeguido = em.find(Usuario.class, idUser);
		a.setUsuario1(em.find(Usuario.class, usuario.getIdUser()));
		a.setUsuario2(usuarioSeguido);
		if (usuarioSeguido.getTipoPerfil() == 0) {
			em.persist(a);
			addNotificacionSeguir(idUser);
		} else {
			Notificacion notificacion = new Notificacion();
			notificacion.setAccion("peticion");
			notificacion.setUsuario1(usuarioSeguido);
			notificacion
					.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
			em.persist(notificacion);
		}
	}

	public void addAmigoNotificacion(int idNotificacion) {

		Notificacion not = em.find(Notificacion.class, idNotificacion);
		Amigo a = new Amigo();
		Usuario usuarioSeguido = em.find(Usuario.class, usuario.getIdUser());
		a.setUsuario1(em.find(Usuario.class, not.getUsuario2().getIdUser()));
		a.setUsuario2(usuarioSeguido);
		em.persist(a);
		Notificacion notificacion = new Notificacion();
		notificacion.setAccion("seguir");
		notificacion.setUsuario1(usuarioSeguido);
		notificacion.setUsuario2(em.find(Usuario.class, not.getUsuario2()
				.getIdUser()));
		em.persist(notificacion);
		em.remove(not);
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
		byte[] aux = user.getFoto();
		usuario.setFoto(aux);
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

	public void addNotificacionLike(int idPublicacion) {
		Notificacion notificacion = new Notificacion();
		Publicacion publicacion = em.find(Publicacion.class, idPublicacion);
		notificacion.setPublicacionBean(publicacion);
		notificacion.setAccion("like");
		notificacion.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
		notificacion.setUsuario1(em.find(Usuario.class, publicacion
				.getUsuario().getIdUser()));
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

	public void addNotificacionSeguir(int idUser) {
		Notificacion notificacion = new Notificacion();
		notificacion.setAccion("seguir");
		notificacion.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
		notificacion.setUsuario1(em.find(Usuario.class, idUser));
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
		List<Notificacion> listaNotificaciones = (List<Notificacion>) em
				.createNamedQuery("Notificacion.findUsuario")
				.setParameter("idUser", getUsuario().getIdUser())
				.getResultList();
		for (Notificacion n : listaNotificaciones) {
			Notificacion not = em.find(Notificacion.class,
					n.getIdNotificacion());
			not.setLeido(1);
			em.persist(not);
		}
		return listaNotificaciones;
	}

	@SuppressWarnings("unchecked")
	public int getNotificacionesNuevas() {
		int notificacionesNuevas = 0;
		List<Notificacion> listaNotificaciones = (List<Notificacion>) em
				.createNamedQuery("Notificacion.findUsuario")
				.setParameter("idUser", getUsuario().getIdUser())
				.getResultList();
		for (Notificacion n : listaNotificaciones) {
			if (n.getLeido() == 0)
				notificacionesNuevas++;
		}
		return notificacionesNuevas;
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
		// comentario.setUsuarioComenta(usuario.getNombreCompleto());
		comentario.setUsuarioComenta(usuario.getUserName());
		em.persist(comentario);

	}

	// ELIMINAR COMENTARIO
	public void removeComentario(int idComentario) {
		Notificacion notificacion = (Notificacion) em
				.createNamedQuery("Notificacion.findComentario")
				.setParameter("idComentario", idComentario)
				.setParameter("accion", "comentario").getSingleResult();
		em.remove(notificacion);
		em.remove(em.find(Comentario.class, idComentario));
	}

	// AÑADIR NOTIFICACION COMENTARIO
	@SuppressWarnings("unchecked")
	public void addNotificacionComentario() {

		List<Comentario> listaCom = (List<Comentario>) em.createNamedQuery(
				"Comentario.findAll").getResultList();

		int idComentario = listaCom.get(listaCom.size() - 1).getIdComentario();

		Notificacion notificacion = new Notificacion();

		Comentario comentario = em.find(Comentario.class, idComentario);

		int idUser = em.find(Publicacion.class, comentario.getPublicacion())
				.getUsuario().getIdUser();
		notificacion.setAccion("comentario");
		notificacion.setUsuario2(em.find(Usuario.class, usuario.getIdUser()));
		notificacion.setUsuario1(em.find(Usuario.class, idUser));
		notificacion.setComentarioBean(comentario);
		em.persist(notificacion);

	}

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

	// ESTABLECER LOS DATOS DE LA BÚSQUEDA
	public void setBusqueda(Busqueda busqueda) {
		this.busqueda = busqueda;
	}

	public Busqueda getBusqueda() {
		return this.busqueda;
	}

	// AUMENTAR NUMERO DE VISUALIZACIONES
	public void incrementarVisualizacion(int idPublicacion) {
		Publicacion publicacion = em.find(Publicacion.class, idPublicacion);
		int n = publicacion.getNVisualizaciones();
		n++;
		publicacion.setNVisualizaciones(n);
		em.persist(publicacion);
	}

	// INICIALIZAR PUBLICACION
	public Publicacion inicializarPublicacion() {
		return new Publicacion();
	}

	// OBTENER USUARIO POR EL NOMBRE DE USUARIO
	public Usuario getUserByName(String username) {
		Usuario user = (Usuario) em.createNamedQuery("Usuario.find")
				.setParameter("userName", username).getSingleResult();
		return user;
	}

	// VALORAR
	public void valorar(Valoracion valoracion) {
		Valoracion valoracionAntigua = new Valoracion();
		valoracion.setUsuario(this.usuario);
		if (this.getValoracion(valoracion.getComentarioBean()) != 0) {
			valoracionAntigua = (Valoracion) em
					.createNamedQuery("Valoracion.findUsuarioComentario")
					.setParameter("idUser", valoracion.getUsuario().getIdUser())
					.setParameter("idComentario",
							valoracion.getComentarioBean().getIdComentario())
					.getSingleResult();
			valoracionAntigua.setPuntuacion(valoracion.getPuntuacion());
		} else {
			valoracionAntigua.setComentarioBean(valoracion.getComentarioBean());
			valoracionAntigua.setPuntuacion(valoracion.getPuntuacion());
			valoracionAntigua.setUsuario(this.usuario);
		}
		em.persist(valoracionAntigua);
	}

	// COMPROBAR SI UN USUARIO HA VALORADO
	public int getValoracion(Comentario comentario) {
		int puntuacion;
		Valoracion valoracion;
		try {
			valoracion = (Valoracion) em
					.createNamedQuery("Valoracion.findUsuarioComentario")
					.setParameter("idUser", this.usuario.getIdUser())
					.setParameter("idComentario", comentario.getIdComentario())
					.getSingleResult();
			puntuacion = valoracion.getPuntuacion();
		} catch (NoResultException e) {
			puntuacion = 0;
		}
		return puntuacion;
	}

	// AÑADIR NOTIFICACION VALORACION
	public void addNotificacionValoracion(Valoracion valoracion) {
		Notificacion notificacion = new Notificacion();
		notificacion.setAccion("valoracion");
		notificacion.setComentarioBean(valoracion.getComentarioBean());
		Publicacion publicacion = em.find(Publicacion.class, valoracion
				.getComentarioBean().getPublicacion());
		notificacion.setPublicacionBean(publicacion);
		Usuario user1 = (Usuario) em
				.createNamedQuery("Usuario.find")
				.setParameter("userName",
						valoracion.getComentarioBean().getUsuarioComenta())
				.getSingleResult();
		notificacion.setUsuario1(user1);
		notificacion.setUsuario2(this.usuario);
		em.persist(notificacion);
	}

	// OBTENER PUNTUACION
	public int getPuntuacion(int idComentario, int idUser) {
		Valoracion valoracion = (Valoracion) em
				.createNamedQuery("Valoracion.findUsuarioComentario")
				.setParameter("idUser", idUser)
				.setParameter("idComentario", idComentario).getSingleResult();
		return valoracion.getPuntuacion();
	}

	// OBTENER VALORACIONES DE UN USUARIO
	@SuppressWarnings("unchecked")
	public List<Valoracion> getValoracionesUsuario(Usuario user) {
		List<Valoracion> lista = em.createNamedQuery("Valoracion.findUsuario")
				.setParameter("user", user.getUserName()).getResultList();
		return lista;
	}

	public int getNValoracionesUsuario(Usuario user) {
		return this.getValoracionesUsuario(user).size();
	}

	public String getBusquedaUsuario() {
		return busquedaUsuario;
	}

	public void setBusquedaUsuario(String busquedaUsuario) {
		this.busquedaUsuario = busquedaUsuario;
	}

}
