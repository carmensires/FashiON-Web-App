package pl;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dl.Publicacion;
import dl.Usuario;
import bl.Ejb;


@ManagedBean
@SessionScoped
public class UsuarioBean {
	
	@EJB
	Ejb ejb=new Ejb();
	
	private Usuario usuario = new Usuario();
	private Usuario editado = new Usuario();
	private int passwordOk=3;
	private boolean passOk;
	private boolean comprobado;
	private int libre=2;
	private boolean added;

	public boolean isAdded() {
		return added;
	}

	public void setAdded(boolean added) {
		this.added = added;
	}

	public int getPasswordOk() {
		return passwordOk;
	}

	public void setPasswordOk(int passwordOk) {
		this.passwordOk = passwordOk;
	}

	public boolean isPassOk() {
		return passOk;
	}

	public void setPassOk(boolean passOk) {
		this.passOk = passOk;
	}

	public boolean isComprobado() {
		return comprobado;
	}

	public void setComprobado(boolean comprobado) {
		this.comprobado = comprobado;
	}

	public int getLibre() {
		return libre;
	}

	public void setLibre(int libre) {
		this.libre = libre;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String comprobarUsuario()
	{
		passwordOk=ejb.comprobarUsuario(usuario.getUserName(), usuario.getPassword());
		if (passwordOk==0)
			usuario=ejb.getUsuario();
		return "listaPublicaciones.xhtml";
	}
	
	public String getOkText()
	{
		String okText;
		if(passwordOk==0)
			okText="Contraseña correcta";
		else if(passwordOk==1)
			okText="No existe el usuario introducido";
		else if(passwordOk==2)
			okText="Contraseña incorrecta";
		else
			okText="ERROR.";
		return okText;
	}
	
	public String addUsuario()
	{
		added=false;
		libre=ejb.addUsuario(usuario);
		added=true;
		return getRegistradoText();
	}
	
	public String getLibreText()
	{
		String libreText;
		if(libre==0)
			libreText="Usuario se ha añadido correctamente.";
		else if(libre==1)
			libreText="Nombre de usuario ya está cogido.";
		else 
			libreText="Ya existe un usuario con ese email.";
		return libreText;
	}
	
	public String getRegistradoText()
	{
		String registradoText;
		if(added && libre==0)
			registradoText="login.xhtml";
		else 
			registradoText="";
		return registradoText;
	}
	
	public String getNSeguidores()
	{
		String nSeguidores;
		int tam=this.getSeguidores().size();
		nSeguidores=tam + " seguidores";
		return nSeguidores;
	}
	
	public String getNSeguidos()
	{
		String nSeguidos;
		int tam=this.getSeguidos().size();
		nSeguidos=tam+" seguidos";
		return nSeguidos;
	}
	
	public String getNPosts()
	{
		String nPosts;
		int tam=this.getPublicacionesUsuario().size();
		nPosts=tam+" posts";
		return nPosts;
	}
	
	public List<Usuario> getSeguidores()
	{
		List<Usuario> seguidores=ejb.getSeguidores(usuario.getIdUser());
		return seguidores;
	}
	
	public List<Usuario> getSeguidos()
	{
		List<Usuario> seguidos=ejb.getSeguidos(usuario.getIdUser());
		return seguidos;
	}
	
	public List<Publicacion> getPublicacionesUsuario()
	{
		List<Publicacion> publicacionesUsuario=ejb.getPublicacionesUsuario(usuario.getIdUser());
		return publicacionesUsuario;
	}

	public Usuario getEditado() {
		return editado;
	}

	public void setEditado(Usuario editado) {
		this.editado = editado;
	}
	
	public String editPerfil(){
		editado=usuario;
		return "editarPerfil.xhtml";
	}
	public String  editarPerfil(){
		ejb.editarPerfil(editado);
		return "perfil.xhtml";
	}
	
	
}
