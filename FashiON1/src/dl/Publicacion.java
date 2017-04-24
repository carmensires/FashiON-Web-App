package dl;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Publicacion database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Publicacion.findAll", query="SELECT p FROM Publicacion p"),
	@NamedQuery(name="Publicacion.findUsuario", query="SELECT p FROM Publicacion p WHERE p.usuario.idUser=:idUsuario"),
	@NamedQuery(name="Publicacion.findFiltro", query="SELECT p FROM Publicacion p WHERE p.etMarca=:etMarca AND p.etColor=:etColor AND p.etGenero=:etGenero AND p.etTipo=:etTipo AND p.etMaterial=:etMaterial AND p.etEstilo=:etEstilo AND p.etDescripcion=:etDescripcion"),
})
public class Publicacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idPublicacion;

	private String descripcion;

	private String etColor;

	private String etDescripcion;

	private String etEstilo;

	private String etGenero;

	private String etMarca;

	private String etMaterial;

	private String etTipo;

	@Lob
	private byte[] multimedia;

	private int nVisualizaciones;

	private String titulo;

	//uni-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioPublica")
	private Usuario usuario;

	public Publicacion() {
	}

	public int getIdPublicacion() {
		return this.idPublicacion;
	}

	public void setIdPublicacion(int idPublicacion) {
		this.idPublicacion = idPublicacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEtColor() {
		return this.etColor;
	}

	public void setEtColor(String etColor) {
		this.etColor = etColor;
	}

	public String getEtDescripcion() {
		return this.etDescripcion;
	}

	public void setEtDescripcion(String etDescripcion) {
		this.etDescripcion = etDescripcion;
	}

	public String getEtEstilo() {
		return this.etEstilo;
	}

	public void setEtEstilo(String etEstilo) {
		this.etEstilo = etEstilo;
	}

	public String getEtGenero() {
		return this.etGenero;
	}

	public void setEtGenero(String etGenero) {
		this.etGenero = etGenero;
	}

	public String getEtMarca() {
		return this.etMarca;
	}

	public void setEtMarca(String etMarca) {
		this.etMarca = etMarca;
	}

	public String getEtMaterial() {
		return this.etMaterial;
	}

	public void setEtMaterial(String etMaterial) {
		this.etMaterial = etMaterial;
	}

	public String getEtTipo() {
		return this.etTipo;
	}

	public void setEtTipo(String etTipo) {
		this.etTipo = etTipo;
	}

	public byte[] getMultimedia() {
		return this.multimedia;
	}

	public void setMultimedia(byte[] multimedia) {
		this.multimedia = multimedia;
	}

	public int getNVisualizaciones() {
		return this.nVisualizaciones;
	}

	public void setNVisualizaciones(int nVisualizaciones) {
		this.nVisualizaciones = nVisualizaciones;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}