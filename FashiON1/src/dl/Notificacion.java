package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Notificacion database table.
 * 
 */
@Entity
@NamedQueries({

	@NamedQuery(name="Notificacion.findAll", query="SELECT n FROM Notificacion n"),
	@NamedQuery(name="Notificacion.findUsuario", query="SELECT n FROM Notificacion n WHERE n.usuario1.idUser=:idUser"),
	@NamedQuery(name="Notificacion.findLike", query="SELECT n FROM Notificacion n WHERE n.publicacionBean.idPublicacion=:idPublicacion AND n.usuario2.idUser=:idUser AND n.accion=:accion "),
	@NamedQuery(name="Notificacion.findSeguir", query="SELECT n FROM Notificacion n WHERE n.usuario1.idUser=:idUser1 AND n.usuario2.idUser=:idUser2 AND n.accion=:accion "),
	@NamedQuery(name="Notificacion.findComentario", query="SELECT n FROM Notificacion n WHERE n.comentarioBean.idComentario=:idComentario AND n.accion=:accion ")
	})
public class Notificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idNotificacion;

	private String accion;

	//uni-directional many-to-one association to Comentario
	@ManyToOne
	@JoinColumn(name="comentario")
	private Comentario comentarioBean;

	//uni-directional many-to-one association to Publicacion
	@ManyToOne
	@JoinColumn(name="publicacion")
	private Publicacion publicacionBean;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioNotificado")
	private Usuario usuario1;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioAccion")
	private Usuario usuario2;

	public Notificacion() {
	}

	public int getIdNotificacion() {
		return this.idNotificacion;
	}

	public void setIdNotificacion(int idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Comentario getComentarioBean() {
		return this.comentarioBean;
	}

	public void setComentarioBean(Comentario comentarioBean) {
		this.comentarioBean = comentarioBean;
	}

	public Publicacion getPublicacionBean() {
		return this.publicacionBean;
	}

	public void setPublicacionBean(Publicacion publicacionBean) {
		this.publicacionBean = publicacionBean;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

}