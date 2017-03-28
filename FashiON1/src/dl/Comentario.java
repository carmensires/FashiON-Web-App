package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Comentario database table.
 * 
 */
@Entity
@NamedQuery(name="Comentario.findAll", query="SELECT c FROM Comentario c")
public class Comentario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idComentario;

	private String textoComentario;

	//uni-directional many-to-one association to Publicacion
	@ManyToOne
	@JoinColumn(name="publicacion")
	private Publicacion publicacionBean;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioComenta")
	private Usuario usuario;

	public Comentario() {
	}

	public int getIdComentario() {
		return this.idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public String getTextoComentario() {
		return this.textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
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