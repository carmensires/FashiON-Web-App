package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Comentario database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Comentario.findAll", query="SELECT c FROM Comentario c"),
	@NamedQuery(name="Comentario.findPublicaciones",query="SELECT c FROM Comentario c WHERE c.publicacion=:idPublicacion")
})
public class Comentario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idComentario;

	private int publicacion;

	private String textoComentario;

	private int usuarioComenta;

	public Comentario() {
	}

	public int getIdComentario() {
		return this.idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public int getPublicacion() {
		return this.publicacion;
	}

	public void setPublicacion(int publicacion) {
		this.publicacion = publicacion;
	}

	public String getTextoComentario() {
		return this.textoComentario;
	}

	public void setTextoComentario(String textoComentario) {
		this.textoComentario = textoComentario;
	}

	public int getUsuarioComenta() {
		return this.usuarioComenta;
	}

	public void setUsuarioComenta(int usuarioComenta) {
		this.usuarioComenta = usuarioComenta;
	}

}