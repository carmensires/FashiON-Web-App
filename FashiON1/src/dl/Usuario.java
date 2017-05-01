package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Usuario database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
@NamedQuery(name="Usuario.find",query="SELECT u FROM Usuario u WHERE u.userName=:userName"),
@NamedQuery(name="Usuario.findEmail", query="SELECT u FROM Usuario u WHERE u.email=:email"),
@NamedQuery(name="Usuario.findNombreBusqueda", query="SELECT u FROM Usuario u WHERE u.nombreCompleto=:busqueda"),
@NamedQuery(name="Usuario.findUserNameBusqueda", query="SELECT u FROM Usuario u WHERE u.userName=:busqueda")
})

/*
 * @NamedQuery(name="Usuario.findNombreBusqueda", query="SELECT u FROM Usuario u WHERE u.nombreCompleto LIKE '%:busqueda%'"),
 * @NamedQuery(name="Usuario.findUserNameBusqueda", query="SELECT u FROM Usuario u WHERE u.userName LIKE '%:busqueda%'")
 * 
 * */

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idUser;

	private String email;

	@Lob
	private byte[] foto;

	private String nombreCompleto;

	private int nPost;

	private int nValoraciones;

	private String password;

	private byte tipoPerfil;

	private String userName;

	private float valoracionMedia;

	public Usuario() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public int getNPost() {
		return this.nPost;
	}

	public void setNPost(int nPost) {
		this.nPost = nPost;
	}

	public int getNValoraciones() {
		return this.nValoraciones;
	}

	public void setNValoraciones(int nValoraciones) {
		this.nValoraciones = nValoraciones;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getTipoPerfil() {
		return this.tipoPerfil;
	}

	public void setTipoPerfil(byte tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public float getValoracionMedia() {
		return this.valoracionMedia;
	}

	public void setValoracionMedia(float valoracionMedia) {
		this.valoracionMedia = valoracionMedia;
	}

}