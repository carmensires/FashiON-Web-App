package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Valoracion database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Valoracion.findAll", query="SELECT v FROM Valoracion v"),
	@NamedQuery(name="Valoracion.findUsuarioComentario", query="SELECT v FROM Valoracion v WHERE v.comentarioBean.idComentario=:idComentario AND v.usuario.idUser=:idUser"),
	@NamedQuery(name="Valoracion.findUsuario", query="SELECT v FROM Valoracion v WHERE v.comentarioBean.usuarioComenta=:user")
})

public class Valoracion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idValoracion;

	private int puntuacion;

	//uni-directional many-to-one association to Comentario
	@ManyToOne
	@JoinColumn(name="comentario")
	private Comentario comentarioBean;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioValora")
	private Usuario usuario;

	public Valoracion() {
	}

	public int getIdValoracion() {
		return this.idValoracion;
	}

	public void setIdValoracion(int idValoracion) {
		this.idValoracion = idValoracion;
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Comentario getComentarioBean() {
		return this.comentarioBean;
	}

	public void setComentarioBean(Comentario comentarioBean) {
		this.comentarioBean = comentarioBean;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}