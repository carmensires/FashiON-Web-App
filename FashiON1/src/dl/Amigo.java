package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Amigos database table.
 * 
 */
@Entity
@Table(name="Amigos")
@NamedQueries({
	@NamedQuery(name="Amigo.findAll", query="SELECT a FROM Amigo a"),
	@NamedQuery(name="Amigo.findSeguidores", query="SELECT a FROM Amigo a WHERE a.usuario2.idUser=:usuarioSeguido"),
	@NamedQuery(name="Amigo.findSeguidos", query="SELECT a FROM Amigo a WHERE a.usuario1.idUser=:usuarioSigue"),
	@NamedQuery(name="Amigo.findSeguidor", query="SELECT a FROM Amigo a WHERE a.usuario1.idUser=:usuarioSigue AND a.usuario2.idUser=:usuarioSeguido")
})
public class Amigo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idAmigos;

	//uni-directional many-to-one association to Usuario
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="usuarioSigue")
	private Usuario usuario1;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioSeguido")
	private Usuario usuario2;

	public Amigo() {
	}

	public int getIdAmigos() {
		return this.idAmigos;
	}

	public void setIdAmigos(int idAmigos) {
		this.idAmigos = idAmigos;
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