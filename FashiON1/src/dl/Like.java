package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Likes database table.
 * 
 */
@Entity
@Table(name="Likes")
@NamedQueries({
	@NamedQuery(name="Like.findAll", query="SELECT l FROM Like l"),
	@NamedQuery(name="Like.findPublicacion", query="SELECT l FROM Like l WHERE l.publicacionBean.idPublicacion=:idPublicacion"),
	@NamedQuery(name="Like.findUsuario", query="SELECT l FROM Like l WHERE l.publicacionBean.idPublicacion=:idPublicacion AND l.usuario.idUser=:idUser") 
})

public class Like implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idLikes;

	//uni-directional many-to-one association to Publicacion
	@ManyToOne
	@JoinColumn(name="publicacion")
	private Publicacion publicacionBean;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioLike")
	private Usuario usuario;

	public Like() {
	}

	public int getIdLikes() {
		return this.idLikes;
	}

	public void setIdLikes(int idLikes) {
		this.idLikes = idLikes;
	}

	public Publicacion getPublicacionBean() {
		return this.publicacionBean;
	}

	public void setPublicacionBean(Publicacion publicacionBean) {
		this.publicacionBean = publicacionBean;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}